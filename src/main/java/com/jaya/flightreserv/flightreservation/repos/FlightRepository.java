package com.jaya.flightreserv.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jaya.flightreserv.flightreservation.entities.Flight;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> 
{
	@Query("SELECT f FROM Flight f WHERE f.departureCity = :from AND f.arrivalCity = :to AND f.dateOfDeparture = :departureDate")
	List<Flight> findFlights(String from, String to, LocalDate departureDate);
}