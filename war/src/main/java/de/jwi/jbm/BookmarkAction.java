package de.jwi.jbm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.Tag;
import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.BookmarkManager;

public class BookmarkAction implements Action
{
	private BookmarkManager bm;

	public BookmarkAction(BookmarkManager bm)
	{
		super();
		this.bm = bm;
	}

	public String run(HttpServletRequest request, User user, String cmd)  throws ActionException
	{
		String forward = "rd:/bookmarks/list";
	
		try
		{
			String submit = request.getParameter("deleteBookmark");
			if (submit != null)
			{
				forward = deleteBookmark(request, user, cmd);
				return forward;
			}
				
			if ("add".equals(cmd))
			{
				forward = addBookmark(request, user);
			}
			if (cmd.startsWith("edit"))
			{
				forward = editBookmark(request, user, cmd);
			}
		} catch (IOException e)
		{
			throw new ActionException(e);
		}
		
		return forward;
	}
	
	private String deleteBookmark(HttpServletRequest request, User user, String cmd) throws IOException
	{
		String bmID = request.getParameter("bmID");
		int bookmarkId = Integer.parseInt(bmID);
		
		boolean done = bm.removeBookmark(user, bookmarkId);
		
		return "rd:/bookmarks/list";
	}
	
	private String addBookmark(HttpServletRequest request, User user) throws IOException
	{
		String submit = request.getParameter("addBookmark");

		if (submit != null && request.getAttribute("bm") == null)
		{
			if ("get it".equals(submit))
			{
				String address = request.getParameter("address");

				Bookmark bookmark = new Bookmark();
				bookmark.setAddress(address);

				StringBuffer keywords = new StringBuffer();

				bm.fetchBookmarkFromURL(bookmark, keywords, address);

				request.setAttribute("bm", bookmark);
				request.setAttribute("keywords", keywords.toString());

				return "/bookmark/add";
			}

			Bookmark b = new Bookmark();
			fillBookmark(request, user, bm, b);

			bm.addBookmark(user, b);

			return "rd:/bookmarks/list";
		}

		request.setAttribute("bmop", "add");
		request.setAttribute("cmd", "add");

		return "/WEB-INF/addbookmark.jsp";
	}

	private void fillBookmark(HttpServletRequest request, User user, BookmarkManager bm,
			Bookmark bookmark)
	{
		String address = request.getParameter("address");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String tags = request.getParameter("tags");

		bookmark.setAddress(address);
		bookmark.setTitle(title);
		bookmark.setDescription(description);

		if (tags != null)
		{
			String[] tagNames = tags.split("\\s*,\\s*");
			
			bm.updateTags(user, bookmark, tagNames);
		}
	}

	private String editBookmark(HttpServletRequest request, User user, String cmd) throws IOException
	{

		String submit = request.getParameter("addBookmark");

		Matcher matcher = Pattern.compile("edit/(\\d+)").matcher(cmd);
		
		StringBuffer keywords = new StringBuffer();

		int id = -1;

		if (matcher.find())
		{
			String s = matcher.group(1);
			id = Integer.parseInt(s);
		}

		Bookmark bookmark = bm.findBookmark(user, id);

		if (bookmark == null)
		{
			return "rd:/bookmarks/list";
		}

		if (submit != null)
		{
			if ("get it".equals(submit))
			{
				String address = request.getParameter("address");
				bm.fetchBookmarkFromURL(bookmark, keywords, address);
			} else
			{
				fillBookmark(request, user, bm, bookmark);
				bm.touchBookmark(bookmark);

				return "rd:/bookmarks/list";
			}
		}
		else
		{
			Iterator<Tag> it = bookmark.getTags().iterator();
			while (it.hasNext())
			{
				Tag tag = it.next();
				keywords.append(tag.getTag());
				if ((it.hasNext()))
				{
					keywords.append(',');
				}
			}
		}

		request.setAttribute("bm", bookmark);
		request.setAttribute("keywords", keywords.toString());

		request.setAttribute("bmop", "edit");

		request.setAttribute("cmd", cmd);

		return "/WEB-INF/addbookmark.jsp";
	}

}
