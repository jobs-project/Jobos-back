package io.shifu.jobsearch.repository;

import io.shifu.jobsearch.model.ReedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReedJobRepository extends JpaRepository<ReedJob, Long> {
}
