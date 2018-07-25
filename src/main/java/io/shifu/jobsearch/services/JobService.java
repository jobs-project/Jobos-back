package io.shifu.jobsearch.services;

import io.shifu.jobsearch.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface JobService {

    Optional<Job> findById(Long id);

    Optional<Job> findBySiteid(String site, String siteId);

    Page<Job> findAll(Integer pageNumber);

    Page<Job> findBySearchQuery(Integer salary, String location, String title, String description, Integer pageNumber);

    @Transactional
    void saveList(List<Job> jobs);
}
