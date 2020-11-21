package aeii;

// Decompiled by:       Fernflower v0.6
// Date:                22.01.2010 19:41:08
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import java.io.*;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public final class Unit extends Sprite
{

	public static byte UNIT_SPEED_FAST = 12;
	public static byte UNIT_SPEED_SLOW = 6;
	public static byte speed = UNIT_SPEED_SLOW;
	public static MainDisplayable currentMainDisplayable;
	public String name;
	public short rank;
	public int experience;
	public byte[][] characterData;
	public Vector currentRoute;
	public short currentStepInRoute;
	public long unitStateChangeDelayCounterStart;
	public byte type;
	public byte fractionPosInTurnQueue;
	public short currentMapPosX;
	public short currentMapPosY;
	public int var_3f2;
	public int var_408;
	public int offenceMin;
	public int offenceMax;
	public int baseDefence;
	public int baseMoveRange;
	public int health;
	public byte unitState;
	public byte effectState;
	public short moveRangeBonus;
	public short offenceBonus;
	public short defenceBonus;
	public boolean idleAnimationActive;
	public boolean idleAnimationFlag = true;
	public int idleAnimationStopTime;
	public long idleAnimationStopDelayCounterStart;
	public byte var_758;
	public byte var_79f;
	public int var_7b1;
	public Unit followerUnit;
	public byte kingVariant = 0;
	public int kingNumber;
	public int cost;
	public int var_8f7;
	public byte fraction;
	
	public static final String[] UNIT_NAMES = new String[] { "soldier", "archer", "lizard", "wizard", "wisp", "spider", "golem", "catapult", "wyvern", "king", "skeleton", "crystall" };

	public static byte[] UNIT_MOVE_RANGE = new byte[12];
	public static byte[][] UNIT_OFFENCE = new byte[12][2];
	public static byte[] UNIT_DEFENCE = new byte[12];
	public static byte[] MAX_ATTACK_RANGE = new byte[12];
	public static byte[] MIN_ATTACK_RANGE = new byte[12];
	public static byte[][][] UNIT_CHARACTERS = new byte[12][][];
	public static short[] UNIT_COST = new short[12];
	public static final short[] UNIT_PROPERTIES = new short[12];

	private Unit(byte type, byte fraction, int x, int y, boolean var5)
	{
		// super(currentMainDisplayable.getUnitIcon(fraction, type));
		
		super(currentMainDisplayable.sprUnitIcons[(fraction - 1) * 2][type]);
		this.fraction = fraction;
		
		this.type = type;
		unitState = 0;
		currentMapPosX = (short)x;
		currentMapPosY = (short)y;
		setPosition(x * 24, y * 24);
		setRank((byte)0);
		if(var5)
		{
			currentMainDisplayable.units.addElement(this);
		}

	}

	public final void setRank(byte newrank)
	{
		rank = (short)newrank;
		int bonus = newrank * 2;

		offenceMin = UNIT_OFFENCE[type][0] + bonus;
		offenceMax = UNIT_OFFENCE[type][1] + bonus;
		baseDefence = UNIT_DEFENCE[type] + bonus;
		baseMoveRange = UNIT_MOVE_RANGE[type]; // + newrank / 6;

		if(type != 9)
		{
			int var3 = rank / 2;

			if(var3 > 3)
			{
				var3 = 3;
			}

			name = PaintableObject.getLocaleString(139 + type * 4 + var3);
		}
	}

	public final void scheduleIdleAnimationStop(int var1)
	{
		idleAnimationActive = true;
		idleAnimationStopDelayCounterStart = currentMainDisplayable.delayCounter;
		idleAnimationStopTime = var1;
	}

	public static final Unit createUnit(byte type, byte turnpos, int x, int y)
	{
		return createUnitEx(type, turnpos, x, y, true);
	}

	public static final Unit createUnitEx(byte type, byte turnpos, int x, int y, boolean var4)
	{
		if(type == 9 && currentMainDisplayable.fractionsKingCount[turnpos] >= currentMainDisplayable.fractionsAllKings[turnpos].length)
		{
			return null;
		}

		Unit unit = new Unit(type, currentMainDisplayable.fractionsTurnQueue[turnpos], x, y, var4);
		unit.type = type;
		unit.fractionPosInTurnQueue = turnpos;
		unit.health = 100;
		unit.characterData = UNIT_CHARACTERS[type];
		unit.cost = UNIT_COST[type];

		if(type == 9)
		{
			unit.setKingVariant(currentMainDisplayable.fractionsTurnQueue[turnpos] - 1);
			unit.kingNumber = currentMainDisplayable.fractionsKingCount[turnpos];
			currentMainDisplayable.fractionsAllKings[turnpos][unit.kingNumber] = unit;
			++currentMainDisplayable.fractionsKingCount[turnpos];
		}

		return unit;
	}

	public static final Unit createUnitForLevelEditor(byte type, byte turnpos, int x, int y, boolean add)
	{
		Unit unit = new Unit(type, currentMainDisplayable.fractionsTurnQueue[turnpos], x, y, add);
		unit.type = type;
		unit.fractionPosInTurnQueue = turnpos;
		unit.health = 100;
		unit.characterData = UNIT_CHARACTERS[type];
		unit.cost = UNIT_COST[type];

		if(type == 9)
		{
			unit.setKingVariant(currentMainDisplayable.fractionsTurnQueue[turnpos] - 1);
		}

		return unit;
	}

	public final void removeThisUnit()
	{
		currentMainDisplayable.units.removeElement(this);
	}

	public final void setKingVariant(int var1)
	{
		kingVariant = (byte)var1;
		name = PaintableObject.getLocaleString(var1 + 93);
	}

	public final int getOffenceBonusAgainstUnit(Unit var1)
	{
		return getOffenceBonusAgainstUnitEx(var1, currentMapPosX, currentMapPosY);
	}

	public final int getOffenceBonusAgainstUnitEx(Unit victim, int var2, int var3)
	{
		int var4 = offenceBonus;

		if(victim != null)
		{
			if(hasProperty((short)64) && victim.hasProperty((short)1))
			{
				var4 += 15;
			}

			if(type == 4 && victim.type == 10)
			{
				var4 += 15;
			}
		}

		if(hasProperty((short)2) && currentMainDisplayable.getTileType(var2, var3) == 5)
		{
			var4 += 10;
		}

		if(currentMainDisplayable.mapData[var2][var3] == 34)
		{
			var4 += 25;
		}

		return var4;
	}

	public final int getDefenceBonusAgainstUnit(Unit var1)
	{
		return getDefenceBonusAgainstUnitEx(var1, currentMapPosX, currentMapPosY);
	}

	public final int getDefenceBonusAgainstUnitEx(Unit var1, int var2, int var3)
	{
		byte var4 = currentMainDisplayable.getTileType(var2, var3);
		int var5 = defenceBonus + MainDisplayable.TERRAIN_DEFENCE_BONUS[var4];

		if(hasProperty((short)2) && var4 == 5)
		{
			var5 += 15;
		}

		if(currentMainDisplayable.mapData[var2][var3] == 34)
		{
			var5 += 15;
		}

		return var5;
	}

	public final int attackUnit(Unit victim)
	{
		int offence = Renderer.randomFromRange(offenceMin, offenceMax) + getOffenceBonusAgainstUnit(victim);
		int defence = victim.baseDefence + victim.getDefenceBonusAgainstUnit(this);

		int hit = (offence - defence) * health / 100;

		if(hit < 0)
		{
			hit = 0;
		}
		else if(hit > victim.health)
		{
			hit = victim.health;
		}

		victim.health -= hit;
		experience += victim.getQualitySum() * hit;

		return hit;
	}

	public final int getQualitySum()
	{
		return offenceMin + offenceMax + baseDefence;
	}

	public final int getNextRankExperience()
	{
		return getQualitySum() * 100 * 2 / 3; // * 2 / 3;
	}

	public final boolean promote()
	{
		if(true) // rank < 9)
		{
			int var1 = getNextRankExperience();

			if(experience >= var1)
			{
				experience -= var1;
				setRank((byte)(rank + 1));

				return true;
			}
		}

		return false;
	}

	public final boolean canPerformCloseAttack(Unit var1, int var2, int var3)
	{
		return unitState != 4 && health > 0 && Math.abs(currentMapPosX - var2) + Math.abs(currentMapPosY - var3) == 1 && MIN_ATTACK_RANGE[type] == 1;
	}

	public final void addEffect(byte var1)
	{
		effectState |= var1;
		updateEffects();
		if(var1 == 1)
		{
			var_79f = currentMainDisplayable.currentTurningPlayer;
		}

	}

	public final void removeEffect(byte var1)
	{
		effectState = (byte)(effectState & ~var1);
		updateEffects();
	}

	public final void updateEffects()
	{
		moveRangeBonus = 0;
		offenceBonus = 0;
		defenceBonus = 0;

		if((effectState & 1) != 0)
		{
			offenceBonus = (short)(offenceBonus - 10);
			defenceBonus = (short)(defenceBonus - 10);
		}

		if((effectState & 2) != 0)
		{
			offenceBonus = (short)(offenceBonus + 10);
		}

	}

	public final void setMapPosition(int var1, int var2)
	{
		currentMapPosX = (short)var1;
		currentMapPosY = (short)var2;
		super.currentX = (short)(var1 * 24);
		super.currentY = (short)(var2 * 24);
	}

	public final int getCharacterCount()
	{
		int var1 = 100 / characterData.length;
		int var2 = health / var1;

		if(health != 100 && health % var1 > 0)
		{
			++var2;
		}

		return var2;
	}

	public final int sub_462(int var1, int var2, Unit var3)
	{
		return (offenceMin + offenceMax + baseDefence + getOffenceBonusAgainstUnitEx(var3, var1, var2) + getDefenceBonusAgainstUnitEx(var3, var1, var2)) * health / 100;
	}

	public final void fillAttackRangeDataEx(byte[][] var1, int x, int y)
	{
		byte minRange = MIN_ATTACK_RANGE[type];
		byte maxRange = MAX_ATTACK_RANGE[type];
		
		int mStartX = x - maxRange;
		if(mStartX < 0)
		{
			mStartX = 0;
		}

		int mStartY = y - maxRange;
		if(mStartY < 0)
		{
			mStartY = 0;
		}

		int mEndX = x + maxRange;
		if(mEndX >= currentMainDisplayable.mapWidth)
		{
			mEndX = currentMainDisplayable.mapWidth - 1;
		}

		int mEndY = y + maxRange;
		if(mEndY >= currentMainDisplayable.mapHeight)
		{
			mEndY = currentMainDisplayable.mapHeight - 1;
		}

		for(int mx = mStartX; mx <= mEndX; ++mx)
		{
			for(int my = mStartY; my <= mEndY; ++my)
			{
				int range = Math.abs(mx - x) + Math.abs(my - y);
				
				if(range >= minRange && range <= maxRange && var1[mx][my] <= 0)
				{
					var1[mx][my] = 127;
				}
			}
		}

	}

	public final void fillAttackRangeData(byte[][] var1)
	{
		if(hasProperty((short)512))
		{
			fillAttackRangeDataEx(var1, currentMapPosX, currentMapPosY);
		}
		else
		{
			fillMoveRangeData(var1);

			for(int var2 = 0; var2 < currentMainDisplayable.mapWidth; ++var2)
			{
				for(int var3 = 0; var3 < currentMainDisplayable.mapHeight; ++var3)
				{
					if(var1[var2][var3] > 0 && var1[var2][var3] != 127)
					{
						fillAttackRangeDataEx(var1, var2, var3);
					}
				}
			}

		}
	}

	public final Unit[] getUnitsWithinAttackRange(int var1, int var2, byte var3)
	{
		return getUnitsWithinRange(var1, var2, MIN_ATTACK_RANGE[type], MAX_ATTACK_RANGE[type], var3);
	}

	public final Unit[] getUnitsWithinRange(int var1, int var2, int minRange, int maxRange, byte var5)
	{
		Vector var6 = new Vector();
		int var7;
		if((var7 = var1 - maxRange) < 0)
		{
			var7 = 0;
		}

		int var8;
		if((var8 = var2 - maxRange) < 0)
		{
			var8 = 0;
		}

		int var9;
		if((var9 = var1 + maxRange) >= currentMainDisplayable.mapWidth)
		{
			var9 = currentMainDisplayable.mapWidth - 1;
		}

		int var10;
		if((var10 = var2 + maxRange) >= currentMainDisplayable.mapHeight)
		{
			var10 = currentMainDisplayable.mapHeight - 1;
		}

		for(int var11 = var7; var11 <= var9; ++var11)
		{
			for(int var12 = var8; var12 <= var10; ++var12)
			{
				int var13;
				if((var13 = Math.abs(var11 - var1) + Math.abs(var12 - var2)) >= minRange && var13 <= maxRange)
				{
					Unit var14;
					if(var5 == 0)
					{
						if((var14 = currentMainDisplayable.getUnit(var11, var12, (byte)0)) != null)
						{
							if(currentMainDisplayable.playerTeams[var14.fractionPosInTurnQueue] != currentMainDisplayable.playerTeams[fractionPosInTurnQueue])
							{
								var6.addElement(var14);
							}
						}
						else if(type == 7 && currentMainDisplayable.getTileType(var11, var12) == 8 && currentMainDisplayable.isFractionBuilding(currentMainDisplayable.mapData[var11][var12]) && !currentMainDisplayable.sub_e68(var11, var12, currentMainDisplayable.playerTeams[fractionPosInTurnQueue]))
						{
							Unit var15;
							(var15 = createUnitEx((byte)0, (byte)0, var11, var12, false)).type = -1;
							var15.unitState = 4;
							var6.addElement(var15);
						}
					}
					else if(var5 == 1)
					{
						if((var14 = currentMainDisplayable.getUnit(var11, var12, (byte)1)) != null)
						{
							var6.addElement(var14);
						}
					}
					else if(var5 == 2 && (var14 = currentMainDisplayable.getUnit(var11, var12, (byte)0)) != null && currentMainDisplayable.playerTeams[var14.fractionPosInTurnQueue] == currentMainDisplayable.playerTeams[fractionPosInTurnQueue])
					{
						var6.addElement(var14);
					}
				}
			}
		}

		Unit[] var16 = new Unit[var6.size()];
		var6.copyInto(var16);
		return var16;
	}

	public final void plotRoute(int var1, int var2, boolean var3)
	{
		plotRouteEx(var1, var2, var3, false);
	}

	public final void plotRouteEx(int x, int y, boolean var3, boolean var4)
	{
		if(var3)
		{
			currentRoute = findRouteFromPointToPoint(currentMapPosX, currentMapPosY, x, y);
		}
		else
		{
			int j;

			if(var4 && currentMainDisplayable.getUnit(x, y, (byte)0) != null)
			{
				boolean var5 = false;

				for(int i = x - 1; i <= x + 1; ++i)
				{
					for(j = y - 1; j <= y + 1; ++j)
					{
						if(((i != x || j != y) && i == x || j == y) && currentMainDisplayable.getUnit(i, j, (byte)0) == null)
						{
							x = i;
							y = j;
							var5 = true;
							break;
						}
					}

					if(var5)
					{
						break;
					}
				}
			}

			currentRoute = new Vector();
			short[] var15 = new short[] { currentMapPosX, currentMapPosY };
			currentRoute.addElement(var15);
			short var14 = currentMapPosX;
			int var9;
			if((j = Math.abs(x - currentMapPosX)) > 0)
			{
				int var8 = (x - currentMapPosX) / j;

				for(var9 = 0; var9 < j; ++var9)
				{
					var14 = (short)(var14 + var8);
					short[] var10 = new short[] { var14, currentMapPosY };
					currentRoute.addElement(var10);
				}
			}

			short var12 = currentMapPosY;
			if((j = Math.abs(y - currentMapPosY)) > 0)
			{
				var9 = (y - currentMapPosY) / j;

				for(int var13 = 0; var13 < j; ++var13)
				{
					var12 = (short)(var12 + var9);
					short[] var11 = new short[] { var14, var12 };
					currentRoute.addElement(var11);
				}
			}
		}

		var_3f2 = x;
		var_408 = y;
		currentStepInRoute = 1;
		unitState = 1;
	}

	public final Vector findRouteFromPointToPoint(int x0, int y0, int x1, int y1)
	{
		Vector v = null;
		short[] coordPair = new short[] { (short)x1, (short)y1 };
		
		if(x0 == x1 && y0 == y1)
		{
			(v = new Vector()).addElement(coordPair);
		}
		else
		{
			byte top = 0;
			byte bottom = 0;
			byte left = 0;
			byte right = 0;
			
			if(y1 > 0)
			{
				top = currentMainDisplayable.mapAlphaData[x1][y1 - 1];
			}

			if(y1 < currentMainDisplayable.mapHeight - 1)
			{
				bottom = currentMainDisplayable.mapAlphaData[x1][y1 + 1];
			}

			if(x1 > 0)
			{
				left = currentMainDisplayable.mapAlphaData[x1 - 1][y1];
			}

			if(x1 < currentMainDisplayable.mapWidth - 1)
			{
				right = currentMainDisplayable.mapAlphaData[x1 + 1][y1];
			}

			int max = Math.max(Math.max(top, bottom), Math.max(left, right));

			if(max == top)
			{
				v = findRouteFromPointToPoint(x0, y0, x1, y1 - 1);
			}
			else if(max == bottom)
			{
				v = findRouteFromPointToPoint(x0, y0, x1, y1 + 1);
			}
			else if(max == left)
			{
				v = findRouteFromPointToPoint(x0, y0, x1 - 1, y1);
			}
			else if(max == right)
			{
				v = findRouteFromPointToPoint(x0, y0, x1 + 1, y1);
			}

			v.addElement(coordPair);
		}

		return v;
	}

	public final void fillMoveRangeData(byte[][] var1)
	{
		fillMoveRangeDataEx(var1, currentMapPosX, currentMapPosY, baseMoveRange + moveRangeBonus, -1, type, fractionPosInTurnQueue, false);
	}

	public static final boolean fillMoveRangeDataEx(byte[][] alphaData, int x, int y, int stepsCount, int var4, byte unitType, byte turnPos, boolean expand)
	{
		if(stepsCount > alphaData[x][y])
		{
			alphaData[x][y] = (byte)stepsCount;
			int var8;

			/*
			boolean flag;
			
			if(currentMainDisplayable.getUnit(x, y, (byte)0) == null)
			{
				flag = true;
			}
			else
			{
				var8 = stepsCount - getRequiredSteps(x, y - 1, unitType, turnPos);
				
				if(var4 != 1 && var8 >= 0 && sub_695(alphaData, x, y - 1, var8, 2, unitType, turnPos, var7) && var7)
				{
					flag = true;
				}
				else
				{
					var8 = stepsCount - getRequiredSteps(x, y + 1, unitType, turnPos);
					
					if(var4 != 2 && var8 >= 0 && sub_695(alphaData, x, y + 1, var8, 1, unitType, turnPos, var7) && var7)
					{
						flag = true;
					}
					else
					{
						var8 = stepsCount - getRequiredSteps(x - 1, y, unitType, turnPos);
						
						if(var4 != 4 && var8 >= 0 && sub_695(alphaData, x - 1, y, var8, 8, unitType, turnPos, var7) && var7)
						{
							flag = true;
						}
						else
						{
							var8 = stepsCount - getRequiredSteps(x + 1, y, unitType, turnPos);
							
							if(var4 != 8 && var8 >= 0 && sub_695(alphaData, x + 1, y, var8, 4, unitType, turnPos, var7) && var7)
							{
								flag = true;
							}
							else
							{
								flag = false;
							}
						}
					}
				}
			}
			*/
			
			return expand && currentMainDisplayable.getUnit(x, y, (byte)0) == null ? true : (var4 != 1 && (var8 = stepsCount - getRequiredSteps(x, y - 1, unitType, turnPos)) >= 0 && fillMoveRangeDataEx(alphaData, x, y - 1, var8, 2, unitType, turnPos, expand) && expand ? true : (var4 != 2 && (var8 = stepsCount - getRequiredSteps(x, y + 1, unitType, turnPos)) >= 0 && fillMoveRangeDataEx(alphaData, x, y + 1, var8, 1, unitType, turnPos, expand) && expand ? true : (var4 != 4 && (var8 = stepsCount - getRequiredSteps(x - 1, y, unitType, turnPos)) >= 0 && fillMoveRangeDataEx(alphaData, x - 1, y, var8, 8, unitType, turnPos, expand) && expand ? true : var4 != 8 && (var8 = stepsCount - getRequiredSteps(x + 1, y, unitType, turnPos)) >= 0 && fillMoveRangeDataEx(alphaData, x + 1, y, var8, 4, unitType, turnPos, expand) && expand)));
		}
		else
		{
			return false;
		}
	}

	public static final int getRequiredSteps(int x, int y, byte unitType, byte turnPos)
	{
		if(x >= 0 && y >= 0 && x < currentMainDisplayable.mapWidth && y < currentMainDisplayable.mapHeight)
		{
			Unit unit = currentMainDisplayable.getUnit(x, y, (byte)0);

			if(unit != null && currentMainDisplayable.playerTeams[unit.fractionPosInTurnQueue] != currentMainDisplayable.playerTeams[turnPos])
			{
				return 1000;
			}
			else
			{
				byte tileType = currentMainDisplayable.getTileType(x, y);

				if(unitType == 11)
				{
					if(tileType == 4)
					{
						return 1000;
					}
				}
				else
				{
					if(hasPropertyEx(unitType, (short)1))
					{
						return 1;
					}

					if(hasPropertyEx(unitType, (short)2) && tileType == 5)
					{
						return 1;
					}
				}

				return MainDisplayable.TERRAIN_STEPS_REQUIRED[tileType];
			}
		}
		else
		{
			return 10000;
		}
	}

	public final void update()
	{
		if(idleAnimationActive)
		{
			if(currentMainDisplayable.delayCounter - idleAnimationStopDelayCounterStart >= (long)idleAnimationStopTime)
			{
				idleAnimationActive = false;
			}
			else
			{
				idleAnimationFlag = !idleAnimationFlag;
			}
		}

		if(unitState == 1)
		{
			if(currentStepInRoute >= currentRoute.size())
			{
				unitState = 0;
				currentMapPosX = (short)(super.currentX / 24);
				currentMapPosY = (short)(super.currentY / 24);
				currentRoute = null;
				currentStepInRoute = 0;
			}
			else
			{
				if(followerUnit != null && super.currentX % 24 == 0 && super.currentY % 24 == 0)
				{
					followerUnit.plotRoute(currentMapPosX, currentMapPosY, false);
				}

				short[] var1 = (short[])currentRoute.elementAt(currentStepInRoute);
				int var2 = var1[0] * 24;
				int var3 = var1[1] * 24;

				Sprite var4 = null;
				if(followerUnit == null && (var_7b1 += 1) >= 24 / speed / 2)
				{
					var4 = currentMainDisplayable.createSimpleSparkSprite(currentMainDisplayable.sprBSmoke, super.currentX, super.currentY, 0, 0, 1, Renderer.randomFromRange(1, 4) * 50);
					var_7b1 = 0;
				}

				if(var2 < super.currentX)
				{
					super.currentX -= speed;
					if(var4 != null)
					{
						var4.setPosition(super.currentX + super.width, super.currentY + super.height - var4.height);
					}
				}
				else if(var2 > super.currentX)
				{
					super.currentX += speed;
					if(var4 != null)
					{
						var4.setPosition(super.currentX - var4.width, super.currentY + super.height - var4.height);
					}
				}
				else if(var3 < super.currentY)
				{
					super.currentY -= speed;
					if(var4 != null)
					{
						var4.setPosition(super.currentX + (super.width - var4.width) / 2, super.currentY + super.height);
					}
				}
				else if(var3 > super.currentY)
				{
					super.currentY += speed;
					if(var4 != null)
					{
						var4.setPosition(super.currentX + (super.width - var4.width) / 2, super.currentY - var4.height);
					}
				}

				if(super.currentX == var2 && super.currentY == var3)
				{
					currentMapPosX = var1[0];
					currentMapPosY = var1[1];
					++currentStepInRoute;
				}
			}

			super.setPosition(super.currentX, super.currentY);
			nextFrame();
		}
		else
		{
			if(unitState == 0 && currentMainDisplayable.delayCounter - unitStateChangeDelayCounterStart >= 200L)
			{
				nextFrame();
				unitStateChangeDelayCounterStart = currentMainDisplayable.delayCounter;
			}

		}
	}

	public static final boolean hasPropertyEx(byte var0, short var1)
	{
		return (UNIT_PROPERTIES[var0] & var1) != 0;
	}

	public final boolean hasProperty(short var1)
	{
		return hasPropertyEx(type, var1);
	}

	public final void finishMove()
	{
		unitState = 2;
		Unit var1;
		if((var1 = currentMainDisplayable.getUnit(currentMapPosX, currentMapPosY, (byte)1)) != null)
		{
			var1.removeThisUnit();
		}

		if(hasProperty((short)256))
		{
			Unit[] var2 = getUnitsWithinRange(currentMapPosX, currentMapPosY, 1, 2, (byte)2);

			for(int var3 = 0; var3 < var2.length; ++var3)
			{
				var2[var3].addEffect((byte)2);
				currentMainDisplayable.createSimpleSparkSprite(currentMainDisplayable.sprSpark, var2[var3].currentX, var2[var3].currentY, 0, 0, 1, 50);
			}
		}

		currentMainDisplayable.lastFinishedMoveUnit = this;
	}

	public static final Unit[] listAvailableUnits(byte var0)
	{
		Unit[] var1 = new Unit[currentMainDisplayable.fractionsKingCount[var0]];
		int var2 = 0;

		for(int var3 = 0; var3 < var1.length; ++var3)
		{
			if(currentMainDisplayable.fractionsAllKings[currentMainDisplayable.currentTurningPlayer][var3] != null && currentMainDisplayable.fractionsAllKings[currentMainDisplayable.currentTurningPlayer][var3].unitState == 3)
			{
				var1[var2++] = currentMainDisplayable.fractionsAllKings[currentMainDisplayable.currentTurningPlayer][var3];
			}
		}

		Unit[] var5 = new Unit[currentMainDisplayable.allowedUnits + 1 + var2];

		for(byte var4 = 0; var4 < var5.length; ++var4)
		{
			if(var4 < var2)
			{
				var5[var4] = var1[var4];
			}
			else
			{
				var5[var4] = createUnitEx((byte)(var4 - var2), var0, 0, 0, false);
			}
		}

		return var5;
	}

	public final void paint(Graphics var1, int var2, int var3)
	{
		paintEx(var1, var2, var3, false);
	}

	public final void paintEx(Graphics g, int x, int y, boolean gray)
	{
		if(unitState != 4)
		{
			int var6;

			if(idleAnimationActive)
			{
				byte var5;

				if(idleAnimationFlag)
				{
					var5 = -2;
				}
				else
				{
					var5 = 2;
				}

				var6 = Renderer.random() % 1;
				super.paint(g, x + var5, y + var6);
			}
			else if(!gray && unitState != 2)
			{
				super.paint(g, x, y);
			}
			else
			{
				currentMainDisplayable.sprUnitIcons[(fraction - 1) * 2 + 1][type].paint(g, super.currentX + x, super.currentY + y);
			}

			if(type == 9)
			{
				int var7 = super.currentX + x;
				var6 = super.currentY + y;
				
				if(gray || unitState == 2)
				{
					currentMainDisplayable.sprKingHeadIcons[1].paintFrame(g, kingVariant * 2 + super.currentFrame, var7, var6, 0);
				}
				else
				{
					currentMainDisplayable.sprKingHeadIcons[0].paintFrame(g, kingVariant * 2 + super.currentFrame, var7, var6, 0);
				}
			}
		}

	}

	public final void drawHealthString(Graphics var1, int var2, int var3)
	{
		int var4 = super.currentX + var2;
		int var5 = super.currentY + var3;

		if(unitState != 3 && health < 100)
		{
			Renderer.drawGraphicString(var1, "" + health, var4, var5 + super.height - 7, 0);
		}

	}

	public static final void readUnitData(MainDisplayable var0)
	{
		currentMainDisplayable = var0;

		try
		{
			for(int i = 0; i < UNIT_NAMES.length; i++)
			{
				InputStream is = Renderer.getResourceAsStream(UNIT_NAMES[i] + ".unit");

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

					if(line[0].equalsIgnoreCase("MoveRange"))
					{
						UNIT_MOVE_RANGE[i] = (byte)Integer.parseInt(line[1]);
					}
					else if(line[0].equalsIgnoreCase("Attack"))
					{
						UNIT_OFFENCE[i][0] = (byte)Integer.parseInt(line[1]);
						UNIT_OFFENCE[i][1] = (byte)Integer.parseInt(line[2]);
					}
					else if(line[0].equalsIgnoreCase("Defence"))
					{
						UNIT_DEFENCE[i] = (byte)Integer.parseInt(line[1]);
					}
					else if(line[0].equalsIgnoreCase("AttackRange"))
					{
						MAX_ATTACK_RANGE[i] = (byte)Integer.parseInt(line[1]);
						MIN_ATTACK_RANGE[i] = (byte)Integer.parseInt(line[2]);
					}
					else if(line[0].equalsIgnoreCase("Cost"))
					{
						UNIT_COST[i] = (short)Integer.parseInt(line[1]);
					}
					else if(line[0].equalsIgnoreCase("CharCount"))
					{
						UNIT_CHARACTERS[i] = new byte[Integer.parseInt(line[1])][2];
					}
					else if(line[0].equalsIgnoreCase("CharPos"))
					{
						index = Integer.parseInt(line[1]);

						UNIT_CHARACTERS[i][index][0] = (byte)Integer.parseInt(line[2]);
						UNIT_CHARACTERS[i][index][1] = (byte)Integer.parseInt(line[3]);
					}
					else if(line[0].equalsIgnoreCase("HasProperty"))
					{
						UNIT_PROPERTIES[i] = (short)(UNIT_PROPERTIES[i] | 1 << Integer.parseInt(line[1]));
					}
				}
			}
		}
		catch(Exception e)
		{
			try
			{
				DataInputStream dis = new DataInputStream(Renderer.getResourceAsStream("units.bin"));

				for(int i = 0; i < 12; ++i)
				{
					UNIT_MOVE_RANGE[i] = dis.readByte();
					UNIT_OFFENCE[i][0] = dis.readByte();
					UNIT_OFFENCE[i][1] = dis.readByte();
					UNIT_DEFENCE[i] = dis.readByte();
					MAX_ATTACK_RANGE[i] = dis.readByte();
					MIN_ATTACK_RANGE[i] = dis.readByte();
					UNIT_COST[i] = dis.readShort();

					byte var3 = dis.readByte();
					UNIT_CHARACTERS[i] = new byte[var3][2];

					for(int var4 = 0; var4 < var3; ++var4)
					{
						UNIT_CHARACTERS[i][var4][0] = dis.readByte();
						UNIT_CHARACTERS[i][var4][1] = dis.readByte();
					}

					byte var6 = dis.readByte();

					for(int var5 = 0; var5 < var6; ++var5)
					{
						UNIT_PROPERTIES[i] = (short)(UNIT_PROPERTIES[i] | 1 << dis.readByte());
					}
				}

				dis.close();
			}
			catch(Exception e2)
			{
				e.printStackTrace();
				e2.printStackTrace();
			}
		}
	}
}