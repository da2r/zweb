package com.cpssoft.dev.zweb.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.cpssoft.dev.zweb.module.UserAccess;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "unique_users_username", columnNames = "username"))
public class UserEntity extends BaseEntity {

	private String username;
	private String passhash;
	private String accessData;

	private UserAccess access = null;

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

	@Column
	public String getAccessData() {
		return accessData;
	}

	public void setAccessData(String accessData) {
		this.accessData = accessData;
	}

	@Transient
	public UserAccess getAccess() {
		if (access == null) {
			access = new UserAccess(this);
		}
		return access;
	}

	@Override
	public String toString() {
		return String.format("id: %d; username: %s", id, username);
	}
}
