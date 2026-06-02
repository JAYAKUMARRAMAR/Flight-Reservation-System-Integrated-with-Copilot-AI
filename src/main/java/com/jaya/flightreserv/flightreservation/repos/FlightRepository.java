package com.jaya.flightreserv.flightreservation.repos;

import com.jaya.flightreserv.flightreservation.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureCity(String departureCity);
    
    List<Flight> findByArrivalCity(String arrivalCity);
    
    List<Flight> findByDepartureCityAndArrivalCity(String departureCity, String arrivalCity);
    
    List<Flight> findByDateOfDeparture(LocalDate dateOfDeparture);
    
    List<Flight> findByOperatingAirline(String operatingAirline);
    
    List<Flight> findByFlightNumber(String flightNumber);
}