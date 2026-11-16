package com.worksnet.repo;

import com.worksnet.model.AppUser;
import com.worksnet.model.Job;
import com.worksnet.model.JobApplication;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<JobApplication, Long> {
	boolean existsByJobAndApplicant(Job job, AppUser applicant);
	List<JobApplication> findByApplicantOrderByCreatedAtDesc(AppUser applicant);
	List<JobApplication> findByJobOrderByCreatedAtDesc(Job job);
}
