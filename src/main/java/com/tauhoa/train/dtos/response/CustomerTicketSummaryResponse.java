package com.tauhoa.train.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerTicketSummaryResponse {

    private int customerId;
    private String email;
    private String phone;
    private Long ticketCount;

    public static CustomerTicketSummaryResponse toCustomerTicketSummaryResponse(int customerId, String email, String phone, Long ticketCount) {
        return CustomerTicketSummaryResponse.builder()
                .customerId(customerId)
                .email(email)
                .phone(phone)
                .ticketCount(ticketCount)
                .build();
    }
}
