package de.jwi.jbm.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sc_tagsstats database table.
 * 
 */
@Entity
@Table(name="sc_tagsstats")
@NamedQuery(name="Tagsstat.findAll", query="SELECT s FROM Tagsstat s")
public class Tagsstat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="tstIds_gen", sequenceName="tstIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tstIds_gen")
	@Column(name="tstId")
	private Integer tstid;

	private Integer depth;

	private Integer nb;

	private Integer nbupdate;

	private String relationtype;

	private String tag1;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="uid")
	private User user;

	public Tagsstat() {
	}

	public Integer getTstid() {
		return this.tstid;
	}

	public void setTstid(Integer tstid) {
		this.tstid = tstid;
	}

	public Integer getDepth() {
		return this.depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getNb() {
		return this.nb;
	}

	public void setNb(Integer nb) {
		this.nb = nb;
	}

	public Integer getNbupdate() {
		return this.nbupdate;
	}

	public void setNbupdate(Integer nbupdate) {
		this.nbupdate = nbupdate;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}