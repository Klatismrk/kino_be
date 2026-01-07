package com.fandik.kino.initializer;

import com.fandik.kino.entities.PerformanceEntity;
import com.fandik.kino.entities.ReservationEntity;
import com.fandik.kino.entities.UserEntity;
import com.fandik.kino.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DependsOn("performanceInitializer")
public class ReservationInitializer implements CommandLineRunner {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void run(String... args) {

        UserEntity fanda = new UserEntity();
        UserEntity lojza = new UserEntity();

        fanda.setId(2L);
        lojza.setId(3L);

        PerformanceEntity fandaVeliky = new PerformanceEntity();
        PerformanceEntity naruto = new PerformanceEntity();
        PerformanceEntity naruto2 = new PerformanceEntity();
        PerformanceEntity deathNote = new PerformanceEntity();

        fandaVeliky.setId(1L);
        naruto.setId(2L);
        naruto2.setId(3L);
        deathNote.setId(4L);

        ReservationEntity fandaFandu = new ReservationEntity();
        ReservationEntity fandaNaruta = new ReservationEntity();
        ReservationEntity lojzaNaruta2 = new ReservationEntity();
        ReservationEntity fandaDeathNote = new ReservationEntity();
        ReservationEntity lojzaDeathNote = new ReservationEntity();

        fandaFandu.setId(1L);
        fandaFandu.setSeat(5);
        fandaFandu.setPerformance(fandaVeliky);
        fandaFandu.setUser(fanda);

        fandaNaruta.setId(2L);
        fandaNaruta.setSeat(12);
        fandaNaruta.setPerformance(naruto);
        fandaNaruta.setUser(fanda);

        lojzaNaruta2.setId(3L);
        lojzaNaruta2.setSeat(1);
        lojzaNaruta2.setPerformance(naruto2);
        lojzaNaruta2.setUser(lojza);

        fandaDeathNote.setId(4L);
        fandaDeathNote.setSeat(10);
        fandaDeathNote.setPerformance(deathNote);
        fandaDeathNote.setUser(fanda);

        lojzaDeathNote.setId(5L);
        lojzaDeathNote.setSeat(9);
        lojzaDeathNote.setPerformance(deathNote);
        lojzaDeathNote.setUser(lojza);

        reservationRepository.saveAll(List.of(fandaFandu, fandaNaruta, lojzaNaruta2, fandaDeathNote, lojzaDeathNote));

    }
}
