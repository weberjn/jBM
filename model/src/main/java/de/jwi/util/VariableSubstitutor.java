package de.jwi.util;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableSubstitutor
{
	Pattern re = Pattern.compile("\\$\\{(.+?)\\}");
	
	public void substitute(Properties properties)
	{
		for (Object key : properties.keySet())
		{
			String val = properties.getProperty((String) key);

			val = resolve(val, properties);
			
			properties.setProperty((String)key, val);
		}

	}

	// https://dzone.com/articles/fun-regular-expressions-ant
	
	private String resolve(String sourcestring, Properties props)
	{
		Matcher m = re.matcher(sourcestring);
		StringBuffer result = new StringBuffer();
		while (m.find())
		{
			String variable = m.group(1);
			
			String s = props.getProperty(variable);
			if (s == null)
			{
				s = System.getProperty(variable);
			}
			
			String resolved = resolve(s, props);
			resolved = resolved.replace("\\", "\\\\");
			m.appendReplacement(result, resolved);
		}
		m.appendTail(result);
		return result.toString();
	}
}
