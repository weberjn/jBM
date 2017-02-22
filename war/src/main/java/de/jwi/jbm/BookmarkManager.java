package de.jwi.jbm;

import java.math.BigInteger;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.User;

public class BookmarkManager {

	EntityManager em;

	public BookmarkManager(EntityManager entityManager) {
		super();
		this.em = entityManager;
	}

	public void addBookmark(User user, URL url, String title, String description, String tags) {

		String md5 = MD5(url.toString());

		Query query = em.createQuery("SELECT b FROM Bookmark b WHERE b.user=:user and b.hash = :hash");
		query.setParameter("user", user);
		query.setParameter("hash", md5);
		List resultList = query.getResultList();

		if (resultList.isEmpty()) {

			Bookmark bookmark = new Bookmark();
			bookmark.setAddress(url.toString());
			bookmark.setTitle(title);
			bookmark.setDescription(description);
			bookmark.setStatus(0);

			Timestamp ts = new Timestamp(new Date().getTime());
			bookmark.setDatetime(ts);
			bookmark.setModified(ts);

			bookmark.setHash(md5);

			bookmark.setUser(user);
			bookmark.setVotes(0);
			bookmark.setVoting(0);

			em.persist(bookmark);
		}
	}

	public Long getBookmarksCount() {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Bookmark", Long.class);
		Long n = query.getSingleResult();
		return n;
	}
	
	public Long getBookmarksCount(User user) {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Bookmark b WHERE b.user=:user", Long.class);
		query.setParameter("user", user);
		Long n = query.getSingleResult();
		return n;
	}

	public List<Bookmark> getBookmarks(User user, PagePosition pagePosition) {

		Query query = em.createQuery("SELECT b FROM Bookmark b WHERE b.user=:user order by b.modified desc");
		query.setParameter("user", user);
		query.setFirstResult((pagePosition.getCurrent()-1) * pagePosition.getPagesize()); 
		query.setMaxResults(pagePosition.getPagesize());
		
		List resultList = query.getResultList();

		return resultList;
	}

	public String MD5(String s) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");

			md.update(s.getBytes());

			String md5 = new BigInteger(1, md.digest()).toString(16);

			return md5;
		} catch (java.security.NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
