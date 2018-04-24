package com.cpssoft.dev.zweb.orm;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "journal", uniqueConstraints = @UniqueConstraint(name = "unique_journal_number", columnNames = "number"))
public class JournalEntity extends BaseEntity {

	private String number;
	private LocalDate transDate;
	private List<JournalDetailEntity> detailList;

	@Id
	@SequenceGenerator(name = "journal_id", sequenceName = "journal_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "journal_id")
	public Long getId() {
		return id;
	}

	@Column
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column
	public LocalDate getTransDate() {
		return transDate;
	}

	public void setTransDate(LocalDate transDate) {
		this.transDate = transDate;
	}

	@OneToMany(mappedBy = "journal", fetch = FetchType.LAZY)
	@OrderBy("seq")
	public List<JournalDetailEntity> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<JournalDetailEntity> detailList) {
		this.detailList = detailList;
	}


}
