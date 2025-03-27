package com.tauhoa.train.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    @JsonProperty("Cccd")
    private String cccd;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("Phone")
    private String phone;


}

