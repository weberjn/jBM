package de.jwi.jbm.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.Tag;
import de.jwi.jbm.entities.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModelTest
{
	static Connection connection;

	protected static EntityManagerFactory emf;
	static EntityManager em;
	static EntityTransaction transaction;
	static UserManager um = new UserManager(em);
	static BookmarkManager bm = new BookmarkManager(em);
	private static Integer bookmarkId;

	@Rule
    public SQLBrowserOnFailed rule = new SQLBrowserOnFailed();
	
	@BeforeClass
	public static void init() throws SQLException, IOException
	{
		EntityManager em;

		Properties p = new Properties();
		p.setProperty("javax.persistence.jdbc.url", "jdbc:h2:mem:jBM");
		p.setProperty("javax.persistence.jdbc.driver", "org.h2.Driver");
		p.setProperty("eclipselink.target-database",
				"org.eclipse.persistence.platform.database.H2Platform");

		emf = Persistence.createEntityManagerFactory("jBM", p);
		em = emf.createEntityManager();

		em.getTransaction().begin();

		connection = em.unwrap(java.sql.Connection.class);

		URL url = ModelTest.class.getResource("/tables-postgresql.sql");
		System.out.println(url);
		InputStream is = url.openStream();
		Reader r = new InputStreamReader(is);

		RunScript.execute(connection, r);

		r.close();

		em.getTransaction().commit();
	}

	@AfterClass
	public static void tearDown()
	{
		emf.close();
	}
	
	@Before
	public void beginTX()
	{
		em = emf.createEntityManager();

		transaction = em.getTransaction();
		transaction.begin();
		
		um = new UserManager(em);
		bm = new BookmarkManager(em);

	}

	@After
	public void commitTX()
	{
		transaction.commit();
		
		em.clear();
		em.close();

		um = null;
		bm = null;
	}

	@Test
	public void a0User()
	{

		UserManager um = new UserManager(em);

		User user0 = um.findUser("weberjn");

		User user = um.createUser("weberjn");

		User user2 = um.findUser("weberjn");

		assertEquals(user.getName(), user2.getName());
	}

	@Test
	public void b1BookmarkCreate() throws MalformedURLException
	{
		User user = um.createIfNotExists("weberjn");

		Bookmark bookmark = new Bookmark();
		bookmark.setAddress("http://www.h2database.com");
		bookmark.setTitle("H2 Database Engine");

		bm.addBookmark(user, bookmark);

		int count = bm.getBookmarksCount();
		assertEquals(count, 1);

		count = bm.getBookmarksCount(user);
		assertEquals(count, 1);

		User user2 = um.createIfNotExists("h2");
		count = bm.getBookmarksCount(user2);
		assertEquals(count, 0);

		bookmarkId = bookmark.getId();
	}

	@Test
	public void c1TagsAdd() throws MalformedURLException
	{
		User user = um.findUser("weberjn");
		assertNotNull(user);
		
		Bookmark bookmark = bm.findBookmark(user, bookmarkId);
		
		assertNotNull(bookmark);
		
		bm.addTag(user, bookmark, "SQL");
		bm.addTag(user, bookmark, "database");
		
		List<Tag> tags = bookmark.getTags();
		assertTrue(2 == tags.size());
	}

	@Test
	public void d1BookmarksWithTag() throws MalformedURLException
	{
		User user = um.findUser("weberjn");
	
		Tag tag = bm.findTag(user, "SQL");
		
		int bookmarksCount = bm.getBookmarksCount(user, tag);
		
		assertTrue(1 == bookmarksCount);
		
		PagePosition pagePosition = new PagePosition(bookmarksCount, 1, 2, tag.getId(), null, null);
		
		List<Bookmark> bookmarks = bm.getBookmarks(user,  tag, pagePosition);
		
		assertTrue(1 == bookmarks.size());
	}

	@Test
	public void d1removeBookmark() throws MalformedURLException
	{
		User user = um.findUser("weberjn");

		Bookmark b = bm.findBookmark(user, bookmarkId);
		assertTrue(b != null);
		
		boolean done = bm.removeBookmark(user, bookmarkId);
		
		assertTrue(done);
		
		b = bm.findBookmark(user, bookmarkId);
		
		assertTrue(b == null);
//		throw new RuntimeException();		
	}
	
}
