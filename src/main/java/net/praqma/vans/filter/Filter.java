package net.praqma.vans.filter;

import java.util.ArrayList;

public abstract class Filter
{	
	public class Findings extends ArrayList<Finding>
	{
		
	}
	
	protected Findings findings = new Findings();
	
	
	public void filter( String input )
	{
		System.out.println( "Abstract filter!?" );
	}
	
	
	public Findings getFindings()
	{
		return findings;
	}
	
}
