package com.tauhoa.train.repositories;

import com.tauhoa.train.dtos.response.DailySalesResponseDTO;
import com.tauhoa.train.dtos.response.MonthlySalesResponseDTO;
import com.tauhoa.train.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.tauhoa.train.dtos.response.TicketCountResponseDTO;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByCustomer(Customer customer);

    Ticket findByTicketId(int ticketId);

    @Query("SELECT t FROM Ticket t WHERE t.trip.tripId = :tripId AND t.ticketStatus IN ('Booked', 'Hold')")
    List<Ticket> findByTripIdAndStatusBookedOrHold(@Param("tripId") int tripId);

    Optional<Ticket> findBySeatAndTripAndDepartureStationAndArrivalStation(
            Seat seat,
            Trip trip,
            Station departureStation,
            Station arrivalStation
    );

    @Query("SELECT t FROM Ticket t WHERE t.trip.tripId = :tripId AND t.seat.seatId = :seatId ")
    Optional<Ticket> findByTripIdAndSeatId(@Param("tripId") int tripId, @Param("seatId") int seatId);

    List<Ticket> findBySeatSeatId(int seatId);

    List<Ticket> findByReservationCodeReservationCodeId(int reservationCodeId);

    @Query("SELECT t FROM Ticket t WHERE t.purchaseTime BETWEEN :startDate AND :endDate AND t.ticketStatus = 'Booked'")
    List<Ticket> findTicketsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT new com.tauhoa.train.dtos.response.TicketCountResponseDTO(t.departureStation.stationName, t.arrivalStation.stationName, COUNT(t)) " +
            "FROM Ticket t " +
            "WHERE t.ticketStatus = 'Booked' " +
            "GROUP BY t.departureStation.stationName, t.arrivalStation.stationName")
    List<TicketCountResponseDTO> findTicketCountGroupedByStations();

    @Query("""
    SELECT new com.tauhoa.train.dtos.response.DailySalesResponseDTO(
        DATE(t.purchaseTime),
        SUM(t.price)
    )
    FROM Ticket t
    WHERE t.purchaseTime BETWEEN :startDate AND :endDate
      AND t.ticketStatus = 'Booked'
    GROUP BY DATE(t.purchaseTime)
    ORDER BY DATE(t.purchaseTime)
""")
    List<DailySalesResponseDTO> getDailySalesByPurchaseTime(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


    @Query("""
    SELECT new com.tauhoa.train.dtos.response.MonthlySalesResponseDTO(
        YEAR(t.purchaseTime),
        MONTH(t.purchaseTime),
        SUM(t.price)
    )
    FROM Ticket t
    WHERE t.purchaseTime BETWEEN :startDate AND :endDate
      AND t.ticketStatus = 'Booked'
    GROUP BY YEAR(t.purchaseTime), MONTH(t.purchaseTime)
    ORDER BY YEAR(t.purchaseTime), MONTH(t.purchaseTime)
""")
    List<MonthlySalesResponseDTO> getMonthlySalesByPurchaseTime(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(t.totalPrice) FROM Ticket t")
    BigDecimal getTotalPriceSum();

}
