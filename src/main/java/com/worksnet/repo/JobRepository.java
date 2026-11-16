package com.worksnet.repo;

import com.worksnet.model.AppUser;
import com.worksnet.model.Job;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findAllByOrderByPostedAtDesc();
	List<Job> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCaseOrderByPostedAtDesc(String title, String company);
	List<Job> findByOwnerOrderByPostedAtDesc(AppUser owner);
}
