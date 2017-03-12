package de.jwi.jbm.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import de.jwi.jbm.ActionException;
import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.APIManager;

public class AddAction implements APIAction
{
	private APIManager am;
	
	public AddAction(APIManager am)
	{
		super();
		this.am = am;
	}
	
	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, User user,
			String cmd) throws ActionException
	{
		
//		url: http://www.zeit.de/index
//			description: ZEIT ONLINE | Nachrichten, HintergrÃ¼nde und Debatten
//			extended:
//			tags:
//			status: 2
//			

		String url = request.getParameter("url");
		String description = request.getParameter("description");
		String tags  = request.getParameter("tags");
		
		try
		{
			am.addBookmark(user, response.getWriter(), url, description, tags);
		} catch (XMLStreamException | IOException e)
		{
			throw new ActionException(e);
		}
	}
}
