package de.jwi.jbm.entities;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the sc_bookmarks2tags database table.
 * 
 */
@Entity
@Table(name="sc_bookmarks2tags")
@NamedQuery(name="Bookmarks2tag.findAll", query="SELECT s FROM Bookmarks2Tag s")
public class Bookmarks2Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="b2tIds_gen", sequenceName="b2tIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="b2tIds_gen")
	@Column(name="id")
	private Integer id;

	private Integer bId;

	private Integer tId;

	public Bookmarks2Tag() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getbId() {
		return this.bId;
	}

	public void setbId(Integer bid) {
		this.bId = bid;
	}

	public Integer gettId()
	{
		return tId;
	}

	public void settId(Integer tId)
	{
		this.tId = tId;
	}

}