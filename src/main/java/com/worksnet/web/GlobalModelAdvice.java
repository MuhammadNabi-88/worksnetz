package com.worksnet.web;

import java.security.Principal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/** Makes the logged-in username available to every template as ${currentUsername}. */
@ControllerAdvice
public class GlobalModelAdvice {

	@ModelAttribute("currentUsername")
	String currentUsername(Principal principal) {
		return principal == null ? null : principal.getName();
	}
}
