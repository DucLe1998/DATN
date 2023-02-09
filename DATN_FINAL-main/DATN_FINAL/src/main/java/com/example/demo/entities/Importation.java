package com.example.demo.entities;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data

@Getter
@Setter
@Entity
@Table(name = "importations")
public class Importation {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_id", referencedColumnName = "id")
	private Supplier supplier;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staff_id", referencedColumnName = "id")
	private User staff;
	@CreationTimestamp
	@Column(name = "create_day")
	private Date createDay;
	@Column(name = "total")
	private double total;
	@Column(name = "status")
	private int status;

	@JsonIgnore
	@OneToMany(mappedBy = "importation")
	private List<ImportationDetail> importationDetails;

	public List<ImportationDetail> getImportationDetails() {
		return importationDetails;
	}

	public void setImportationDetails(List<ImportationDetail> importationDetails) {
		this.importationDetails = importationDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public User getStaff() {
		return staff;
	}

	public void setStaff(User staff) {
		this.staff = staff;
	}

	public Date getCreateDay() {
		return createDay;
	}

	public void setCreateDay(Date createDay) {
		this.createDay = createDay;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Importation(Long id, Supplier supplier, User staff, Date createDay, double total, int status,
			List<ImportationDetail> importationDetails) {
		super();
		this.id = id;
		this.supplier = supplier;
		this.staff = staff;
		this.createDay = createDay;
		this.total = total;
		this.status = status;
		this.importationDetails = importationDetails;
	}

	public Importation() {
	}

}
