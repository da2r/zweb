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

import com.cpssoft.dev.zweb.type.UserType;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

	private String username;
	private String passhash;
	private UserType type;

	@Id
	@SequenceGenerator(name = "users_id", sequenceName = "users_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id")
	public Long getId() {
		return id;
	}
	
	@Column
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column
	public String getPasshash() {
		return passhash;
	}

	public void setPasshash(String passhash) {
		this.passhash = passhash;
	}
	
	@Enumerated(EnumType.STRING)
	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("id: %d; username: %s; type: %s", id, username, type);
	}
}
