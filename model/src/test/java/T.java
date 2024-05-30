import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import de.jwi.jbm.entities.Bookmark;
import de.jwi.jbm.entities.User;

public class T {

	public static void main(String[] args) {

		Properties properties = new Properties();

		// Configure the internal EclipseLink connection pool
		properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
		properties.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://luna:8432/scuttle");
		properties.put("jakarta.persistence.jdbc.user", "intuser");
		properties.put("jakarta.persistence.jdbc.password", "int2001");

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
