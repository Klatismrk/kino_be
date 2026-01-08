package com.fandik.kino.controllers;

import com.fandik.kino.entities.ReservationEntity;
import com.fandik.kino.entities.UserEntity;
import com.fandik.kino.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.fandik.kino.entities.UserEntity.Role.ROLE_ADMIN;
import static com.fandik.kino.entities.UserEntity.Role.ROLE_USER;
import static com.fandik.kino.utils.Constants.ID;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {

  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  public ResponseEntity<List<ReservationEntity>> getAllReservations(@AuthenticationPrincipal UserEntity userEntity) {
    if (userEntity == null)
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    return switch (userEntity.getRole()) {
      case ROLE_ADMIN -> new ResponseEntity<>(reservationService.findAll(), HttpStatus.OK);
      case ROLE_USER -> new ResponseEntity<>(reservationService.findByUserId(userEntity.getId()), HttpStatus.OK);
    };
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationEntity> getOneById(@PathVariable(ID) Long id,
                                                      @AuthenticationPrincipal UserEntity userEntity) {
    if (userEntity == null)
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    return switch (userEntity.getRole()) {
      case ROLE_ADMIN -> reservationService.findById(id)
          .map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
          .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
      case ROLE_USER -> reservationService.findByUserIdAndId(userEntity.getId(), id)
          .map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
          .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    };
  }

  @PostMapping
  public ResponseEntity<ReservationEntity> save(@RequestBody ReservationEntity reservationEntity,
                                                @AuthenticationPrincipal UserEntity userEntity) {
    if (userEntity == null)
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    if (userEntity.getRole() != ROLE_ADMIN)
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    return new ResponseEntity<>(reservationService.save(reservationEntity), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable(ID) Long id, @AuthenticationPrincipal UserEntity userEntity) {
    if (userEntity == null)
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    return switch (userEntity.getRole()) {
      case ROLE_ADMIN -> {
        reservationService.deleteById(id);
        yield  new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      case ROLE_USER -> {
        if (reservationService.findByUserIdAndId(userEntity.getId(), id).isPresent()) {
          reservationService.deleteById(id);
          yield  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        yield  new ResponseEntity<>(HttpStatus.FORBIDDEN);
      }
    };
  }
}
