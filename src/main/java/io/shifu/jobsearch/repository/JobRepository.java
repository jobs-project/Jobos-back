package io.shifu.jobsearch.repository;

import io.shifu.jobsearch.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
     List<Job> findAll();
}
