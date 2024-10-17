package org.example.proyectotic1grupo1.models;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;
    public long venueId;
    public long screeningId;
    public long userId;
    public int seatRow;
    public int seatColumn;

    public Reservation() {}

    public Reservation(long screeningId, long venueId, long userId, int seatRow, int seatColumn) {
        this.screeningId = screeningId;
        this.venueId = venueId;
        this.userId = userId;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
    }



    public long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(long screeningId) {
        this.screeningId = screeningId;
    }

    public long getVenueId() {
        return venueId;
    }

    public void setVenueId(long venueId) {
        this.venueId = venueId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(int seatColumn) {
        this.seatColumn = seatColumn;
    }
}
