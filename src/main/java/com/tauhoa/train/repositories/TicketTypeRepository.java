package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Seat;
import com.tauhoa.train.models.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface TicketTypeRepository extends JpaRepository<TicketType, Integer>{
}
