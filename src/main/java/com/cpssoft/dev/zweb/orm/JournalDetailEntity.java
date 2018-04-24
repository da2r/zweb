package com.cpssoft.dev.zweb.orm;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "journal_detail")
public class JournalDetailEntity extends BaseEntity {

	private LocalDate transDate;
	private GlAccountEntity glAccount;
	private long amount;
	private String description;
	private int seq;
	private int year;
	private int period;
	private JournalEntity journal;

	@Id
	@SequenceGenerator(name = "journal_detail_id", sequenceName = "journal_detail_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "journal_detail_id")
	public Long getId() {
		return id;
	}

	@Column
	public LocalDate getTransDate() {
		return transDate;
	}

	public void setTransDate(LocalDate transDate) {
		this.transDate = transDate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "gl_account_id", nullable = false)
	public GlAccountEntity getGlAccount() {
		return glAccount;
	}

	public void setGlAccount(GlAccountEntity glAccount) {
		this.glAccount = glAccount;
	}

	@Column
	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Column
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Column
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Column
	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	@ManyToOne(targetEntity = JournalEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "journal_id")
	public JournalEntity getJournal() {
		return journal;
	}

	public void setJournal(JournalEntity journal) {
		this.journal = journal;
	}

}
