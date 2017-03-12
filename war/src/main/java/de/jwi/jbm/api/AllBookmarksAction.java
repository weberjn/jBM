package de.jwi.jbm.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import de.jwi.jbm.ActionException;
import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.APIManager;

public class AllBookmarksAction implements APIAction
{
	private APIManager am;
	
	public AllBookmarksAction(APIManager am)
	{
		super();
		this.am = am;
	}
	
	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, User user,
			String cmd) throws ActionException
	{
		try
		{
			am.fetchAllBookmarks(user, response.getWriter());
		} catch (XMLStreamException | IOException e)
		{
			throw new ActionException(e);
		}
	}
}
