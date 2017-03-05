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

		Pattern pattern = Pattern.compile(Controller.PAGE_PATTERN);

		cmd = "list";
		Matcher matcher = pattern.matcher(cmd);
		b = matcher.find();
		assertTrue(b);
		s = matcher.group(Controller.GRP_PAGE);
		assertEquals(null,s);
		s = matcher.group(Controller.GRP_TAG);
		assertEquals(null,s);

		
		cmd = "list/3";
		matcher = pattern.matcher(cmd);
		b = matcher.find();
		assertTrue(b);

		s = matcher.group(Controller.GRP_PAGE);
		assertEquals("3",s);
		
		cmd = "list/3/5";
		matcher = pattern.matcher(cmd);
		b = matcher.find();
		assertTrue(b);

		s = matcher.group(Controller.GRP_PAGE);
		assertEquals("3",s);

		s = matcher.group(Controller.GRP_TAG);
		assertEquals("5",s);

	}

}
