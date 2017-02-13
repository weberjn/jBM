package de.jwi.jbm.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sc_votes database table.
 * 
 */
@Entity
@Table(name="sc_votes")
@NamedQuery(name="Vote.findAll", query="SELECT s FROM Vote s")
public class Vote implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@SequenceGenerator(name="vIds_gen", sequenceName="vIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="vIds_gen")
	@Column(name="id")
	private Integer id;
	
	private Integer bid;

	private Integer vote;

	//bi-directional many-to-one association to ScUser
	@ManyToOne
	@JoinColumn(name="uid")
	private User user;

	public Vote() {
	}

	public Integer getId() {
		return id;
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

	public Integer getVote() {
		return this.vote;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}