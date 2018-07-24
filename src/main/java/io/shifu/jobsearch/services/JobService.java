package io.shifu.jobsearch.services;

import io.shifu.jobsearch.model.Job;

import java.util.List;

public interface JobService {
    List<Job> findAll();
}
