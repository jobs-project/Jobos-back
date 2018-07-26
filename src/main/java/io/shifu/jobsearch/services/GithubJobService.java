package io.shifu.jobsearch.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import io.shifu.jobsearch.model.GithubJob;

public interface GithubJobService {
	
	@Transactional
	void saveAll();
	
	List<GithubJob> download(int page);

}
