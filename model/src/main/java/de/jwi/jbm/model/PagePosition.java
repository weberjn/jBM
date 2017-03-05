package de.jwi.jbm.model;

public class PagePosition {

	private int first, current, last, pagesize;
	private int bookmarksCount;
	private int tagID = -1;
	private String linkFormat;
	private String linkFormatTag;

	// all Positions 1..
	public PagePosition(int bookmarksCount, int newpos, int pagesize, int tagID, String linkFormat, String linkFormatTag)
	{
		// 0
		// 1 1  
		// 2 1
		// 3 2
		// 4 2
		
		this.bookmarksCount = bookmarksCount;
		this.linkFormat = linkFormat;
		this.linkFormatTag = linkFormatTag;
		
		int pageCount = (bookmarksCount + pagesize - 1) / pagesize;
		
		first = 1;
		last = pageCount;
		current = newpos; 
		
		if (current > last)
		{
			current = last;
		}
		
		if (current < first)
		{
			current = first;
		}
		
		this.pagesize = pagesize;
		this.tagID = tagID;
	}
	
	public int getPagesize() {
		return pagesize;
	}

	private String makeLink(int page)
	{
		String s;
		
		if (tagID == -1)
		{
			s = String.format(linkFormat, page);
		}
		else
		{
			s = String.format(linkFormatTag, page, tagID);
		}
		return s;
	}
	
	public String getFirst() {
		if (bookmarksCount == 0 || current == first)
		{
			return null;
		}
		
		String s = makeLink(first);
		return s;
	}

	public Integer getCurrent() {
		return current;
	}

	public String getLast() {
		if (bookmarksCount == 0 || current == last)
		{
			return null;
		}
		
		String s = makeLink(last);
		return s;
	}

	public String getPrevious()
	{
		if (bookmarksCount == 0)
		{
			return null;
		}
		if (current > first)
		{
			String s = makeLink(current - 1);
			return s;
		}
		return null;
	}
	
	public String getNext()
	{
		if (bookmarksCount == 0)
		{
			return null;
		}

		if (current < last)
		{
			String s = makeLink(current + 1);
			return s;
		}
		return null;
	}

}
