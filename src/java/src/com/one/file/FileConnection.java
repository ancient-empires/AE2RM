package com.one.file;

import java.io.*;
import java.util.Enumeration;
import javax.microedition.io.StreamConnection;

public interface FileConnection extends StreamConnection
{
	public abstract boolean isOpen();
	public abstract InputStream openInputStream() throws IOException;
	public abstract DataInputStream openDataInputStream() throws IOException;
	public abstract OutputStream openOutputStream() throws IOException;
	public abstract DataOutputStream openDataOutputStream() throws IOException;
	public abstract OutputStream openOutputStream(long byteOffset) throws IOException;
	public abstract long totalSize();
	public abstract long availableSize();
	public abstract long usedSize();
	public abstract long directorySize(boolean includeSubDirs) throws IOException;
	public abstract long fileSize() throws IOException;
	public abstract boolean canRead();
	public abstract boolean canWrite();
	public abstract boolean isHidden();
	public abstract void setReadable(boolean readable) throws IOException;
	public abstract void setWritable(boolean writable) throws IOException;
	public abstract void setHidden(boolean hidden) throws IOException;
	public abstract Enumeration list() throws IOException;
	public abstract Enumeration list(String filter, boolean includeHidden) throws IOException;
	public abstract void create() throws IOException;
	public abstract void mkdir() throws IOException;
	public abstract boolean exists();
	public abstract boolean isDirectory();
	public abstract void delete() throws IOException;
	public abstract void rename(String newName) throws IOException;
	public abstract void truncate(long byteOffset) throws IOException;
	public abstract void setFileConnection(String fileName) throws IOException;
	public abstract String getName();
	public abstract String getPath();
	public abstract String getURL();
	public abstract long lastModified();
}