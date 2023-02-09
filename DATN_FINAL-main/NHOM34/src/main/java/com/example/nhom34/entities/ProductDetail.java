package com.example.nhom34.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_details", schema = "vinh")
public class ProductDetail {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "size")
    private String size;

    @Basic
    @Column(name = "color")
    private String color;

    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    @Basic
    @Column(name = "thumnail")
    private String thumnail;

    @Basic
    @Column(name = "create_day")
    private Date createDay;

    @Column(name = "created_by")
    private Integer createdBy;

    @Basic
    @Column(name = "modify_day")
    private Date modifyDay;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Basic
    @Column(name = "status")
    private Integer status;

    @Basic
    @Column(name = "bar_code")
    private String barCode;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
