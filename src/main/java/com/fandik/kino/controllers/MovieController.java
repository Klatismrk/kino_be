package com.fandik.kino.controllers;

import com.fandik.kino.entities.MovieEntity;
import com.fandik.kino.entities.UserEntity;
import com.fandik.kino.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.fandik.kino.entities.UserEntity.Role.ROLE_ADMIN;
import static com.fandik.kino.utils.Constants.ID;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

  private final MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping
  public ResponseEntity<List<MovieEntity>> getAllMovies() {
    return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovieEntity> getMovieById(@PathVariable(ID) Long id) {
    return movieService.findById(id)
        .map(film -> new ResponseEntity<>(film, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<MovieEntity> save(@RequestBody MovieEntity movieEntity,
                                          @AuthenticationPrincipal UserEntity userEntity) {
    if (userEntity == null)
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    if (userEntity.getRole() != ROLE_ADMIN)
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    return new ResponseEntity<>(movieService.save(movieEntity), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteMovie(@PathVariable(ID) Long id, @AuthenticationPrincipal UserEntity userEntity) {
    if (userEntity == null)
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    if (userEntity.getRole() != ROLE_ADMIN)
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    movieService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
