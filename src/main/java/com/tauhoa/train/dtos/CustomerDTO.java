package com.tauhoa.train.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private String cccd;

    private String email;

    private String fullName;

    private String phone;


}

