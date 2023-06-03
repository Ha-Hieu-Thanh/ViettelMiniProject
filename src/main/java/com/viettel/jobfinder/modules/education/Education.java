package com.viettel.jobfinder.modules.education;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.viettel.jobfinder.modules.employee.Employee;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "education")
public class Education {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @Column(nullable = false)
  @NonNull
  private String schoolName;

  @Column(nullable = false)
  @NonNull
  private String major;

  @Column
  private String startDate;

  @Column
  private String endDate;

}
