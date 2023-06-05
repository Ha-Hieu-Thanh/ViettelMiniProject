package com.viettel.jobfinder.modules.skill.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employee.service.EmployeeService;
import com.viettel.jobfinder.modules.skill.Skill;
import com.viettel.jobfinder.modules.skill.dto.CreateEmployeeSkillRequestDto;
import com.viettel.jobfinder.modules.skill.dto.EditEmployeeSkillRequestDto;
import com.viettel.jobfinder.modules.skill.dto.SkillResponseDto;
import com.viettel.jobfinder.modules.skill.service.SkillService;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.EmployeePermission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/skill")
@EmployeePermission
@Tag(name = "Skill")
@Order(4)
public class SkillController {
  @Autowired
  private SkillService skillService;
  @Autowired
  private EmployeeService employeeService;

  @PostMapping
  @Operation(summary = "Create employee skill")
  public ResponseEntity<SkillResponseDto> createEmployeeSkill(@CurrentUser("id") long userId,
      @Valid @RequestBody CreateEmployeeSkillRequestDto createEmployeeSkillRequestDto) {
    Skill skill = skillService.createEmployeeSkill(userId, createEmployeeSkillRequestDto);
    return new ResponseEntity<>(new SkillResponseDto(skill),
        HttpStatus.CREATED);
  }

  @PutMapping("/{skillId}")
  @Operation(summary = "Edit employee skill")
  public ResponseEntity<SkillResponseDto> editEmployeeSkill(@CurrentUser("id") long userId,
      @PathVariable("skillId") long skillId,
      @Valid @RequestBody EditEmployeeSkillRequestDto editEmployeeSkillRequestDto) {
    Skill skill = skillService.editEmployeeSkill(userId, skillId, editEmployeeSkillRequestDto);
    return new ResponseEntity<>(new SkillResponseDto(skill),
        HttpStatus.OK);

  }

  @DeleteMapping("/{skillId}")
  @Operation(summary = "Delete employee skill")
  public ResponseEntity<Object> deleteEmployeeSkill(@CurrentUser("id") long userId,
      @PathVariable("skillId") long skillId) {
    skillService.deleteEmployeeSkill(userId, skillId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
