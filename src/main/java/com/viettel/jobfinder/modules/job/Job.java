package com.viettel.jobfinder.modules.job;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.viettel.jobfinder.modules.application.Application;
import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employer.Employer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
@Table(name = "job")
public class Job {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "employer_id")
  private Employer employer;

  @Column(nullable = false)
  @NonNull
  private String title;

  @Column(nullable = false)
  @NonNull
  private String description;

  @Column(nullable = false)
  @NonNull
  private String location;

  @Column(nullable = false)
  @NonNull
  private Double salary;

  @Column(nullable = false, columnDefinition = "boolean default true")
  private boolean active;

  @OneToMany(mappedBy = "job")
  private List<Application> applications;
}
