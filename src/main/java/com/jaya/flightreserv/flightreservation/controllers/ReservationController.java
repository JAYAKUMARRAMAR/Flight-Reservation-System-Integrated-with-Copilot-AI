package com.jaya.flightreserv.flightreservation.controllers;

import com.jaya.flightreserv.flightreservation.dtos.ReservationRequest;
import com.jaya.flightreserv.flightreservation.entities.Flight;
import com.jaya.flightreserv.flightreservation.entities.Reservation;
import com.jaya.flightreserv.flightreservation.repos.FlightRepository;
import com.jaya.flightreserv.flightreservation.services.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ReservationController {

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/showCompleteReservation")
    public String showCompleteReservation(@RequestParam("flightId") Long flightId, Model model) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        if (flight.isEmpty()) {
            return "redirect:/findFlights";
        }
        model.addAttribute("flight", flight.get());
        return "completeReservation";
    }

    @PostMapping("/completeReservation")
    public String completeReservation(ReservationRequest request, Model model)
    {
        Reservation reservation = reservationService.bookFlight(request);
        model.addAttribute("msg","Reservation created successfully with a reservation id : "+ reservation.getId());
        return "reservationConfirmation";
    } 
}
