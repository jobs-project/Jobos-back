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
    @RequestMapping(value = {"/", "/page/{pageId}"}, method = RequestMethod.GET)
    Collection<Job> readAllJob(@PathVariable("pageId") Optional<Integer> pageId) {
        return jobService.findAll(pageId.orElse(1)).getContent();
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
    @RequestMapping(value = {"/search", "/search/page/{pageId}"}, method = RequestMethod.GET)
    Collection<Job> searchJob(@PathVariable("pageId") Optional<Integer> pageId,
            @RequestParam(value = "salary", required = false) Integer salary,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description) {
        return jobService.findBySearchQuery(salary, location, title, description, pageId.orElse(1)).getContent();
    }
}
