package com.tauhoa.train.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.tauhoa.train.models.*;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    @NotNull(message = "Thông tin người dùng không được để trống")
    private UserDTO userDTO;

    @NotNull(message = "Danh sách vé không được để trống")
    private List<TicketInformationDTO> ticketInformationDTO;
}
