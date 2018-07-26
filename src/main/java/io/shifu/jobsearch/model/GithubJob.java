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
public class GithubJob {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @Column(name = "site")
    private String site = "github";

    @JsonProperty("id")
    @Column(name = "site_id")
    private String siteId;

    @JsonProperty("created_at")
    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM d HH:mm:ss z yyyy")
    private Date date;

    @JsonProperty("title")
    @Column(name = "title")
    private String title;

    @JsonProperty("location")
    @Column(name = "location")
    private String location;

    @JsonIgnore
    @Column(name = "salary")
    private Integer salary;

    @JsonIgnore
    @Column(name = "currency")
    private String currency;

    @JsonProperty("company")
    @Column(name = "company")
    private String company;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;

    @JsonProperty("url")
    @Column(name = "url")
    private String url;

}
