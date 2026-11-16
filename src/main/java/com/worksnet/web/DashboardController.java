package com.worksnet.web;

import com.worksnet.repo.ApplicationRepository;
import com.worksnet.repo.JobRepository;
import com.worksnet.repo.UserRepository;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	private final UserRepository users;
	private final JobRepository jobs;
	private final ApplicationRepository applications;

	public DashboardController(UserRepository users, JobRepository jobs, ApplicationRepository applications) {
		this.users = users;
		this.jobs = jobs;
		this.applications = applications;
	}

	@GetMapping("/dashboard")
	String dashboard(Principal principal, Model model) {
		var me = users.findByUsername(principal.getName()).orElseThrow();
		model.addAttribute("me", me);
		model.addAttribute("myJobs", jobs.findByOwnerOrderByPostedAtDesc(me));
		model.addAttribute("myApplications", applications.findByApplicantOrderByCreatedAtDesc(me));
		return "dashboard";
	}
}
