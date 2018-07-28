package io.shifu.jobsearch.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReedJobResponse {
    @JsonProperty("results")
    private List<ReedJob> result;

    public List<ReedJob> getResult() {
        return result;
    }

    public void setResult(List<ReedJob> result) {
        this.result = result;
    }
}
