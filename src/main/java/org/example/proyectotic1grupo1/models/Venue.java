package org.example.proyectotic1grupo1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="venue")
public class Venue {
    @Id
    @GeneratedValue
    private long id;
}
