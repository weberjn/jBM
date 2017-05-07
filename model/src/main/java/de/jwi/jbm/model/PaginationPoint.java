package de.jwi.jbm.model;

public class PaginationPoint
{
	private String href;
	private String a;
	
	public PaginationPoint(String href, String a)
	{
		super();
		this.href = href;
		this.a = a;
	}
	public String getHref()
	{
		return href;
	}
	public String getA()
	{
		return a;
	}
}
