package io.shifu.jobsearch.services;

import io.shifu.jobsearch.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<Job> findAll();

    Optional<Job> findById(Long id);

    List<Job> findBySearchQuery(Integer salary, String location);
}
