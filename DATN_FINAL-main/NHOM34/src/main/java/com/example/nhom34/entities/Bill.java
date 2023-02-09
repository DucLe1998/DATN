package com.example.nhom34.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bills", schema = "vinh")
public class Bill {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "staff_id")
    private Long staffId;
    @Basic
    @Column(name = "create_day")
    private Timestamp createDay;
    @Basic
    @Column(name = "total")
    private Double total;
    @Basic
    @Column(name = "payment_type")
    private String paymentType;
    @Basic
    @Column(name = "note")
    private String note;
    @Basic
    @Column(name = "delete_day")
    private Timestamp deleteDay;
    @Basic
    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;
}
