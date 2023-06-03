package com.viettel.jobfinder.modules.employee.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employee.dto.EditEmployeeBasicInfoRequestDto;
import com.viettel.jobfinder.modules.employee.dto.EmployeeResponseDto;
import com.viettel.jobfinder.modules.employee.service.EmployeeService;
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.modules.user.service.UserService;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.EmployeePermission;
import com.viettel.jobfinder.shared.annotation.Public;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
  @Autowired
  private EmployeeService employeeService;

  @GetMapping
  @Public
  public ResponseEntity<List<EmployeeResponseDto>> getEmployees() {
    List<EmployeeResponseDto> employees = employeeService.getEmployees().stream()
        .map(employee -> new EmployeeResponseDto(employee))
        .collect(Collectors.toList());
    return new ResponseEntity<List<EmployeeResponseDto>>(employees, HttpStatus.OK);
  }

  @GetMapping("/info/{id}")
  @Public
  public ResponseEntity<EmployeeResponseDto> getEmployeeInfoById(@PathVariable("id") long id) {
    Employee employee = employeeService.getEmployeeInfo(id);
    return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employee), HttpStatus.OK);
  }

  @PutMapping("/info")
  @EmployeePermission
  public ResponseEntity<EmployeeResponseDto> editBasicInfo(@CurrentUser("id") long id,
      @Valid @RequestBody EditEmployeeBasicInfoRequestDto editEmployeeBasicInfoRequestDto) {
    Employee employee = employeeService.editBasicInfo(id, editEmployeeBasicInfoRequestDto);
    return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employee), HttpStatus.OK);
  }

  @GetMapping("/info")
  @EmployeePermission
  public ResponseEntity<EmployeeResponseDto> getEmployeeInfo(@CurrentUser("id") long id) {
    Employee employee = employeeService.getEmployeeInfo(id);
    return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employee), HttpStatus.OK);
  }

}
