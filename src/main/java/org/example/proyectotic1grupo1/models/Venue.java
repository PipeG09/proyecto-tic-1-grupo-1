package org.example.proyectotic1grupo1.models;

import jakarta.persistence.*;

@Entity
@Table(name="venue")
public class Venue {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "neighborhood")
    private String neighborhood;

    public Venue() {
    }

    public Venue(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public long getId() {
        return id;
    }

    public String getNeighborhood() {
        return neighborhood;
    }
}
