package com.viettel.jobfinder.modules.employee.controller;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employee.dto.EditEmployeeBasicInfoRequestDto;
import com.viettel.jobfinder.modules.employee.dto.EmployeeResponseDto;
import com.viettel.jobfinder.modules.employee.service.EmployeeService;
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.modules.user.service.UserService;
import com.viettel.jobfinder.shared.Utils;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.EmployeePermission;
import com.viettel.jobfinder.shared.annotation.Public;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employee")
@Tag(name = "Employee")
@Order(2)
public class EmployeeController {
  @Autowired
  private EmployeeService employeeService;

  @GetMapping
  @Public
  @Operation(summary = "Get all employees")
  public ResponseEntity<List<EmployeeResponseDto>> getEmployees(
      @RequestParam(name = "userEmployeeId", required = false) Long userEmployeeId,
      @RequestParam(name = "username", required = false) String username,
      @RequestParam(name = "fullName", required = false) String fullName,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    Page<Employee> employees = employeeService.filterEmployee(userEmployeeId, username, fullName, pageable);
    Function<Employee, EmployeeResponseDto> converter = EmployeeResponseDto::new;
    return Utils.buildPaginationResponse(employees, converter);
  }

  // @GetMapping("/info/{id}")
  // @Public
  // public ResponseEntity<EmployeeResponseDto>
  // getEmployeeInfoById(@PathVariable("id") long id) {
  // Employee employee = employeeService.getEmployeeInfo(id);
  // return new ResponseEntity<EmployeeResponseDto>(new
  // EmployeeResponseDto(employee), HttpStatus.OK);
  // }

  @PutMapping("/my-info")
  @EmployeePermission
  @Operation(summary = "Edit basic info of employee")
  public ResponseEntity<EmployeeResponseDto> editBasicInfo(@CurrentUser("id") long id,
      @Valid @RequestBody EditEmployeeBasicInfoRequestDto editEmployeeBasicInfoRequestDto) {
    Employee employee = employeeService.editBasicInfo(id, editEmployeeBasicInfoRequestDto);
    return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employee), HttpStatus.OK);
  }

  @GetMapping("/my-info")
  @EmployeePermission
  @Operation(summary = "Get basic info of employee")
  public ResponseEntity<EmployeeResponseDto> getEmployeeInfo(@CurrentUser("id") long id) {
    Employee employee = employeeService.getEmployeeInfo(id);
    return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employee), HttpStatus.OK);
  }

}
