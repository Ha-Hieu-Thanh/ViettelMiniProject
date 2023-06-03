package com.viettel.jobfinder.modules.experience.controller;

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

import com.viettel.jobfinder.modules.experience.Experience;
import com.viettel.jobfinder.modules.experience.dto.CreateEmployeeExperienceRequestDto;
import com.viettel.jobfinder.modules.experience.dto.EditEmployeeExperienceRequestDto;
import com.viettel.jobfinder.modules.experience.dto.ExperienceResponseDto;
import com.viettel.jobfinder.modules.experience.service.ExperienceService;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.EmployeePermission;

@RestController
@RequestMapping("/experience")
@EmployeePermission
public class ExperienceController {
  @Autowired
  private ExperienceService experienceService;

  @PostMapping
  public ResponseEntity<ExperienceResponseDto> createEmployeeExperience(
      @CurrentUser("id") long userId,
      @Valid @RequestBody CreateEmployeeExperienceRequestDto createEmployeeExperienceRequestDto) {
    Experience createdExperience = experienceService.createEmployeeExperience(userId,
        createEmployeeExperienceRequestDto);
    return new ResponseEntity<>(new ExperienceResponseDto(createdExperience), HttpStatus.CREATED);
  }

  @PutMapping("/{experienceId}")
  public ResponseEntity<ExperienceResponseDto> editEmployeeExperience(
      @CurrentUser("id") long userId,
      @PathVariable("experienceId") long experienceId,
      @Valid @RequestBody EditEmployeeExperienceRequestDto editEmployeeExperienceRequestDto) {
    Experience editedExperience = experienceService.editEmployeeExperience(userId, experienceId,
        editEmployeeExperienceRequestDto);
    return new ResponseEntity<>(new ExperienceResponseDto(editedExperience), HttpStatus.OK);
  }

  @DeleteMapping("/{experienceId}")
  public ResponseEntity<Object> deleteEmployeeExperience(
      @CurrentUser("id") long userId,
      @PathVariable("experienceId") long experienceId) {
    experienceService.deleteEmployeeExperience(userId, experienceId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
