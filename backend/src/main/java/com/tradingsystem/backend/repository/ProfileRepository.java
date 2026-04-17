package com.tradingsystem.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradingsystem.backend.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
}
