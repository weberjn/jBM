package de.jwi.jbm;

import java.net.URL;
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

	public void addBookmark(String userEmail, URL url, String title, String description, String tags)
	{
		Query query = em.createQuery("Select u FROM User u WHERE u.email = :email");
		query.setParameter("email", userEmail);
		
		List resultList = query.getResultList(); 
		
		User user = (User)query.getSingleResult();
		
		Query query2 = em.createQuery("Select b FROM Bookmark b WHERE b.uid = :uid");
		query2.setParameter("uid", user.getUid());
		Bookmark bookmark = (Bookmark)query2.getSingleResult();
		
		if (bookmark == null)
		{
			bookmark = new Bookmark();
			bookmark.setAddress(url.toString());
			bookmark.setTitle(title);
			bookmark.setDescription(description);
		
			Timestamp ts = new Timestamp(new Date().getTime());
			bookmark.setDatetime(ts);
			bookmark.setModified(ts);
			
			em.persist(bookmark);
		}
	}
	
	
}
