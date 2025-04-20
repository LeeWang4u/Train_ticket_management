package com.tauhoa.train.services;

import com.tauhoa.train.models.Customer;

public interface IEmailService {

    void sendTicketEmail(Customer customer, int ReservationCode);
    void sendEmail(String toEmail, String subject , String html);
}
