package com.viettel.jobfinder.modules.employer.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.employer.dto.EditEmployerBasicInfoRequestDto;
import com.viettel.jobfinder.modules.employer.dto.EmployerResponseDto;
import com.viettel.jobfinder.modules.employer.service.EmployerService;
import com.viettel.jobfinder.modules.user.service.UserService;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.EmployerPermission;
import com.viettel.jobfinder.shared.annotation.Public;

@RestController
@RequestMapping("/employer")
public class EmployerController {
  @Autowired
  private EmployerService employerService;

  @GetMapping
  @Public
  public ResponseEntity<List<EmployerResponseDto>> getEmployers() {
    List<EmployerResponseDto> employers = employerService.getEmployers().stream()
        .map(employer -> new EmployerResponseDto(employer))
        .collect(Collectors.toList());
    return new ResponseEntity<List<EmployerResponseDto>>(employers, HttpStatus.OK);

  }

  @GetMapping("/info/{id}")
  @Public
  public ResponseEntity<EmployerResponseDto> getEmployerInfoById(@PathVariable("id") long id) {
    Employer employer = employerService.getEmployerInfo(id);
    return new ResponseEntity<EmployerResponseDto>(new EmployerResponseDto(employer), HttpStatus.OK);
  }

  @PutMapping("/info")
  @EmployerPermission
  public ResponseEntity<EmployerResponseDto> editBasicInfo(@CurrentUser("id") long id,
      @Valid @RequestBody EditEmployerBasicInfoRequestDto editEmployerBasicInfoRequestDto) {
    Employer employer = employerService.editBasicInfo(id, editEmployerBasicInfoRequestDto);
    return new ResponseEntity<EmployerResponseDto>(new EmployerResponseDto(employer), HttpStatus.OK);
  }

  @GetMapping("/info")
  @EmployerPermission
  public ResponseEntity<EmployerResponseDto> getEmployerInfo(@CurrentUser("id") long id) {
    Employer employer = employerService.getEmployerInfo(id);
    return new ResponseEntity<EmployerResponseDto>(new EmployerResponseDto(employer), HttpStatus.OK);
  }

}
