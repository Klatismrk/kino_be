package com.fandik.kino.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "reservation")
@Table(name = "RESERVATIONS")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private UserEntity user;

    @NotNull
    @ManyToOne
    private PerformanceEntity performance;

    @NotNull
    private int seat;

}
