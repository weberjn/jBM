package de.jwi.jbm;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import de.jwi.util.VariableSubstitutor;

public class AppContextListener implements ServletContextListener
{
	private static final Logger log = Logger.getLogger(AppContextListener.class.getName());

	public static final String EXCEPTION = "Exception";
	public static final String EMF = "EntityManagerFactory";
	public static final String PROPS = "properties";
	
	private static final String PROPERTIES = "/WEB-INF/jBM.properties";
	private static final String CUSTOMPROPERTIES = "/jBM-custom.properties";
	private static final String VERSIONPROPERTIES = "/version.properties";
	private static final String CUSTOMSYSTEMPROPERTY = "jBM.custom.config";
	
	private EntityManagerFactory entityManagerFactory;

	
	@Override
	public void contextInitialized(ServletContextEvent contextEvent)
	{
		ServletContext context = contextEvent.getServletContext();
		String s;
		
		Properties properties = new Properties();
		try
		{
			URL url = context.getResource(PROPERTIES);
			InputStream is = url.openStream();
			properties.load(is);
			is.close();

			url = Controller.class.getResource(CUSTOMPROPERTIES);
			if (url != null)
			{
				log.info("loading custom Properties from " + url);

				Properties properties2 = new Properties();
				is = url.openStream();
				properties2.load(is);
				is.close();
				properties.putAll(properties2);
			}
			else if ((s=System.getProperty(CUSTOMSYSTEMPROPERTY))!=null)
			{
				Properties p = new Properties();
				p.setProperty(CUSTOMSYSTEMPROPERTY, s);
				new VariableSubstitutor().substitute(p);
				s = p.getProperty(CUSTOMSYSTEMPROPERTY);
				
				log.info("loading custom Properties from " + s);
				FileInputStream fis = new FileInputStream(s);
				p.load(fis);
				fis.close();
				
				properties.putAll(p);
			}


			url = Controller.class.getResource(VERSIONPROPERTIES);
			if (url != null)
			{
				Properties properties2 = new Properties();
				is = url.openStream();
				properties2.load(is);
				is.close();
				properties.putAll(properties2);
			}
			
			new VariableSubstitutor().substitute(properties);
			context.setAttribute(PROPS, properties);
			
			entityManagerFactory = Persistence.createEntityManagerFactory("jBM", properties);
			context.setAttribute(EMF, entityManagerFactory);

			log.info(entityManagerFactory.toString());

		} catch (Exception e)
		{
			context.setAttribute(EXCEPTION, e);
			log.log(Level.SEVERE, e.getMessage(), e);
		} 
	}

	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent)
	{
		entityManagerFactory.close();
		entityManagerFactory = null;
	}
}
