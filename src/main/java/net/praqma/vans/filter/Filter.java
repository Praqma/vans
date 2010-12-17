package net.praqma.vans.filter;

import java.util.ArrayList;

import net.praqma.vans.filter.Finding.Level;
import net.praqma.vans.util.Status;

public abstract class Filter
{	
	public class Findings extends ArrayList<Finding>
	{
		private int errors = 0;
		
		public boolean add( Finding f )
		{
			boolean done = super.add( f );
			
			if( f.level == Level.ERROR || f.level == Level.FATAL )
			{
				errors++;
			}
			
			return done;
		}
		
		public int numberOfErrors()
		{
			return errors;
		}
		
		public void reset()
		{
			this.clear();
			errors = 0;
		}
	}
	
	protected Findings findings = new Findings();
	protected boolean failOnWarnings = false;
	
	
	public Status filter( String input )
	{
		System.out.println( "Abstract filter!?" );
		return null;
	}
	
	
	public Findings getFindings()
	{
		return findings;
	}
	
	public String getName()
	{
		return "Unknown";
	}
	
	public void reset()
	{
		findings.reset();
	}
	
	public void failOnWarnings( boolean b )
	{
		this.failOnWarnings = b;
	}
	
}
