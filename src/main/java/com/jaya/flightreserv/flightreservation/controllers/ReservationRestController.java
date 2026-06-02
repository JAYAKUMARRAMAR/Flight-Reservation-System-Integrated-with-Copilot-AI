package com.jaya.flightreserv.flightreservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jaya.flightreserv.flightreservation.dtos.ReservationUpdateRequest;
import com.jaya.flightreserv.flightreservation.entities.Reservation;
import com.jaya.flightreserv.flightreservation.repos.ReservationRepository;

@RestController
@CrossOrigin
public class ReservationRestController {
   
    @Autowired
    ReservationRepository reservationRepository;
    
    @GetMapping("/reservations/{id}")
    public Reservation findReservation(@PathVariable("id") Long id) {
        return reservationRepository.findById(id).get();
    }

    @PostMapping("/reservations")
    public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
        Reservation reservation = reservationRepository.findById(request.getId()).get();
        reservation.setCheckedIn(request.getCheckedIn());
        reservation.setNumberOfBags(request.getNumberOfBags());
        return reservationRepository.save(reservation);
    }
}