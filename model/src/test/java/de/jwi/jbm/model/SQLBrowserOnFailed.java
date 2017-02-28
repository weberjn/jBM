package de.jwi.jbm.model;

import java.sql.SQLException;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class SQLBrowserOnFailed extends TestWatcher
{
	@Override
	protected void failed(Throwable e, Description description)
	{
		System.out.println("Only executed when a test fails");
		
		try
		{
			org.h2.tools.Server.startWebServer(ModelTest.connection);
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
