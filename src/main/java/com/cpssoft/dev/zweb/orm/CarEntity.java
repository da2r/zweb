package com.cpssoft.dev.zweb.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cpssoft.dev.zweb.type.CarType;

@Entity
@Table(name = "car")
public class CarEntity extends BaseEntity {

	private String name;
	private Long price;
	private BrandEntity brand;
	private CarType type;

	@Id
	@SequenceGenerator(name = "car_id", sequenceName = "car_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_id")
	public Long getId() {
		return id;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_id", nullable = true)
	public BrandEntity getBrand() {
		return brand;
	}

	public void setBrand(BrandEntity brand) {
		this.brand = brand;
	}

	@Enumerated(EnumType.STRING)
	public CarType getType() {
		return type;
	}

	public void setType(CarType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("id: %d; name: %s; price: %d, brand: {%s}", id, name, price, brand);
	}
}
