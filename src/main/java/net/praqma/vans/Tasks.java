package net.praqma.vans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.praqma.vans.filter.Filter;
import net.praqma.vans.filter.Findings;
import net.praqma.vans.task.Shell;
import net.praqma.vans.task.Task;
import net.praqma.vans.util.Status;
import net.praqma.vans.util.VANSException;

public class Tasks
{
	private List<Task> commands = new ArrayList<Task>();
	private Filter filter          = null;
	private static final File cwd  = new File( System.getProperty( "user.dir" ) );

	private VANSReport report = null;

	public Tasks( Task command, Filter filter )
	{
		this.commands.add( command );
		this.filter  = filter;
	}

	public Tasks( Task[] commands, Filter filter )
	{
		this.commands.addAll( Arrays.asList( commands ) );
		this.filter  = filter;
	}

	public Tasks( List<Task> commands, Filter filter )
	{
		this.commands = commands;
		this.filter  = filter;
	}

	public Tasks( File commands, Filter filter ) throws VANSException
	{
		/* Get commands from file */
		try
		{
			BufferedReader br = new BufferedReader( new FileReader( commands ) );

			String line = "";
			while( ( line = br.readLine() ) != null )
			{
				this.commands.add( new Shell( cwd, line.trim() ) );
			}

		}
		catch ( Exception e )
		{
			throw new VANSException( "Could not process the file \"" + commands.toString() + "\"" );
		}
		this.filter  = filter;
	}

	public void saveVANSReport( File save ) throws VANSException
	{
		if( report != null )
		{
			System.out.println( "Saving log to " + save );
			report.saveState( save );
		}
		else
		{
			System.err.println( "Cannot save an uninitialized report, make sure the Task is run()" );
			throw new VANSException( "Cannot save an uninitialized report" );
		}
	}

	public void saveReport( File save ) throws VANSException
	{
		if( report != null )
		{
			System.out.println( "Saving log to " + save );
			report.transform( "/junit.xsl", save );
		}
		else
		{
			System.err.println( "Cannot save an uninitialized report, make sure the Task is run()" );
			throw new VANSException( "Cannot save an uninitialized report" );
		}
	}

	public void run()
	{
		report = new VANSReport( filter.getName() );

		int errors = 0;
		for( Task cmd : commands )
		{
			System.out.println( "Directory: " + cmd.getCwd() );
			System.out.println( "Command:   " + cmd.getCmd() );
			String result = null;
			try
			{
				result = cmd.execute();
			}
			catch ( VANSException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Status status = filter.filter( result );
			Findings findings = filter.getFindings();
			System.out.println( findings.size() + " findings." );
			errors += findings.numberOfErrors();
			report.addCase( findings, cmd, status );

			findings.reset();
		}

		report.setErrors( errors );
	}

}
