package de.jwi.jbm.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the sc_bookmarks database table.
 * 
 */
@Entity
@Table(name = "sc_bookmarks")
@NamedQuery(name = "Bookmark.findAll", query = "SELECT s FROM Bookmark s")
public class Bookmark implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "bIds_gen", sequenceName = "bIds", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bIds_gen")
	@Column(name = "bId")
	private Integer id;

	@Column(name = "bAddress")
	private String address;

	@Column(name = "bDatetime")
	private Timestamp datetime;

	@Column(name = "bDescription")
	private String description;

	@Column(name = "bHash")
	private String hash;

	@Column(name = "bIp")
	private String ip;

	@Column(name = "bModified")
	private Timestamp modified;

	@Column(name = "bPrivateNote")
	private String privatenote;

	@Column(name = "bShort")
	private String sShort;

	@Column(name = "bStatus")
	private Integer status;

	@Column(name = "bTitle")
	private String title;

	@Column(name = "bVotes")
	private Integer votes;

	@Column(name = "bVoting")
	private Integer voting;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "uid")
	private User user;

	public Bookmark() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer bid) {
		this.id = bid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getModified() {
		return this.modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public String getPrivatenote() {
		return this.privatenote;
	}

	public void setPrivatenote(String privatenote) {
		this.privatenote = privatenote;
	}

	public String getShort() {
		return this.sShort;
	}

	public void setShort(String sShort) {
		this.sShort = sShort;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getVotes() {
		return this.votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	public Integer getVoting() {
		return this.voting;
	}

	public void setVoting(Integer voting) {
		this.voting = voting;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}