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
        if (userEntity != null) {
            List<String> roles = userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            if (roles.contains(ROLE_ADMIN)) {
                return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
            } else if (roles.contains(ROLE_USER)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable(ID) Long id, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity != null) {
            List<String> roles = userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            Optional<UserEntity> user = userService.findById(id);
            if (roles.contains(ROLE_ADMIN)) {
                return user.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            } else if (roles.contains(ROLE_USER)) {
                if (userEntity.getId().equals(id)) {
                    return user.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                }
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity userEntityCreated, @AuthenticationPrincipal UserEntity userEntityLoggedIn) {
        if (userEntityLoggedIn != null) {
            List<String> roles = userEntityLoggedIn.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            userEntityCreated.setPassword(passwordEncoder.encode(userEntityCreated.getPassword()));
            if (roles.contains(ROLE_ADMIN)) {
                return new ResponseEntity<>(userService.save(userEntityCreated), HttpStatus.CREATED);
            } else if (roles.contains(ROLE_USER)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable(ID) Long id, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity !=null) {
            List<String> roles = userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            if (roles.contains(ROLE_ADMIN)) {
                userService.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else if (roles.contains(ROLE_USER)) {
                if (userEntity.getId().equals(id)) {
                    userService.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
