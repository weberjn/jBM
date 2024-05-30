package de.jwi.jbm;

import jakarta.servlet.http.HttpServletRequest;

import de.jwi.jbm.entities.User;

public interface Action
{
	String run(HttpServletRequest request, User user, String cmd) throws ActionException;
}
