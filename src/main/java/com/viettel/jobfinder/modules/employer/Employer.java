package com.viettel.jobfinder.modules.employer;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.viettel.jobfinder.modules.job.Job;
import com.viettel.jobfinder.modules.user.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "employer")
public class Employer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToOne
  @NonNull
  @JoinColumn(name = "user_id")
  private User user;

  @Column
  private String websiteLink;

  @Column
  private String description;

  @OneToMany(mappedBy = "employer")
  private List<Job> jobs;
  // Constructors, getters, and setters
}
