package com.fandik.kino.services;

import com.fandik.kino.entities.PerformanceEntity;
import com.fandik.kino.repositories.PerformanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    public PerformanceService(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    public List<PerformanceEntity> findAll() {
            return StreamSupport.stream(performanceRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<PerformanceEntity> findById(Long id) {
        return performanceRepository.findById(id);
    }

    public PerformanceEntity save(PerformanceEntity performanceEntity) {
        return performanceRepository.save(performanceEntity);
    }

    public void deleteById(Long id) {
        performanceRepository.deleteById(id);
    }
}
