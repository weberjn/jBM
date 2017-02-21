package de.jwi.jbm;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.jwi.jbm.entities.User;

public class UserManager {

	EntityManager em;
	
	public UserManager(EntityManager entityManager) {
		super();
		this.em = entityManager;
	}
	
	public User findUser(String username)
	{
		User user = null;
		
		Query query = em.createQuery("Select u FROM User u WHERE u.username = :username");
		query.setParameter("username", username);
		
		List resultList = query.getResultList(); 
		
		if (!resultList.isEmpty())
		{
			user = (User)resultList.get(0);
			return user;
		}
		
		return null;
	}
	
	public User createUser(String username)
	{
		User user = new User();
		user.setUsername(username);

		Timestamp ts = new Timestamp(new Date().getTime());
		user.setDatetime(ts);
		user.setModified(ts);
		
		em.persist(user);
		
		return user;
	}	

	public void saveUser(User user)
	{
		em.persist(user);
	}
	
	public void updateTimeStamp(User user)
	{
		Timestamp ts = new Timestamp(new Date().getTime());
		user.setModified(ts);
	}

}
