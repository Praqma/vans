package net.praqma.vans.configuration;

import java.io.File;

import net.praqma.vans.task.Shell;
import net.praqma.vans.task.Task;

public class Maven extends Configuration
{
	public Task buildTask()
	{
		System.out.println( "RUNNING MAVEN" );
		
		/* Build options */
		String cmd = "mvn";
		if( this.options.containsKey( "clean" ) && !this.options.get( "clean" ).equals( "" ) )
		{
			cmd += " clean";
		}
		
		if( this.options.containsKey( "package" ) && !this.options.get( "package" ).equals( "" ) )
		{
			cmd += " package";
		}
		
		if( this.options.containsKey( "install" ) && !this.options.get( "install" ).equals( "" ) )
		{
			cmd += " install";
		}

		logger.debug( cmd );
		
		return new Shell( this.workingDir, cmd );
	}
}
