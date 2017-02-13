package de.jwi.jbm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sc_searchhistory database table.
 * 
 */
@Entity
@Table(name="sc_searchhistory")
@NamedQuery(name="Searchhistory.findAll", query="SELECT s FROM Searchhistory s")
public class Searchhistory implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@SequenceGenerator(name="shIds_gen", sequenceName="shIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="shIds_gen")
	@Column(name="shId")
	private Integer id;

	@Column(name="shDatetime")
	private Timestamp datetime;

	@Column(name="shNbResults")
	private Integer nbresults;

	@Column(name="shRange")
	private String range;

	@Column(name="shTerms")
	private String terms;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="uid")
	private User user;

	public Searchhistory() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setShid(Integer id) {
		this.id = id;
	}

	public Timestamp getDatetime() {
		return this.datetime;
	}

	public void setShdatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	public Integer getNbresults() {
		return this.nbresults;
	}

	public void setNbresults(Integer nbresults) {
		this.nbresults = nbresults;
	}

	public String getRange() {
		return this.range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getTerms() {
		return this.terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}