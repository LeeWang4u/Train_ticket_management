package com.tauhoa.train.dtos;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    @NotNull(message = "Thông tin người dùng không được để trống")
    private CustomerDTO customerDTO;

    @NotNull(message = "Danh sách vé không được để trống")
    private List<TicketInformationDTO> ticketInformationDTO;
}
