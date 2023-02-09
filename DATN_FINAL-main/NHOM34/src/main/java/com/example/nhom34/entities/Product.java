package com.example.nhom34.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products", schema = "vinh")
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "product_name")
    private String productName;

    @Basic
    @Column(name = "unit_price")
    private Double unitPrice;

    @Basic
    @Column(name = "create_day")
    private Date createDay;

    @Basic
    @Column(name = "created_by")
    private Long createBy;

    @Basic
    @Column(name = "modify_day")
    private Date modifyDay;

    @Basic
    @Column(name = "material")
    private String material;

    @Basic
    @Column(name = "image")
    private String image;

    @Basic
    @Column(name = "status")
    private Integer status;

    @Basic
    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Basic
    @Column(name = "modified_by")
    private Long modifiedBy;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<ProductDetail> productDetailList;
}
