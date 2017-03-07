package de.jwi.jbm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.Tag;
import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.BookmarkManager;
import de.jwi.jbm.model.PagePosition;

public class ListBookmarksAction implements Action
{
	private BookmarkManager bm;
	private int pageSize;

	public ListBookmarksAction(BookmarkManager bm, int pageSize)
	{
		super();
		this.bm = bm;
		this.pageSize = pageSize;
	}

	static final String PAGE_PATTERN = "list(/(\\d+)(/(\\d+))?)?";

	static final int GRP_PAGE = 2;
	static final int GRP_TAG = 4;

	static final String LINK_FORMAT = "list/%d";
	static final String LINK_FORMAT_TAG = "list/%d/%d";

	public String run(HttpServletRequest request, User user, String cmd)
	{

		int page = 1;
		int tagID = -1;
		Tag tag = null;
		int bookmarksCount = 0;
		List<Bookmark> bookmarks = null;

		Matcher matcher = Pattern.compile(PAGE_PATTERN).matcher(cmd);
		if (matcher.find())
		{
			String s = matcher.group(GRP_PAGE);
			if (s != null)
			{
				page = Integer.parseInt(s);
			}
			s = matcher.group(GRP_TAG);
			if (s != null)
			{
				tagID = Integer.parseInt(s);
			}
		}

		if (tagID != -1)
		{
			tag = bm.findTag(tagID);
			bookmarksCount = bm.getBookmarksCount(user, tag);
		} else
		{
			bookmarksCount = bm.getBookmarksCount(user);
		}

		PagePosition pagePosition = new PagePosition(bookmarksCount, page, pageSize, tagID,
				LINK_FORMAT, LINK_FORMAT_TAG);

		if (tagID != -1)
		{
			bookmarks = bm.getBookmarks(user, tag, pagePosition);
		} else
		{
			bookmarks = bm.getBookmarks(user, pagePosition);
		}

		request.setAttribute("pagePosition", pagePosition);
		request.setAttribute("bookmarksCount", new Integer(bookmarksCount));
		request.setAttribute("bookmarks", bookmarks);
		request.setAttribute("tag", tag);

		return "/WEB-INF/listbookmarks.jsp";
	}

}
