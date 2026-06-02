
package com.bharath.flightreservation.entities;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_number", nullable = false, unique = true)
    private String flightNumber;

    @Column(name = "operating_airlines", nullable = false)
    private String operatingAirlines;

    @Column(name = "departure_city", nullable = false)
    private String departureCity;

    @Column(name = "arrival_city", nullable = false)
    private String arrivalCity;

    @Column(name = "date_of_departure", nullable = false)
    private LocalDate dateOfDeparture;

    @Column(name = "estimated_departure_time", nullable = false)
    private LocalTime estimatedDepartureTime;

    // Constructors
    public Flight() {
    }

    public Flight(String flightNumber, String operatingAirlines, String departureCity,
                  String arrivalCity, LocalDate dateOfDeparture, LocalTime estimatedDepartureTime) {
        this.flightNumber = flightNumber;
        this.operatingAirlines = operatingAirlines;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.dateOfDeparture = dateOfDeparture;
        this.estimatedDepartureTime = estimatedDepartureTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOperatingAirlines() {
        return operatingAirlines;
    }

    public void setOperatingAirlines(String operatingAirlines) {
        this.operatingAirlines = operatingAirlines;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public LocalTime getEstimatedDepartureTime() {
        return estimatedDepartureTime;
    }

    public void setEstimatedDepartureTime(LocalTime estimatedDepartureTime) {
        this.estimatedDepartureTime = estimatedDepartureTime;
    }
}