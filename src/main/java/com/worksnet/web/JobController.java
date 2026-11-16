package com.worksnet.web;

import com.worksnet.model.AppUser;
import com.worksnet.model.Job;
import com.worksnet.model.JobApplication;
import com.worksnet.model.JobType;
import com.worksnet.repo.ApplicationRepository;
import com.worksnet.repo.JobRepository;
import com.worksnet.repo.UserRepository;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JobController {

	private final JobRepository jobs;
	private final UserRepository users;
	private final ApplicationRepository applications;

	public JobController(JobRepository jobs, UserRepository users, ApplicationRepository applications) {
		this.jobs = jobs;
		this.users = users;
		this.applications = applications;
	}

	@GetMapping("/jobs/{id}")
	String detail(@PathVariable Long id, Principal principal, Model model) {
		Job job = find(id);
		boolean isOwner = principal != null && job.getOwner().getUsername().equals(principal.getName());
		boolean alreadyApplied = false;
		if (principal != null && !isOwner) {
			alreadyApplied = applications.existsByJobAndApplicant(job, currentUser(principal));
		}
		model.addAttribute("job", job);
		model.addAttribute("isOwner", isOwner);
		model.addAttribute("alreadyApplied", alreadyApplied);
		if (isOwner) {
			model.addAttribute("received", applications.findByJobOrderByCreatedAtDesc(job));
		}
		return "job";
	}

	@GetMapping("/jobs/new")
	String newForm(Model model) {
		model.addAttribute("form", new JobForm());
		model.addAttribute("types", JobType.values());
		return "job-form";
	}

	@PostMapping("/jobs")
	String create(@Valid @ModelAttribute("form") JobForm form, BindingResult result, Principal principal, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("types", JobType.values());
			return "job-form";
		}
		Job job = jobs.save(new Job(form.getTitle().trim(), form.getCompany().trim(), form.getLocation().trim(),
			form.getType(), form.getDescription().trim(), currentUser(principal)));
		return "redirect:/jobs/" + job.getId();
	}

	@PostMapping("/jobs/{id}/apply")
	String apply(@PathVariable Long id, @RequestParam String message, Principal principal, RedirectAttributes redirect) {
		Job job = find(id);
		AppUser me = currentUser(principal);
		if (job.getOwner().getId().equals(me.getId())) {
			redirect.addFlashAttribute("error", "You cannot apply to your own posting.");
		} else if (message.isBlank() || message.length() > 2000) {
			redirect.addFlashAttribute("error", "Please write a short message (max. 2000 characters).");
		} else if (applications.existsByJobAndApplicant(job, me)) {
			redirect.addFlashAttribute("error", "You already applied to this job.");
		} else {
			applications.save(new JobApplication(job, me, message.trim()));
			redirect.addFlashAttribute("msg", "Application sent.");
		}
		return "redirect:/jobs/" + id;
	}

	@PostMapping("/jobs/{id}/delete")
	String delete(@PathVariable Long id, Principal principal, RedirectAttributes redirect) {
		Job job = find(id);
		if (!job.getOwner().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		jobs.delete(job);
		redirect.addFlashAttribute("msg", "Job deleted.");
		return "redirect:/dashboard";
	}

	private Job find(Long id) {
		return jobs.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	private AppUser currentUser(Principal principal) {
		return users.findByUsername(principal.getName()).orElseThrow();
	}
}
