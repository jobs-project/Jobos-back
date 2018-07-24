package io.shifu.jobsearch.controller;

import io.shifu.jobsearch.exception.VacancyNotFoundException;
import io.shifu.jobsearch.model.Job;
import io.shifu.jobsearch.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // общий список вакансий
    @RequestMapping(value = "/", method = RequestMethod.GET)
    Collection<Job> readAllJob() {
        return jobService.findAll();
    }

    // запрос конкретной вакансии
    @RequestMapping(value = "/vacancy/{vacancyId}", method = RequestMethod.GET)
    Job readJob(@PathVariable("vacancyId") Long vacancyId) {
        Optional<Job> optionalJob = jobService.findById(vacancyId);
        if (optionalJob.isPresent()) {
            return optionalJob.get();
        } else {
            throw new VacancyNotFoundException(vacancyId);
        }
    }

    // поиск
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    Collection<Job> searchJob(
            @RequestParam(value = "salary", required = false) Integer salary,
            @RequestParam(value = "location", required = false) String location) {
        return jobService.findBySearchQuery(salary, location);
    }
}
