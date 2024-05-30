package de.jwi.jbm.api;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

		String url = request.getParameter("url");
		String description = request.getParameter("description");
		String extended = request.getParameter("extended");
		String tags  = request.getParameter("tags");
		String replace  = request.getParameter("replace");
		String status  = request.getParameter("status");
		/*
		url:          http://www.foxnews.com
		description:  Fox News - Breaking News Updates | Latest News Headlines | Photos & News Videos
		extended:     Breaking News, Latest News and Current News from FOXNews.com. Breaking news and video. Latest Current
		              News: U.S., World, Entertainment, Health, Business, Technology, Politics, Sports.
		tags:         Breaking News, Latest News and Current News from FOXNews.com. Breaking news and video. Latest Current
		              News: U.S., World, Entertainment, Health, Business, Technology, Politics, Sports.
		status:       2
		replace:      yes
*/
		
		try
		{
			am.addBookmark(user, response.getWriter(), url, description, extended, tags, status, replace);
		} catch (XMLStreamException | IOException e)
		{
			throw new ActionException(e);
		}
	}
}
