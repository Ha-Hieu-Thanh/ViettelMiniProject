package com.viettel.jobfinder.modules.education.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.jobfinder.modules.education.Education;
import com.viettel.jobfinder.modules.education.dto.CreateEmployeeEducationRequestDto;
import com.viettel.jobfinder.modules.education.dto.EditEmployeeEducationRequestDto;
import com.viettel.jobfinder.modules.education.dto.EducationResponseDto;
import com.viettel.jobfinder.modules.education.service.EducationService;
import com.viettel.jobfinder.modules.employee.service.EmployeeService;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.EmployeePermission;

@RestController
@RequestMapping("/education")
@EmployeePermission
public class EducationController {
  @Autowired
  private EducationService educationService;

  @PostMapping
  public ResponseEntity<EducationResponseDto> createEmployeeEducation(@CurrentUser("id") long userId,
      @Valid @RequestBody CreateEmployeeEducationRequestDto createEmployeeEducationRequestDto) {
    Education createdEducation = educationService.createEmployeeEducation(userId, createEmployeeEducationRequestDto);
    return new ResponseEntity<>(new EducationResponseDto(createdEducation), HttpStatus.CREATED);
  }

  @PutMapping("/{educationId}")
  public ResponseEntity<EducationResponseDto> editEmployeeEducation(@CurrentUser("id") long userId,
      @PathVariable("educationId") long educationId,
      @Valid @RequestBody EditEmployeeEducationRequestDto editEmployeeEducationRequestDto) {
    Education editedEducation = educationService.editEmployeeEducation(userId, educationId,
        editEmployeeEducationRequestDto);
    return new ResponseEntity<>(new EducationResponseDto(editedEducation), HttpStatus.OK);
  }

  @DeleteMapping("/{educationId}")
  public ResponseEntity<Object> deleteEmployeeEducation(@CurrentUser("id") long userId,
      @PathVariable("educationId") long educationId) {
    educationService.deleteEmployeeEducation(userId, educationId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
