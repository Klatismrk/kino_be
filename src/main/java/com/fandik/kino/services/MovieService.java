package com.fandik.kino.services;

import com.fandik.kino.entities.MovieEntity;
import com.fandik.kino.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieEntity> findAll() {
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<MovieEntity> findById(Long id) {
        return movieRepository.findById(id);
    }

    public MovieEntity save(MovieEntity movieEntity) {
        return movieRepository.save(movieEntity);
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }
}
