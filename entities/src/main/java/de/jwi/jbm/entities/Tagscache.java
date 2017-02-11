package de.jwi.jbm.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sc_tagscache database table.
 * 
 */
@Entity
@Table(name="sc_tagscache")
@NamedQuery(name="Tagscache.findAll", query="SELECT s FROM Tagscache s")
public class Tagscache implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="tcIds_gen", sequenceName="tcIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tcIds_gen")
	@Column(name="tcId")
	private Integer id;

	private String relationtype;

	private String tag1;

	private String tag2;

	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="uid")
	private User user;

	public Tagscache() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer tcid) {
		this.id = tcid;
	}

	public String getRelationtype() {
		return this.relationtype;
	}

	public void setRelationtype(String relationtype) {
		this.relationtype = relationtype;
	}

	public String getTag1() {
		return this.tag1;
	}

	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}

	public String getTag2() {
		return this.tag2;
	}

	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}