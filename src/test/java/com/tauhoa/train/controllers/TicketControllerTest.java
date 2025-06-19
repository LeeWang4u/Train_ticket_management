package com.tauhoa.train.controllers;


import com.tauhoa.train.dtos.request.CustomerDTO;
import com.tauhoa.train.dtos.request.ReservationCodeRequestDTO;
import com.tauhoa.train.dtos.request.TicketRequestDTO;
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

    private ReservationCodeRequestDTO validRequest;
    @RegisterExtension
    TestWatcher watcher = new TestWatcher() {
        @Override
        public void testSuccessful(ExtensionContext context) {
            System.out.println("PASSED: " + context.getDisplayName());
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            System.out.println("FAILED: " + context.getDisplayName());
        }

        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {
            System.out.println("DISABLED: " + context.getDisplayName() + " reason: " + reason.orElse("No reason"));
        }
    };
    @BeforeEach
    void setUp() {
        validRequest = new ReservationCodeRequestDTO();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFullName("Trịnh Huy Hoàng");
        customerDTO.setEmail("abc@example.com");
        customerDTO.setPhone("0912345678");
        customerDTO.setCccd("012345678901");

        TicketRequestDTO ticket = new TicketRequestDTO();
        ticket.setFullName("Trịnh Huy Hoàng");
        ticket.setCccd("012345678901");
        ticket.setTicketType(new TicketType(1,"Người lớn", BigDecimal.valueOf(0)));
        ticket.setTotalPrice(BigDecimal.valueOf(100000));

        validRequest.setCustomerDTO(customerDTO);
        validRequest.setTicketRequestDTO(List.of(ticket));
    }

    @Test
    void bookTicket_whenValid_shouldReturnSuccess() {
        Customer mockCustomer = new Customer();
        ReservationCode mockReservation = new ReservationCode();
        mockReservation.setReservationCodeId(1);
        mockReservation.setTotalAmount(BigDecimal.valueOf(100000));
        mockReservation.setCreatedAt(LocalDateTime.now());

        when(customerService.save(any())).thenReturn(mockCustomer);
        when(reservationCodeService.save(any())).thenReturn(mockReservation);
        when(passengerService.save(any())).thenReturn(new Passenger());

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(200, response.getStatusCodeValue());
        BookingResponse body = (BookingResponse) response.getBody();
        assertEquals("success", body.getStatus());
        assertEquals("Đặt vé thành công", body.getMessage());
    }

    @Test
    void bookTicket_whenMissingCustomerEmail_shouldReturn401() {
        validRequest.getCustomerDTO().setEmail(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(401, response.getStatusCodeValue());
    }
    @Test
    void bookTicket_whenMissingCustomerSDT_shouldReturn401() {
        validRequest.getCustomerDTO().setPhone(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(401, response.getStatusCodeValue());
    }
    @Test
    void bookTicket_whenMissingCustomerCccd_shouldReturn401() {
        validRequest.getCustomerDTO().setCccd(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(401, response.getStatusCodeValue());
    }
    @Test
    void bookTicket_whenMissingCustomerFullName_shouldReturn401() {
        validRequest.getCustomerDTO().setFullName(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(401, response.getStatusCodeValue());
    }
    @Test
    void bookTicket_whenInvalidCustomerFullName_shouldReturn402() {
        validRequest.getCustomerDTO().setFullName(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    void bookTicket_whenInvalidEmail_shouldReturn403() {
        validRequest.getCustomerDTO().setEmail("invalid-email");

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(403, response.getStatusCodeValue());
    }

    @Test
    void bookTicket_whenInvalidPhone_shouldReturn406() {
        validRequest.getCustomerDTO().setPhone("123");

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(406, response.getStatusCodeValue());
    }

    @Test
    void bookTicket_whenInvalidCccd_shouldReturn405() {
        validRequest.getCustomerDTO().setCccd("123");

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(405, response.getStatusCodeValue());
    }

    @Test
    void bookTicket_whenMissingTicketInfo_shouldReturn400() {
        validRequest.setTicketRequestDTO(null);

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void bookTicket_whenPassengerInvalidFullName_shouldReturn402() {
        validRequest.getTicketRequestDTO().get(0).setFullName("1234");

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(402, response.getStatusCodeValue());
    }

    @Test
    void bookTicket_whenPassengerInvalidCccd_shouldReturn405() {
        validRequest.getTicketRequestDTO().get(0).setCccd("abc");

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(405, response.getStatusCodeValue());
    }

    @Test
    void bookTicket_whenExceptionThrown_shouldReturn500() {
        when(customerService.save(any())).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<?> response = ticketController.bookTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        BookingResponse body = (BookingResponse) response.getBody();
        assertEquals("error", body.getStatus());
        assertEquals("Đặt vé thất bại!", body.getMessage());
    }
}
