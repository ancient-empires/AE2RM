package aeii;

// Decompiled by:       Fernflower v0.6
// Date:                22.01.2010 19:41:44
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.io.*;
import javax.microedition.lcdui.Graphics;

public class Sprite
{

	public SpriteFrame[] frames;
	private byte[] currentFrameSequence;
	public int currentFrame = 0;
	public int currentX = 0;
	public int currentY = 0;
	public boolean visible = true;
	public int width;
	public int height;
	public byte[][] frameSequences;
	public int bounceValue;
	public byte mode = 0;
	public int bounceMode = -1;
	public int delayCounter;
	public int frameChangeCounterTarget;
	public int shiftX;
	public int shiftY;
	public int bounceDelta;
	public boolean active = true;
	public boolean autoDeactivate;
	public boolean var_379;
	public int shakeCounter = -1;
	public byte var_3b9 = -1;
	public boolean bouncing;
	public int var_3ef;
	public String text;
	public int fontSize;
	public Sprite var_4e4;
	public Sprite var_52f;
	public int[][] rectPos;
	public short[][] rectSpeed;
	public int color = 0xFFE000;
	public byte[] rectSize;
	public boolean[] rectPaintFlags;


	public Sprite(String var1)
	{
		readSpriteData(var1, -1);
	}

	public Sprite(SpriteFrame[] var1)
	{
		frames = var1;
		currentFrameSequence = new byte[frames.length];

		for(byte var2 = 0; var2 < frames.length; currentFrameSequence[var2] = var2++)
		{
			;
		}

		width = frames[0].imgwidth;
		height = frames[0].imgheight;
	}

	public Sprite(String var1, byte var2)
	{
		readSpriteData(var1, var2);
	}

	private void readSpriteData(String name, int fraction)
	{
		try
		{
			byte count = 0;
			
			int[][] framedef = null;
			int[][] translate = null;
			int align = 0;
			
			InputStream is = Renderer.getResourceAsStream(name + ".sprite");
			
			if(is != null)
			{
				String[] line;
				String s;
				int index;
				
				while(true)
				{
					s = Renderer.readLine(is);
					
					if(s == null)
					{
						break;
					}
					
					index = s.indexOf(';');

					if(index >= 0)
					{
						s = s.substring(0, index);
					}

					s = s.trim();

					if(s.length() == 0)
					{
						continue;
					}

					line = Renderer.tokenizeString(s, ' ');
					
					if(line[0].equalsIgnoreCase("FrameCount"))
					{
						count = (byte)Integer.parseInt(line[1]);
						
						framedef = new int[count][2];
						translate = new int[count][2];
					}
					else if(line[0].equalsIgnoreCase("FrameWidth"))
					{
						width = Integer.parseInt(line[1]);
					}
					else if(line[0].equalsIgnoreCase("FrameHeight"))
					{
						height = Integer.parseInt(line[1]);
					}
					else if(line[0].equalsIgnoreCase("FrameDef"))
					{
						index = Integer.parseInt(line[1]);
						
						framedef[index][0] = Integer.parseInt(line[2]);
						framedef[index][1] = Integer.parseInt(line[3]);
					}
					else if(line[0].equalsIgnoreCase("Align"))
					{
						align = Integer.parseInt(line[1]);
					}
					else if(line[0].equalsIgnoreCase("FrameSeqCount"))
					{
						index = Integer.parseInt(line[1]);
						
						if(index > 0)
						{
							frameSequences = new byte[index][];
						}
					}
					else if(line[0].equalsIgnoreCase("FrameDelay"))
					{
						frameChangeCounterTarget = Integer.parseInt(line[1]) * 50;
					}
					else if(line[0].equalsIgnoreCase("FrameSeq"))
					{
						index = Integer.parseInt(line[1]);
						
						frameSequences[index] = new byte[Integer.parseInt(line[2])];
						
						for(int i = 0; i < frameSequences[index].length; i++)
						{
							frameSequences[index][i] = (byte)Integer.parseInt(line[3 + i]);
						}
					}
					else if(line[0].equalsIgnoreCase("Translate"))
					{
						index = Integer.parseInt(line[1]);
						
						translate[index][0] = Integer.parseInt(line[2]);
						translate[index][1] = Integer.parseInt(line[3]);
					}
				}
			}
			else
			{
				is = Renderer.getResourceAsStream(name + ".sprbin");
				
				count = (byte)is.read();
				
				framedef = new int[count][2];
				translate = new int[count][2];
				
				width = (byte)is.read();
				height = (byte)is.read();

				int var7;
				int var9;
				int var10;
				int var18;

				int var17;

				for(var17 = 0; var17 < count; ++var17)
				{
					framedef[var17][0] = is.read();
					framedef[var17][1] = is.read();
				}

				align = is.read();

				var7 = is.read();

				if(var7 > 0)
				{
					frameSequences = new byte[var7][];
					frameChangeCounterTarget = is.read() * 50;

					for(var18 = 0; var18 < var7; ++var18)
					{
						var9 = is.read();
						frameSequences[var18] = new byte[var9];

						for(var10 = 0; var10 < var9; ++var10)
						{
							frameSequences[var18][var10] = (byte)is.read();
						}
					}
				}

				for(var18 = 0; var18 < count; ++var18)
				{
					byte var16 = (byte)is.read();
					byte var15 = (byte)is.read();

					if(var16 == -1 || var15 == -1)
					{
						break;
					}

					translate[var18][0] = var16;
					translate[var18][1] = var15;
				}
			}
			
			is.close();
			
			frames = new SpriteFrame[count];
			SpriteFrame[] sfmTempFrames = new SpriteFrame[count];

			try
			{
				SpriteFrame var6 = new SpriteFrame(name, fraction);

				int var7 = var6.imgwidth / width;
				int var18 = var6.imgheight / height;

				int var9 = 0;

				for(int var10 = 0; var10 < var18; ++var10)
				{
					for(int var11 = 0; var11 < var7; ++var11)
					{
						sfmTempFrames[var9] = new SpriteFrame(var6, var11, var10, width, height);
						++var9;
					}
				}
			}
			catch(Exception var13)
			{
				try
				{
					for(int var7 = 0; var7 < count; ++var7)
					{
						StringBuffer var8 = new StringBuffer(name);

						var8.append('_');

						if(var7 < 10)
						{
							var8.append('0');
						}

						var8.append(var7);

						if(fraction < 0)
						{
							sfmTempFrames[var7] = new SpriteFrame(var8.toString());
						}
						else
						{
							sfmTempFrames[var7] = new SpriteFrame(var8.toString(), fraction);
						}
					}
				}
				catch(Exception var12)
				{
				}
			}

			for(int i = 0; i < count; ++i)
			{
				frames[i] = new SpriteFrame(sfmTempFrames[framedef[i][0]], framedef[i][1]);
			}

			if(align > 0)
			{
				for(int i = 0; i < count; ++i)
				{
					frames[i].align(align, width, height);
					frames[i].translate(translate[i][0], translate[i][1]);
				}
			}
			else
			{
				for(int i = 0; i < count; ++i)
				{
					frames[i].translate(translate[i][0], translate[i][1]);
				}
			}

			if(frameSequences != null)
			{
				currentFrameSequence = frameSequences[0];
			}
			else
			{
				currentFrameSequence = new byte[frames.length];
				for(byte var14 = 0; var14 < frames.length; currentFrameSequence[var14] = var14++);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Sprite(Sprite var1)
	{
		frames = var1.frames;
		currentFrameSequence = var1.currentFrameSequence;
		currentFrame = var1.currentFrame;
		currentX = var1.currentX;
		currentY = var1.currentY;
		bounceValue = var1.bounceValue;
		visible = var1.visible;
		width = var1.width;
		height = var1.height;
		frameChangeCounterTarget = var1.frameChangeCounterTarget;
		frameSequences = var1.frameSequences;
	}

	public Sprite(int var1, int var2)
	{
		width = var1;
		height = var2;
	}

	public final int getFrameSequenceLength()
	{
		return currentFrameSequence.length;
	}

	public final int getFrameCount()
	{
		return frames.length;
	}

	public final void setCurrentFrame(int var1)
	{
		if(var1 < currentFrameSequence.length)
		{
			currentFrame = (byte)var1;
		}

	}

	public final void setPosition(int var1, int var2)
	{
		currentX = (short)var1;
		currentY = (short)var2;
	}

	public final void nextFrame()
	{
		++currentFrame;
		if(currentFrame >= currentFrameSequence.length)
		{
			currentFrame = 0;
		}

	}

	public final void setExternalFrameSequence(byte[] var1)
	{
		currentFrameSequence = var1;
		currentFrame = 0;
		delayCounter = 0;
	}

	public final void setFrameSequence(int var1, boolean var2)
	{
		if(frameSequences != null && var1 <= frameSequences.length)
		{
			byte[] newFrameSequence = frameSequences[var1];

			if(var2)
			{
				byte[] temp = new byte[newFrameSequence.length];

				for(int i = 0; i < temp.length; ++i)
				{
					temp[i] = (byte)(newFrameSequence[i] + getFrameCount() / 2);
				}

				newFrameSequence = temp;
			}

			setExternalFrameSequence(newFrameSequence);
		}

	}

	public final void paintFrame(Graphics var1, int var2, int var3, int var4, int var5)
	{
		if(mode != 2 && mode != 4 && mode != 3)
		{
			if(visible)
			{
				int var6 = currentX + var3;
				int var7 = currentY + var4;
				frames[var2].paintEx(var1, var6, var7, var5);
			}

		}
		else
		{
			paint(var1, var3, var4);
		}
	}

	public final void paintCurrentFrame(Graphics var1, int var2, int var3, int var4)
	{
		paintFrame(var1, currentFrameSequence[currentFrame], var2, var3, var4);
	}

	public void paint(Graphics g, int offsetX, int offsetY)
	{
		int x;
		int y;

		if(mode != 2 && mode != 4)
		{
			if(mode == 6)
			{
				if(currentFrame == 0)
				{
					g.setColor(0xEFD700);
				}
				else
				{
					g.setColor(0xFFFFFF);
				}

				if(shiftX > 0)
				{
					y = currentX + 15;
					g.fillArc(currentX, currentY - 15, 30, 30, 0, 360);
					g.fillRect(y, currentY - 15, Renderer.width - y, 30);
					return;
				}

				g.fillArc(currentX - 30, currentY - 15, 30, 30, 0, 360);
				g.fillRect(0, currentY - 15, currentX - 15, 30);

				return;
			}

			if(mode == 3)
			{
				g.setColor(0);

				if(shiftX > 0)
				{
					g.drawLine(currentX, currentY, currentX + 4, currentY - 2);
					return;
				}

				g.drawLine(currentX - 4, currentY - 2, currentX, currentY);
				return;
			}

			if(visible)
			{
				x = currentX + offsetX;
				y = currentY + offsetY;

				if(text != null)
				{
					Renderer.drawAlignedGraphicString(g, text, x, y, fontSize, 33);
					return;
				}

				if(shakeCounter > 0)
				{
					x += Renderer.randomFromRange(-4, 5);
					y += Renderer.randomFromRange(-1, 2);
				}

				byte frame = currentFrameSequence[currentFrame];
				frames[frame].paint(g, x, y);

				if(var_4e4 != null)
				{
					int var7 = frame % (getFrameCount() / 2);
					Sprite var8;

					if(var7 == 2)
					{
						var8 = var_52f;
					}
					else
					{
						var8 = var_4e4;
						var_4e4.setCurrentFrame(var7);
					}

					var8.paint(g, x, y);
				}
			}
		}
		else
		{
			g.setColor(color);

			for(int i = 0; i < 5; ++i)
			{
				if(rectPaintFlags[i])
				{
					x = (rectPos[i][0] >> 10) + offsetX + currentX;
					y = (rectPos[i][1] >> 10) + offsetY + currentY;
					g.fillRect(x, y, rectSize[i], rectSize[i]);
				}
			}
		}

	}

	public static final Sprite createGraphicTextSprite(String text, int var1, int var2, byte size)
	{
		int strWidth = Renderer.getGraphicStringWidth(size, text);
		int strHeight = Renderer.getGraphicFontHeight(size);
		Sprite var6;
		(var6 = new Sprite(strWidth, strHeight)).fontSize = size;
		var6.text = text;
		var6.shiftX = var1;
		var6.bounceDelta = var2;
		var6.mode = 5;
		return var6;
	}

	public static final Sprite createSimpleSparkSprite(Sprite baseSprite, int x, int y, int bounceDelta, int bounceMode, int delay, byte mode)
	{
		Sprite sprRes = null;

		if(baseSprite != null)
		{
			sprRes = new Sprite(baseSprite);
		}
		else
		{
			sprRes = new Sprite(0, 0);

			if(mode == 2 || mode == 4)
			{
				if(mode == 4)
				{
					sprRes.color = 0xEEEEFF;
				}

				sprRes.rectPos = new int[5][2];
				sprRes.rectSpeed = new short[5][2];
				sprRes.rectSize = new byte[5];
				sprRes.rectPaintFlags = new boolean[5];

				for(int i = 0; i < 5; ++i)
				{
					sprRes.rectPaintFlags[i] = true;

					if(mode == 4)
					{
						sprRes.rectSpeed[i][0] = (short)(Renderer.rnd.nextInt() % 4 << 10);
						sprRes.rectSpeed[i][1] = (short)(Renderer.rnd.nextInt() % 4 << 10);
					}
					else
					{
						sprRes.rectSpeed[i][0] = (short)(Math.abs(Renderer.rnd.nextInt()) % 8192 + -4096);
						sprRes.rectSpeed[i][1] = (short)(Math.abs(Renderer.rnd.nextInt()) % 4096 + -2048);
					}

					sprRes.rectSize[i] = (byte)(Math.abs(Renderer.rnd.nextInt()) % 2 + 1);
				}
			}
		}

		sprRes.mode = mode;
		sprRes.bounceMode = bounceMode;
		sprRes.frameChangeCounterTarget = delay;
		sprRes.shiftX = x;
		sprRes.shiftY = y;
		sprRes.bounceDelta = bounceDelta;
		sprRes.autoDeactivate = true;

		return sprRes;
	}

	public void update()
	{
		if(active)
		{
			delayCounter += 50;

			if(shakeCounter >= 0)
			{
				--shakeCounter;
			}

			switch(mode)
			{
				case 2:
				case 4:
					updateSimpleSparkSprite();
					return;
				case 3:
					setPosition(currentX + shiftX, currentY + shiftY);
					return;
				case 5:
					if(bounceMode == -1)
					{
						setPosition(currentX + shiftX, currentY);

						bounceValue += bounceDelta;

						if(bounceValue < 0)
						{
							++bounceDelta;
							return;
						}

						bounceValue = 0;
						bounceDelta = -bounceDelta / 2;

						if(bounceDelta == 0)
						{
							bounceMode = 1;
							delayCounter = 0;
							return;
						}
					}
					else if(delayCounter >= 400)
					{
						active = false;
						return;
					}
					break;
				case 6:
					currentFrame = (currentFrame + 1) % 2;
					if(delayCounter >= frameChangeCounterTarget)
					{
						active = false;
						return;
					}
					break;
				default:
					setPosition(currentX + shiftX, currentY + shiftY);
					bounceValue += bounceDelta;

					if(bounceMode != 0 && delayCounter >= frameChangeCounterTarget)
					{
						nextFrame();

						if(mode == 0 && currentFrame == 0 && bounceMode > 0)
						{
							--bounceMode;

							if(bounceMode <= 0)
							{
								setCurrentFrame(getFrameSequenceLength() - 1);

								if(autoDeactivate)
								{
									active = false;
								}
							}
						}

						delayCounter = 0;
					}
			}
		}

	}

	public final void updateSimpleSparkSprite()
	{
		if(mode != 4)
		{
			color += -0x040400; // 0xFFFBFC00;
		}

		for(int i = 0; i < 5; ++i)
		{
			if(rectPaintFlags[i])
			{
				if(mode == 4)
				{
					rectPos[i][0] += rectSpeed[i][0];
					rectPos[i][1] += rectSpeed[i][1];

					if(rectSpeed[i][0] < 0)
					{
						rectSpeed[i][0] = (short)(rectSpeed[i][0] + 256);
					}
					else if(rectSpeed[i][0] > 0)
					{
						rectSpeed[i][0] = (short)(rectSpeed[i][0] - 256);
					}

					if(rectSpeed[i][1] < 0)
					{
						rectSpeed[i][1] = (short)(rectSpeed[i][1] + 256);
					}
					else if(rectSpeed[i][1] > 0)
					{
						rectSpeed[i][1] = (short)(rectSpeed[i][1] - 256);
					}
				}
				else
				{
					rectPos[i][0] += rectSpeed[i][0];
					rectPos[i][1] += rectSpeed[i][1];
					rectSpeed[i][1] = (short)(rectSpeed[i][1] + 256);
				}
			}
		}

		if(delayCounter >= frameChangeCounterTarget)
		{
			active = false;
		}

	}
}
