package com.tauhoa.train.repositories;

import com.tauhoa.train.models.ReservationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<ReservationCode, Integer> {
    ReservationCode findByInvoiceId(int id);
}
