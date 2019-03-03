package org.isima.tp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.isima.tp.CurrentUser;
import org.isima.tp.model.Community;
import org.isima.tp.model.Config;
import org.isima.tp.repository.ConfigRepository;
import org.isima.tp.util.Cipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
class AdminController {

  private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

  @Autowired
  private
  ConfigRepository configRepository;

  @Autowired
  private
  Cipher cipher;

  @Autowired
  CurrentUser currentUser;

  @GetMapping(path = "")
  public List<Config> index() {

    List<Config> result = new ArrayList<>();

    Community adminCommunity = new Community().setName("admin");
    Community sysadminCommunity = new Community().setName("sysadmin");

    if (currentUser.getAppUser() != null && currentUser.getAppUser().getCommunities() != null) {
      result = configRepository.findAll().stream()
          .filter(c -> c.getKey().equals("database password") && currentUser.getAppUser().getCommunities().contains(sysadminCommunity)
                    || c.getKey().equals("indice") && currentUser.getAppUser().getCommunities().contains(adminCommunity))
          .collect(Collectors.toList());
    }
    return result;
  }

  @GetMapping(path = "/log")
  public List<String> getLogs() {

    List<String> result = null;
    if (currentUser != null) { // only for authenticated users
      try {
        result = Files.readAllLines(new File("app.log").toPath());
      } catch (IOException e) {
        LOG.error(null, e);
      }
    }
    return result;
  }
}
