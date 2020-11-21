package com.one.file;

import java.io.*;
import javax.microedition.io.Connection;

public class Connector
{
	public static final int READ = javax.microedition.io.Connector.READ;
	public static final int WRITE = javax.microedition.io.Connector.WRITE;
	public static final int READ_WRITE = javax.microedition.io.Connector.READ_WRITE;
	
	public static Connection open(String name) throws IOException
	{
		if(name.startsWith("file://"))
		{
			return convertFileConnection(javax.microedition.io.Connector.open(name));
		}
		else
		{
			return javax.microedition.io.Connector.open(name);
		}
	}
	
	public static Connection open(String name, int mode) throws IOException
	{
		if(name.startsWith("file://"))
		{
			return convertFileConnection(javax.microedition.io.Connector.open(name, mode));
		}
		else
		{
			return javax.microedition.io.Connector.open(name, mode);
		}
	}
	
	public static Connection open(String name, int mode, boolean timeouts) throws IOException
	{
		if(name.startsWith("file://"))
		{
			return convertFileConnection(javax.microedition.io.Connector.open(name, mode, timeouts));
		}
		else
		{
			return javax.microedition.io.Connector.open(name, mode, timeouts);
		}
	}
	
	public static InputStream openInputStream(String name) throws IOException
	{
		return javax.microedition.io.Connector.openInputStream(name);
	}
	
	public static OutputStream openOutputStream(String name) throws IOException
	{
		return javax.microedition.io.Connector.openOutputStream(name);
	}
	
	public static DataInputStream openDataInputStream(String name) throws IOException
	{
		return javax.microedition.io.Connector.openDataInputStream(name);
	}
	
	public static DataOutputStream openDataOutputStream(String name) throws IOException
	{
		return javax.microedition.io.Connector.openDataOutputStream(name);
	}
	
	private static FileConnection convertFileConnection(Connection conn)
	{
		switch(FileSystemRegistry.getApiType())
		{
			case FileSystemRegistry.API_GENERIC:
				return new GenericFileConnection(conn);
			
			case FileSystemRegistry.API_SIEMENS:
				return new SiemensFileConnection(conn);
			
			case FileSystemRegistry.API_MOTOROLA_TYPE_A:
				return new MotorolaTypeAFileConnection(conn);
			
			case FileSystemRegistry.API_MOTOROLA_TYPE_B:
				return new MotorolaTypeBFileConnection(conn);
			
			default:
				return null;
		}
	}
}