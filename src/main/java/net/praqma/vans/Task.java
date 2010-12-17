package net.praqma.vans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.praqma.vans.command.Command;
import net.praqma.vans.command.Shell;
import net.praqma.vans.filter.Filter;
import net.praqma.vans.filter.Filter.Findings;
import net.praqma.vans.filter.Finding;
import net.praqma.vans.util.Status;
import net.praqma.vans.util.TaskException;

public class Task
{
	private List<Command> commands = new ArrayList<Command>();
	private Filter filter          = null;
	private static final File cwd  = new File( System.getProperty( "user.dir" ) );
	
	private VANSReport report = null;
	
	public Task( Command command, Filter filter )
	{
		this.commands.add( command );
		this.filter  = filter;
	}
	
	public Task( Command[] commands, Filter filter )
	{
		this.commands.addAll( Arrays.asList( commands ) );
		this.filter  = filter;
	}
	
	public Task( List<Command> commands, Filter filter )
	{
		this.commands = commands;
		this.filter  = filter;
	}
	
	public Task( File commands, Filter filter ) throws TaskException
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
			throw new TaskException( "Could not process the file \"" + commands.toString() + "\"" );
		}
		this.filter  = filter;
	}
	
	public void saveVANSReport( File save ) throws TaskException
	{
		if( report != null )
		{
			System.out.println( "Saving log to " + save );
			report.saveState( save );
		}
		else
		{
			System.err.println( "Cannot save an uninitialized report, make sure the Task is run()" );
			throw new TaskException( "Cannot save an uninitialized report" );
		}
	}
	
	public void saveReport( File save ) throws TaskException
	{
		if( report != null )
		{
			System.out.println( "Saving log to " + save );
			report.transform( "/junit.xsl", save );
		}
		else
		{
			System.err.println( "Cannot save an uninitialized report, make sure the Task is run()" );
			throw new TaskException( "Cannot save an uninitialized report" );
		}
	}
	
	public void run()
	{
		report = new VANSReport( filter.getName() );
		
		int errors = 0;
		for( Command cmd : commands )
		{
			System.out.println( "Directory: " + cmd.getCwd() );
			System.out.println( "Command:   " + cmd.getCmd() );
			String result = cmd.execute();
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
