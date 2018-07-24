package io.shifu.jobsearch.controller;

import io.shifu.jobsearch.model.Job;
import io.shifu.jobsearch.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    Collection<Job> readJob() {
        return jobService.findAll();
    }
}
