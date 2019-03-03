package org.isima.tp.repository;

import org.isima.tp.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, String> {
}
