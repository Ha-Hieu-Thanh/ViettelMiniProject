package com.viettel.jobfinder.modules.employee.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viettel.jobfinder.modules.employee.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByUser_Id(long userId);

  @Query("SELECT e FROM Employee e JOIN User u ON e.user.id = u.id WHERE (:userEmployeeId IS NULL OR u.id = :userEmployeeId) AND (:username IS NULL OR u.username = :username) AND (:fullName IS NULL OR u.fullName LIKE CONCAT('%',:fullName,'%'))")
  Page<Employee> filterEmployee(Long userEmployeeId, String username, String fullName, Pageable pageable);
}
