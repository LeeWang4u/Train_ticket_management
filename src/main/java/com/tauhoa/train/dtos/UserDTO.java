package com.tauhoa.train.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    @Size(max = 10, message = "Căn cước công dân không được vượt quá 10 số")
    @Pattern(regexp = "^[0-9]", message = "Số điện thoại chỉ được nhập số")
    private String Cccd;

    @Email(message = "Email phải hợp lệ")
    @NotBlank(message = "Email không được bỏ trống")
    @Pattern(regexp = "^[a-zA-Z0-9@.]+$", message = "Email không được chứa các kí tự đặc biệt ")
    private String Email;

    @NotBlank(message = "Họ và tên không được bỏ trống")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Họ và tên không được chứa các kí tự đặc biệt")
    private String FullName;

    @Pattern(regexp = "^[0-9]", message = "Số điện thoại chỉ được nhập số")
    private String Phone;
}
