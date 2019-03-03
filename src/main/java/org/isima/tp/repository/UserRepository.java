package org.isima.tp.repository;

import java.util.Optional;
import org.isima.tp.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, String> {
  Optional<AppUser> findByResetPasswordLink(String link);
}
