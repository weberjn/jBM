package de.jwi.jbm;

public class PagePosition {

	private Integer first, current, last, pagesize;

	
	public PagePosition(int bookmarksCount, int current, int pagesize)
	{
		// 0
		// 1 1  
		// 2 1
		// 3 2
		
		int pc = bookmarksCount / pagesize + 1;
		
		this.current = current; 
		
		if (this.current > pc)
		{
			this.current = pc;
		}
		
		this.pagesize = pagesize;
		
		first = 1;
		last = pc;
	}
	
	public Integer getPagesize() {
		return pagesize;
	}

	public Integer getFirst() {
		return first;
	}

	public Integer getCurrent() {
		return current;
	}

	public Integer getLast() {
		return last;
	}

	public Integer getPrevious()
	{
		if (current > first)
		{
			return current - 1;
		}
		return null;
	}
	
	public Integer getNext()
	{
		if (current < last)
		{
			return current + 1;
		}
		return null;
	}

}
