package de.jwi.jbm;

import javax.servlet.http.HttpServletRequest;

import de.jwi.jbm.entities.User;

public class AboutAction implements Action
{
	public String run(HttpServletRequest request, User user, String cmd)
	{
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		request.setAttribute("hostport", String.format("%s:%d", serverName, serverPort));

		return "/WEB-INF/about.jsp";
	}
}
