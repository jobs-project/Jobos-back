package io.shifu.jobsearch.services;

import io.shifu.jobsearch.model.Job;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface JobService {

    Optional<Job> findById(Long id);

    Page<Job> findAll(Integer pageNumber);

    Page<Job> findBySearchQuery(Integer salary, String location, String title, String description, Integer pageNumber);
}
