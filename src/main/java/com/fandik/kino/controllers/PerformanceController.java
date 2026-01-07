package com.fandik.kino.controllers;

import com.fandik.kino.entities.PerformanceEntity;
import com.fandik.kino.entities.UserEntity;
import com.fandik.kino.services.PerformanceService;
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
@RequestMapping("/api/performance")
@CrossOrigin(origins = "http://localhost:3000")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @GetMapping
    public ResponseEntity<List<PerformanceEntity>> getAllPerformances() {
        return new ResponseEntity<>(performanceService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceEntity> getOneById(@PathVariable(ID) Long id) {
        Optional<PerformanceEntity> performances = performanceService.findById(id);
        return performances.map(performance -> new ResponseEntity<>(performance, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PerformanceEntity> save(@RequestBody PerformanceEntity performanceEntity, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity !=null) {
            List<String> roles = userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            if (roles.contains(ROLE_ADMIN)) {
                return new ResponseEntity<>(performanceService.save(performanceEntity), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(ID) Long id, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity !=null) {
            List<String> roles = userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            if (roles.contains(ROLE_ADMIN)) {
                performanceService.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
