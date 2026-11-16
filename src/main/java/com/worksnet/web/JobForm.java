package com.worksnet.web;

import com.worksnet.model.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class JobForm {

	@NotBlank @Size(max = 120)
	private String title;

	@NotBlank @Size(max = 120)
	private String company;

	@NotBlank @Size(max = 120)
	private String location;

	@NotNull
	private JobType type = JobType.FULL_TIME;

	@NotBlank @Size(min = 20, max = 4000)
	private String description;

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getCompany() { return company; }
	public void setCompany(String company) { this.company = company; }
	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }
	public JobType getType() { return type; }
	public void setType(JobType type) { this.type = type; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
}
