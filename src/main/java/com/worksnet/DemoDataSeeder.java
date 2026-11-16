package com.worksnet;

import com.worksnet.model.AppUser;
import com.worksnet.model.Job;
import com.worksnet.model.JobType;
import com.worksnet.repo.JobRepository;
import com.worksnet.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Seeds a demo account and a few postings so the app looks alive on first start. */
@Configuration
class DemoDataSeeder {

	@Bean
	CommandLineRunner seed(UserRepository users, JobRepository jobs, PasswordEncoder encoder,
			@Value("${worksnetz.seed-demo-data:true}") boolean enabled) {
		return args -> {
			if (!enabled || users.count() > 0) {
				return;
			}
			AppUser demo = users.save(new AppUser("demo", "demo@worksnetz.example", encoder.encode("demo1234"), "Demo Recruiter"));
			jobs.save(new Job("Junior Java Developer", "Nordlicht Software", "Stuttgart", JobType.FULL_TIME,
				"Join our backend team and build REST services with Spring Boot and PostgreSQL. Mentoring, code reviews and a modern CI pipeline included.", demo));
			jobs.save(new Job("Werkstudent Web Development", "Pixelwerk GmbH", "Reutlingen", JobType.PART_TIME,
				"Support our frontend team with Thymeleaf and JavaScript. 15 hours per week, flexible schedule that fits around lectures.", demo));
			jobs.save(new Job("Freelance UI/UX Designer", "Kleinstadt Studio", "Remote", JobType.FREELANCE,
				"We need a designer for a three-month project redesigning a booking platform. Figma experience is required.", demo));
			jobs.save(new Job("Praktikum Softwareentwicklung", "Schwabenbyte AG", "Tübingen", JobType.INTERNSHIP,
				"Six-month internship working on internal tools in Java. A great first step into professional software development.", demo));
		};
	}
}
