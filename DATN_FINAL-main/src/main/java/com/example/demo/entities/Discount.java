package com.example.demo.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
@Table(name = "discounts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "condition")
    private Integer condition;

    @Column(name = "decrease_percent")
    private Integer decreasePercent;

    @Column(name = "discount_name" , nullable = false)
    private String discountName;

    @Column(name = "start_day")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDay;

    @Column(name = "end_day")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDay;

    private Integer quantity;
    
    @Column(name = "member_type")
    private String memberType;
    
    private String unit;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getDecreasePercent() {
		return decreasePercent;
	}

	public void setDecreasePercent(Integer decreasePercent) {
		this.decreasePercent = decreasePercent;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public Date getStartDay() {
		return startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
    
}