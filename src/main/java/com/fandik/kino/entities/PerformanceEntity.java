package com.fandik.kino.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "performance")
@Table(name = "PERFORMANCES")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PerformanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private MovieEntity movie;

    @NotNull
    private String name;

    @NotNull
    private Date date;

}
