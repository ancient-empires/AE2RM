package aeii;

// Decompiled by:       Fernflower v0.6
// Date:                22.01.2010 19:41:05
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import javax.microedition.midlet.MIDlet;

public class Main extends MIDlet
{

	public static Main main;
	public static Renderer renderer;


	public final void startApp()
	{
		if(main == null)
		{
			main = this;
			renderer = new Renderer(this);
		}

	}

	public final void destroyApp(boolean var1)
	{
		if(renderer != null)
		{
			renderer.stop();
		}

		renderer = null;
		main = null;
	}

	public final void pauseApp()
	{
	}
}
