package io.shifu.jobsearch.controller;

import io.shifu.jobsearch.exception.VacancyNotFoundException;
import io.shifu.jobsearch.model.Job;
import io.shifu.jobsearch.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<List<Job>> readAllJob(@RequestParam(value = "page", required = false) Integer pageId) {
        Page<Job> result = jobService.findAll(pageId == null ? 1 : pageId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("totalFound", Long.toString(result.getTotalElements()));
        responseHeaders.set("totalPage", Integer.toString(result.getTotalPages()));
        responseHeaders.set("currentPage", Integer.toString(result.getNumber() + 1));
        return new ResponseEntity<>(result.getContent(), responseHeaders, HttpStatus.OK);
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
    ResponseEntity<List<Job>> searchJob(@RequestParam(value = "page", required = false) Integer pageId,
                                        @RequestParam(value = "salary", required = false) Integer salary,
                                        @RequestParam(value = "location", required = false) String location,
                                        @RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "description", required = false) String description) {
        Page<Job> result = jobService.findBySearchQuery(salary, location, title, description, pageId == null ? 1 : pageId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("totalFound", Long.toString(result.getTotalElements()));
        responseHeaders.set("totalPage", Integer.toString(result.getTotalPages()));
        responseHeaders.set("currentPage", Integer.toString(result.getNumber() + 1));
        return new ResponseEntity<>(result.getContent(), responseHeaders, HttpStatus.OK);
    }
}
