package de.jwi.jbm.model;

public class PagePosition {

	private Integer first, current, last, pagesize;
	private int bookmarksCount;

	// all Positions 1..
	public PagePosition(int bookmarksCount, int newpos, int pagesize)
	{
		// 0
		// 1 1  
		// 2 1
		// 3 2
		// 4 2
		
		this.bookmarksCount = bookmarksCount;
		
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
		
	}
	
	public Integer getPagesize() {
		return pagesize;
	}

	public Integer getFirst() {
		if (bookmarksCount == 0 || 0 == current.compareTo(first))
		{
			return null;
		}
		return first;
	}

	public Integer getCurrent() {
		return current;
	}

	public Integer getLast() {
		if (bookmarksCount == 0 || 0 == current.compareTo(last))
		{
			return null;
		}
		return last;
	}

	public Integer getPrevious()
	{
		if (bookmarksCount == 0)
		{
			return null;
		}
		if (current > first)
		{
			return current - 1;
		}
		return null;
	}
	
	public Integer getNext()
	{
		if (bookmarksCount == 0)
		{
			return null;
		}

		if (current < last)
		{
			return current + 1;
		}
		return null;
	}

}
