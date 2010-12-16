package net.praqma.vans.filter;

import java.util.ArrayList;

import net.praqma.vans.filter.Finding.Level;

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
	
	
	public void filter( String input )
	{
		System.out.println( "Abstract filter!?" );
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
	
}
