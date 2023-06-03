package com.viettel.jobfinder.modules.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viettel.jobfinder.modules.employee.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByUser_Id(long userId);
}
