package de.jwi.jbm.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import de.jwi.jbm.ActionException;
import de.jwi.jbm.entities.User;

public interface APIAction
{
	void run(HttpServletRequest request, HttpServletResponse response, User user, String cmd) throws ActionException;
}
