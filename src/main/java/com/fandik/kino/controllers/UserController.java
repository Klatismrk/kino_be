package com.fandik.kino.controllers;

import com.fandik.kino.entities.UserEntity;
import com.fandik.kino.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.fandik.kino.entities.UserEntity.Role.ROLE_ADMIN;
import static com.fandik.kino.entities.UserEntity.Role.ROLE_USER;
import static com.fandik.kino.utils.Constants.ID;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping
  public ResponseEntity<List<UserEntity>> getAllUsers(@AuthenticationPrincipal UserEntity userEntity) {
    if (userEntity == null)
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    return switch (userEntity.getRole()) {
      case ROLE_ADMIN -> new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
      case ROLE_USER -> new ResponseEntity<>(HttpStatus.FORBIDDEN);
    };
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserEntity> getById(@PathVariable(ID) Long id, @AuthenticationPrincipal UserEntity userEntity) {
    if (userEntity == null)
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    UserEntity user = userService.findById(id)
        .orElse(null);
    if (user == null)
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return switch (userEntity.getRole()) {
      case ROLE_ADMIN -> new ResponseEntity<>(user, HttpStatus.OK);
      case ROLE_USER -> user.getId().equals(id) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    };
  }

  @PostMapping
  public ResponseEntity<UserEntity> save(@RequestBody UserEntity userEntityCreated,
                                         @AuthenticationPrincipal UserEntity userEntityLoggedIn) {
    if (userEntityLoggedIn == null)
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    return switch (userEntityLoggedIn.getRole()) {
      case ROLE_ADMIN -> {
        userEntityCreated.setPassword(passwordEncoder.encode(userEntityCreated.getPassword()));
        yield new ResponseEntity<>(userService.save(userEntityCreated), HttpStatus.CREATED);
      }
      case ROLE_USER -> new ResponseEntity<>(HttpStatus.FORBIDDEN);
    };
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteUser(@PathVariable(ID) Long id, @AuthenticationPrincipal UserEntity userEntity) {
    if (userEntity == null || userEntity.getRole() != ROLE_ADMIN || !userEntity.getId().equals(id))
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    userService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
