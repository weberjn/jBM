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
		assertEquals(Arrays.asList(-1), points);

		p = new PagePosition(2 * S, 1, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(-1, 2), points);
		
		p = new PagePosition(2 * S, 2, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(1, -2), points);		

		p = new PagePosition(3 * S, 2, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(1, -2, 3), points);		

		p = new PagePosition(3 * S, 1, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(-1, 2, 3), points);		
		
		p = new PagePosition(3 * S, 3, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(1, 2, -3), points);		

		p = new PagePosition(4 * S, 1, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(-1, 2, 3, 4), points);		

		p = new PagePosition(5 * S, 1, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(-1, 2, 3, 0, 5), points);		

		p = new PagePosition(5 * S, 5, S);
		points = p.getPaginationPoints();
		assertEquals(Arrays.asList(1, 0, 3, 4, -5), points);		
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
		assertEquals(Arrays.asList(-1, 2, 3, 0, 27, 0, 50), points);	

		p = new PagePosition(50 * S, 14, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, 0, 12, 13, -14, 15, 16, 0, 33, 0, 50), points);	

		p = new PagePosition(50 * S, 15, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, 0, 6, 0, 13, 14, -15, 16, 17, 0, 34, 0, 50), points);	

		
		p = new PagePosition(50 * S, 50, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, 0, 24, 0, 48, 49, -50), points);	
		
		p = new PagePosition(50 * S, 37, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, 0, 17, 0, 35, 36, -37, 38, 39, 0, 50), points);	

		p = new PagePosition(50 * S, 36, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, 0, 17, 0, 34, 35, -36, 37, 38, 0, 44, 0, 50), points);	
		
		p = new PagePosition(50 * S, 20, S);
		points = p.getPaginationPoints();
		assertTrue(points.size() <= 13);
		assertEquals(Arrays.asList(1, 0, 9, 0, 18, 19, -20, 21, 22, 0, 36, 0, 50), points);	
	}
}
