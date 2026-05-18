package com.tradingsystem.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.backend.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmail(String email);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"user"})
    List<Profile> findAllByActiveTrades(Boolean activeTrades);
}
