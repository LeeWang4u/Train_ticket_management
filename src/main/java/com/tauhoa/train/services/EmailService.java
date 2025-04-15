package com.tauhoa.train.services;


import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.Ticket;
import com.tauhoa.train.models.TrainSchedule;
import com.tauhoa.train.repositories.TicketRepository;
import com.tauhoa.train.repositories.TrainScheduleRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final TicketRepository ticketRepository;
    private final TrainScheduleRepository trainScheduleRepository;

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final JavaMailSender mailSender;

    public void sendTicketEmail( Customer customer, int reservationCode) {
        String subject = "Vé điện tử - Mã đặt chỗ: " + reservationCode;

        List<Ticket> tickets = ticketRepository.findByReservationCodeReservationCodeId(reservationCode);


        StringBuilder ticketsHtml = new StringBuilder();

        for (Ticket ticket : tickets) {
            Optional<TrainSchedule> departureTime = trainScheduleRepository.findByTrainIdAndStationId(ticket.getTrip().getTrain().getTrainId(), ticket.getDepartureStation().getStationId());
            Optional<TrainSchedule> arrivalTime = trainScheduleRepository.findByTrainIdAndStationId(ticket.getTrip().getTrain().getTrainId(), ticket.getArrivalStation().getStationId());
            String departureTimeString = departureTime.map(TrainSchedule::getArrivalTime).map(time -> time.format(timeFormatter))
                    .orElse("N/A");
            String arrivalTimeString = arrivalTime.map(TrainSchedule::getDepartureTime).map(time -> time.format(timeFormatter))
                    .orElse("N/A");
            ticketsHtml.append(String.format("""
                            <div class="flight-info">
                              <h3>%s → %s</h3>
                              <p><strong>Giờ đi:</strong> %s</p>
                              <p><strong>Giờ đến:</strong> %s</p>
                              <p><strong>Tên tàu:</strong> %s</p>
                              <p><strong>Hành khách:</strong> %s</p>
                              <p><strong>Đối tượng:</strong> %s</p>
                              <p><strong>Chỗ ngồi:</strong>Toa %s - Ghế %s</p>
                              <p><strong>Mã vé:</strong> %s</p>
                            </div>
                            <hr/>
                            """,
                    ticket.getDepartureStation().getStationName(),
                    ticket.getArrivalStation().getStationName(),
                    departureTimeString,
                    arrivalTimeString,
                    ticket.getTrip().getTrain().getTrainName(),
                    ticket.getPassenger().getFullname(),
                    ticket.getPassenger().getTicketType().getTicketTypeName(),
                    String.valueOf(ticket.getSeat().getCarriageList().getStt()),
                    String.valueOf(ticket.getSeat().getSeatNumber()),
                    String.valueOf(ticket.getTicketId())));
        }
        String html = String.format("""
                <html>
                <head>
                  <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; }
                    .header { text-align: center; padding: 20px; }
                    .reservation-code {
                      background: #ffe3ee;
                      padding: 15px;
                      text-align: center;
                      font-size: 20px;
                      font-weight: bold;
                      color: #c2185b;
                      border-radius: 10px;
                      margin: 20px auto;
                      width: 60%%;
                    }
                    .flight-info {
                      background-color: #f9f9f9;
                      padding: 15px;
                      margin: 15px 0;
                      border-radius: 10px;
                      box-shadow: 0 0 5px rgba(0,0,0,0.1);
                    }
                  </style>
                </head>
                <body>
                  <div class="header">
                    <h2>VÉ ĐIỆN TỬ</h2>
                    <p>Xin chào <strong>%s</strong>, cảm ơn bạn đã đặt vé!</p>
                  </div>
                
                  <div class="reservation-code">MÃ ĐẶT CHỖ: %s</div>
                  %s
                </body>
                </html>
                """, customer.getFullname(), reservationCode, ticketsHtml.toString());

        // Gửi email
        sendEmail(customer.getEmail(), subject, html);
    }

    public void sendEmail(String toEmail, String subject, String html) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("duongsatvietnam@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(html, true); // true: gửi HTML

            mailSender.send(message);
            System.out.println("Đã gửi email thành công tới " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Lỗi gửi email: " + e.getMessage());
        }
    }

}

