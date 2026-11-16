package com.worksnet.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String company;

	@Column(nullable = false)
	private String location;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private JobType type;

	@Column(nullable = false, length = 4000)
	private String description;

	@Column(nullable = false)
	private LocalDateTime postedAt = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private AppUser owner;

	@OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
	private List<JobApplication> applications = new ArrayList<>();

	protected Job() { }

	public Job(String title, String company, String location, JobType type, String description, AppUser owner) {
		this.title = title;
		this.company = company;
		this.location = location;
		this.type = type;
		this.description = description;
		this.owner = owner;
	}

	public Long getId() { return id; }
	public String getTitle() { return title; }
	public String getCompany() { return company; }
	public String getLocation() { return location; }
	public JobType getType() { return type; }
	public String getDescription() { return description; }
	public LocalDateTime getPostedAt() { return postedAt; }
	public AppUser getOwner() { return owner; }
	public List<JobApplication> getApplications() { return applications; }
}
