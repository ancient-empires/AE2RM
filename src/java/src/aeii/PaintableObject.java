package aeii;

// Decompiled by:       Fernflower v0.6
// Date:                22.01.2010 19:41:05
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.io.*;
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class PaintableObject
{

	public static Renderer currentRenderer;
	public int width;
	public int height;
	public int halfWidth;
	public int halfHeight;
	public static String[] localeStrings;
	public static short[] sintab = null;
	public static int FULL_CIRCLE = 360;
	public static int HALF_CIRCLE = 0;
	public static int QUARTER_CIRCLE = 0;


	public PaintableObject()
	{
		width = currentRenderer.getWidth();
		height = currentRenderer.getHeight();
		halfWidth = width >> 1;
		halfHeight = height >> 1;
	}

	public void showNotify()
	{
	}

	public void keyPressed(int var1, int var2)
	{
	}

	public void update()
	{
	}

	public void paint(Graphics var1)
	{
	}

	public static final String[] splitStringMultiline(String var0, int var1, Font var2)
	{
		Vector var3 = new Vector();
		int var4 = 0;
		boolean var5 = false;
		int var6 = var0.length();
		String var7 = null;

		while(true)
		{
			int var9 = var4;
			int var10 = var0.indexOf(10, var4);

			while(true)
			{
				int var11 = var9;
				String var8 = var7;
				var9 = sub_aa(var0, var9);
				if(var10 > -1 && var10 < var9)
				{
					var9 = var10;
				}

				var7 = var0.substring(var4, var9).trim();
				if(var2.stringWidth(var7) > var1)
				{
					if(var11 == var4)
					{
						for(int var12 = var7.length() - 1; var12 > 0; --var12)
						{
							String var13 = var7.substring(0, var12);
							if(var2.stringWidth(var13) <= var1)
							{
								var9 = var11 + var12;
								var7 = var13;
								break;
							}
						}
					}
					else
					{
						var9 = var11;
						var7 = var8;
					}
				}
				else if(var9 == var10)
				{
					++var9;
				}
				else if(var9 < var6)
				{
					continue;
				}

				var3.addElement(var7);
				var4 = var9;
				if(var9 >= var6)
				{
					String[] var14 = new String[var3.size()];
					var3.copyInto(var14);
					return var14;
				}
				break;
			}
		}
	}

	private static final int sub_aa(String var0, int var1)
	{
		if(sub_102(var0.charAt(var1)))
		{
			return var1 + 1;
		}
		else
		{
			int var4;
			for(boolean var3 = false; (var4 = var0.indexOf(32, var1)) == 0; ++var1)
			{
				;
			}

			int var5;
			if(var4 == -1)
			{
				var5 = var0.length();
			}
			else
			{
				var5 = var4 + 1;
			}

			for(var4 = var1 + 1; var4 < var5; ++var4)
			{
				if(sub_102(var0.charAt(var4)))
				{
					return var4;
				}
			}

			return var5;
		}
	}

	private static final boolean sub_102(int var0)
	{
		return var0 >= 11904 && var0 < '\uac00' || var0 >= '\uf900' && var0 < '\ufb00' || var0 >= '\uff00' && var0 < '\uffe0';
	}

	public static final int loadLocale(String var0, boolean var1) throws IOException
	{
		DataInputStream dis = new DataInputStream(Main.main.getClass().getResourceAsStream(var0));

		localeStrings = new String[dis.readInt()];
		int var4 = 0;

		for(int var5 = localeStrings.length; var4 < var5; ++var4)
		{
			String var6 = dis.readUTF();
			localeStrings[var4] = var6;
		}

		dis.close();

		return localeStrings.length;
	}
	
public void readLocale(DataInputStream dis) throws IOException
{
	int count = dis.readInt();
	String[] locale = new String[count];

	for(int i = 0; i < count; i++)
	{
		locale[i] = dis.readUTF();
	}
}

	public static final String getLocaleString(int var0)
	{
		return getLocaleStringEx(var0, false);
	}

	public static final String getLocaleStringEx(int var0, boolean var1)
	{
		if(var0 < localeStrings.length)
		{
			String var2 = localeStrings[var0];
			if(var1 && (var2 = replaceText(replaceText(replaceText(replaceText(var2, "%K5", getReplacedLocaleString(20, currentRenderer.getKeyNameEx(16)), true), "%K0", currentRenderer.getKeyNameEx(32), true), "%K7", currentRenderer.getKeyNameEx(256), true), "%K9", currentRenderer.getKeyNameEx(512), true)).indexOf("%KM") != -1)
			{
				StringBuffer var3 = new StringBuffer();
				String[] var4 = new String[] { currentRenderer.getKeyNameEx(1), currentRenderer.getKeyNameEx(2), currentRenderer.getKeyNameEx(4), currentRenderer.getKeyNameEx(8) };
				var3.append(sub_1c1(17, var4));
				if(var3.length() > 0)
				{
					var3.append('/');
				}

				var3.append(getLocaleString(18));
				if(var3.length() > 0)
				{
					var3.append('/');
				}

				var3.append(getLocaleString(19));
				var2 = replaceText(var2, "%KM", var3.toString(), true);
			}

			return var2;
		}
		else
		{
			return "?: " + var0;
		}
	}

	public static final String sub_1c1(int var0, String[] var1)
	{
		String var2 = new String(getLocaleString(var0));

		for(int var3 = 0; var3 < var1.length; ++var3)
		{
			var2 = replaceText(var2, "%U", var1[var3], false);
		}

		return var2;
	}

	public static final String getReplacedLocaleString(int var0, String var1)
	{
		return replaceText(getLocaleString(var0), "%U", var1, false);
	}

	public static final String replaceText(String text, String replaceWhat, String replaceWith, boolean replaceAll)
	{
		String temp = text;

		int index;
		while((index = temp.indexOf(replaceWhat)) != -1)
		{
			temp = temp.substring(0, index) + replaceWith + temp.substring(index + replaceWhat.length());
			if(!replaceAll)
			{
				break;
			}
		}

		return temp;
	}

	public static final void createSinTab()
	{
		boolean var0 = false;
		HALF_CIRCLE = FULL_CIRCLE >> 1;
		QUARTER_CIRCLE = HALF_CIRCLE >> 1;
		sintab = new short[FULL_CIRCLE];
		int var1 = FULL_CIRCLE * 10000 / 2 / 31415;
		int var2 = 1024 * var1;
		int var3 = 0;

		for(int var5 = 0; var5 < FULL_CIRCLE; ++var5)
		{
			int var4 = var3 / var1;
			sintab[var5] = (short)var4;
			var2 -= var4;
			var3 += var2 / var1;
		}

		sintab[180] = 0;
		sintab[270] = -1024;
	}

	public static final short sin(int var0)
	{
		var0 %= 360;
		return sintab[var0];
	}

	public static final short cos(int var0)
	{
		var0 = (var0 + QUARTER_CIRCLE) % 360;
		return sintab[var0];
	}

}
