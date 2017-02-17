package de.jwi.jbm;

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
	
	public User findUser(String name)
	{
		User user = null;
		
		Query query = em.createQuery("Select u FROM User u WHERE u.name = :name");
		query.setParameter("name", name);
		
		List resultList = query.getResultList(); 
		
		if (!resultList.isEmpty())
		{
			user = (User)resultList.get(0);
			return user;
		}
		
		return null;
	}
	
}
