package com.one;

import java.util.Vector;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class CompoundEnumeration implements Enumeration
{
	protected Enumeration current;
	protected Vector components;
	protected int index, maxindex;
	
	public CompoundEnumeration()
	{
		components = new Vector();
		
		Enumeration first = new Enumeration()
		{
			public boolean hasMoreElements()
			{
				return false;
			}
			
			public Object nextElement()
			{
				throw new NoSuchElementException();
			}
		};
		
		components.addElement(first);
		current = first;
		maxindex = 0;
		index = 0;
	}
	
	public void addEnumeration(Enumeration next)
	{
		if(next == null || !next.hasMoreElements())
		{
			return;
		}
		
		components.addElement(next);
		maxindex = components.size() - 1;
	}
	
	public boolean hasMoreElements()
	{
		return index < maxindex ? true : current.hasMoreElements();
	}
	
	public Object nextElement()
	{
		if(current.hasMoreElements())
		{
			return current.nextElement();
		}
		else if(++index <= maxindex)
		{
			current = (Enumeration)components.elementAt(index);
			return nextElement();
		}
		else
		{
			throw new NoSuchElementException();
		}
		
		/*
		try
		{
			return current.nextElement();
		}
		catch(NoSuchElementException e)
		{
			if(++index <= maxindex)
			{
				current = (Enumeration)components.elementAt(index);
				return nextElement();
			}
			else
			{
				throw e;
			}
		}
		*/
	}
}