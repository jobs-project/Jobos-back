package io.shifu.jobsearch.repository;

import io.shifu.jobsearch.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(nativeQuery = true, value = "select * from job j where (?1 is null or j.salary >= ?1) and (?2 is null or j.location = ?2 )")
    List<Job> findBySearchQuery(Integer salary, String location);
}
