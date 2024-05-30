package de.jwi.jbm.entities;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the sc_tags2tags database table.
 * 
 */
@Entity
@Table(name="sc_tags2tags")
@NamedQuery(name="Tags2tag.findAll", query="SELECT s FROM Tags2tag s")
public class Tags2tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ttIds_gen", sequenceName="ttIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ttIds_gen")
	@Column(name="ttId")
	private Integer ttid;

	private String relationtype;

	private String tag1;

	private String tag2;

	private Integer uid;

	public Tags2tag() {
	}

	public Integer getTtid() {
		return this.ttid;
	}

	public void setTtid(Integer ttid) {
		this.ttid = ttid;
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

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

}