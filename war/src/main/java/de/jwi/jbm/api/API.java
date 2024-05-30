package de.jwi.jbm.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import de.jwi.jbm.ActionException;
import de.jwi.jbm.AppContextListener;
import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.APIManager;
import de.jwi.jbm.model.BookmarkManager;
import de.jwi.jbm.model.UserManager;

public class API extends HttpServlet
{

	private static final Logger log = Logger.getLogger(API.class.getName());

	private EntityManagerFactory entityManagerFactory;

	@Override
	public void init() throws ServletException
	{
		ServletContext servletContext = getServletContext();
		
		Exception e = (Exception)servletContext.getAttribute(AppContextListener.EXCEPTION);
		if (e!=null)
		{
			throw new ServletException(e);
		}
		
		entityManagerFactory = (EntityManagerFactory)servletContext.getAttribute(AppContextListener.EMF);

		super.init();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
        request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		
		String pathInfo = request.getPathInfo();
    
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		String username = request.getUserPrincipal().getName();
		
		String cmd = null;
		APIAction action = null;
		String result = "<result code=\"something went wrong\" />";

		if (pathInfo != null)
		{
			Matcher matcher = Pattern.compile("/posts/(\\w+)").matcher(pathInfo);
			if (matcher.find())
			{
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();

				UserManager um = new UserManager(entityManager);
				BookmarkManager bm = new BookmarkManager(entityManager);
				APIManager am = new APIManager(bm);

				User user = um.createIfNotExists(username);
				
				cmd = matcher.group(1);
				
				if ("add".equals(cmd))
				{
					action = new AddAction(am);
				}
				if ("delete".equals(cmd))
				{
					action = new DeleteAction(am);
				}
				if ("all".equals(cmd))
				{
					action = new AllBookmarksAction(am);
				}

				try
				{
					action.run(request, response, user, cmd);
					
					transaction.commit();
					entityManager.close();
				} catch (ActionException e)
				{
					log.log(Level.SEVERE, e.getMessage(), e);

					out.println(result);

					transaction.rollback();
					entityManager.close();
					
				}
			}
		}
	}

}
