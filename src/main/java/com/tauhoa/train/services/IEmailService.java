package com.tauhoa.train.services;

import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.Ticket;

public interface IEmailService {

    void sendTicketEmail(Customer customer, int ReservationCode);
    void sendEmail(String toEmail, String subject , String html);
    void sendEmailCancelTicket(Ticket ticket);
    void sendEmailCancel(String toEmail, String subject , String html);
    void sendOtpToEmail(String email);
}
