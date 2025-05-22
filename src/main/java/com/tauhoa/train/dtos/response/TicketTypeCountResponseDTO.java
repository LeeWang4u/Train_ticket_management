package com.tauhoa.train.dtos.response;

public class TicketTypeCountResponseDTO {
        private String ticketTypeName;
        private Long ticketCount;

        public TicketTypeCountResponseDTO (String ticketTypeName, Long ticketCount) {
            this.ticketTypeName = ticketTypeName;
            this.ticketCount = ticketCount;
        }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public Long getTicketCount() {
        return ticketCount;
    }
}
