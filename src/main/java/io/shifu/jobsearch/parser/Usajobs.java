package io.shifu.jobsearch.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.shifu.jobsearch.model.Job;
import io.shifu.jobsearch.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class Usajobs {
    private final JobService jobService;

    @Autowired
    public Usajobs(JobService jobService) {
        this.jobService = jobService;
    }

    @Scheduled(fixedDelay = 86400000) // 24 часа
    public void parse() throws IOException {
        JsonFactory factory = new JsonFactory();

        Job job = new Job();
        int i = 0;
        List<Job> jobList = new ArrayList<>(10);
        while (true) {
            URL url = new URL("https://jobs.search.gov/jobs/search.json?from=" + i);
            JsonParser parser = factory.createParser(url);
            while (!parser.isClosed()) {
                JsonToken jsonToken = parser.nextToken();
                if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                    String fieldName = parser.getCurrentName();
                    jsonToken = parser.nextToken();
                    // собираю объект руками (в образовательных целях) перевести потом на pojo для jackson
                    if ("id".equals(fieldName)) {
                        job = new Job();
                        job.setSite("usajobs");
                        job.setSiteId(parser.getValueAsString());
                    }
                    if ("position_title".equals(fieldName)) {
                        job.setTitle(parser.getValueAsString());
                    }
                    if ("organization_name".equals(fieldName)) {
                        job.setCompany(parser.getValueAsString());
                    }
                    if ("minimum".equals(fieldName)) {
                        job.setSalary(parser.getValueAsInt());
                        if (job.getSalary() < 500) {
                            job.setCurrency("usd/hour");
                        } else {
                            job.setCurrency("ush/year");
                        }
                    }
                    if ("start_date".equals(fieldName)) {
                        try {
                            job.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(parser.getValueAsString()));
                        } catch (ParseException e) {
                            // если нет даты - указываем свежую дату
                            job.setDate(new Date());
                        }
                    }
                    if ("locations".equals(fieldName)) {
                        parser.nextValue();
                        job.setLocation(parser.getText());
                    }
                    if ("url".equals(fieldName)) {
                        // в вакансиях usajobs нет описания, доступно на сайте
                        job.setDescription("Описание доступно по ссылке: " + parser.getValueAsString());
                        job.setUrl(parser.getValueAsString());
                        // ищем существует ли такая вакансия уже
                        Optional<Job> bySiteid = jobService.findBySiteid(job.getSite(), job.getSiteId());
                        if (bySiteid.isPresent()) {
                            //если вакансия существует - обновляем
                            job.setId(bySiteid.get().getId());
                        }
                        jobList.add(job);
                    }
                }
            }
            jobService.saveList(jobList);
            if (jobList.size() < 10) {
                System.out.println("end parsing, exit");
                break;
            }
            jobList.clear();
            i = i+10;
        }
    }
}
