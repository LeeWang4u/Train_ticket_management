package com.tauhoa.train.services;


import com.tauhoa.train.models.ReservationCode;

import java.math.BigDecimal;

public interface IInvoiceService {
    ReservationCode save(BigDecimal basePrice);
//    Invoice findByInvoiceId(int id);
}
