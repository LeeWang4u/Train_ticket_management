package com.tauhoa.train.services;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.Ticket;
import com.tauhoa.train.models.TrainSchedule;
import com.tauhoa.train.repositories.TicketRepository;
import com.tauhoa.train.repositories.TrainScheduleRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final TicketRepository ticketRepository;
    private final TrainScheduleRepository trainScheduleRepository;
//    private final UserRepository userRepository;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final JavaMailSender mailSender;

    public void sendTicketEmail( Customer customer, int reservationCode) {
        String subject = "Vé điện tử - Mã đặt chỗ: " + reservationCode;

        List<Ticket> tickets = ticketRepository.findByReservationCodeReservationCodeId(reservationCode);


        StringBuilder ticketsHtml = new StringBuilder();

        for (Ticket ticket : tickets) {
            String qrData = "Ticket ID: " + ticket.getTicketId()+ " Tên Hành Khách: "+ticket.getPassenger().getFullname();
            String qrBase64 = generateQRCodeBase64(qrData);
            String qrImgTag = "<img src=\"data:image/png;base64," + qrBase64 + "\" width=\"150\" height=\"150\"/>";
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
                              <p><strong>QR Code:</strong><br/>%s</p>
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
                    String.valueOf(ticket.getTicketId()),
                    qrImgTag));
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

    @Override
    public void sendEmailCancelTicket(Ticket ticket) {
        Customer customer = ticket.getCustomer();
        String subject = "Hủy vé - Mã đặt chỗ: " + ticket.getReservationCode().getReservationCodeId();
        String html = String.format("""
            <html>
            <head>
              <style>
                body { font-family: Arial, sans-serif; line-height: 1.6; }
                .cancel-info {
                  background-color: #fff3f3;
                  padding: 20px;
                  border: 1px solid #ffcccc;
                  border-radius: 10px;
                  margin: 20px;
                }
              </style>
            </head>
            <body>
              <div class="cancel-info">
                <h2>THÔNG BÁO HỦY VÉ</h2>
                <p>Xin chào <strong>%s</strong>,</p>
                <p>Vé có mã <strong>%s</strong> cho hành trình <strong>%s → %s</strong> đã được hủy.</p>
                <p>Do chúng tôi đã ngừng khai thác chuyến tàu này, nên vé của bạn đã bị hủy và bạn sẽ được hoàn tiền trong 3 ngày tới.
                <p>Chúng tôi rất tiếc vì sự bất tiện này và hy vọng bạn sẽ sử dụng lại dịch vụ chúng tôi trong tương lai.</p>
                <p>Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ bộ phận hỗ trợ khách hàng.</p>
              </div>
            </body>
            </html>
            """,
                customer.getFullname(),
                ticket.getTicketId(),
                ticket.getDepartureStation().getStationName(),
                ticket.getArrivalStation().getStationName());
        sendEmailCancel(customer.getEmail(), subject, html);
    }
    @Override
    public void sendEmailCancel(String toEmail, String subject , String html) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("duongsatvietnam@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(message);
            System.out.println("Đã gửi email hủy vé tới " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Lỗi gửi email hủy vé: " + e.getMessage());
        }
    }

    @Override
    public void sendOtpToEmail(String email) {
//        if (userRepository.findByEmail(email).isPresent()) {
//            throw new IllegalArgumentException("This email is already in use.");
//        }
        String throttleKey = "OTP_THROTTLE::" + email;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(throttleKey))) {
            throw new IllegalArgumentException("Please wait before requesting another OTP.");
        }

        redisTemplate.opsForValue().set(throttleKey, "1", Duration.ofSeconds(30));

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        redisTemplate.opsForValue().set("OTP::" + email, otp, Duration.ofMinutes(5));

        String html = "<p>Your OTP code is: <strong>" + otp + "</strong></p>";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("duongsatvietnam@gmail.com");
            helper.setTo(email);
            helper.setSubject("Your OTP Code");
            helper.setText(html, true);

            mailSender.send(message);
            System.out.println("OTP sent to: " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send OTP email: " + e.getMessage());
            throw new IllegalStateException("Failed to send OTP email.");
        }

        System.out.println("OTP sent to: " + email);
    }

    public String generateQRCodeBase64(String data) {
        try {
            int width = 200;
            int height = 200;

            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(data, BarcodeFormat.QR_CODE, width, height);

            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

