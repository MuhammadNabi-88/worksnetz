package com.worksnet.web;

import com.worksnet.model.Job;
import com.worksnet.repo.JobRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	private final JobRepository jobs;

	public HomeController(JobRepository jobs) {
		this.jobs = jobs;
	}

	@GetMapping("/")
	String index(@RequestParam(required = false) String q, Model model) {
		boolean search = q != null && !q.isBlank();
		List<Job> result = search
			? jobs.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCaseOrderByPostedAtDesc(q.trim(), q.trim())
			: jobs.findAllByOrderByPostedAtDesc();
		model.addAttribute("jobs", result);
		model.addAttribute("q", q == null ? "" : q);
		return "index";
	}
}
