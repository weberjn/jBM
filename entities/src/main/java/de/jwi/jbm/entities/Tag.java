package de.jwi.jbm.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;


/**
 * The persistent class for the sc_tags database table.
 * 
 */
@Entity
@Table(name="sc_tags")
@NamedQuery(name="Tag.findAll", query="SELECT s FROM Tag s")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@SequenceGenerator(name="tIds_gen", sequenceName="tIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tIds_gen")
	@Column(name="tId")
	private Integer id;

	private String tag;

	@Column(name="tDescription")
	private String description;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="uid")
	private User user;

	public Tag() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer tid) {
		this.id = tid;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}