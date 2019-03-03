package org.isima.tp;


import org.isima.tp.model.AppUser;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="request", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class CurrentUser {
  private AppUser appUser;

  public AppUser getAppUser() {
    return appUser;
  }

  @SuppressWarnings("UnusedReturnValue")
  public CurrentUser setAppUser(AppUser appUser) {
    this.appUser = appUser;
    return this;
  }
}

