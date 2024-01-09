package com.fandik.kino.controller;

import com.fandik.kino.entity.FilmEntity;
import com.fandik.kino.service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/film")
@CrossOrigin(origins = "http://localhost:3000")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<FilmEntity>> getAllFilms() {
        return new ResponseEntity<>(filmService.findAll().stream().collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmEntity> getFilmById(@PathVariable("id") Long id) {
        Optional<FilmEntity> findFilm = filmService.findById(id);
        return findFilm.map(film -> new ResponseEntity<>(film, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<FilmEntity> save(@RequestBody FilmEntity filmEntity) {
        return new ResponseEntity<>(filmService.save(filmEntity), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFilm(@PathVariable("id") Long id) {
        filmService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
