package de.jwi.jbm.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.jwi.jbm.ActionException;
import de.jwi.jbm.entities.User;

public interface APIAction
{
	void run(HttpServletRequest request, HttpServletResponse response, User user, String cmd) throws ActionException;
}
