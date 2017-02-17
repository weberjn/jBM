package de.jwi.jbm;


import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.jwi.jbm.entities.Bookmark;


public class Controller extends HttpServlet {

//	@PersistenceContext(name="jbm")
//	private EntityManager em;

	ServletContext servletContext = null;
	private EntityManagerFactory entityManagerFactory;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		servletContext = getServletContext();
		
		entityManagerFactory = Persistence.createEntityManagerFactory("jbm");
			
		System.out.println(entityManagerFactory);	
		
	}

	
	
	@Override
	public void destroy() {
		super.destroy();
		entityManagerFactory.close();
		entityManagerFactory = null;
	}



	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		String q = "SELECT b FROM Bookmark b";

		Query query = entityManager.createQuery(q);

		List<Bookmark> resultList = query.getResultList();

		System.out.println(resultList);

		response.getWriter().print(resultList);
		
		entityManager.close();
		
		
		String servletPath = request.getServletPath();
				
		System.out.println(servletPath);
		
		String cmd = servletPath.substring(1);
		
		String forward = "index.jsp";

		if ("bookmarks".equals(cmd))
		{
			forward = listBookmarks();
		}
		if ("addbookmark".equals(cmd))
		{
			forward = addBookmark();
		}
		
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(forward);

		requestDispatcher.forward(request, response);
	}

	private String addBookmark()
	{
		return "";
	}
	
	private String listBookmarks()
	{
		return "/bookmark.jsp";
	}
	
} 