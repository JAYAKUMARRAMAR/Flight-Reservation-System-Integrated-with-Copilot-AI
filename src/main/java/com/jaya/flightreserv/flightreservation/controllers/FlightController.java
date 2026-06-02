package com.jaya.flightreserv.flightreservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jaya.flightreserv.flightreservation.entities.Flight;
import com.jaya.flightreserv.flightreservation.repos.FlightRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
public class FlightController {
    
    @Autowired
    private FlightRepository flightRepository;
    
    @PostMapping("/findFlights")
    public String displayFindFlights(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("departureDate") @DateTimeFormat(pattern = "MM-dd-yyyy") LocalDate departureDate,
            Model model) {
        
        List<Flight> flights = flightRepository.findFlights(from, to, departureDate);
        model.addAttribute("flights", flights);
        
        return "displayFlights";
    }

    
}