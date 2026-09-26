package de.jwi.jbm.api;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import de.jwi.jbm.ActionException;
import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.APIManager;

/*
 * Web Share Target API 
 * names parameters different to delicious API
 * so we map them
 * Also, there are only 2 parameters.
 */

public class ShareAction implements APIAction
{
	private APIManager am;
	
	public ShareAction(APIManager am)
	{
		super();
		this.am = am;
	}
	
	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, User user,
			String cmd) throws ActionException
	{
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		String url = request.getParameter("text");
		String description = request.getParameter("title");
		String extended = null;
		String tags  = "Web Share";
		String replace  = null;
		String status  = "2";
		
		if (url == null)
		{
			throw new ActionException("no URL share");
		}
		
		try
		{
			am.addBookmark(user, response.getWriter(), url, description, extended, tags, status, replace);
		} catch (XMLStreamException | IOException e)
		{
			throw new ActionException(e);
		}
	}
}
