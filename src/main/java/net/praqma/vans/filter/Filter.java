package net.praqma.vans.filter;

import java.util.ArrayList;

import net.praqma.vans.filter.Finding.Level;
import net.praqma.vans.util.Status;

public abstract class Filter
{
	

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
