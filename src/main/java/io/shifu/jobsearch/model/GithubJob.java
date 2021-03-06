package io.shifu.jobsearch.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "job")
public class GithubJob extends AbstractJob {
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
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM d HH:mm:ss z yyyy")
    public void setDate(Date date) {
        super.setDate(date);
    }

    @Override
    @JsonProperty("title")
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    @JsonProperty("location")
    public void setLocation(String location) {
        super.setLocation(location);
    }

    @Override
    @JsonIgnore
    public void setSalary(Integer salary) {
        super.setSalary(salary);
    }

    @Override
    @JsonIgnore
    public void setCurrency(String currency) {
        super.setCurrency(currency);
    }

    @Override
    @JsonProperty("company")
    public void setCompany(String company) {
        super.setCompany(company);
    }

    @Override
    @JsonProperty("description")
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    @JsonProperty("url")
    public void setUrl(String url) {
        super.setUrl(url);
    }

}
