package de.jwi.jbm.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import de.jwi.jbm.entities.User;



public class ModelTest
{
	protected static EntityManagerFactory emf;
    protected static EntityManager em;
    
    @BeforeClass
    public static void init()
    {
    	
    	
    	Properties p = new Properties();
    	p.setProperty("javax.persistence.jdbc.url", "jdbc:h2:mem:jBM");
    	p.setProperty("javax.persistence.jdbc.driver", "org.h2.Driver");
    	p.setProperty("eclipselink.target-database", "org.eclipse.persistence.platform.database.H2Platform");
    	
        emf = Persistence.createEntityManagerFactory("jBM",p);
        em = emf.createEntityManager();
    }
    
    @Before
    public void initializeDatabase() throws SQLException, IOException{
    	
    	Connection connection = DriverManager.getConnection("jdbc:h2:mem:jBM");
    	URL url = ModelTest.class.getResource("/tables-postgresql.sql");
    	System.out.println(url);
    	InputStream is = url.openStream();
    	Reader r = new InputStreamReader(is);
    	
    	RunScript.execute(connection, r);
    	
    	r.close();
    	connection.close();
    }
    
    @Test
    public void testUser()
    {
    	UserManager um = new UserManager(em);
    	
    	User user = um.createUser("weberjn");
    	
    	User user2 = um.findUser("weberjn");
    	
    	assertEquals(user.getName(), user2.getName());
    }

    @AfterClass
    public static void tearDown(){
        em.clear();
        em.close();
        emf.close();
    }
}
