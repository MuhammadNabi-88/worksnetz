package com.worksnet.model;

public enum JobType {
	FULL_TIME("Full-time"), PART_TIME("Part-time"), FREELANCE("Freelance"), INTERNSHIP("Internship");

	private final String label;

	JobType(String label) { this.label = label; }

	public String getLabel() { return label; }
}
