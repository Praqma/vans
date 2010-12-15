package net.praqma.vans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.praqma.vans.command.Command;
import net.praqma.vans.filter.Filter;
import net.praqma.vans.filter.Filter.Findings;
import net.praqma.vans.filter.Finding;

public class Task
{
	private List<Command> commands = new ArrayList<Command>();
	private Filter filter   = null;

	public Task( Command command, Filter filter )
	{
		this.commands.add( command );
		this.filter  = filter;
	}
	
	public Task( File commands, Filter filter )
	{
		//this.command = command;
		this.filter  = filter;
	}
	
	public void run()
	{
		List<Findings> findingsList = new ArrayList<Findings>();
		
		for( Command cmd : commands )
		{
			String result = cmd.execute();
			filter.filter( result );
			findingsList.add( filter.getFindings() );
		}
		
		for( Findings findings : findingsList )
		{
			for( Finding f : findings )
			{
				System.out.println( f.toString() );
			}
		}
		
		VANSLog log = new VANSLog( findingsList );
		log.SaveState();
	}

}
