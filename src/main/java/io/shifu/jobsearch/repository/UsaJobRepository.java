package io.shifu.jobsearch.repository;

import io.shifu.jobsearch.model.Usajob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsaJobRepository extends JpaRepository<Usajob, Long> {
}
