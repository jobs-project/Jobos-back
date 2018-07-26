package io.shifu.jobsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "job")
public class Usajob extends AbstractJob {
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
        super.setSite("usajobs");
    }

    @Override
    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setDate(Date date) {
        super.setDate(date);
    }

    @Override
    @JsonProperty("position_title")
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @JsonProperty("locations")
    public void setLocation(List<String> location) {
        super.setLocation(location.get(0));
    }

    @Override
    @JsonProperty("minimum")
    public void setSalary(Integer salary) {
        super.setSalary(salary);
        if (salary < 500) {
            super.setCurrency("usd/hour");
        } else {
            super.setCurrency("usd/year");
        }
    }

    @Override
    @JsonIgnore
    public void setCurrency(String currency) {
        super.setCurrency(currency);
    }

    @Override
    @JsonProperty("organization_name")
    public void setCompany(String company) {
        super.setCompany(company);
    }

    @Override
    @JsonIgnore
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    @JsonProperty("url")
    public void setUrl(String url) {
        super.setUrl(url);
        super.setDescription("Описание доступно по ссылке: " + url);
    }
}
