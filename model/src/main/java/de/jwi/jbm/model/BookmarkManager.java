package de.jwi.jbm.model;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.Tag;
import de.jwi.jbm.entities.User;

public class BookmarkManager {
	
	private static final Logger log = Logger.getLogger(BookmarkManager.class.getName());
	
	EntityManager em;

	public BookmarkManager(EntityManager entityManager) {
		super();
		this.em = entityManager;
	}
	
	public Timestamp touchBookmark(Bookmark bookmark)
	{
		Timestamp ts = new Timestamp(new Date().getTime());
		bookmark.setDatetime(ts);
		bookmark.setModified(ts);
		return ts;
	}
	
	public Tag addTag(User user, Bookmark bookmark, String tagName)
	{
		Tag tag = findTag(user, tagName);
		
		if (tag == null)
		{
			tag = new Tag();
			tag.setTag(tagName);
			tag.setUser(user);
			em.persist(tag);
		}
		if (!bookmark.getTags().contains(tag))
		{
			bookmark.getTags().add(tag);
		}
		
		return tag;
	}

	public void updateTags(User user, Bookmark bookmark, String[] tagNames)
	{
		List<Tag> previousTags = new ArrayList<Tag>(bookmark.getTags());
		
		for (String s : tagNames)
		{
			Tag newTag = addTag(user, bookmark, s);
			previousTags.remove(newTag);
		}
		
		bookmark.getTags().removeAll(previousTags);
	}
	
	public Tag findTag(User user, String tagName)
	{
		Tag tag = null;
		
		Query query = em.createQuery("SELECT t FROM Tag t WHERE t.user = :user and t.tag = :tag");
		query.setParameter("user", user);
		query.setParameter("tag", tagName);
		List resultList = query.getResultList();

		if (!resultList.isEmpty()) {
			tag = (Tag)resultList.get(0);
		}
		
		return tag;
	}
	
	
	public Tag findTag(Integer id)
	{
		TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.id=:id", Tag.class);
		query.setParameter("id", id);
		Tag tag = query.getSingleResult();
		return tag;
	}


	
	
	public void addBookmark(User user, Bookmark bookmark) throws MalformedURLException {

		String address = bookmark.getAddress();
		
		URL url = new URL(address);
		
		String md5 = MD5(url.toString());

		Query query = em.createQuery("SELECT b FROM Bookmark b WHERE b.user=:user and b.hash = :hash");
		query.setParameter("user", user);
		query.setParameter("hash", md5);
		List resultList = query.getResultList();

		if (resultList.isEmpty()) {

			bookmark.setAddress(url.toString());
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

	public int getBookmarksCount() {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Bookmark b", Long.class);
		Long n = query.getSingleResult();
		return n.intValue();
	}
	
	public int getBookmarksCount(User user) {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Bookmark b WHERE b.user=:user", Long.class);
		query.setParameter("user", user);
		Long n = query.getSingleResult();
		return n.intValue();
	}
	
	public int getBookmarksCount(User user, Tag tag) {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Bookmark b, IN (b.tags) t WHERE t = :tag and b.user=:user", Long.class);
		query.setParameter("user", user);
		query.setParameter("tag", tag);
		Long n = query.getSingleResult();
		return n.intValue();
	}

	
	public Bookmark findBookmark(User user, int bookmarkId)
	{
		Bookmark bookmark = null;
		
		Query query = em.createQuery("SELECT b FROM Bookmark b WHERE b.user=:user and b.id = :id");
		query.setParameter("user", user);
		query.setParameter("id", bookmarkId);
		List resultList = query.getResultList();

		if (!resultList.isEmpty()) {
			bookmark = (Bookmark)resultList.get(0);
		}
		
		return bookmark;
	}

	public boolean removeBookmark(User user, int bookmarkId)
	{
		Bookmark bookmark = findBookmark(user, bookmarkId);
		if (bookmark != null)
		{
			em.remove(bookmark);
			return true;
		}
		return false;
	}
	
	public List<Bookmark> getBookmarks(User user, PagePosition pagePosition) {

		Query query = em.createQuery("SELECT b FROM Bookmark b WHERE b.user=:user order by b.modified desc");
		query.setParameter("user", user);
		query.setFirstResult((pagePosition.getCurrent()-1) * pagePosition.getPagesize()); 
		query.setMaxResults(pagePosition.getPagesize());
		
		List resultList = query.getResultList();

		return resultList;
	}
	
	
	public int getBookmarksCountForSearch(User user, String text) {
		
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Bookmark b WHERE b.user=:user AND (b.address LIKE :text OR b.title LIKE :text)", Long.class);
		query.setParameter("user", user);
		query.setParameter("text", "%"+text+"%");
		Long n = query.getSingleResult();
		return n.intValue();
	}
	
	public List<Bookmark> searchBookmarks(User user,  String text, PagePosition pagePosition) {

		Query query = em.createQuery("SELECT b FROM Bookmark b WHERE b.user=:user AND (b.address LIKE :text OR b.title LIKE :text) order by b.modified desc");
		query.setParameter("user", user);
		query.setParameter("text", "%"+text+"%");
		query.setFirstResult((pagePosition.getCurrent()-1) * pagePosition.getPagesize()); 
		query.setMaxResults(pagePosition.getPagesize());
		
		List resultList = query.getResultList();

		return resultList;
	}
	
	
	
	public List<Bookmark> getBookmarks(User user,  Tag tag, PagePosition pagePosition) {

		Query query = em.createQuery("SELECT b FROM Bookmark b , IN (b.tags) t WHERE t = :tag and b.user=:user order by b.modified desc");
		query.setParameter("user", user);
		query.setParameter("tag", tag);
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

	public boolean fetchBookmarkFromURL(Bookmark bookmark, StringBuffer keywords, String address, String userAgent)
	{
		boolean b = true;
		
		try
		{
			fetchBookmarkFromURL1(bookmark, keywords, address, userAgent);
		} catch (Exception e)
		{
			b = false;
		}
		
		return b;
	}
	
	private void fetchBookmarkFromURL1(Bookmark bookmark, StringBuffer keywords, String address, String userAgent) throws IOException
	{
		Connection connection = Jsoup.connect(address);
		connection.validateTLSCertificates(false);
		if (userAgent != null)
		{
			connection.userAgent(userAgent);
		}
		
		Document doc = connection.get();
		String title = doc.title();
		String description = null;

		Elements metaTags = doc.getElementsByTag("meta");

		for (Element metaTag : metaTags)
		{
			String name = metaTag.attr("name");

			String content = metaTag.attr("content");

			if ("description".equals(name))
			{
				description = content;
			}

			if ("keywords".equals(name))
			{
				keywords.append(content);
			}

		}

		bookmark.setTitle(title);
		bookmark.setDescription(description);
	}
}
