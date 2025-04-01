package com.tauhoa.train.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO {
    @NotNull(message = "Thông tin người dùng không được để trống")
    private CustomerDTO customerDTO;
//    @JsonProperty("customer_id")
//    private int customerId;

    @NotNull(message = "Danh sách vé không được để trống")
    private List<TicketInformationDTO> ticketInformationDTO;
    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public List<TicketInformationDTO> getTicketInformationDTO() {
        return ticketInformationDTO;
    }

    public void setTicketInformationDTO(List<TicketInformationDTO> ticketInformationDTO) {
        this.ticketInformationDTO = ticketInformationDTO;
    }

}
