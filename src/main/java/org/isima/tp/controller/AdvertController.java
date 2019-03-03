package org.isima.tp.controller;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.isima.tp.CurrentUser;
import org.isima.tp.model.Advert;
import org.isima.tp.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/advert")
class AdvertController {

  @Autowired
  private
  AdvertRepository advertRepository;

  @Autowired
  CurrentUser currentUser;

  @GetMapping
  public Iterable<Advert> index() {
    List<Advert> adverts = advertRepository.findAll();
    Collections.shuffle(adverts, new Random(System.nanoTime()));
    return adverts;
  }

  @PostMapping
  public Advert create(@RequestBody Advert advert) {
    if (currentUser.getAppUser() != null) { // user is authenticated
      advert.setAuthor(currentUser.getAppUser());
      int sponsorIndex = currentUser.getAppUser().getCommunities().indexOf(advert.getSponsor());
      if (sponsorIndex >= 0) {
        advert.setSponsor(currentUser.getAppUser().getCommunities().get(sponsorIndex));
        return advertRepository.save(advert);
      } else {
        throw new RuntimeException("forbidden");
      }
    } else {
      throw new RuntimeException("forbidden");
    }
  }

}
