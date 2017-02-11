package de.jwi.jbm.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sc_users_sslclientcerts database table.
 * 
 */
@Entity
@Table(name="sc_users_sslclientcerts")
@NamedQuery(name="UsersSslclientcert.findAll", query="SELECT s FROM UsersSslclientcert s")
public class UsersSslclientcert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ids_gen", sequenceName="ids", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ids_gen")
	@Column(name="id")
	private Integer id;

	private String sslclientissuerdn;

	private String sslemail;

	private String sslname;

	private String sslserial;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="uid")
	private User user;

	public UsersSslclientcert() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSslclientissuerdn() {
		return this.sslclientissuerdn;
	}

	public void setSslclientissuerdn(String sslclientissuerdn) {
		this.sslclientissuerdn = sslclientissuerdn;
	}

	public String getSslemail() {
		return this.sslemail;
	}

	public void setSslemail(String sslemail) {
		this.sslemail = sslemail;
	}

	public String getSslname() {
		return this.sslname;
	}

	public void setSslname(String sslname) {
		this.sslname = sslname;
	}

	public String getSslserial() {
		return this.sslserial;
	}

	public void setSslserial(String sslserial) {
		this.sslserial = sslserial;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}