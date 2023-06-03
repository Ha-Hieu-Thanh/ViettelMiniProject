package com.viettel.jobfinder.modules.skill.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employee.repository.EmployeeRepository;
import com.viettel.jobfinder.modules.employee.service.EmployeeService;
import com.viettel.jobfinder.modules.skill.Skill;
import com.viettel.jobfinder.modules.skill.dto.CreateEmployeeSkillRequestDto;
import com.viettel.jobfinder.modules.skill.dto.EditEmployeeSkillRequestDto;
import com.viettel.jobfinder.modules.skill.repository.SkillRepository;
import com.viettel.jobfinder.shared.exception.NotFoundException;

@Service
public class SkillService {
  @Autowired
  private SkillRepository skillRepository;
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private EmployeeService employeeService;

  public Skill createEmployeeSkill(long userId,
      CreateEmployeeSkillRequestDto createEmployeeSkillRequestDto) {
    Skill skill = new Skill();
    skill.setTitle(createEmployeeSkillRequestDto.getTitle());
    skill.setCertificateLink(createEmployeeSkillRequestDto.getCertificateLink());
    skill.setScore(createEmployeeSkillRequestDto.getScore());
    Employee employee = employeeService.getEmployeeInfo(userId);
    skill.setEmployee(employee);
    return skillRepository.save(skill);
  }

  public Skill editEmployeeSkill(long userId, long skillId,
      EditEmployeeSkillRequestDto editEmployeeSkillRequestDto) {
    Skill skill = skillRepository.findById(skillId)
        .orElseThrow(() -> new NotFoundException("Skill not found"));
    // check if user have skill
    Employee employee = employeeService.getEmployeeInfo(userId);
    if (!employee.getSkills().contains(skill)) {
      throw new NotFoundException("Skill not found || You dont have permission to edit this skill");
    }
    if (Objects.nonNull(editEmployeeSkillRequestDto.getTitle())) {
      skill.setTitle(editEmployeeSkillRequestDto.getTitle());
    }
    if (Objects.nonNull(editEmployeeSkillRequestDto.getCertificateLink())) {
      skill.setCertificateLink(editEmployeeSkillRequestDto.getCertificateLink());
    }
    if (Objects.nonNull(editEmployeeSkillRequestDto.getScore())) {
      skill.setScore(editEmployeeSkillRequestDto.getScore());
    }

    return skillRepository.save(skill);
  }

  public void deleteEmployeeSkill(long userId, long skillId) {
    Skill skill = skillRepository.findById(skillId)
        .orElseThrow(() -> new NotFoundException("Skill not found"));
    // check if user have skill
    Employee employee = employeeService.getEmployeeInfo(userId);
    if (!employee.getSkills().contains(skill)) {
      throw new NotFoundException("Skill not found || You dont have permission to delete this skill");
    }
    skillRepository.delete(skill);
  }

}
