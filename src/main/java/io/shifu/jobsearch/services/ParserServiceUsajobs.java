package io.shifu.jobsearch.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shifu.jobsearch.model.Job;
import io.shifu.jobsearch.model.Usajob;
import io.shifu.jobsearch.repository.JobRepository;
import io.shifu.jobsearch.repository.UsaJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class ParserServiceUsajobs extends ParserService {

    private final UsaJobRepository usaJobRepository;

    @Autowired
    public ParserServiceUsajobs(JobRepository jobRepository, UsaJobRepository usaJobRepository) {
        super(jobRepository);
        this.usaJobRepository = usaJobRepository;
    }

    @Transactional
    public void saveList(List<Usajob> jobs) {
        List<Usajob> saveList = new ArrayList<>();
        jobs.forEach(f -> {
            // проверяем существует ли уже вакансия, если да то не сохраняем
            Optional<Job> bySiteid = findBySiteid(f.getSite(), f.getSiteId());
            if (!bySiteid.isPresent()) saveList.add(f);
        });
        if (!saveList.isEmpty()) usaJobRepository.saveAll(saveList);
    }

    @Override
    @Scheduled(cron = "0 1 1 * * ?")
    public void parse() {
        int page = 0;
        while(true) {
            String query = String.format("https://jobs.search.gov/jobs/search.json?from=%s", page);
            List<Usajob> jobs = new ArrayList<>();
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) new URL(query).openConnection();
                connection.connect();
                if(HttpsURLConnection.HTTP_OK == connection.getResponseCode()) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    jobs = mapper.readValue(connection.getInputStream(), new TypeReference<List<Usajob>>(){});
                } else {
                    System.out.println(connection.getResponseMessage());
                }
            } catch(Throwable e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
            }

            if(jobs.isEmpty()) {
                break;
            }
            saveList(jobs);
            page = page+10;
        }
    }
}
