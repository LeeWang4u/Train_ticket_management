package com.tauhoa.train.services;

import com.tauhoa.train.models.ReservationCode;

import java.math.BigDecimal;

public interface IReservationCodeService {
    ReservationCode save(BigDecimal totalAmount);
}
