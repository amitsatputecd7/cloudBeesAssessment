package com.model;


public class Seat {
    private String section;
    private int seatNumber;

    public Seat(String section, int seatNumber) {
        this.section = section;
        this.seatNumber = seatNumber;
    }

    public String getSection() {
        return section;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}
