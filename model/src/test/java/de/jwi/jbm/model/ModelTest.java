package de.jwi.jbm.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.stream.XMLStreamException;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
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

//	@Rule
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

		emf = Persistence.createEntityManagerFactory("jBM-Test", p);
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
		boolean b;
		User user = um.createIfNotExists("weberjn");

		Bookmark bookmark = new Bookmark();
		bookmark.setAddress("http://www.h2database.com");
		bookmark.setTitle("H2 Database Engine");

		b = bm.addBookmark(user, bookmark);
		assertTrue(b);

		int count = bm.getBookmarksCount();
		assertEquals(count, 1);

		count = bm.getBookmarksCount(user);
		assertEquals(count, 1);

		String hash = bookmark.getHash();
		Bookmark b1 = bm.findBookmark(user, hash);
		assertNotNull(b1);
		
		Bookmark bookmark2 = new Bookmark();
		bookmark2.setAddress("http://www.h2database.com");
		bookmark2.setTitle("H2 Database Engine");
		b = bm.addBookmark(user, bookmark2);
		assertFalse(b);
		
		count = bm.getBookmarksCount(user);
		assertEquals(count, 1);
		
		User user2 = um.createIfNotExists("h2");
		count = bm.getBookmarksCount(user2);
		assertEquals(count, 0);

		bookmarkId = bookmark.getId();
	}

	
	@Test
	public void b2BookmarkUpdate() throws MalformedURLException
	{
		boolean b;
		
		User user = um.findUser("weberjn");
		assertNotNull(user);
		
		Bookmark bookmark = new Bookmark();
		bookmark.setAddress("http://gitblit.com/");
		bookmark.setTitle("Gitblit");
		b = bm.addBookmark(user, bookmark);
		assertTrue(b);
		Bookmark withChanges = new Bookmark();
		bm.copyTo(bookmark, withChanges);
		withChanges.setDescription("Github in Java");
		bm.updateBookmark(user, withChanges);
		assertEquals("Github in Java", bookmark.getDescription());
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
		bm.addTag(user, bookmark, "Database");
		
		List<Tag> tags = bookmark.getTags();
		assertTrue(2 == tags.size());
		
		bm.addTag(user, bookmark, "SQL");
		
		tags = bookmark.getTags();
		assertTrue(2 == tags.size());
	}

	@Test
	public void d1BookmarksWithTag() throws MalformedURLException
	{
		User user = um.findUser("weberjn");
	
		Tag tag = bm.findTag(user, "SQL");
		
		int bookmarksCount = bm.getBookmarksCount(user, tag);
		
		assertTrue(1 == bookmarksCount);
		
		PagePosition pagePosition = new PagePosition(bookmarksCount, 1, 2);
		
		List<Bookmark> bookmarks = bm.getBookmarks(user,  tag, pagePosition);
		
		assertTrue(1 == bookmarks.size());
	}
	
	@Test
	public void d1SearchBookmarks() throws MalformedURLException
	{
		User user = um.findUser("weberjn");
		
		int bookmarksCount = bm.getBookmarksCountForSearch(user, "Database");
		assertTrue(1 == bookmarksCount);	
		
		PagePosition pagePosition = new PagePosition(bookmarksCount, 1, 2);
		
		List<Bookmark> bookmarks = bm.searchBookmarks(user, "Database", pagePosition);
		assertTrue(1 == bookmarks.size());
	}


	@Test
	public void e1listAllTags()
	{
		int n = 0;
		
		User user = um.findUser("weberjn");
		
		List<Tag> allTags = bm.getAllTags(user);
		
		n = allTags.size();
		assertTrue(n == 2);
	}
	
	
	@Test
	public void h1removeBookmark() throws MalformedURLException
	{
		User user = um.findUser("weberjn");

		Bookmark b = bm.findBookmark(user, bookmarkId);
		assertTrue(b != null);
		
		boolean done = bm.removeBookmark(user, bookmarkId);
		
		assertTrue(done);
		
		b = bm.findBookmark(user, bookmarkId);
		
		assertTrue(b == null);
	}

	@Test
	public void k1updateTags() throws MalformedURLException
	{
		int n = 0;
		
		User user = um.findUser("weberjn");

		Bookmark bookmark = new Bookmark();
		bookmark.setAddress("http://tomcat.apache.org/");
		bookmark.setTitle("Apache Tomcat&reg; - Welcome!");
		bm.addBookmark(user, bookmark);
		
		n = bookmark.getTags().size();
		assertTrue(n == 0);
		
		String[] tagNames3 = {"Java Servlet","JavaServer Pages", "Java Expression Language"}; 
		
		bm.updateTags(user, bookmark, tagNames3);
		
		n = bookmark.getTags().size();
		assertTrue(n == 3);
		
		String[] tagNames4 = {"Java Servlet","JavaServer Pages", "Java Expression Language", "Java WebSocket"};
		bm.updateTags(user, bookmark, tagNames4);
		
		n = bookmark.getTags().size();
		assertTrue(n == 4);
		
		String[] tagNames2 = {"Java Servlet","JavaServer Pages"};
		bm.updateTags(user, bookmark, tagNames2);
		
		n = bookmark.getTags().size();
		assertTrue(n == 2);
	
		String[] tagNames2a = {"Apache","JavaServer Pages"};
		bm.updateTags(user, bookmark, tagNames2a);
		
		n = bookmark.getTags().size();
		assertTrue(n == 2);
	
		List<String> tagNamesList = new ArrayList<String>(n);
		
		for (Tag tag : bookmark.getTags())
		{
			tagNamesList.add(tag.getTag());
		}
		
		assertTrue(tagNamesList.contains("Apache"));
		assertTrue(tagNamesList.contains("JavaServer Pages"));

	
		String[] tagNames0 = {};
		bm.updateTags(user, bookmark, tagNames0);
		
		n = bookmark.getTags().size();
		assertTrue(n == 0);
	}

	@Test
	public void n1ApiAllBookmarks() throws XMLStreamException, IOException
	{
		User user = um.findUser("weberjn");

		
		APIManager api = new APIManager(bm);
		
		Writer w = new PrintWriter(System.out, true);
		
		api.fetchAllBookmarks(user, w);
		
		w.close();
		
		System.out.println("done **");
		
//		throw new RuntimeException();
	}
	
}
