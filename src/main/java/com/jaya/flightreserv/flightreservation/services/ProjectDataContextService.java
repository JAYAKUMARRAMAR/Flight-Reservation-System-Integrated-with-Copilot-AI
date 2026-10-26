package com.jaya.flightreserv.flightreservation.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jaya.flightreserv.flightreservation.entities.Flight;
import com.jaya.flightreserv.flightreservation.entities.Passenger;
import com.jaya.flightreserv.flightreservation.entities.Reservation;
import com.jaya.flightreserv.flightreservation.repos.FlightRepository;
import com.jaya.flightreserv.flightreservation.repos.PassengerRepository;
import com.jaya.flightreserv.flightreservation.repos.ReservationRepository;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class ProjectDataContextService {
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;
    private final ObjectMapper objectMapper;

    public ProjectDataContextService(
            FlightRepository flightRepository,
            PassengerRepository passengerRepository,
            ReservationRepository reservationRepository,
            ObjectMapper objectMapper) {
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.reservationRepository = reservationRepository;
        this.objectMapper = objectMapper;
    }

    public String getContext() {
        List<Map<String, Object>> flights = flightRepository.findAll().stream()
                .map(this::flightData)
                .toList();
        List<Map<String, Object>> passengers = passengerRepository.findAll().stream()
                .map(this::passengerData)
                .toList();
        List<Map<String, Object>> reservations = reservationRepository.findAll().stream()
                .map(this::reservationData)
                .toList();

        try {
            return objectMapper.writeValueAsString(Map.of(
                    "flights", flights,
                    "passengers", passengers,
                    "reservations", reservations));
        } catch (JacksonException exception) {
            throw new IllegalStateException("Could not serialize project database data", exception);
        }
    }

    private Map<String, Object> flightData(Flight flight) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", flight.getId());
        data.put("flightNumber", flight.getFlightNumber());
        data.put("operatingAirlines", flight.getOperatingAirlines());
        data.put("departureCity", flight.getDepartureCity());
        data.put("arrivalCity", flight.getArrivalCity());
        data.put("dateOfDeparture", flight.getDateOfDeparture());
        data.put("estimatedDepartureTime", flight.getEstimatedDepartureTime());
        return data;
    }

    private Map<String, Object> passengerData(Passenger passenger) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", passenger.getId());
        data.put("firstName", passenger.getFirstName());
        data.put("lastName", passenger.getLastName());
        data.put("middleName", passenger.getMiddleName());
        data.put("email", passenger.getEmail());
        data.put("phone", passenger.getPhone());
        return data;
    }

    private Map<String, Object> reservationData(Reservation reservation) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", reservation.getId());
        data.put("checkedIn", reservation.isCheckedIn());
        data.put("numberOfBags", reservation.getNumberOfBags());
        data.put("passengerId", reservation.getPassenger() == null ? null : reservation.getPassenger().getId());
        data.put("flightId", reservation.getFlight() == null ? null : reservation.getFlight().getId());
        return data;
    }
}
