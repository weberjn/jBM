package de.jwi.jbm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class MatcherTest
{
	@Test
	public void matchPattern()
	{
		String cmd;
		boolean b;
		String s;

		Pattern pattern = Pattern.compile(ListBookmarksAction.PAGE_PATTERN);

		cmd = "list";
		Matcher matcher = pattern.matcher(cmd);
		b = matcher.find();
		assertTrue(b);
		s = matcher.group(ListBookmarksAction.GRP_PAGE);
		assertEquals(null,s);
		s = matcher.group(ListBookmarksAction.GRP_TAG);
		assertEquals(null,s);

		
		cmd = "list/3";
		matcher = pattern.matcher(cmd);
		b = matcher.find();
		assertTrue(b);

		s = matcher.group(ListBookmarksAction.GRP_PAGE);
		assertEquals("3",s);
		
		cmd = "list/3/5";
		matcher = pattern.matcher(cmd);
		b = matcher.find();
		assertTrue(b);

		s = matcher.group(ListBookmarksAction.GRP_PAGE);
		assertEquals("3",s);

		s = matcher.group(ListBookmarksAction.GRP_TAG);
		assertEquals("5",s);

		pattern = Pattern.compile(SearchBookmarksAction.SEARCH_PAGE_PATTERN);
		cmd = "search";
		matcher = pattern.matcher(cmd);
		b = matcher.find();
		assertTrue(b);
		
		cmd = "search/word/5";
		matcher = pattern.matcher(cmd);
		b = matcher.find();
		assertTrue(b);
		
		s = matcher.group(SearchBookmarksAction.GRP_SPAGE);
		assertEquals("5",s);

		s = matcher.group(SearchBookmarksAction.GRP_SWORD);
		assertEquals("word",s);
		
	}

}
