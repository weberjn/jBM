package de.jwi.jbm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.User;

public class Controller extends HttpServlet {

	private static final Logger log = Logger.getLogger(Controller.class.getName());

	ServletContext servletContext = null;
	private EntityManagerFactory entityManagerFactory;

	@Override
	public void init() throws ServletException {
		super.init();

		servletContext = getServletContext();

		entityManagerFactory = Persistence.createEntityManagerFactory("jbm");

		log.info(entityManagerFactory.toString());
	}

	@Override
	public void destroy() {
		super.destroy();
		entityManagerFactory.close();
		entityManagerFactory = null;
	}

	void x() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		String q = "SELECT b FROM Bookmark b";

		Query query = entityManager.createQuery(q);

		List<Bookmark> resultList = query.getResultList();

		System.out.println(resultList);

		entityManager.close();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String contextPath = request.getContextPath();

		String servlet = request.getServletPath().substring(1);

		System.out.println(servlet);

		String pathInfo = request.getPathInfo();

		String cmd = null;

		if (pathInfo != null) {
			cmd = pathInfo.substring(1);
		}

		String forward = "index.jsp";

		String username = request.getUserPrincipal().getName();

		if ("profile".equals(servlet) && "logout".equals(cmd)) {
			request.getSession().invalidate();
		} else {

			EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();

			if ("profile".equals(servlet)) {
				forward = editProfile(request, entityManager);
			}

			if ("list".equals(cmd)) {
				forward = listBookmarks(request, entityManager);
			}
			if ("add".equals(cmd)) {
				forward = showAddBookmark(request, entityManager);
			}

			transaction.commit();
			entityManager.close();

			request.setAttribute("context", contextPath);

			request.setAttribute("username", username);
		}

		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(forward);

		requestDispatcher.forward(request, response);
	}

	private String showAddBookmark(HttpServletRequest request, EntityManager entityManager) {
		return "/addbookmark.jsp";
	}

	private String addBookmark(HttpServletRequest request, EntityManager entityManager) throws MalformedURLException {
		Map<String, String[]> parameterMap = request.getParameterMap();

		String address = request.getParameter("address");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String tags = request.getParameter("tags");

		BookmarkManager bm = new BookmarkManager(entityManager);

		bm.addBookmark("j@jwi.de", new URL(address), title, description, tags);

		return listBookmarks(request, entityManager);
	}

	private String editProfile(HttpServletRequest request, EntityManager entityManager) throws MalformedURLException {

		String username = request.getUserPrincipal().getName();

		Boolean saved = new Boolean(false);

		UserManager um = new UserManager(entityManager);

		User user = um.findUser(username);

		if (user == null) {
			user = um.createUser(username);
		}

		if (request.getParameter("submitted") != null) {
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

		return "/profile.jsp";
	}

	private String listBookmarks(HttpServletRequest request, EntityManager entityManager) throws MalformedURLException {
		String addsubmit = request.getParameter("addBookmark");

		if (addsubmit != null) {
			String address = request.getParameter("address");
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String tags = request.getParameter("tags");

			BookmarkManager bm = new BookmarkManager(entityManager);

			bm.addBookmark("j@jwi.de", new URL(address), title, description, tags);
		}

		return "/bookmark.jsp";
	}

}