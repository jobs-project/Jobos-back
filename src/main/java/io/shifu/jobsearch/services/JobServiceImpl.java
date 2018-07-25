package io.shifu.jobsearch.services;

import io.shifu.jobsearch.model.Job;
import io.shifu.jobsearch.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final static int PAGE_SIZE = 5;

    private final JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // поиск всех
    @Override
    public Page<Job> findAll(Integer pageNumber) {
        return jobRepository.findAll(PageRequest.of(pageNumber-1, PAGE_SIZE, Sort.Direction.DESC, "date"));
    }

    // поиск по id
    @Override
    public Optional<Job> findById(Long id) {
        return jobRepository.findById(id);
    }

    // поиск по зп равно или больше
    @Override
    public Page<Job> findBySearchQuery(Integer salary, String location, String title, String description, Integer pageNumber) {
        return jobRepository.findBySearchQuery(salary, location, title, description, PageRequest.of(pageNumber-1, PAGE_SIZE, Sort.Direction.DESC, "date"));
    }

    @Transactional
    @Override
    public void saveList(List<Job> jobs) {
        jobRepository.saveAll(jobs);
    }

    @Override
    public Optional<Job> findBySiteid(String site, String siteId) {
        return jobRepository.findBySiteAndSiteId(site, siteId);
    }
}
