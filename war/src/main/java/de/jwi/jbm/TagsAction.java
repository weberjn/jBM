package de.jwi.jbm;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import de.jwi.jbm.entities.Tag;
import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.BookmarkManager;

public class TagsAction implements Action
{
	private BookmarkManager bm;
	
	public TagsAction(BookmarkManager bm)
	{
		super();
		this.bm = bm;
	}
	
	public String run(HttpServletRequest request, User user, String cmd)
	{
		List<Tag> tags = bm.getAllTags(user);
		
		request.setAttribute("tags", tags);
		
		return "/WEB-INF/tags.jsp";
	}
}
