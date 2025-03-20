package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Invoice;
import com.tauhoa.train.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
