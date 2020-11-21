package com.one.file;

import java.io.*;
import javax.microedition.io.Connection;
import java.util.Enumeration;

class GenericFileConnection implements com.one.file.FileConnection
{
	protected javax.microedition.io.file.FileConnection conn;
	
	public static Enumeration listRoots()
	{
		return javax.microedition.io.file.FileSystemRegistry.listRoots();
	}
	
	public GenericFileConnection(Connection conn)
	{
		this.conn = (javax.microedition.io.file.FileConnection)conn;
	}
	
	public void close() throws IOException
	{
		conn.close();
	}
	
	public boolean isOpen()
	{
		return conn.isOpen();
	}
	
	public InputStream openInputStream() throws IOException
	{
		return conn.openInputStream();
	}
	
	public DataInputStream openDataInputStream() throws IOException
	{
		return conn.openDataInputStream();
	}
	
	public OutputStream openOutputStream() throws IOException
	{
		return conn.openOutputStream();
	}
	
	public DataOutputStream openDataOutputStream() throws IOException
	{
		return conn.openDataOutputStream();
	}
	
	public OutputStream openOutputStream(long byteOffset) throws IOException
	{
		return conn.openOutputStream(byteOffset);
	}
	
	public long totalSize()
	{
		return conn.totalSize();
	}
	
	public long availableSize()
	{
		return conn.availableSize();
	}
	
	public long usedSize()
	{
		return conn.usedSize();
	}
	
	public long directorySize(boolean includeSubDirs) throws IOException
	{
		return conn.directorySize(includeSubDirs);
	}
	
	public long fileSize() throws IOException
	{
		return conn.fileSize();
	}
	
	public boolean canRead()
	{
		return conn.canRead();
	}
	
	public boolean canWrite()
	{
		return conn.canWrite();
	}
	
	public boolean isHidden()
	{
		return conn.isHidden();
	}
	
	public void setReadable(boolean readable) throws IOException
	{
		conn.setReadable(readable);
	}
	
	public void setWritable(boolean writable) throws IOException
	{
		conn.setWritable(writable);
	}
	
	public void setHidden(boolean hidden) throws IOException
	{
		conn.setHidden(hidden);
	}
	
	public Enumeration list() throws IOException
	{
		return conn.list();
	}
	
	public Enumeration list(String filter, boolean includeHidden) throws IOException
	{
		return conn.list(filter, includeHidden);
	}
	
	public void create() throws IOException
	{
		conn.create();
	}
	
	public void mkdir() throws IOException
	{
		conn.mkdir();
	}
	
	public boolean exists()
	{
		return conn.exists();
	}
	
	public boolean isDirectory()
	{
		return conn.isDirectory();
	}
	
	public void delete() throws IOException
	{
		conn.delete();
	}
	
	public void rename(String newName) throws IOException
	{
		conn.rename(newName);
	}
	
	public void truncate(long byteOffset) throws IOException
	{
		conn.truncate(byteOffset);
	}
	
	public void setFileConnection(String fileName) throws IOException
	{
		conn.setFileConnection(fileName);
	}
	
	public String getName()
	{
		return conn.getName();
	}
	
	public String getPath()
	{
		return conn.getPath();
	}
	
	public String getURL()
	{
		return conn.getURL();
	}
	
	public long lastModified()
	{
		return conn.lastModified();
	}
}

class SiemensFileConnection implements com.one.file.FileConnection
{
	protected com.siemens.mp.io.file.FileConnection conn;
	
	public static Enumeration listRoots()
	{
		return com.siemens.mp.io.file.FileSystemRegistry.listRoots();
	}
	
	public SiemensFileConnection(Connection conn)
	{
		this.conn = (com.siemens.mp.io.file.FileConnection)conn;
	}
	
	public void close() throws IOException
	{
		conn.close();
	}
	
	public boolean isOpen()
	{
		return conn.isOpen();
	}
	
	public InputStream openInputStream() throws IOException
	{
		return conn.openInputStream();
	}
	
	public DataInputStream openDataInputStream() throws IOException
	{
		return conn.openDataInputStream();
	}
	
	public OutputStream openOutputStream() throws IOException
	{
		return conn.openOutputStream();
	}
	
	public DataOutputStream openDataOutputStream() throws IOException
	{
		return conn.openDataOutputStream();
	}
	
	public OutputStream openOutputStream(long byteOffset) throws IOException
	{
		return conn.openOutputStream(byteOffset);
	}
	
	public long totalSize()
	{
		return conn.totalSize();
	}
	
	public long availableSize()
	{
		return conn.availableSize();
	}
	
	public long usedSize()
	{
		return conn.usedSize();
	}
	
	public long directorySize(boolean includeSubDirs) throws IOException
	{
		return conn.directorySize(includeSubDirs);
	}
	
	public long fileSize() throws IOException
	{
		return conn.fileSize();
	}
	
	public boolean canRead()
	{
		return conn.canRead();
	}
	
	public boolean canWrite()
	{
		return conn.canWrite();
	}
	
	public boolean isHidden()
	{
		return conn.isHidden();
	}
	
	public void setReadable(boolean readable) throws IOException
	{
		conn.setReadable(readable);
	}
	
	public void setWritable(boolean writable) throws IOException
	{
		conn.setWritable(writable);
	}
	
	public void setHidden(boolean hidden) throws IOException
	{
		conn.setHidden(hidden);
	}
	
	public Enumeration list() throws IOException
	{
		return conn.list();
	}
	
	public Enumeration list(String filter, boolean includeHidden) throws IOException
	{
		return conn.list(filter, includeHidden);
	}
	
	public void create() throws IOException
	{
		conn.create();
	}
	
	public void mkdir() throws IOException
	{
		conn.mkdir();
	}
	
	public boolean exists()
	{
		return conn.exists();
	}
	
	public boolean isDirectory()
	{
		return conn.isDirectory();
	}
	
	public void delete() throws IOException
	{
		conn.delete();
	}
	
	public void rename(String newName) throws IOException
	{
		conn.rename(newName);
	}
	
	public void truncate(long byteOffset) throws IOException
	{
		conn.truncate(byteOffset);
	}
	
	public void setFileConnection(String fileName) throws IOException
	{
		conn.setFileConnection(fileName);
	}
	
	public String getName()
	{
		return conn.getName();
	}
	
	public String getPath()
	{
		return conn.getPath();
	}
	
	public String getURL()
	{
		return conn.getURL();
	}
	
	public long lastModified()
	{
		return conn.lastModified();
	}
}

class MotorolaTypeAFileConnection implements com.one.file.FileConnection
{
	protected com.motorola.io.FileConnection conn;
	
	public static Enumeration listRoots()
	{
		return com.motorola.io.FileSystemRegistry.listRoots();
	}
	
	public MotorolaTypeAFileConnection(Connection conn)
	{
		this.conn = (com.motorola.io.FileConnection)conn;
	}
	
	public void close() throws IOException
	{
		conn.close();
	}
	
	public boolean isOpen()
	{
		return conn.isOpen();
	}
	
	public InputStream openInputStream() throws IOException
	{
		return conn.openInputStream();
	}
	
	public DataInputStream openDataInputStream() throws IOException
	{
		return conn.openDataInputStream();
	}
	
	public OutputStream openOutputStream() throws IOException
	{
		return conn.openOutputStream();
	}
	
	public DataOutputStream openDataOutputStream() throws IOException
	{
		return conn.openDataOutputStream();
	}
	
	public OutputStream openOutputStream(long byteOffset) throws IOException
	{
		return conn.openOutputStream(byteOffset);
	}
	
	public long totalSize()
	{
		return conn.totalSize();
	}
	
	public long availableSize()
	{
		return conn.availableSize();
	}
	
	public long usedSize()
	{
		return conn.usedSize();
	}
	
	public long directorySize(boolean includeSubDirs) throws IOException
	{
		return conn.directorySize(includeSubDirs);
	}
	
	public long fileSize() throws IOException
	{
		return conn.fileSize();
	}
	
	public boolean canRead()
	{
		return conn.canRead();
	}
	
	public boolean canWrite()
	{
		return conn.canWrite();
	}
	
	public boolean isHidden()
	{
		return conn.isHidden();
	}
	
	public void setReadable(boolean readable) throws IOException
	{
		conn.setReadable(readable);
	}
	
	public void setWritable(boolean writable) throws IOException
	{
		conn.setWritable(writable);
	}
	
	public void setHidden(boolean hidden) throws IOException
	{
		conn.setHidden(hidden);
	}
	
	public Enumeration list() throws IOException
	{
		return conn.list();
	}
	
	public Enumeration list(String filter, boolean includeHidden) throws IOException
	{
		return conn.list(filter, includeHidden);
	}
	
	public void create() throws IOException
	{
		conn.create();
	}
	
	public void mkdir() throws IOException
	{
		conn.mkdir();
	}
	
	public boolean exists()
	{
		return conn.exists();
	}
	
	public boolean isDirectory()
	{
		return conn.isDirectory();
	}
	
	public void delete() throws IOException
	{
		conn.delete();
	}
	
	public void rename(String newName) throws IOException
	{
		conn.rename(newName);
	}
	
	public void truncate(long byteOffset) throws IOException
	{
		conn.truncate(byteOffset);
	}
	
	public void setFileConnection(String fileName) throws IOException
	{
		conn.setFileConnection(fileName);
	}
	
	public String getName()
	{
		return conn.getName();
	}
	
	public String getPath()
	{
		return conn.getPath();
	}
	
	public String getURL()
	{
		return conn.getURL();
	}
	
	public long lastModified()
	{
		return conn.lastModified();
	}
}

class MotorolaTypeBFileConnection implements com.one.file.FileConnection
{
	protected com.motorola.io.file.FileConnection conn;
	
	public static Enumeration listRoots()
	{
		return com.motorola.io.file.FileSystemRegistry.listRoots();
	}
	
	public MotorolaTypeBFileConnection(Connection conn)
	{
		this.conn = (com.motorola.io.file.FileConnection)conn;
	}
	
	public void close() throws IOException
	{
		conn.close();
	}
	
	public boolean isOpen()
	{
		return conn.isOpen();
	}
	
	public InputStream openInputStream() throws IOException
	{
		return conn.openInputStream();
	}
	
	public DataInputStream openDataInputStream() throws IOException
	{
		return conn.openDataInputStream();
	}
	
	public OutputStream openOutputStream() throws IOException
	{
		return conn.openOutputStream();
	}
	
	public DataOutputStream openDataOutputStream() throws IOException
	{
		return conn.openDataOutputStream();
	}
	
	public OutputStream openOutputStream(long byteOffset) throws IOException
	{
		return conn.openOutputStream(byteOffset);
	}
	
	public long totalSize()
	{
		return conn.totalSize();
	}
	
	public long availableSize()
	{
		return conn.availableSize();
	}
	
	public long usedSize()
	{
		return conn.usedSize();
	}
	
	public long directorySize(boolean includeSubDirs) throws IOException
	{
		return conn.directorySize(includeSubDirs);
	}
	
	public long fileSize() throws IOException
	{
		return conn.fileSize();
	}
	
	public boolean canRead()
	{
		return conn.canRead();
	}
	
	public boolean canWrite()
	{
		return conn.canWrite();
	}
	
	public boolean isHidden()
	{
		return conn.isHidden();
	}
	
	public void setReadable(boolean readable) throws IOException
	{
		conn.setReadable(readable);
	}
	
	public void setWritable(boolean writable) throws IOException
	{
		conn.setWritable(writable);
	}
	
	public void setHidden(boolean hidden) throws IOException
	{
		conn.setHidden(hidden);
	}
	
	public Enumeration list() throws IOException
	{
		return conn.list();
	}
	
	public Enumeration list(String filter, boolean includeHidden) throws IOException
	{
		return conn.list(filter, includeHidden);
	}
	
	public void create() throws IOException
	{
		conn.create();
	}
	
	public void mkdir() throws IOException
	{
		conn.mkdir();
	}
	
	public boolean exists()
	{
		return conn.exists();
	}
	
	public boolean isDirectory()
	{
		return conn.isDirectory();
	}
	
	public void delete() throws IOException
	{
		conn.delete();
	}
	
	public void rename(String newName) throws IOException
	{
		conn.rename(newName);
	}
	
	public void truncate(long byteOffset) throws IOException
	{
		conn.truncate(byteOffset);
	}
	
	public void setFileConnection(String fileName) throws IOException
	{
		conn.setFileConnection(fileName);
	}
	
	public String getName()
	{
		return conn.getName();
	}
	
	public String getPath()
	{
		return conn.getPath();
	}
	
	public String getURL()
	{
		return conn.getURL();
	}
	
	public long lastModified()
	{
		return conn.lastModified();
	}
}