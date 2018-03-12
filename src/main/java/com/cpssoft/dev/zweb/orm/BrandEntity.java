package com.cpssoft.dev.zweb.orm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "brand")
public class BrandEntity extends BaseEntity {

	private String name;

	@Id
	@SequenceGenerator(name = "brand_id", sequenceName = "brand_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_id")
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("id: %d; name: %s", id, name);
	}

}
