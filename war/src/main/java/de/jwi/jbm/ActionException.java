package de.jwi.jbm;

public class ActionException extends Exception
{
	
	public ActionException(String message)
	{
		super(message);
	}

	public ActionException(Throwable cause)
	{
		super(cause);
	}
}
