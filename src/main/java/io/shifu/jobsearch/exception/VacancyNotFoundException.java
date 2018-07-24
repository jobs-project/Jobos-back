package io.shifu.jobsearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VacancyNotFoundException extends RuntimeException {
    public VacancyNotFoundException(Long vacancyId) {
        super("could not find vacancy '" + vacancyId + "'.");
    }
}
