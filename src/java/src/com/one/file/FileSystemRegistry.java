package com.one.file;

import java.util.Enumeration;

public class FileSystemRegistry
{
	static final int API_UNKNOWN = 0;
	static final int API_GENERIC = 1;
	static final int API_SIEMENS = 2;
	static final int API_MOTOROLA_TYPE_A = 3;
	static final int API_MOTOROLA_TYPE_B = 4;
	
	private static int apitype = -1;
	
	private static void detectApiType()
	{
		if(classExists("javax.microedition.io.file.FileConnection"))
		{
			apitype = API_GENERIC;
		}
		else if(classExists("com.siemens.mp.io.file.FileConnection"))
		{
			apitype = API_SIEMENS;
		}
		else if(classExists("com.motorola.io.FileConnection"))
		{
			apitype = API_MOTOROLA_TYPE_A;
		}
		else if(classExists("com.motorola.io.file.FileConnection"))
		{
			apitype = API_MOTOROLA_TYPE_B;
		}
		else
		{
			apitype = API_UNKNOWN;
		}
	}
	
	static int getApiType()
	{
		if(apitype < 0)
		{
			detectApiType();
		}
		
		return apitype;
	}
	
	private static boolean classExists(String name)
	{
		try
		{
			return Class.forName(name) != null;
		}
		catch(Throwable t)
		{
			return false;
		}
	}
	
	public static Enumeration listRoots()
	{
		switch(getApiType())
		{
			case API_GENERIC:
				return GenericFileConnection.listRoots();
			
			case API_SIEMENS:
				return SiemensFileConnection.listRoots();
			
			case API_MOTOROLA_TYPE_A:
				return MotorolaTypeAFileConnection.listRoots();
			
			case API_MOTOROLA_TYPE_B:
				return MotorolaTypeBFileConnection.listRoots();
			
			default:
				return null;
		}
	}
}