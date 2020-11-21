package aeii;

// Decompiled by:       Fernflower v0.6
// Date:                22.01.2010 19:41:41
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public final class AuxDisplayable extends PaintableObject
{

	public boolean[] drawSoftButtonsFlags = new boolean[] { false, false };
	public int fontColor = 13553358;
	public static final int SMALL_SPACE = Renderer.height <= 143 ? 1 : 2;
	public static final int LARGE_SPACE = SMALL_SPACE * 2 + 1;
	public byte masterMode = 2;
	public short var_187 = 3;
	public static MainDisplayable currentMainDisplayable;
	public String[] msgText;
	public SpriteFrame[] msgIcons;
	public int targetX;
	public int targetY;
	public int currentWidth;
	public int currentHeight;
	public int textLineHeight;
	public Font var_2f9;
	public int menuSelected;
	public int msgTextLength;
	public int var_39d;
	public int var_3e3;
	public int currentX;
	public int currentY;
	public byte mode;
	public int menuFrameStyle;
	public boolean needRepaint;
	public boolean paintMainDisplayable;
	public Unit[] availableUnits;
	public int textLineSpace;
	public int var_591;
	public byte var_5c0;
	public int var_61a;
	public int var_65d;
	public int var_6b4;
	public int var_6c8;
	public String[] msgTitle;
	public int displayTimeCounter;
	public boolean var_769;
	public int var_77d;
	public Unit selectedUnit;
	public boolean var_81e;
	public PaintableObject nextDisplayable;
	public int var_873;
	public Sprite[] sprMenuSparks;
	public int transitionFactor;
	public boolean scrollNeeded;
	public Vector attachedAuxDisplayables;
	public int currentAttachedAuxDisplayable;
	public boolean var_92c;
	public boolean passUpdatesToAllAttachedAuxDisplayables;
	public int var_9a4;
	public int var_9cc;
	public int var_a1a;
	public int var_a2b;
	public byte[][] var_a36;
	public Vector var_a92;
	public int var_adc;
	public int var_b1c;
	public int gradientColor;
	public int mainColor;
	public SpriteFrame titleIcon;
	public AuxDisplayable titleAuxDisplayable;
	public int[] var_c25;
	public int var_c36;
	public int var_c7a;
	public int[] var_c85;
	public int var_cc7;
	public int alignedTextX;
	public short[] menuCircleAngles;
	public int var_d79;
	public int var_db9;
	public int var_e1d;
	public int var_e4e;
	public int menuCircleHalfSector;
	public int menuCircleSector;
	public int menuCircleShiftStatic;
	public int menuCircleShiftDynamic;
	public int var_fc0;
	public int var_1022;
	public byte var_105d;
	public Sprite sprMenuIconBackground;


	public final void showNotify()
	{
		menuCircleShiftDynamic = 0;

		if(sprMenuSparks != null)
		{
			updateMenuSparks();
		}

		transitionFactor = 4;
		needRepaint = true;
		paintMainDisplayable = true;

		if(currentMainDisplayable != null)
		{
			currentMainDisplayable.showNotify();
		}

		if(titleAuxDisplayable != null)
		{
			titleAuxDisplayable.needRepaint = true;
		}

		if(mode == 15)
		{
			for(int var1 = 0; var1 < attachedAuxDisplayables.size(); ++var1)
			{
				AuxDisplayable var2;
				(var2 = (AuxDisplayable)attachedAuxDisplayables.elementAt(var1)).showNotify();
				var2.paintMainDisplayable = false;
			}
		}

	}

	public final void setDrawSoftButtonFlag(byte var1, boolean var2)
	{
		drawSoftButtonsFlags[var1] = var2;
	}

	public final void setNextDisplayable(PaintableObject var1)
	{
		nextDisplayable = var1;
		drawSoftButtonsFlags[1] = var1 != null;
	}

	public AuxDisplayable(byte var1, int var2)
	{
		var_2f9 = Renderer.fontA;
		needRepaint = false;
		paintMainDisplayable = false;
		var_5c0 = -1;
		displayTimeCounter = -1;
		var_769 = true;
		var_81e = true;
		scrollNeeded = false;
		currentAttachedAuxDisplayable = -1;
		gradientColor = 2370117;
		mainColor = 2370117;
		alignedTextX = -1;
		mode = var1;
		menuFrameStyle = var2;
		if(var1 == 15)
		{
			var_cc7 = currentMainDisplayable.height_ - currentMainDisplayable.sprButtons.height;
			var_769 = true;
		}
		else if(var1 != 0 && var1 != 11)
		{
			if(var1 == 3)
			{
				sprMenuIconBackground = currentMainDisplayable.sprBigCircle;
				createMenuSparks();
				var_769 = false;
				var_92c = true;
				textLineSpace = Renderer.fontABaseLineEx - Renderer.fontABaseLine;
				currentWidth = currentMainDisplayable.width_;
				currentHeight = currentMainDisplayable.sprBigCircle.height + LARGE_SPACE;
				if((var2 & 2) == 0)
				{
					currentHeight += 5;
				}

				availableUnits = Unit.listAvailableUnits(currentMainDisplayable.currentTurningPlayer);
				msgTextLength = availableUnits.length;
				boolean var3 = false;
				int var4 = currentWidth - currentMainDisplayable.sprSideArrow.width * 2;
				if((var2 & 4) == 0)
				{
					var4 -= 8;
				}

				if((var2 & 8) == 0)
				{
					var4 -= 8;
				}

				var_65d = var4 / (currentMainDisplayable.sprBigCircle.width + 3);
				if(var_65d > msgTextLength)
				{
					var_65d = msgTextLength;
				}

				textLineHeight = var4 / var_65d;
				var_873 = (var4 - textLineHeight * var_65d) / 2;
				masterMode = 2;
			}
			else if(var1 != 2 && var1 != 5)
			{
				if(var1 != 7 && var1 == 8)
				{
					var_187 = 8;
					drawSoftButtonsFlags[0] = true;
				}
			}
			else
			{
				var_769 = false;
				currentHeight = 5 + SMALL_SPACE + 24 + LARGE_SPACE + currentMainDisplayable.sprSmallCircle.height * 2 + SMALL_SPACE + SMALL_SPACE + 1;
				if(var1 == 5)
				{
					currentHeight += LARGE_SPACE + Renderer.fontABaseLine;
					selectedUnit = currentMainDisplayable.getUnit(currentMainDisplayable.cursorMapX, currentMainDisplayable.cursorMapY, (byte)0);
					menuSelected = selectedUnit.type;
					currentWidth = currentMainDisplayable.width_;
				}
				else
				{
					currentWidth = currentMainDisplayable.width_;
				}
			}
		}
		else
		{
			drawSoftButtonsFlags[0] = true;
			drawSoftButtonsFlags[1] = true;
		}

		needRepaint = true;
	}

	public final AuxDisplayable createTitleAuxDisplayable(String var1)
	{
		titleAuxDisplayable = new AuxDisplayable((byte)10, 0);
		titleAuxDisplayable.initMsgAuxDisplayable((String)null, var1, currentMainDisplayable.width_, -1);
		return titleAuxDisplayable;
	}

	public final void attachAuxDisplayable(AuxDisplayable var1, int var2, int var3, int var4)
	{
		if(attachedAuxDisplayables == null)
		{
			attachedAuxDisplayables = new Vector();
		}

		int var5;
		if(var_c85 == null)
		{
			var_c85 = new int[5];

			for(var5 = 0; var5 < 5; ++var5)
			{
				var_c85[var5] = var_cc7;
				if(var5 > 0)
				{
					var_c85[var5] -= currentMainDisplayable.sprButtons.height;
				}
			}
		}

		var1.setPosition(var2, var3, var4);
		var5 = var1.targetY;

		for(int var6 = 0; var6 < 5; ++var6)
		{
			if(var5 < var_c85[var6])
			{
				if(var5 + var1.currentHeight > var_c85[var6])
				{
					var_c85[var6] = var5;
					if(var6 + 1 > var_c7a)
					{
						var_c7a = var6 + 1;
					}
				}
				break;
			}

			var5 -= var_c85[var6];
		}

		var1.setDrawSoftButtonFlag((byte)0, false);
		var1.setDrawSoftButtonFlag((byte)1, false);
		attachedAuxDisplayables.addElement(var1);
	}

	public final void sub_1c3(int var1, int var2, byte[][] var3, Vector var4)
	{
		menuFrameStyle = 15;
		var_a36 = var3;
		var_a92 = var4;
		var_187 = 8;
		drawSoftButtonsFlags[0] = true;
		var_769 = true;
		var_adc = var3.length;
		var_b1c = var3[0].length;
		boolean var5 = false;
		currentWidth = var_adc * currentMainDisplayable.sfmSmallTiles[0].imgwidth + 8;
		currentHeight = var_b1c * currentMainDisplayable.sfmSmallTiles[0].imgheight + 8;
		int var6;
		if(currentWidth > var1)
		{
			var6 = currentMainDisplayable.sfmSmallTiles[0].imgwidth;
			var_a1a = (var1 - 8) / var6;
			currentWidth = var6 * var_a1a + 8;
		}
		else
		{
			var_a1a = var_adc;
		}

		if(currentHeight > var2)
		{
			var6 = currentMainDisplayable.sfmSmallTiles[0].imgheight;
			var_a2b = (var2 - 8) / var6;
			currentHeight = var6 * var_a2b + 8;
		}
		else
		{
			var_a2b = var_b1c;
		}

		mode = 8;
	}

	public final void setPosition(int var1, int var2, int var3)
	{
		targetX = var1;
		targetY = var2;

		if((var3 & 1) != 0)
		{
			targetX -= currentWidth >> 1;
		}
		else if((var3 & 8) != 0)
		{
			targetX -= currentWidth;
		}

		if((var3 & 2) != 0)
		{
			targetY -= currentHeight >> 1;
		}
		else if((var3 & 32) != 0)
		{
			targetY -= currentHeight;
		}

		currentX = targetX;
		currentY = targetY;
	}

	public final void sub_1f4(String var1, int var2, int var3, byte var4, byte var5)
	{
		var_5c0 = var4;
		if(var4 == -1)
		{
			menuFrameStyle = 14;
		}
		else
		{
			var_6b4 = currentMainDisplayable.sprPortraits.width - 8;
		}

		int var6 = var2 - var_6b4 - 16;
		msgText = PaintableObject.splitStringMultiline(var1, var6, Renderer.fontA);
		initMsgAuxDisplayableEx((String)null, msgText, var2, var3);
		scrollNeeded = false;
		mode = 7;
	}

	public final void initMsgAuxDisplayableEx(String var1, String[] var2, int var3, int var4)
	{
		var_769 = false;
		currentWidth = var3;
		currentHeight = var4;
		msgTextLength = var2.length;
		menuSelected = 0;
		var_61a = 0;
		var_6c8 = 0;
		scrollNeeded = false;
		int var5 = var3 - var_6b4 - 16;
		if(var1 != null)
		{
			msgTitle = PaintableObject.splitStringMultiline(var1, var5, Renderer.fontA);
		}

		msgText = var2;
		textLineHeight = Renderer.fontABaseLineEx;
		textLineSpace = Renderer.fontABaseLineEx - Renderer.fontABaseLine;
		var_591 = textLineSpace / 2;
		int var6;
		if(var4 <= 0)
		{
			var6 = super.height;
		}
		else
		{
			var6 = var4;
		}

		if((menuFrameStyle & 1) == 0)
		{
			var6 -= 5;
		}

		if((menuFrameStyle & 2) == 0)
		{
			var6 -= 5;
		}

		if(var1 != null)
		{
			var6 -= msgTitle.length * textLineHeight;
		}

		var_65d = (var6 - 2) / textLineHeight;
		if(var_65d > msgText.length)
		{
			var_65d = msgText.length;
		}
		else if(var_65d < msgText.length)
		{
			scrollNeeded = true;
		}

		if(var4 < 0)
		{
			if(msgTitle != null)
			{
				currentHeight = msgTitle.length * textLineHeight;
			}

			currentHeight += var_65d * textLineHeight;
			if((menuFrameStyle & 1) == 0)
			{
				currentHeight += 5;
			}

			if((menuFrameStyle & 2) == 0)
			{
				currentHeight += 5;
			}
		}
		else
		{
			var_873 = (var6 - var_65d * textLineHeight) / 2;
		}

		mode = 10;
		masterMode = 2;
	}

	public final void initMsgAuxDisplayable(String var1, String var2, int var3, int var4)
	{
		int var5 = var3 - var_6b4;
		if((menuFrameStyle & 4) == 0)
		{
			var5 -= 8;
		}

		if((menuFrameStyle & 8) == 0)
		{
			var5 -= 8;
		}

		msgText = PaintableObject.splitStringMultiline(var2, var5, Renderer.fontA);
		initMsgAuxDisplayableEx(var1, msgText, var3, var4);

		if(scrollNeeded)
		{
			var5 -= currentMainDisplayable.sprArrow.width;
			msgText = PaintableObject.splitStringMultiline(var2, var5, Renderer.fontA);
			initMsgAuxDisplayableEx(var1, msgText, var3, var4);
		}

	}

	private final void createMenuSparks()
	{
		sprMenuSparks = new Sprite[3];

		for(int var1 = 0; var1 < sprMenuSparks.length; ++var1)
		{
			sprMenuSparks[var1] = new Sprite(currentMainDisplayable.sprSmallSpark);
		}

		updateMenuSparks();
	}

	public final void updateMenuSparks()
	{
		for(int var1 = 0; var1 < sprMenuSparks.length; ++var1)
		{
			sprMenuSparks[var1].visible = true;
			sprMenuSparks[var1].setPosition(Renderer.randomToRange(sprMenuIconBackground.width), Renderer.randomToRange(sprMenuIconBackground.height));
			sprMenuSparks[var1].setCurrentFrame(Renderer.randomToRange(sprMenuSparks[var1].getFrameCount()));
		}

	}

	public final void initMiniListAuxDisplayable(String[] var1, SpriteFrame[] var2, int x, int y, int anchor)
	{
		menuFrameStyle = 15;
		msgText = var1;
		msgIcons = var2;
		msgTextLength = msgText.length;
		currentWidth = 0;

		for(int var6 = 0; var6 < msgTextLength; ++var6)
		{
			int var7;
			if((var7 = Renderer.fontA.stringWidth(msgText[var6])) > currentWidth)
			{
				currentWidth = var7;
			}
		}

		textLineSpace = Renderer.fontABaseLineEx - Renderer.fontABaseLine;
		var_591 = textLineSpace / 2;
		var_fc0 = currentMainDisplayable.sprSmallCircle.width;
		textLineHeight = var_fc0 + textLineSpace;
		currentWidth += msgTextLength * textLineHeight;
		currentWidth += 32;
		if(currentWidth > super.width)
		{
			currentWidth = super.width;
		}

		currentHeight = var_fc0;
		setPosition(x, y, anchor);
		mode = 13;
		masterMode = 2;
	}

	public final void initHorizontalListAuxDisplayable(String[] var1, int var2, int var3)
	{
		msgText = var1;
		msgTextLength = msgText.length;
		var_92c = true;
		var_769 = false;
		currentHeight = var3;
		int var4 = 0;

		for(int var5 = 0; var5 < msgText.length; ++var5)
		{
			int var6;
			if((var6 = Renderer.fontA.stringWidth(msgText[var5])) > var4)
			{
				var4 = var6;
			}
		}

		currentWidth = var4 + 16 + currentMainDisplayable.sprSideArrow.width * 2;
		if(currentWidth < var2)
		{
			currentWidth = var2;
		}

		if(currentHeight < 0)
		{
			currentHeight = Renderer.fontABaseLineEx;
			if(currentMainDisplayable.sprSideArrow.height > currentHeight)
			{
				currentHeight = currentMainDisplayable.sprSideArrow.height;
			}

			if((menuFrameStyle & 1) == 0)
			{
				currentHeight += 5;
			}

			if((menuFrameStyle & 2) == 0)
			{
				currentHeight += 5;
			}
		}

		mode = 14;
		masterMode = 2;
	}

	public final void initVerticalListAuxDisplayable(String[] var1, int x, int y, int width, int height, int anchor, int var7)
	{
		msgText = var1;
		msgTextLength = msgText.length;
		textLineSpace = Renderer.fontABaseLineEx - Renderer.fontABaseLine;
		textLineHeight = Renderer.fontABaseLineEx;
		int var8 = 0;

		for(int var9 = 0; var9 < msgText.length; ++var9)
		{
			int var10;
			if((var10 = Renderer.fontA.stringWidth(msgText[var9])) > var8)
			{
				var8 = var10;
			}
		}

		currentWidth = var8 + 4 + 16;
		if(currentWidth > super.width)
		{
			currentWidth = super.width;
		}
		else if(currentWidth < width)
		{
			if(var7 == 4)
			{
				alignedTextX = (width - currentWidth) / 2;
			}

			currentWidth = width;
		}

		currentHeight = textLineHeight * msgText.length + textLineSpace + 16;
		if(currentHeight > height)
		{
			currentHeight = height;
		}

		initMsgAuxDisplayableEx((String)null, msgText, currentWidth, currentHeight);

		if(currentWidth < super.width && scrollNeeded)
		{
			currentWidth += currentMainDisplayable.sprArrow.width;
		}

		mode = 11;
		setPosition(x, y, anchor);
	}

	public final void initCircleMenuAuxDisplayable(String[] menuText, SpriteFrame[] menuIcons, int x, int y, int var5, int anchor, byte var7)
	{
		var_105d = var7;
		msgText = menuText;
		msgIcons = menuIcons;
		msgTextLength = msgText.length;
		if(var7 == 1)
		{
			sprMenuIconBackground = currentMainDisplayable.sprBigCircle;
		}
		else if(var7 == 2)
		{
			sprMenuIconBackground = currentMainDisplayable.sprSmallCircle;
			if(msgTextLength < 4)
			{
				String[] var8 = new String[4];
				System.arraycopy(msgText, 0, var8, 0, msgTextLength);
				msgText = var8;
				msgTextLength = 4;
			}
		}

		textLineSpace = Renderer.fontABaseLineEx - Renderer.fontABaseLine;
		menuFrameStyle = 15;
		var_fc0 = sprMenuIconBackground.width;
		var_1022 = var_fc0 >> 1;
		createMenuSparks();
		menuCircleAngles = new short[msgTextLength];
		menuCircleSector = 360 / msgTextLength;
		menuCircleHalfSector = menuCircleSector / 2;
		var_e4e = menuCircleHalfSector;

		int var9;
		for(var9 = 0; var9 < msgTextLength; ++var9)
		{
			menuCircleAngles[var9] = (short)(menuCircleSector * var9);
		}

		if(msgTextLength == 1)
		{
			var_db9 = 0;
		}
		else if(var5 <= 0)
		{
			var_db9 = (sprMenuIconBackground.width << 10) / (2 * PaintableObject.sin(45));
			var_e1d = var_db9 + sprMenuIconBackground.width / 2;
			var5 = var_e1d * 2 + Renderer.fontABaseLineEx + 2;
		}
		else
		{
			var9 = (sprMenuIconBackground.width << 10) / PaintableObject.sin(menuCircleSector / 2) + sprMenuIconBackground.height / 2;
			var_e1d = (var5 - Renderer.fontABaseLineEx) / 2 - 2;
			if(var_e1d > var9)
			{
				var_e1d = var9;
			}

			var_db9 = var_e1d - sprMenuIconBackground.height / 2;
		}

		var_d79 = 0;
		currentWidth = var_e1d * 2;
		currentHeight = var5;
		masterMode = 0;
		setPosition(x, y, anchor);
	}

	public final int selectNextAttachedAuxDisplayable(int var1)
	{
		int var2 = currentAttachedAuxDisplayable;
		int var3 = currentAttachedAuxDisplayable;
		int var4 = attachedAuxDisplayables.size();

		do
		{
			if((var2 += var1) < 0)
			{
				var2 = var4 - 1;
			}
			else if(var2 >= attachedAuxDisplayables.size())
			{
				if(var3 < 0)
				{
					return -1;
				}

				var2 = 0;
			}
		}
		while(!((AuxDisplayable)attachedAuxDisplayables.elementAt(var2)).var_92c);

		return var2;
	}

	public final void update()
	{
		updateEx(true);
	}

	public final void updateEx(boolean var1)
	{
		if(masterMode != 3)
		{
			if(mode == 10 && displayTimeCounter > 0 && displayTimeCounter <= 250)
			{
				++transitionFactor;
				needRepaint = true;
			}
			else if(transitionFactor > 0)
			{
				--transitionFactor;
				needRepaint = true;
			}

			if(var1 && masterMode == 2)
			{
				boolean okpressed = false;
				if(drawSoftButtonsFlags[0] && (PaintableObject.currentRenderer.wasKeyPressed(MainDisplayable.KEY_LSK) || PaintableObject.currentRenderer.wasKeyPressed(16)))
				{
					okpressed = true;
					PaintableObject.currentRenderer.handleKeyReleasedGameAction(MainDisplayable.KEY_LSK);
					PaintableObject.currentRenderer.handleKeyReleasedGameAction(16);
				}

				int i;
				if(mode == 0 || mode == 3)
				{
					for(i = 0; i < sprMenuSparks.length; ++i)
					{
						if(sprMenuSparks[i].currentFrame == sprMenuSparks[i].getFrameCount() - 1)
						{
							if(menuCircleShiftDynamic == 0)
							{
								sprMenuSparks[i].setPosition(Renderer.randomToRange(sprMenuIconBackground.width - sprMenuSparks[i].width), Renderer.randomToRange(sprMenuIconBackground.height - sprMenuSparks[i].height));
							}
							else
							{
								sprMenuSparks[i].visible = false;
							}
						}

						sprMenuSparks[i].nextFrame();
					}

					needRepaint = true;
				}

				if(mode == 15)
				{
					if(!passUpdatesToAllAttachedAuxDisplayables && currentAttachedAuxDisplayable >= 0)
					{
						int var4;
						int var5;
						AuxDisplayable var8;
						if(PaintableObject.currentRenderer.wasKeyPressed(1))
						{
							((AuxDisplayable)attachedAuxDisplayables.elementAt(currentAttachedAuxDisplayable)).needRepaint = true;
							currentAttachedAuxDisplayable = selectNextAttachedAuxDisplayable(-1);
							(var8 = (AuxDisplayable)attachedAuxDisplayables.elementAt(currentAttachedAuxDisplayable)).needRepaint = true;
							var4 = var8.targetY;

							for(var5 = 0; var5 < 5; ++var5)
							{
								if(var4 < var_c85[var5])
								{
									if(var_c36 != var5)
									{
										showNotify();
										var_c36 = var5;
									}
									break;
								}

								var4 -= var_c85[var5];
							}
						}
						else if(PaintableObject.currentRenderer.wasKeyPressed(2))
						{
							((AuxDisplayable)attachedAuxDisplayables.elementAt(currentAttachedAuxDisplayable)).needRepaint = true;
							currentAttachedAuxDisplayable = selectNextAttachedAuxDisplayable(1);
							(var8 = (AuxDisplayable)attachedAuxDisplayables.elementAt(currentAttachedAuxDisplayable)).needRepaint = true;
							var4 = var8.targetY;

							for(var5 = 0; var5 < 5; ++var5)
							{
								if(var4 < var_c85[var5])
								{
									if(var_c36 != var5)
									{
										showNotify();
										var_c36 = var5;
									}
									break;
								}

								var4 -= var_c85[var5];
							}
						}
					}

					if(okpressed)
					{
						currentMainDisplayable.menuStateChanged(this, currentAttachedAuxDisplayable, "", (byte)0);
						return;
					}

					for(i = 0; i < attachedAuxDisplayables.size(); ++i)
					{
						AuxDisplayable var7 = (AuxDisplayable)attachedAuxDisplayables.elementAt(i);
						if(passUpdatesToAllAttachedAuxDisplayables)
						{
							var7.updateEx(true);
						}
						else
						{
							var7.updateEx(i == currentAttachedAuxDisplayable);
						}
					}

					needRepaint = true;
				}
				else if(mode == 0)
				{
					if(masterMode == 2)
					{
						needRepaint = true;
						if(PaintableObject.currentRenderer.wasKeyPressed(4))
						{
							menuCircleShiftDynamic -= menuCircleSector;
							menuCircleShiftStatic += menuCircleSector;
							--menuSelected;
							if(menuSelected < 0)
							{
								menuSelected = msgTextLength - 1;
							}
						}
						else if(PaintableObject.currentRenderer.wasKeyPressed(8))
						{
							menuCircleShiftDynamic += menuCircleSector;
							menuCircleShiftStatic -= menuCircleSector;

							if(menuCircleShiftStatic < 0)
							{
								menuCircleShiftStatic += 360;
							}

							++menuSelected;
							if(menuSelected >= msgTextLength)
							{
								menuSelected = 0;
							}
						}

						if(menuCircleShiftDynamic != 0)
						{
							i = -menuCircleShiftDynamic / 2;

							if(i == 0)
							{
								menuCircleShiftDynamic = 0;
							}
							else
							{
								menuCircleShiftDynamic += i;
							}

							if(menuCircleShiftDynamic == 0)
							{
								updateMenuSparks();
							}
						}
						else if(PaintableObject.currentRenderer.isKeyHeld(4))
						{
							menuCircleShiftDynamic -= menuCircleSector;
							menuCircleShiftStatic += menuCircleSector;
							--menuSelected;
							if(menuSelected < 0)
							{
								menuSelected = msgTextLength - 1;
							}
						}
						else if(PaintableObject.currentRenderer.isKeyHeld(8))
						{
							menuCircleShiftDynamic += menuCircleSector;
							menuCircleShiftStatic -= menuCircleSector;
							if(menuCircleShiftStatic < 0)
							{
								menuCircleShiftStatic += 360;
							}

							++menuSelected;
							if(menuSelected >= msgTextLength)
							{
								menuSelected = 0;
							}
						}

						if(okpressed)
						{
							currentMainDisplayable.menuStateChanged(this, menuSelected, msgText[menuSelected], (byte)0);
							return;
						}
					}
				}
				else if(mode != 13 && mode != 14)
				{
					if(mode == 3)
					{
						if(var_6c8 != 0)
						{
							if(Math.abs(var_6c8) < 2)
							{
								var_6c8 = 0;
							}
							else
							{
								var_6c8 -= var_6c8 / 2;
							}

							needRepaint = true;
						}

						if(okpressed)
						{
							currentMainDisplayable.menuStateChanged(this, menuSelected, msgText[menuSelected], (byte)0);
							return;
						}

						if(PaintableObject.currentRenderer.wasKeyPressed(4))
						{
							if(menuSelected < var_61a)
							{
								menuSelected += msgTextLength;
							}

							--menuSelected;
							if(menuSelected < var_61a)
							{
								if(menuSelected < 0)
								{
									menuSelected += msgTextLength;
								}

								var_61a = menuSelected;
								var_6c8 = -textLineHeight;
							}

							menuSelected %= msgTextLength;
							currentMainDisplayable.menuStateChanged(this, menuSelected, (String)null, (byte)2);
							updateMenuSparks();
							needRepaint = true;
						}
						else if(PaintableObject.currentRenderer.wasKeyPressed(8))
						{
							if(menuSelected < var_61a)
							{
								menuSelected += msgTextLength;
							}

							++menuSelected;
							if(menuSelected >= var_61a + var_65d)
							{
								var_6c8 = textLineHeight;
								var_61a = (var_61a + 1) % msgTextLength;
							}

							menuSelected %= msgTextLength;
							currentMainDisplayable.menuStateChanged(this, menuSelected, (String)null, (byte)3);
							updateMenuSparks();
							needRepaint = true;
						}
					}
					else if(mode != 10 && mode != 7 && mode != 11)
					{
						if(mode == 8)
						{
							if(PaintableObject.currentRenderer.wasKeyPressed(1))
							{
								if(var_9cc > 0)
								{
									--var_9cc;
									needRepaint = true;
								}
							}
							else if(PaintableObject.currentRenderer.wasKeyPressed(2) && var_9cc + var_a2b < var_b1c)
							{
								++var_9cc;
								needRepaint = true;
							}

							if(PaintableObject.currentRenderer.wasKeyPressed(4))
							{
								if(var_9a4 > 0)
								{
									--var_9a4;
									needRepaint = true;
								}
							}
							else if(PaintableObject.currentRenderer.wasKeyPressed(8) && var_9a4 + var_a1a < var_adc)
							{
								++var_9a4;
								needRepaint = true;
							}
						}
					}
					else
					{
						if(displayTimeCounter != -1)
						{
							if(displayTimeCounter > 0)
							{
								displayTimeCounter -= 50;
							}
							else
							{
								PaintableObject.currentRenderer.setCurrentDisplayable(nextDisplayable);
							}
						}

						if(var_6c8 > 0)
						{
							var_6c8 -= textLineHeight / 3 + 1;
							if(var_6c8 < 0)
							{
								var_6c8 = 0;
							}

							needRepaint = true;
						}
						else if(var_6c8 < 0)
						{
							var_6c8 += textLineHeight / 3 + 1;
							if(var_6c8 > 0)
							{
								var_6c8 = 0;
							}

							needRepaint = true;
						}

						if(var_6c8 == 0)
						{
							if((mode == 11 || mode == 10) && okpressed)
							{
								currentMainDisplayable.menuStateChanged(this, menuSelected, msgText[menuSelected], (byte)0);
								return;
							}

							if(mode != 7 && PaintableObject.currentRenderer.wasKeyPressed(1))
							{
								if(mode == 11)
								{
									--menuSelected;
									if(menuSelected < 0)
									{
										menuSelected = msgTextLength - 1;
										var_61a = msgTextLength - var_65d;
										if(mode == 3)
										{
											var_61a = menuSelected;
										}
									}
									else if(menuSelected < var_61a)
									{
										var_6c8 = -textLineHeight;
										--var_61a;
									}

									currentMainDisplayable.menuStateChanged(this, menuSelected, (String)null, (byte)2);
									needRepaint = true;
								}
								else if(var_61a > 0)
								{
									var_6c8 = -textLineHeight;
									--var_61a;
									needRepaint = true;
								}

								PaintableObject.currentRenderer.clearKeyStates();
							}

							if(PaintableObject.currentRenderer.wasKeyPressed(2) || mode == 7 && PaintableObject.currentRenderer.wasKeyPressed(2048))
							{
								if(mode == 11)
								{
									++menuSelected;
									if(menuSelected >= msgTextLength)
									{
										menuSelected = 0;
										var_61a = 0;
									}
									else if(menuSelected >= var_61a + var_65d)
									{
										var_6c8 = textLineHeight;
										++var_61a;
									}

									currentMainDisplayable.menuStateChanged(this, menuSelected, (String)null, (byte)3);
									needRepaint = true;
								}
								else if(var_61a + var_65d < msgText.length)
								{
									var_6c8 = textLineHeight;
									++var_61a;
									needRepaint = true;
								}
								else if(mode == 7)
								{
									currentMainDisplayable.menuStateChanged(this, 0, (String)null, (byte)0);
									return;
								}

								PaintableObject.currentRenderer.clearKeyStates();
							}
						}
					}
				}
				else if(PaintableObject.currentRenderer.wasKeyPressed(4))
				{
					--menuSelected;
					if(menuSelected < 0)
					{
						menuSelected = msgTextLength - 1;
					}

					if(mode == 14)
					{
						currentMainDisplayable.menuStateChanged(this, menuSelected, (String)null, (byte)2);
					}

					needRepaint = true;
				}
				else if(PaintableObject.currentRenderer.wasKeyPressed(8))
				{
					++menuSelected;
					if(menuSelected >= msgTextLength)
					{
						menuSelected = 0;
					}

					if(mode == 14)
					{
						currentMainDisplayable.menuStateChanged(this, menuSelected, (String)null, (byte)2);
					}

					needRepaint = true;
				}
				else if(okpressed)
				{
					currentMainDisplayable.menuStateChanged(this, menuSelected, msgText[menuSelected], (byte)0);
					return;
				}

				if(masterMode == 2 && drawSoftButtonsFlags[1] && PaintableObject.currentRenderer.wasKeyPressed(MainDisplayable.KEY_RSK))
				{
					PaintableObject.currentRenderer.handleKeyReleasedGameAction(MainDisplayable.KEY_RSK);
					PaintableObject.currentRenderer.clearKeyStates();
					if(nextDisplayable != null)
					{
						PaintableObject.currentRenderer.setCurrentDisplayable(nextDisplayable);
					}

					if(msgText != null)
					{
						currentMainDisplayable.menuStateChanged(this, menuSelected, msgText[menuSelected], (byte)1);
						return;
					}

					currentMainDisplayable.menuStateChanged(this, -1, (String)null, (byte)1);
					return;
				}
			}

			if(var_769 && (var_3e3 += 1) >= var_187)
			{
				if(var_39d == 0)
				{
					var_39d = 2;
				}
				else
				{
					var_39d = 0;
				}

				var_3e3 = 0;
				needRepaint = true;
			}

			switch(masterMode)
			{
				case 0:
					int var6;
					if(mode == 0)
					{
						if(var_d79 < var_db9)
						{
							if(var_105d == 2)
							{
								var6 = var_db9 / 2;
							}
							else
							{
								var6 = var_db9 / 5;
							}

							if(var6 < 1)
							{
								var6 = 1;
							}

							var_d79 += var6;
							if(var_d79 > var_db9)
							{
								var_d79 = var_db9;
							}
						}
						else
						{
							var_e4e = Math.abs(360 - menuCircleAngles[0]) / 2;
							if(var_e4e < 1)
							{
								var_e4e = 1;
							}
							else if(var_e4e > menuCircleHalfSector)
							{
								var_e4e = menuCircleHalfSector;
							}
						}

						if(var_105d == 1)
						{
							for(var6 = 0; var6 < menuCircleAngles.length; ++var6)
							{
								menuCircleAngles[var6] = (short)((menuCircleAngles[var6] + var_e4e) % 360);
							}
						}

						if(PaintableObject.currentRenderer.isAnyKeyPressed() || var_d79 >= var_db9 && menuCircleAngles[0] == 0)
						{
							var_d79 = var_db9;

							for(var6 = 0; var6 < menuCircleAngles.length; ++var6)
							{
								menuCircleAngles[var6] = (short)(menuCircleSector * var6);
							}

							masterMode = 2;
							if(var_81e)
							{
								PaintableObject.currentRenderer.clearKeyStates();
							}
						}
					}
					else if(mode == 13)
					{
						currentX += (targetX - currentX) / 2;

						++var_77d;
						if(var_77d == 2)
						{
							masterMode = 2;
							currentX = targetX;
						}
					}
					else
					{
						if((var6 = (targetX - currentX) / 4) <= 0)
						{
							var6 = 1;
						}

						currentX += var6;
						if(currentX == targetX)
						{
							masterMode = 2;
						}
					}

					needRepaint = true;
					return;
				case 1:
					masterMode = 3;
				default:
			}
		}
	}

	public static final void drawRoundRect(Graphics var0, int var1, int var2, int var3, int var4)
	{
		if(var4 <= 2)
		{
			var0.fillRect(var1, var2, var3, var4);
		}
		else
		{
			var0.fillRect(var1, var2 + 1, var3, var4 - 2);
			var0.fillRect(var1 + 1, var2, var3 - 2, var4);
		}
	}

	public final void paint(Graphics var1)
	{
		paintEx(var1, 0, 0, false);
	}

	public final void paintEx(Graphics g, int var2, int var3, boolean var4)
	{
		if(masterMode != 3)
		{
			if(needRepaint)
			{
				needRepaint = false;

				if(PaintableObject.currentRenderer.currentDisplayable == this && paintMainDisplayable || mode == 0)
				{
					currentMainDisplayable.paint(g);
				}

				paintMainDisplayable = false;
				g.setClip(0, 0, super.width, super.height);
				if(titleAuxDisplayable != null)
				{
					titleAuxDisplayable.paint(g);
				}

				int var5 = currentX + var2;
				int var6 = currentY + var3;
				boolean var7 = false;
				boolean var8 = false;

				if(mode != 0 && mode != 13)
				{
					drawMenuFrameEx(g, var5, var6, currentWidth, currentHeight, menuFrameStyle, mainColor, gradientColor, transitionFactor, 5);
					g.setClip(0, 0, super.width, super.height);
				}

				int cw = currentWidth;
				int ch = currentHeight;
				if((menuFrameStyle & 1) == 0)
				{
					ch -= 5;
					var6 += 5;
				}

				if((menuFrameStyle & 2) == 0)
				{
					ch -= 5;
				}

				if((menuFrameStyle & 4) == 0)
				{
					var5 += 8;
					cw -= 8;
				}

				if((menuFrameStyle & 8) == 0)
				{
					cw -= 8;
				}

				g.translate(var5, var6);
				g.setFont(var_2f9);
				if(titleIcon != null && (msgText == null || var_2f9.stringWidth(msgText[0]) < cw - titleIcon.imgwidth * 2))
				{
					titleIcon.paintEx(g, 0, ch / 2, 6);
				}

				if(var4)
				{
					g.setColor(5594742);
					drawRoundRect(g, 0, 0, cw, ch);
				}

				int locX;
				int y;
				int angshift_locY;
				int i;
				int angle;
				int j;

				if(mode == 0)
				{
					g.setColor(16777215);
					angshift_locY = menuCircleShiftStatic + menuCircleShiftDynamic;

					for(i = menuCircleAngles.length - 1; i >= 0; --i)
					{
						if((angle = (menuCircleAngles[i] + angshift_locY) % 360) < 0)
						{
							angle += 360;
						}

						locX = var_e1d + (PaintableObject.sin(angle) * var_d79 >> 10);
						y = Renderer.fontABaseLineEx + var_e1d + 2 - (PaintableObject.cos(angle) * var_d79 >> 10);
						if(masterMode == 2 && i == menuSelected)
						{
							sprMenuIconBackground.paintFrame(g, 1, locX, y, 3);
							if(menuCircleShiftDynamic == 0)
							{
								for(j = 0; j < sprMenuSparks.length; ++j)
								{
									sprMenuSparks[j].paintCurrentFrame(g, locX - var_1022, y - var_1022, 20);
								}
							}
						}
						else
						{
							sprMenuIconBackground.paintFrame(g, 0, locX, y, 3);
						}

						if(msgText[i] != null && msgIcons != null && msgIcons[i] != null)
						{
							msgIcons[i].paintEx(g, locX, y, 3);
						}
					}

					if(masterMode == 2)
					{
						for(i = 0; i < sprMenuSparks.length; ++i)
						{
							sprMenuSparks[i].paintCurrentFrame(g, (currentWidth - var_fc0) / 2, Renderer.fontABaseLineEx, 3);
						}
					}
				}

				int spc;
				int x;
				int expPBH;
				int expPBValW;
				int var23;
				int var22;
				int var25;
				int var24;
				int var27;
				int var26;
				int var29;
				int var28;
				int var31;
				int var30;
				int charW;
            label409:
				switch(mode)
				{
					case 0:
						if(masterMode == 2)
						{
							g.setColor(1645370);
							if(var_105d == 2)
							{
								spc = currentWidth;
								if(msgText[menuSelected] != null)
								{
									charW = Renderer.fontA.stringWidth(msgText[menuSelected]) + 2;
									if(spc < charW)
									{
										spc = charW;
									}
								}

								drawRoundRect(g, (currentWidth - spc) / 2, 1, spc, Renderer.fontABaseLineEx);
							}
							else
							{
								drawRoundRect(g, 2 - targetX, 1, super.width - 4, Renderer.fontABaseLineEx);
							}

							if(msgText[menuSelected] != null)
							{
								g.setColor(16777215);
								Renderer.drawString(g, msgText[menuSelected], var_e1d, (textLineSpace >> 1) + 1, 17);
							}
						}
					case 1:
					case 4:
					case 6:
					case 9:
					case 12:
					default:
						break;
					case 2:
					case 5:
						g.setClip(0, 0, cw, ch);
						spc = SMALL_SPACE;
						charW = Renderer.sprChars[0].width;
						selectedUnit.paint(g, -selectedUnit.currentX + spc, -selectedUnit.currentY + spc);
						angshift_locY = spc + selectedUnit.height / 2;
						String ts = null;
						g.setFont(Renderer.fontA);
						g.setColor(fontColor);
						Renderer.drawString(g, selectedUnit.name, spc + selectedUnit.width + spc, angshift_locY - Renderer.fontABaseLine / 2, 20);
						if(mode == 2)
						{
							ts = "" + selectedUnit.cost;
							currentMainDisplayable.sprHudIcons2.paintFrame(g, 1, cw - spc - Renderer.getGraphicStringWidth((byte)1, ts), angshift_locY, 10);
						}
						else
						{
							ts = "" + selectedUnit.health;
						}

						Renderer.drawAlignedGraphicString(g, ts, cw - spc, angshift_locY, 1, 10);
						textLineSpace = Renderer.fontABaseLineEx - Renderer.fontABaseLine;
						y = spc + selectedUnit.height + SMALL_SPACE;
						g.setColor(fontColor);
						g.drawLine(spc, y, cw - spc - spc, y);
						y += 1 + SMALL_SPACE;
						int expPBW;

						if(mode == 5)
						{
							expPBH = Renderer.fontABaseLine;
							angshift_locY = y + expPBH / 2;
							Renderer.drawString(g, PaintableObject.getLocaleString(97), spc, y, 20);
							x = Renderer.fontA.stringWidth(PaintableObject.getLocaleString(97));
							locX = spc + x + spc;
							
							String rs = "" + selectedUnit.rank;
							int rsw = Renderer.getGraphicStringWidth((byte)0, rs);

							expPBW = cw - locX - spc - currentMainDisplayable.sprHudIcons.width - rsw - spc;
							g.setColor(fontColor);
							drawRoundRect(g, locX, y, expPBW, expPBH);
							g.setColor(2370117);

							expPBValW = expPBW * selectedUnit.experience / selectedUnit.getNextRankExperience();
							
							if(expPBValW <= 0)
							{
								expPBValW = 1;
							}

							g.fillRect(locX + 1, y + 1, expPBValW, expPBH - 2);
							locX = cw - spc - rsw;
							currentMainDisplayable.sprHudIcons.paintFrame(g, 2, locX, angshift_locY, 10);
							Renderer.drawAlignedGraphicString(g, rs, locX, angshift_locY, 0, 6);
							y += expPBH + SMALL_SPACE;
							g.setColor(fontColor);
							g.drawLine(spc, y, cw - spc - spc, y);
							y += 1 + SMALL_SPACE;
						}

						expPBH = (cw - spc * 3) / 2;
						x = currentMainDisplayable.sprHudIcons.height;
						expPBW = currentMainDisplayable.sprSmallCircle.height;
						expPBValW = currentMainDisplayable.sprSmallCircle.height / 2;

						for(var23 = 0; var23 < 2; ++var23)
						{
							var22 = y + expPBValW - x / 2;
							locX = spc;

							for(var24 = 0; var24 < 2; ++var24)
							{
								if(var23 == 0 || var24 == 0)
								{
									var25 = locX + expPBValW;
									drawRoundRect(g, var25, var22, expPBH - expPBValW, x);
									currentMainDisplayable.sprSmallCircle.paint(g, locX, y);
									if((var26 = var23 * 2 + var24) == 0 || var26 == 1)
									{
										currentMainDisplayable.sprHudIcons.paintFrame(g, var26, var25, y + expPBValW, 3);
									}

									var27 = 0;
									if(var26 == 0)
									{
										if(mode == 5)
										{
											var27 = selectedUnit.getOffenceBonusAgainstUnit((Unit)null);
										}

										ts = selectedUnit.offenceMin + var27 + "-" + (selectedUnit.offenceMax + var27);
									}
									else if(var26 == 1)
									{
										if(mode == 5)
										{
											var27 = selectedUnit.getDefenceBonusAgainstUnit((Unit)null);
										}

										ts = "" + (selectedUnit.baseDefence + var27);
									}
									else if(var26 == 2)
									{
										currentMainDisplayable.sprActionIcons[5].paintEx(g, var25, y + expPBValW, 3);
										ts = "" + selectedUnit.baseMoveRange; // Unit.UNIT_MOVE_RANGE[selectedUnit.type];
									}

									Renderer.drawAlignedGraphicString(g, ts, locX + expPBW + 1, y + expPBValW, 0, 6);
									if(var27 > 0)
									{
										currentMainDisplayable.sprArrowIcons.paintFrame(g, 1, var25 + expPBH - expPBValW - 1, y + expPBValW, 10);
									}
									else if(var27 < 0)
									{
										currentMainDisplayable.sprArrowIcons.paintFrame(g, 2, var25 + expPBH - expPBValW - 1, y + expPBValW, 10);
									}

									locX += expPBH + SMALL_SPACE;
								}
							}

							y += expPBW;
						}

						y += SMALL_SPACE;
						g.setColor(fontColor);
						g.drawLine(spc, y, cw - spc - spc, y);
						break;
					case 3:
						g.setClip(0, 0, cw, ch);
						y = SMALL_SPACE;
						g.setColor(11515819);
						g.drawLine(SMALL_SPACE, y, cw - SMALL_SPACE * 2, y);
						y = (y += 1 + SMALL_SPACE) + currentMainDisplayable.sprBigCircle.height / 2;
						locX = currentMainDisplayable.sprSideArrow.width + var_6c8 + var_873;
						spc = var_61a;
						charW = var_61a + var_65d;
						if(var_6c8 > 0)
						{
							--spc;
							locX -= textLineHeight;
						}
						else if(var_6c8 < 0)
						{
							++charW;
						}

						for(int var17 = spc; var17 < charW; ++var17)
						{
							if((expPBH = var17 % msgTextLength) < 0)
							{
								expPBH += msgTextLength;
							}

							x = locX + textLineHeight / 2;
							if(expPBH == menuSelected)
							{
								currentMainDisplayable.sprBigCircle.paintFrame(g, 1, x, y, 3);
							}
							else
							{
								currentMainDisplayable.sprBigCircle.paintFrame(g, 0, x, y, 3);
							}

							Unit var20 = availableUnits[expPBH];
							expPBValW = x - var20.currentX - var20.width / 2;
							var22 = y - var20.currentY - var20.height / 2;
							var20.paintEx(g, expPBValW, var22, var20.cost > currentMainDisplayable.money[currentMainDisplayable.currentTurningPlayer]);
							if(expPBH == menuSelected)
							{
								var23 = x - sprMenuIconBackground.width / 2;
								var24 = y - sprMenuIconBackground.width / 2;

								for(var25 = 0; var25 < sprMenuSparks.length; ++var25)
								{
									sprMenuSparks[var25].paintCurrentFrame(g, var23, var24, 20);
								}
							}

							locX += textLineHeight;
						}

						currentMainDisplayable.sprSideArrow.paintFrame(g, 0, 0, y, 6);
						currentMainDisplayable.sprSideArrow.paintFrame(g, 1, cw, y, 10);
						break;
					case 7:
					case 10:
					case 11:
						g.setFont(Renderer.fontA);
						if(var_5c0 != -1)
						{
							currentMainDisplayable.sprPortraits.paintFrame(g, var_5c0, -8, ch, 36);
						}

						cw -= var_6b4;
						g.setClip(0, 0, cw, currentHeight - 10);
						var31 = 0;
						y = 0;
						int var44;
						if(msgTitle != null)
						{
							g.setColor(MainDisplayable.blendColors(16777215, mainColor, transitionFactor, 5));

							for(var44 = 0; var44 < msgTitle.length; ++var44)
							{
								Renderer.drawString(g, msgTitle[var44], var_6b4 + cw / 2, y + var_591, 17);
								y += textLineHeight;
							}

							g.setColor(10463131);
							g.drawLine(0, y, cw - 1, y);
							var31 = y;
						}

						var44 = y + var_873;
						g.setColor(fontColor);
						int var33 = var_61a;
						int var34 = var_61a + var_65d;
						if(var_6c8 > 0)
						{
							--var33;
							y -= textLineHeight;
						}
						else if(var_6c8 < 0)
						{
							++var34;
						}

						y += var_6c8 + var_873;
						g.setClip(var_6b4, var31, cw, ch - var31);
						int var35 = cw;
						if(scrollNeeded)
						{
							var35 = cw - currentMainDisplayable.sprArrow.width;
						}

						int var36 = var_6b4 + var35 / 2;

						int var38;
						int var39;
						int var37;
						for(var37 = var33; var37 < var34; ++var37)
						{
							var38 = 0;
							if(y < var44)
							{
								var38 = var44 - y;
							}
							else if(y + textLineHeight > ch - var_873)
							{
								var38 = y + textLineHeight - ch + var_873;
							}

							if(mode == 11 && var37 == menuSelected)
							{
								g.setColor(5594742);
								drawRoundRect(g, 0, y, var35, textLineHeight);
								var39 = MainDisplayable.blendColors(mainColor, 16777215, textLineHeight - var38, textLineHeight);
							}
							else
							{
								var39 = MainDisplayable.blendColors(mainColor, fontColor, textLineHeight - var38, textLineHeight);
							}

							var39 = MainDisplayable.blendColors(var39, mainColor, transitionFactor, 5);
							g.setColor(var39);
							if(alignedTextX >= 0)
							{
								Renderer.drawString(g, msgText[var37], alignedTextX, y + var_591, 20);
							}
							else
							{
								Renderer.drawString(g, msgText[var37], var36, y + var_591, 17);
							}

							y += textLineHeight;
						}

						if(scrollNeeded)
						{
							var37 = currentMainDisplayable.sprArrow.height;
							var38 = currentMainDisplayable.sprArrow.width;
							var39 = currentMainDisplayable.sprArrow.width / 2;
							int var40 = ch - var37 * 2 - 2;
							int var41 = cw - (var38 + var39) / 2;
							if(var40 > 2)
							{
								g.setColor(fontColor);
								drawRoundRect(g, var41, var37 + 1, var39, var40);
								int var42;
								if((var42 = (var40 - 2) * var_65d / msgTextLength) < 1)
								{
									var42 = 1;
								}

								g.setColor(2370117);
								drawRoundRect(g, var41 + 1, var37 + (var40 - 2) * var_61a / msgTextLength + 2, var39 - 2, var42);
								currentMainDisplayable.sprArrow.paintFrame(g, 0, cw - var38, 0, 20);
								currentMainDisplayable.sprArrow.paintFrame(g, 1, cw - var38, ch, 36);
							}
							else
							{
								if(var_61a > 0)
								{
									currentMainDisplayable.sprArrow.paintFrame(g, 0, cw - var38, 0, 20);
								}

								if(var_61a + var_65d < msgTextLength)
								{
									currentMainDisplayable.sprArrow.paintFrame(g, 1, cw - var38, ch, 36);
								}
							}
						}

						if(mode == 7)
						{
							g.setClip(0, 0, super.width, super.height);
							var37 = ch;
							if((menuFrameStyle & 2) == 0)
							{
								var37 = ch + 5;
							}

							currentMainDisplayable.sprArrow.paintFrame(g, 1, cw + var_6b4, var37, 40);
						}
						break;
					case 8:
						currentMainDisplayable.sub_b8e(g, 0, 0, currentWidth, currentHeight);
						var24 = currentMainDisplayable.sfmSmallTiles[0].imgwidth;
						var25 = currentMainDisplayable.sfmSmallTiles[0].imgheight;
						var26 = var_a2b + var_9cc;
						var27 = var_a1a + var_9a4;
						y = 4;

						for(var28 = var_9cc; var28 < var26; ++var28)
						{
							locX = 4;

							for(var29 = var_9a4; var29 < var27; ++var29)
							{
								var30 = currentMainDisplayable.smallTiles[var_a36[var29][var28]];
								
//								if(currentMainDisplayable.isFractionBuilding(var_a36[var29][var28]))
//								{
//									var31 = (var_a36[var29][var28] - MainDisplayable.FRACTION_BUILDINGS) / 2;
//									var30 = 2 * var31 + 8 + var30 - 8;
//								}

								currentMainDisplayable.sfmSmallTiles[var30].paint(g, locX, y);
								locX += var24;
							}

							y += var25;
						}

						if(var_a92 != null && var_39d == 0)
						{
							var28 = -var_9a4 * var24 + 4;
							var29 = -var_9cc * var25 + 4;
							var30 = 0;

							for(var31 = currentMainDisplayable.units.size(); var30 < var31; ++var30)
							{
								Unit var32;
								if((var32 = (Unit)currentMainDisplayable.units.elementAt(var30)).currentMapPosX >= var_9a4 && var32.currentMapPosX < var27 && var32.currentMapPosY >= var_9cc && var32.currentMapPosY < var26)
								{
									currentMainDisplayable.sprMiniIcons.paintFrame(g, currentMainDisplayable.fractionsTurnQueue[var32.fractionPosInTurnQueue] - 1, var32.currentMapPosX * var24 + var28, var32.currentMapPosY * var25 + var29, 0);
								}
							}
						}

						if(var_39d == 0)
						{
							if(var_9cc > 0)
							{
								currentMainDisplayable.sprArrow.paintFrame(g, 0, cw / 2, 0, 17);
							}

							if(var_9cc + var_a2b < var_b1c)
							{
								currentMainDisplayable.sprArrow.paintFrame(g, 1, cw / 2, ch, 33);
							}

							if(var_9a4 > 0)
							{
								currentMainDisplayable.sprSideArrow.paintFrame(g, 0, 0, ch / 2, 6);
							}

							if(var_9a4 + var_a1a < var_adc)
							{
								currentMainDisplayable.sprSideArrow.paintFrame(g, 1, cw, ch / 2, 10);
							}
						}
						break;
					case 13:
						var28 = Renderer.fontABaseLineEx;
						var29 = (currentHeight - var28) / 2;
						g.setColor(MainDisplayable.blendColors(1645370, 16777215, transitionFactor, 5));
						drawRoundRect(g, 0, var29, currentWidth, var28);
						g.setFont(Renderer.fontA);
						g.setColor(16777215);
						Renderer.drawString(g, msgText[menuSelected], 16, var29 + var_591, 20);
						var30 = currentWidth - textLineHeight;
						var31 = msgTextLength - 1;

						while(true)
						{
							if(var31 < 0)
							{
								break label409;
							}

							if(var31 == menuSelected)
							{
								currentMainDisplayable.sprSmallCircle.paintFrame(g, 1, var30, 0, 20);
							}
							else
							{
								currentMainDisplayable.sprSmallCircle.paintFrame(g, 0, var30, 0, 20);
							}

							msgIcons[var31].paintEx(g, var30 + currentMainDisplayable.sprSmallCircle.width / 2, currentHeight / 2, 3);
							var30 -= textLineHeight;
							--var31;
						}
					case 14:
						g.setFont(Renderer.fontA);
						g.setColor(MainDisplayable.blendColors(16777215, 1645370, transitionFactor, 5));
						angshift_locY = ch / 2;
						Renderer.drawString(g, msgText[menuSelected], cw / 2, (ch - Renderer.fontABaseLine) / 2, 17);
						currentMainDisplayable.sprSideArrow.paintFrame(g, 0, 0, angshift_locY, 6);
						currentMainDisplayable.sprSideArrow.paintFrame(g, 1, cw, angshift_locY, 10);
						break;
					case 15:
						i = 0;
						angle = 0;
						j = var_c85[0];

						for(spc = 1; spc <= var_c36; ++spc)
						{
							i = j;
							j += var_c85[spc];
						}

						if(var_c36 > 0)
						{
							angle = -i + currentMainDisplayable.sprButtons.height;
						}

						for(spc = 0; spc < attachedAuxDisplayables.size(); ++spc)
						{
							AuxDisplayable var16;
							if((var16 = (AuxDisplayable)attachedAuxDisplayables.elementAt(spc)).targetY >= i && var16.targetY < j)
							{
								var16.paintEx(g, 0, angle, spc == currentAttachedAuxDisplayable);
							}
						}

						g.setClip(0, 0, currentMainDisplayable.width_, currentMainDisplayable.height_);
						if(var_c36 > 0)
						{
							g.setColor(2370117);
							g.fillRect(0, 0, super.width, currentMainDisplayable.sprButtons.height);
							currentMainDisplayable.sprArrow.paintFrame(g, 0, currentMainDisplayable.width_ / 2, -var_39d, 17);
						}

						if(var_c36 < var_c7a)
						{
							g.setColor(2370117);
							spc = var_c85[var_c36];
							if(var_c36 > 0)
							{
								spc += currentMainDisplayable.sprButtons.height;
							}

							g.fillRect(0, spc, super.width, currentMainDisplayable.height_ - spc);
							currentMainDisplayable.sprArrow.paintFrame(g, 1, currentMainDisplayable.width_ / 2, currentMainDisplayable.height_ + var_39d, 33);
						}
				}

				g.translate(-var5, -var6);
				g.setClip(0, 0, super.width, super.height);
				if(PaintableObject.currentRenderer.currentDisplayable == this && masterMode == 2)
				{
					if(drawSoftButtonsFlags[0])
					{
						currentMainDisplayable.drawSoftButton(g, MainDisplayable.KEY_LSK, 0, currentMainDisplayable.height_);
					}

					if(drawSoftButtonsFlags[1])
					{
						currentMainDisplayable.drawSoftButton(g, MainDisplayable.KEY_RSK, 1, currentMainDisplayable.height_);
					}
				}

			}
		}
	}

	public static final void drawMenuFrame(Graphics g, int x, int y, int width, int height, int style)
	{
		drawMenuFrameEx(g, x, y, width, height, style, 2370117, 2370117, 0, 0);
	}

	public static final void drawMenuFrameEx(Graphics g, int x, int y, int width, int height, int style, int var6, int var7, int var8, int var9)
	{
		Sprite var10 = currentMainDisplayable.sprMenu;
		g.setClip(x, y, width, height);
		g.setColor(var6);
		g.fillRect(x, y, width, height);

		if(var7 != var6)
		{
			int var11 = height / 4;
			int var12 = y + 5;

			for(int var14 = 0; var14 < var11; ++var14)
			{
				int var13 = MainDisplayable.blendColors(var7, var6, var14, var11);
				g.setColor(MainDisplayable.blendColors(var13, var6, var8, var9));
				g.fillRect(x, var12, width, 1);
				++var12;
			}
		}

		if(style != 15)
		{
			boolean var23 = (style & 4) == 0;
			boolean var24 = (style & 8) == 0;
			boolean var22 = (style & 1) == 0;
			boolean var25 = (style & 2) == 0;
			int var15 = width / var10.width - 2;
			if(width % var10.width != 0)
			{
				++var15;
			}

			if(!var23)
			{
				++var15;
			}

			if(!var24)
			{
				++var15;
			}

			int var16 = height / var10.height - 2;
			if(height % var10.height != 0)
			{
				++var16;
			}

			if(!var22)
			{
				++var16;
			}

			if(!var25)
			{
				++var16;
			}

			int var17 = x;
			if(var23)
			{
				var17 = x + var10.width;
			}

			int var18 = y + height - var10.height;

			int var19;
			for(var19 = 0; var19 < var15; ++var19)
			{
				if(var22)
				{
					var10.paintFrame(g, 1, var17, y, 0);
				}

				if(var25)
				{
					var10.paintFrame(g, 6, var17, var18, 0);
				}

				var17 += var10.width;
			}

			var19 = y;
			if(var22)
			{
				var19 = y + var10.height;
			}

			int var20 = x + width - var10.width;

			for(int var21 = 0; var21 < var16; ++var21)
			{
				if(var23)
				{
					var10.paintFrame(g, 3, x, var19, 0);
				}

				if(var24)
				{
					var10.paintFrame(g, 4, var20, var19, 0);
				}

				var19 += var10.height;
			}

			if(var23 && var22)
			{
				var10.paintFrame(g, 0, x, y, 0);
			}

			if(var24 && var22)
			{
				var10.paintFrame(g, 2, var20, y, 0);
			}

			if(var23 && var25)
			{
				var10.paintFrame(g, 5, x, var18, 0);
			}

			if(var24 && var25)
			{
				var10.paintFrame(g, 7, var20, var18, 0);
			}
		}

	}

}
