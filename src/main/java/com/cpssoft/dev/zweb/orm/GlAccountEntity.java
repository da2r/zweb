package com.cpssoft.dev.zweb.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cpssoft.dev.zweb.type.GlAccountType;

@Entity
@Table(name = "gl_account", uniqueConstraints = @UniqueConstraint(name = "unique_gl_account_code", columnNames = "code"))
public class GlAccountEntity extends BaseEntity {

	private String code;
	private String name;
	private GlAccountType accountType;

	@Id
	@SequenceGenerator(name = "glaccount_id", sequenceName = "glaccount_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "glaccount_id")
	public Long getId() {
		return id;
	}

	@Column
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	public GlAccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(GlAccountType accountType) {
		this.accountType = accountType;
	}

}
