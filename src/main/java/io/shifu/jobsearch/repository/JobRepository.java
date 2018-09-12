package io.shifu.jobsearch.repository;

import io.shifu.jobsearch.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(nativeQuery = true, value = "select * from job j where (?1 is null or j.salary >= ?1) and (?2 is null or j.location like %?2%) and (?3 is null or j.title like %?3%) and (?4 is null or j.description like %?4%)",
            countQuery = "select count(*) from job j where (?1 is null or j.salary >= ?1) and (?2 is null or j.location like %?2%) and (?3 is null or j.title like %?3%) and (?4 is null or j.description like %?4%)")
    Page<Job> findBySearchQuery(Integer salary, String location, String title, String description, Pageable pageable);

    Optional<Job> findBySiteAndSiteId(String site, String siteId);
}
