import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.User;

public class T {

	public static void main(String[] args) {

		Properties properties = new Properties();
		properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/lanchecker-test");

		// Configure the internal EclipseLink connection pool
		properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
		properties.put("javax.persistence.jdbc.url", "jdbc:postgresql://luna:8432/scuttle");
		properties.put("javax.persistence.jdbc.user", "intuser");
		properties.put("javax.persistence.jdbc.password", "int2001");

		// Configure logging. FINE ensures all SQL is shown
		properties.put("eclipselink.logging.level", "FINE");

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jbm-entities", properties);

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction( ).begin( );

		
		String q = "SELECT b FROM Bookmark b";

		Query query = entityManager.createQuery(q);

		List<Bookmark> resultList = query.getResultList();

		System.out.println(resultList);
		
		User u = new User();
		u.setUsername("weberjn");
		u.setPassword("**");
		u.setEmail("j@d.de");
		u.setDatetime(new Timestamp(0));
		u.setModified(new Timestamp(0));
		
		entityManager.persist(u);
		
		Bookmark b = new Bookmark();

		b.setUser(u);
		b.setAddress("http://www.heise.de");
		b.setStatus(2);
		b.setTitle("title");
		b.setHash("XXX");
		b.setVotes(0);
		b.setVoting(0);
		
		b.setDatetime(new Timestamp(0));
		b.setModified(new Timestamp(0));
		
		
		
		entityManager.persist(b);
		

		
		Query query1 = entityManager.createQuery(q);

		List<Bookmark> resultList1 = query.getResultList();

		System.out.println(resultList1);

		entityManager.getTransaction( ).commit( );

	}

}
