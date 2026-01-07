package com.fandik.kino.repositories;

import com.fandik.kino.entities.PerformanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository extends CrudRepository<PerformanceEntity, Long> {
}
