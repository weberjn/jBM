package de.jwi.jbm;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.BookmarkManager;
import de.jwi.jbm.model.UserManager;

public class Controller extends HttpServlet
{
	String PAGESIZE_KEY = "jbm.pagesize";
	
	private static final Logger log = Logger.getLogger(Controller.class.getName());

	int pageSize = 20;

	ServletContext servletContext = null;
	protected EntityManagerFactory entityManagerFactory;

	private Properties properties;
	
	@Override
	public void init() throws ServletException
	{
		servletContext = getServletContext();
		
		Exception e = (Exception)servletContext.getAttribute(AppContextListener.EXCEPTION);
		if (e!=null)
		{
			throw new ServletException(e);
		}
		
		entityManagerFactory = (EntityManagerFactory)servletContext.getAttribute(AppContextListener.EMF);
		
		properties = (Properties)servletContext.getAttribute(AppContextListener.PROPS);

		pageSize = Integer.parseInt(properties.getProperty(PAGESIZE_KEY, "" + pageSize));
		
		super.init();
	}
	


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		StringBuffer requestURL = request.getRequestURL();

		String contextPath = request.getContextPath();

		String servlet = request.getServletPath().substring(1);

		System.out.println(servlet);

		String pathInfo = request.getPathInfo();

        request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");


		String cmd = null;

		if (pathInfo != null)
		{
			cmd = pathInfo.substring(1);
		}

		String forward = "/";

		String username = request.getUserPrincipal().getName();

		if ("profile".equals(servlet) && "logout".equals(cmd))
		{
			request.getSession().invalidate();
			forward = "rd:/";
		} else
		{

			EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = null;
			transaction = entityManager.getTransaction();
			transaction.begin();

			UserManager um = new UserManager(entityManager);
			BookmarkManager bm = new BookmarkManager(entityManager);

			User user = um.createIfNotExists(username);
			Action action = null;
			
			try
			{
				if ("profile".equals(servlet))
				{
					action = new ProfileAction(um);
				}
				if ("about".equals(servlet))
				{
					action = new AboutAction();
				}
				if ("bookmarks".equals(servlet) && (cmd != null))
				{
					if (cmd.startsWith("list"))
					{
						action = new ListBookmarksAction(bm, pageSize);
					}
					if (cmd.startsWith("search"))
					{
						action = new SearchBookmarksAction(bm, pageSize);
					}
				}
				if ("bookmark".equals(servlet) && (cmd != null))
				{
					action = new BookmarkAction(bm);
				}
				if ("tags".equals(servlet))
				{
					action = new TagsAction(bm);
				}
				forward = action.run(request, user, cmd);

				
			} catch (ActionException e)
			{
				log.log(Level.SEVERE, e.getMessage(), e);

				transaction.rollback();
				
				entityManager.close();
					
				throw new ServletException(e);
			}
			
			transaction.commit();
			entityManager.close();

			request.setAttribute("context", contextPath);
			request.setAttribute("user", user);
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

}