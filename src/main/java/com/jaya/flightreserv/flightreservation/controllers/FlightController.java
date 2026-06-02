//flight controller class with a displayFindFlights method that returns the thymeleaf template
package com.jaya.flightreserv.flightreservation.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {
    @GetMapping("/findFlights")
    public String displayFindFlights(Model model) {
        return "findFlights";
    }
}