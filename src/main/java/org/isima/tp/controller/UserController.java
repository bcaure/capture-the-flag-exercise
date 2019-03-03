package org.isima.tp.controller;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGridException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.isima.tp.CurrentUser;
import org.isima.tp.model.AppUser;
import org.isima.tp.model.Community;
import org.isima.tp.repository.CommunityRepository;
import org.isima.tp.repository.UserRepository;
import org.isima.tp.util.Cipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/user")
class UserController {

  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  @Autowired
  Cipher cipher;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CommunityRepository communityRepository;

  @Value("${security.jwt.secret}")
  private String secret;

  @Autowired
  CurrentUser currentUser;

  @Autowired(required = false)
  SendGrid sendGrid;

  @GetMapping
  public List<Community> findCommunities() {
    if (currentUser.getAppUser() == null || currentUser.getAppUser().getCommunities() == null) {
      throw new RuntimeException("forbidden");
    }
    List<AppUser> users = userRepository.findAll();
    List<Community> communities = communityRepository.findAll().stream()
        // Current user communities only
        .filter(community -> currentUser.getAppUser().getCommunities().contains(community))
        .collect(Collectors.toList());

    // Add users to each community
    communities.forEach(community -> {
      List<AppUser> communityUsers = users.stream()
          .filter(user -> user.getCommunities().contains(community))
          .map(user -> new AppUser(user.getEmail(), user.getName())) // Copy user without communities to prevent JSON serialization infinite recursive calls
          .collect(Collectors.toList());
      community.setUsers(communityUsers);
    });
    return communities;
  }

  @PostMapping(path = "{email}/sign-in")
  public AppUser signIn(@PathVariable String email, @RequestBody @NotNull String password) {

    AppUser user = userRepository.findById(email)
        .orElseThrow(() -> new RuntimeException("forbidden"));

    if (!Arrays.equals(user.getPassword(), cipher.cipher(password))) {
      throw new RuntimeException("forbidden");
    }

    Key key = Keys.hmacShaKeyFor(secret.getBytes());
    String jws = Jwts.builder()
        .setSubject(user.getEmail())
        .claim("communities", user.getCommunities())
        .signWith(key).compact();

    currentUser.setAppUser(user);

    user.setToken(jws);
    return user;
  }

  @GetMapping(path = "{email}/forgotten-password")
  public void sendResetPasswordLink(@PathVariable String email) throws SendGridException {
    Optional<AppUser> user = userRepository.findById(email);

    if (user.isPresent()) {

      // Generate random reset link param
      String randomString = String.valueOf(new Random().nextLong());

      // Save it for later control
      userRepository.save(user.get().setResetPasswordLink(randomString));

      // Generate reset link
      String resetLink = ServletUriComponentsBuilder.fromCurrentContextPath().queryParam("key", randomString).toUriString();

      // Build message with link

      Email message = new Email();
      message.addTo(email);
      message.setSubject("Password reset link");
      message.setText("Please click link to reset your 'Free ads here' website password: \n"+resetLink);

      // Send message
      if (sendGrid != null)
        sendGrid.send(message);
      else
        LOG.error("No send mail service available");
        LOG.error("Cannot send link {}", resetLink);
    }
  }


  @PostMapping(path = "{email}/reset-password/{key}")
  public void resetPassword(@PathVariable String email, @PathVariable String key, @RequestBody @NotNull String password) {
    userRepository.findByResetPasswordLink(key).ifPresent(user -> {
      if (user.getEmail().equals(email)) {
        user.setPassword(cipher.cipher(password));
        user.setResetPasswordLink(null);
        userRepository.save(user);
      }
    });
  }
}
