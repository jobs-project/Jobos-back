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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

@Service
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


        String url = "https://www.reed.co.uk/api/1.0/search?keywords=developer&location=london&distancefromlocation=15&resultsToTake=10";
        RestTemplate restTemplate = new RestTemplate();

        ReedJobResponse response = restTemplate.exchange
                (url, HttpMethod.GET, new HttpEntity<>(createHeaders(username, "")),
                        ReedJobResponse.class).getBody();
        response.getResult().forEach(System.out::println);

        List<ReedJob> list = response.getResult();

        for (ReedJob job : list) {
            job.setSite("reed.co.uk");
            job.setSiteId("reed_site_id");
            log.info(job.toString());
            reedJobRepository.save(job);

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
