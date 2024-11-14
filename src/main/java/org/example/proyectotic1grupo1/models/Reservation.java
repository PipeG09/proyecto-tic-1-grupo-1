package org.example.proyectotic1grupo1.models;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name="screeningId")
    private long screeningId;

    @Column(name="userId")
    private long userId;

    @Column(name = "seatRow")
    private int seatRow;

    @Column(name = "seatColumn")
    private int seatColumn;

    @ManyToOne
    @JoinColumn(name = "screeningId", insertable = false, updatable = false)
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    public Reservation() {
    }

    public Reservation(long screeningId, long userId, int seatRow, int seatColumn) {
        this.screeningId = screeningId;
        this.userId = userId;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(long screeningId) {
        this.screeningId = screeningId;
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

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }
}