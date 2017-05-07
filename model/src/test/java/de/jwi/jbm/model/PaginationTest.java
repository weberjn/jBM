package de.jwi.jbm.model;

import java.util.Arrays;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaginationTest
{
	@Test
	public void aSmall()
	{
		int S = 10;
		PagePosition p;
		List<Integer> points;
		
		p = new PagePosition(0, 0, S);
		
		points = p.getPaginationPoints();
		assertTrue(points.isEmpty());
		
		p = new PagePosition(1 * S, 1, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(0), points);

		p = new PagePosition(2 * S, 1, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(0, 2), points);
		
		p = new PagePosition(2 * S, 2, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(1, 0), points);		

		p = new PagePosition(3 * S, 2, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(1, 0, 3), points);		

		p = new PagePosition(3 * S, 1, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(0, 2, 3), points);		
		
		p = new PagePosition(3 * S, 3, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(1, 2, 0), points);		

		p = new PagePosition(4 * S, 1, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(0, 2, 3, 4), points);		

		p = new PagePosition(5 * S, 1, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(0, 2, 3, -1, 5), points);		

		p = new PagePosition(5 * S, 5, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(1, -1, 3, 4, 0), points);		
	}
	
	@Test
	public void bLarge()
	{
		int S = 10;
		PagePosition p;
		List<Integer> points;
		
		p = new PagePosition(50 * S, 1, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(0, 2, 3, -1, 27, -1, 50), points);	

		p = new PagePosition(50 * S, 14, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, -1, 12, 13, 0, 15, 16, -1, 33, -1, 50), points);	

		p = new PagePosition(50 * S, 15, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, -1, 6, -1, 13, 14, 0, 16, 17, -1, 34, -1, 50), points);	

		
		p = new PagePosition(50 * S, 50, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, -1, 24, -1, 48, 49, 0), points);	
		
		p = new PagePosition(50 * S, 37, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, -1, 17, -1, 35, 36, 0, 38, 39, -1, 50), points);	

		p = new PagePosition(50 * S, 36, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, -1, 17, -1, 34, 35, 0, 37, 38, -1, 44, -1, 50), points);	
		
		p = new PagePosition(50 * S, 20, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, -1, 9, -1, 18, 19, 0, 21, 22, -1, 36, -1, 50), points);	


		
		
		System.out.println(points);
	}
}
