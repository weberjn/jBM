package de.jwi.jbm.model;

public class PagePosition {

	private int first, current, last, pagesize;
	private int bookmarksCount;
	private int pageCount;

	// all Positions 1..
	public PagePosition(int bookmarksCount, int newpos, int pagesize)
	{
		// 0
		// 1 1  
		// 2 1
		// 3 2
		// 4 2
		
		this.bookmarksCount = bookmarksCount;
	
		pageCount = (bookmarksCount + pagesize - 1) / pagesize;
		
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
	
	public int getBookmarksCount()
	{
		return bookmarksCount;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public int getPagesize() {
		return pagesize;
	}

	public int getCurrent() {
		return current;
	}
	
	// TODO
	public String[] getPaginationPoints()
	{
		int n = 0;
		if (pageCount < 2)
		{
			return null;
		}
		return null;
	}
	
	public int getFirst() {
		if (bookmarksCount == 0 || current == first)
		{
			return -1;
		}
		return first;
	}



	public int getLast() {
		if (bookmarksCount == 0 || current == last)
		{
			return -1;
		}
		return last;
	}

	public int getPrevious()
	{
		if (bookmarksCount == 0)
		{
			return -1;
		}
		if (current > first)
		{
			return current - 1;
		}
		return -1;
	}
	
	public int getNext()
	{
		if (bookmarksCount == 0)
		{
			return -1;
		}

		if (current < last)
		{
			return current + 1;
		}
		return -1;
	}

}
