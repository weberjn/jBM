package de.jwi.jbm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the sc_users database table.
 * 
 */
@Entity
@Table(name="sc_users")
@NamedQuery(name="User.findAll", query="SELECT s FROM User s")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="uIds_gen", sequenceName="uIds", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="uIds_gen")
	@Column(name="uId")
	private Integer uid;

	private String email;

	private String homepage;

	private String name;

	private String password;

	private String privatekey;

	@Column(name = "uContent")
	private String content;
	
	@Column(name = "uDatetime")
	private Timestamp datetime;

	@Column(name = "uModified")
	private Timestamp modified;

	private String username;

	//bi-directional many-to-one association to Bookmark
	@OneToMany(mappedBy="User",cascade = CascadeType.PERSIST)
	private List<Bookmark> bookmarks;

	//bi-directional many-to-one association to Searchhistory
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST)
	private List<Searchhistory> searchhistories;

	//bi-directional many-to-one association to Commondescription
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST)
	private List<Commondescription> commondescriptions;

	//bi-directional many-to-one association to Tag
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST)
	private List<Tag> tags;

	//bi-directional many-to-one association to Tagscache
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST)
	private List<Tagscache> tagscaches;

	//bi-directional many-to-one association to Tagsstat
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST)
	private List<Tagsstat> tagsstats;

	//bi-directional many-to-one association to UsersSslclientcert
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST)
	private List<UsersSslclientcert> usersSslclientcerts;


	public User() {
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomepage() {
		return this.homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrivatekey() {
		return this.privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String ucontent) {
		this.content = ucontent;
	}

	public Timestamp getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	public Timestamp getModified() {
		return this.modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Bookmark> getBookmarks() {
		return this.bookmarks;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public Bookmark addBookmark(Bookmark bookmark) {
		getBookmarks().add(bookmark);
		bookmark.setUser(this);

		return bookmark;
	}

	public Bookmark removeBookmark(Bookmark bookmark) {
		getBookmarks().remove(bookmark);
		bookmark.setUser(null);

		return bookmark;
	}

	public List<Searchhistory> getSearchhistories() {
		return this.searchhistories;
	}

	public void setSearchhistories(List<Searchhistory> searchhistories) {
		this.searchhistories = searchhistories;
	}

	public Searchhistory addSearchhistory(Searchhistory searchhistory) {
		getSearchhistories().add(searchhistory);
		searchhistory.setUser(this);

		return searchhistory;
	}

	public Searchhistory removeSearchhistory(Searchhistory searchhistory) {
		getSearchhistories().remove(searchhistory);
		searchhistory.setUser(null);

		return searchhistory;
	}

	public List<Commondescription> getCommondescriptions() {
		return this.commondescriptions;
	}

	public void setScCommondescriptions(List<Commondescription> commondescriptions) {
		this.commondescriptions = commondescriptions;
	}

	public Commondescription addScCommondescription(Commondescription commondescription) {
		getCommondescriptions().add(commondescription);
		commondescription.setUser(this);

		return commondescription;
	}

	public Commondescription removeScCommondescription(Commondescription commondescription) {
		getCommondescriptions().remove(commondescription);
		commondescription.setUser(null);

		return commondescription;
	}

	public List<Tag> getTags() {
		return this.tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Tag addTag(Tag tag) {
		getTags().add(tag);
		tag.setUser(this);

		return tag;
	}

	public Tag removeTag(Tag tag) {
		getTags().remove(tag);
		tag.setUser(null);

		return tag;
	}

	public List<Tagscache> getTagscaches() {
		return this.tagscaches;
	}

	public void setTagscaches(List<Tagscache> tagscaches) {
		this.tagscaches = tagscaches;
	}

	public Tagscache addTagscach(Tagscache tagscach) {
		getTagscaches().add(tagscach);
		tagscach.setUser(this);

		return tagscach;
	}

	public Tagscache removeTagscach(Tagscache tagscach) {
		getTagscaches().remove(tagscach);
		tagscach.setUser(null);

		return tagscach;
	}

	public List<Tagsstat> getTagsstats() {
		return this.tagsstats;
	}

	public void setTagsstats(List<Tagsstat> tagsstats) {
		this.tagsstats = tagsstats;
	}

	public Tagsstat addTagsstat(Tagsstat tagsstat) {
		getTagsstats().add(tagsstat);
		tagsstat.setUser(this);

		return tagsstat;
	}

	public Tagsstat removeTagsstat(Tagsstat tagsstat) {
		getTagsstats().remove(tagsstat);
		tagsstat.setUser(null);

		return tagsstat;
	}

	public List<UsersSslclientcert> getUsersSslclientcerts() {
		return this.usersSslclientcerts;
	}

	public void setUsersSslclientcerts(List<UsersSslclientcert> UsersSslclientcerts) {
		this.usersSslclientcerts = UsersSslclientcerts;
	}

	public UsersSslclientcert addUsersSslclientcert(UsersSslclientcert usersSslclientcert) {
		getUsersSslclientcerts().add(usersSslclientcert);
		usersSslclientcert.setUser(this);

		return usersSslclientcert;
	}

	public UsersSslclientcert removeUsersSslclientcert(UsersSslclientcert UsersSslclientcert) {
		getUsersSslclientcerts().remove(UsersSslclientcert);
		UsersSslclientcert.setUser(null);

		return UsersSslclientcert;
	}


}