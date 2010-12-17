package net.praqma.vans.command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell extends BasicCommand
{
	private static String linesep = System.getProperty( "line.separator" );
	
	private File tempbatch = null;
	
	public Shell( File path, String cmd )
	{
		this.cmd = cmd;
		this.cwd = path;
		
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
		
		//System.out.println( "Using batch file: " + temp.getAbsolutePath() );
		
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
		
		Wait waiter = new Wait();
		waiter.start();

		/* Eat the output stream */
		InputStreamReader isr = new InputStreamReader( p.getInputStream() );
		BufferedReader br = new BufferedReader( isr );
		String line = null;
		StringBuffer sb = new StringBuffer();
		
		int counter = 0;
		
		try
		{
			while( ( line = br.readLine() ) != null )
			{
				counter++;
				
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
        
        waiter.done();
        
        try
		{
			waiter.join();
		}
		catch ( InterruptedException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println( "" );
        
        /* Do we care about the exit value? */
        //System.out.println( "Exit value: " + exitValue );

        return sb.toString();
	}
	
	private class Wait extends Thread
	{
		private volatile boolean done = false;
		
		public void done()
		{
			this.done = true;
		}
		
		public void run()
		{
			int length = 10;
			int counter = 10;
			String out2 = new String(new char[(length+1)]).replace("\0", ".");
			
			while( !done )
			{
				
				counter++;
				System.out.print( "\r" + out2 );
				System.out.print( "\r" + new String(new char[Math.abs( -9 + (counter%(2*length)))]).replace("\0", ".") + "0" );
				
				try
				{
					Thread.sleep( 50 );
				}
				catch ( InterruptedException e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.out.print( "\r (DONE)          " );
		}
	}
	
}
