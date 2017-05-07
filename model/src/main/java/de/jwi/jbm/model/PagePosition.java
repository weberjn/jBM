package de.jwi.jbm.model;

import java.util.ArrayList;
import java.util.List;

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
	
	/*
	   1 .. 10 .. 20 21 22 23 24 .. 39 .. 50
	   
	   if pagecount <= 7 : no gap
	   
	   if gap > 10 : add position into middle of gap
	   
	   gap is 0, current is negative
	 */
	public List<Integer> getPaginationPoints()
	{
		int GAP = 0;
		
		List<Integer> points = new ArrayList<Integer>(13);
		int m;
		
		if (pageCount == 0)
		{
			return points;
		}
		
		if (current > first)
		{
			points.add(first);
		}
		
		// 1 2 3 c 5 6 ....
		
		if (current > 4)
		{
			points.add(GAP);
		}

		// at least 10 gap to the left, so add middle point
		if (current > 14)
		{
			m = (current - 2) / 2;
			points.add(m);
			points.add(GAP);
		}

		if (current > 3)
		{
			points.add(current -2);
		}
		if (current > 2)
		{
			points.add(current -1);
		}
		
		points.add(-current);
		
		if (current < last - 1)
		{
			points.add(current + 1);
		}
		if (current < last - 2)
		{
			points.add(current + 2);
		}
		if (current < last - 3)
		{
			points.add(GAP);
			
			if (current < last - 13)
			{
				m = last - (last - 2 - current) / 2;
				points.add(m);
				points.add(GAP);
			}
		}
		
		if (current < last)
		{
			points.add(last);
		}

		return points;
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
