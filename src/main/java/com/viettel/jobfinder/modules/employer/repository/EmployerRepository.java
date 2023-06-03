package com.viettel.jobfinder.modules.employer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettel.jobfinder.modules.employer.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
  Optional<Employer> findByUser_Id(Long id);
}
