package com.tauhoa.train.controllers;


import com.tauhoa.train.dtos.request.CustomerDTO;
import com.tauhoa.train.dtos.request.ReservationCodeRequestDTO;
import com.tauhoa.train.dtos.request.TicketRequestDTO;
import com.tauhoa.train.dtos.request.TicketReservationReqDTO;
import com.tauhoa.train.dtos.response.BookingResponse;
import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.Passenger;
import com.tauhoa.train.models.ReservationCode;
import com.tauhoa.train.models.TicketType;
import com.tauhoa.train.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.reset;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {
    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    @Mock
    private CustomerService customerService;
    @Mock
    private PassengerService passengerService;
    @Mock
    private ReservationCodeService reservationCodeService;
    @Mock
    private EmailService emailService;




    private ReservationCodeRequestDTO setUp(String nameCustomer, String email, String phone, String cccd ,String namePassenger, String cccdPassenger, BigDecimal totalPrice, TicketType ticketType) {
        ReservationCodeRequestDTO validRequest = new ReservationCodeRequestDTO();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFullName(nameCustomer);
        customerDTO.setEmail(email);
        customerDTO.setPhone(phone);
        customerDTO.setCccd(cccd);

        TicketRequestDTO ticket = new TicketRequestDTO();
        ticket.setFullName(namePassenger);
        ticket.setCccd(cccdPassenger);
        ticket.setTicketType(ticketType);
        ticket.setTotalPrice(totalPrice);

        validRequest.setCustomerDTO(customerDTO);
        validRequest.setTicketRequestDTO(List.of(ticket));
        return validRequest;
    }
    void bookTicket_whenValid_shouldReturnSuccess(int expectedStatus, ReservationCodeRequestDTO validRequest) {
        Customer mockCustomer = new Customer();
        ReservationCode mockReservation = new ReservationCode();
        mockReservation.setReservationCodeId(1);
        mockReservation.setTotalAmount(BigDecimal.valueOf(100000));
        mockReservation.setCreatedAt(LocalDateTime.now());

        when(customerService.save(any())).thenReturn(mockCustomer);
        when(reservationCodeService.save(any())).thenReturn(mockReservation);
        when(passengerService.save(any())).thenReturn(new Passenger());

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        BookingResponse body = (BookingResponse) response.getBody();
        assertEquals("success", body.getStatus());
        assertEquals("Đặt vé thành công", body.getMessage());
    }

    void bookTicket_whenMissingCustomerEmail_shouldReturn401(int expectedStatus, ReservationCodeRequestDTO validRequest) {
        validRequest.getCustomerDTO().setEmail(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }
    void bookTicket_whenMissingCustomerSDT_shouldReturn401(int expectedStatus, ReservationCodeRequestDTO validRequest) {
        validRequest.getCustomerDTO().setPhone(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }
    void bookTicket_whenMissingCustomerCccd_shouldReturn401(int expectedStatus, ReservationCodeRequestDTO validRequest) {
        validRequest.getCustomerDTO().setCccd(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }
    void bookTicket_whenMissingCustomerFullName_shouldReturn401(int expectedStatus, ReservationCodeRequestDTO validRequest) {
        validRequest.getCustomerDTO().setFullName(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }
    void bookTicket_whenInvalidCustomerFullName_shouldReturn402(int expectedStatus, ReservationCodeRequestDTO validRequest) {

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    void bookTicket_whenInvalidEmail_shouldReturn403(int expectedStatus, ReservationCodeRequestDTO validRequest) {

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    void bookTicket_whenInvalidPhone_shouldReturn406(int expectedStatus, ReservationCodeRequestDTO validRequest) {

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    void bookTicket_whenInvalidCccd_shouldReturn405(int expectedStatus, ReservationCodeRequestDTO validRequest) {

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }



    void bookTicket_whenPassengerInvalidFullName_shouldReturn402(int expectedStatus, ReservationCodeRequestDTO validRequest) {

        when(customerService.save(any())).thenReturn(new Customer());
        when(reservationCodeService.save(any())).thenReturn(new ReservationCode());

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    void bookTicket_whenPassengerInvalidCccd_shouldReturn405(int expectedStatus, ReservationCodeRequestDTO validRequest) {
        when(customerService.save(any())).thenReturn(new Customer());
        when(reservationCodeService.save(any())).thenReturn(new ReservationCode());

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    void bookTicket_whenExceptionThrown_shouldReturn500(int expectedStatus, ReservationCodeRequestDTO validRequest) {
        when(customerService.save(any())).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        BookingResponse body = (BookingResponse) response.getBody();
        assertEquals("error", body.getStatus());
        assertEquals("Đặt vé thất bại!", body.getMessage());
    }
    void bookTicket_whenMissingTicketInfo_shouldReturn400(int expectedStatus, ReservationCodeRequestDTO validRequest) {
        validRequest.setTicketRequestDTO(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    String nameCus="Trịnh Huy Hoàng";
    String email = "abc@example.com";
    String Phone="0912345678";
    String cccd = "012345678901";


    String namePass = "Trịnh Huy Hoàng";
    String cccdPass = "012345678901";
    TicketType ticketType =    new TicketType(1,"Người lớn", BigDecimal.valueOf(0));
    BigDecimal TotalPrice=BigDecimal.valueOf(100000);

    @Test
    void confirmTicket() {
        ReservationCodeRequestDTO validRequest = setUp(nameCus, email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenValid_shouldReturnSuccess(200, validRequest);

        bookTicket_whenExceptionThrown_shouldReturn500( 500,  validRequest);
        validRequest = setUp(nameCus, email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenMissingCustomerEmail_shouldReturn401(401, validRequest);
        validRequest = setUp(nameCus, email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenMissingCustomerSDT_shouldReturn401(401, validRequest);
        validRequest = setUp(nameCus, email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenMissingCustomerCccd_shouldReturn401(401, validRequest);
        validRequest = setUp(nameCus, email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenMissingCustomerFullName_shouldReturn401(401, validRequest);
        validRequest = setUp("123hinag", email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCustomerFullName_shouldReturn402(402, validRequest);
        validRequest = setUp(nameCus, "email", Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidEmail_shouldReturn403(403, validRequest);
        validRequest = setUp(nameCus, email, "123", cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidPhone_shouldReturn406(406, validRequest);
        validRequest = setUp(nameCus, email, Phone, "123", namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCccd_shouldReturn405(405, validRequest);
        validRequest = setUp(nameCus, email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenMissingTicketInfo_shouldReturn400(400, validRequest);
        reset(customerService);
        ReservationCodeRequestDTO validRequest2 = setUp(nameCus, email, Phone, cccd, "123jhdas", cccdPass, TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidFullName_shouldReturn402(402, validRequest2);
        ReservationCodeRequestDTO validRequest3 = setUp(nameCus, email, Phone, cccd, namePass, "cccdPass", TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidCccd_shouldReturn405(405, validRequest3);
        reset(customerService);
        validRequest = setUp("+++++", email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCustomerFullName_shouldReturn402(402, validRequest);
        validRequest = setUp(nameCus, "+++++", Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidEmail_shouldReturn403(403, validRequest);
        validRequest = setUp(nameCus, email, "hoang", cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidPhone_shouldReturn406(406, validRequest);
        validRequest = setUp(nameCus, email, Phone, "hoang", namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCccd_shouldReturn405(405, validRequest);
        reset(customerService);
         validRequest2 = setUp(nameCus, email, Phone, cccd, "=====", cccdPass, TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidFullName_shouldReturn402(402, validRequest2);
         validRequest3 = setUp(nameCus, email, Phone, cccd, namePass, "hoang", TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidCccd_shouldReturn405(405, validRequest3);
        reset(customerService);
        validRequest = setUp(";;;;;;;", email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCustomerFullName_shouldReturn402(402, validRequest);
        validRequest = setUp(nameCus, "hoang", Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidEmail_shouldReturn403(403, validRequest);
        validRequest = setUp(nameCus, email, ";[;[", cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidPhone_shouldReturn406(406, validRequest);
        validRequest = setUp(nameCus, email, Phone, "[][]]", namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCccd_shouldReturn405(405, validRequest);
        reset(customerService);
        validRequest2 = setUp(nameCus, email, Phone, cccd, "[][]]", cccdPass, TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidFullName_shouldReturn402(402, validRequest2);
        validRequest3 = setUp(nameCus, email, Phone, cccd, namePass, "123edd", TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidCccd_shouldReturn405(405, validRequest3);
        reset(customerService);
        validRequest = setUp("1qwd1", email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCustomerFullName_shouldReturn402(402, validRequest);
        validRequest = setUp(nameCus, "22ee", Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidEmail_shouldReturn403(403, validRequest);
        validRequest = setUp(nameCus, email, "*****", cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidPhone_shouldReturn406(406, validRequest);
        validRequest = setUp(nameCus, email, Phone, "$$$$", namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCccd_shouldReturn405(405, validRequest);
        reset(customerService);
        validRequest2 = setUp(nameCus, email, Phone, cccd, "&&&&", cccdPass, TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidFullName_shouldReturn402(402, validRequest2);
        validRequest3 = setUp(nameCus, email, Phone, cccd, namePass, "!!!!!", TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidCccd_shouldReturn405(405, validRequest3);
        reset(customerService);
        validRequest = setUp("!!@#", email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCustomerFullName_shouldReturn402(402, validRequest);
        validRequest = setUp(nameCus, "_)_)", Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidEmail_shouldReturn403(403, validRequest);
        validRequest = setUp(nameCus, email, "||||", cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidPhone_shouldReturn406(406, validRequest);
        validRequest = setUp(nameCus, email, Phone, "|}|}", namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCccd_shouldReturn405(405, validRequest);
        reset(customerService);
        validRequest2 = setUp(nameCus, email, Phone, cccd, "}|}|", cccdPass, TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidFullName_shouldReturn402(402, validRequest2);
        validRequest3 = setUp(nameCus, email, Phone, cccd, namePass, "}}}|", TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidCccd_shouldReturn405(405, validRequest3);
        reset(customerService);
        validRequest = setUp("}|||", email, Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCustomerFullName_shouldReturn402(402, validRequest);
        validRequest = setUp(nameCus, "}|||", Phone, cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidEmail_shouldReturn403(403, validRequest);
        validRequest = setUp(nameCus, email, "|}}}", cccd, namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidPhone_shouldReturn406(406, validRequest);
        validRequest = setUp(nameCus, email, Phone, "123qweqwe", namePass, cccdPass, TotalPrice, ticketType);
        bookTicket_whenInvalidCccd_shouldReturn405(405, validRequest);
        reset(customerService);
        validRequest2 = setUp(nameCus, email, Phone, cccd, "~~~", cccdPass, TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidFullName_shouldReturn402(402, validRequest2);
        validRequest3 = setUp(nameCus, email, Phone, cccd, namePass, "~```1``", TotalPrice, ticketType);
        bookTicket_whenPassengerInvalidCccd_shouldReturn405(405, validRequest3);
    }

}
