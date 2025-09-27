package com.example.Enterprise_Portfolio_Instructions.Dao;

import com.example.Enterprise_Portfolio_Instructions.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDao extends JpaRepository<Profile, Long> {
}
