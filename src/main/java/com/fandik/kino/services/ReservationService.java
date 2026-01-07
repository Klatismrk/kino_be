package com.fandik.kino.services;

import com.fandik.kino.entities.ReservationEntity;
import com.fandik.kino.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationEntity> findAll() {
        return StreamSupport.stream(reservationRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<ReservationEntity> findById(Long id) {
        return reservationRepository.findById(id);
    }

    public ReservationEntity save(ReservationEntity reservationEntity) {
        return reservationRepository.save(reservationEntity);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationEntity> findByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public Optional<ReservationEntity> findByUserIdAndId(Long userId, Long id) {
        return reservationRepository.findByUserIdAndId(userId, id);
    }
}
