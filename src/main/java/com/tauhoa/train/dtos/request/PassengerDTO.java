package com.tauhoa.train.dtos.request;

import com.tauhoa.train.models.TicketType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDTO {
    @Size(max = 10, message = "Căn cước công dân không được vượt quá 10 số")
    @Pattern(regexp = "^[0-9]", message = "Số điện thoại chỉ được nhập số")
    private String Cccd;


    @NotBlank(message = "Họ và tên không được bỏ trống")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Họ và tên không được chứa các kí tự đặc biệt")
    private String FullName;

    private TicketType ticketType;

}
