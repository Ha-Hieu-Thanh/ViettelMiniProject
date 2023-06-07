package com.viettel.jobfinder.modules.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.viettel.jobfinder.modules.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);
}