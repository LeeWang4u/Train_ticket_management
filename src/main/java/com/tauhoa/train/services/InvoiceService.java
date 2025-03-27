package com.tauhoa.train.services;

import com.tauhoa.train.models.Invoice;
import com.tauhoa.train.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceService implements IInvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice save(BigDecimal basePrice){
        LocalDateTime date =LocalDateTime.now();
        String status = "Complete";
        Invoice invoice = new Invoice( basePrice, status ,  date);
        return invoiceRepository.save(invoice);
    }
}
