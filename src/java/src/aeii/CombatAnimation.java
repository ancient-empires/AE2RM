package aeii;

// Decompiled by:       Fernflower v0.6
// Date:                22.01.2010 19:41:57
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import javax.microedition.lcdui.Graphics;

public final class CombatAnimation
{
//	public static final String[] TERRAIN_NAMES = new String[] { "road", "grass", "woods", "hill", "mountain", "water", "bridge", "town" };
//	public static final byte[] FOREGROUNDS = new byte[] { (byte)0, (byte)1, (byte)1, (byte)1, (byte)4, (byte)5, (byte)6, (byte)7, (byte)7, (byte)7 };
//	public static final byte[] BACKGROUNDS = new byte[] { (byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)7, (byte)7 };
	
	public static String[] BACKGROUNDS = new String[] { "road", "grass", "woods", "hill",  "mountain", "water", "bridge", "town", "town", "town" };
	public static String[] FOREGROUNDS = new String[] { "road", "grass", "grass", "grass", "mountain", "water", "bridge", "town", "town", "town" };
	
	public static final String[] UNITS_NAMES = new String[] { "soldier", "archer", "lizard", "wizard", "wisp", "spider", "golem", "catapult", "wyvern", "king", "skeleton" };
	
	public MainDisplayable currentMainDisplayable;
	public Unit currentUnit;
	public byte unitType;
	public boolean var_1fd = false;
	public boolean var_226 = false;
	public boolean var_24e;
	public byte initialUnitHealth;
	public byte updatedUnitHealth;
	public byte initialUnitCharCount;
	public byte updatedUnitCharCount;
	public byte currentCharCount;
	public byte mirror;
	public boolean shiftFrameSequence;
	public int combatState = 0;
	public static final byte[] var_4a6;
	public Sprite sprUnit;
	public Sprite sprAttackSideEffect;
	public Sprite sprVictimSideEffect;
	public Sprite sprKingSlash;
	public int var_5c5;
	public int var_629;
	public long sparkAddDelayCounterStart;
	public long combatDelayCounterStart;
	public long var_70b;
	public Sprite[] sprUnitCharacters;
	public SpriteFrame[] sfmTerrainForeground;
	public SpriteFrame sfmTerrainBackground;
	public CombatAnimation currentAttackerSideCombatAnimation;
	public int tileType;
	public int backgroundHeight;
	public int tilesCountX;
	public int tilesCountY;
	public boolean var_950 = false;
	public int currentUnitHealth;
	public int killedCharCount;
	public int[][] characterPositions;
	public int[] charBounceAngles;
	public int var_a9b;
	public int bounceAngleDelta = 20;
	public int var_b08;
	public int var_b3d;
	public byte[][] foregroundFrames;
	public Sprite sprKingHeads;
	public Sprite sprKingHeadsBack;
	public Sprite[] var_c08;
	public int var_c2c;
	public int var_c8a;


	public CombatAnimation(MainDisplayable md, Unit unit, CombatAnimation otherSide)
	{
		currentMainDisplayable = md;
		currentUnit = unit;
		unitType = unit.type;
		currentAttackerSideCombatAnimation = otherSide;

		initialUnitHealth = (byte)unit.health;
		currentUnitHealth = initialUnitHealth;

		initialUnitCharCount = (byte)unit.getCharacterCount();
		currentCharCount = initialUnitCharCount;

		int var4 = 0;

		if(otherSide == null)
		{
			mirror = 0;
			var_5c5 = 0;
			shiftFrameSequence = true;
		}
		else
		{
			var4 = md.halfWidth_;
			mirror = 1;
			combatState = 0;
		}

		tileType = md.getTileType(unit.currentMapPosX, unit.currentMapPosY);

		String backgroundType = BACKGROUNDS[tileType];
		String foregroundType = FOREGROUNDS[tileType];

		if(otherSide != null && foregroundType.equals(FOREGROUNDS[otherSide.tileType]))
		{
			sfmTerrainForeground = new SpriteFrame[otherSide.sfmTerrainForeground.length];
			System.arraycopy(otherSide.sfmTerrainForeground, 0, sfmTerrainForeground, 0, sfmTerrainForeground.length);
		}
		else
		{
			sfmTerrainForeground = (new Sprite(foregroundType)).frames;
		}

		int var7;

		if(mirror == 1)
		{
			for(var7 = 0; var7 < sfmTerrainForeground.length; ++var7)
			{
				sfmTerrainForeground[var7] = new SpriteFrame(sfmTerrainForeground[var7], 1);
			}
		}

		try
		{
			if(otherSide != null && backgroundType.equals(BACKGROUNDS[otherSide.tileType]))
			{
				sfmTerrainBackground = otherSide.sfmTerrainBackground;
			}
			else
			{
				sfmTerrainBackground = new SpriteFrame(backgroundType + "_bg");
			}

			if(mirror == 1)
			{
				sfmTerrainBackground = new SpriteFrame(sfmTerrainBackground, 1);
			}
		}
		catch(Exception var9)
		{
			;
		}

		if(sfmTerrainBackground != null)
		{
			backgroundHeight = sfmTerrainBackground.imgheight;
		}

		tilesCountX = md.halfWidth_ / sfmTerrainForeground[0].imgwidth;

		if(md.halfWidth_ % sfmTerrainForeground[0].imgwidth != 0)
		{
			++tilesCountX;
		}

		tilesCountY = (md.var_4138 - backgroundHeight) / sfmTerrainForeground[0].imgheight;

		if((md.var_4138 - backgroundHeight) % sfmTerrainForeground[0].imgheight != 0)
		{
			++tilesCountY;
		}

		foregroundFrames = new byte[tilesCountX][tilesCountY];

		for(var7 = 0; var7 < tilesCountX; ++var7)
		{
			for(int var8 = 0; var8 < tilesCountY; ++var8)
			{
				foregroundFrames[var7][var8] = (byte)Math.abs(Renderer.rnd.nextInt() % sfmTerrainForeground.length);
			}
		}

		sprUnit = new Sprite(UNITS_NAMES[unitType], (byte)((md.fractionsTurnQueue[unit.fractionPosInTurnQueue] - 1) * 2));

		if(otherSide != null && otherSide.unitType == unitType)
		{
			if(otherSide.sprKingSlash != null)
			{
				sprKingSlash = new Sprite(otherSide.sprKingSlash);
			}

			if(otherSide.sprAttackSideEffect != null)
			{
				sprAttackSideEffect = new Sprite(otherSide.sprAttackSideEffect);
			}

			if(otherSide.sprVictimSideEffect != null)
			{
				sprVictimSideEffect = new Sprite(otherSide.sprVictimSideEffect);
			}

			if(otherSide.sprKingHeads != null)
			{
				sprKingHeads = new Sprite(otherSide.sprKingHeads);
				sprKingHeadsBack = new Sprite(otherSide.sprKingHeadsBack);
			}
		}
		else if(unitType != 0 && unitType != 10 && unitType != 5)
		{
			if(unitType == 1)
			{
				sprVictimSideEffect = new Sprite("archer_arrow");
			}
			else if(unitType == 9)
			{
				sprAttackSideEffect = new Sprite("kingwave");
				sprKingSlash = new Sprite("kingslash");
				sprKingHeads = new Sprite("king_heads");
				sprKingHeadsBack = new Sprite("king_heads_back");
			}
			else if(unitType == 2)
			{
				sprAttackSideEffect = new Sprite("watermagic");
				sprVictimSideEffect = new Sprite("fish");
			}
			else if(unitType != 7 && unitType != 6)
			{
				if(unitType == 3)
				{
					sprAttackSideEffect = new Sprite("spell");
					sprVictimSideEffect = sprAttackSideEffect;
				}
			}
			else
			{
				sprVictimSideEffect = new Sprite("crater");
			}
		}
		else
		{
			sprKingSlash = new Sprite("slash");
		}

		if(sprAttackSideEffect != null)
		{
			sprAttackSideEffect.setFrameSequence(0, shiftFrameSequence);
		}

		if(sprVictimSideEffect != null)
		{
			sprVictimSideEffect.setFrameSequence(0, shiftFrameSequence);
		}

		if(sprKingSlash != null)
		{
			sprKingSlash.setFrameSequence(0, shiftFrameSequence);
		}

		if(sprKingHeads != null)
		{
			sprKingHeads.setFrameSequence(unit.kingVariant, shiftFrameSequence);
			sprKingHeads.setCurrentFrame(unit.kingVariant);
			sprKingHeadsBack.setFrameSequence(0, shiftFrameSequence);
			sprKingHeadsBack.setCurrentFrame(unit.kingVariant);
		}

		characterPositions = new int[unit.characterData.length][2];

		for(var7 = 0; var7 < characterPositions.length; ++var7)
		{
			characterPositions[var7][0] = unit.characterData[var7][0] * md.width / 128;

			if(mirror == 1)
			{
				characterPositions[var7][0] = md.halfWidth_ - characterPositions[var7][0] - sprUnit.width + var4;
			}

			characterPositions[var7][1] = unit.characterData[var7][1] * md.var_4138 / 128 - sprUnit.height;
		}

		sprUnitCharacters = new Sprite[initialUnitCharCount];

		if(unitType == 4 || unitType == 6)
		{
			charBounceAngles = new int[initialUnitCharCount];
		}

		for(var7 = 0; var7 < initialUnitCharCount; ++var7)
		{
			sprUnitCharacters[var7] = new Sprite(sprUnit);
			md.addCombatAnimationSprite(sprUnitCharacters[var7]);
			sprUnitCharacters[var7].setPosition(characterPositions[var7][0], characterPositions[var7][1]);
			sprUnitCharacters[var7].setFrameSequence(0, shiftFrameSequence);
			sprUnitCharacters[var7].autoDeactivate = false;
			sprUnitCharacters[var7].shiftY = 0;

			if(unitType == 6)
			{
				sprUnitCharacters[var7].bouncing = true;
				charBounceAngles[var7] = Renderer.randomToRange(360);
				sprUnitCharacters[var7].bounceValue = -6 + 4 * PaintableObject.sin(charBounceAngles[var7]) >> 10;
			}
			else if(unitType == 4)
			{
				sprUnitCharacters[var7].bouncing = true;
				sprUnitCharacters[var7].bounceValue = -5 - Renderer.randomToRange(10);
				charBounceAngles[var7] = Renderer.randomToRange(360);
			}
			else if(unitType == 9)
			{
				sprUnitCharacters[var7].var_4e4 = sprKingHeads;
				sprUnitCharacters[var7].var_52f = sprKingHeadsBack;
			}
		}

	}

	public final int sub_12(Sprite var1, int var2)
	{
		return mirror == 1 ? sprUnit.width - var2 - var1.width : var2;
	}

	public final void sub_1f()
	{
		var_1fd = true;
		combatState = 1;
		combatDelayCounterStart = currentMainDisplayable.delayCounter;
	}

	public final void sub_44()
	{
		boolean var1;
		int var2;
		Sprite var3;
		int var4;
		int var9;
		Sprite var10;

		switch(combatState)
		{
			case 1:
				if(unitType == 6)
				{
					if(var_b08 == 0)
					{
						if(currentMainDisplayable.delayCounter - combatDelayCounterStart >= 200L)
						{
							if(var_c8a < currentCharCount)
							{
								sprUnitCharacters[var_c8a].var_3ef = -1;
								sprUnitCharacters[var_c8a].bouncing = false;
								sprUnitCharacters[var_c8a].bounceDelta = 0;
							}

							if((var_c8a += 1) >= currentCharCount)
							{
								var_c8a = 0;
								var_b08 = 1;
								combatDelayCounterStart = currentMainDisplayable.delayCounter;
								return;
							}
						}
					}
					else if(var_b08 == 1)
					{
						if(currentMainDisplayable.delayCounter - combatDelayCounterStart >= 200L)
						{
							if(var_c8a < currentCharCount)
							{
								sprUnitCharacters[var_c8a].bounceDelta = -1;
							}

							if((var_c8a += 1) >= currentCharCount)
							{
								var_c8a = 0;
								var_b08 = 2;
								combatDelayCounterStart = currentMainDisplayable.delayCounter;
								return;
							}
						}
					}
					else if(var_b08 == 2)
					{
						var1 = true;
						if(currentMainDisplayable.delayCounter - combatDelayCounterStart >= 200L)
						{
							if(var_c8a < currentCharCount)
							{
								sprUnitCharacters[var_c8a].var_3ef = 0;
								sprUnitCharacters[var_c8a].bounceDelta = 0;
								sprUnitCharacters[var_c8a].setFrameSequence(2, shiftFrameSequence);
								sprUnitCharacters[var_c8a].bounceMode = 1;
							}

							if((var_c8a += 1) < currentCharCount)
							{
								var1 = false;
							}
						}
						else
						{
							var1 = false;
						}

						for(var2 = 0; var2 < currentCharCount; ++var2)
						{
							if(sprUnitCharacters[var2].var_3ef == 0)
							{
								if(sprUnitCharacters[var2].currentFrame == 1)
								{
									sprUnitCharacters[var2].bounceValue = 0;
									sprUnitCharacters[var2].var_3ef = 1;
									Renderer.vibrate(200);
									currentMainDisplayable.shakeMap(1200);
									Renderer.startPlayer(14, 1);

									for(var9 = 0; var9 < 2; ++var9)
									{
										Sprite var8;
										(var8 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprSmoke, 0, 0, -1, 1, Renderer.randomToRange(4) * 50, (byte)0)).setPosition(sprUnitCharacters[var2].currentX + Renderer.randomToRange(sprUnit.width - var8.width), sprUnitCharacters[var2].currentY + sprUnit.height - var8.height + 2);
										var8.var_379 = true;
										currentMainDisplayable.addCombatAnimationSprite(var8);
									}

									(var3 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprSmoke, -1, 0, -1, 1, Renderer.randomToRange(4) * 50, (byte)0)).setPosition(sprUnitCharacters[var2].currentX, sprUnitCharacters[var2].currentY + sprUnit.height - var3.height + 2);
									var3.var_379 = true;
									currentMainDisplayable.addCombatAnimationSprite(var3);
									(var3 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprSmoke, 1, 0, -1, 1, Renderer.randomToRange(4) * 50, (byte)0)).setPosition(sprUnitCharacters[var2].currentX + sprUnit.width - var3.width, sprUnitCharacters[var2].currentY + sprUnit.height - var3.height + 2);
									var3.var_379 = true;
									currentMainDisplayable.addCombatAnimationSprite(var3);
								}

								var1 = false;
							}
							else if(sprUnitCharacters[var2].var_3ef != -1)
							{
								if(sprUnitCharacters[var2].bounceMode > 0)
								{
									var1 = false;
								}
								else if(sprUnitCharacters[var2].bounceMode != -1)
								{
									if(sprUnitCharacters[var2].var_3ef == 1)
									{
										sprUnitCharacters[var2].setFrameSequence(3, shiftFrameSequence);
										sprUnitCharacters[var2].bounceMode = 1;
										sprUnitCharacters[var2].var_3ef = 2;
										var1 = false;
									}
									else if(sprUnitCharacters[var2].var_3ef == 2)
									{
										sprUnitCharacters[var2].bounceValue = -6;
										sprUnitCharacters[var2].bouncing = true;
										sprUnitCharacters[var2].bounceDelta = Renderer.randomFromRange(-2, 3);
										sprUnitCharacters[var2].setFrameSequence(0, shiftFrameSequence);
										sprUnitCharacters[var2].bounceMode = -1;
										sprUnitCharacters[var2].var_3ef = 3;
										var1 = false;
									}
								}
							}
						}

						if(var1)
						{
							var_b08 = 0;
							combatDelayCounterStart = currentMainDisplayable.delayCounter;
							combatState = 4;
							return;
						}
					}
				}
				else if(var_b08 == 0)
				{
					if(currentMainDisplayable.delayCounter - combatDelayCounterStart >= 200L)
					{
						if(unitType == 9)
						{
							var_c08 = new Sprite[currentCharCount*2];
						}
						else if(unitType != 7 && unitType != 1)
						{
							var_c08 = new Sprite[currentCharCount];
						}

						var_b08 = 1;
						var_c8a = 0;
						combatDelayCounterStart = currentMainDisplayable.delayCounter;
						return;
					}
				}
				else if(var_b08 == 1)
				{
					var1 = true;
					if(var_c8a < currentCharCount)
					{
						sprUnitCharacters[var_c8a].setFrameSequence(2, shiftFrameSequence);
						sprUnitCharacters[var_c8a].bounceMode = 1;
						if(unitType != 3 && unitType != 2)
						{
							if(unitType == 1)
							{
								(var10 = Sprite.createSimpleSparkSprite(sprVictimSideEffect, 0, 0, 0, 1, 0, (byte)0)).setFrameSequence(1, shiftFrameSequence);
								var10.setPosition(sprUnitCharacters[var_c8a].currentX + sub_12(var10, sprUnitCharacters[var_c8a].width), sprUnitCharacters[var_c8a].currentY);
								currentMainDisplayable.addCombatAnimationSprite(var10);
							}
							else if(unitType == 7)
							{
								sprUnitCharacters[var_c8a].shakeCounter = 5;

								for(var2 = 0; var2 < 3; ++var2)
								{
									(var3 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprBSmoke, Renderer.randomFromRange(-1, 2), 0, 0, 1, Renderer.randomToRange(4) * 50, (byte)0)).setPosition(sprUnitCharacters[var_c8a].currentX + sprUnit.width / 2, sprUnitCharacters[var_c8a].currentY);
									var3.var_379 = true;
									currentMainDisplayable.addCombatAnimationSprite(var3);
								}
							}
							else if(unitType == 9)
							{
								(var10 = Sprite.createSimpleSparkSprite(sprKingSlash, 0, 0, 0, 1, 200, (byte)0)).setPosition(sprUnitCharacters[0].currentX, sprUnitCharacters[0].currentY + sprUnit.height);
								var10.bounceValue = -sprUnit.height;
								currentMainDisplayable.addCombatAnimationSprite(var10);
								var_c08[0] = Sprite.createSimpleSparkSprite(sprAttackSideEffect, var_4a6[mirror] * 3, -2, 0, -1, 100, (byte)0);
								var9 = sprUnitCharacters[var_c8a].currentX + sub_12(var_c08[var_c8a], sprUnitCharacters[var_c8a].width / 2);
								var4 = sprUnitCharacters[var_c8a].currentY + sprUnit.height - var_c08[var_c8a].height + 2;
								var_c08[0].setPosition(var9, var4);
								var_c08[1] = Sprite.createSimpleSparkSprite(sprAttackSideEffect, var_4a6[mirror] * 3, 1, 0, -1, 100, (byte)0);
								var_c08[1].setPosition(var9, var4);
								currentMainDisplayable.addCombatAnimationSprite(var_c08[1]);
								var_c08[1].var_3b9 = mirror;
								currentMainDisplayable.addCombatAnimationSprite(var_c08[0]);
								var_c08[0].var_3b9 = mirror;
							}
							else if(unitType == 8)
							{
								var_c08[var_c8a] = Sprite.createSimpleSparkSprite((Sprite)null, var_4a6[mirror], 0, 0, -1, 2000, (byte)6);
								var_c08[var_c8a].setPosition(sprUnitCharacters[var_c8a].currentX + sub_12(var_c08[var_c8a], sprUnitCharacters[var_c8a].width + 2), sprUnitCharacters[var_c8a].currentY + 30);
								var_c08[var_c8a].var_379 = true;
								Renderer.vibrate(200);
								currentMainDisplayable.shakeMap(1200);
								Renderer.startPlayer(14, 1);
								currentMainDisplayable.addCombatAnimationSprite(var_c08[var_c8a]);
							}
						}
						else
						{
							var_c08[var_c8a] = Sprite.createSimpleSparkSprite(sprAttackSideEffect, 0, 0, 0, 1, 50, (byte)0);
							var_c08[var_c8a].setPosition(sprUnitCharacters[var_c8a].currentX + sub_12(var_c08[var_c8a], sprUnitCharacters[var_c8a].width), sprUnitCharacters[var_c8a].currentY);
							currentMainDisplayable.addCombatAnimationSprite(var_c08[var_c8a]);
						}
					}

					if((var_c8a += 1) < currentCharCount)
					{
						var1 = false;
					}

					for(var2 = 0; var2 < currentCharCount; ++var2)
					{
						if(sprUnitCharacters[var2].bounceMode > 0)
						{
							var1 = false;
						}
						else if(unitType != 7 && sprUnitCharacters[var2].bounceMode != -1)
						{
							if(sprUnitCharacters[var2].var_3ef == 0)
							{
								sprUnitCharacters[var2].setFrameSequence(3, shiftFrameSequence);
								sprUnitCharacters[var2].bounceMode = 1;
								sprUnitCharacters[var2].var_3ef = 1;
								if(unitType == 8)
								{
									var_c08[var2].active = false;
								}

								var1 = false;
							}
							else if(sprUnitCharacters[var2].var_3ef == 1)
							{
								sprUnitCharacters[var2].setFrameSequence(0, shiftFrameSequence);
								sprUnitCharacters[var2].bounceMode = -1;
								sprUnitCharacters[var2].var_3ef = 2;
								var1 = false;
							}
						}

						if(unitType == 8 && var_c08[var2] != null && var_c08[var2].active)
						{
							(var3 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprBSmoke, var_4a6[mirror] * Renderer.randomFromRange(1, 4), Renderer.randomFromRange(-2, 3), 0, 1, 50 * Renderer.randomToRange(4), (byte)0)).setPosition(sprUnitCharacters[var2].currentX + sub_12(var3, sprUnit.width), var_c08[var2].currentY + Renderer.randomToRange(30 - var_c08[var2].height) - 15);
							var3.var_379 = true;
							currentMainDisplayable.addCombatAnimationSprite(var3);
						}
					}

					if(var1)
					{
						if(currentUnit.type == 9)
						{
							combatState = 6;
						}
						else
						{
							combatState = 4;
						}

						var_b3d = 400;

						if(currentUnit.type == 8)
						{
							var_b3d = 0;
						}

						combatDelayCounterStart = currentMainDisplayable.delayCounter;
						var_b08 = 0;
						return;
					}
				}
			case 2:
			case 3:
			case 5:
			default:
				break;
			case 4:
				if(var_b08 == 0)
				{
					if(currentMainDisplayable.delayCounter - combatDelayCounterStart >= (long)var_b3d)
					{
						currentAttackerSideCombatAnimation.updateKilledCharacters();
						if(unitType != 1)
						{
							currentMainDisplayable.shakeMap(200);
						}

						Renderer.vibrate(200);
						Renderer.startPlayer(14, 1);

						if(sprVictimSideEffect != null)
						{
							var_c2c = Unit.UNIT_CHARACTERS[unitType].length;
						}

						var_b08 = 1;
						return;
					}
				}
				else if(var_b08 == 1)
				{
					if((var_c2c -= 1) < 0)
					{
						combatState = 7;
						combatDelayCounterStart = currentMainDisplayable.delayCounter;
						return;
					}

					if(unitType != 3 && unitType != 2 && unitType != 1)
					{
						var10 = Sprite.createSimpleSparkSprite(sprVictimSideEffect, 0, 0, 0, -1, 0, (byte)0);
					}
					else
					{
						var10 = Sprite.createSimpleSparkSprite(sprVictimSideEffect, 0, 0, 0, 1, 0, (byte)0);
					}

					if(unitType == 2 || unitType == 1)
					{
						var10.autoDeactivate = false;
					}

					var9 = Renderer.randomToRange(currentMainDisplayable.width_ / 2 - var10.width);
					var4 = 0;
					if(currentAttackerSideCombatAnimation.sfmTerrainBackground != null)
					{
						var4 = 0 + (currentAttackerSideCombatAnimation.sfmTerrainBackground.imgheight - var10.height);
					}

					int var5 = (currentMainDisplayable.var_4138 - var4) * (var_c2c * 2 + 1) / (Unit.UNIT_CHARACTERS[unitType].length * 2) - var10.height / 2 + var4;
					if(mirror == 0)
					{
						var9 += currentMainDisplayable.halfWidth_;
					}

					if(unitType == 7 || unitType == 6)
					{
						var10.bounceValue = var5;
						var5 = 0;
					}

					var10.setPosition(var9, var5);
					currentMainDisplayable.addCombatAnimationSprite(var10);

					for(int var6 = 0; var6 < 3; ++var6)
					{
						Sprite var7;
						if(unitType != 7 && unitType != 6)
						{
							(var7 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprBSmoke, Renderer.randomFromRange(-1, 2), 0, -1, 1, 100, (byte)0)).setPosition(var9 + Renderer.randomToRange(var10.width - var7.width), var5 + var10.height - var7.height + 1);
						}
						else
						{
							(var7 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprSmoke, Renderer.randomFromRange(-1, 2), 0, Renderer.randomFromRange(-2, 0), 1, Renderer.randomToRange(4) * 50, (byte)0)).setPosition(var9 + Renderer.randomToRange(var10.width - var7.width), var10.bounceValue + var10.height - var7.height + 1);
							var7.bounceValue = -var10.height / 2;
						}

						currentMainDisplayable.addCombatAnimationSprite(var7);
					}
				}
				break;
			case 6:
				var1 = true;

				for(var2 = 0; var2 < var_c08.length; ++var2)
				{
					if(unitType == 9 && Renderer.randomToRange(2) == 0)
					{
						(var3 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprBSmoke, Renderer.randomFromRange(-2, 1), 0, -1, 1, 100, (byte)0)).setPosition(var_c08[var2].currentX + sub_12(var_c08[var2], 0), var_c08[var2].currentY + var_c08[var2].height - var3.height);
						if(var_c08[var2].var_3ef == 1)
						{
							var3.var_3b9 = currentAttackerSideCombatAnimation.mirror;
						}
						else
						{
							var3.var_3b9 = mirror;
						}

						currentMainDisplayable.addCombatAnimationSprite(var3);
					}

					if(unitType == 9)
					{
						if(mirror == 0)
						{
							if(var_c08[var2].currentX >= currentMainDisplayable.width_)
							{
								if(var_c08[var2].var_3ef == 0)
								{
									var_c08[var2].setPosition(currentMainDisplayable.halfWidth_ - var_c08[var2].width, var_c08[var2].currentY);
									var_c08[var2].var_3b9 = currentAttackerSideCombatAnimation.mirror;
									var_c08[var2].var_3ef = 1;
									var1 = false;
								}
								else if(var_c08[var2].var_3ef == 1)
								{
									currentMainDisplayable.removeCombatAnimationSprite(var_c08[var2]);
									var_c08[var2].var_3ef = 2;
								}
							}
							else
							{
								var1 = false;
							}
						}
						else if(mirror == 1)
						{
							if(var_c08[var2].currentX + var_c08[var2].width < 0)
							{
								if(var_c08[var2].var_3ef == 0)
								{
									var_c08[var2].setPosition(currentMainDisplayable.halfWidth_, var_c08[var2].currentY);
									var_c08[var2].var_3b9 = currentAttackerSideCombatAnimation.mirror;
									var_c08[var2].var_3ef = 1;
									var1 = false;
								}
								else if(var_c08[var2].var_3ef == 1)
								{
									currentMainDisplayable.removeCombatAnimationSprite(var_c08[var2]);
									var_c08[var2].var_3ef = 2;
								}
							}
							else
							{
								var1 = false;
							}
						}
					}
				}

				if(var1)
				{
					var_b08 = 0;
					combatState = 4;
					combatDelayCounterStart = currentMainDisplayable.delayCounter;
					return;
				}
				break;
			case 7:
				if(currentMainDisplayable.delayCounter - combatDelayCounterStart >= 1000L)
				{
					combatState = 0;
					var_226 = true;
				}
		}

	}

	public final void shakeCharacters()
	{
		for(int var1 = 0; var1 < currentCharCount; ++var1)
		{
			sprUnitCharacters[var1].shakeCounter = 6;
		}

	}

	public final void updateWisp()
	{
		switch(combatState)
		{
			case 1:
				if(var_b08 == 0)
				{
					if(currentMainDisplayable.delayCounter - combatDelayCounterStart >= 200L)
					{
						var_b08 = 1;
						combatDelayCounterStart = currentMainDisplayable.delayCounter;
					}
				}
				else
				{
					int var1;
					Sprite var2;

					if(var_b08 == 1)
					{
						bounceAngleDelta += 5;

						if(bounceAngleDelta >= 90)
						{
							++var_b08;
							combatDelayCounterStart = currentMainDisplayable.delayCounter;
						}

						if((bounceAngleDelta - 20) % 15 == 0)
						{
							for(var1 = 0; var1 < sprUnitCharacters.length; ++var1)
							{
								(var2 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprBSmoke, Renderer.randomFromRange(-1, 2), 0, 0, 1, 100, (byte)0)).setPosition(sprUnitCharacters[var1].currentX + Renderer.randomToRange(sprUnit.width - var2.width), sprUnitCharacters[var1].currentY + sprUnit.height - var2.height + 1);
								currentMainDisplayable.addCombatAnimationSprite(var2);
							}
						}
					}
					else if(var_b08 == 2 && currentMainDisplayable.delayCounter - combatDelayCounterStart >= 400L)
					{
						bounceAngleDelta = 20;
						currentAttackerSideCombatAnimation.updateKilledCharacters();
						combatState = 4;
						currentAttackerSideCombatAnimation.var_950 = false;
						combatDelayCounterStart = currentMainDisplayable.delayCounter;
					}

					if((var_a9b += 1) >= 2)
					{
						for(var1 = 0; var1 < currentAttackerSideCombatAnimation.sprUnitCharacters.length; ++var1)
						{
							(var2 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprRedSpark, 0, 0, 0, 1, 50, (byte)0)).setPosition(currentAttackerSideCombatAnimation.sprUnitCharacters[var1].currentX + Renderer.randomToRange(currentAttackerSideCombatAnimation.sprUnitCharacters[var1].width - var2.width), currentAttackerSideCombatAnimation.sprUnitCharacters[var1].currentY + Renderer.randomToRange(currentAttackerSideCombatAnimation.sprUnitCharacters[var1].height - var2.height));
							var2.var_379 = true;
							currentMainDisplayable.addCombatAnimationSprite(var2);
						}

						var_a9b = 0;
						currentAttackerSideCombatAnimation.shakeCharacters();
					}
				}
				break;
			case 4:
				if(currentMainDisplayable.delayCounter - combatDelayCounterStart >= 800L)
				{
					var_226 = true;
					combatState = 0;
				}
		}

		boolean var5 = false;

		if(currentMainDisplayable.delayCounter - sparkAddDelayCounterStart >= 300L)
		{
			var5 = true;
			sparkAddDelayCounterStart = currentMainDisplayable.delayCounter;
		}

		for(int var4 = 0; var4 < sprUnitCharacters.length; ++var4)
		{
			if(var5)
			{
				Sprite var3 = Sprite.createSimpleSparkSprite((Sprite)null, 0, 0, 0, 1, 500, (byte)4);
				var3.setPosition(sprUnitCharacters[var4].currentX + (sprUnitCharacters[var4].width >> 1), sprUnitCharacters[var4].currentY + (sprUnitCharacters[var4].height >> 1) + var_629);
				currentMainDisplayable.addCombatAnimationSprite(var3);
			}

			int var6 = sprUnitCharacters[var4].width / 3;
			sprUnitCharacters[var4].setPosition(characterPositions[var4][0] + (var6 * PaintableObject.cos(charBounceAngles[var4]) >> 10), characterPositions[var4][1] + (var6 * PaintableObject.sin(charBounceAngles[var4]) / 3 >> 10));
			charBounceAngles[var4] = (charBounceAngles[var4] + bounceAngleDelta) % 360;
		}

	}

	public final void update()
	{
		if(var_950)
		{
			if(currentMainDisplayable.delayCounter - var_70b >= 300L)
			{
				var_950 = false;
				var_5c5 = 0;
				var_629 = 0;
			}
			else
			{
				if(var_5c5 > 0)
				{
					var_5c5 = -2;
				}
				else
				{
					var_5c5 = 2;
				}

				var_629 = Renderer.rnd.nextInt() % 1;
			}
		}

		if(var_24e && currentUnitHealth > updatedUnitHealth)
		{
			currentUnitHealth -= 2;

			if(currentUnitHealth < updatedUnitHealth)
			{
				currentUnitHealth = updatedUnitHealth;
			}

			currentMainDisplayable.combatFooterNeedsRepaint = true;
		}

		if(unitType != 8 && unitType != 9 && unitType != 7 && unitType != 1 && unitType != 3 && unitType != 2 && unitType != 6)
		{
			if(unitType == 4)
			{
				updateWisp();
			}
			else
			{
				boolean var1 = false;
				boolean var2;
				int var3;

				switch(combatState)
				{
					case 1:
						if(currentMainDisplayable.delayCounter - combatDelayCounterStart >= 200L)
						{
							combatState = 3;
						}
					case 2:
					case 5:
					default:
						break;
					case 3:
						var2 = true;
						Sprite var4;

						if(var_b08 < currentCharCount)
						{
							if(unitType == 0 || unitType == 5)
							{
								sprUnitCharacters[var_b08].bounceDelta = -6;
							}

							if(unitType == 5)
							{
								sprUnitCharacters[var_b08].shiftX = 2 * var_4a6[mirror];

								for(var3 = 0; var3 < 3; ++var3)
								{
									var4 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprBSmoke, Renderer.randomFromRange(-1, 2), 0, -1, 1, 100, (byte)0);
									var4.setPosition(sprUnitCharacters[var_b08].currentX + Renderer.randomToRange(sprUnit.width - var4.width), sprUnitCharacters[var_b08].currentY + sprUnit.height - var4.height + 1);
									currentMainDisplayable.addCombatAnimationSprite(var4);
								}
							}
							else
							{
								sprUnitCharacters[var_b08].shiftX = var_4a6[mirror];
							}

							sprUnitCharacters[var_b08].setFrameSequence(1, shiftFrameSequence);
							++var_b08;
							var2 = false;
						}

						for(var3 = 0; var3 < var_b08; ++var3)
						{
							if(unitType == 10)
							{
								if(sprUnitCharacters[var3].var_3ef != -1)
								{
									++sprUnitCharacters[var3].var_3ef;

									if(sprUnitCharacters[var3].var_3ef >= 16)
									{
										sprUnitCharacters[var3].setFrameSequence(2, shiftFrameSequence);
										sprUnitCharacters[var3].shiftX = 0;
										sprUnitCharacters[var3].var_3ef = -1;

										var4 = Sprite.createSimpleSparkSprite((Sprite)null, 0, 0, 0, 1, 800, (byte)2);
										var4.setPosition(sprUnitCharacters[var3].currentX + sub_12(var4, sprUnit.width), sprUnitCharacters[var3].currentY + sprUnit.height);
										currentMainDisplayable.addCombatAnimationSprite(var4);
										
										var4 = Sprite.createSimpleSparkSprite(sprKingSlash, 0, 0, 0, 1, 150, (byte)0);
										var4.setPosition(sprUnitCharacters[var3].currentX + sub_12(var4, 24), sprUnitCharacters[var3].currentY + 3);
										currentMainDisplayable.addCombatAnimationSprite(var4);
									}
									else
									{
										var2 = false;
									}
								}
							}
							else if(sprUnitCharacters[var3].bounceValue < 0)
							{
								++sprUnitCharacters[var3].bounceDelta;
								var2 = false;
							}
							else if(sprUnitCharacters[var3].bounceDelta >= 6)
							{
								sprUnitCharacters[var3].bounceValue = 0;
								sprUnitCharacters[var3].bounceDelta = 0;
								sprUnitCharacters[var3].shiftX = 0;
								if(unitType == 0 || unitType == 5)
								{
									sprUnitCharacters[var3].setFrameSequence(2, shiftFrameSequence);
									if(unitType == 0)
									{
										(var4 = Sprite.createSimpleSparkSprite(sprKingSlash, 0, 0, 0, 1, 150, (byte)0)).setPosition(sprUnitCharacters[var3].currentX + sub_12(var4, 14), sprUnitCharacters[var3].currentY + sprUnitCharacters[var3].height);
										var4.bounceValue = 4 - sprUnitCharacters[var3].height;
										currentMainDisplayable.addCombatAnimationSprite(var4);
									}
									else if(unitType == 5)
									{
										(var4 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprRedSpark, 0, 0, 0, 1, 50, (byte)0)).setPosition(sprUnitCharacters[var3].currentX + sub_12(var4, sprUnit.width * 3 / 4), sprUnitCharacters[var3].currentY + sprUnitCharacters[var3].height);
										var4.bounceValue = -var4.height;
										currentMainDisplayable.addCombatAnimationSprite(var4);
									}
								}
							}
						}

						if(var2)
						{
							var_b08 = 0;
							combatState = 6;
							currentAttackerSideCombatAnimation.updateKilledCharacters();
							currentMainDisplayable.shakeMap(200);
							Renderer.vibrate(200);
							Renderer.startPlayer(14, 1);
							combatDelayCounterStart = currentMainDisplayable.delayCounter;
						}
						break;
					case 4:
						var2 = true;
						if(var_b08 < currentCharCount)
						{
							if(unitType != 0 && unitType != 2 && unitType != 5)
							{
								if(unitType == 10)
								{
									sprUnitCharacters[var_b08].var_3ef = 0;
								}
							}
							else
							{
								sprUnitCharacters[var_b08].bounceDelta = -6;
							}

							if(unitType == 5)
							{
								sprUnitCharacters[var_b08].shiftX = -2 * var_4a6[mirror];
							}
							else
							{
								sprUnitCharacters[var_b08].shiftX = -var_4a6[mirror];
							}

							sprUnitCharacters[var_b08].setFrameSequence(3, shiftFrameSequence);
							++var_b08;
							var2 = false;
						}

						for(var3 = 0; var3 < var_b08; ++var3)
						{
							if(unitType == 10)
							{
								if(sprUnitCharacters[var3].var_3ef != -1)
								{
									++sprUnitCharacters[var3].var_3ef;
									if(sprUnitCharacters[var3].var_3ef >= 16)
									{
										sprUnitCharacters[var3].setFrameSequence(0, shiftFrameSequence);
										sprUnitCharacters[var3].shiftX = 0;
										sprUnitCharacters[var3].var_3ef = -1;
									}
									else
									{
										var2 = false;
									}
								}
							}
							else if(sprUnitCharacters[var3].bounceValue < 0)
							{
								++sprUnitCharacters[var3].bounceDelta;
								var2 = false;
							}
							else if(sprUnitCharacters[var3].bounceDelta >= 6)
							{
								sprUnitCharacters[var3].bounceDelta = 0;
								sprUnitCharacters[var3].shiftX = 0;
								sprUnitCharacters[var3].bounceValue = 0;
								sprUnitCharacters[var3].setFrameSequence(0, shiftFrameSequence);
							}
						}

						if(var2)
						{
							var_226 = true;
							combatState = 0;
							combatDelayCounterStart = currentMainDisplayable.delayCounter;
						}
						break;
					case 6:
						if(unitType == 10 && currentMainDisplayable.delayCounter - combatDelayCounterStart >= 400L || (unitType == 0 || unitType == 2 || unitType == 5) && currentMainDisplayable.delayCounter - combatDelayCounterStart >= 50L)
						{
							combatState = 4;
						}
				}
			}
		}
		else
		{
			sub_44();
		}

		if(unitType == 6)
		{
			for(int var5 = 0; var5 < sprUnitCharacters.length; ++var5)
			{
				if(sprUnitCharacters[var5].bouncing)
				{
					sprUnitCharacters[var5].bounceValue = -6 + 4 * PaintableObject.sin(charBounceAngles[var5]) >> 10;
					charBounceAngles[var5] = (charBounceAngles[var5] + 10) % 360;
				}
			}
		}

	}

	public final void updateKilledCharacters()
	{
		var_24e = true;
		killedCharCount = initialUnitCharCount - updatedUnitCharCount;
		currentCharCount = updatedUnitCharCount;

		Sprite var2;
		int var4;

		for(int i = 0; i < killedCharCount; ++i)
		{
			currentMainDisplayable.removeCombatAnimationSprite(sprUnitCharacters[i]);

			var2 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprRedSpark, 0, 0, 0, 1, 0, (byte)0);
			var2.setPosition(sprUnitCharacters[i].currentX + (sprUnitCharacters[i].width - var2.width) / 2, currentMainDisplayable.height);
			var2.bounceValue = sprUnitCharacters[i].currentY + (sprUnitCharacters[i].height - var2.height) / 2 - currentMainDisplayable.height;
			currentMainDisplayable.addCombatAnimationSprite(var2);

			Sprite var3;

			for(var4 = 0; var4 < 3; ++var4)
			{
				var3 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprBSmoke, -1 + var4, 0, Renderer.randomFromRange(-4, -1), 1, Renderer.randomToRange(4) * 50, (byte)0);
				var3.setPosition(sprUnitCharacters[i].currentX + (sprUnitCharacters[i].width - var3.width) / 2, sprUnitCharacters[i].currentY + sprUnitCharacters[i].height - var3.height + 3);
				currentMainDisplayable.addCombatAnimationSprite(var3);
			}

			var3 = Sprite.createSimpleSparkSprite(currentMainDisplayable.sprSmoke, 0, 0, -1, 1, 200, (byte)0);
			var3.setPosition(sprUnitCharacters[i].currentX + (sprUnitCharacters[i].width - var3.width) / 2, sprUnitCharacters[i].currentY + sprUnitCharacters[i].height - var3.height + 3);
			currentMainDisplayable.addCombatAnimationSprite(var3);
		}

		Sprite[] var5 = new Sprite[currentCharCount];
		System.arraycopy(sprUnitCharacters, killedCharCount, var5, 0, currentCharCount);
		sprUnitCharacters = var5;
		var2 = Sprite.createGraphicTextSprite("" + (updatedUnitHealth - initialUnitHealth), 0, -4, (byte)1);
		int var6;
		if(updatedUnitCharCount == 1)
		{
			var6 = sprUnitCharacters[0].currentX + sprUnitCharacters[0].width / 2;
			var4 = sprUnitCharacters[0].currentY + sprUnitCharacters[0].height + 1;
		}
		else
		{
			var6 = currentMainDisplayable.halfWidth_ / 2;
			if(mirror == 1)
			{
				var6 += currentMainDisplayable.halfWidth_;
			}

			var4 = (currentMainDisplayable.var_4138 + backgroundHeight) / 2;
		}

		var2.setPosition(var6, var4);
		var2.var_379 = true;
		currentMainDisplayable.addCombatAnimationSprite(var2);
	}

	public final void paintTerrain(Graphics g, int tx, int ty)
	{
		g.translate(tx, ty);

		int var4 = 0;
		int var6 = 0;

		int var7;
		int var8;

		for(var7 = tilesCountX; var6 < var7; ++var6)
		{
			int var5 = backgroundHeight + ty;
			var8 = 0;

			for(int var9 = tilesCountY; var8 < var9; ++var8)
			{
				sfmTerrainForeground[foregroundFrames[var6][var8]].paint(g, var4, var5);
				var5 += 24;
			}

			var4 += 24;
		}

		if(sfmTerrainBackground != null)
		{
			var6 = sfmTerrainBackground.imgwidth;
			var4 = 0;
			var7 = 0;

			for(var8 = (currentMainDisplayable.halfWidth_ + var6 - 1) / var6; var7 < var8; ++var7)
			{
				sfmTerrainBackground.paint(g, var4, 0);
				var4 += var6;
			}
		}

		g.translate(-tx, -ty);
	}

	public final void drawCurrentUnitHealth(Graphics var1)
	{
		int var2 = currentMainDisplayable.height - MainDisplayable.FOOTER_HEIGHT / 2;
		Renderer.drawAlignedGraphicString(var1, currentUnitHealth + "/" + 100, currentMainDisplayable.halfWidth_ / 2, var2, 1, 3);
	}

	public final void paintShadows(Graphics var1)
	{
		var1.setColor(0x404040);

		for(int var2 = 0; var2 < currentCharCount; ++var2)
		{
			Sprite var3 = sprUnitCharacters[var2];

			if(unitType == 0 || unitType == 4 || unitType == 5 || unitType == 6)
			{
				var1.fillArc(var3.currentX, var3.currentY + var3.height * 4 / 5, var3.width, var3.height / 4, 0, 360);
			}
		}

	}

	static
	{
		byte[] var10000 = new byte[] { (byte)18, (byte)-18 };
		var_4a6 = new byte[] { (byte)3, (byte)-3 };
	}
}
