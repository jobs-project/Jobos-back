package io.shifu.jobsearch.services;

import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shifu.jobsearch.model.GithubJob;
import io.shifu.jobsearch.repository.GithubJobRepository;

@Service
public class GithubJobServiceImpl implements GithubJobService {
	
	@Autowired
	private GithubJobRepository githubJobRepository;
	
	@Transactional
	@Override
	public void saveAll() {
		
		int page = 0;
		List<GithubJob> jobs = null;
		while(true) {
			jobs = download(page);
			if(jobs.isEmpty() || jobs == null) {
				return;
			}
			githubJobRepository.saveAll(jobs);
			page++;
		}		
	}
	
	@Override
	public List<GithubJob> download(int page){	
			
		String query = String.format("https://jobs.github.com/positions.json?page=%s", page);
		HttpsURLConnection connection = null;
		List<GithubJob> jobs = null;
		
		try {		
						
			connection = (HttpsURLConnection) new URL(query).openConnection();				
			connection.connect();			
			
			if(HttpsURLConnection.HTTP_OK == connection.getResponseCode()) {
			
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);				
				
				jobs = mapper.readValue(connection.getInputStream(), new TypeReference<List<GithubJob>>(){});
							
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
		return jobs;
	}
}
