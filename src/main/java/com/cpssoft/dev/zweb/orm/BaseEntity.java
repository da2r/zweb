package com.cpssoft.dev.zweb.orm;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class BaseEntity implements IEntity {

	protected Long id;
	protected Long ver;
	protected LocalDateTime cts;
	protected LocalDateTime uts;

	protected void setId(Long id) {
		this.id = id;
	}

	@Version
	@Column(name = "ver")
	public Long getVer() {
		return ver;
	}

	protected void setVer(Long ver) {
		this.ver = ver;
	}

	@CreationTimestamp
	@Column(name = "cts")
	public LocalDateTime getCts() {
		return cts;
	}

	protected void setCts(LocalDateTime cts) {
		this.cts = cts;
	}

	@UpdateTimestamp
	@Column(name = "uts")
	public LocalDateTime getUts() {
		return uts;
	}

	protected void setUts(LocalDateTime uts) {
		this.uts = uts;
	}

}
