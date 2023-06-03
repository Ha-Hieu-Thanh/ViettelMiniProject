package com.viettel.jobfinder.modules.employee.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.viettel.jobfinder.modules.application.Application;
import com.viettel.jobfinder.modules.education.Education;
import com.viettel.jobfinder.modules.education.dto.EducationResponseDto;
import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.experience.Experience;
import com.viettel.jobfinder.modules.experience.dto.ExperienceResponseDto;
import com.viettel.jobfinder.modules.skill.Skill;
import com.viettel.jobfinder.modules.skill.dto.SkillResponseDto;
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.shared.ERoleName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeResponseDto {

  private long userId;

  private String username;

  private String fullName;

  private String email;

  private String mobilePhone;

  private String location;

  private ERoleName role;

  private String gender;

  private LocalDate dateOfBirth;

  private String linkedinLink;

  private String githubLink;

  private List<SkillResponseDto> skills;

  private List<ExperienceResponseDto> experiences;

  private List<EducationResponseDto> educations;

  public EmployeeResponseDto(Employee employee) {
    this.userId = employee.getUser().getId();
    this.username = employee.getUser().getUsername();
    this.fullName = employee.getUser().getFullName();
    this.email = employee.getUser().getEmail();
    this.mobilePhone = employee.getUser().getMobilePhone();
    this.location = employee.getUser().getLocation();
    this.role = employee.getUser().getRole();
    this.gender = employee.getGender();
    this.dateOfBirth = employee.getDateOfBirth();
    this.linkedinLink = employee.getLinkedinLink();
    this.githubLink = employee.getGithubLink();
    this.skills = employee.getSkills().stream()
        .map(SkillResponseDto::new)
        .collect(Collectors.toList());
    this.experiences = employee.getExperiences().stream()
        .map(ExperienceResponseDto::new)
        .collect(Collectors.toList());
    this.educations = employee.getEducations().stream()
        .map(EducationResponseDto::new)
        .collect(Collectors.toList());
  }

}
