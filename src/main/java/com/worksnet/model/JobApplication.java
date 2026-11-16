package com.worksnet.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications", uniqueConstraints = @UniqueConstraint(columnNames = { "job_id", "applicant_id" }))
public class JobApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Job job;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private AppUser applicant;

	@Column(nullable = false, length = 2000)
	private String message;

	@Column(nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	protected JobApplication() { }

	public JobApplication(Job job, AppUser applicant, String message) {
		this.job = job;
		this.applicant = applicant;
		this.message = message;
	}

	public Long getId() { return id; }
	public Job getJob() { return job; }
	public AppUser getApplicant() { return applicant; }
	public String getMessage() { return message; }
	public LocalDateTime getCreatedAt() { return createdAt; }
}
