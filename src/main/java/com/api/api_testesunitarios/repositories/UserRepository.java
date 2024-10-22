package com.api.api_testesunitarios.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.api_testesunitarios.domain.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
	boolean existsByEmail(String email);
	Optional<UserModel>findByEmail(String email);
}
