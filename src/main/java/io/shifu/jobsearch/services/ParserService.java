package io.shifu.jobsearch.services;

import io.shifu.jobsearch.model.Job;
import io.shifu.jobsearch.repository.JobRepository;

import java.util.Optional;

public abstract class ParserService {

    private final JobRepository jobRepository;

    public ParserService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    Optional<Job> findBySiteid(String site, String siteId) {
        return jobRepository.findBySiteAndSiteId(site, siteId);
    }

    abstract void parse();
}
