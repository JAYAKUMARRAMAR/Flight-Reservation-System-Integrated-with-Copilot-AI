//reservation jpa repository that uses reservation model
package com.jaya.flightreserv.flightreservation.repos;

import com.jaya.flightreserv.flightreservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}