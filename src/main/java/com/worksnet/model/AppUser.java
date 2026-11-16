package com.worksnet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 30)
	private String username;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String fullName;

	protected AppUser() { }

	public AppUser(String username, String email, String password, String fullName) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
	}

	public Long getId() { return id; }
	public String getUsername() { return username; }
	public String getEmail() { return email; }
	public String getPassword() { return password; }
	public String getFullName() { return fullName; }
}
