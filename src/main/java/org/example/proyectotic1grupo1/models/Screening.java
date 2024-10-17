package org.example.proyectotic1grupo1.models;

import jakarta.persistence.*;

@Entity
@Table(name = "screening")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long screeningId;

}
