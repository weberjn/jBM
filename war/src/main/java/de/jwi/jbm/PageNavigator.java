package de.jwi.jbm;

import de.jwi.jbm.model.PagePosition;

public class PageNavigator {

	private int tagID = -1;
	private String linkFormat;
	private String linkFormatTag;
	private PagePosition pagePosition;


	// all Positions 1..
	public PageNavigator(PagePosition pagePosition, String linkFormat, String linkFormatTag, int tagID)
	{
		// 0
		// 1 1  
		// 2 1
		// 3 2
		// 4 2
		
		this.pagePosition = pagePosition;
		this.linkFormat = linkFormat;
		this.linkFormatTag = linkFormatTag;
		
		this.tagID = tagID;
	}
	
	public PagePosition getPagePosition()
	{
		return pagePosition;
	}

	
	// TODO
	public String[] getPaginationPoints()
	{
		return null;
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
	
	public Integer getBookmarksCount()
	{
		return new Integer(pagePosition.getBookmarksCount());
	}
	
	public Integer getPageCount()
	{
		return new Integer(pagePosition.getPageCount());
	}
	
	public Integer getCurrent() {
		return new Integer(pagePosition.getCurrent());
	}
	
	public String getFirst() {
		int first = pagePosition.getFirst();
		if (first == -1)
		{
			return null;
		}
		
		String s = makeLink(first);
		return s;
	}



	public String getLast() {
		int last = pagePosition.getLast();
		if (last == -1)
		{
			return null;
		}
		
		String s = makeLink(last);
		return s;
	}

	public String getPrevious()
	{
		int previous = pagePosition.getPrevious();
		if (previous == -1)
		{
			return null;
		}

		String s = makeLink(previous);
		return s;
	}
	
	public String getNext()
	{
		int next = pagePosition.getNext();
		if (next == -1)
		{
			return null;
		}

		String s = makeLink(next);
		return s;
	}

}
