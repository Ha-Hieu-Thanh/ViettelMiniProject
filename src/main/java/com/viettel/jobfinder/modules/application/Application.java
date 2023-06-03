package com.viettel.jobfinder.modules.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.job.Job;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
@Table(name = "application")
public class Application {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @ManyToOne
  @JoinColumn(name = "job_id")
  private Job job;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private boolean accepted;

  @Column
  private String message;
}
