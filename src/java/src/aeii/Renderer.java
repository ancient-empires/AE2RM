package aeii;

// Decompiled by:       Fernflower v0.6
// Date:                22.01.2010 19:41:42
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.io.*;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;

public final class Renderer extends Canvas implements Runnable, CommandListener
{
	public static boolean devmode;
	public static boolean nointro;
	
	public static final Font fontB = Font.getFont(0, 1, 8);
	public static final Font fontA = Font.getFont(0, 1, 8);
	public static final int fontABaseLine = fontA.getBaselinePosition();
	public static final int fontABaseLineEx = fontABaseLine + 6;
	public static final int fontBHeight;
	public static final short[] var_17c;
	public static final short[] var_1dc;
	public static final byte[][] var_21f;
	private static Display dsp;
	private boolean running = false;
	private boolean var_2b1 = true;
	private int fpsDelay;
	private String fpsText = "";
	public PaintableObject currentDisplayable;
	public static int width;
	public static int height;
	public int gameActionsStateImmediate = 0;
	public int gameActionsStateDelayed;
	public int lastGameAction = 0;
	public long keyPressedTime;
	public static Sprite[] sprChars;
	public static Random rnd;
	public static boolean[] mainSettings;
	public static String[] var_5fa;
	public static int[] rgb;
	public boolean var_667 = false;
	public static int delayedMusic;
	public static int delayedLoopCount;
	public static boolean delayPlayerStart;
	public static final String[] MUSIC_NAMES;
	public static final byte[] var_7c7;
	public static Player[] players;
	public static Player currentPlayer;
	public static boolean[] playerReadyFlags;
	public static int currentMusic;
	public static int currentLoopCount;
	public static byte[][] resourceData;
	public static String[] resourceNames;

	public Renderer(MIDlet var1)
	{
		try
		{
			devmode = "true".equalsIgnoreCase(Main.main.getAppProperty("Developer-Mode"));
		}
		catch(Exception e)
		{
			devmode = false;
		}

		try
		{
			nointro = "true".equalsIgnoreCase(Main.main.getAppProperty("Disable-Intro"));
		}
		catch(Exception e)
		{
			nointro = false;
		}

		try
		{
			setFPS(Integer.parseInt(Main.main.getAppProperty("Override-FPS")));
		}
		catch(Exception e)
		{
			setFPS(15);
		}
		
		try
		{
			setFullScreenMode(true);
			PaintableObject.currentRenderer = this;
			PaintableObject.loadLocale("/lang.dat", false);
			width = getWidth();
			height = getHeight();
			dsp = Display.getDisplay(var1);
			dsp.setCurrent(this);
			(new Thread(this)).start();
		}
		catch(Exception var3)
		{
			var3.printStackTrace();
			showErrMsg(var3.toString());
		}
	}

	public static final int randomToRange(int var0)
	{
		return randomFromRange(0, var0);
	}

	public static final int randomFromRange(int var0, int var1)
	{
		return var0 + Math.abs(rnd.nextInt()) % (var1 - var0);
	}

	public static final int random()
	{
		return rnd.nextInt();
	}

	public static final int randomFromProbabilities(int[] probabilities)
	{
		int test = randomToRange(probabilities[probabilities.length - 1]);

		for(int i = 0; i < probabilities.length; i++)
		{
			if(test < probabilities[i])
			{
				return i;
			}
		}

		return -1;
	}

	public static final void convertProbabilities(int[] probabilities, boolean flag)
	{
		if(flag)
		{
			for(int i = 1; i < probabilities.length; i++)
			{
				probabilities[i] += probabilities[i - 1];
			}
		}
		else
		{
			for(int i = probabilities.length - 1; i >= 1; i--)
			{
				probabilities[i] -= probabilities[i - 1];
			}
		}
	}
	
	public static String readLine(InputStream is) throws IOException
	{
		StringBuffer res = new StringBuffer();
		char c;
		
		while(true)
		{
			c = (char)is.read();

			if(c == (char)-1)
			{
				if(res.length() > 0)
				{
					return res.toString();
				}
				else
				{
					return null;
				}
			}
			else if(c == '\n')
			{
				return res.toString();
			}
			else if(c != '\r')
			{
				res.append(c);
			}
		}
	}

	public static String[] tokenizeString(String str, char sep)
	{
		Vector v = new Vector();
		int index;

		while(str.length() > 0)
		{
			index = str.indexOf(sep);

			if(index >= 0)
			{
				v.addElement(str.substring(0, index));
				str = str.substring(index + 1);
			}
			else if(index == 0)
			{
				str = str.substring(1);
			}
			else
			{
				v.addElement(str);
				break;
			}
		}

		String[] res = new String[v.size()];
		v.copyInto(res);

		return res;
	}

	public static final byte[] getRMSData(String var0, int var1)
	{
		try
		{
			RecordStore var2;
			byte[] var3 = (var2 = RecordStore.openRecordStore(var0, false)).getRecord(var1 + 1);
			var2.closeRecordStore();
			return var3;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static final void setRMSData(String var0, int var1, byte[] var2)
	{
		try
		{
			RecordStore var3;
			int var4;
			if((var4 = (var3 = RecordStore.openRecordStore(var0, true)).getNumRecords()) <= var1)
			{
				while(var4 < var1)
				{
					var3.addRecord((byte[])null, 0, 0);
					++var4;
				}
	
				var3.addRecord(var2, 0, var2.length);
			}
			else
			{
				var3.setRecord(var1 + 1, var2, 0, var2.length);
			}
	
			var3.closeRecordStore();
		}
		catch(Exception e)
		{
		}
	}

	public static final int addRMSRecord(String var0, byte[] var1)
	{
		try
		{
			RecordStore var2;
			int var3 = (var2 = RecordStore.openRecordStore(var0, true)).addRecord(var1, 0, var1.length);
			var2.closeRecordStore();
			return var3 - 1;
		}
		catch(Exception e)
		{
			return -1;
		}
	}

	public static final void deleteRMSRecord(String var0, int var1)
	{
		try
		{
			RecordStore var2;
			(var2 = RecordStore.openRecordStore(var0, true)).deleteRecord(var1 + 1);
			var2.closeRecordStore();
		}
		catch(Exception e)
		{
		}
	}

	public static final int getAvailableRMSSize(String var0)
	{
		int var1 = 0;

		try
		{
			RecordStore var2;
			var1 = (var2 = RecordStore.openRecordStore(var0, true)).getSizeAvailable();
			var2.closeRecordStore();
		}
		catch(Exception var3)
		{
			;
		}

		return var1;
	}

	public static final int getGraphicStringWidth(byte var0, String var1)
	{
		return sprChars[var0].width * var1.length();
	}

	public static final int getGraphicFontHeight(byte var0)
	{
		return sprChars[var0].height;
	}

	public static final void setColor(Graphics var0, int var1)
	{
		var0.setColor(var1);
	}

	public static final void fillAlphaRect(Graphics var0, int color, int x, int y, int width, int height)
	{
		for(int index = width * height - 1; index >= 0; --index)
		{
			rgb[index] = color;
		}

		var0.drawRGB(rgb, 0, width, x, y, width, height, true);
	}

	public final void showNotify()
	{
		setFullScreenMode(true);
		
		var_667 = false;
		delayPlayerStart = false;
		clearKeyStates();
		if(currentDisplayable != null)
		{
			currentDisplayable.showNotify();
		}

	}

	public final void hideNotify()
	{
		clearKeyStates();
		if(currentDisplayable != null)
		{
			if(!var_667)
			{
				delayPlayerStart = true;
				if(currentPlayer != null && currentPlayer.getState() == 400 && var_7c7[currentMusic] == 1)
				{
					delayedMusic = currentMusic;
					delayedLoopCount = currentLoopCount;
				}

				stopCurrentPlayer();
			}

			var_667 = false;
		}

	}

	public static final void drawAlignedGraphicString(Graphics var0, String var1, int var2, int var3, int var4, int var5)
	{
		if((var5 & 8) != 0)
		{
			var2 -= getGraphicStringWidth((byte)var4, var1);
		}
		else if((var5 & 1) != 0)
		{
			var2 -= getGraphicStringWidth((byte)var4, var1) / 2;
		}

		if((var5 & 32) != 0)
		{
			var3 -= getGraphicFontHeight((byte)var4);
		}
		else if((var5 & 2) != 0)
		{
			var3 -= getGraphicFontHeight((byte)var4) / 2;
		}

		drawGraphicString(var0, var1, var2, var3, var4);
	}

	public static final void drawGraphicString(Graphics var0, String var1, int var2, int var3, int var4)
	{
		boolean var5 = false;
		int var8 = 0;

		for(int var9 = var1.length(); var8 < var9; ++var8)
		{
			char var7;
			if((var7 = var1.charAt(var8)) >= var_17c[var4] && var7 <= var_1dc[var4])
			{
				byte var6;
				if((var6 = var_21f[var4][var7 - var_17c[var4]]) != -1)
				{
					sprChars[var4].setCurrentFrame(var6);
					sprChars[var4].paint(var0, var2, var3);
					var2 += sprChars[var4].width;
				}
				else
				{
					byte[] var10 = new byte[] { (byte)var7 };
					String var11 = new String(var10);
					var0.drawString(var11, var2, var3, 20);
					var2 += var0.getFont().stringWidth(var11);
				}
			}
		}

	}

	public static final void drawString(Graphics var0, String var1, int var2, int var3, int var4)
	{
		var0.drawString(var1, var2, var3 - 2, var4);
	}

	public final void setCurrent(Displayable dp)
	{
		dsp.setCurrent(dp);
	}

	public final void setCurrentDisplayable(PaintableObject var1)
	{
		clearKeyStates();
		var1.showNotify();
		currentDisplayable = var1;
	}

	public final void repaintAndService()
	{
		repaint();
		serviceRepaints();
	}

	public final void paint(Graphics g)
	{
		if(var_2b1)
		{
			g.setColor(16777215);
			g.fillRect(0, 0, width, height);
			g.setFont(fontA);
			g.setColor(0);
			g.drawString(PaintableObject.getLocaleString(58), width / 2, height / 2 - 1, 33);
		}
		else
		{
			currentDisplayable.paint(g);
		}

		if(devmode)
		{
			g.setFont(fontA);
			MainDisplayable.drawOutlinedString(g, fpsText, 2, 2, 20, -1, 0);
		}
	}

	public final int getGameAction(int var1)
	{
		try
		{
			switch(var1)
			{
				case -11: // back
					return 0;
				case -7: // rsk
					return 2048;
				case -6: // lsk
					return 1024;
				case 35: // star
					return 8192;
				case 42: // pound
					return 16384;
				case 48: // 0
					return 32;
				case 49: // 1
					return 64;
				case 50: // 2
					return 1;
				case 51: // 3
					return 128;
				case 52: // 4
					return 4;
				case 53: // 5
					return 16;
				case 54: // 6
					return 8;
				case 55: // 7
					return 256;
				case 56: // 8
					return 2;
				case 57: // 9
					return 512;
				default:
					switch(super.getGameAction(var1))
					{
						case 1:
							return 1;
						case 2:
							return 4;
						case 3:
						case 4:
						case 7:
						default:
							break;
						case 5:
							return 8;
						case 6:
							return 2;
						case 8:
							return 16;
					}
			}
		}
		catch(Exception var2)
		{
			;
		}

		return 4096;
	}

	public final String getKeyNameEx(int var1)
	{
		byte var2 = 0;
		switch(var1)
		{
			case 1:
				var2 = 50;
				break;
			case 2:
				var2 = 56;
				break;
			case 4:
				var2 = 52;
				break;
			case 8:
				var2 = 54;
				break;
			case 16:
				var2 = 53;
				break;
			case 32:
				var2 = 48;
				break;
			case 64:
				var2 = 49;
				break;
			case 128:
				var2 = 51;
				break;
			case 256:
				var2 = 55;
				break;
			case 512:
				var2 = 57;
		}

		return super.getKeyName(var2);
	}

	public final void keyPressed(int var1)
	{
		int var2 = getGameAction(var1);
		handleKeyPressedGameAction(var2);

		if(currentDisplayable != null)
		{
			currentDisplayable.keyPressed(var1, var2);
		}

	}

	public final boolean isAnyKeyPressed()
	{
		return gameActionsStateImmediate != 0;
	}

	public final void clearKeyStates()
	{
		lastGameAction = 0;
		gameActionsStateImmediate = 0;
		gameActionsStateDelayed = 0;
	}

	public final boolean wasKeyPressed(int var1)
	{
		boolean var2 = (gameActionsStateDelayed & var1) != 0;
		gameActionsStateDelayed &= ~var1;
		return var2;
	}

	public final boolean isKeyPressed(int var1)
	{
		return (gameActionsStateImmediate & var1) != 0;
	}

	public final void keyReleased(int var1)
	{
		handleKeyReleasedGameAction(getGameAction(var1));
	}

	public final boolean isKeyHeld(int var1)
	{
		return lastGameAction == var1 && System.currentTimeMillis() - keyPressedTime >= 400L;
	}

	public final void handleKeyPressedGameAction(int var1)
	{
		lastGameAction = var1;
		keyPressedTime = System.currentTimeMillis();
		gameActionsStateImmediate |= var1;
		gameActionsStateDelayed |= var1;
	}

	public final void handleKeyReleasedGameAction(int var1)
	{
		if(var1 == lastGameAction)
		{
			lastGameAction = 0;
		}

		gameActionsStateImmediate &= ~var1;
	}

	public final void showErrMsg(String var1)
	{
		running = false;
		Form var2;
		(var2 = new Form("Fatal error!")).append(var1);
		Command var3 = new Command("Exit", 7, 1);
		var2.addCommand(var3);
		var2.setCommandListener(this);
		dsp.setCurrent(var2);
	}

	public final void stop()
	{
		running = false;
	}

	public static final void loadCharSprites()
	{
		sprChars[0] = new Sprite("chars");
		sprChars[1] = new Sprite("lchars");
	}

	public final void setFPS(int fps)
	{
		if(fps <= 0)
		{
			fpsDelay = 0;
		}
		else
		{
			fpsDelay = 1000 / fps;
		}
	}

	public final void run()
	{
		try
		{
			repaintAndService();
			var_5fa = new String[] { PaintableObject.getLocaleString(26), PaintableObject.getLocaleString(28), PaintableObject.getLocaleString(25), PaintableObject.getLocaleString(24) };
			MainDisplayable var2 = new MainDisplayable();
			repaintAndService();
			currentDisplayable = var2;
			var_2b1 = false;
			running = true;
			var2.load();
			rgb = new int[width * height];

			long fpsPrevTime = System.currentTimeMillis();
			int fpsTimeDelta = 0;
			int fpsFrameCount = 0;

			while(running)
			{
				long prevTime = System.currentTimeMillis();

				if(isShown() && !delayPlayerStart)
				{
					if(delayedMusic >= 0)
					{
						startPlayer(delayedMusic, delayedLoopCount);
						if(currentPlayer != null && currentPlayer.getState() == 400)
						{
							delayedMusic = -1;
						}
					}

					currentDisplayable.update();
					repaintAndService();
				}

				if(devmode)
				{
					fpsTimeDelta = (int)(System.currentTimeMillis() - fpsPrevTime);
					fpsFrameCount++;

					if(fpsTimeDelta >= 1000)
					{
						fpsText = Integer.toString(fpsFrameCount * 1000 / fpsTimeDelta);

						fpsPrevTime = System.currentTimeMillis();
						fpsFrameCount = 0;
					}
				}

				int time = (int)(System.currentTimeMillis() - prevTime);
				int delay = fpsDelay - time;

				if(delay > 0)
				{
					try
					{
						Thread.sleep((long)delay);
					}
					catch(Exception var7)
					{
						;
					}
				}
			}

			if(Main.main != null)
			{
				Main.main.notifyDestroyed();
				Main.main.destroyApp(true);
			}
		}
		catch(Exception var8)
		{
			var8.printStackTrace();
			showErrMsg(var8.toString());
			return;
		}

	}

	public static final void vibrate(int var0)
	{
		try
		{
			if(mainSettings[1])
			{
				dsp.vibrate(var0 * 4);
			}
		}
		catch(Exception var1)
		{
			return;
		}

	}

	public static final void createPlayerArrays()
	{
		players = new Player[MUSIC_NAMES.length];
		playerReadyFlags = new boolean[MUSIC_NAMES.length];
	}

	public static final void createPlayer(int var0)
	{
		try
		{
			playerReadyFlags[var0] = false;
			InputStream var1 = getResourceAsStream(MUSIC_NAMES[var0] + ".mid");
			players[var0] = Manager.createPlayer(var1, "audio/midi");
			players[var0].realize();
			players[var0].prefetch();
			playerReadyFlags[var0] = true;
		}
		catch(Exception var2)
		{
			;
		}
	}

	public static final void startPlayer_(int var0, int var1)
	{
		startPlayer(var0, var1);
	}

	public static final void stopCurrentPlayer()
	{
		try
		{
			if(currentPlayer != null)
			{
				currentPlayer.stop();
				currentPlayer = null;
				currentMusic = -1;
			}
		}
		catch(Exception var0)
		{
			return;
		}

	}

	public static final void startPlayer(int var0, int var1)
	{
		try
		{
			if(!playerReadyFlags[var0])
			{
				return;
			}

			if(currentPlayer != null)
			{
				currentPlayer.stop();
			}

			if(var_7c7[var0] == 1 && mainSettings[0])
			{
				if(var1 == 0)
				{
					var1 = -1;
				}

				if(delayPlayerStart)
				{
					delayedMusic = var0;
					delayedLoopCount = var1;
				}
				else
				{
					currentPlayer = players[var0];
					currentPlayer.setLoopCount(var1);
					currentPlayer.start();
					currentMusic = var0;
					currentLoopCount = var1;
				}
			}
		}
		catch(Exception var2)
		{
			return;
		}

	}

	public static final void stopPlayer(int var0)
	{
		try
		{
			if(!playerReadyFlags[var0])
			{
				return;
			}

			if(currentPlayer == players[var0])
			{
				currentPlayer.stop();
				currentPlayer = null;
				currentMusic = -1;
			}
		}
		catch(Exception var1)
		{
			return;
		}

	}

	public static final void loadResources() throws IOException
	{
		if(resourceNames == null)
		{
			try
			{
				DataInputStream var4 = new DataInputStream(Main.main.getClass().getResourceAsStream("/res.pak"));

				short var5 = var4.readShort();
				short var6 = var4.readShort();

				resourceNames = new String[var6];
				int[] var9 = new int[var6];
				int[] var10 = new int[var6];

				for(int var7 = 0; var7 < var6; ++var7)
				{
					resourceNames[var7] = var4.readUTF();
					var9[var7] = var4.readInt() + var5;
					var10[var7] = var4.readShort();
				}

				resourceData = new byte[resourceNames.length][];

				for(int var8 = 0; var8 < resourceNames.length; ++var8)
				{
					resourceData[var8] = new byte[var10[var8]];
					var4.readFully(resourceData[var8]);
				}

				var4.close();
			}
			catch(Exception e)
			{
			}
		}

	}

	public static final byte[] getResource(String var0)
	{
		if(resourceNames != null)
		{
			for(int var1 = 0; var1 < resourceNames.length; ++var1)
			{
				if(var0.equals(resourceNames[var1]))
				{
					return resourceData[var1];
				}
			}
		}

		try
		{
			InputStream is = Main.main.getClass().getResourceAsStream("/res/" + var0);

			if(is != null)
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int b;

				while((b = is.read()) >= 0)
				{
					baos.write(b);
				}

				is.close();

				byte[] res = baos.toByteArray();
				baos.close();

				return res;
			}
		}
		catch(IOException ioe)
		{
		}

		return null;
	}

	public static final InputStream getResourceAsStream(String var0)
	{
		if(resourceNames != null)
		{
			for(int var1 = 0; var1 < resourceNames.length; ++var1)
			{
				if(var0.equals(resourceNames[var1]))
				{
					return new ByteArrayInputStream(resourceData[var1]);
				}
			}
		}

		return Main.main.getClass().getResourceAsStream("/res/" + var0);
	}

	public final void commandAction(Command var1, Displayable var2)
	{
		Main.main.notifyDestroyed();
	}

	static
	{
		fontB.getBaselinePosition();
		fontBHeight = fontB.getHeight();
		var_17c = new short[] { (short)45, (short)43 };
		var_1dc = new short[] { (short)57, (short)57 };
		var_21f = new byte[][] { { (byte)10, (byte)11, (byte)-1, (byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9 }, { (byte)12, (byte)-1, (byte)11, (byte)-1, (byte)10, (byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9 } };
		sprChars = new Sprite[2];
		rnd = new Random();
		mainSettings = new boolean[] { true, true, true, true };
		delayedMusic = -1;
		delayPlayerStart = false;
		MUSIC_NAMES = new String[] { "main_theme", "bg_story", "bg_good", "bg_bad", "battle_good", "battle_bad", "victory", "gameover", "game_complete" };
		var_7c7 = new byte[] { (byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1, (byte)1 };
	}
}
