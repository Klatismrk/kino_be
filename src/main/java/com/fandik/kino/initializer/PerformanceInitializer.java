package com.fandik.kino.initializer;

import com.fandik.kino.entities.MovieEntity;
import com.fandik.kino.entities.PerformanceEntity;
import com.fandik.kino.repositories.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
@DependsOn("movieInitializer")
public class PerformanceInitializer implements CommandLineRunner {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Override
    public void run(String... args) {
        MovieEntity fandaVelikyFilm = new MovieEntity();
        MovieEntity narutoFilm = new MovieEntity();
        MovieEntity deathNoteFilm = new MovieEntity();

        fandaVelikyFilm.setId(1L);

        narutoFilm.setId(2L);

        deathNoteFilm.setId(3L);

        PerformanceEntity fandaVeliky = new PerformanceEntity();
        PerformanceEntity naruto = new PerformanceEntity();
        PerformanceEntity naruto2 = new PerformanceEntity();
        PerformanceEntity deathNote = new PerformanceEntity();

        fandaVeliky.setId(1L);
        fandaVeliky.setName("Velkolepá podívaná na Fandu velikého");
        fandaVeliky.setDate(Date.from(Instant.now().plus(Duration.ofDays(1))));
        fandaVeliky.setMovie(fandaVelikyFilm);

        naruto.setId(2L);
        naruto.setName("Příběh o ninjovi který vás vezme za srdce");
        naruto.setDate(Date.from(Instant.now().plus(Duration.ofDays(3)).plus(Duration.ofHours(2))));
        naruto.setMovie(narutoFilm);

        naruto2.setId(3L);
        naruto2.setName("Pro velký zájem budeme dávat znovu");
        naruto2.setDate(Date.from(Instant.now().plus(Duration.ofDays(7)).plus(Duration.ofMinutes(24))));
        naruto2.setMovie(narutoFilm);

        deathNote.setId(4L);
        deathNote.setName("Zajímavé anime");
        deathNote.setDate(Date.from(Instant.now().plus(Duration.ofDays(10)).plus(Duration.ofHours(5)).plus(Duration.ofMinutes(5))));
        deathNote.setMovie(deathNoteFilm);

        performanceRepository.saveAll(List.of(fandaVeliky, naruto, naruto2, deathNote));

    }
}
