package com.tauhoa.train.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationCodeRequestDTO {
    @NotNull(message = "Thông tin người dùng không được để trống")
    private CustomerDTO customerDTO;

    @NotNull(message = "Danh sách vé không được để trống")
    private List<TicketRequestDTO> ticketRequestDTO;


    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public List<TicketRequestDTO> getTicketInformationDTO() {
        return ticketRequestDTO;
    }

    public void setTicketInformationDTO(List<TicketRequestDTO> ticketRequestDTO) {
        this.ticketRequestDTO = ticketRequestDTO;
    }
}
