package de.jwi.jbm.model;

public class PaginationPoint
{
	private String href;
	private String a;
	private int page;
	private int kind;
	
	public final static int CURRENT=0, LINK = 1, GAP = -1;
	
	public PaginationPoint(int page, int kind)
	{
		super();
		this.page = page;
		this.kind = kind;
	}
	
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
