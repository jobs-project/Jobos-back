package io.shifu.jobsearch.services;

import io.shifu.jobsearch.model.ReedJob;
import io.shifu.jobsearch.model.ReedJobResponse;
import io.shifu.jobsearch.repository.ReedJobRepository;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
@EnableScheduling
public class ParserServiceReedJobs {

    @Autowired
    ReedJobRepository reedJobRepository;

    private static final Logger log = LoggerFactory.getLogger(ParserServiceReedJobs.class);


    @Transactional
    public void parse() {

        String username = "72689e9c-1e0a-48b1-b06f-9548961db2ee";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);



        for (int i = 0; i < 3000; i += 100) {


            String url = "https://www.reed.co.uk/api/1.0/search?resultsToSkip=" + i + "&resultsToTake=100";
            RestTemplate restTemplate = new RestTemplate();

            ReedJobResponse response = restTemplate.exchange
                    (url, HttpMethod.GET, new HttpEntity<>(createHeaders(username, "")),
                            ReedJobResponse.class).getBody();

            List<ReedJob> list = response.getResult();

            for (ReedJob job : list) {
                job.setSiteId("reed_site_id");
                log.info(job.toString());
                reedJobRepository.save(job);

            }

        }
    }

    @Transactional
    @Scheduled(cron = "0 10 3 * * ?")
    public void parseWithUpdate() {
        String username = "72689e9c-1e0a-48b1-b06f-9548961db2ee";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (int i = 270000; i < 278000; i += 100) {
            String url = "https://www.reed.co.uk/api/1.0/search?resultsToSkip=" + i + "&resultsToTake=100";
            RestTemplate restTemplate = new RestTemplate();

            ReedJobResponse response = restTemplate.exchange
                    (url, HttpMethod.GET, new HttpEntity<>(createHeaders(username, "")),
                            ReedJobResponse.class).getBody();

            List<ReedJob> list = response.getResult();

            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

            Calendar c2 = Calendar.getInstance();

            for (ReedJob job : list) {
                c2.setTime(job.getDate()); // vacancy date

                if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {

                    job.setSiteId("reed_site_id");
                    reedJobRepository.save(job);
                    log.info(job.toString());
                }



            }
        }

    }

    public static HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("UTF-8")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};

    }
}
