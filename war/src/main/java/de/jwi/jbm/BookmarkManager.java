package de.jwi.jbm;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.User;

public class BookmarkManager {

	EntityManager em;

	public BookmarkManager(EntityManager entityManager) {
		super();
		this.em = entityManager;
	}

	public void addBookmark(String userEmail, URL url, String title, String description, String tags) {
		Query query = em.createQuery("Select u FROM User u WHERE u.email = :email");
		query.setParameter("email", userEmail);

		List resultList = query.getResultList();

		User user = (User) query.getSingleResult();
		
		String md5 = MD5(url.toString());

		Query query2 = em.createQuery("Select b FROM Bookmark b WHERE b.uid = :uid AND b.hash = :hash");
		query2.setParameter("uid", user.getUid());
		query2.setParameter("hash", md5);
		Bookmark bookmark = (Bookmark) query2.getSingleResult();

		if (bookmark == null) {
			bookmark = new Bookmark();
			bookmark.setAddress(url.toString());
			bookmark.setTitle(title);
			bookmark.setDescription(description);

			Timestamp ts = new Timestamp(new Date().getTime());
			bookmark.setDatetime(ts);
			bookmark.setModified(ts);
			
			bookmark.setHash(md5);
			
			bookmark.setUser(user);

			em.persist(bookmark);
		}
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
