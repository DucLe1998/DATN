package com.example.demo.entities;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_details")
public class ProductDetail implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "size")
	private String size;

	@Column(name = "color")
	private String color;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "thumnail")
	private String thumnail;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "create_day")
	private Date createDay;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "modify_day")
	private Date modifyDay;

	@Column(name = "modified_by")
	private Integer modifiedBy;

	@Column(name = "status")
	private Integer status;

	@Override
	public String toString() {
		return "ProductDetail [id=" + id + ", size=" + size + ", color=" + color + ", quantity=" + quantity
				+ ", thumnail=" + thumnail + ", product=" + product.getName() + ", createDay=" + createDay + ", createdBy="
				+ createdBy + ", modifyDay=" + modifyDay + ", modifiedBy=" + modifiedBy + ", status=" + status + "]";
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getThumnail() {
		return thumnail;
	}

	public void setThumnail(String thumnail) {
		this.thumnail = thumnail;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getCreateDay() {
		return createDay;
	}

	public void setCreateDay(Date createDay) {
		this.createDay = createDay;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifyDay() {
		return modifyDay;
	}

	public void setModifyDay(Date modifyDay) {
		this.modifyDay = modifyDay;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
