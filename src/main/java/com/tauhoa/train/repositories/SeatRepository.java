package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface SeatRepository extends JpaRepository<Seat, Integer>{

    @Query("SELECT s FROM Seat s " +
            "JOIN s.carriageList cl " +
            "JOIN cl.trip t " +
            "WHERE t.tripId = :tripId")
    List<Seat> findAllSeatsByTripId(@Param("tripId") int tripId);

    List<Seat> findByCarriageList_Trip_TripId(int tripId);

    List<Seat> getAllBySeatId(int seatId);
    Seat getSeatsBySeatId(int seatId);

}
