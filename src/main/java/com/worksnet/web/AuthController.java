package com.worksnet.web;

import com.worksnet.model.AppUser;
import com.worksnet.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

	private final UserRepository users;
	private final PasswordEncoder encoder;

	public AuthController(UserRepository users, PasswordEncoder encoder) {
		this.users = users;
		this.encoder = encoder;
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@GetMapping("/register")
	String registerForm(Model model) {
		model.addAttribute("form", new RegisterForm());
		return "register";
	}

	@PostMapping("/register")
	String register(@Valid @ModelAttribute("form") RegisterForm form, BindingResult result, RedirectAttributes redirect) {
		if (users.existsByUsername(form.getUsername())) {
			result.rejectValue("username", "taken", "this username is already taken");
		}
		if (users.existsByEmail(form.getEmail())) {
			result.rejectValue("email", "taken", "this email is already registered");
		}
		if (result.hasErrors()) {
			return "register";
		}
		users.save(new AppUser(form.getUsername(), form.getEmail(), encoder.encode(form.getPassword()), form.getFullName()));
		redirect.addFlashAttribute("msg", "Account created. You can log in now.");
		return "redirect:/login";
	}
}
