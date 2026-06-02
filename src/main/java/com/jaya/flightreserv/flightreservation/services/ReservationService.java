package com.jaya.flightreserv.flightreservation.services;

import com.jaya.flightreserv.flightreservation.dtos.ReservationRequest;
import com.jaya.flightreserv.flightreservation.entities.Reservation;

public interface ReservationService {
    public Reservation bookFlight(ReservationRequest request);
}
