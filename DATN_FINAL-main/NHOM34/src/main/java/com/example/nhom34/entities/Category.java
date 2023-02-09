package com.example.nhom34.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "create_day")
    private Date createDay;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "modify_day")
    private Date modifyDay;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "status")
    private int status;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    List<Product> products;
}
