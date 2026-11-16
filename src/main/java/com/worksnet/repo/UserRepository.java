package com.worksnet.repo;

import com.worksnet.model.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	Optional<AppUser> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
