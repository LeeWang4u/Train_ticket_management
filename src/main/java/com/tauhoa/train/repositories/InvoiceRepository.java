package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Invoice;
import com.tauhoa.train.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findByInvoiceId(int id);
}
