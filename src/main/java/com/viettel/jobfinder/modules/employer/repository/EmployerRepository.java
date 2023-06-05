package com.viettel.jobfinder.modules.employer.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viettel.jobfinder.modules.employer.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
  Optional<Employer> findByUser_Id(Long id);

  @Query("SELECT e FROM Employer e JOIN User u ON e.user.id = u.id WHERE (:userEmployerId IS NULL OR u.id = :userEmployerId) AND (:username IS NULL OR u.username = :username) AND (:fullName IS NULL OR u.fullName LIKE CONCAT('%',:fullName,'%'))")
  Page<Employer> filterEmployer(Long userEmployerId, String username, String fullName, Pageable pageable);
}
