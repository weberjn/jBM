package de.jwi.jbm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sc_commondescription database table.
 * 
 */
@Entity
@Table(name="sc_commondescription")
@NamedQuery(name="Commondescription.findAll", query="SELECT s FROM Commondescription s")
public class Commondescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="cdIds_gen", sequenceName="cdIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cdIds_gen")
	@Column(name="cdId")
	private Integer id;

	@Column(name="bHash")	
	private String hash;

	@Column(name="cdDatetime")	
	private Timestamp datetime;

	@Column(name="cdDescription")
	private String description;

	@Column(name="cdTitle")
	private String title;

	@Column(name="tag")
	private String tag;

<<<<<<< HEAD
	//bi-directional many-to-one association to User
	@OneToOne
=======
	//bi-directional many-to-one association to ScUser
	@ManyToOne
>>>>>>> 230ff8a8332eb2d7de56e57453e302a0d844b572
	@JoinColumn(name="uid")
	private User user;

	public Commondescription() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer cdid) {
		this.id = cdid;
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(String bhash) {
		this.hash = bhash;
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}