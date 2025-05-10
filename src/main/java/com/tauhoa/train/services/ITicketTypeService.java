package com.tauhoa.train.services;

import com.tauhoa.train.dtos.response.TicketTypeCountResponseDTO;
import com.tauhoa.train.models.TicketType;

import java.util.List;

public interface ITicketTypeService {
    List<TicketType> getAllTicketTypes();
    public List<TicketTypeCountResponseDTO> getTicketCountsGroupedByType();
}
