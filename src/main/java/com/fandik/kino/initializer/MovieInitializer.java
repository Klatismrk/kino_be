package com.fandik.kino.initializer;

import com.fandik.kino.entities.MovieEntity;
import com.fandik.kino.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieInitializer implements CommandLineRunner {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void run(String... args) {
        MovieEntity fandaVeliky = new MovieEntity();
        MovieEntity naruto = new MovieEntity();
        MovieEntity deathNote = new MovieEntity();

        fandaVeliky.setId(1L);
        fandaVeliky.setName("Fanda veliký");
        fandaVeliky.setDescription("Příběh o neohroženém fandovi který se nebojí vůbec ničeho.");

        naruto.setId(2L);
        naruto.setName("Naruto");
        naruto.setDescription("Naruto, chlapec s nezvyklými schopnostmi a devítiocasou liškou, se stal hrdinou populárního seriálu s 11 řadami, 3 filmy a NARUTO: SHIPPÛDEN.");

        deathNote.setId(3L);
        deathNote.setName("Death note");
        deathNote.setDescription("Japonský středoškolák dostane do rukou mystický zápisník, který zabije každého, jehož jméno do něj napíše.");

        movieRepository.saveAll(List.of(fandaVeliky, naruto, deathNote));
    }
}
