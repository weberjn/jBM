package de.jwi.jbm;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.User;

public class Controller extends HttpServlet
{

	private static final Logger log = Logger.getLogger(Controller.class.getName());

	int PAGESIZE = 2;

	private static final String PROPERTIES = "/WEB-INF/jBM.properties";
	private static final String CUSTOMPROPERTIES = "/jBM-custom.properties";
	private static final String VERSIONPROPERTIES = "/version.properties";

	ServletContext servletContext = null;
	private EntityManagerFactory entityManagerFactory;

	private Properties properties;

	@Override
	public void init() throws ServletException
	{
		super.init();

		properties = new Properties();

		try
		{
			URL url = getServletContext().getResource(PROPERTIES);
			InputStream is = url.openStream();
			properties.load(is);
			is.close();

			url = Controller.class.getResource(CUSTOMPROPERTIES);
			if (url != null)
			{
				log.info("loading custom Properties from " + url);

				Properties properties2 = new Properties();
				is = url.openStream();
				properties2.load(is);
				is.close();
				properties.putAll(properties2);
			}

			url = Controller.class.getResource(VERSIONPROPERTIES);
			if (url != null)
			{
				Properties properties2 = new Properties();
				is = url.openStream();
				properties2.load(is);
				is.close();
				properties.putAll(properties2);
			}

		} catch (Exception e)
		{
			throw new ServletException(e);
		}

		servletContext = getServletContext();

		entityManagerFactory = Persistence.createEntityManagerFactory("jBM", properties);

		log.info(entityManagerFactory.toString());
	}

	@Override
	public void destroy()
	{
		super.destroy();
		entityManagerFactory.close();
		entityManagerFactory = null;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{

		String contextPath = request.getContextPath();

		String servlet = request.getServletPath().substring(1);

		System.out.println(servlet);

		String pathInfo = request.getPathInfo();

		String cmd = null;

		if (pathInfo != null)
		{
			cmd = pathInfo.substring(1);
		}

		String forward = "index.jsp";

		String username = request.getUserPrincipal().getName();

		if ("profile".equals(servlet) && "logout".equals(cmd))
		{
			request.getSession().invalidate();
		} else
		{

			EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();

			UserManager um = new UserManager(entityManager);
			BookmarkManager bm = new BookmarkManager(entityManager);

			User user = um.createIfNotExists(username);

			if ("profile".equals(servlet))
			{
				forward = editProfile(request, um, user);
			}

			if ("bookmark".equals(servlet) && (cmd != null))
			{
				if (cmd.startsWith("list"))
				{
					forward = listBookmarks(request, user, bm, cmd);
				}
				if ("add".equals(cmd))
				{
					forward = addBookmark(request, user, bm);
				}
				if (cmd.startsWith("edit"))
				{
					forward = editBookmark(request, user, bm, cmd);
				}
			}
			transaction.commit();
			entityManager.close();

			request.setAttribute("context", contextPath);

			request.setAttribute("username", username);
		}

		request.setAttribute("version", properties.getProperty("version"));
		request.setAttribute("builddate", properties.getProperty("builddate"));
		
		if (forward.startsWith("rd:"))
		{
			forward = contextPath + forward.substring(3);
			response.sendRedirect(forward);
			return;
		}

		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(forward);

		requestDispatcher.forward(request, response);
	}

	private String addBookmark(HttpServletRequest request, User user, BookmarkManager bm) throws IOException
	{
		String submit = request.getParameter("addBookmark");
		
		if (submit != null && request.getAttribute("bm") == null)
		{
			if ("get it".equals(submit))
			{
				String address = request.getParameter("address");

				Bookmark bookmark = new Bookmark();
				bookmark.setAddress(address);
				
				bm.fetchBookmarkFromURL(bookmark, address);
				
				request.setAttribute("bm", bookmark);

				return "/bookmark/add";
			}
			
			Bookmark b = new Bookmark(); 
			fillBookmark(request, b);
			
			bm.addBookmark(user, b);
			
			return "rd:/bookmark/list";
		}
		
		request.setAttribute("bmop", "add");
		request.setAttribute("cmd", "add");

		return "/WEB-INF/addbookmark.jsp";
	}

	private String editBookmark(HttpServletRequest request, User user, BookmarkManager bm, String cmd)
			throws IOException
	{

		String submit = request.getParameter("addBookmark");

		Matcher matcher = Pattern.compile("edit/(\\d+)").matcher(cmd);

		int id = 0;

		if (matcher.find())
		{
			String s = matcher.group(1);
			id = Integer.parseInt(s);
		}

		Bookmark bookmark = bm.findBookmark(user, id);

		if (bookmark == null)
		{
			return "rd:/bookmark/list";
		}
		
		if (submit != null)
		{
			if ("get it".equals(submit))
			{
				String address = request.getParameter("address");

				bm.fetchBookmarkFromURL(bookmark, address);
				
				request.setAttribute("bm", bookmark);
			}
			else
			{
				fillBookmark(request, bookmark);
				bm.touchBookmark(bookmark);
			
				return "rd:/bookmark/list";
			}
		}


		request.setAttribute("bm", bookmark);

		request.setAttribute("bmop", "edit");
		
		request.setAttribute("cmd", cmd);

		return "/WEB-INF/addbookmark.jsp";
	}

	private void fillBookmark(HttpServletRequest request, Bookmark bm)
	{
		String address = request.getParameter("address");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String tags = request.getParameter("tags");

		bm.setAddress(address);
		bm.setTitle(title);
		bm.setDescription(description);
	}
	
	
	private String listBookmarks(HttpServletRequest request, User user, BookmarkManager bm, String cmd)
			throws IOException
	{

		int page = 1;

		Matcher matcher = Pattern.compile("list/(\\d+)").matcher(cmd);
		if (matcher.find())
		{
			String s = matcher.group(1);
			page = Integer.parseInt(s);
		}

		Long bookmarksCount = bm.getBookmarksCount(user);
		request.setAttribute("bookmarksCount", bookmarksCount);

		PagePosition pagePosition = new PagePosition(bookmarksCount.intValue(), page, PAGESIZE);

		List<Bookmark> bookmarks = bm.getBookmarks(user, pagePosition);

		request.setAttribute("pagePosition", pagePosition);

		request.setAttribute("bookmarks", bookmarks);

		return "/WEB-INF/listbookmarks.jsp";
	}

	private String editProfile(HttpServletRequest request, UserManager um, User user) throws MalformedURLException
	{

		Boolean saved = new Boolean(false);

		if (request.getParameter("submitted") != null)
		{
			String email = request.getParameter("pMail");
			String name = request.getParameter("pName");
			String homepage = request.getParameter("pPage");
			String content = request.getParameter("pDesc");

			user.setEmail(email);
			user.setName(name);
			user.setHomepage(homepage);
			user.setContent(content);

			um.updateTimeStamp(user);

			saved = new Boolean(true);
		}

		request.setAttribute("user", user);

		request.setAttribute("saved", saved);

		return "/WEB-INF/profile.jsp";
	}

}