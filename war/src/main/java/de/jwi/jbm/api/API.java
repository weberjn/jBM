package de.jwi.jbm.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.jwi.jbm.AboutAction;
import de.jwi.jbm.Action;
import de.jwi.jbm.ActionException;
import de.jwi.jbm.Controller;
import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.APIManager;
import de.jwi.jbm.model.BookmarkManager;
import de.jwi.jbm.model.UserManager;

public class API extends Controller
{

	private static final Logger log = Logger.getLogger(API.class.getName());
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		StringBuffer requestURL = request.getRequestURL();
		System.out.printf("requestURL: %s%n",requestURL);
		
		String contextPath = request.getContextPath();
		System.out.printf("contextPath: %s%n",contextPath);
		
		String servlet = request.getServletPath().substring(1);
		System.out.printf("servlet: %s%n",servlet);
		
		String pathInfo = request.getPathInfo();
		System.out.printf("pathInfo: %s%n",pathInfo);
		
		String queryString = request.getQueryString();
		System.out.printf("queryString: %s%n",queryString);
		


		for (String key : Collections.list(request.getParameterNames()))
		{
			System.out.printf("%s: %s%n", key, request.getParameter(key));
		}
    
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
