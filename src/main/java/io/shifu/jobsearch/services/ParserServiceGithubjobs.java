package io.shifu.jobsearch.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

import io.shifu.jobsearch.model.Job;
import io.shifu.jobsearch.repository.JobRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shifu.jobsearch.model.GithubJob;
import io.shifu.jobsearch.repository.GithubJobRepository;

@Service
@EnableScheduling
public class ParserServiceGithubjobs extends ParserService {

	private final GithubJobRepository githubJobRepository;

	public ParserServiceGithubjobs(JobRepository jobRepository, GithubJobRepository githubJobRepository) {
		super(jobRepository);
		this.githubJobRepository = githubJobRepository;
	}

	@Transactional
	public void saveList(List<GithubJob> jobs) {
		jobs.forEach(f -> {
			// проверяем существует ли уже вакансия, если да то обновляем старую
			Optional<Job> bySiteid = findBySiteid(f.getSite(), f.getSiteId());
			bySiteid.ifPresent(job -> f.setId(job.getId()));
		});
		githubJobRepository.saveAll(jobs);
	}

	@Override
	@Scheduled(cron = "0 15 1 * * ?")
	void parse() {
		int page = 0;
		while (true) {
			String query = String.format("https://jobs.github.com/positions.json?page=%s", page);
			List<GithubJob> jobs = new ArrayList<>();
			HttpsURLConnection connection = null;
			try {
				connection = (HttpsURLConnection) new URL(query).openConnection();
				connection.connect();
				if (HttpsURLConnection.HTTP_OK == connection.getResponseCode()) {
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					jobs = mapper.readValue(connection.getInputStream(), new TypeReference<List<GithubJob>>() {
					});
				} else {
					System.out.println(connection.getResponseMessage());
				}
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}

			if (jobs.isEmpty()) {
				break;
			}
			saveList(jobs);
			page++;
		}
	}
}