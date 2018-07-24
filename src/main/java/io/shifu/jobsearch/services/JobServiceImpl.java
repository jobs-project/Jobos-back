package io.shifu.jobsearch.services;

import io.shifu.jobsearch.model.Job;
import io.shifu.jobsearch.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // поиск всех
    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    // поиск по id
    @Override
    public Optional<Job> findById(Long id) {
        return jobRepository.findById(id);
    }

    // поиск по зп равно или больше
    @Override
    public List<Job> findBySearchQuery(Integer salary, String location) {
        return jobRepository.findBySearchQuery(salary, location);
    }
}
