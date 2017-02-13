package de.jwi.jbm.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sc_bookmarks2tags database table.
 * 
 */
@Entity
@Table(name="sc_bookmarks2tags")
@NamedQuery(name="Bookmarks2tag.findAll", query="SELECT s FROM Bookmarks2tag s")
public class Bookmarks2tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="b2tIds_gen", sequenceName="b2tIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="b2tIds_gen")
	@Column(name="id")
	private Integer id;

	private Integer bid;

	private String tag;

	public Bookmarks2tag() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBid() {
		return this.bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}