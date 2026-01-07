package com.fandik.kino.repositories;

import com.fandik.kino.entities.ReservationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByUserId(Long userId);

    Optional<ReservationEntity> findByUserIdAndId(Long userId, Long id);
}
