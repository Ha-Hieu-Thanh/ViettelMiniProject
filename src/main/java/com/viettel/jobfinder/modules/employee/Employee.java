package com.viettel.jobfinder.modules.employee;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.viettel.jobfinder.modules.application.Application;
import com.viettel.jobfinder.modules.education.Education;
import com.viettel.jobfinder.modules.experience.Experience;
import com.viettel.jobfinder.modules.skill.Skill;
import com.viettel.jobfinder.modules.user.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToOne
  @NonNull
  @JoinColumn(name = "user_id")
  private User user;

  private String gender;

  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private LocalDate dateOfBirth;

  @Column
  private String linkedinLink;

  @Column
  private String githubLink;

  @Lob
  @Column(name = "resume")
  private byte[] resume;

  @OneToMany(mappedBy = "employee")
  private List<Skill> skills;

  @OneToMany(mappedBy = "employee")
  private List<Experience> experiences;

  @OneToMany(mappedBy = "employee")
  private List<Education> educations;

  @OneToMany(mappedBy = "employee")
  private List<Application> applications;
}
