package com.viettel.jobfinder.modules.experience.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettel.jobfinder.modules.experience.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
