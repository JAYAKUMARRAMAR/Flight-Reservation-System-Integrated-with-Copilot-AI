package com.jaya.flightreserv.flightreservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.jaya.flightreserv.flightreservation.entities.Flight;
import com.jaya.flightreserv.flightreservation.entities.Passenger;
import com.jaya.flightreserv.flightreservation.entities.Reservation;
import com.jaya.flightreserv.flightreservation.repos.FlightRepository;
import com.jaya.flightreserv.flightreservation.repos.PassengerRepository;
import com.jaya.flightreserv.flightreservation.repos.ReservationRepository;

import tools.jackson.databind.ObjectMapper;

class ProjectDataContextServiceTest {
    @Test
    void includesAllProjectDatabaseRecordsInTheModelContext() throws Exception {
        FlightRepository flightRepository = org.mockito.Mockito.mock(FlightRepository.class);
        PassengerRepository passengerRepository = org.mockito.Mockito.mock(PassengerRepository.class);
        ReservationRepository reservationRepository = org.mockito.Mockito.mock(ReservationRepository.class);
        ObjectMapper objectMapper = org.mockito.Mockito.mock(ObjectMapper.class);

        Flight flight = new Flight();
        flight.setId(7L);
        flight.setFlightNumber("FR123");
        Passenger passenger = new Passenger();
        passenger.setId(9L);
        passenger.setFirstName("Alex");
        passenger.setMiddleName(null);
        Reservation reservation = new Reservation();
        reservation.setId(11L);
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        given(flightRepository.findAll()).willReturn(List.of(flight));
        given(passengerRepository.findAll()).willReturn(List.of(passenger));
        given(reservationRepository.findAll()).willReturn(List.of(reservation));
        given(objectMapper.writeValueAsString(any())).willReturn("{\"database\":\"context\"}");

        ProjectDataContextService service = new ProjectDataContextService(
                flightRepository,
                passengerRepository,
                reservationRepository,
                objectMapper);

        assertEquals("{\"database\":\"context\"}", service.getContext());

        ArgumentCaptor<Map<String, Object>> contextCaptor = ArgumentCaptor.forClass(Map.class);
        verify(objectMapper).writeValueAsString(contextCaptor.capture());
        Map<String, Object> context = contextCaptor.getValue();
        assertEquals(1, ((List<?>) context.get("flights")).size());
        assertEquals(1, ((List<?>) context.get("passengers")).size());
        assertEquals(1, ((List<?>) context.get("reservations")).size());
        Map<?, ?> passengerData = (Map<?, ?>) ((List<?>) context.get("passengers")).get(0);
        assertNull(passengerData.get("middleName"));
    }
}
