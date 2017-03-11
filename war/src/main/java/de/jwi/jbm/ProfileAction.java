package de.jwi.jbm;

import javax.servlet.http.HttpServletRequest;

import de.jwi.jbm.entities.User;
import de.jwi.jbm.model.UserManager;

public class ProfileAction implements Action
{
	UserManager um;
	
	public ProfileAction(UserManager um)
	{
		super();
		this.um = um;
	}

	@Override
	public String run(HttpServletRequest request, User user, String cmd)
	{
		Boolean saved = new Boolean(false);

		if (request.getParameter("submitted") != null)
		{
			String email = request.getParameter("pMail");
			String name = request.getParameter("pName");
			String homepage = request.getParameter("pPage");
			String content = request.getParameter("pDesc");

			user.setEmail(email);
			user.setName(name);
			user.setHomepage(homepage);
			user.setContent(content);

			um.updateTimeStamp(user);

			saved = new Boolean(true);
		}

		request.setAttribute("saved", saved);

		return "/WEB-INF/profile.jsp";
	}
}
