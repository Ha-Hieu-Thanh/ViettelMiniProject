package com.viettel.jobfinder.modules.experience;

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
@Entity
@NoArgsConstructor
@Table(name = "experience")
public class Experience {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @Column(nullable = false)
  @NonNull
  private String companyName;

  @Column(nullable = false)
  @NonNull
  private String position;

  @Column
  private String startDate;

  @Column
  private String endDate;

}
