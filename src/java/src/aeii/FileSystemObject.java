package aeii;

import com.one.file.*;
import java.util.Vector;
import java.util.Enumeration;
import com.one.CompoundEnumeration;

public class FileSystemObject
{
	public static final String PARENT_DIR = "..";

	protected String currentPath;
	protected Exception lastError;
	
	public FileSystemObject()
	{
		currentPath = null;
	}
	
	public FileSystemObject(String initialPath)
	{
		currentPath = initialPath;
	}
	
	public Enumeration list()
	{
		lastError = null;
		
		try
		{
			if(currentPath == null)
			{
				return FileSystemRegistry.listRoots();
			}
			else
			{
				FileConnection fc = (FileConnection)Connector.open("file:///" + currentPath, Connector.READ);
				Enumeration e = fc.list();
				fc.close();
				
				return e;
			}
		}
		catch(Exception x)
		{
			lastError = x;
			return null;
		}
	}
	
	public Enumeration list(String ext, boolean folders)
	{
		lastError = null;
		
		try
		{
			if(currentPath == null)
			{
				return FileSystemRegistry.listRoots();
			}
			else
			{
				FileConnection fc = (FileConnection)Connector.open("file:///" + currentPath, Connector.READ);
				Enumeration e = fc.list();
				fc.close();
				
				Vector v = new Vector();
				String s;
				
				if(ext == null)
				{
					ext = "";
				}
				
				if(folders)
				{
					while(e.hasMoreElements())
					{
						s = (String)e.nextElement();
						
						if(s.endsWith("/") || s.toLowerCase().endsWith(ext))
						{
							v.addElement(s);
						}
					}
				}
				else
				{
					while(e.hasMoreElements())
					{
						s = (String)e.nextElement();
						
						if(s.toLowerCase().endsWith(ext))
						{
							v.addElement(s);
						}
					}
				}
				
				return v.elements();
			}
		}
		catch(Exception x)
		{
			lastError = x;
			return null;
		}
	}
	
	public void changeDir(String nextDir)
	{
		if(nextDir != null && !nextDir.equals(PARENT_DIR))
		{
			if(currentPath != null)
			{
				currentPath += nextDir;
			}
			else
			{
				currentPath = nextDir;
			}
		}
		else if(currentPath != null)
		{
			currentPath = currentPath.substring(0, currentPath.length() - 1);
			int index = currentPath.lastIndexOf('/');
			
			if(index >= 0)
			{
				currentPath = currentPath.substring(0, index + 1);
			}
			else
			{
				currentPath = null;
			}
		}
	}
	
	public void resetPath()
	{
		currentPath = null;
	}
	
	public FileConnection openFileConnection(String file)
	{
		lastError = null;
		
		try
		{
			return (FileConnection)Connector.open("file:///" + currentPath + file);
		}
		catch(Exception x)
		{
			lastError = x;
			return null;
		}
	}
	
	public String getCurrentPath()
	{
		return currentPath;
	}
	
	public Exception getLastError()
	{
		return lastError;
	}
	
	public static Enumeration sortFileList(Enumeration list)
	{
		if(list == null)
		{
			return null;
		}
		
		Vector vdirs = new Vector();
		Vector vfiles = new Vector();
		
		String s;
		
		while(list.hasMoreElements())
		{
			s = (String)list.nextElement();
			
			if(s.endsWith("/"))
			{
				vdirs.addElement(s);
			}
			else
			{
				vfiles.addElement(s);
			}
		}
		
		if(vdirs.size() > 0)
		{
			quickSort(vdirs, 0, vdirs.size() - 1);
		}
		
		if(vfiles.size() > 0)
		{
			quickSort(vfiles, 0, vfiles.size() - 1);
		}
		
		CompoundEnumeration result = new CompoundEnumeration();
		result.addEnumeration(vdirs.elements());
		result.addEnumeration(vfiles.elements());
		
		return result;
	}
	
	public static void quickSort(Vector arr, int first, int last)
	{
		String p = ((String)arr.elementAt((last - first) / 2 + first)).toLowerCase();
		String temp;
		int i = first, j = last;

		while(i <= j)
		{
			while(((String)arr.elementAt(i)).toLowerCase().compareTo(p) < 0 && i <= last)
			{
				i++;
			}

			while(((String)arr.elementAt(j)).toLowerCase().compareTo(p) > 0 && j >= first)
			{
				j--;
			}

			if(i <= j)
			{
				temp = ((String)arr.elementAt(i));
				arr.setElementAt(arr.elementAt(j), i);
				arr.setElementAt(temp, j);

				i++;
				j--;
			}
		}

		if(j > first)
		{
			quickSort(arr, first, j);
		}

		if(i < last)
		{
			quickSort(arr, i, last);
		}
	}

	public static String[] enumerationToArray(Enumeration e, String[] add)
	{
		Vector v = new Vector();

		if(add != null)
		{
			for(int i = 0; i < add.length; i++)
			{
				v.addElement(add[i]);
			}
		}

		while(e.hasMoreElements())
		{
			v.addElement(e.nextElement());
		}

		String[] res = new String[v.size()];
		v.copyInto(res);

		return res;
	}
}