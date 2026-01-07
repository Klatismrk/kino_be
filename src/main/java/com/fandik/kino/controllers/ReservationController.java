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
        if (userEntity != null) {
            List<String> roles = userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            if (roles.contains(ROLE_ADMIN)) {
                return new ResponseEntity<>(reservationService.findAll(), HttpStatus.OK);
            } else if (roles.contains(ROLE_USER)) {
                Long userId = userEntity.getId();
                return new ResponseEntity<>(reservationService.findByUserId(userId), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationEntity> getOneById(@PathVariable(ID) Long id, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity != null) {
            List<String> roles = userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            if (roles.contains(ROLE_ADMIN)) {
                Optional<ReservationEntity> reservations = reservationService.findById(id);
                return reservations.map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            } else if (roles.contains(ROLE_USER)) {
                Long userId = userEntity.getId();
                Optional<ReservationEntity> reservation = reservationService.findByUserIdAndId(userId, id);
                return reservation.map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.FORBIDDEN));
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<ReservationEntity> save(@RequestBody ReservationEntity reservationEntity, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity != null) {
            List<String> roles = userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            if (roles.contains(ROLE_ADMIN)) {
                return new ResponseEntity<>(reservationService.save(reservationEntity), HttpStatus.CREATED);
            } else if (roles.contains(ROLE_USER)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(ID) Long id, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity != null) {
            List<String> roles = userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            if (roles.contains(ROLE_ADMIN)) {
                reservationService.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else if (roles.contains(ROLE_USER)) {
                Optional<ReservationEntity> reservation = reservationService.findByUserIdAndId(
                    userEntity.getId(), id);
                if (reservation.isPresent()) {
                    reservationService.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
