package com.tauhoa.train.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "invoice_status", nullable = false, length = 50)
    private String invoiceStatus;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL", updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public Invoice() {}

    public Invoice(BigDecimal totalAmount, String invoiceStatus, LocalDateTime createdAt) {
        this.totalAmount = totalAmount;
        this.invoiceStatus = invoiceStatus;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // toString Method
    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", totalAmount=" + totalAmount +
                ", invoiceStatus='" + invoiceStatus + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}