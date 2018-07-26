package io.shifu.jobsearch.repository;

import io.shifu.jobsearch.model.GithubJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubJobRepository extends JpaRepository<GithubJob, Long> {
}