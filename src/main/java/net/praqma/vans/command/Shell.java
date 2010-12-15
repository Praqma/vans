package net.praqma.vans.command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell implements Command
{
	private static String linesep = System.getProperty( "line.separator" );
	
	private File cwd;
	private String cmd;
	private File tempbatch = null;
	
	private boolean verbose = false;

	public Shell( File path, String cmd )
	{
		/* Make temporary batch file */
		File temp = null;
		try
		{
			temp = File.createTempFile( "VANS", ".bat" );
			FileWriter fw = new FileWriter( temp );
			BufferedWriter out = new BufferedWriter( fw );
			out.write( cmd );
			out.close();
			fw.close();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		
		System.out.println( "Using batch file: " + temp.getAbsolutePath() );
		
		this.tempbatch = temp;
		this.cwd = path;
		this.cmd = cmd;
	}

	public String execute()
	{
		/* Setup the external process */
		String[] cmd = { "cmd.exe", "/c", "call", this.tempbatch.getAbsoluteFile().toString() };
		ProcessBuilder pb = new ProcessBuilder( cmd );
		pb.redirectErrorStream( true );
		pb.directory( cwd );
		
		Process p = null;
		
		/* Start the process */
		try
		{
			p = pb.start();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		
		/* Eat the output stream */
		InputStreamReader isr = new InputStreamReader( p.getInputStream() );
		BufferedReader br = new BufferedReader( isr );
		String line = null;
		StringBuffer sb = new StringBuffer();
		
		try
		{
			while( ( line = br.readLine() ) != null )
			{
				sb.append( line + linesep );
				if( verbose )
				{
					System.out.println( line );
				}
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		
		int exitValue = 0;
		
		/* Wait for the process to terminate */
		try
		{
			exitValue = p.waitFor();
		}
		catch( InterruptedException e )
		{
			p.destroy();
		}
        finally
        {
            Thread.interrupted();
        }
        
        /* Do we care about the exit value? */
        System.out.println( "Exit value: " + exitValue );

        return sb.toString();
	}

}
