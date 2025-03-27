package com.tauhoa.train.services;


import com.tauhoa.train.models.Invoice;

import java.math.BigDecimal;

public interface IInvoiceService {
    Invoice save(BigDecimal basePrice);
//    Invoice findByInvoiceId(int id);
}
