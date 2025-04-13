package com.tauhoa.train.services;

import com.tauhoa.train.models.ReservationCode;
import com.tauhoa.train.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceService implements IInvoiceService {
    private final InvoiceRepository invoiceRepository;

//    @Override
//    public ReservationCode save(BigDecimal basePrice){
//        LocalDateTime date =LocalDateTime.now();
//        String status = "Complete";
//        ReservationCode invoice = new ReservationCode( basePrice, status ,  date);
//        return invoiceRepository.save(invoice);
//    }
}
