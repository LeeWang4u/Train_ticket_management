package com.tauhoa.train.services;

import com.tauhoa.train.models.ReservationCode;
import com.tauhoa.train.repositories.ReservationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationCodeService implements IReservationCodeService{

    private final ReservationCodeRepository reservationCodeRepository;

    @Override
    public ReservationCode save(BigDecimal totalAmount ){
        LocalDateTime now = LocalDateTime.now();
        ReservationCode reservationCode = new ReservationCode(totalAmount, now);
        return reservationCodeRepository.save(reservationCode);
    }
}
