package com.tauhoa.train.services;

import com.tauhoa.train.dtos.response.TicketTypeCountResponseDTO;
import com.tauhoa.train.models.TicketType;
import com.tauhoa.train.repositories.TicketTypeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketTypeService implements ITicketTypeService {
    @Autowired
    private TicketTypeRepository ticketTypeRepository;


    @NonNull
    public List<TicketType> getAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }
    public List<TicketTypeCountResponseDTO> getTicketCountsGroupedByType() {
        return ticketTypeRepository.countTicketsByTicketType();
    }
}
