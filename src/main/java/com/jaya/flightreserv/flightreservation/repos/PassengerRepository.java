//passenger jpa repository that uses passenger model
package com.jaya.flightreserv.flightreservation.repos;
import com.jaya.flightreserv.flightreservation.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}