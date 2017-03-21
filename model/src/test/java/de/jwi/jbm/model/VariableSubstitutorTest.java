package de.jwi.jbm.model;

import static org.junit.Assert.assertEquals;
import java.util.Properties;

import org.junit.Test;

import de.jwi.util.VariableSubstitutor;

public class VariableSubstitutorTest
{
	@Test
	public void testVars()
	{
		Properties p = new Properties();
		
		p.setProperty("base", "/opt/jboss");
		p.setProperty("custom", "${base}/my.properties");
		
		p.setProperty("javaversion", "${java.version}");
		
		new VariableSubstitutor().substitute(p);
		
		assertEquals("/opt/jboss/my.properties", p.getProperty("custom"));
		assertEquals(System.getProperty("java.version"), p.getProperty("javaversion"));
	}

}
