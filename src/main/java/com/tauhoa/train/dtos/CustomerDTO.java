package com.tauhoa.train.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String cccd;

    private String email;

    private String fullName;

    private String phone;


}

