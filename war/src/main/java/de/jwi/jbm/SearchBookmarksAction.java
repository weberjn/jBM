package de.jwi.jbm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.BookmarkManager;
import de.jwi.jbm.model.PagePosition;

public class SearchBookmarksAction implements Action
{
	private BookmarkManager bm;
	private int pageSize;

	public SearchBookmarksAction(BookmarkManager bm, int pageSize)
	{
		super();
		this.bm = bm;
		this.pageSize = pageSize;
	}

	static final String SEARCH_PAGE_PATTERN = "search(/(\\p{Alnum}+)/(\\d+))?";
	static final int GRP_SWORD = 2;
	static final int GRP_SPAGE = 3;

	public String run(HttpServletRequest request, User user, String cmd)
	{
		int bookmarksCount = 0;
		List<Bookmark> bookmarks = null;
		int page = 1;
		String text = "";

		Matcher matcher = Pattern.compile(SEARCH_PAGE_PATTERN).matcher(cmd);
		if (matcher.find())
		{
			String s = matcher.group(GRP_SWORD);
			if (s != null)
			{
				text = s;
			}
			s = matcher.group(GRP_SPAGE);
			if (s != null)
			{
				page = Integer.parseInt(s);
			}
		}

		String submit = request.getParameter("search");

		if (submit != null)
		{
			String s = request.getParameter("terms");
			text = s;
		}

		if (!text.matches("\\p{Alnum}+"))
		{
			return "rd:/bookmarks/list";
		}

		bookmarksCount = bm.getBookmarksCountForSearch(user, text);

		String pagePattern = "search/" + text + "/%s";

		PageNavigator navigator = new PageNavigator(new PagePosition(bookmarksCount, page, pageSize), 
				pagePattern, null, -1);
		
		bookmarks = bm.searchBookmarks(user, text, navigator.getPagePosition());

		request.setAttribute("query", text);
		request.setAttribute("navigator", navigator);
		request.setAttribute("bookmarks", bookmarks);

		return "/WEB-INF/listbookmarks.jsp";
	}
}
