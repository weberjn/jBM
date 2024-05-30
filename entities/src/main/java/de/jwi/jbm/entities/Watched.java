package de.jwi.jbm.entities;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the sc_watched database table.
 * 
 */
@Entity
@Table(name="sc_watched")
@NamedQuery(name="Watched.findAll", query="SELECT s FROM Watched s")
public class Watched implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="wIds_gen", sequenceName="wIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="wIds_gen")
	@Column(name="wId")
	private Integer wid;

	private Integer uid;

	private Integer watched;

	public Watched() {
	}

	public Integer getWid() {
		return this.wid;
	}

	public void setWid(Integer wid) {
		this.wid = wid;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getWatched() {
		return this.watched;
	}

	public void setWatched(Integer watched) {
		this.watched = watched;
	}

}