package io.shifu.jobsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "job")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReedJob extends AbstractJob {

    @Override
    @JsonIgnore
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    @JsonIgnore
    public void setSite(String site) {
        super.setSite(site);
    }

    @Override
    @JsonProperty("id")
    public void setSiteId(String siteId) {
        super.setSiteId(siteId);
        super.setSite("github");
    }

    @Override
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public void setDate(Date date) {
        super.setDate(date);
    }

    @Override
    @JsonProperty("jobTitle")
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    @JsonProperty("locationName")
    public void setLocation(String location) {
        super.setLocation(location);
    }

    @Override
    @JsonProperty("maximumSalary")
    public void setSalary(Integer salary) {
        super.setSalary(salary);
    }

    @Override
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        super.setCurrency(currency);
    }

    @Override
    @JsonProperty("employerName")
    public void setCompany(String company) {
        super.setCompany(company);
    }

    @Override
    @JsonProperty("jobDescription")
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    @JsonProperty("jobUrl")
    public void setUrl(String url) {
        super.setUrl(url);
    }

}
