package org.isima.tp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.isima.tp.model.AppUser;
import org.isima.tp.repository.CommunityRepository;
import org.isima.tp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);
  @Value("${security.jwt.uri:/auth/**}")
  private String uri;

  @Value("${security.jwt.prefix:Bearer }")
  private String prefix;

  @Value("${security.jwt.secret}")
  private String secret;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CommunityRepository communityRepository;

  @Autowired
  CurrentUser currentUser;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
  throws ServletException, IOException {

    // 1. get the authentication header. Tokens are supposed to be passed in the authentication header
    String header = request.getHeader("Authorization");

    // 2. validate the header and check the prefix
    if (header == null || !header.startsWith("Bearer")) {
      chain.doFilter(request, response); // If not valid, go to the next filter.
      return;
    }

    // If there is no token provided and hence the user won't be authenticated.
    // It's Ok. Maybe the user accessing a public path or asking for a token.

    // All secured paths that needs a token are already defined and secured in config class.
    // And If user tried to access without access token, then he won't be authenticated and an exception will be thrown.

    // 3. Get the token
    String token = header.replace("Bearer", "");

    try {  // exceptions might be thrown in creating the claims if for example the token is expired

      // 4. Validate the token
      Jws<Claims> claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
          .parseClaimsJws(token);

      String email = claims.getBody().getSubject();
      if (email != null) {
        // 5. Create auth object
        // Now, user is authenticated
        Optional<AppUser> optional = userRepository.findById(email);
        optional.ifPresent(appUser -> currentUser.setAppUser(appUser));
      }

    } catch (Exception e) {
      LOG.error(null, e);
      // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
      currentUser.setAppUser(null);
    }

    // go to the next filter in the filter chain
    chain.doFilter(request, response);
  }
}
