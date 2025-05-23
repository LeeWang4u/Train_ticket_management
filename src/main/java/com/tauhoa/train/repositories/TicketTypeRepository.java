package com.tauhoa.train.repositories;

import com.tauhoa.train.dtos.response.TicketTypeCountResponseDTO;
import com.tauhoa.train.models.Seat;
import com.tauhoa.train.models.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface TicketTypeRepository extends JpaRepository<TicketType, Integer>{
    @Override
    List<TicketType> findAll();

    @Query("SELECT new com.tauhoa.train.dtos.response.TicketTypeCountResponseDTO(tt.ticketTypeName, COUNT(t)) " +
            "FROM TicketType tt " +
            "LEFT JOIN Passenger p ON p.ticketType = tt " +
            "LEFT JOIN Ticket t ON t.passenger = p " +
            "GROUP BY tt.ticketTypeName ")
    List<TicketTypeCountResponseDTO> countTicketsByTicketType();
}
