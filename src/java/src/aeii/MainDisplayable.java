package aeii;

// Decompiled by:       Fernflower v0.6
// Date:                22.01.2010 19:43:50
// Copyright:           2008-2009, Stiver
// Home page:           http://www.reversed-java.com

import com.one.file.FileConnection;
import java.io.*;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.*;

public final class MainDisplayable extends PaintableObject implements Runnable, CommandListener, ItemStateListener
{
	public static final int MAX_SAVES = 10;
	public static final String FSO_MAP_SUFFIX = ".aem";
	
	public static String[] FSO_PARENT_DIR;
	public static String[] FSO_PARENT_DIR_SAVE;

	public static String[] MENU_INGAME_LEVEL_EDITOR_ITEMS;

	public String midletVersion = "?";
	public static byte FOOTER_HEIGHT = 32;
	public int width_;
	public int height_;
	public int halfWidth_;
	public int halfHeight_;
	public int missionsComplete = 0;
	public static String[] skirmishLevelsNames = new String[12];
	public static int[] SKIRMISH_LOCKED_LEVELS = new int[] { -1, -1, -1, -1, -1, -1, -1, -1 }; // new int[] { 4, 5, 6, 7, 8, 9, 10, 11 };
	public boolean[] skirmishLevelLockFlags;
	public static final int[] SKIRMISH_START_MONEY = new int[] { 500, 1000, 2000, 5000, 10000, 25000, 50000, 75000, 100000, 150000, 200000 };
	public static final int[] SKIRMISH_UNIT_CAPS = new int[] { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200 };
	public int skirmishStartMoney;
	public int skirmishUnitCap;
	public byte skirmishMode;
	public static int KEY_LSK = 1024;
	public static int KEY_RSK = 2048;
	public String[] INGAME_MENU_TEXT;
	public String[] INGAME_ACTIONS_TEXT;
	public SpriteFrame[] sprActionIcons;
	public String[] MENU_ITEMS_TEXT;
	public SpriteFrame[] sfmMenuIcons;
	public String[] MENU_PLAYER_MODE_TEXT;
	public String[] MENU_ON_OFF_TEXT;
	public byte[] var_5e3;
	public byte[] MAIN_MENU_ITEMS;
	public byte[] PLAY_MENU_ITEMS;
	public static final byte[][] CURSOR_FRAME_SEQUENCES = new byte[][] { { (byte)0, (byte)1 }, { (byte)2, (byte)3, (byte)4 }, { (byte)0, (byte)1 }, { (byte)5 } };
	public static final byte[] var_6d7 = new byte[] { (byte)0 };
	public long var_726;
	public long var_755;

	public static final int[][][] PALETTE_REPLACE_TABLE = new int[][][]
	{
		new int[0][],
		{
			{ 150, 217, 244 },
			{ 65, 149, 233 },
			{ 0, 100, 198 },
			{ 12, 53, 112 }
		},
		{
			{ 244, 158, 156 },
			{ 219, 36, 113 },
			{ 161, 0, 112 },
			{ 95, 5, 120 }
		},
		{
			{ 171, 237, 90 },
			{ 99, 190, 37 },
			{ 0, 153, 55 },
			{ 0, 85, 82 }
		},
		{
			{ 0, 118, 150 },
			{ 0, 65, 114 },
			{ 0, 43, 75 },
			{ 0, 22, 48 }
		}
	};

	public static final String[] FRACTION_COLOR_PREFIXES = new String[]
	{
		"blue",
		"red",
		"green",
		"black"
	};

	public static final int[] FRACTION_COLORS = new int[] { 0xA0A0A0, 0x65C6, 0xE80052, 0x9A31, 0x4172 };
	public static final int[] FRACTION_BACKGROUND_MUSIC = new int[] { -1, 2, 3, 2, 3 };
	public static final int[] FRACTION_BATTLE_MUSIC = new int[] { -1, 4, 5, 4, 5 };
	public Sprite[][] sprUnitIcons;

	public static final byte[] WATER_PAIR = new byte[] { (byte)1, (byte)2 };

	public static final byte BROKEN_BUILDING = 27;
	public static final byte FRACTION_BUILDINGS = 37;
	public static final byte CUSTOM_TILES = FRACTION_BUILDINGS + 10;
	
	public static byte[] TERRAIN_DEFENCE_BONUS = new byte[] { (byte)0, (byte)5, (byte)10, (byte)10, (byte)15, (byte)0, (byte)5, (byte)15, (byte)15, (byte)15 };
	public static byte[] TERRAIN_STEPS_REQUIRED = new byte[] { (byte)1, (byte)1, (byte)2, (byte)2, (byte)3, (byte)3, (byte)1, (byte)1, (byte)1, (byte)1 };
	
	//public int tilesCount;
	public SpriteFrame[] sfmSmallTiles;
	public byte[] tilesTypes;
	public byte[] smallTiles;
	public int mapWidthPixels;
	public int mapHeightPixels;
	public int mapX;
	public int mapY;
	public int mapWidth;
	public int mapHeight;
	private SpriteFrame sprTombStone;
	public SpriteFrame[] sfmTiles;
	public Sprite sprCursor;
	public Sprite sprCursorCopy;
	public Sprite sprSideArrow;
	public Sprite sprArrow;
	public Sprite sprButtons;
	public Sprite sprMenu;
	public Sprite sprSmoke;
	public Sprite sprSpark;
	public Sprite sprRedSpark;
	public Sprite sprStatus;
	public Sprite sprSmallSpark;
	public Sprite sprPortraits;
	public int cursorMapX;
	public int cursorMapY;
	public byte[][] mapData;
	public byte gameState;
	public byte prevGameState;
	public long delayCounter;
	public int currentMission;
	public int currentSkirmishLevelNumber;
	public int currentAttackTargetUnit;
	public Unit[] unitsWithinAttackRange;
	public Unit currentSelectedUnit;
	public int selectedUnitPrevMapX;
	public int selectedUnitPrevMapY;
	public byte[][] mapAlphaData;
	public boolean paintMapAlphaDataFlag;
	public boolean showAtackRange;
	public boolean drawCursorFlag;
	public Vector units;
	public Vector currentRoute;
	public int var_129c;
	public int var_12cb;
	public long var_12ff;
	public byte turnQueueLength;
	public byte[] fractionsPosInTurnQueue;
	public byte[] fractionsTurnQueue;
	public byte[] playerTeams;
	public byte currentTurningPlayer;
	public short currentTurn;
	public Unit[] fractionKings;
	public Unit[][] fractionsAllKings;
	public int[] fractionsKingCount;
	public int[] money;
	public byte[][] fractionKingPositions;
	public byte[] playerModes;
	public AuxDisplayable auxInGameMenu;
	public Vector activeEffects;
	public Vector newEffects;
	public Unit lastDeadUnit;
	public Unit var_16e5;
	public long var_1728;
	public Unit var_1774;
	public byte var_17b3;
	public long var_17c8;
	public int var_17d5;
	public boolean var_17f7;
	public boolean var_1824;
	public byte mode;
	public SpriteFrame sfmLogo;
	public SpriteFrame sfmSplash;
	public SpriteFrame sfmGameLogo;
	public SpriteFrame sfmGlow;
	public int introTransMode;
	public boolean var_19c3;
	public int combatDrapValue;
	public int var_1a23;
	public int var_1a3b;
	public int var_1a98;
	public long var_1ac1;
	public Unit currentAttackUnit;
	public Unit currentAttackVictimUnit;
	public boolean var_1b42;
	public long var_1b6a;
	public boolean cursorPositionChanged;
	public long var_1c06;
	public int currentWaterPairTile;
	public int var_1c69;
	public SpriteFrame[] sprWaterPair;
	public boolean paintLoadingStringFlag;
	public boolean paintPressAnyKeyFlag;
	public static int[] INSTRUCTION_TITLES = new int[] { 85, 83, 83, 83, 83, 83, 83, 83, 83, 175, 84, 84, 84, 175, 147, 159, 151, 155, 167, 171 };
	public static int[] INSTRUCTION_TEXT_MESSAGES = new int[] { 15, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214  };
	public int currentHelp;
	public AuxDisplayable auxInstructionsContainer;
	public AuxDisplayable auxInstructionsPartSelector;
	public AuxDisplayable auxInstructionsTextMessage;
	public AuxDisplayable auxObjectivesMessage;
	public int allowedUnits;
	public AuxDisplayable auxSkirmishLevelList;
	public AuxDisplayable auxSkirmishLevelListContainer;
	public AuxDisplayable auxSelectLevelList;
	public boolean var_202c;
	public int var_207c;
	public byte[][] buildingData;
	public byte[][] castleData;
	public Sprite sprBigCircle;
	public Sprite sprSmallCircle;
	public Unit currentUnitUnderCursor;
	public Sprite sprHudIcons;
	public Sprite sprHudIcons2;
	public int var_21e9;
	public int var_223e;
	public int glowX;
	public boolean activeFlag;
	public int statusBarOffset;
	public boolean var_2319;
	public Sprite sprMiniIcons;
	public Sprite[] buildingEffects;
	public Sprite sprBSmoke;
	public AuxDisplayable auxSkirmishMapPreviewContainer;
	public AuxDisplayable auxSkirmishTeamSetupContainer;
	public AuxDisplayable auxSkirmishStartMoneySelector;
	public AuxDisplayable auxSkirmishUnitCapSelector;
	public AuxDisplayable[] auxSkirmishPlayerModeSelector;
	public AuxDisplayable[] auxSkirmishPlayerTeamSelector;
	public AuxDisplayable auxSettingsMenuContainer;
	public AuxDisplayable[] auxSettingsSwitches;
	public Sprite sprAlpha;
	public int drapAlphaValue;
	public boolean drawAlphaDrap;
	public boolean inverseDrapAlpha;
	public boolean updateAlphaDrap;
	public Vector var_26d9;
	public Sprite sprLevelUp;
	public int var_2786;
	public Sprite[] sprKingHeadIcons;
	public AuxDisplayable auxUnitBuyContainer;
	public AuxDisplayable auxBuyUnitInfo;
	public AuxDisplayable auxBuyUnitSelector;
	public AuxDisplayable auxBuyUnitDescription;
	public Sprite sprArrowIcons;
	public Sprite heavenFuryBlast;
	public Unit heavenFuryTarget;
	public int var_2985;
	public boolean var_29c1;
	public boolean var_29de;
	public String currentMapName;
	public String[] saveInfoStrings;
	public byte[] saveCurrentFraction;
	public int[] saveCurrentMission;
	public AuxDisplayable auxLoadGameContainer;
	public AuxDisplayable auxSaveGameContainer;
	public AuxDisplayable var_2b0e;
	public AuxDisplayable var_2b46;
	public AuxDisplayable auxExitGameQuestion;
	public AuxDisplayable auxNewGameQuestion;
	public AuxDisplayable auxSaveOverwrireQuestion;
	public byte skirmishLevelPlayerCount;
	public byte[] skirmishLevelFractions;
	public int downloadedSkirmishLevelCount;
	public String[] downloadedSkirmishLevelNames;
	public int[] downloadedSkirmishLevelNumbers;
	public String[] ONLINE_MENU_ITEMS;
	public String[] DOWNLOAD_LEVELS_MENU_ITEMS;
	public AuxDisplayable auxOnLineMenuContainer;
	public AuxDisplayable auxDownloadLevelsMenuContainer;
	public AuxDisplayable var_2df9;
	public AuxDisplayable var_2e13;
	public AuxDisplayable var_2e20;
	public AuxDisplayable auxDownloadableSkirmishLevelsContainer;
	public AuxDisplayable var_2e5d;
	public AuxDisplayable var_2e87;
	public int var_2ee6;
	public String var_2f35;
	public int availableRMSDownloadSize;
	public boolean macrospaceHighScoreUpload;
	public boolean statusBarNeedsRepaint;
	public boolean tileIconNeedsRepaint;
	public SpriteFrame sfmGameOver;
	public Image[][] alphaCoveredTiles;
	public int loadProgress;
	//public static final String[] CHEAT_KEY_SEQUENCES = new String[] { "14281428", "18241824", "14148282" };
	public StringBuffer cheatStringBuffer;
	public Unit var_31be;
	public int mapStepMax;
	public int mapStepMin;
	public boolean var_326f;
	public int alphaWindowSize;
	public int lineHeight;
	public int var_3308;
	public int linesOnScreen;
	public int var_3395;
	public SpriteFrame sprIntro;
	public String[] textLines;
	public boolean var_3437;
	public byte var_3491;
	public int var_34b0;
	public int var_34ec;
	public int introHeight;
	public int var_357f;
	public int var_35c4;
	public int var_3622;
	public int var_3685;
	public int var_3695;
	public int currentIncome;
	public static final byte[] var_36cf = new byte[] { (byte)0, (byte)2, (byte)3, (byte)3, (byte)1, (byte)3, (byte)3, (byte)3, (byte)3, (byte)3, (byte)3, (byte)3 };
	public byte var_36e4;
	public int var_3733;
	public int var_3781;
	public Unit var_37c2;
	public Unit var_37e4;
	public Unit var_381f;
	public int var_3864;
	public long var_38a4;
	public Unit[] var_38d5;
	public Unit[] var_3903;
	public byte[] var_3947;
	public int[][] var_395f;
	public int[] var_399e;
	public int var_39d0;
	public byte[][] var_39e3;
	public int castleCount;
	public int var_3a15;
	public int var_3a34;
	public int var_3a41;
	public int var_3a5c;
	public Vector var_3aad;
	public boolean var_3aed;
	public int scriptUpdateDelayCounter;
	public AuxDisplayable auxMapNameMessage;
	public Unit crystallEscortLeader;
	public Unit crystallEscortCrystall;
	public Unit crystallEscortFollower;
	public Unit templeWarrior;
	public Unit var_3c5c;
	public Unit lastFinishedMoveUnit;
	public int scriptState;
	public String[][][] script;
	public long var_3cee;
	public int var_3d08;
	public boolean var_3d48;
	public boolean var_3d57;
	public int mapTargetX;
	public int mapTargetY;
	public int var_3e0c;
	public CombatAnimation combatAttackerUnitAnimation;
	public CombatAnimation combatVictimUnitAnimation;
	public long var_3eaa;
	public boolean var_3f02;
	public Vector combatAnimationSprites;
	public boolean shakeMapFlag;
	public long shakeMapTime;
	public long shakeMapDelayCounterStart;
	public boolean victimUnitResponded;
	public boolean combatDrawDrapFlag;
	public boolean combatHeaderNeedsRepaint;
	public boolean combatFooterNeedsRepaint;
	public int var_4138;
	public int var_4191;
	public int var_41ef;
	public String[] var_4217;
	public String[] var_4263;
	public String[] var_4277;
	public String[] var_4289;
	public String[] var_42a0;
	public int[] var_42c2;
	public String provisionHighScorePortalCode;
	public String provisionHighScoreGameCode;
	public String provisionHighScoreURL;
	private ByteArrayOutputStream var_43a8;
	private DataOutputStream var_43e4;
	public int var_442b;
	public boolean var_4482;
	public PaintableObject var_44bb;
	public int var_44e6;
	public AuxDisplayable auxImportLevelsContainer;
	public AuxDisplayable auxImportLevelsList;
	public AuxDisplayable auxExportLevelsContainer;
	public AuxDisplayable auxExportLevelsList;
	public FileSystemObject fso;
	public String[] currentFileList;
	public byte currentTile;
	public byte currentUnitType;
	public Unit currentUnit;
	public byte levelEditorMode;
	public AuxDisplayable auxEditorLevelListContainer;
	public AuxDisplayable auxEditorLevelList;
	public AuxDisplayable auxExportableLevelListContainer;
	public AuxDisplayable auxExportableLevelList;
	public int[] tileProbabilities;
	public int[] tilePrevProbabilities;
	public boolean[] selflags;

	public Form frmNewLevel;
	public Form frmFillSetup;
	public Form frmRename;
	public Form frmResize;
	public Form frmProbabilities;
	public Form frmRandomSelect;
	public TextField tfLevelName;
	public TextField tfMapWidth;
	public TextField tfMapHeight;
	public ChoiceGroup cgFillWith;
	public TextField tfProbability;
	public Gauge gTileSelector;
	public ImageItem imgTile;
	public TextField tfDeltaLeft;
	public TextField tfDeltaRight;
	public TextField tfDeltaTop;
	public TextField tfDeltaBottom;
	public TextBox tbConsole;
	public Gauge gCoverageFactor;

	public Command cmdOK;
	public Command cmdCancel;
	public Command cmdSelectAll;
	public Command cmdDeselectAll;
	public Command cmdProbabilities;
	public Command cmdUniqueName;

	public MainDisplayable()
	{
		FSO_PARENT_DIR = new String[] { FileSystemObject.PARENT_DIR };
		FSO_PARENT_DIR_SAVE = new String[] { PaintableObject.getLocaleString(300), FileSystemObject.PARENT_DIR };

		MENU_INGAME_LEVEL_EDITOR_ITEMS = new String[] { PaintableObject.getLocaleString(305), PaintableObject.getLocaleString(306), PaintableObject.getLocaleString(307), PaintableObject.getLocaleString(303), PaintableObject.getLocaleString(70), PaintableObject.getLocaleString(293), PaintableObject.getLocaleString(60) };
		
		levelEditorMode = 0;

		cmdOK = new Command(PaintableObject.getLocaleString(12), Command.OK, 1);
		cmdCancel = new Command(PaintableObject.getLocaleString(13), Command.CANCEL, 5);
		cmdSelectAll = new Command(PaintableObject.getLocaleString(301), Command.OK, 3);
		cmdDeselectAll = new Command(PaintableObject.getLocaleString(302), Command.OK, 4);
		cmdProbabilities = new Command(PaintableObject.getLocaleString(304), Command.OK, 2);
		cmdUniqueName = new Command(PaintableObject.getLocaleString(316), Command.OK, 2);

		if(Renderer.devmode)
		{
			INGAME_MENU_TEXT = new String[] { PaintableObject.getLocaleString(66), PaintableObject.getLocaleString(70), PaintableObject.getLocaleString(71), PaintableObject.getLocaleString(4), PaintableObject.getLocaleString(314), PaintableObject.getLocaleString(60) };
		}
		else
		{
			INGAME_MENU_TEXT = new String[] { PaintableObject.getLocaleString(66), PaintableObject.getLocaleString(70), PaintableObject.getLocaleString(71), PaintableObject.getLocaleString(4), PaintableObject.getLocaleString(60) };
		}

		INGAME_ACTIONS_TEXT = new String[] { PaintableObject.getLocaleString(63), PaintableObject.getLocaleString(67), PaintableObject.getLocaleString(68), PaintableObject.getLocaleString(62), PaintableObject.getLocaleString(69), PaintableObject.getLocaleString(61), PaintableObject.getLocaleString(64) };

		sprActionIcons = new SpriteFrame[INGAME_ACTIONS_TEXT.length];
		MENU_ITEMS_TEXT = new String[] { PaintableObject.getLocaleString(1), PaintableObject.getLocaleString(2), PaintableObject.getLocaleString(5), PaintableObject.getLocaleString(3), PaintableObject.getLocaleString(6), PaintableObject.getLocaleString(8), PaintableObject.getLocaleString(7), PaintableObject.getLocaleString(9), PaintableObject.getLocaleString(10), PaintableObject.getLocaleString(11), PaintableObject.getLocaleString(4) };
		sfmMenuIcons = new SpriteFrame[MENU_ITEMS_TEXT.length];
		MENU_PLAYER_MODE_TEXT = new String[] { PaintableObject.getLocaleString(35), PaintableObject.getLocaleString(36), PaintableObject.getLocaleString(37) };
		MENU_ON_OFF_TEXT = new String[] { PaintableObject.getLocaleString(29), PaintableObject.getLocaleString(30) };
		var_5e3 = new byte[] { (byte)0, (byte)6, (byte)5, (byte)7, (byte)8, (byte)9 };
		MAIN_MENU_ITEMS = new byte[] { (byte)0, (byte)6, (byte)5, (byte)7, (byte)8, (byte)9 };
		PLAY_MENU_ITEMS = new byte[] { (byte)1, (byte)2, (byte)3, (byte)4 };
		var_726 = 0L;
		paintMapAlphaDataFlag = false;
		showAtackRange = false;
		drawCursorFlag = true;
		units = new Vector();
		turnQueueLength = 2;
		fractionsPosInTurnQueue = new byte[5];
		fractionsTurnQueue = new byte[4];
		playerTeams = new byte[4];
		currentTurningPlayer = 0;
		money = new int[4];
		fractionKingPositions = new byte[4][2];
		playerModes = new byte[4];
		activeEffects = new Vector();
		newEffects = new Vector();
		var_17f7 = false;
		var_1824 = false;
		var_19c3 = false;
		var_1b42 = true;
		cursorPositionChanged = false;
		paintLoadingStringFlag = false;
		currentHelp = -1;
		allowedUnits = 8;
		var_202c = false;
		activeFlag = true;
		buildingEffects = new Sprite[0];
		var_26d9 = new Vector(2);
		var_2985 = 0;
		var_29c1 = true;
		ONLINE_MENU_ITEMS = new String[] { PaintableObject.getLocaleString(46), PaintableObject.getLocaleString(47), PaintableObject.getLocaleString(291) };
		DOWNLOAD_LEVELS_MENU_ITEMS = new String[] { PaintableObject.getLocaleString(289), PaintableObject.getLocaleString(298), PaintableObject.getLocaleString(48), PaintableObject.getLocaleString(49) };
		macrospaceHighScoreUpload = true;
		statusBarNeedsRepaint = true;
		tileIconNeedsRepaint = true;
		cheatStringBuffer = new StringBuffer();
		var_31be = null;
		mapStepMax = 12;
		mapStepMin = 1;
		var_357f = 0;
		var_3622 = 24;
		var_3685 = 8;
		var_3695 = var_3685 >> 1;
		var_36e4 = 0;
		var_3aed = false;
		var_3c5c = null;
		lastFinishedMoveUnit = null;
		var_3d48 = false;
		var_3d57 = false;
		mapTargetX = -1;
		mapTargetY = -1;
		var_3e0c = 0;
		combatAnimationSprites = new Vector();
		shakeMapFlag = false;
		provisionHighScorePortalCode = "Macrospace";
		provisionHighScoreGameCode = "msaeii";
		provisionHighScoreURL = "http://msaeii.scores.macrospace.com/connectx/in";
		mode = 4;
	}

	public final void load() throws IOException
	{
		setLoadProgress(0);
		Renderer.loadResources();
		setLoadProgress(18);
		Renderer.createPlayerArrays();

		for(int var1 = 0; var1 < Renderer.MUSIC_NAMES.length; ++var1)
		{
			Renderer.createPlayer(var1);
			setLoadProgress(19 + var1);
		}

		setLoadProgress(28);
		AuxDisplayable.currentMainDisplayable = this;
		setLoadProgress(29);
		PaintableObject.createSinTab();
		setLoadProgress(30);
		Renderer.loadCharSprites();
		setLoadProgress(32);
		sprActionIcons = (new Sprite("action_icons")).frames;
		setLoadProgress(34);
		sfmMenuIcons = (new Sprite("menu_icons")).frames;
		setLoadProgress(36);
		sprHudIcons = new Sprite("hud_icons");
		setLoadProgress(38);
		sprHudIcons2 = new Sprite("hud_icons_2");
		setLoadProgress(40);
		sprArrow = new Sprite("arrow");
		setLoadProgress(42);
		sprSideArrow = new Sprite("side_arrow");
		setLoadProgress(44);
		sprButtons = new Sprite("buttons");
		setLoadProgress(46);
		sprMenu = new Sprite("menu");
		setLoadProgress(48);
		sprBigCircle = new Sprite("big_circle");
		setLoadProgress(50);
		sprSmallCircle = new Sprite("small_circle");
		setLoadProgress(52);
		sprSmallSpark = new Sprite("small_spark");
		setLoadProgress(54);
		sprAlpha = new Sprite("alpha");
		setLoadProgress(56);

		try
		{
			sfmGameOver = new SpriteFrame("gameover");
		}
		catch(Exception var15)
		{
			;
		}

		setLoadProgress(58);
		
		sfmLogo = new SpriteFrame("ms_logo");
		setLoadProgress(62);
		
		DataInputStream var17;
		InputStream is = Renderer.getResourceAsStream("tiles0.prop");
		
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
				
				if(line[0].equalsIgnoreCase("TypeCount"))
				{
					index = Integer.parseInt(line[1]);
					
					TERRAIN_STEPS_REQUIRED = new byte[index];
					TERRAIN_DEFENCE_BONUS = new byte[index];
					
					CombatAnimation.BACKGROUNDS = new String[index];
					CombatAnimation.FOREGROUNDS = new String[index];
				}
				else if(line[0].equalsIgnoreCase("TypeDef"))
				{
					index = Integer.parseInt(line[1]);

					TERRAIN_STEPS_REQUIRED[index] = (byte)Integer.parseInt(line[2]);
					TERRAIN_DEFENCE_BONUS[index] = (byte)Integer.parseInt(line[3]);
					
					CombatAnimation.BACKGROUNDS[index] = line[4];
					CombatAnimation.FOREGROUNDS[index] = line[5];
				}
				else if(line[0].equalsIgnoreCase("TileCount"))
				{
					tilesTypes = new byte[Integer.parseInt(line[1])];
					smallTiles = new byte[tilesTypes.length];
				}
				else if(line[0].equalsIgnoreCase("TileDef"))
				{
					index = Integer.parseInt(line[1]);
					
					tilesTypes[index] = (byte)Integer.parseInt(line[2]);
					smallTiles[index] = (byte)Integer.parseInt(line[3]);
				}
			}

			is.close();
		}
		else
		{
			var17 = new DataInputStream(Renderer.getResourceAsStream("tiles0.bin"));
			short var2 = var17.readShort();
			var17.readShort();
			tilesTypes = new byte[var2];

			for(int var4 = 0; var4 < var2; ++var4)
			{
				tilesTypes[var4] = var17.readByte();
			}

			var17.close();
		}
		
		setLoadProgress(64);
		Sprite var19 = new Sprite("stiles0");
		sfmSmallTiles = var19.frames;
		setLoadProgress(70);
		sprMiniIcons = new Sprite("mini_icons");
		setLoadProgress(72);

//		SpriteFrame[] var71 = (new Sprite("tiles0")).frames;
//		tilesCount = var71.length;
//		SpriteFrame[] var51 = new SpriteFrame[10];
//
//		for(byte fraction = 0; fraction <= 4; ++fraction)
//		{
//			Sprite var9 = new Sprite("buildings", fraction);
//
//			for(byte unit = 0; unit < 2; ++unit)
//			{
//				var51[fraction * 2 + unit] = var9.frames[unit];
//			}
//		}
//
//		sfmTiles = new SpriteFrame[var71.length + var51.length];
//		System.arraycopy(var71, 0, sfmTiles, 0, var71.length);
//		System.arraycopy(var51, 0, sfmTiles, tilesCount, var51.length);

		sfmTiles = (new Sprite("tiles0")).frames;

		width_ = super.width;
		height_ = super.height;
		halfWidth_ = width_ >> 1;
		halfHeight_ = height_ >> 1;
		introTransMode = 0;

		int var5;
		for(var5 = 0; var5 < 12; ++var5)
		{
			skirmishLevelsNames[var5] = PaintableObject.getLocaleString(101 + var5);
		}

		loadMainSettings();
		setLoadProgress(74);

		try
		{
			missionsComplete = Renderer.getRMSData("settings", 1)[0];
		}
		catch(Exception var14)
		{
			;
		}

		setLoadProgress(76);
		downloadedSkirmishLevelNames = new String[0];
		downloadedSkirmishLevelNumbers = new int[0];

		try
		{
			byte[] var20 = Renderer.getRMSData("settings", 2);
			var17 = new DataInputStream(new ByteArrayInputStream(var20));
			downloadedSkirmishLevelCount = var17.readInt();
			downloadedSkirmishLevelNumbers = new int[downloadedSkirmishLevelCount];
			downloadedSkirmishLevelNames = new String[downloadedSkirmishLevelCount];

			for(int var6 = 0; var6 < downloadedSkirmishLevelCount; ++var6)
			{
				downloadedSkirmishLevelNumbers[var6] = var17.readInt();
				downloadedSkirmishLevelNames[var6] = var17.readUTF();
			}

			var17.close();
		}
		catch(Exception var16)
		{
			;
		}

		setLoadProgress(80);

		availableRMSDownloadSize = Renderer.getAvailableRMSSize("download");
		setLoadProgress(84);

		saveInfoStrings = new String[MAX_SAVES];
		saveCurrentFraction = new byte[MAX_SAVES];
		saveCurrentMission = new int[MAX_SAVES];

		for(var5 = 0; var5 < MAX_SAVES; ++var5)
		{
			saveCurrentFraction[var5] = -1;
			saveCurrentMission[var5] = -1;
			byte[] var21 = null;

			try
			{
				var21 = Renderer.getRMSData("save", var5);
			}
			catch(Exception var13)
			{
				;
			}

			if(var21 != null && var21.length != 0)
			{
				byte var7 = (var17 = new DataInputStream(new ByteArrayInputStream(var21))).readByte();
				byte var8 = var17.readByte();
				var17.readByte();
				var17.readByte();
				byte var11 = var17.readByte();
				short var12 = var17.readShort();
				var17.close();
				saveCurrentFraction[var5] = var11;
				saveInfoStrings[var5] = getSaveInfoString(var7, var8, var12);
				saveCurrentMission[var5] = var8;
			}
			else
			{
				saveInfoStrings[var5] = "\n" + PaintableObject.getLocaleString(79) + "\n ";
			}
		}

		setLoadProgress(90);
		var5 = 0;
		String var18;
		if((var18 = Main.main.getAppProperty("ProvisionX-Highscore-gameCode")) != null)
		{
			provisionHighScoreGameCode = var18.trim();
		}

		if((var18 = Main.main.getAppProperty("ProvisionX-Highscore-portalCode")) != null)
		{
			provisionHighScorePortalCode = var18.trim();
		}

		if((var18 = Main.main.getAppProperty("ProvisionX-Highscore-Url")) != null)
		{
			provisionHighScoreURL = var18.trim();
		}

		if((var18 = Main.main.getAppProperty("ms-highscoreUpload")) != null)
		{
			macrospaceHighScoreUpload = Integer.parseInt(var18.trim()) == 1;
		}

		if((var18 = Main.main.getAppProperty("ms-skPos")) != null)
		{
			var5 = Integer.parseInt(var18.trim());
		}

		if((var18 = Main.main.getAppProperty("MIDlet-Version")) != null)
		{
			midletVersion = var18.trim();
		}

		setLoadProgress(96);
		if(var5 == 1)
		{
			KEY_LSK = 2048;
			KEY_RSK = 1024;
		}

		setLoadProgress(100);
		Renderer.startPlayer(0, 0);
		mode = 0;
	}

	public final void setLoadProgress(int var1)
	{
		loadProgress = var1;
		PaintableObject.currentRenderer.repaintAndService();
	}

	public final String getSaveInfoString(int gameMode, int mission, int turn)
	{
		String var4;

		if(gameMode == 0)
		{
			var4 = PaintableObject.getLocaleString(121 + mission);
		}
		else
		{
			var4 = getSkirmishLevelName(mission);
		}

		return PaintableObject.getLocaleString(32 + gameMode) + "\n" + var4 + "\n" + "Current turn: " + (turn + 1);
	}

	public final boolean isActive()
	{
		return activeFlag && PaintableObject.currentRenderer.currentDisplayable == this;
	}

	public final void loadMainGameResources() throws IOException
	{
		sfmLogo = null;
		sfmSplash = null;
		sfmGameLogo = null;
		sfmGlow = null;
		System.gc();
		height_ = super.height - FOOTER_HEIGHT;
		halfHeight_ = height_ >> 1;
		Renderer.stopCurrentPlayer();

		if(mode != 1)
		{
			mode = 1;
			Unit.readUnitData(this);
			Renderer.loadResources();
			sprUnitIcons = new Sprite[8][12];

			byte fraction;
			byte unit;
			SpriteFrame[] var5;

			for(fraction = 0; fraction < 8; ++fraction)
			{
				Sprite unitIcons = new Sprite("unit_icons", fraction);
				int iconCount = unitIcons.getFrameCount() / 12;

				for(unit = 0; unit < 12; ++unit)
				{
					if((fraction & 1) == 1)
					{
						var5 = new SpriteFrame[] { unitIcons.frames[unit] };
						sprUnitIcons[fraction][unit] = new Sprite(var5);
					}
					else
					{
						var5 = new SpriteFrame[iconCount];

						for(int i = 0; i < iconCount; ++i)
						{
							var5[i] = unitIcons.frames[i * 12 + unit];
						}

						sprUnitIcons[fraction][unit] = new Sprite(var5);
					}
				}
			}

			alphaCoveredTiles = new Image[2][sfmTiles.length];

			for(fraction = 0; fraction < 2; ++fraction)
			{
				for(unit = 0; unit < sfmTiles.length; ++unit)
				{
					alphaCoveredTiles[fraction][unit] = Image.createImage(24, 24);
					Graphics var8 = alphaCoveredTiles[fraction][unit].getGraphics();
					sfmTiles[unit].paint(var8, 0, 0);
					sprAlpha.paintFrame(var8, fraction, 0, 0, 0);
				}
			}

			sprPortraits = new Sprite("portraits");
			sprCursor = new Sprite("cursor");
			sprRedSpark = new Sprite("redspark");
			sprSmoke = new Sprite("smoke");
			sprSpark = new Sprite("spark");
			sprStatus = new Sprite("status");
			sprArrowIcons = new Sprite("arrow_icons");
			sprTombStone = new SpriteFrame("tombstone");
			sprLevelUp = new Sprite("levelup");
			sprKingHeadIcons = new Sprite[2];
			sprKingHeadIcons[0] = new Sprite("king_head_icons");
			sprKingHeadIcons[1] = new Sprite("king_head_icons", (byte)0);
			sprCursor.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[0]);
			sprCursorCopy = new Sprite(sprCursor);
			sprCursorCopy.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[3]);
			sprWaterPair = new SpriteFrame[2];
			var_1c69 = WATER_PAIR[0];
			sprWaterPair[0] = sfmTiles[WATER_PAIR[0]];
			sprWaterPair[1] = sfmTiles[WATER_PAIR[1]];
			sprBSmoke = new Sprite("b_smoke");
		}
	}

	public final void keyPressed(int var1, int var2)
	{
//		if(Renderer.devmode /*&& skirmishMode == 0*/ && mode == 1 && gameState == 0)
//		{
//			boolean var3 = false;
//			cheatStringBuffer.append(var2);
//			String var4 = cheatStringBuffer.toString();
//
//			for(int var5 = 0; var5 < CHEAT_KEY_SEQUENCES.length; ++var5)
//			{
//				if(var4.equals(CHEAT_KEY_SEQUENCES[var5]))
//				{
//					if(var5 == 0)
//					{
//						if(currentMission == 7)
//						{
//							var_29de = true;
//						}
//						else
//						{
//							completeMission();
//						}
//					}
//					else if(var5 == 1)
//					{
//						money[currentTurningPlayer] += 1000;
//					}
//
//					statusBarNeedsRepaint = true;
//				}
//				else if(CHEAT_KEY_SEQUENCES[var5].startsWith(var4))
//				{
//					var3 = true;
//				}
//			}
//
//			if(!var3)
//			{
//				cheatStringBuffer = new StringBuffer();
//			}
//		}
	}

	public final void showNotify()
	{
		statusBarNeedsRepaint = true;
		tileIconNeedsRepaint = true;
		combatFooterNeedsRepaint = true;
		combatHeaderNeedsRepaint = true;
	}

	public final byte[] getGameSaveData() throws IOException
	{
		fractionKingPositions[currentTurningPlayer][0] = (byte)cursorMapX;
		fractionKingPositions[currentTurningPlayer][1] = (byte)cursorMapY;
		ByteArrayOutputStream var1 = new ByteArrayOutputStream();
		DataOutputStream var2;
		(var2 = new DataOutputStream(var1)).writeByte(skirmishMode);
		var2.writeByte(currentMission);
		var2.writeByte(turnQueueLength);
		var2.writeByte(currentTurningPlayer);
		var2.writeByte(fractionsTurnQueue[currentTurningPlayer]);
		var2.writeShort(currentTurn);
		var2.writeByte(allowedUnits);
		var2.writeByte(turnQueueLength);

		int var3;
		for(var3 = 0; var3 < turnQueueLength; ++var3)
		{
			var2.writeByte(playerTeams[var3]);
			var2.writeByte(playerModes[var3]);
			var2.writeInt(money[var3]);
			var2.writeByte(fractionKingPositions[var3][0]);
			var2.writeByte(fractionKingPositions[var3][1]);
		}

		var2.writeShort(skirmishUnitCap);

		for(var3 = 0; var3 < buildingData.length; ++var3)
		{
			var2.writeByte(mapData[buildingData[var3][0]][buildingData[var3][1]]);
		}

		var2.writeByte(units.size());
		var3 = 0;

		for(int var4 = units.size(); var3 < var4; ++var3)
		{
			Unit var5 = (Unit)units.elementAt(var3);
			var2.writeByte(var5.type);
			var2.writeByte(var5.fractionPosInTurnQueue);
			var2.writeByte(var5.unitState);
			var2.writeByte(var5.effectState);
			var2.writeByte(var5.health);
			var2.writeByte(var5.rank);
			var2.writeShort(var5.experience);
			var2.writeShort(var5.currentMapPosX);
			var2.writeShort(var5.currentMapPosY);
			var2.writeByte(var5.var_758);
			var2.writeByte(var5.var_79f);
			if(var5.type == 9)
			{
				var2.writeByte(var5.kingVariant);
				var2.writeShort(var5.cost);
				var2.writeByte(fractionKings[var5.fractionPosInTurnQueue] == var5 ? 1 : 0);
			}
		}

		var2.writeShort((short)scriptState);
		var2.writeInt((short)((int)var_3cee));
		var2.writeInt(var_3d08);
		var2.writeByte(var_3d48 ? 0 : 1);
		byte[] var6 = var1.toByteArray();
		var2.close();
		return var6;
	}

	public final void loadGameSaveData(byte[] var1) throws IOException
	{
		DataInputStream var2 = new DataInputStream(new ByteArrayInputStream(var1));
		skirmishMode = var2.readByte();
		currentMission = var2.readByte();
		turnQueueLength = var2.readByte();
		loadLevel(currentMission);
		currentTurningPlayer = var2.readByte();
		var2.readByte();
		currentTurn = var2.readShort();
		allowedUnits = var2.readByte();
		turnQueueLength = var2.readByte();

		int var3;
		for(var3 = 0; var3 < turnQueueLength; ++var3)
		{
			playerTeams[var3] = var2.readByte();
			playerModes[var3] = var2.readByte();
			money[var3] = var2.readInt();
			fractionKingPositions[var3][0] = var2.readByte();
			fractionKingPositions[var3][1] = var2.readByte();
		}

		skirmishUnitCap = var2.readShort();

		for(var3 = 0; var3 < buildingData.length; ++var3)
		{
			mapData[buildingData[var3][0]][buildingData[var3][1]] = var2.readByte();
		}

		fractionsAllKings = new Unit[turnQueueLength][4];
		fractionsKingCount = new int[turnQueueLength];
		sub_58e();
		byte var21 = var2.readByte();
		int var4 = 0;

		for(byte var5 = var21; var4 < var5; ++var4)
		{
			byte var6 = var2.readByte();
			byte var7 = var2.readByte();
			byte var8 = var2.readByte();
			byte var9 = var2.readByte();
			byte var10 = var2.readByte();
			byte var11 = var2.readByte();
			short var12 = var2.readShort();
			short var13 = var2.readShort();
			short var14 = var2.readShort();
			byte var15 = var2.readByte();
			byte var16 = var2.readByte();
			Unit var17;
			(var17 = Unit.createUnit(var6, var7, var13, var14)).unitState = var8;
			var17.experience = var12;
			var17.setRank(var11);
			var17.effectState = var9;
			var17.updateEffects();
			var17.health = (short)var10;
			var17.var_758 = var15;
			var17.var_79f = var16;
			if(var6 == 9)
			{
				byte var18 = var2.readByte();
				short var19 = var2.readShort();
				var17.setKingVariant(var18);
				var17.cost = var19;
				if(var2.readByte() == 1)
				{
					fractionKings[var17.fractionPosInTurnQueue] = var17;
				}
			}
		}

		scriptState = var2.readShort();
		var_3cee = (long)var2.readInt();
		var_3d08 = var2.readInt();
		var_3d48 = var2.readByte() != 0;
		var2.close();
		if(currentMission == 6 && scriptState > 32)
		{
			auxObjectivesMessage = createMessageScreen(PaintableObject.getLocaleString(121 + currentMission), PaintableObject.getLocaleString(138), height_, -1);
			auxObjectivesMessage.setDrawSoftButtonFlag((byte)0, true);
			auxObjectivesMessage.setNextDisplayable((PaintableObject)null);
		}

		moveCursor(fractionKingPositions[currentTurningPlayer][0], fractionKingPositions[currentTurningPlayer][1]);
		moveMapShowPoint(fractionKingPositions[currentTurningPlayer][0], fractionKingPositions[currentTurningPlayer][1]);
		Renderer.startPlayer_(FRACTION_BACKGROUND_MUSIC[fractionsTurnQueue[currentTurningPlayer]], 0);
	}

	public final void sub_180(int var1, PaintableObject var2)
	{
		try
		{
			Renderer.setRMSData("save", var1, getGameSaveData());
			saveInfoStrings[var1] = getSaveInfoString(skirmishMode, currentMission, currentTurn);
			saveCurrentFraction[var1] = fractionsTurnQueue[currentTurningPlayer];
			saveCurrentMission[var1] = currentMission;
			var_2b46.initMsgAuxDisplayable((String)null, saveInfoStrings[var1], width_, -1);
			var_2b46.gradientColor = FRACTION_COLORS[saveCurrentFraction[var1]];
			var_2b46.showNotify();
			AuxDisplayable var3;
			(var3 = createMessageScreen((String)null, PaintableObject.getLocaleString(77), height_, 1000)).nextDisplayable = var2;
			PaintableObject.currentRenderer.setCurrentDisplayable(var3);
		}
		catch(Exception var4)
		{
			;
		}
	}

	public static final void loadMainSettings()
	{
		try
		{
			byte[] var0 = Renderer.getRMSData("settings", 0);

			for(int var1 = 0; var1 < 4; ++var1)
			{
				Renderer.mainSettings[var1] = (var0[0] & 1 << var1) != 0;
			}
		}
		catch(Exception var2)
		{
			return;
		}

	}

	public final void saveMainSettings()
	{
		try
		{
			byte[] var1 = new byte[1];

			for(int var2 = 0; var2 < 4; ++var2)
			{
				if(Renderer.mainSettings[var2])
				{
					var1[0] = (byte)(var1[0] | 1 << var2);
				}
			}

			Renderer.setRMSData("settings", 0, var1);
		}
		catch(Exception var3)
		{
			;
		}
	}

	public final void sub_26e(Unit var1)
	{
		var_2985 = 0;
		moveCursor(var1.currentMapPosX, var1.currentMapPosY);
		heavenFuryTarget = var1;
	}

	public final void startAttack(Unit var1, Unit var2)
	{
		if(var2.unitState == 4)
		{
			var_16e5 = var2;
			createSimpleSparkSprite(sprRedSpark, var_16e5.currentX, var_16e5.currentY, 0, 0, 1, 50);
			var_17d5 = 6;
			currentSelectedUnit.finishMove();
			gameState = 0;
			clearAttackData();
			sprCursor.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[0]);
			if(playerModes[currentTurningPlayer] == 0)
			{
				var_38a4 = delayCounter;
				var_36e4 = 6;
				return;
			}
		}
		else
		{
			if(Renderer.mainSettings[3] && var2.characterData.length > 0)
			{
				var_19c3 = true;
				combatDrapValue = 0;
				Renderer.stopCurrentPlayer();
			}
			else
			{
				gameState = 13;
				var_1a98 = 0;
				sprCursor.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[0]);
			}

			currentAttackUnit = var1;
			currentAttackVictimUnit = var2;
		}

	}

	public final void finishAttack()
	{
		clearAttackData();

		if(currentAttackUnit.health <= 0)
		{
			lastDeadUnit = currentAttackUnit;
		}
		else if(currentAttackUnit.promote())
		{
			var_26d9.addElement(currentAttackUnit);
		}

		if(currentAttackVictimUnit.health <= 0)
		{
			lastDeadUnit = currentAttackVictimUnit;
		}
		else
		{
			if(currentAttackUnit.hasProperty((short)128))
			{
				createSimpleSparkSprite(sprSpark, currentAttackVictimUnit.currentX, currentAttackVictimUnit.currentY, 0, 0, 1, 50);
				Sprite var1;
				(var1 = Sprite.createSimpleSparkSprite(sprStatus, 0, 0, -4, -1, 800, (byte)5)).setPosition(currentAttackVictimUnit.currentX + (currentAttackVictimUnit.width - var1.width) / 2, currentAttackVictimUnit.currentY - var1.height);
				var1.setExternalFrameSequence(var_6d7);
				activeEffects.addElement(var1);
				currentAttackVictimUnit.addEffect((byte)1);
			}

			if(currentAttackVictimUnit.promote())
			{
				var_26d9.addElement(currentAttackVictimUnit);
			}
		}

		if(lastDeadUnit != null)
		{
			moveCursor(lastDeadUnit.currentMapPosX, lastDeadUnit.currentMapPosY);
			createSimpleSparkSprite(sprSpark, lastDeadUnit.currentX, lastDeadUnit.currentY, 0, 0, 1, 50);
			Renderer.startPlayer(12, 1);
		}

		var_1728 = delayCounter;
		if(playerModes[currentTurningPlayer] == 0)
		{
			var_38a4 = delayCounter;
			var_36e4 = 6;
		}

		sprCursor.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[0]);
		gameState = 0;
		currentAttackUnit.finishMove();
		currentAttackVictimUnit = null;
		currentAttackUnit = null;
	}

	public final Sprite createSimpleSparkSprite(Sprite baseSprite, int cx, int cy, int sx, int sy, int bounceMode, int delay)
	{
		Sprite var8 = Sprite.createSimpleSparkSprite(baseSprite, sx, sy, 0, bounceMode, delay, (byte)0);
		var8.setPosition(cx, cy);
		newEffects.addElement(var8);
		return var8;
	}

	public final void prepareUnitMovement(Unit var1)
	{
		var_1824 = true;
		var_17f7 = !var_202c;
		alphaWindowSize = 12;
		gameState = 1;
		cursorPositionChanged = true;
		setArrayValuesEx(mapAlphaData, 0);
		var1.fillMoveRangeData(mapAlphaData);
		paintMapAlphaDataFlag = true;
		showAtackRange = false;
		sprCursor.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[2]);
	}

	public final void createCircleMenu(byte[] var1, int var2, int var3, PaintableObject var4)
	{
		AuxDisplayable auxdisp = new AuxDisplayable((byte)0, 0);

		var_21e9 = var2;
		var_223e = var3;

		int var6 = var1.length;
		Vector var7 = new Vector(var6);
		Vector var8 = new Vector(var6);

		for(int var9 = 0; var9 < var6; ++var9)
		{
			byte var10 = var1[var9];
			if(macrospaceHighScoreUpload || var10 != 6)
			{
				var7.addElement(MENU_ITEMS_TEXT[var10]);
				var8.addElement(sfmMenuIcons[var10]);
			}
		}

		String[] var12 = new String[var7.size()];
		SpriteFrame[] var11 = new SpriteFrame[var8.size()];

		var7.copyInto(var12);
		var8.copyInto(var11);

		auxdisp.initCircleMenuAuxDisplayable(var12, var11, super.halfWidth, var_21e9, var_223e, 3, (byte)1);
		auxdisp.setNextDisplayable(var4);

		PaintableObject.currentRenderer.setCurrentDisplayable(auxdisp);
	}

	public final void createInGameMiniMenu(byte[] var1, Unit var2)
	{
		auxInGameMenu = new AuxDisplayable((byte)0, 0);
		int var3;
		String[] var4 = new String[var3=var1.length];
		SpriteFrame[] var5 = new SpriteFrame[var3];

		for(int var6 = 0; var6 < var1.length; ++var6)
		{
			var4[var6] = INGAME_ACTIONS_TEXT[var1[var6]];
			var5[var6] = sprActionIcons[var1[var6]];
		}

		if(cursorMapY * 24 <= height_ / 2 - 24)
		{
			auxInGameMenu.initMiniListAuxDisplayable(var4, var5, 0, height_ - sprButtons.height, 36);
		}
		else
		{
			auxInGameMenu.initMiniListAuxDisplayable(var4, var5, width_, 0, 8);
		}

		auxInGameMenu.setNextDisplayable(this);
		PaintableObject.currentRenderer.setCurrentDisplayable(auxInGameMenu);
	}

	public final AuxDisplayable sub_375(String seltext, SpriteFrame icon)
	{
		String[] var3 = new String[MAX_SAVES];

		for(int var4 = 0; var4 < MAX_SAVES; ++var4)
		{
			var3[var4] = "SLOT " + (var4 + 1) + "/" + MAX_SAVES;
		}

		var_2b0e = new AuxDisplayable((byte)14, 0);
		var_2b0e.initHorizontalListAuxDisplayable(var3, width_, -1);
		var_2b46 = new AuxDisplayable((byte)10, 0);
		var_2b46.initMsgAuxDisplayable((String)null, saveInfoStrings[0], width_, -1);

		if(saveCurrentFraction[0] != -1)
		{
			var_2b46.gradientColor = FRACTION_COLORS[saveCurrentFraction[0]];
		}

		AuxDisplayable var7 = new AuxDisplayable((byte)15, 15);
		int var5 = (height_ - var_2b0e.currentHeight - var_2b46.currentHeight) / 2;
		AuxDisplayable var6;
		(var6 = new AuxDisplayable((byte)10, 0)).initMsgAuxDisplayable((String)null, seltext, width_, -1);
		var6.titleIcon = icon;
		var7.attachAuxDisplayable(var6, 0, 0, 0);
		var5 += var6.currentHeight / 2;
		var7.attachAuxDisplayable(var_2b0e, 0, var5, 0);
		var5 += var_2b0e.currentHeight;
		var7.attachAuxDisplayable(var_2b46, 0, var5, 20);
		var7.passUpdatesToAllAttachedAuxDisplayables = true;
		var7.setDrawSoftButtonFlag((byte)0, true);

		return var7;
	}

	public final void createDownloadableSkirmishLevelsListAuxDisplayable(PaintableObject var1)
	{
		int var2;
		String[] var3 = new String[var2=var_4289.length];
		int[] var4 = new int[var2];
		int var5 = 0;
		int var6 = 0;

		while(var6 < var2)
		{
			boolean var7 = false;
			int var8 = 0;

			while(true)
			{
				if(var8 < downloadedSkirmishLevelCount)
				{
					if(!var_4289[var6].equals(downloadedSkirmishLevelNames[var8]))
					{
						++var8;
						continue;
					}

					var7 = true;
				}

				if(!var7)
				{
					var3[var5] = var_4289[var6];
					var4[var5] = var6;
					++var5;
				}

				++var6;
				break;
			}
		}

		auxDownloadableSkirmishLevelsContainer = new AuxDisplayable((byte)15, 15);
		AuxDisplayable var13;
		(var13 = new AuxDisplayable((byte)10, 0)).initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(48), width_, -1);
		if(var5 == 0)
		{
			AuxDisplayable var14;
			(var14 = new AuxDisplayable((byte)10, 0)).initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(52), width_, halfHeight_);
			auxDownloadableSkirmishLevelsContainer.attachAuxDisplayable(var14, 0, (height_ + var13.currentHeight) / 2, 6);
		}
		else
		{
			String[] var12 = new String[var5];
			int[] var11 = new int[var5];
			System.arraycopy(var3, 0, var12, 0, var5);
			System.arraycopy(var4, 0, var11, 0, var5);
			var_2e87 = new AuxDisplayable((byte)10, 0);
			String var9 = formatFileSizeString(availableRMSDownloadSize);
			var_2e87.initMsgAuxDisplayable((String)null, PaintableObject.getReplacedLocaleString(54, formatFileSizeString(var_42c2[var11[0]])) + "\n" + PaintableObject.getReplacedLocaleString(53, var9), width_, -1);
			var_2e5d = new AuxDisplayable((byte)11, 0);
			var_2e5d.initVerticalListAuxDisplayable(var12, halfWidth_, halfHeight_, width_, height_ - var13.currentHeight - var_2e87.currentHeight, 3, 4);
			var_2e5d.var_c25 = var11;
			int var10 = (height_ - var_2e87.currentHeight - var_2e5d.currentHeight + var13.currentHeight) / 2;
			auxDownloadableSkirmishLevelsContainer.attachAuxDisplayable(var_2e5d, 0, var10, 20);
			var10 += var_2e5d.currentHeight;
			auxDownloadableSkirmishLevelsContainer.attachAuxDisplayable(var_2e87, 0, var10, 20);
			auxDownloadableSkirmishLevelsContainer.passUpdatesToAllAttachedAuxDisplayables = true;
			auxDownloadableSkirmishLevelsContainer.setDrawSoftButtonFlag((byte)0, true);
		}

		auxDownloadableSkirmishLevelsContainer.attachAuxDisplayable(var13, 0, 0, 20);
		auxDownloadableSkirmishLevelsContainer.setNextDisplayable(var1);
	}

	public final AuxDisplayable sub_3e5(PaintableObject var1)
	{
		AuxDisplayable var2;
		if(downloadedSkirmishLevelNames.length == 0)
		{
			AuxDisplayable var3;
			(var3 = (var2 = new AuxDisplayable((byte)10, 0)).createTitleAuxDisplayable(PaintableObject.getLocaleString(49))).titleIcon = sfmMenuIcons[6];
			var2.initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(52), super.width, -1);
			var2.setPosition(0, (height_ + var3.currentHeight) / 2, 6);
			var2.setNextDisplayable(var1);
			var_2e13 = null;
			return var2;
		}
		else
		{
			var_2e13 = new AuxDisplayable((byte)11, 0);
			(var2 = var_2e13.createTitleAuxDisplayable(PaintableObject.getLocaleString(49))).titleIcon = sfmMenuIcons[6];
			var_2e13.initVerticalListAuxDisplayable(downloadedSkirmishLevelNames, super.width / 2, (height_ + var2.currentHeight) / 2, width_, height_ - var2.currentHeight, 3, 4);
			var_2e13.setNextDisplayable(var1);
			return var_2e13;
		}
	}

	public final int getSkirmishLevelNumber(int var1)
	{
		return var1 >= skirmishLevelsNames.length ? downloadedSkirmishLevelNumbers[var1 - skirmishLevelsNames.length] + skirmishLevelsNames.length : var1;
	}

	public final DataInputStream getSkirmishLevelAsStream(int var1) throws IOException
	{
		if(var1 >= skirmishLevelsNames.length)
		{
			int var2 = var1 - skirmishLevelsNames.length;
			return new DataInputStream(new ByteArrayInputStream(Renderer.getRMSData("download", var2)));
		}
		else
		{
			return new DataInputStream(Renderer.getResourceAsStream("s" + var1 + ".aem"));
		}
	}
	
	public final byte[] getSkirmishLevel(int var1)
	{
		if(var1 >= skirmishLevelsNames.length)
		{
			int var2 = var1 - skirmishLevelsNames.length;
			return Renderer.getRMSData("download", var2);
		}
		else
		{
			try
			{
				InputStream is = Renderer.getResourceAsStream("s" + var1);
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
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
	}

	public final String getSkirmishLevelName(int var1)
	{
		if(var1 >= skirmishLevelsNames.length)
		{
			int var2 = var1 - skirmishLevelsNames.length;

			for(int var3 = 0; var3 < downloadedSkirmishLevelNumbers.length; ++var3)
			{
				if(downloadedSkirmishLevelNumbers[var3] == var2)
				{
					return downloadedSkirmishLevelNames[var3];
				}
			}

			return null;
		}
		else
		{
			return skirmishLevelsNames[var1];
		}
	}

	public final String formatFileSizeString(int var1)
	{
		int var2 = var1 * 100 / 1024;
		int var3 = var2 / 100;
		int var4 = var2 % 100;

		return var3 + "." + var4;
	}

	public final String formatFolderTitleString(String path, String root)
	{
		if(path != null)
		{
			if(path.endsWith("/"))
			{
				path = path.substring(0, path.length() - 1);
			}

			int index = path.lastIndexOf('/');

			if(index >= 0)
			{
				return path.substring(index + 1);
			}
			else
			{
				return path;
			}
		}
		else
		{
			return root;
		}
	}

	public final void menuStateChanged(AuxDisplayable auxdisp, int selindex, String seltext, byte action)
	{
		try
		{
			var_4482 = true;
			if(auxdisp == auxInGameMenu && action == 1)
			{
				if(gameState == 3)
				{
					currentSelectedUnit.setMapPosition(selectedUnitPrevMapX, selectedUnitPrevMapY);
					currentSelectedUnit.fillMoveRangeData(mapAlphaData);
					prepareUnitMovement(currentSelectedUnit);
					cursorPositionChanged = true;
				}
	
			}
			else if(auxdisp == auxExitGameQuestion)
			{
				if(action == 0)
				{
					PaintableObject.currentRenderer.stop();
				}
				else
				{
					auxExitGameQuestion = null;
				}
			}
			else if(auxdisp == auxNewGameQuestion)
			{
				if(action == 0)
				{
					createCircleMenu(PLAY_MENU_ITEMS, var_21e9, var_223e, auxdisp.nextDisplayable);
				}
	
				auxNewGameQuestion = null;
			}
			else if(auxdisp == auxImportLevelsContainer)
			{
				if(action == 0)
				{
					String file = currentFileList[auxImportLevelsList.menuSelected];
					
					if(file.endsWith(FSO_MAP_SUFFIX))
					{
						FileConnection fc = fso.openFileConnection(file);
						InputStream is = fc.openInputStream();

						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						int b;

						while((b = is.read()) >= 0)
						{
							baos.write(b);
						}

						is.close();
						fc.close();

						file = file.substring(0, file.length() - FSO_MAP_SUFFIX.length());
						addDownloadedSkirmishLevel(file, baos.toByteArray());

						baos.close();

						AuxDisplayable auxMsg = createMessageScreen((String)null, PaintableObject.getReplacedLocaleString(45, file), height_, 2000);
						auxMsg.setNextDisplayable(auxImportLevelsContainer);
						PaintableObject.currentRenderer.setCurrentDisplayable(auxMsg);
					}
					else
					{
						fso.changeDir(file);
						
						currentFileList = FileSystemObject.enumerationToArray(FileSystemObject.sortFileList(fso.list(FSO_MAP_SUFFIX, true)), fso.getCurrentPath() != null ? FSO_PARENT_DIR : null);

						auxImportLevelsContainer = new AuxDisplayable((byte)15, 15);

						AuxDisplayable auxTTL = auxImportLevelsContainer.createTitleAuxDisplayable(formatFolderTitleString(fso.getCurrentPath(), PaintableObject.getLocaleString(290)));
						auxTTL.titleIcon = sfmMenuIcons[6];

						auxImportLevelsList = new AuxDisplayable((byte)0, 0);
						auxImportLevelsList.initVerticalListAuxDisplayable(currentFileList, 0, 0, width_, height_ - auxTTL.currentHeight - sprButtons.height * 2, 3, 4);

						if(fso.getCurrentPath() != null && currentFileList.length > 1)
						{
							auxImportLevelsList.menuSelected = 1;
						}

						auxImportLevelsContainer.attachAuxDisplayable(auxImportLevelsList, halfWidth_, (height_ + auxTTL.currentHeight) / 2, 3);

						auxImportLevelsContainer.setDrawSoftButtonFlag((byte)0, true);
						auxImportLevelsContainer.passUpdatesToAllAttachedAuxDisplayables = true;
						auxImportLevelsContainer.setNextDisplayable(auxDownloadLevelsMenuContainer);

						PaintableObject.currentRenderer.setCurrentDisplayable(auxImportLevelsContainer);
					}
				}
				else
				{
					auxImportLevelsContainer = null;
					auxImportLevelsList = null;
					currentFileList = null;
				}
			}
			else if(auxdisp == auxExportLevelsContainer)
			{
				if(action == 0)
				{
					String file;

					if(auxExportLevelsList.menuSelected == 0)
					{
						file = currentMapName + FSO_MAP_SUFFIX;
					}
					else
					{
						file = currentFileList[auxExportLevelsList.menuSelected];
					}

					if(file.endsWith(FSO_MAP_SUFFIX))
					{
						FileConnection fc = fso.openFileConnection(file);

						if(fc.exists())
						{
							fc.truncate(0);
						}
						else
						{
							fc.create();
						}

						OutputStream os = fc.openOutputStream();

						os.write(getSkirmishLevel(currentSkirmishLevelNumber));

						os.close();
						fc.close();

						currentFileList = FileSystemObject.enumerationToArray(FileSystemObject.sortFileList(fso.list(FSO_MAP_SUFFIX, true)), fso.getCurrentPath() != null ? FSO_PARENT_DIR_SAVE : null);

						auxExportLevelsContainer = new AuxDisplayable((byte)15, 15);

						AuxDisplayable auxTTL = auxExportLevelsContainer.createTitleAuxDisplayable(formatFolderTitleString(fso.getCurrentPath(), PaintableObject.getLocaleString(299)));
						auxTTL.titleIcon = sfmMenuIcons[6];

						auxExportLevelsList = new AuxDisplayable((byte)0, 0);
						auxExportLevelsList.initVerticalListAuxDisplayable(currentFileList, 0, 0, width_, height_ - auxTTL.currentHeight - sprButtons.height * 2, 3, 4);

						if(fso.getCurrentPath() != null && currentFileList.length > 2)
						{
							auxExportLevelsList.menuSelected = 2;
						}

						auxExportLevelsContainer.attachAuxDisplayable(auxExportLevelsList, halfWidth_, (height_ + auxTTL.currentHeight) / 2, 3);

						auxExportLevelsContainer.setDrawSoftButtonFlag((byte)0, true);
						auxExportLevelsContainer.passUpdatesToAllAttachedAuxDisplayables = true;
						auxExportLevelsContainer.setNextDisplayable(auxExportableLevelListContainer);

						AuxDisplayable auxMsg = createMessageScreen((String)null, PaintableObject.getReplacedLocaleString(45, currentMapName), height_, 2000);
						auxMsg.setNextDisplayable(auxExportLevelsContainer);
						PaintableObject.currentRenderer.setCurrentDisplayable(auxMsg);
					}
					else
					{
						fso.changeDir(file);

						currentFileList = FileSystemObject.enumerationToArray(FileSystemObject.sortFileList(fso.list(FSO_MAP_SUFFIX, true)), fso.getCurrentPath() != null ? FSO_PARENT_DIR_SAVE : null);

						auxExportLevelsContainer = new AuxDisplayable((byte)15, 15);

						AuxDisplayable auxTTL = auxExportLevelsContainer.createTitleAuxDisplayable(formatFolderTitleString(fso.getCurrentPath(), PaintableObject.getLocaleString(290)));
						auxTTL.titleIcon = sfmMenuIcons[6];

						auxExportLevelsList = new AuxDisplayable((byte)0, 0);
						auxExportLevelsList.initVerticalListAuxDisplayable(currentFileList, 0, 0, width_, height_ - auxTTL.currentHeight - sprButtons.height * 2, 3, 4);

						if(fso.getCurrentPath() != null && currentFileList.length > 2)
						{
							auxExportLevelsList.menuSelected = 2;
						}

						auxExportLevelsContainer.attachAuxDisplayable(auxExportLevelsList, halfWidth_, (height_ + auxTTL.currentHeight) / 2, 3);

						auxExportLevelsContainer.setDrawSoftButtonFlag((byte)0, true);
						auxExportLevelsContainer.passUpdatesToAllAttachedAuxDisplayables = true;
						auxExportLevelsContainer.setNextDisplayable(auxExportableLevelListContainer);

						PaintableObject.currentRenderer.setCurrentDisplayable(auxExportLevelsContainer);
					}
				}
				else
				{
					auxExportLevelsContainer = null;
					auxExportLevelsList = null;
					currentFileList = null;
				}
			}
			else
			{
				int var5;
				if(auxdisp == auxSaveOverwrireQuestion)
				{
					if(action == 0)
					{
						var5 = var_2b0e.menuSelected;
						sub_180(var5, auxSaveGameContainer);
					}
	
					auxSaveOverwrireQuestion = null;
				}
				else if(auxdisp == auxBuyUnitSelector)
				{
					if(action == 2 || action == 3)
					{
						auxBuyUnitDescription.initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(184 + auxBuyUnitSelector.availableUnits[selindex].type), width_, height_ - auxBuyUnitInfo.currentHeight - auxBuyUnitSelector.currentHeight);
						auxBuyUnitInfo.selectedUnit = auxBuyUnitSelector.availableUnits[selindex];
						auxBuyUnitDescription.showNotify();
						auxBuyUnitInfo.showNotify();
					}
	
				}
				else if(auxdisp == auxUnitBuyContainer)
				{
					if(action == 0)
					{
						Unit var22 = auxBuyUnitSelector.availableUnits[auxBuyUnitSelector.menuSelected];
						if(!canBuyUnit(var22, cursorMapX, cursorMapY))
						{
							return;
						}
	
						currentSelectedUnit = buyUnit(var22, cursorMapX, cursorMapY);
						var_202c = true;
						prepareUnitMovement(currentSelectedUnit);
						PaintableObject.currentRenderer.setCurrentDisplayable(this);
					}
	
					auxUnitBuyContainer = null;
					auxBuyUnitSelector = null;
					auxBuyUnitDescription = null;
					auxBuyUnitInfo = null;
				}
				else
				{
					String var18;
					if(auxdisp == auxInstructionsPartSelector)
					{
						if(action == 2 || action == 3)
						{
							var18 = PaintableObject.getLocaleStringEx(INSTRUCTION_TEXT_MESSAGES[selindex], true);

							auxInstructionsTextMessage.initMsgAuxDisplayable((String)null, var18, width_, auxInstructionsTextMessage.currentHeight);
							auxInstructionsTextMessage.showNotify();
						}
	
					}
					else if(auxdisp == auxInstructionsContainer)
					{
						auxInstructionsContainer = null;
						auxInstructionsPartSelector = null;
						auxInstructionsTextMessage = null;
					}
					else if(auxdisp == auxObjectivesMessage)
					{
						if(action == 0)
						{
							PaintableObject.currentRenderer.setCurrentDisplayable(this);
						}
	
					}
					else
					{
						int var7;
						int var11;
						int var13;
						AuxDisplayable var19;
						int var28;
						String[] var31;
						AuxDisplayable var32;
						if(auxdisp == auxSkirmishMapPreviewContainer)
						{
							if(action != 0)
							{
								auxSkirmishMapPreviewContainer = null;
							}
							else
							{
								auxSkirmishTeamSetupContainer = new AuxDisplayable((byte)15, 15);
								(var32 = new AuxDisplayable((byte)10, 0)).titleIcon = sfmMenuIcons[4];
								var32.initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(34), width_, -1);
								auxSkirmishTeamSetupContainer.attachAuxDisplayable(var32, 0, 0, 20);
								var31 = new String[skirmishLevelPlayerCount];
	
								for(var7 = 0; var7 < skirmishLevelPlayerCount; ++var7)
								{
									var31[var7] = PaintableObject.getReplacedLocaleString(38, "" + (var7 + 1));
								}
	
								var7 = var32.currentHeight;
								AuxDisplayable[] var17 = new AuxDisplayable[skirmishLevelPlayerCount];
								auxSkirmishPlayerModeSelector = new AuxDisplayable[skirmishLevelPlayerCount];
								auxSkirmishPlayerTeamSelector = new AuxDisplayable[skirmishLevelPlayerCount];
	
								for(var28 = 0; var28 < skirmishLevelPlayerCount; ++var28)
								{
									auxSkirmishPlayerModeSelector[var28] = new AuxDisplayable((byte)14, 6);
									auxSkirmishPlayerModeSelector[var28].initHorizontalListAuxDisplayable(MENU_PLAYER_MODE_TEXT, super.halfWidth, -1);
									auxSkirmishPlayerTeamSelector[var28] = new AuxDisplayable((byte)14, 5);
									auxSkirmishPlayerTeamSelector[var28].initHorizontalListAuxDisplayable(var31, super.halfWidth, -1);
									auxSkirmishPlayerTeamSelector[var28].menuSelected = var28;
									int var34 = Math.max(auxSkirmishPlayerModeSelector[var28].currentWidth, auxSkirmishPlayerTeamSelector[var28].currentWidth);
									var11 = super.width - var34;
									auxSkirmishPlayerModeSelector[var28].currentWidth = var34;
									auxSkirmishPlayerTeamSelector[var28].currentWidth = var34;
									auxSkirmishTeamSetupContainer.attachAuxDisplayable(auxSkirmishPlayerModeSelector[var28], var11, var7, 20);
									var7 += auxSkirmishPlayerModeSelector[var28].currentHeight;
									auxSkirmishTeamSetupContainer.attachAuxDisplayable(auxSkirmishPlayerTeamSelector[var28], var11, var7, 20);
									var7 += auxSkirmishPlayerTeamSelector[var28].currentHeight;
									var17[var28] = new AuxDisplayable((byte)10, 8);
									var17[var28].initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(skirmishLevelFractions[var28] - 1 + 89), var11, auxSkirmishPlayerModeSelector[var28].currentHeight + auxSkirmishPlayerTeamSelector[var28].currentHeight);
									var17[var28].gradientColor = FRACTION_COLORS[skirmishLevelFractions[var28]];
									auxSkirmishTeamSetupContainer.attachAuxDisplayable(var17[var28], 0, var7, 36);
								}
	
								auxSkirmishTeamSetupContainer.currentAttachedAuxDisplayable = 1;
								AuxDisplayable var30;
								(var30 = new AuxDisplayable((byte)10, 8)).initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(40), halfWidth_, -1);
								auxSkirmishTeamSetupContainer.attachAuxDisplayable(var30, 0, var7, 20);
								auxSkirmishStartMoneySelector = new AuxDisplayable((byte)14, 4);
								String[] var25 = new String[SKIRMISH_START_MONEY.length];
	
								for(var11 = 0; var11 < var25.length; ++var11)
								{
									var25[var11] = "" + SKIRMISH_START_MONEY[var11];
								}
	
								auxSkirmishStartMoneySelector.initHorizontalListAuxDisplayable(var25, halfWidth_, var30.currentHeight);
								auxSkirmishTeamSetupContainer.attachAuxDisplayable(auxSkirmishStartMoneySelector, super.halfWidth, var7, 20);
								var7 += var30.currentHeight;
								(var19 = new AuxDisplayable((byte)10, 8)).initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(41), halfWidth_, -1);
								auxSkirmishTeamSetupContainer.attachAuxDisplayable(var19, 0, var7, 20);
								auxSkirmishUnitCapSelector = new AuxDisplayable((byte)14, 4);
								String[] var24 = new String[SKIRMISH_UNIT_CAPS.length];
	
								for(var13 = 0; var13 < var24.length; ++var13)
								{
									var24[var13] = "" + SKIRMISH_UNIT_CAPS[var13];
								}
	
								auxSkirmishUnitCapSelector.initHorizontalListAuxDisplayable(var24, halfWidth_, var19.currentHeight);
								auxSkirmishTeamSetupContainer.attachAuxDisplayable(auxSkirmishUnitCapSelector, super.halfWidth, var7, 20);
								auxSkirmishTeamSetupContainer.setNextDisplayable(auxdisp);
								auxSkirmishTeamSetupContainer.setDrawSoftButtonFlag((byte)0, true);
								PaintableObject.currentRenderer.setCurrentDisplayable(auxSkirmishTeamSetupContainer);
							}
						}
						else
						{
							int var8;
							int var37;
							if(auxdisp == auxSkirmishTeamSetupContainer)
							{
								if(action == 0)
								{
									var5 = 0;
									var37 = 0;
									boolean[] var38 = new boolean[skirmishLevelPlayerCount];
	
									for(var8 = 0; var8 < skirmishLevelPlayerCount; ++var8)
									{
										if(auxSkirmishPlayerModeSelector[var8].menuSelected == 2)
										{
											playerModes[var8] = 2;
										}
										else
										{
											++var5;
											if(auxSkirmishPlayerModeSelector[var8].menuSelected == 0)
											{
												playerModes[var8] = 1;
											}
											else if(auxSkirmishPlayerModeSelector[var8].menuSelected == 1)
											{
												playerModes[var8] = 0;
											}
	
											playerTeams[var8] = (byte)auxSkirmishPlayerTeamSelector[var8].menuSelected;
											if(!var38[playerTeams[var8]])
											{
												++var37;
												var38[playerTeams[var8]] = true;
											}
										}
									}
	
									if(var5 < 2 || var37 < 2)
									{
										AuxDisplayable var15;
										(var15 = createMessageScreen((String)null, PaintableObject.getLocaleString(39), height_, 2000)).setNextDisplayable(auxSkirmishTeamSetupContainer);
										PaintableObject.currentRenderer.setCurrentDisplayable(var15);
										return;
									}
	
									auxSkirmishTeamSetupContainer = null;
									auxSkirmishMapPreviewContainer = null;
									PaintableObject.currentRenderer.setCurrentDisplayable(this);
									skirmishStartMoney = SKIRMISH_START_MONEY[auxSkirmishStartMoneySelector.menuSelected];
									skirmishUnitCap = SKIRMISH_UNIT_CAPS[auxSkirmishUnitCapSelector.menuSelected];
									auxSkirmishPlayerModeSelector = null;
									auxSkirmishStartMoneySelector = null;
									auxSkirmishUnitCapSelector = null;

									disableLevelEditor();

									skirmishMode = 1;
									allowedUnits = 8;
									paintLoadingStringFlag = true;
									PaintableObject.currentRenderer.repaintAndService();
									loadMainGameResources();
									loadLevel(currentSkirmishLevelNumber);
									currentMission = currentSkirmishLevelNumber;
									paintLoadingStringFlag = false;
									gameState = 0;
								}
	
							}
							else
							{
								boolean var23;
								if(auxdisp == auxSkirmishLevelListContainer)
								{
									var5 = auxSkirmishLevelList.menuSelected;

									if(action == 0 && (var5 >= skirmishLevelsNames.length || !skirmishLevelLockFlags[var5]))
									{
										currentSkirmishLevelNumber = getSkirmishLevelNumber(var5);
										DataInputStream var6;
										var7 = (var6 = getSkirmishLevelAsStream(currentSkirmishLevelNumber)).readInt();
										var8 = var6.readInt();
										byte[][] var9 = new byte[var7][var8];
										skirmishLevelFractions = new byte[4];
										byte[] var10 = new byte[5];
	
										for(var11 = 0; var11 < 5; ++var11)
										{
											var10[var11] = -1;
										}
	
										skirmishLevelPlayerCount = 0;
	
										for(var11 = 0; var11 < var7; ++var11)
										{
											for(int var12 = 0; var12 < var8; ++var12)
											{
												var9[var11][var12] = var6.readByte();
												if(tilesTypes[var9[var11][var12]] == 9 && (var13 = getBuildingFractionEx(var11, var12, var9)) != 0 && var10[var13] == -1)
												{
													skirmishLevelFractions[skirmishLevelPlayerCount] = (byte)var13;
													var10[var13] = skirmishLevelPlayerCount++;
												}
											}
										}
	
										var6.close();
										currentMapName = auxSkirmishLevelList.msgText[var5];
										auxSkirmishMapPreviewContainer = new AuxDisplayable((byte)15, 15);
										(var19 = new AuxDisplayable((byte)10, 0)).titleIcon = sfmMenuIcons[4];
										var19.initMsgAuxDisplayable((String)null, currentMapName, width_, -1);
										AuxDisplayable var33;
										(var33 = new AuxDisplayable((byte)8, 0)).sub_1c3(super.width, height_ - var19.currentHeight - sprButtons.height, var9, (Vector)null);
										auxSkirmishMapPreviewContainer.attachAuxDisplayable(var33, halfWidth_, halfHeight_ + (var19.currentHeight - sprButtons.height) / 2, 3);
										auxSkirmishMapPreviewContainer.attachAuxDisplayable(var19, 0, 0, 0);
										auxSkirmishMapPreviewContainer.setNextDisplayable(auxdisp);
										auxSkirmishMapPreviewContainer.setDrawSoftButtonFlag((byte)0, true);
										auxSkirmishMapPreviewContainer.passUpdatesToAllAttachedAuxDisplayables = true;
										PaintableObject.currentRenderer.setCurrentDisplayable(auxSkirmishMapPreviewContainer);
									}
								}
								else if(auxdisp == auxEditorLevelListContainer)
								{
									var5 = auxEditorLevelList.menuSelected - 1;

									if(action != 0)
									{
										auxEditorLevelListContainer = null;
										auxEditorLevelList = null;
										return;
									}

									if(var5 < 0)
									{
										createNewLevelForm();
										PaintableObject.currentRenderer.setCurrent(frmNewLevel);
										return;
									}

									if(var5 >= skirmishLevelsNames.length || !skirmishLevelLockFlags[var5])
									{
										currentSkirmishLevelNumber = getSkirmishLevelNumber(var5);
										currentMapName = auxEditorLevelList.msgText[var5];

										auxEditorLevelListContainer = null;
										auxEditorLevelList = null;

										PaintableObject.currentRenderer.setCurrentDisplayable(this);
				
										skirmishMode = 1;
										allowedUnits = 8;
										levelEditorMode = 1;

										paintLoadingStringFlag = true;
										PaintableObject.currentRenderer.repaintAndService();
										loadMainGameResources();
										loadLevel(currentSkirmishLevelNumber);
										currentMission = currentSkirmishLevelNumber;
										paintLoadingStringFlag = false;
										gameState = 0;
									}
								}
								else if(auxdisp == auxExportableLevelListContainer)
								{
									var5 = auxExportableLevelList.menuSelected;

									if(action != 0)
									{
										auxExportableLevelListContainer = null;
										auxExportableLevelList = null;
										return;
									}

									if(var5 >= skirmishLevelsNames.length || !skirmishLevelLockFlags[var5])
									{
										currentSkirmishLevelNumber = getSkirmishLevelNumber(var5);
										currentMapName = auxExportableLevelList.msgText[var5];

										if(fso == null)
										{
											fso = new FileSystemObject();
										}

										currentFileList = FileSystemObject.enumerationToArray(FileSystemObject.sortFileList(fso.list(FSO_MAP_SUFFIX, true)), fso.getCurrentPath() != null ? FSO_PARENT_DIR_SAVE : null);

										auxExportLevelsContainer = new AuxDisplayable((byte)15, 15);

										AuxDisplayable auxTTL = auxExportLevelsContainer.createTitleAuxDisplayable(formatFolderTitleString(fso.getCurrentPath(), PaintableObject.getLocaleString(299)));
										auxTTL.titleIcon = sfmMenuIcons[6];

										auxExportLevelsList = new AuxDisplayable((byte)0, 0);
										auxExportLevelsList.initVerticalListAuxDisplayable(currentFileList, 0, 0, width_, height_ - auxTTL.currentHeight - sprButtons.height * 2, 3, 4);

										if(fso.getCurrentPath() != null && currentFileList.length > 2)
										{
											auxExportLevelsList.menuSelected = 2;
										}

										auxExportLevelsContainer.attachAuxDisplayable(auxExportLevelsList, halfWidth_, (height_ + auxTTL.currentHeight) / 2, 3);

										auxExportLevelsContainer.setDrawSoftButtonFlag((byte)0, true);
										auxExportLevelsContainer.passUpdatesToAllAttachedAuxDisplayables = true;
										auxExportLevelsContainer.setNextDisplayable(auxExportableLevelListContainer);

										PaintableObject.currentRenderer.setCurrentDisplayable(auxExportLevelsContainer);
									}
								}
								else if(auxdisp == auxSettingsMenuContainer)
								{
									if(action == 1)
									{
										var23 = Renderer.mainSettings[0];
										boolean var36 = false;
	
										for(var7 = 0; var7 < 4; ++var7)
										{
											boolean var16;
											if((var16 = auxSettingsSwitches[var7].menuSelected == 0) != Renderer.mainSettings[var7])
											{
												Renderer.mainSettings[var7] = var16;
												var36 = true;
											}
										}
	
										if(var36)
										{
											saveMainSettings();
											if(var23 != Renderer.mainSettings[0])
											{
												if(!Renderer.mainSettings[0])
												{
													Renderer.stopCurrentPlayer();
													return;
												}
	
												if(mode == 1)
												{
													if(gameState != 11 && gameState != 14)
													{
														Renderer.startPlayer(FRACTION_BACKGROUND_MUSIC[fractionsTurnQueue[currentTurningPlayer]], 0);
														return;
													}
												}
												else if(mode == 0)
												{
													Renderer.startPlayer(0, 0);
													return;
												}
											}
										}
										else
										{
											auxSettingsMenuContainer = null;
											auxSettingsSwitches = null;
										}
									}
	
									return;
								}
	
								if(auxdisp == auxSaveGameContainer)
								{
									if(action == 0)
									{
										var5 = var_2b0e.menuSelected;

										if(saveCurrentFraction[var5] == -1)
										{
											sub_180(var5, auxdisp);
										}
										else
										{
											auxSaveOverwrireQuestion = createMessageScreen((String)null, PaintableObject.getLocaleString(88), height_, -1);
											auxSaveOverwrireQuestion.setDrawSoftButtonFlag((byte)0, true);
											auxSaveOverwrireQuestion.setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(auxSaveOverwrireQuestion);
										}
									}
									else
									{
										auxSaveGameContainer = null;
										var_2b0e = null;
										var_2b46 = null;
									}
								}
								else if(auxdisp == auxLoadGameContainer)
								{
									if(action == 0)
									{
										byte[] var35 = null;
	
										try
										{
											var35 = Renderer.getRMSData("save", var_2b0e.menuSelected);
										}
										catch(Exception var14)
										{
											;
										}
	
										if(var35 != null)
										{
											auxLoadGameContainer = null;

											disableLevelEditor();
											
											var_2b0e = null;
											var_2b46 = null;
											PaintableObject.currentRenderer.setCurrentDisplayable(this);
											paintLoadingStringFlag = true;
											PaintableObject.currentRenderer.repaintAndService();
											loadMainGameResources();
											loadGameSaveData(var35);
											if(skirmishMode == 0)
											{
												activeFlag = true;
											}
	
											paintLoadingStringFlag = false;
											gameState = 0;
										}
									}
									else
									{
										auxLoadGameContainer = null;
										var_2b0e = null;
										var_2b46 = null;
									}
								}
								else if(auxdisp == var_2b0e)
								{
									if(action == 2 || action == 3)
									{
										var_2b46.initMsgAuxDisplayable((String)null, saveInfoStrings[selindex], width_, -1);
										if(saveCurrentFraction[selindex] == -1)
										{
											var_2b46.gradientColor = 2370117;
										}
										else
										{
											var_2b46.gradientColor = FRACTION_COLORS[saveCurrentFraction[selindex]];
										}
	
										var_2b46.showNotify();
									}
	
									return;
								}
	
								AuxDisplayable var26;
								if(auxdisp == auxOnLineMenuContainer)
								{
									if(action == 0)
									{
										if(seltext.equals(ONLINE_MENU_ITEMS[0]))
										{
											if(var_4217 == null)
											{
												sub_1508(0, "news", PaintableObject.getLocaleString(0), auxdisp);
												return;
											}
	
											var_2df9 = sub_14a5(var_4217, auxdisp);
											return;
										}
	
										if(seltext.equals(ONLINE_MENU_ITEMS[1]))
										{
											auxDownloadLevelsMenuContainer = new AuxDisplayable((byte)11, 0);
											(var32 = auxDownloadLevelsMenuContainer.createTitleAuxDisplayable(seltext)).titleIcon = sfmMenuIcons[6];
											auxDownloadLevelsMenuContainer.initVerticalListAuxDisplayable(DOWNLOAD_LEVELS_MENU_ITEMS, super.width / 2, (height_ + var32.currentHeight) / 2, width_, height_ - var32.currentHeight, 3, 0);
											auxDownloadLevelsMenuContainer.setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(auxDownloadLevelsMenuContainer);
										}

										if(seltext.equals(ONLINE_MENU_ITEMS[2]))
										{
											auxEditorLevelListContainer = new AuxDisplayable((byte)15, 15);
											(var32 = new AuxDisplayable((byte)10, 0)).initMsgAuxDisplayable((String)null, seltext, width_, -1);
											var32.titleIcon = sfmMenuIcons[6];
											skirmishLevelLockFlags = new boolean[12];

											for(var37 = missionsComplete; var37 <= 7; ++var37)
											{
												if(SKIRMISH_LOCKED_LEVELS[var37] < 0)
												{
													continue;
												}

												skirmishLevelLockFlags[SKIRMISH_LOCKED_LEVELS[var37]] = true;
											}

											String[] var27 = new String[12];

											for(var8 = 0; var8 < 12; ++var8)
											{
												if(skirmishLevelLockFlags[var8])
												{
													var27[var8] = PaintableObject.getLocaleString(42);
												}
												else
												{
													var27[var8] = skirmishLevelsNames[var8];
												}
											}

											String[] var21 = new String[12 + downloadedSkirmishLevelCount + 1];
											var21[0] = PaintableObject.getLocaleString(292);
											System.arraycopy(var27, 0, var21, 1, 12);
											System.arraycopy(downloadedSkirmishLevelNames, 0, var21, 13, downloadedSkirmishLevelCount);

											auxEditorLevelList = new AuxDisplayable((byte)0, 0);
											auxEditorLevelList.initVerticalListAuxDisplayable(var21, 0, 0, width_, height_ - var32.currentHeight - sprButtons.height * 2, 3, 4);
											auxEditorLevelListContainer.attachAuxDisplayable(auxEditorLevelList, halfWidth_, (height_ + var32.currentHeight) / 2, 3);
											auxEditorLevelListContainer.attachAuxDisplayable(var32, 0, 0, 0);
											auxEditorLevelListContainer.passUpdatesToAllAttachedAuxDisplayables = true;
											auxEditorLevelListContainer.setDrawSoftButtonFlag((byte)0, true);
											auxEditorLevelListContainer.setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(auxEditorLevelListContainer);
										}
									}
									else if(action == 1)
									{
										auxOnLineMenuContainer = null;
									}
								}
								else if(auxdisp == auxDownloadLevelsMenuContainer)
								{
									if(action == 0)
									{
										if(seltext.equals(DOWNLOAD_LEVELS_MENU_ITEMS[0]))
										{
											if(fso == null)
											{
												fso = new FileSystemObject();
											}
											
											currentFileList = FileSystemObject.enumerationToArray(FileSystemObject.sortFileList(fso.list(FSO_MAP_SUFFIX, true)), fso.getCurrentPath() != null ? FSO_PARENT_DIR : null);

											auxImportLevelsContainer = new AuxDisplayable((byte)15, 15);

											AuxDisplayable auxTTL = auxImportLevelsContainer.createTitleAuxDisplayable(formatFolderTitleString(fso.getCurrentPath(), PaintableObject.getLocaleString(290)));
											auxTTL.titleIcon = sfmMenuIcons[6];

											auxImportLevelsList = new AuxDisplayable((byte)0, 0);
											auxImportLevelsList.initVerticalListAuxDisplayable(currentFileList, 0, 0, width_, height_ - auxTTL.currentHeight - sprButtons.height * 2, 3, 4);

											if(fso.getCurrentPath() != null && currentFileList.length > 1)
											{
												auxImportLevelsList.menuSelected = 1;
											}

											auxImportLevelsContainer.attachAuxDisplayable(auxImportLevelsList, halfWidth_, (height_ + auxTTL.currentHeight) / 2, 3);

											auxImportLevelsContainer.setDrawSoftButtonFlag((byte)0, true);
											auxImportLevelsContainer.passUpdatesToAllAttachedAuxDisplayables = true;
											auxImportLevelsContainer.setNextDisplayable(auxDownloadLevelsMenuContainer);

											PaintableObject.currentRenderer.setCurrentDisplayable(auxImportLevelsContainer);
										}
										else if(seltext.equals(DOWNLOAD_LEVELS_MENU_ITEMS[1]))
										{
											auxExportableLevelListContainer = new AuxDisplayable((byte)15, 15);
											(var32 = new AuxDisplayable((byte)10, 0)).initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(299), width_, -1);
											var32.titleIcon = sfmMenuIcons[6];
											skirmishLevelLockFlags = new boolean[12];

											for(var37 = missionsComplete; var37 <= 7; ++var37)
											{
												if(SKIRMISH_LOCKED_LEVELS[var37] < 0)
												{
													continue;
												}

												skirmishLevelLockFlags[SKIRMISH_LOCKED_LEVELS[var37]] = true;
											}

											String[] var27 = new String[12];

											for(var8 = 0; var8 < 12; ++var8)
											{
												if(skirmishLevelLockFlags[var8])
												{
													var27[var8] = PaintableObject.getLocaleString(42);
												}
												else
												{
													var27[var8] = skirmishLevelsNames[var8];
												}
											}

											String[] var21 = new String[12 + downloadedSkirmishLevelCount];
											System.arraycopy(var27, 0, var21, 0, 12);
											System.arraycopy(downloadedSkirmishLevelNames, 0, var21, 12, downloadedSkirmishLevelCount);

											auxExportableLevelList = new AuxDisplayable((byte)0, 0);
											auxExportableLevelList.initVerticalListAuxDisplayable(var21, 0, 0, width_, height_ - var32.currentHeight - sprButtons.height * 2, 3, 4);
											auxExportableLevelListContainer.attachAuxDisplayable(auxExportableLevelList, halfWidth_, (height_ + var32.currentHeight) / 2, 3);
											auxExportableLevelListContainer.attachAuxDisplayable(var32, 0, 0, 0);
											auxExportableLevelListContainer.passUpdatesToAllAttachedAuxDisplayables = true;
											auxExportableLevelListContainer.setDrawSoftButtonFlag((byte)0, true);
											auxExportableLevelListContainer.setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(auxExportableLevelListContainer);
										}
										else if(seltext.equals(DOWNLOAD_LEVELS_MENU_ITEMS[2]))
										{
											if(var_4289 == null)
											{
												sub_1508(2, "levels", PaintableObject.getLocaleString(0), auxdisp);
											}
											else
											{
												createDownloadableSkirmishLevelsListAuxDisplayable(auxdisp);
												PaintableObject.currentRenderer.setCurrentDisplayable(auxDownloadableSkirmishLevelsContainer);
											}
										}
										else if(seltext.equals(DOWNLOAD_LEVELS_MENU_ITEMS[3]))
										{
											PaintableObject.currentRenderer.setCurrentDisplayable(sub_3e5(auxdisp));
										}
									}
									else if(action == 1)
									{
										auxDownloadLevelsMenuContainer = null;
									}
								}
								else if(auxdisp == var_2df9)
								{
									if(action == 0)
									{
										if(var_4277[selindex] == null)
										{
											var_4191 = selindex;
											sub_1508(1, var_4263[selindex], PaintableObject.getLocaleString(0), auxdisp);
										}
										else
										{
											(var32 = createMessageScreenEx(var_4217[selindex], var_4277[selindex], height_, height_ / 2, -1)).setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(var32);
										}
									}
									else if(action == 1)
									{
										var_2df9 = null;
									}
								}
								else if(auxdisp == auxDownloadableSkirmishLevelsContainer)
								{
									if(action == 0)
									{
										var_41ef = var_2e5d.var_c25[var_2e5d.menuSelected];
										if(availableRMSDownloadSize >= var_42c2[var_41ef])
										{
											sub_1508(3, var_42a0[var_41ef], PaintableObject.getLocaleString(0), auxdisp);
										}
										else
										{
											(var32 = createMessageScreen((String)null, PaintableObject.getLocaleString(55), height_, -1)).setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(var32);
										}
									}
									else if(action == 1)
									{
										var_2e87 = null;
										var_2e5d = null;
										auxDownloadableSkirmishLevelsContainer = null;
									}
								}
								else if(auxdisp == var_2e5d)
								{
									if(action == 2 || action == 3)
									{
										var18 = formatFileSizeString(availableRMSDownloadSize);
										var_2e87.initMsgAuxDisplayable((String)null, PaintableObject.getReplacedLocaleString(54, formatFileSizeString(var_42c2[var_2e5d.var_c25[selindex]])) + "\n" + PaintableObject.getReplacedLocaleString(53, var18), width_, -1);
										var_2e87.showNotify();
									}
								}
								else if(auxdisp == var_2e13)
								{
									if(action == 0)
									{
										if(downloadedSkirmishLevelNumbers[selindex] + skirmishLevelsNames.length == currentMission)
										{
											(var32 = createMessageScreen((String)null, PaintableObject.getLocaleString(56), height_, -1)).setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(var32);
										}
										else
										{
											var_2ee6 = selindex;
											var_2f35 = seltext;
											var_2e20 = createMessageScreenEx((String)null, PaintableObject.getReplacedLocaleString(50, seltext), height_, halfHeight_, -1);
											var_2e20.setNextDisplayable(auxdisp);
											var_2e20.setDrawSoftButtonFlag((byte)0, true);
											PaintableObject.currentRenderer.setCurrentDisplayable(var_2e20);
										}
									}
									else if(action == 1)
									{
										var_2e13 = null;
									}
								}
								else if(auxdisp == var_2e20)
								{
									if(action == 0)
									{
										deleteDownloadedSkirmishLevel(var_2ee6);
										var32 = sub_3e5(var_2e13.nextDisplayable);
										(var26 = createMessageScreen((String)null, PaintableObject.getReplacedLocaleString(51, var_2f35), height_, -1)).setNextDisplayable(var32);
										PaintableObject.currentRenderer.setCurrentDisplayable(var26);
									}
	
									var_2e20 = null;
									var_2f35 = null;
								}
	
								if(auxdisp.mode == 7)
								{
									PaintableObject.currentRenderer.setCurrentDisplayable(this);
								}
								else if(seltext != null && action == 0)
								{
									if(seltext.equals(MENU_ITEMS_TEXT[0]))
									{
										if(mode != 0 && gameState == 0)
										{
											auxNewGameQuestion = createMessageScreen((String)null, PaintableObject.getLocaleString(87), height_, -1);
											auxNewGameQuestion.setDrawSoftButtonFlag((byte)0, true);
											auxNewGameQuestion.setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(auxNewGameQuestion);
										}
										else
										{
											createCircleMenu(PLAY_MENU_ITEMS, var_21e9, var_223e, auxdisp);
										}
									}
									else if(!seltext.equals(MENU_ITEMS_TEXT[1]) && auxdisp != auxSelectLevelList)
									{
										AuxDisplayable var39;
										if(seltext.equals(PaintableObject.getLocaleString(3))) // select level
										{
											auxSelectLevelList = new AuxDisplayable((byte)11, 0);
											var5 = missionsComplete;
											if(missionsComplete > 7)
											{
												var5 = 7;
											}
	
											++var5;
											var31 = new String[var5];
	
											for(var7 = 0; var7 < var5; ++var7)
											{
												var31[var7] = var7 + 1 + ". " + PaintableObject.getLocaleString(121 + var7);
											}
	
											(var39 = auxSelectLevelList.createTitleAuxDisplayable(seltext)).titleIcon = sfmMenuIcons[3];
											auxSelectLevelList.initVerticalListAuxDisplayable(var31, super.width / 2, (height_ + var39.currentHeight) / 2, width_, height_ - var39.currentHeight, 3, 4);
											auxSelectLevelList.setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(auxSelectLevelList);
										}
										else if(seltext.equals(PaintableObject.getLocaleString(4))) // save game
										{
											auxSaveGameContainer = sub_375(seltext, sfmMenuIcons[10]);
											auxSaveGameContainer.setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(auxSaveGameContainer);
										}
										else if(seltext.equals(PaintableObject.getLocaleString(5))) // load game
										{
											auxLoadGameContainer = sub_375(seltext, sfmMenuIcons[2]);
											auxLoadGameContainer.setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(auxLoadGameContainer);
										}
										else if(!seltext.equals(PaintableObject.getLocaleString(6)))
										{
											if(seltext.equals(PaintableObject.getLocaleString(7))) // online
											{
												auxOnLineMenuContainer = new AuxDisplayable((byte)11, 0);
												(var32 = auxOnLineMenuContainer.createTitleAuxDisplayable(seltext)).titleIcon = sfmMenuIcons[6];
												auxOnLineMenuContainer.initVerticalListAuxDisplayable(ONLINE_MENU_ITEMS, super.width / 2, (height_ + var32.currentHeight) / 2, width_, height_ - var32.currentHeight, 3, 0);
												auxOnLineMenuContainer.setNextDisplayable(auxdisp);
												PaintableObject.currentRenderer.setCurrentDisplayable(auxOnLineMenuContainer);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(8))) // settings
											{
												auxSettingsMenuContainer = new AuxDisplayable((byte)15, 15);
												(var32 = new AuxDisplayable((byte)10, 0)).titleIcon = sfmMenuIcons[5];
												var32.initMsgAuxDisplayable((String)null, seltext, width_, -1);
												auxSettingsMenuContainer.attachAuxDisplayable(var32, 0, 0, 20);
												var37 = var32.currentHeight;
												AuxDisplayable[] var20 = new AuxDisplayable[4];
												auxSettingsSwitches = new AuxDisplayable[4];
	
												for(var8 = 0; var8 < 4; ++var8)
												{
													var28 = 8;
													if(var8 != 0)
													{
														var28 = 9;
													}
	
													if(var8 != 3)
													{
														var28 |= 2;
													}
	
													var20[var8] = new AuxDisplayable((byte)10, var28);
													var20[var8].initMsgAuxDisplayable((String)null, Renderer.var_5fa[var8], halfWidth_, -1);
													auxSettingsMenuContainer.attachAuxDisplayable(var20[var8], 0, var37, 20);
													var28 = 4;
													if(var8 != 0)
													{
														var28 = 5;
													}
	
													if(var8 != 3)
													{
														var28 |= 2;
													}
	
													auxSettingsSwitches[var8] = new AuxDisplayable((byte)14, var28);
													auxSettingsSwitches[var8].initHorizontalListAuxDisplayable(MENU_ON_OFF_TEXT, halfWidth_, var20[var8].currentHeight);
													auxSettingsSwitches[var8].menuSelected = Renderer.mainSettings[var8] ? 0 : 1;
													auxSettingsMenuContainer.attachAuxDisplayable(auxSettingsSwitches[var8], super.halfWidth, var37, 20);
													var37 += var20[var8].currentHeight;
												}
	
												auxSettingsMenuContainer.currentAttachedAuxDisplayable = 2;
												auxSettingsMenuContainer.setNextDisplayable(auxdisp);
												PaintableObject.currentRenderer.setCurrentDisplayable(auxSettingsMenuContainer);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(9))) // instructions
											{
												auxInstructionsContainer = new AuxDisplayable((byte)15, 15);
												(var32 = new AuxDisplayable((byte)10, 0)).initMsgAuxDisplayable((String)null, seltext, width_, -1);
												var32.titleIcon = sfmMenuIcons[7];
												var31 = new String[20];
	
												for(var7 = 0; var7 < INSTRUCTION_TITLES.length; ++var7)
												{
													var31[var7] = PaintableObject.getLocaleString(INSTRUCTION_TITLES[var7]) + " " + var7 + "/" + 19;
												}
	
												auxInstructionsPartSelector = new AuxDisplayable((byte)14, 2);
												auxInstructionsPartSelector.initHorizontalListAuxDisplayable(var31, width_, -1);

												auxInstructionsTextMessage = new AuxDisplayable((byte)10, 1);
												auxInstructionsTextMessage.initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(INSTRUCTION_TEXT_MESSAGES[0]), width_, height_ - var32.currentHeight - auxInstructionsPartSelector.currentHeight - sprButtons.height * 2);

												var7 = var32.currentHeight + sprButtons.height;
												auxInstructionsContainer.attachAuxDisplayable(auxInstructionsPartSelector, halfWidth_, var7, 17);
												var7 += auxInstructionsPartSelector.currentHeight;
												auxInstructionsContainer.attachAuxDisplayable(auxInstructionsTextMessage, halfWidth_, var7, 17);
												auxInstructionsContainer.attachAuxDisplayable(var32, 0, 0, 0);
												auxInstructionsContainer.setNextDisplayable(auxdisp);
												auxInstructionsContainer.passUpdatesToAllAttachedAuxDisplayables = true;
												PaintableObject.currentRenderer.setCurrentDisplayable(auxInstructionsContainer);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(10))) // about
											{
												var32 = new AuxDisplayable((byte)15, 15);
												(var26 = new AuxDisplayable((byte)10, 0)).initMsgAuxDisplayable((String)null, seltext, width_, -1);
												var26.titleIcon = sfmMenuIcons[8];
												var39 = new AuxDisplayable((byte)10, 0);
												String var29 = PaintableObject.getReplacedLocaleString(16, midletVersion);
												var39.initMsgAuxDisplayable((String)null, var29, width_, height_ - var26.currentHeight - sprButtons.height * 2);
												var32.attachAuxDisplayable(var39, 0, (height_ + var26.currentHeight) / 2, 6);
												var32.attachAuxDisplayable(var26, 0, 0, 0);
												var32.setNextDisplayable(auxdisp);
												var32.passUpdatesToAllAttachedAuxDisplayables = true;
												PaintableObject.currentRenderer.setCurrentDisplayable(var32);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(11))) // exit
											{
												auxExitGameQuestion = createMessageScreen((String)null, PaintableObject.getLocaleString(86), height_, -1);
												auxExitGameQuestion.setDrawSoftButtonFlag((byte)0, true);
												auxExitGameQuestion.setNextDisplayable(auxdisp);
												PaintableObject.currentRenderer.setCurrentDisplayable(auxExitGameQuestion);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(60))) // main menu
											{
												createCircleMenu(MAIN_MENU_ITEMS, halfHeight_, height_, auxdisp);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(61))) // move
											{
												var_202c = false;
												prepareUnitMovement(currentSelectedUnit);
												PaintableObject.currentRenderer.setCurrentDisplayable(this);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(62))) // attack
											{
												setArrayValuesEx(mapAlphaData, 0);
												prevGameState = gameState;
												gameState = 6;
												cursorPositionChanged = true;
												unitsWithinAttackRange = currentSelectedUnit.getUnitsWithinAttackRange(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY, (byte)0);
												currentAttackTargetUnit = 0;
												paintMapAlphaDataFlag = true;
												showAtackRange = true;
												currentSelectedUnit.fillAttackRangeDataEx(mapAlphaData, currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY);
												sprCursor.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[1]);
												moveCursor(unitsWithinAttackRange[currentAttackTargetUnit].currentMapPosX, unitsWithinAttackRange[currentAttackTargetUnit].currentMapPosY);
												var_17f7 = true;
												var_1824 = true;
												PaintableObject.currentRenderer.setCurrentDisplayable(this);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(63))) // buy
											{
												auxUnitBuyContainer = new AuxDisplayable((byte)15, 15);
												auxUnitBuyContainer.var_cc7 = height_;
												var23 = false;
												auxBuyUnitInfo = new AuxDisplayable((byte)2, 2);
												auxBuyUnitSelector = new AuxDisplayable((byte)3, 1);
												auxBuyUnitDescription = new AuxDisplayable((byte)10, 3);
												auxBuyUnitDescription.var_92c = true;
												auxBuyUnitInfo.selectedUnit = auxBuyUnitSelector.availableUnits[0];
												auxBuyUnitDescription.initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(184 + auxBuyUnitSelector.availableUnits[0].type), width_, height_ - auxBuyUnitInfo.currentHeight - auxBuyUnitSelector.currentHeight);
												auxUnitBuyContainer.attachAuxDisplayable(auxBuyUnitInfo, 0, 0, 0);
												auxUnitBuyContainer.attachAuxDisplayable(auxBuyUnitDescription, 0, auxBuyUnitInfo.currentHeight, 0);
												auxUnitBuyContainer.attachAuxDisplayable(auxBuyUnitSelector, 0, height_, 32);
												auxUnitBuyContainer.passUpdatesToAllAttachedAuxDisplayables = true;
												auxUnitBuyContainer.setNextDisplayable(this);
												auxUnitBuyContainer.setDrawSoftButtonFlag((byte)0, true);
												PaintableObject.currentRenderer.setCurrentDisplayable(auxUnitBuyContainer);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(64))) // end move
											{
												currentSelectedUnit.finishMove();
												clearAttackData();
												currentUnitUnderCursor = getUnit(cursorMapX, cursorMapY, (byte)0);
												gameState = 0;
												PaintableObject.currentRenderer.setCurrentDisplayable(this);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(66))) // end turn
											{
												endTurn();
												PaintableObject.currentRenderer.setCurrentDisplayable(this);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(68))) // repair
											{
												setMapElement(FRACTION_BUILDINGS, currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY);
												PaintableObject.currentRenderer.setCurrentDisplayable(createMessageScreen((String)null, PaintableObject.getLocaleString(74), height_, 1000));
												Renderer.startPlayer(9, 1);
												currentSelectedUnit.finishMove();
												gameState = 0;
											}
											else if(seltext.equals(PaintableObject.getLocaleString(67))) // occupy
											{
												if(sub_d5e(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY, currentSelectedUnit))
												{
													setBuildingFraction(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY, fractionsTurnQueue[currentSelectedUnit.fractionPosInTurnQueue]);
													PaintableObject.currentRenderer.setCurrentDisplayable(createMessageScreen((String)null, PaintableObject.getLocaleString(73), height_, 1000));
													gameState = 9;
													Renderer.startPlayer(9, 1);
													var_12ff = delayCounter;
												}
	
												currentSelectedUnit.finishMove();
											}
											else if(seltext.equals(PaintableObject.getLocaleString(69)))
											{
												gameState = 7;
												unitsWithinAttackRange = currentSelectedUnit.getUnitsWithinAttackRange(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY, (byte)1);
												paintMapAlphaDataFlag = true;
												showAtackRange = true;
												currentSelectedUnit.fillAttackRangeDataEx(mapAlphaData, currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY);
												var_17f7 = true;
												PaintableObject.currentRenderer.setCurrentDisplayable(this);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(70)))
											{
												var32 = new AuxDisplayable((byte)15, 15);
												(var26 = new AuxDisplayable((byte)10, 0)).initMsgAuxDisplayable((String)null, currentMapName, width_, -1);
												(var39 = new AuxDisplayable((byte)8, 0)).sub_1c3(width_, height_ - var26.currentHeight - sprButtons.height, mapData, units);
												var32.attachAuxDisplayable(var39, halfWidth_, halfHeight_ + (var26.currentHeight - sprButtons.height) / 2, 3);
												var32.attachAuxDisplayable(var26, 0, 0, 0);
												var32.setNextDisplayable(auxdisp);
												var32.passUpdatesToAllAttachedAuxDisplayables = true;
												PaintableObject.currentRenderer.setCurrentDisplayable(var32);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(71)))
											{
												auxObjectivesMessage.setDrawSoftButtonFlag((byte)0, false);
												auxObjectivesMessage.setNextDisplayable(auxdisp);
												PaintableObject.currentRenderer.setCurrentDisplayable(auxObjectivesMessage);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(293)))
											{
												addDownloadedSkirmishLevel(currentMapName, saveLevel());
												
												AuxDisplayable auxMsg = createMessageScreen((String)null, PaintableObject.getReplacedLocaleString(45, currentMapName), height_, 2000);
												auxMsg.setNextDisplayable(this);
												PaintableObject.currentRenderer.setCurrentDisplayable(auxMsg);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(305))) // rename
											{
												createRenameForm();
												PaintableObject.currentRenderer.setCurrent(frmRename);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(306))) // resize
											{
												createFillSetupForm();
												createResizeForm();
												PaintableObject.currentRenderer.setCurrent(frmResize);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(307))) // rnd select
											{
												createRandomSelectForm();
												PaintableObject.currentRenderer.setCurrent(frmRandomSelect);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(303))) // fill setup
											{
												createFillSetupForm();
												PaintableObject.currentRenderer.setCurrent(frmFillSetup);
											}
											else if(seltext.equals(PaintableObject.getLocaleString(314))) // console
											{
												createConsoleForm();
												PaintableObject.currentRenderer.setCurrent(tbConsole);
											}
										}
										else
										{
											auxSkirmishLevelListContainer = new AuxDisplayable((byte)15, 15);
											(var32 = new AuxDisplayable((byte)10, 0)).initMsgAuxDisplayable((String)null, seltext, width_, -1);
											var32.titleIcon = sfmMenuIcons[4];
											skirmishLevelLockFlags = new boolean[12];
	
											for(var37 = missionsComplete; var37 <= 7; ++var37)
											{
												if(SKIRMISH_LOCKED_LEVELS[var37] < 0)
												{
													continue;
												}

												skirmishLevelLockFlags[SKIRMISH_LOCKED_LEVELS[var37]] = true;
											}
	
											String[] var27 = new String[12];
	
											for(var8 = 0; var8 < 12; ++var8)
											{
												if(skirmishLevelLockFlags[var8])
												{
													var27[var8] = PaintableObject.getLocaleString(42);
												}
												else
												{
													var27[var8] = skirmishLevelsNames[var8];
												}
											}
	
											String[] var21 = new String[12 + downloadedSkirmishLevelCount];
											System.arraycopy(var27, 0, var21, 0, 12);
											System.arraycopy(downloadedSkirmishLevelNames, 0, var21, 12, downloadedSkirmishLevelCount);

											auxSkirmishLevelList = new AuxDisplayable((byte)0, 0);
											auxSkirmishLevelList.initVerticalListAuxDisplayable(var21, 0, 0, width_, height_ - var32.currentHeight - sprButtons.height * 2, 3, 4);
											auxSkirmishLevelListContainer.attachAuxDisplayable(auxSkirmishLevelList, halfWidth_, (height_ + var32.currentHeight) / 2, 3);
											auxSkirmishLevelListContainer.attachAuxDisplayable(var32, 0, 0, 0);
											auxSkirmishLevelListContainer.passUpdatesToAllAttachedAuxDisplayables = true;
											auxSkirmishLevelListContainer.setDrawSoftButtonFlag((byte)0, true);
											auxSkirmishLevelListContainer.setNextDisplayable(auxdisp);
											PaintableObject.currentRenderer.setCurrentDisplayable(auxSkirmishLevelListContainer);
										}
									}
									else
									{
										PaintableObject.currentRenderer.setCurrentDisplayable(this);

										if(auxdisp == auxSelectLevelList)
										{
											currentMission = selindex;
											auxSelectLevelList = null;
										}
										else
										{
											currentMission = 0;
										}

										disableLevelEditor();
	
										skirmishMode = 0;
										playerModes[1] = 0;
										paintLoadingStringFlag = true;
										PaintableObject.currentRenderer.repaintAndService();
										System.gc();
										loadMainGameResources();
										loadLevel(currentMission);
										paintLoadingStringFlag = false;
										initStoryMission();
										gameState = 0;
									}
								}
							}
						}
					}
				}
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public final Unit buyUnit(Unit var1, int var2, int var3)
	{
		money[currentTurningPlayer] -= var1.cost;
		var1.health = 100;
		var1.setMapPosition(var2, var3);
		if(!units.contains(var1))
		{
			units.addElement(var1);
		}

		statusBarNeedsRepaint = true;
		return var1;
	}

	public final Unit buyUnit(byte var1, int var2, int var3)
	{
		money[currentTurningPlayer] -= Unit.UNIT_COST[var1];
		statusBarNeedsRepaint = true;
		return Unit.createUnit(var1, currentTurningPlayer, var2, var3);
	}

//	public final Sprite getUnitIcon(byte var1, byte var2)
//	{
//		return sprUnitIcons[var1][var2];
//	}

	public final void sub_58e()
	{
		units = new Vector();
		currentSelectedUnit = null;
		unitsWithinAttackRange = null;
		var_31be = null;
	}

	public final void createLevel(String name, int mwidth, int mheight, int[] probabilities)
	{
		try
		{
			Renderer.stopCurrentPlayer();
			activeEffects = new Vector();
			drawAlphaDrap = false;
			drawCursorFlag = true;
			var_19c3 = false;
			var_29de = false;
			currentRoute = null;
			lastDeadUnit = null;
			heavenFuryTarget = null;
			var_16e5 = null;
			var_1774 = null;
			var_26d9.removeAllElements();
			currentTurn = 0;
			currentTurningPlayer = 0;
			currentTurningPlayer = 0;
			scriptState = 0;
			sub_58e();
			fractionKings = null;
			mapData = (byte[][])null;
			mapAlphaData = (byte[][])null;
			turnQueueLength = 0;

			for(int var2 = 0; var2 < 5; ++var2)
			{
				fractionsPosInTurnQueue[var2] = -1;
			}

			cursorPositionChanged = true;
			Renderer.loadResources();

			currentMapName = name;

			mapWidth = mwidth;
			mapHeight = mheight;
			mapData = new byte[mapWidth][mapHeight];
			mapAlphaData = new byte[mapWidth][mapHeight];
			var_39e3 = new byte[mapWidth][mapHeight];

			castleCount = 0;
			int buildingCount = 0;

			Vector vBuildingData = new Vector();
			Vector vCastleData = new Vector();

			int buildingFraction;
			short mx;

			Renderer.convertProbabilities(probabilities, true);

			for(mx = 0; mx < mapWidth; ++mx)
			{
				for(short my = 0; my < mapHeight; ++my)
				{
					mapData[mx][my] = (byte)Renderer.randomFromProbabilities(probabilities);
					mapAlphaData[mx][my] = 0;

					if(isBuilding(mapData[mx][my]))
					{
						buildingFraction = getBuildingFraction(mx, my);

						byte[] var6 = new byte[3];
						var6[0] = (byte)mx;
						var6[1] = (byte)my;
						var6[2] = (byte)buildingFraction;

						vBuildingData.addElement(var6);
						++buildingCount;

						if(getTileType(mx, my) == 9)
						{
							if(skirmishMode == 1 && buildingFraction != 0 && fractionsPosInTurnQueue[buildingFraction] == -1)
							{
								fractionsTurnQueue[turnQueueLength] = (byte)buildingFraction;
								fractionsPosInTurnQueue[buildingFraction] = turnQueueLength++;
							}

							byte[] var7 = new byte[2];
							var7[0] = (byte)mx;
							var7[1] = (byte)my;

							vCastleData.addElement(var7);
							++castleCount;
						}
					}
				}
			}

			Renderer.convertProbabilities(probabilities, false);

			var_399e = new int[buildingCount];
			buildingData = new byte[buildingCount][];

			vBuildingData.copyInto(buildingData);
			vBuildingData = null;

			castleData = new byte[castleCount][];

			vCastleData.copyInto(castleData);
			vCastleData = null;

			mapWidthPixels = mapWidth * 24;
			mapHeightPixels = mapHeight * 24;

			var_3d57 = false;
			var_3c5c = null;
			crystallEscortLeader = null;
			crystallEscortCrystall = null;
			crystallEscortFollower = null;
			templeWarrior = null;
			lastFinishedMoveUnit = null;

			if(skirmishMode == 1)
			{
				scriptState = 100;
				activeFlag = true;
			}

			var_3903 = new Unit[buildingData.length];
			var_3947 = new byte[buildingData.length];
			buildingEffects = new Sprite[buildingData.length];

			for(mx = 0; mx < buildingData.length; ++mx)
			{
				if(getTileType(buildingData[mx][0], buildingData[mx][1]) == 8)
				{
					buildingEffects[mx] = Sprite.createSimpleSparkSprite(sprBSmoke, 0, -1, 0, 1, 250, (byte)0);
					buildingEffects[mx].active = false;
				}
			}

			Renderer.startPlayer_(FRACTION_BACKGROUND_MUSIC[1], 0);

			mapX = 0;
			mapY = 0;
			cursorMapX = 0;
			cursorMapY = 0;
			sprCursor.setPosition(cursorMapX * 24, cursorMapY * 24);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public final void loadLevel(int var1)
	{
		try
		{
			Renderer.stopCurrentPlayer();
			activeEffects = new Vector();
			drawAlphaDrap = false;
			drawCursorFlag = true;
			var_19c3 = false;
			var_29de = false;
			currentRoute = null;
			lastDeadUnit = null;
			heavenFuryTarget = null;
			var_16e5 = null;
			var_1774 = null;
			var_26d9.removeAllElements();
			currentTurn = 0;
			currentTurningPlayer = 0;
			currentTurningPlayer = 0;
			scriptState = 0;
			sub_58e();
			fractionKings = null;
			mapData = (byte[][])null;
			mapAlphaData = (byte[][])null;
			turnQueueLength = 0;
	
			for(int var2 = 0; var2 < 5; ++var2)
			{
				fractionsPosInTurnQueue[var2] = -1;
			}
	
			cursorPositionChanged = true;
			Renderer.loadResources();
			DataInputStream dis;

			if(skirmishMode == 0)
			{
				currentMapName = PaintableObject.getLocaleString(113 + var1);
				dis = new DataInputStream(Renderer.getResourceAsStream("m" + var1 + ".aem"));
			}
			else
			{
				currentMapName = getSkirmishLevelName(var1);
				dis = getSkirmishLevelAsStream(var1);
			}
	
			mapWidth = dis.readInt();
			mapHeight = dis.readInt();
			mapData = new byte[mapWidth][mapHeight];
			mapAlphaData = new byte[mapWidth][mapHeight];
			var_39e3 = new byte[mapWidth][mapHeight];

			castleCount = 0;
			int buildingCount = 0;

			Vector vBuildingData = new Vector();
			Vector vCastleData = new Vector();
	
			int buildingFraction;
			short mx;

			for(mx = 0; mx < mapWidth; ++mx)
			{
				for(short my = 0; my < mapHeight; ++my)
				{
					mapData[mx][my] = dis.readByte();
					mapAlphaData[mx][my] = 0;

					if(isBuilding(mapData[mx][my]))
					{
						buildingFraction = getBuildingFraction(mx, my);

						byte[] var6 = new byte[3];
						var6[0] = (byte)mx;
						var6[1] = (byte)my;
						var6[2] = (byte)buildingFraction;

						vBuildingData.addElement(var6);
						++buildingCount;

						if(getTileType(mx, my) == 9)
						{
							if(skirmishMode == 1 && buildingFraction != 0 && fractionsPosInTurnQueue[buildingFraction] == -1)
							{
								fractionsTurnQueue[turnQueueLength] = (byte)buildingFraction;
								fractionsPosInTurnQueue[buildingFraction] = turnQueueLength++;
							}

							byte[] var7 = new byte[2];
							var7[0] = (byte)mx;
							var7[1] = (byte)my;

							vCastleData.addElement(var7);
							++castleCount;
						}
					}
				}
			}
	
			var_399e = new int[buildingCount];
			buildingData = new byte[buildingCount][];

			vBuildingData.copyInto(buildingData);
			vBuildingData = null;
	
			castleData = new byte[castleCount][];

			vCastleData.copyInto(castleData);
			vCastleData = null;
			
			mapWidthPixels = mapWidth * 24;
			mapHeightPixels = mapHeight * 24;

			if(skirmishMode == 1)
			{
				for(mx = 0; mx < turnQueueLength; ++mx)
				{
					money[mx] = skirmishStartMoney;
				}
			}
			else
			{
				turnQueueLength = 2;
				money[0] = 0;
				money[1] = 0;
				fractionsPosInTurnQueue[1] = 0;
				fractionsPosInTurnQueue[2] = 1;
				fractionsTurnQueue[0] = 1;
				fractionsTurnQueue[1] = 2;
				playerTeams[0] = 0;
				playerTeams[1] = 1;
				playerModes[0] = 1;
				playerModes[1] = 0;
				skirmishUnitCap = 100;
			}
	
			for(mx = 0; mx < buildingData.length; ++mx)
			{
				byte var17 = buildingData[mx][2];
				int var18 = getFractionPosInTurnQueue(var17);

				if(var17 > 0 && (var18 < 0 || playerModes[var18] == 2))
				{
					setBuildingFraction(buildingData[mx][0], buildingData[mx][1], 0);
				}
			}
	
			buildingFraction = dis.readInt();
			dis.skip((long)(buildingFraction * 4));
			int var9 = dis.readInt();
			fractionKings = new Unit[turnQueueLength];
			fractionsAllKings = new Unit[turnQueueLength][4];
			fractionsKingCount = new int[turnQueueLength];
	
			for(mx = 0; mx < var9; ++mx)
			{
				byte var10 = dis.readByte();
				int var11 = dis.readShort() / 24;
				int var12 = dis.readShort() / 24;
				byte var13 = (byte)(var10 % 12);
				byte var14 = (byte)getFractionPosInTurnQueue(1 + var10 / 12);

				if(playerModes[var14] != 2)
				{
					Unit var15 = Unit.createUnit(var13, var14, var11, var12);

					if(var13 == 9)
					{
						fractionKings[var14] = var15;
					}
				}
			}
	
			dis.close();
			if(skirmishMode == 0)
			{
				auxObjectivesMessage = createMessageScreen(PaintableObject.getLocaleString(121 + currentMission), PaintableObject.getLocaleString(129 + currentMission), height_, -1);
			}
			else
			{
				auxObjectivesMessage = auxObjectivesMessage = createMessageScreen(PaintableObject.getLocaleString(71), PaintableObject.getLocaleString(137), height_, -1);
			}
	
			var_3d57 = false;
			var_3c5c = null;
			crystallEscortLeader = null;
			crystallEscortCrystall = null;
			crystallEscortFollower = null;
			templeWarrior = null;
			lastFinishedMoveUnit = null;
	
			for(mx = 0; mx < fractionKings.length; ++mx)
			{
				if(fractionKings[mx] == null)
				{
					fractionKingPositions[mx][0] = 0;
					fractionKingPositions[mx][1] = 0;
				}
				else
				{
					fractionKingPositions[mx][0] = (byte)fractionKings[mx].currentMapPosX;
					fractionKingPositions[mx][1] = (byte)fractionKings[mx].currentMapPosY;
				}
			}
	
			if(skirmishMode == 1)
			{
				scriptState = 100;
				activeFlag = true;
	
				for(mx = 0; mx < turnQueueLength; ++mx)
				{
					if(playerModes[mx] != 2)
					{
						currentTurningPlayer = (byte)mx;
						break;
					}
	
					++currentTurn;
				}
			}
	
			if(fractionKings.length > 0 && fractionKings[currentTurningPlayer] != null)
			{
				moveMapShowPoint(fractionKings[currentTurningPlayer].currentMapPosX, fractionKings[currentTurningPlayer].currentMapPosY);
				moveCursor(fractionKings[currentTurningPlayer].currentMapPosX, fractionKings[currentTurningPlayer].currentMapPosY);
			}
	
			var_3903 = new Unit[buildingData.length];
			var_3947 = new byte[buildingData.length];
			buildingEffects = new Sprite[buildingData.length];
	
			for(mx = 0; mx < buildingData.length; ++mx)
			{
				if(getTileType(buildingData[mx][0], buildingData[mx][1]) == 8)
				{
					buildingEffects[mx] = Sprite.createSimpleSparkSprite(sprBSmoke, 0, -1, 0, 1, 250, (byte)0);
					buildingEffects[mx].active = false;
				}
			}
	
			if(playerModes[currentTurningPlayer] == 0)
			{
				sub_1022();
			}

			if(levelEditorMode != 0 || turnQueueLength == 0)
			{
				Renderer.startPlayer_(FRACTION_BACKGROUND_MUSIC[1], 0);
			}

			if(levelEditorMode != 0)
			{
				mapX = 0;
				mapY = 0;
				cursorMapX = 0;
				cursorMapY = 0;
				sprCursor.setPosition(cursorMapX * 24, cursorMapY * 24);
			}
			else
			{
				loadScript(var1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public final void fillLevel(int[] probabilities)
	{
		Renderer.convertProbabilities(probabilities, true);

		for(int mx = 0; mx < mapWidth; mx++)
		{
			for(int my = 0; my < mapHeight; my++)
			{
				if(mapAlphaData[mx][my] > 0)
				{
					mapData[mx][my] = (byte)Renderer.randomFromProbabilities(probabilities);
				}
			}
		}

		Renderer.convertProbabilities(probabilities, false);

		rescanLevel();
	}

	public final void resizeLevel(int dl, int dr, int dt, int db, int[] probabilities)
	{
		int newWidth = mapWidth + dl + dr;
		int newHeight = mapHeight + dt + db;

		byte[][] newData = new byte[newWidth][newHeight];
		int x, y, nx, ny;

		Renderer.convertProbabilities(probabilities, true);

		for(nx = 0; nx < newWidth; nx++)
		{
			for(ny = 0; ny < newHeight; ny++)
			{
				x = nx - dl;
				y = ny - dt;

				if(x >= 0 && y >= 0 && x < mapWidth && y < mapHeight)
				{
					newData[nx][ny] = mapData[x][y];
				}
				else
				{
					newData[nx][ny] = (byte)Renderer.randomFromProbabilities(probabilities);
				}
			}
		}

		Renderer.convertProbabilities(probabilities, false);
		
		for(int i = 0; i < units.size(); i++)
		{
			Unit unit = (Unit)units.elementAt(i);
			unit.setMapPosition(unit.currentMapPosX + dl, unit.currentMapPosY + dt);
		}

		mapData = newData;

		mapWidth = newWidth;
		mapHeight = newHeight;

		mapAlphaData = new byte[mapWidth][mapHeight];
		var_39e3 = new byte[mapWidth][mapHeight];

		rescanLevel();

		mapWidthPixels = mapWidth * 24;
		mapHeightPixels = mapHeight * 24;

		mapX = 0;
		mapY = 0;
		cursorMapX = 0;
		cursorMapY = 0;
		sprCursor.setPosition(cursorMapX * 24, cursorMapY * 24);
	}

	public final void rescanLevel()
	{
		try
		{
			turnQueueLength = 0;

			for(int var2 = 0; var2 < 5; ++var2)
			{
				fractionsPosInTurnQueue[var2] = -1;
			}

			castleCount = 0;
			int buildingCount = 0;

			Vector vBuildingData = new Vector();
			Vector vCastleData = new Vector();

			int buildingFraction;
			short mx;

			for(mx = 0; mx < mapWidth; ++mx)
			{
				for(short my = 0; my < mapHeight; ++my)
				{
					//mapAlphaData[mx][my] = 0;

					if(isBuilding(mapData[mx][my]))
					{
						buildingFraction = getBuildingFraction(mx, my);

						byte[] var6 = new byte[3];
						var6[0] = (byte)mx;
						var6[1] = (byte)my;
						var6[2] = (byte)buildingFraction;

						vBuildingData.addElement(var6);
						++buildingCount;

						if(getTileType(mx, my) == 9)
						{
							if(skirmishMode == 1 && buildingFraction != 0 && fractionsPosInTurnQueue[buildingFraction] == -1)
							{
								fractionsTurnQueue[turnQueueLength] = (byte)buildingFraction;
								fractionsPosInTurnQueue[buildingFraction] = turnQueueLength++;
							}

							byte[] var7 = new byte[2];
							var7[0] = (byte)mx;
							var7[1] = (byte)my;

							vCastleData.addElement(var7);
							++castleCount;
						}
					}
				}
			}

			var_399e = new int[buildingCount];
			buildingData = new byte[buildingCount][];

			vBuildingData.copyInto(buildingData);
			vBuildingData = null;

			castleData = new byte[castleCount][];

			vCastleData.copyInto(castleData);
			vCastleData = null;

			var_3903 = new Unit[buildingData.length];
			var_3947 = new byte[buildingData.length];
			buildingEffects = new Sprite[buildingData.length];

			for(mx = 0; mx < buildingData.length; ++mx)
			{
				if(getTileType(buildingData[mx][0], buildingData[mx][1]) == 8)
				{
					buildingEffects[mx] = Sprite.createSimpleSparkSprite(sprBSmoke, 0, -1, 0, 1, 250, (byte)0);
					buildingEffects[mx].active = false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public final byte[] saveLevel()
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);

			dos.writeInt(mapWidth);
			dos.writeInt(mapHeight);

			for(int mx = 0; mx < mapWidth; mx++)
			{
				for(int my = 0; my < mapHeight; my++)
				{
					dos.writeByte(mapData[mx][my]);
				}
			}

			dos.writeInt(0);

			dos.writeInt(units.size());

			for(int i = 0; i < units.size(); i++)
			{
				Unit unit = (Unit)units.elementAt(i);

				dos.writeByte((fractionsTurnQueue[unit.fractionPosInTurnQueue] - 1) * 12 + unit.type);
				dos.writeShort(unit.currentMapPosX * 24);
				dos.writeShort(unit.currentMapPosY * 24);
			}

			dos.flush();

			byte[] res = baos.toByteArray();
			dos.close();

			return res;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public final void clearAttackData()
	{
		currentAttackTargetUnit = 0;
		currentSelectedUnit = null;
		unitsWithinAttackRange = new Unit[0];
		setArrayValuesEx(mapAlphaData, 0);
		paintMapAlphaDataFlag = false;
		showAtackRange = false;
	}

	public final void setArrayValuesEx(byte[][] var1, int var2)
	{
		for(int var3 = 0; var3 < mapWidth; ++var3)
		{
			for(int var4 = 0; var4 < mapHeight; ++var4)
			{
				var1[var3][var4] = (byte)var2;
			}
		}
	}

	public final void sub_675(Unit var1)
	{
		Renderer.stopPlayer(10);
		var_31be = null;
		setArrayValuesEx(mapAlphaData, 0);
		paintMapAlphaDataFlag = false;
		if(playerModes[currentTurningPlayer] == 1)
		{
			mapStepMin = 1;
			drawCursorFlag = true;
			sprCursor.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[0]);
			gameState = 3;
			createInGameMiniMenu(getInGameMenuOptionsAvailabe(var1, (byte)0), var1);
			Renderer.startPlayer(11, 1);
		}
		else
		{
			if(playerModes[currentTurningPlayer] == 0)
			{
				var_36e4 = 4;
				gameState = 0;
			}

		}
	}

	public final byte[] getInGameMenuOptionsAvailabe(Unit var1, byte var2)
	{
		int var3 = 0;
		byte[] var4 = new byte[INGAME_ACTIONS_TEXT.length];
		if(var2 == 1 && getTileType(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY) == 9 && isBuildingOfFraction(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY, var1.fractionPosInTurnQueue))
		{
			++var3;
			var4[0] = 0;
		}

		if(sub_d41(var1.currentMapPosX, var1.currentMapPosY, var1))
		{
			var4[var3++] = 2;
		}
		else if(sub_d5e(var1.currentMapPosX, var1.currentMapPosY, var1))
		{
			var4[var3++] = 1;
		}

		if((var2 == 1 || var1.type != 7) && var1.characterData.length > 0 && var1.getUnitsWithinAttackRange(var1.currentMapPosX, var1.currentMapPosY, (byte)0).length > 0)
		{
			var4[var3++] = 3;
		}

		if(var1.hasProperty((short)32) && var1.getUnitsWithinAttackRange(var1.currentMapPosX, var1.currentMapPosY, (byte)1).length > 0)
		{
			var4[var3++] = 4;
		}

		if(var2 == 1)
		{
			var4[var3++] = 5;
		}
		else
		{
			var4[var3++] = 6;
		}

		byte[] var5 = new byte[var3];
		System.arraycopy(var4, 0, var5, 0, var3);
		return var5;
	}

	public final void updateIntroTransition()
	{
		if(Renderer.nointro && introTransMode < 4)
		{
			sfmGameLogo = new SpriteFrame("logo", width, -1);
			introTransMode = 3;
			combatDrapValue = 40;
		}

		switch(introTransMode)
		{
			case 0:
				if(delayCounter >= 1200L)
				{
					introTransMode = 1;
				}

				combatDrapValue = 40;
				return;
			case 1:
				if(combatDrapValue <= 0)
				{
					sfmLogo = null;
					combatDrapValue = 0;
					++introTransMode;
					setIntroMode(0, 0, 3);
					return;
				}

				--combatDrapValue;
				return;
			case 2:
				setIntroMode(1, 2, 3);
				sfmGameLogo = new SpriteFrame("logo", width, -1);
				++introTransMode;
				return;
			case 3:
				if((combatDrapValue += 1) > 40)
				{
					try
					{
						sfmSplash = new SpriteFrame("splash", width_, -1);
						sfmGlow = new SpriteFrame("glow");
					}
					catch(Exception var2)
					{
						;
					}

					if(sfmSplash != null)
					{
						combatDrapValue = 0;
					}
					else
					{
						combatDrapValue = 11;
					}

					++introTransMode;
					PaintableObject.currentRenderer.clearKeyStates();
					return;
				}
				break;
			case 4:
				if(combatDrapValue < 16)
				{
					++combatDrapValue;
					++var_1a3b;
					if(sfmGlow != null)
					{
						glowX = -sfmGlow.imgwidth;
						return;
					}
				}
				else
				{
					if(glowX >= super.width * 4)
					{
						if(sfmGlow != null)
						{
							glowX = -sfmGlow.imgwidth;
						}
					}
					else
					{
						glowX += sfmGameLogo.imgwidth / 6;
					}

					if(delayCounter % 100L == 0L)
					{
						paintPressAnyKeyFlag = !paintPressAnyKeyFlag;
					}

					if((sfmSplash == null || PaintableObject.currentRenderer.isAnyKeyPressed()) && isActive())
					{
						if(sfmGlow != null)
						{
							glowX = -sfmGlow.imgwidth;
						}

						paintPressAnyKeyFlag = false;
						int var1 = sfmGameLogo.imgheight + 1;
						createCircleMenu(var_5e3, (super.height + var1) / 2, super.height - var1, sfmSplash == null ? null : this);
						PaintableObject.currentRenderer.clearKeyStates();
					}
				}
		}

	}

	public final void update()
	{
		delayCounter += 50L;

		if(mode == 2)
		{
			updateCombatAnimation();
		}
		else if(mode == 3)
		{
			updateIntro();
		}
		else if(mode == 0)
		{
			updateIntroTransition();
		}
		else
		{
			updateScript();

			if(currentHelp != -1)
			{
				if(Renderer.mainSettings[2])
				{
					AuxDisplayable var4;
					(var4 = createDialogScreen(PaintableObject.getLocaleStringEx(196 + currentHelp, true), (byte)-1, (byte)2)).mainColor = 7831691;
					var4.gradientColor = 7831691;
					var4.fontColor = 16250855;
				}

				currentHelp = -1;
			}

			if(PaintableObject.currentRenderer.currentDisplayable == this)
			{
				int var1;
				int var21;

				if(gameState == 0)
				{
					for(var1 = 0; var1 < buildingEffects.length; ++var1)
					{
						var21 = getBuildingFraction(buildingData[var1][0], buildingData[var1][1]);

						if(buildingEffects[var1] != null && var21 != -1 && var21 != 0 && !buildingEffects[var1].active && Renderer.rnd.nextInt() % 8 == 0)
						{
							buildingEffects[var1].active = true;
							buildingEffects[var1].setCurrentFrame(0);
							buildingEffects[var1].bounceMode = 1;
							buildingEffects[var1].setPosition((buildingData[var1][0] + 1) * 24 - sprBSmoke.width, buildingData[var1][1] * 24 - 2);
							newEffects.addElement(buildingEffects[var1]);
						}
					}
				}

				if(delayCounter - var_1b6a >= 300L)
				{
					var_1b42 = !var_1b42;
					var_1b6a = delayCounter;
				}

				if(shakeMapFlag && delayCounter - shakeMapDelayCounterStart >= shakeMapTime)
				{
					shakeMapFlag = false;
				}

				if(var_19c3)
				{
					++combatDrapValue;
					if(combatDrapValue > 16)
					{
						if(gameState == 10)
						{
							var_3e0c = 1;
						}
						else if(gameState == 11)
						{
							if(skirmishMode == 0)
							{
								updateAlphaDrap = true;
								inverseDrapAlpha = true;
								drapAlphaValue = 0;
							}

							var_207c = 0;
							var_12ff = delayCounter;
						}
						else
						{
							createCombatAnimation(currentAttackUnit, currentAttackVictimUnit);
							var_37c2 = null;
							clearAttackData();
						}

						var_19c3 = false;
					}

				}
				else
				{
					if(updateAlphaDrap)
					{
						if(drawAlphaDrap)
						{
							if(drapAlphaValue < 16)
							{
								++drapAlphaValue;
							}
						}
						else if(inverseDrapAlpha)
						{
							++drapAlphaValue;
							if(drapAlphaValue > 16)
							{
								inverseDrapAlpha = false;
							}
						}
					}

					if(currentRoute != null)
					{
						var_129c = (var_129c + 1) % 12;
					}

					if(paintMapAlphaDataFlag)
					{
						if(var_35c4 == 0)
						{
							++var_357f;
							if(var_357f >= 15)
							{
								var_35c4 = 1;
							}
						}
						else
						{
							--var_357f;
							if(var_357f <= 0)
							{
								var_35c4 = 0;
							}
						}

						if(alphaWindowSize > 0)
						{
							alphaWindowSize -= 4;
							if(alphaWindowSize < 0)
							{
								alphaWindowSize = 0;
							}
						}
					}

					if(drawCursorFlag && delayCounter - var_726 >= 200L)
					{
						sprCursor.nextFrame();
						var_726 = delayCounter;
					}

					var21 = cursorMapX * 24;
					int var5 = cursorMapY * 24;
					int var6 = sprCursor.currentX;
					int var7 = sprCursor.currentY;
					if(var21 > var6)
					{
						var6 += 8;
					}
					else if(var21 < var6)
					{
						var6 -= 8;
					}

					if(var5 > var7)
					{
						var7 += 8;
					}
					else if(var5 < var7)
					{
						var7 -= 8;
					}

					sprCursor.setPosition(var6, var7);

					if(!var_2319 && statusBarOffset > 0)
					{
						if(statusBarOffset < 2)
						{
							statusBarOffset = 0;
						}
						else
						{
							statusBarOffset /= 2;
						}

						statusBarNeedsRepaint = true;
						tileIconNeedsRepaint = true;
					}
					else
					{
						String var8;
						AuxDisplayable var9;
						Unit var20;

						if(gameState == 8)
						{
							if(var_12cb == 0)
							{
								if(statusBarOffset >= FOOTER_HEIGHT)
								{
									var_12cb = 1;
									sub_cff();

									if(playerModes[currentTurningPlayer] == 1)
									{
										var8 = "" + currentIncome;
									}
									else
									{
										var8 = "?";
									}

									var9 = createMessageScreen(PaintableObject.getLocaleString(75), PaintableObject.getReplacedLocaleString(76, var8), height_, 1500);
									PaintableObject.currentRenderer.setCurrentDisplayable(var9);
									var9.gradientColor = FRACTION_COLORS[fractionsTurnQueue[currentTurningPlayer]];
									Renderer.startPlayer_(FRACTION_BACKGROUND_MUSIC[fractionsTurnQueue[currentTurningPlayer]], 0);
									return;
								}

								if(statusBarOffset == 0)
								{
									var_2319 = true;
									statusBarOffset = 1;
								}
								else
								{
									statusBarOffset *= 2;
								}

								statusBarNeedsRepaint = true;
								tileIconNeedsRepaint = true;
							}
							else
							{
								for(var1 = units.size() - 1; var1 >= 0; --var1)
								{
									var20 = (Unit)units.elementAt(var1);
									
									if(var20.unitState != 3 && currentTurningPlayer == var20.fractionPosInTurnQueue && (getTileType(var20.currentMapPosX, var20.currentMapPosY) == 7 || sub_e68(var20.currentMapPosX, var20.currentMapPosY, playerTeams[var20.fractionPosInTurnQueue])) && var20.health < 100)
									{
										int hpToHeal = 100 - var20.health;
										
										if(hpToHeal > 20)
										{
											hpToHeal = 20;
										}

										var20.health += hpToHeal;
										
										Sprite var10 = Sprite.createGraphicTextSprite("+" + hpToHeal, 0, -4, (byte)1);
										var10.setPosition(var20.currentX + var20.width / 2, var20.currentY + var20.height);
										
										activeEffects.addElement(var10);
									}
								}

								var_12cb = 0;
								var_2319 = false;
								gameState = 0;
							}
						}
						else if(gameState == 9)
						{
							gameState = 0;
						}
						else if(gameState == 11)
						{
							if(!var_19c3 && var_207c == 0 && (skirmishMode == 1 || delayCounter - var_12ff >= 3000L || PaintableObject.currentRenderer.isAnyKeyPressed()))
							{
								height_ = super.height;
								halfHeight_ = super.halfHeight;
								createCircleMenu(var_5e3, super.halfHeight, super.height, (PaintableObject)null);
								var_207c = 1;
								inverseDrapAlpha = false;
							}
						}
						else if(gameState != 10 && gameState != 14)
						{
							int var16;
							Sprite var19;
							int var23;
							if(gameState == 13)
							{
								if(var_1a98 == 0)
								{
									Renderer.vibrate(200);
									var16 = currentAttackUnit.attackUnit(currentAttackVictimUnit);
									currentAttackVictimUnit.scheduleIdleAnimationStop(400);
									Renderer.startPlayer(14, 1);
									createSimpleSparkSprite(sprRedSpark, currentAttackVictimUnit.currentX, currentAttackVictimUnit.currentY, 0, 0, 2, 50);
									var19 = Sprite.createGraphicTextSprite("-" + var16, 0, -4, (byte)1);
									if((var23 = currentAttackVictimUnit.currentX + currentAttackVictimUnit.width / 2) + var19.width / 2 > mapWidthPixels)
									{
										var23 = mapWidthPixels - var19.width / 2;
									}
									else if(var23 - var19.width / 2 < 0)
									{
										var23 = var19.width / 2;
									}

									var19.setPosition(var23, currentAttackVictimUnit.currentY + currentAttackVictimUnit.height);
									activeEffects.addElement(var19);
									var_1ac1 = delayCounter;
									++var_1a98;
								}
								else if(var_1a98 == 1)
								{
									if(delayCounter - var_1ac1 >= 800L)
									{
										moveCursor(currentAttackUnit.currentMapPosX, currentAttackUnit.currentMapPosY);
										if(currentAttackVictimUnit.canPerformCloseAttack(currentAttackUnit, currentAttackUnit.currentMapPosX, currentAttackUnit.currentMapPosY))
										{
											Renderer.vibrate(200);
											var16 = currentAttackVictimUnit.attackUnit(currentAttackUnit);
											currentAttackUnit.scheduleIdleAnimationStop(400);
											Renderer.startPlayer(14, 1);
											createSimpleSparkSprite(sprRedSpark, currentAttackUnit.currentX, currentAttackUnit.currentY, 0, 0, 2, 50);
											var19 = Sprite.createGraphicTextSprite("-" + var16, 0, -4, (byte)1);
											if((var23 = currentAttackUnit.currentX + currentAttackUnit.width / 2) + var19.width / 2 > mapWidthPixels)
											{
												var23 = mapWidthPixels - var19.width / 2;
											}
											else if(var23 - var19.width / 2 < 0)
											{
												var23 = var19.width / 2;
											}

											var19.setPosition(var23, currentAttackUnit.currentY + currentAttackUnit.height);
											activeEffects.addElement(var19);
											var_1ac1 = delayCounter;
											++var_1a98;
										}
										else
										{
											finishAttack();
										}
									}
								}
								else if(delayCounter - var_1ac1 >= 800L)
								{
									finishAttack();
								}
							}
							else if(heavenFuryTarget != null)
							{
								if(var_2985 == 0)
								{
									if(var_326f)
									{
										heavenFuryBlast = createSimpleSparkSprite(sprSpark, heavenFuryTarget.currentX, -mapY, 0, 12, -1, 0);
										AuxDisplayable var18;
										(var18 = createMessageScreen((String)null, PaintableObject.getLocaleString(280), height_, 2000)).setPosition(halfWidth_, 2, 17);
										PaintableObject.currentRenderer.setCurrentDisplayable(var18);
										var_2985 = 1;
									}
								}
								else if(var_2985 == 1)
								{
									for(var1 = 0; var1 < 3; ++var1)
									{
										createSimpleSparkSprite(sprBSmoke, heavenFuryBlast.currentX + Renderer.randomToRange(heavenFuryBlast.width - sprBSmoke.width), heavenFuryBlast.currentY, 0, Renderer.randomFromRange(-3, 0), 1, 50 * Renderer.randomToRange(4));
									}

									if(heavenFuryBlast.currentY >= heavenFuryTarget.currentY)
									{
										heavenFuryBlast.active = false;
										Renderer.vibrate(200);
										shakeMap(500);
										if(var_29c1)
										{
											if((var16 = 25 + Renderer.randomToRange(25)) > heavenFuryTarget.health)
											{
												var16 = heavenFuryTarget.health;
											}

											heavenFuryTarget.health -= var16;
											(var19 = Sprite.createGraphicTextSprite("-" + var16, 0, -4, (byte)1)).setPosition(heavenFuryTarget.currentX + heavenFuryTarget.width / 2, heavenFuryTarget.currentY + heavenFuryTarget.height);
											activeEffects.addElement(var19);
										}

										sub_6de(heavenFuryTarget);
										var_2985 = 2;
									}
								}
								else if((var_2985 += 1) >= 20)
								{
									if(heavenFuryTarget.health <= 0)
									{
										lastDeadUnit = heavenFuryTarget;
										createSimpleSparkSprite(sprSpark, lastDeadUnit.currentX, lastDeadUnit.currentY, 0, 0, 1, 50);
										Renderer.startPlayer(12, 1);
										var_1728 = delayCounter;
									}

									heavenFuryTarget = null;
								}
							}
							else if(var_16e5 != null)
							{
								if((var_17d5 -= 1) <= 0)
								{
									Renderer.vibrate(100);
									sub_6de(var_16e5);
									setMapElement((byte)27, var_16e5.currentMapPosX, var_16e5.currentMapPosY);
									var_16e5 = null;
								}
							}
							else if(lastDeadUnit != null)
							{
								if(delayCounter - var_1728 >= 300L && isMapAtPosition(lastDeadUnit.currentMapPosX, lastDeadUnit.currentMapPosY))
								{
									if(skirmishMode == 0 && currentMission == 7 && lastDeadUnit == fractionKings[1])
									{
										var_29de = true;
									}
									else
									{
										createSimpleSparkSprite(sprSmoke, lastDeadUnit.currentX, lastDeadUnit.currentY, 0, -3, 1, 100);
										lastDeadUnit.unitState = 3;
										lastDeadUnit.var_758 = 3;
										if(lastDeadUnit.type != 10 && lastDeadUnit.type != 11)
										{
											if(lastDeadUnit.type == 9)
											{
												lastDeadUnit.setMapPosition(-10, -10);
												lastDeadUnit.effectState = 0;
												lastDeadUnit.updateEffects();
											}
										}
										else
										{
											lastDeadUnit.removeThisUnit();
										}

										if(lastDeadUnit.type == 9 && lastDeadUnit.cost < 1000)
										{
											lastDeadUnit.cost += 200;
										}
									}

									lastDeadUnit = null;
								}
							}
							else if(var_26d9.size() > 0)
							{
								var20 = (Unit)var_26d9.elementAt(0);
								if(var_2786 == 0)
								{
									moveCursor(var20.currentMapPosX, var20.currentMapPosY);
									var_2786 = 1;
								}
								else if(isMapAtPosition(var20.currentMapPosX, var20.currentMapPosY))
								{
									createSimpleSparkSprite(sprSmallSpark, var20.currentX + Renderer.randomToRange(var20.width), var20.currentY + Renderer.randomToRange(var20.height), 0, 0, 1, 50);
									if(var_2786 == 1)
									{
										Renderer.startPlayer(13, 1);
									}

									if(var_2786 <= 5)
									{
										short var22 = 200;
										if(var_2786 == 5)
										{
											var22 = 1000;
										}

										var23 = var20.currentX + (var20.width - sprLevelUp.width) / 2;
										int var11 = var20.currentY - var_2786 * 4;
										if(var23 < 0)
										{
											var23 = 0;
										}
										else if(var23 + sprLevelUp.width > mapWidthPixels)
										{
											var23 = mapWidthPixels - sprLevelUp.width;
										}

										if(var11 < 0)
										{
											var11 = 0;
										}

										createSimpleSparkSprite(sprLevelUp, var23, var11, 0, 0, 1, var22);
									}

									++var_2786;
									if(var_2786 >= 20)
									{
										var_26d9.removeElement(var20);
										var_2786 = 0;
										if(var20.type != 9 && var20.rank <= 6 && var20.rank % 2 == 0)
										{
											PaintableObject.currentRenderer.setCurrentDisplayable(createMessageScreen((String)null, PaintableObject.getLocaleString(80) + "\n" + var20.name, width_, 2000));
										}
									}
								}
							}
							else if(var_1774 != null)
							{
								if(delayCounter - var_17c8 >= 400L)
								{
									var_1774.removeThisUnit();
									Unit.createUnit((byte)10, var_17b3, var_1774.currentMapPosX, var_1774.currentMapPosY).finishMove();
									var_1774 = null;
								}
							}
							else if(!var_3d57)
							{
								if(gameState == 2)
								{
									if(currentSelectedUnit.unitState != 1 && var_326f)
									{
										sub_675(currentSelectedUnit);
									}
								}
								else if(levelEditorMode == 0 && playerModes[currentTurningPlayer] == 0)
								{
									sub_1043();
								}
								else if(isActive())
								{
									if(var_1824 && PaintableObject.currentRenderer.isKeyPressed(KEY_LSK))
									{
										PaintableObject.currentRenderer.handleKeyPressedGameAction(16);
										PaintableObject.currentRenderer.handleKeyReleasedGameAction(KEY_LSK);
									}

									if(gameState != 6 && gameState != 7)
									{
										if(delayCounter - var_755 >= 150L && sprCursor.currentX % 24 == 0 && sprCursor.currentY % 24 == 0)
										{
											if(!PaintableObject.currentRenderer.wasKeyPressed(4) && !PaintableObject.currentRenderer.isKeyHeld(4))
											{
												if(PaintableObject.currentRenderer.wasKeyPressed(8) || PaintableObject.currentRenderer.isKeyHeld(8))
												{
													if(cursorMapX < mapWidth - 1)
													{
														++cursorMapX;
													}

													cursorPositionChanged = true;
													var_755 = delayCounter;
												}
											}
											else
											{
												if(cursorMapX > 0)
												{
													--cursorMapX;
												}

												cursorPositionChanged = true;
												var_755 = delayCounter;
											}

											if(!PaintableObject.currentRenderer.wasKeyPressed(1) && !PaintableObject.currentRenderer.isKeyHeld(1))
											{
												if(PaintableObject.currentRenderer.wasKeyPressed(2) || PaintableObject.currentRenderer.isKeyHeld(2))
												{
													if(cursorMapY < mapHeight - 1)
													{
														++cursorMapY;
													}

													cursorPositionChanged = true;
													var_755 = delayCounter;
												}
											}
											else
											{
												if(cursorMapY > 0)
												{
													--cursorMapY;
												}

												cursorPositionChanged = true;
												var_755 = delayCounter;
											}

											if(cursorPositionChanged)
											{
												if(gameState == 1)
												{
													if(mapAlphaData[cursorMapX][cursorMapY] > 0)
													{
														currentRoute = currentSelectedUnit.findRouteFromPointToPoint(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY, cursorMapX, cursorMapY);
													}
												}
												else
												{
													currentUnitUnderCursor = getUnit(cursorMapX, cursorMapY, (byte)0);
												}

												tileIconNeedsRepaint = true;
											}

											cursorPositionChanged = false;
										}

										if((gameState == 1 || gameState == 0) && levelEditorMode == 0 && PaintableObject.currentRenderer.wasKeyPressed(256))
										{
											if((var20 = getUnit(cursorMapX, cursorMapY, (byte)0)) != null)
											{
												(var9 = new AuxDisplayable((byte)15, 15)).var_cc7 = height_;
												boolean var24 = false;
												AuxDisplayable var26 = new AuxDisplayable((byte)5, 2);
												AuxDisplayable var12;
												(var12 = new AuxDisplayable((byte)10, 1)).var_92c = true;
												String var13 = PaintableObject.getLocaleString(184 + var20.type);
												if(var20.effectState != 0)
												{
													StringBuffer var14 = new StringBuffer(PaintableObject.getLocaleString(98));
													if((var20.effectState & 2) != 0)
													{
														var14.append('\n');
														var14.append(PaintableObject.getLocaleString(100));
													}

													if((var20.effectState & 1) != 0)
													{
														var14.append('\n');
														var14.append(PaintableObject.getLocaleString(99));
													}

													var14.append("\n-----------\n");
													var13 = var14.toString() + var13;
												}

												var12.initMsgAuxDisplayable((String)null, var13, width_, height_ - var26.currentHeight);
												var9.attachAuxDisplayable(var26, 0, 0, 0);
												var9.attachAuxDisplayable(var12, 0, var26.currentHeight, 0);
												var9.passUpdatesToAllAttachedAuxDisplayables = true;
												var9.setNextDisplayable(this);
												PaintableObject.currentRenderer.setCurrentDisplayable(var9);
											}

											PaintableObject.currentRenderer.handleKeyReleasedGameAction(256);
										}

										if(gameState == 1)
										{
											if(PaintableObject.currentRenderer.wasKeyPressed(16) && currentSelectedUnit != null)
											{
												var20 = getUnit(cursorMapX, cursorMapY, (byte)0);

												if(mapAlphaData[cursorMapX][cursorMapY] > 0 && (var20 == null || var20 == currentSelectedUnit))
												{
													selectedUnitPrevMapX = currentSelectedUnit.currentMapPosX;
													selectedUnitPrevMapY = currentSelectedUnit.currentMapPosY;
													currentSelectedUnit.plotRoute(cursorMapX, cursorMapY, true);
													var_31be = currentSelectedUnit;
													drawCursorFlag = false;
													paintMapAlphaDataFlag = false;
													currentRoute = null;
													auxInGameMenu = null;
													var_17f7 = false;
													var_1824 = false;
													gameState = 2;
													Renderer.startPlayer(10, 1);
												}

												PaintableObject.currentRenderer.handleKeyReleasedGameAction(16);
											}
										}
										else if(gameState == 0)
										{
											if(levelEditorMode == 1)
											{
												if(PaintableObject.currentRenderer.wasKeyPressed(128))
												{
													if(++currentTile >= sfmTiles.length)
													{
														currentTile = 0;
													}

													statusBarNeedsRepaint = true;
													tileIconNeedsRepaint = true;
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(64))
												{
													if(--currentTile < 0)
													{
														currentTile = (byte)(sfmTiles.length - 1);
													}

													statusBarNeedsRepaint = true;
													tileIconNeedsRepaint = true;
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(16))
												{
													if(paintMapAlphaDataFlag)
													{
														if(mapAlphaData[cursorMapX][cursorMapY] == 0)
														{
															mapAlphaData[cursorMapX][cursorMapY] = 1;
														}
														else
														{
															mapAlphaData[cursorMapX][cursorMapY] = 0;
														}

														paintMapAlphaDataFlag = false;

														for(int mx = 0; mx < mapWidth; mx++)
														{
															for(int my = 0; my < mapHeight; my++)
															{
																if(mapAlphaData[mx][my] > 0)
																{
																	paintMapAlphaDataFlag = true;
																	break;
																}
															}

															if(paintMapAlphaDataFlag)
															{
																break;
															}
														}
													}
													else
													{
														mapData[cursorMapX][cursorMapY] = currentTile;
														rescanLevel();
													}
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(512))
												{
													if(currentTile == mapData[cursorMapX][cursorMapY])
													{
														if(++currentTile >= sfmTiles.length)
														{
															currentTile = 0;
														}

														mapData[cursorMapX][cursorMapY] = currentTile;
														rescanLevel();
													}
													else
													{
														currentTile = mapData[cursorMapX][cursorMapY];
													}

													statusBarNeedsRepaint = true;
													tileIconNeedsRepaint = true;
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(256))
												{
													if(currentTile == mapData[cursorMapX][cursorMapY])
													{
														if(--currentTile < 0)
														{
															currentTile = (byte)(sfmTiles.length - 1);
														}

														mapData[cursorMapX][cursorMapY] = currentTile;
														rescanLevel();
													}
													else
													{
														currentTile = mapData[cursorMapX][cursorMapY];
													}

													statusBarNeedsRepaint = true;
													tileIconNeedsRepaint = true;
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(32))
												{
													if(paintMapAlphaDataFlag)
													{
														fillLevel(tileProbabilities);
													}
													else
													{
														currentTile = mapData[cursorMapX][cursorMapY];

														createFillSetupForm();

														for(int i = 0; i < tileProbabilities.length; i++)
														{
															tileProbabilities[i] = 0;
															selflags[i] = false;
														}

														tileProbabilities[currentTile] = 100;
														selflags[currentTile] = true;

														cgFillWith.setSelectedFlags(selflags);

														statusBarNeedsRepaint = true;
														tileIconNeedsRepaint = true;
													}
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(16384))
												{
													if(paintMapAlphaDataFlag)
													{
														if(mapAlphaData[cursorMapX][cursorMapY] == 0)
														{
															mapAlphaData[cursorMapX][cursorMapY] = 1;
														}
														else
														{
															int minX = mapWidth;
															int minY = mapHeight;

															int maxX = -1;
															int maxY = -1;

															int x, y;

															for(x = 0; x < mapWidth; x++)
															{
																for(y = 0; y < mapHeight; y++)
																{
																	if(mapAlphaData[x][y] > 0)
																	{
																		if(x < minX)
																		{
																			minX = x;
																		}

																		if(x > maxX)
																		{
																			maxX = x;
																		}

																		if(y < minY)
																		{
																			minY = y;
																		}

																		if(y > maxY)
																		{
																			maxY = y;
																		}
																	}
																}
															}

															boolean rectAlreadyFilled = true;

															for(x = minX; x <= maxX; x++)
															{
																for(y = minY; y <= maxY; y++)
																{
																	if(mapAlphaData[x][y] == 0)
																	{
																		rectAlreadyFilled = false;
																	}

																	mapAlphaData[x][y] = 1;
																}
															}

															if(rectAlreadyFilled)
															{
																if(minX == 0 && minY == 0 && maxX == mapWidth - 1 && maxY == mapHeight - 1)
																{
																	setArrayValuesEx(mapAlphaData, 0);
																	paintMapAlphaDataFlag = false;
																}
																else
																{
																	setArrayValuesEx(mapAlphaData, 1);
																}
															}
														}
													}
													else
													{
														mapAlphaData[cursorMapX][cursorMapY] = 1;
														paintMapAlphaDataFlag = true;
													}
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(8192))
												{
													if(turnQueueLength > 0)
													{
														setArrayValuesEx(mapAlphaData, 0);
														paintMapAlphaDataFlag = false;

														levelEditorMode = 2;
														currentTurningPlayer = 0;
														currentUnit = null;
														statusBarNeedsRepaint = true;
														tileIconNeedsRepaint = true;
													}
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(KEY_LSK))
												{
													auxInGameMenu = new AuxDisplayable((byte)11, 0);
													auxInGameMenu.initVerticalListAuxDisplayable(MENU_INGAME_LEVEL_EDITOR_ITEMS, 2, 2, -1, height_, 20, 0);
													auxInGameMenu.setNextDisplayable(this);
													PaintableObject.currentRenderer.setCurrentDisplayable(auxInGameMenu);
												}
											}
											else if(levelEditorMode == 2)
											{
												if(PaintableObject.currentRenderer.wasKeyPressed(128))
												{
													if(++currentUnitType >= 12)
													{
														currentUnitType = 0;
													}

													currentUnit = null;
													statusBarNeedsRepaint = true;
													tileIconNeedsRepaint = true;
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(64))
												{
													if(--currentUnitType < 0)
													{
														currentUnitType = 11;
													}

													currentUnit = null;
													statusBarNeedsRepaint = true;
													tileIconNeedsRepaint = true;
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(16))
												{
													Unit unit = getUnit(cursorMapX, cursorMapY, (byte)0);

													if(unit == null)
													{
														Unit.createUnitForLevelEditor(currentUnitType, currentTurningPlayer, cursorMapX, cursorMapY, true);
													}
													else
													{
														unit.removeThisUnit();
													}
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(512))
												{
													Unit unit = getUnit(cursorMapX, cursorMapY, (byte)0);

													if(unit != null)
													{
														if(currentUnitType != unit.type)
														{
															currentUnitType = unit.type;
														}
														else
														{
															if(++currentUnitType >= 12)
															{
																currentUnitType = 0;
															}

															unit.removeThisUnit();
															Unit.createUnitForLevelEditor(currentUnitType, currentTurningPlayer, cursorMapX, cursorMapY, true);
														}
													}
													else
													{
														Unit.createUnitForLevelEditor(currentUnitType, currentTurningPlayer, cursorMapX, cursorMapY, true);
													}

													currentUnit = null;
													statusBarNeedsRepaint = true;
													tileIconNeedsRepaint = true;
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(256))
												{
													Unit unit = getUnit(cursorMapX, cursorMapY, (byte)0);

													if(unit != null)
													{
														if(currentUnitType != unit.type)
														{
															currentUnitType = unit.type;
														}
														else
														{
															if(--currentUnitType < 0)
															{
																currentUnitType = 11;
															}

															unit.removeThisUnit();
															Unit.createUnitForLevelEditor(currentUnitType, currentTurningPlayer, cursorMapX, cursorMapY, true);
														}
													}
													else
													{
														Unit.createUnitForLevelEditor(currentUnitType, currentTurningPlayer, cursorMapX, cursorMapY, true);
													}

													currentUnit = null;
													statusBarNeedsRepaint = true;
													tileIconNeedsRepaint = true;
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(8192))
												{
													if(++currentTurningPlayer >= turnQueueLength)
													{
														currentTurningPlayer = 0;
														levelEditorMode = 1;
													}
													
													currentUnit = null;
													statusBarNeedsRepaint = true;
													tileIconNeedsRepaint = true;
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(KEY_LSK))
												{
													auxInGameMenu = new AuxDisplayable((byte)11, 0);
													auxInGameMenu.initVerticalListAuxDisplayable(MENU_INGAME_LEVEL_EDITOR_ITEMS, 2, 2, -1, height_, 20, 0);
													auxInGameMenu.setNextDisplayable(this);
													PaintableObject.currentRenderer.setCurrentDisplayable(auxInGameMenu);
												}
											}
											else
											{
												if(PaintableObject.currentRenderer.wasKeyPressed(512))
												{
													var16 = 0;
													Unit unit = fractionKings[currentTurningPlayer];
													if(currentUnitUnderCursor != null && currentUnitUnderCursor.type == 9)
													{
														unit = fractionsAllKings[currentTurningPlayer][(currentUnitUnderCursor.kingNumber + 1) % fractionsKingCount[currentTurningPlayer]];
													}

													while(true)
													{
														++var16;
														if(var16 >= fractionsKingCount[currentTurningPlayer] || unit.unitState != 3)
														{
															if(unit != null && unit.unitState != 3)
															{
																moveCursor(unit.currentMapPosX, unit.currentMapPosY);
																moveMapShowPointPixels(unit.currentX + 12, unit.currentY + 12);
															}
															break;
														}

														unit = fractionsAllKings[currentTurningPlayer][(unit.kingNumber + 1) % fractionsKingCount[currentTurningPlayer]];
													}
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(32))
												{
													if(showAtackRange)
													{
														setArrayValuesEx(mapAlphaData, 0);
														paintMapAlphaDataFlag = false;
														showAtackRange = false;
													}
													else
													{
														currentSelectedUnit = getUnit(cursorMapX, cursorMapY, (byte)0);
														if(currentSelectedUnit != null)
														{
															setArrayValuesEx(mapAlphaData, 0);
															currentSelectedUnit.fillAttackRangeData(mapAlphaData);
															showAtackRange = true;
															paintMapAlphaDataFlag = true;
															alphaWindowSize = 12;
														}
													}

													PaintableObject.currentRenderer.handleKeyReleasedGameAction(32);
												}
												else if(PaintableObject.currentRenderer.wasKeyPressed(16) || PaintableObject.currentRenderer.wasKeyPressed(KEY_LSK))
												{
													currentSelectedUnit = getUnit(cursorMapX, cursorMapY, (byte)0);
													byte[] var28;

													if(currentSelectedUnit != null && currentSelectedUnit.unitState == 0 && currentSelectedUnit.fractionPosInTurnQueue == currentTurningPlayer)
													{
														if((var28 = getInGameMenuOptionsAvailabe(currentSelectedUnit, (byte)1)).length > 1)
														{
															createInGameMiniMenu(var28, currentSelectedUnit);
															Renderer.startPlayer(11, 1);
														}
														else
														{
															var_202c = false;
															prepareUnitMovement(currentSelectedUnit);
														}
													}
													else if(getTileType(cursorMapX, cursorMapY) == 9 && isBuildingOfFraction(cursorMapX, cursorMapY, currentTurningPlayer))
													{
														var28 = new byte[] { (byte)0 };
														createInGameMiniMenu(var28, (Unit)null);
														Renderer.startPlayer(11, 1);
													}
													else
													{
														currentSelectedUnit = null;
														auxInGameMenu = new AuxDisplayable((byte)11, 0);
														auxInGameMenu.initVerticalListAuxDisplayable(INGAME_MENU_TEXT, 2, 2, -1, height_, 20, 0);
														auxInGameMenu.setNextDisplayable(this);
														PaintableObject.currentRenderer.setCurrentDisplayable(auxInGameMenu);
														Renderer.startPlayer(11, 1);
													}

													PaintableObject.currentRenderer.clearKeyStates();
												}
											}
										}
									}
									else
									{
										if(!PaintableObject.currentRenderer.wasKeyPressed(4) && !PaintableObject.currentRenderer.wasKeyPressed(1))
										{
											if(PaintableObject.currentRenderer.wasKeyPressed(8) || PaintableObject.currentRenderer.wasKeyPressed(2))
											{
												++currentAttackTargetUnit;
												if(currentAttackTargetUnit >= unitsWithinAttackRange.length)
												{
													currentAttackTargetUnit = 0;
												}

												PaintableObject.currentRenderer.handleKeyReleasedGameAction(8);
												PaintableObject.currentRenderer.handleKeyReleasedGameAction(2);
												cursorPositionChanged = true;
											}
										}
										else
										{
											--currentAttackTargetUnit;
											if(currentAttackTargetUnit < 0)
											{
												currentAttackTargetUnit = unitsWithinAttackRange.length - 1;
											}

											PaintableObject.currentRenderer.handleKeyReleasedGameAction(4);
											PaintableObject.currentRenderer.handleKeyReleasedGameAction(1);
											cursorPositionChanged = true;
										}

										moveCursor(unitsWithinAttackRange[currentAttackTargetUnit].currentMapPosX, unitsWithinAttackRange[currentAttackTargetUnit].currentMapPosY);
										if(cursorPositionChanged)
										{
											currentUnitUnderCursor = getUnit(cursorMapX, cursorMapY, (byte)0);
											tileIconNeedsRepaint = true;
										}

										if(PaintableObject.currentRenderer.wasKeyPressed(16))
										{
											if(gameState == 6)
											{
												startAttack(currentSelectedUnit, unitsWithinAttackRange[currentAttackTargetUnit]);
											}
											else if(gameState == 7)
											{
												sub_726(unitsWithinAttackRange[currentAttackTargetUnit], currentTurningPlayer);
												currentSelectedUnit.finishMove();
												gameState = 0;
											}

											auxInGameMenu = null;
											paintMapAlphaDataFlag = false;
											showAtackRange = false;
											var_17f7 = false;
											var_1824 = false;
										}

										cursorPositionChanged = false;
									}
								}
							}
						}
						else if(var_3e0c != 1 && (gameState != 14 || var_3e0c == 2))
						{
							if(var_3e0c == 0)
							{
								var_19c3 = true;
								combatDrapValue = 0;
							}
							else if(var_3e0c == 2)
							{
								if(gameState == 14)
								{
									height_ = super.height;
									halfHeight_ = super.halfHeight;
									createCircleMenu(var_5e3, super.halfHeight, super.height, (PaintableObject)null);
									return;
								}

								if(currentMission <= 7)
								{
									loadLevel(currentMission);
									initStoryMission();
									gameState = 0;
								}
							}
						}
						else
						{
							++currentMission;
							if(currentMission > missionsComplete)
							{
								if(SKIRMISH_LOCKED_LEVELS[missionsComplete] >= 0)
								{
									var8 = skirmishLevelsNames[SKIRMISH_LOCKED_LEVELS[missionsComplete]];
									var9 = createMessageScreen((String)null, PaintableObject.getReplacedLocaleString(82, var8), super.height, 3000);
									PaintableObject.currentRenderer.setCurrentDisplayable(var9);
								}

								missionsComplete = currentMission;

								try
								{
									byte[] var27 = new byte[] { (byte)missionsComplete };
									Renderer.setRMSData("settings", 1, var27);
								}
								catch(Exception var15)
								{
									;
								}
							}

							var_3e0c = 2;
						}
					}

					var1 = 0;

					int var3;
					for(var3 = units.size(); var1 < var3; ++var1)
					{
						((Unit)units.elementAt(var1)).update();
					}

					if(delayCounter - var_1c06 >= 300L)
					{
						currentWaterPairTile = (currentWaterPairTile + 1) % sprWaterPair.length;
						sfmTiles[var_1c69] = sprWaterPair[currentWaterPairTile];
						var_1c06 = delayCounter;
					}

					sub_768();
					if(var_17f7 && PaintableObject.currentRenderer.isKeyPressed(KEY_RSK))
					{
						if(gameState == 1)
						{
							gameState = 0;
							setArrayValuesEx(mapAlphaData, 0);
							currentRoute = null;
							sprCursor.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[0]);
							moveCursor(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY);
							currentSelectedUnit = null;
						}
						else if(gameState == 6 || gameState == 7)
						{
							gameState = prevGameState;
							setArrayValuesEx(mapAlphaData, 0);
							sprCursor.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[0]);
							moveCursor(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY);
							PaintableObject.currentRenderer.setCurrentDisplayable(auxInGameMenu);
						}

						paintMapAlphaDataFlag = false;
						showAtackRange = false;
						PaintableObject.currentRenderer.handleKeyReleasedGameAction(KEY_RSK);
						var_17f7 = false;
						var_1824 = false;
					}

					for(var1 = activeEffects.size() - 1; var1 >= 0; --var1)
					{
						Sprite var17;
						(var17 = (Sprite)activeEffects.elementAt(var1)).update();
						if(!var17.active)
						{
							activeEffects.removeElement(var17);
						}
					}

					var1 = 0;

					for(var3 = newEffects.size(); var1 < var3; ++var1)
					{
						activeEffects.addElement(newEffects.elementAt(var1));
					}

					newEffects.removeAllElements();
				}
			}
		}
	}

	public final void sub_6de(Unit var1)
	{
		int var2 = var1.currentY + 24;
		createSimpleSparkSprite(sprSmoke, var1.currentX, var2 - sprSmoke.height, 0, -2, 1, 100);

		for(int var3 = 0; var3 < 5; ++var3)
		{
			createSimpleSparkSprite(sprBSmoke, var1.currentX, var2 - sprBSmoke.height, -2 + var3, Renderer.randomFromRange(-4, -1), 1, 50 + 50 * Renderer.randomToRange(4));
		}

		createSimpleSparkSprite(sprSpark, var1.currentX, var1.currentY, 0, 0, 1, 100);
	}

	public final void sub_726(Unit var1, byte var2)
	{
		var_1774 = var1;
		var_17b3 = var2;
		createSimpleSparkSprite(sprSpark, var1.currentX - 8, var1.currentY - 8, 1, 1, 3, 50);
		createSimpleSparkSprite(sprSpark, var1.currentX + 8, var1.currentY - 8, -1, 1, 3, 50);
		createSimpleSparkSprite(sprSpark, var1.currentX - 8, var1.currentY + 8, 1, -1, 3, 50);
		createSimpleSparkSprite(sprSpark, var1.currentX + 8, var1.currentY + 8, -1, -1, 3, 50);
		var_17c8 = delayCounter;
	}

	public final void sub_768()
	{
		if(var_31be == null)
		{
			moveMapOneStep(sprCursor.currentX + 12, sprCursor.currentY + 12);
		}
		else
		{
			moveMapOneStep(var_31be.currentX + 12, var_31be.currentY + 12);
		}
	}

	public final boolean isMapAtPositionPixels(int var1, int var2)
	{
		return mapX == getNormalizedMapX(var1) && mapY == getNormalizedMapY(var2);
	}

	public final boolean isMapAtPosition(int var1, int var2)
	{
		return isMapAtPositionPixels(var1 * 24 + 12, var2 * 24 + 12);
	}

	public final int getNormalizedMapX(int var1)
	{
		int var2;

		if(mapWidthPixels > width_)
		{
			var2 = halfWidth_ - var1;

			if(var2> 0)
			{
				var2 = 0;
			}
			else if(var2 < width_ - mapWidthPixels)
			{
				var2 = width_ - mapWidthPixels;
			}
		}
		else
		{
			var2 = (width_ - mapWidthPixels) / 2;
		}

		return var2;
	}

	public final int getNormalizedMapY(int var1)
	{
		int var2;
		if(mapHeightPixels > height_)
		{
			if((var2 = halfHeight_ - var1) > 0)
			{
				var2 = 0;
			}
			else if(var2 < height_ - mapHeightPixels)
			{
				var2 = height_ - mapHeightPixels;
			}
		}
		else
		{
			var2 = (height_ - mapHeightPixels) / 2;
		}

		return var2;
	}

	public final void moveMapShowPointPixels(int var1, int var2)
	{
		mapX = getNormalizedMapX(var1);
		mapY = getNormalizedMapY(var2);
	}

	public final void moveMapShowPoint(int var1, int var2)
	{
		moveMapShowPointPixels(var1 * 24 + 12, var2 * 24 + 12);
	}

	public final void moveMapOneStep(int var1, int var2)
	{
		var_326f = true;

		int mapNormX = getNormalizedMapX(var1);
		int mapNormY = getNormalizedMapY(var2);

		int mapDeltaX = mapNormX - mapX;
		int mapDeltaY = mapNormY - mapY;

		int delta;

		if(mapDeltaX != 0)
		{
			delta = mapDeltaX / 2;

			if(mapDeltaX < 0)
			{
				if(delta > -mapStepMin)
				{
					delta = -mapStepMin;
				}
				else if(delta < -mapStepMax)
				{
					delta = -mapStepMax;
				}
			}
			else if(delta < mapStepMin)
			{
				delta = mapStepMin;
			}
			else if(delta > mapStepMax)
			{
				delta = mapStepMax;
			}

			mapX += delta;
			var_326f = false;
		}

		if(mapDeltaY != 0)
		{
			delta = mapDeltaY / 2;

			if(mapDeltaY < 0)
			{
				if(delta > -mapStepMin)
				{
					delta = -mapStepMin;
				}
				else if(delta < -mapStepMax)
				{
					delta = -mapStepMax;
				}
			}
			else if(delta < mapStepMin)
			{
				delta = mapStepMin;
			}
			else if(delta > mapStepMax)
			{
				delta = mapStepMax;
			}

			mapY += delta;
			var_326f = false;
		}

	}

	public final void moveCursor(int var1, int var2)
	{
		cursorMapX = var1;
		cursorMapY = var2;
		sprCursor.setPosition(var1 * 24, var2 * 24);
		currentUnitUnderCursor = getUnit(cursorMapX, cursorMapY, (byte)0);
		tileIconNeedsRepaint = true;
	}

	public final void paintMap(Graphics g)
	{
		int mStartX = -mapX / 24;
		int mStartY = -mapY / 24;
		
		if(mStartX < 0)
		{
			mStartX = 0;
		}
		
		if(mStartY < 0)
		{
			mStartY = 0;
		}

		int mEndX = (width_ - mapX - 1) / 24;
		int mEndY = (height_ - mapY - 1) / 24;

		if(mEndX >= mapWidth)
		{
			mEndX = mapWidth - 1;
		}
		
		if(mEndY >= mapHeight)
		{
			mEndY = mapHeight - 1;
		}

		int tStartX;
		
		if(mapX < 0)
		{
			tStartX = mapX % 24;
		}
		else
		{
			tStartX = mapX;
		}

		int ty;
		
		if(mapY < 0)
		{
			ty = mapY % 24;
		}
		else
		{
			ty = mapY;
		}

		byte alphaType = 0;
		
		if(showAtackRange)
		{
			alphaType = 1;
		}

		for(int my = mStartY; my <= mEndY; ++my)
		{
			int tx = tStartX;

			for(int mx = mStartX; mx <= mEndX; ++mx)
			{
				byte tile = mapData[mx][my];

				if(!paintMapAlphaDataFlag || mapAlphaData[mx][my] == 0 || alphaWindowSize > 0)
				{
					sfmTiles[tile].paint(g, tx, ty);
				}

				if(paintMapAlphaDataFlag && mapAlphaData[mx][my] > 0)
				{
					if(alphaWindowSize != 0)
					{
						g.clipRect(tx + alphaWindowSize, ty + alphaWindowSize, 24 - alphaWindowSize * 2, 24 - alphaWindowSize * 2);
					}

					g.drawImage(alphaCoveredTiles[alphaType][tile], tx, ty, 0);

					if(alphaWindowSize != 0)
					{
						g.setClip(0, 0, width_, height_);
					}
				}

				int var12;
				if((var12 = my + 1) < mapHeight && tilesTypes[mapData[mx][var12]] == 9)
				{
					sfmTiles[28].paint(g, tx, ty);
				}

				tx += 24;
			}

			ty += 24;
		}

	}

	public final void drawLoadingString(Graphics g)
	{
		g.setFont(Renderer.fontA);
		g.setColor(0);
		g.fillRect(0, 0, super.width, super.height);
		g.setColor(16777215);
		Renderer.drawString(g, PaintableObject.getLocaleString(58), super.width / 2, (super.height - Renderer.fontA.getHeight()) / 2, 17);
	}

	public static final int blendColors(int color1, int color2, int factor, int divisor)
	{
		if(factor == 0)
		{
			return color1;
		}
		else if(factor == divisor)
		{
			return color2;
		}
		else
		{
			int r1 = color1 & 16711680;
			int g1 = color1 & '\uff00';
			int b1 = color1 & 255;

			int r2 = (((color2 & 16711680) - r1) * factor / divisor & 16711680) + r1;
			int g2 = (((color2 & '\uff00') - g1) * factor / divisor & '\uff00') + g1;
			int b2 = ((color2 & 255) - b1) * factor / divisor + b1;

			return r2 | g2 | b2;
		}
	}

	public static final int sub_a35(int var0, int var1, int var2)
	{
		int var3 = (var0 & 16711680) * var1 / var2 & 16711680;
		int var4 = (var0 & '\uff00') * var1 / var2 & '\uff00';
		int var5 = (var0 & 255) * var1 / var2;
		return var3 | var4 | var5;
	}

	public final void setIntroMode(int var1, int var2, int var3)
	{
		var_34b0 = var2;
		var_34ec = var3;
		var_3491 = mode;

		try
		{
			sprIntro = new SpriteFrame("intro" + var1, width_, -1);
		}
		catch(Exception var4)
		{
			;
		}

		lineHeight = Renderer.fontBHeight;
		if(sprIntro != null)
		{
			introHeight = sprIntro.imgheight;
			combatDrapValue = 0;
			linesOnScreen = (super.height - sprIntro.imgheight) / lineHeight;
		}
		else
		{
			introHeight = 0;
			combatDrapValue = 16;
			linesOnScreen = super.height / lineHeight;
		}

		textLines = PaintableObject.splitStringMultiline(PaintableObject.getLocaleString(215 + var1), super.width, Renderer.fontB);
		var_3308 = super.height - lineHeight;
		var_3395 = 0;
		var_3437 = false;
		PaintableObject.currentRenderer.clearKeyStates();
		mode = 3;
	}

	public final void sub_a6b(String var1)
	{
		var_3491 = mode;
		textLines = PaintableObject.splitStringMultiline(var1, super.width, Renderer.fontB);
		combatDrapValue = 16;
		var_3437 = false;
		var_34b0 = 3;
		var_34ec = 3;
		introHeight = 0;
		lineHeight = Renderer.fontBHeight;
		linesOnScreen = super.height / lineHeight;
		var_3308 = super.height - lineHeight;
		var_3395 = 0;
		PaintableObject.currentRenderer.clearKeyStates();
		mode = 3;
	}

	public final void updateIntro()
	{
		if(var_3437)
		{
			--combatDrapValue;

			if(combatDrapValue < 0)
			{
				combatDrapValue = 0;
				mode = var_3491;
				sprIntro = null;
				textLines = null;
				return;
			}
		}
		else
		{
			if((var_34b0 != 2 || combatDrapValue >= 40) && (var_34b0 == 2 || combatDrapValue >= 16))
			{
				--var_3308;
				if(var_3308 < introHeight)
				{
					var_3308 = introHeight + lineHeight - (introHeight - var_3308);
					++var_3395;
				}
			}
			else
			{
				++combatDrapValue;
			}

			if(var_3395 >= textLines.length || PaintableObject.currentRenderer.isKeyPressed(KEY_LSK))
			{
				var_3437 = true;
				if(sprIntro != null || var_3395 < textLines.length)
				{
					if(var_34ec == 2)
					{
						combatDrapValue = 40;
						return;
					}

					combatDrapValue = 16;
					return;
				}

				combatDrapValue = 0;
			}
		}

	}

	public final void paintIntro(Graphics g)
	{
		g.setFont(Renderer.fontB);
		g.setColor(0);
		g.fillRect(0, 0, super.width, super.height);
		int var2;

		if(sprIntro != null)
		{
			if(!var_3437 && (var_34b0 == 2 && combatDrapValue >= 40 || var_34b0 != 2 && combatDrapValue >= 16))
			{
				sprIntro.paint(g, 0, 0);
			}
			else if((var_34ec != 2 || !var_3437) && (var_34b0 != 2 || var_3437))
			{
				if((var_34ec != 3 || !var_3437) && (var_34b0 != 3 || var_3437))
				{
					sprIntro.paint(g, 0, 0);

					if(var_3437)
					{
						if(var_34ec == 1 && combatDrapValue <= 16)
						{
							drawDrap(g, 0, 16 - combatDrapValue, 16, 0, 0, 0, super.width, super.height);
						}
					}
					else if(var_34b0 == 0)
					{
						if(combatDrapValue <= 16)
						{
							drawDrap(g, 16777215, combatDrapValue, 16, 1, 0, 0, super.width, super.height);
						}
					}
					else if(var_34b0 == 1 && combatDrapValue <= 16)
					{
						drawDrap(g, 0, combatDrapValue, 16, 1, 0, 0, super.width, sprIntro.imgheight);
					}
				}
				else
				{
					sprIntro.paint(g, 0, 0);
					var2 = 255 * (16 - combatDrapValue) / 16;
					Renderer.fillAlphaRect(g, var2 << 24, 0, 0, sprIntro.imgwidth, sprIntro.imgheight);
				}
			}
			else
			{
				drawWavedImage(g, combatDrapValue, 40, 0, sprIntro, 0, 0, 2);
			}
		}

		g.setClip(0, 0, super.width, super.height);
		var2 = combatDrapValue;
		if(var_3437 && var_34ec == 2)
		{
			var2 -= 24;
			if(var2 < 0)
			{
				var2 = 0;
			}
		}

		int var3 = var_3308;

		for(int i = var_3395; i < var_3395 + linesOnScreen && i < textLines.length && var3 < super.height - lineHeight; ++i)
		{
			int var5 = lineHeight;
			if(var3 < introHeight + lineHeight)
			{
				var5 = var3 - introHeight;
			}
			else if(var3 + lineHeight > super.height - lineHeight)
			{
				var5 = super.height - lineHeight - var3;
			}

			int var6;
			if(var5 < lineHeight)
			{
				var6 = sub_a35(14672074, var5, lineHeight);
			}
			else
			{
				var6 = 14672074;
			}

			if(var_3437)
			{
				var6 = blendColors(0, var6, var2, 16);
			}

			g.setColor(var6);
			Renderer.drawString(g, textLines[i], super.halfWidth, var3 + 3, 17);
			var3 += lineHeight;
		}

		if(!var_3437)
		{
			drawSoftButton(g, KEY_LSK, 2, super.height);
		}

	}

	public final void paintIntroTransition(Graphics g)
	{
		if(introTransMode == 0)
		{
			g.setColor(16777215);
			g.fillRect(0, 0, super.width, super.height);
			drawWavedImage(g, combatDrapValue, 40, 0, sfmLogo, (super.width - sfmLogo.imgwidth) / 2, (super.height - sfmLogo.imgheight) / 2, 4);
		}
		else if(introTransMode == 1)
		{
			g.setColor(16777215);
			g.fillRect(0, 0, super.width, super.height);
			drawWavedImage(g, combatDrapValue, 40, 0, sfmLogo, (super.width - sfmLogo.imgwidth) / 2, (super.height - sfmLogo.imgheight) / 2, 4);
		}
		else if(introTransMode == 3)
		{
			g.setColor(0);
			g.fillRect(0, 0, super.width, super.height);
			drawWavedImage(g, combatDrapValue, 40, 0, sfmGameLogo, 0, 0, 1);
			g.setClip(0, 0, super.width, super.height);
		}
		else
		{
			if(introTransMode == 4)
			{
				if(combatDrapValue >= 16)
				{
					if(sfmSplash != null)
					{
						sfmGameLogo.paint(g, 0, 0);

						if(glowX != -1)
						{
							int count = (sfmGameLogo.imgheight + sfmGlow.imgheight - 1) / sfmGlow.imgheight;

							for(int i = 0; i < count; i++)
							{
								sfmGlow.paint(g, 4 + glowX - 38 * i, 6 + sfmGlow.imgheight * i);
							}
						}

						sfmSplash.paint(g, 0, 0);
					}
					else
					{
						g.setColor(0);
						g.fillRect(0, 0, super.width, super.height);
						sfmGameLogo.paint(g, 0, 0);
					}

					if(paintPressAnyKeyFlag && sfmSplash != null)
					{
						g.setColor(16777215);
						g.setFont(Renderer.fontA);
						drawOutlinedString(g, PaintableObject.getLocaleString(59), super.halfWidth, super.height - sprButtons.height - 1, 33, 16777215, 0);
						return;
					}
				}
				else
				{
					if(sfmSplash != null)
					{
						sfmSplash.paint(g, 0, 0);
					}
					else
					{
						g.setColor(0);
						g.fillRect(0, 0, super.width, super.height);
					}

					Renderer.fillAlphaRect(g, 255 * (16 - combatDrapValue) / 16 << 24, 0, 0, super.width, super.height);
					sfmGameLogo.paint(g, 0, 0);
				}
			}

		}
	}

	public static final void drawOutlinedString(Graphics g, String text, int x, int y, int anchor, int textColor, int outlineColor)
	{
		g.setColor(outlineColor);
		g.drawString(text, x - 1, y - 1, anchor);
		g.drawString(text, x - 1, y, anchor);
		g.drawString(text, x - 1, y + 1, anchor);
		g.drawString(text, x, y - 1, anchor);
		g.drawString(text, x, y + 1, anchor);
		g.drawString(text, x + 1, y + 1, anchor);
		g.drawString(text, x + 1, y, anchor);
		g.drawString(text, x + 1, y - 1, anchor);


		g.setColor(textColor);
		g.drawString(text, x, y, anchor);
	}

	public final void paint(Graphics g)
	{
		int var2;
		int var3;

		if(mode == 4)
		{
			g.setColor(16777215);
			g.fillRect(0, 0, super.width, super.height);
			g.setFont(Renderer.fontA);
			g.setColor(0);
			g.drawString(PaintableObject.getLocaleString(58), super.width / 2, super.height / 2 - 1, 33);
			if((var2 = super.height / 18) < 12)
			{
				var2 = 12;
			}

			var3 = super.height / 2 + 1;
			g.setColor(13553358);
			AuxDisplayable.drawRoundRect(g, 1, var3, super.width - 2, var2);
			g.setColor(2370117);
			AuxDisplayable.drawRoundRect(g, 2, var3 + 2, loadProgress * (super.width - 6) / 100, var2 - 4);
		}
		else if(mode == 2)
		{
			paintCombatAnimation(g);
		}
		else if(mode == 3)
		{
			paintIntro(g);
		}
		else if(paintLoadingStringFlag)
		{
			drawLoadingString(g);
		}
		else if(var_19c3)
		{
			if(combatDrapValue >= 16)
			{
				if(gameState != 11 && gameState != 10)
				{
					drawLoadingString(g);
				}
				else
				{
					g.setColor(0);
					g.fillRect(0, 0, super.width, super.height);
				}
			}
			else
			{
				drawDrap(g, 0, combatDrapValue, 16, var_1a23, 0, 0, super.width, super.height);
			}
		}
		else
		{
			if(mode == 0)
			{
				paintIntroTransition(g);
			}
			else if(gameState == 14)
			{
				g.setClip(0, 0, super.width, super.height);
				g.setColor(0);
				g.fillRect(0, 0, super.width, super.height);
			}
			else if(gameState == 10 && var_3e0c >= 1)
			{
				drawLoadingString(g);
			}
			else if(gameState == 11 && !var_19c3)
			{
				String var17 = PaintableObject.getLocaleString(57);
				g.setClip(0, 0, super.width, super.height);
				g.setFont(Renderer.fontA);
				g.setColor(0);
				g.fillRect(0, 0, super.width, super.height);

				if(skirmishMode == 0)
				{
					g.setColor(16777215);

					if(sfmGameOver != null)
					{
						sfmGameOver.paintEx(g, super.halfWidth, super.halfHeight, 3);
						Renderer.drawString(g, var17, super.halfWidth, super.height - 2, 33);
					}
					else
					{
						var3 = super.halfHeight - Renderer.fontA.getHeight() / 2;
						Renderer.drawString(g, var17, super.halfWidth, var3, 17);
					}
				}
			}
			else
			{
				g.setClip(0, 0, width_, height_);

				if(mapWidthPixels < width_ || mapHeightPixels < height_)
				{
					g.setColor(0);
					g.fillRect(0, 0, width_, height_);
				}

				if(shakeMapFlag)
				{
					var2 = Renderer.random() % 10;
					var3 = Renderer.random() % 4;
					g.translate(var2, var3);
					paintMap(g);
					g.translate(-var2, -var3);
				}
				else
				{
					paintMap(g);
				}

				var2 = 0;

				for(var3 = units.size(); var2 < var3; ++var2)
				{
					Unit unit = (Unit)units.elementAt(var2);

					if(unit.unitState == 3)
					{
						sprTombStone.paint(g, mapX + unit.currentX, mapY + unit.currentY);
					}
					else if(unit != currentSelectedUnit)
					{
						unit.paint(g, mapX, mapY);
					}
				}

				var2 = 0;

				for(var3 = units.size(); var2 < var3; ++var2)
				{
					((Unit)units.elementAt(var2)).drawHealthString(g, mapX, mapY);
				}

				int var5;
				int var6;
				int var7;
				int var18;

				if(currentRoute != null)
				{
					g.setColor(14745682);
					var3 = 12 + var_3685 / 4;
					var18 = 24 - var3;
					var5 = 0;

					for(var6 = currentRoute.size(); var5 < var6; ++var5)
					{
						short[] var16;
						var7 = (var16 = (short[])currentRoute.elementAt(var5))[0] * 24 + mapX;
						int var8 = var16[1] * 24 + mapY;
						int var9 = var7 + 12;
						int var10 = var8 + 12;
						boolean var11 = false;
						boolean var12 = false;
						short[] var13;
						if(var5 != 0)
						{
							if((var13 = (short[])currentRoute.elementAt(var5 - 1))[0] == var16[0] + 1)
							{
								g.fillRect(var7 + var18, var10 - var_3695, var3, var_3685);
							}
							else if(var13[0] == var16[0] - 1)
							{
								g.fillRect(var7, var10 - var_3695, var3, var_3685);
							}
							else if(var13[1] == var16[1] + 1)
							{
								g.fillRect(var9 - var_3695, var8 + var18, var_3685, var3);
							}
							else if(var13[1] == var16[1] - 1)
							{
								g.fillRect(var9 - var_3695, var8, var_3685, var3);
							}
						}

						if(var5 == var6 - 1)
						{
							g.setClip(0, 0, width_, height_);
							sprCursorCopy.paintCurrentFrame(g, var9, var10, 3);
						}
						else if((var13 = (short[])currentRoute.elementAt(var5 + 1))[0] == var16[0] + 1)
						{
							g.fillRect(var7 + var18, var10 - var_3695, var3, var_3685);
						}
						else if(var13[0] == var16[0] - 1)
						{
							g.fillRect(var7, var10 - var_3695, var3, var_3685);
						}
						else if(var13[1] == var16[1] + 1)
						{
							g.fillRect(var9 - var_3695, var8 + var18, var_3685, var3);
						}
						else if(var13[1] == var16[1] - 1)
						{
							g.fillRect(var9 - var_3695, var8, var_3685, var3);
						}
					}
				}

				if(currentSelectedUnit != null)
				{
					currentSelectedUnit.paint(g, mapX, mapY);
					currentSelectedUnit.drawHealthString(g, mapX, mapY);
				}

				if(drawCursorFlag)
				{
					sprCursor.paintCurrentFrame(g, mapX + 12, mapY + 12, 3);
				}

				var2 = 0;

				for(var3 = activeEffects.size(); var2 < var3; ++var2)
				{
					Sprite var14 = (Sprite)activeEffects.elementAt(var2);
					var14.paint(g, mapX, mapY + var14.bounceValue);
				}

				g.setClip(0, 0, super.width, super.height);
				var2 = super.height - FOOTER_HEIGHT;

				if(statusBarOffset > 0)
				{
					AuxDisplayable.drawMenuFrame(g, 0, var2, width_, FOOTER_HEIGHT, 14);
					g.setClip(0, 0, super.width, super.height);
				}

				var3 = FOOTER_HEIGHT - 24 >> 1;
				var18 = 24 + var3 * 2;
				var5 = super.width - var18;
				var2 += statusBarOffset;

				if(statusBarNeedsRepaint)
				{
					statusBarNeedsRepaint = false;

					var6 = super.height - FOOTER_HEIGHT / 2 + statusBarOffset;

					byte var19 = 10;
					if(width_ <= 120)
					{
						var19 = 4;
					}

					if(levelEditorMode == 1)
					{
						AuxDisplayable.drawMenuFrameEx(g, 0, var2, var5 + 1, FOOTER_HEIGHT, 0, 2370117, FRACTION_COLORS[0], statusBarOffset, FOOTER_HEIGHT);
						Renderer.drawAlignedGraphicString(g, (currentTile + 1) + "/" + sfmTiles.length, var19 + 1, var6, 1, 6);
					}
					else if(levelEditorMode == 2)
					{
						AuxDisplayable.drawMenuFrameEx(g, 0, var2, var5 + 1, FOOTER_HEIGHT, 0, 2370117, FRACTION_COLORS[fractionsTurnQueue[currentTurningPlayer]], statusBarOffset, FOOTER_HEIGHT);
						Renderer.drawAlignedGraphicString(g, (currentUnitType + 1) + "/" + 12, var19 + 1, var6, 1, 6);
					}
					else
					{
						AuxDisplayable.drawMenuFrameEx(g, 0, var2, var5 + 1, FOOTER_HEIGHT, 0, 2370117, FRACTION_COLORS[fractionsTurnQueue[currentTurningPlayer]], statusBarOffset, FOOTER_HEIGHT);

						if(skirmishMode == 1)
						{
							var7 = var5 / 2;
							sprHudIcons2.paintFrame(g, 0, var7, var6, 6);
							Renderer.drawAlignedGraphicString(g, countUnits(-1, -1, currentTurningPlayer) - countUnits(10, -1, currentTurningPlayer) + "/" + skirmishUnitCap, var7 + sprHudIcons2.width + 1, var6, 1, 6);
						}

						sprHudIcons2.paintFrame(g, 1, var19, var6, 6);
						var7 = var19 + sprHudIcons2.width + 1;

						if(!Renderer.devmode && playerModes[currentTurningPlayer] != 1)
						{
							Renderer.drawAlignedGraphicString(g, "- - -", var7, var6, 1, 6);
						}
						else
						{
							Renderer.drawAlignedGraphicString(g, "" + money[currentTurningPlayer], var7, var6, 1, 6);
						}
					}

					g.setClip(0, 0, super.width, super.height);
				}

				if(tileIconNeedsRepaint)
				{
					tileIconNeedsRepaint = false;

					if(var3 > 0)
					{
						sub_b8e(g, var5, var2, var18, FOOTER_HEIGHT);
					}

					var6 = var5 + var3;
					var7 = var2 + var3;

					if(levelEditorMode == 0 || levelEditorMode == 2)
					{
						currentTile = mapData[cursorMapX][cursorMapY];
					}

					sfmTiles[currentTile].paint(g, var6, var7);

					if(levelEditorMode == 2)
					{
						if(currentUnit == null)
						{
							currentUnit = Unit.createUnitForLevelEditor(currentUnitType, currentTurningPlayer, 0, 0, false);
						}
						
						currentUnit.paint(g, var6, var7);
					}

					if(levelEditorMode == 0)
					{
						String var15 = "." + TERRAIN_DEFENCE_BONUS[tilesTypes[currentTile]];
						Renderer.drawAlignedGraphicString(g, var15, var6 + 24, var7 + 24, 0, 40);
					}

					if(var3 == 0)
					{
						g.setColor(0);
						g.drawRect(var6, var7, 24, 24);
					}
				}

				if(gameState == 6 && unitsWithinAttackRange[currentAttackTargetUnit].unitState != 4)
				{
					var6 = 0;
					if(cursorMapY * 24 <= height_ / 2 - 24)
					{
						var6 = height_ - sprButtons.height - var_3622 + 2;
					}

					drawCombatHeader(g, currentSelectedUnit, unitsWithinAttackRange[currentAttackTargetUnit], var6);
				}
			}

			if(isActive())
			{
				if(var_17f7)
				{
					drawSoftButton(g, KEY_RSK, 1, height_);
				}

				if(var_1824)
				{
					drawSoftButton(g, KEY_LSK, 0, height_);
				}

				if(mode == 1 && (playerModes[currentTurningPlayer] == 0 || gameState == 0) && gameState != 11)
				{
					drawSoftButton(g, KEY_LSK, 3, height_);
				}
			}

			if(drawAlphaDrap || inverseDrapAlpha)
			{
				var2 = drapAlphaValue;

				if(inverseDrapAlpha)
				{
					var2 = 16 - drapAlphaValue;
				}

				var3 = 255 * var2 / 16;
				Renderer.fillAlphaRect(g, var3 << 24, 0, 0, super.width, super.height);
			}

		}
	}

	public final void sub_b8e(Graphics var1, int var2, int var3, int var4, int var5)
	{
		var1.setColor(4344163);
		var1.fillRect(var2, var3, var4, var5);
		var1.setColor(11384493);
		var1.fillRect(var2 + 1, var3 + 1, var4 - 2, var5 - 2);
		var1.setColor(4344163);
		var1.fillRect(var2 + 3, var3 + 3, var4 - 6, var5 - 6);
	}

	public final void drawCombatHeader(Graphics var1, Unit var2, Unit var3, int var4)
	{
		int var5 = var_3622 - 2;
		var1.setColor(11384493);
		var1.fillRect(0, var4, width_, var5);
		var1.setColor(0);
		var1.fillRect(0, var5 + var4, width_, 2);
		boolean var6 = false;
		int var7 = 0;
		int var9;
		int var10 = (var9 = var5 / 2) + var4;

		for(int part = 0; part < 3; ++part)
		{
			sprHudIcons.paintFrame(var1, part, var7 + 1, var10, 6);
			var7 += sprHudIcons.width + 2;
			int var8 = var4 + 1;
			int var11;

			if(part == 0)
			{
				if(super.width <= 132)
				{
					var11 = 56;
				}
				else
				{
					var11 = 61 * width_ / 176;
				}
			}
			else if(part == 1)
			{
				if(super.width <= 132)
				{
					var11 = 28;
				}
				else
				{
					var11 = 47 * width_ / 176;
				}
			}
			else
			{
				var11 = width_ - var7;
			}

			for(int var14 = 0; var14 < 2; ++var14)
			{
				Unit var15;
				Unit var16;
				if(var14 == 0)
				{
					var15 = var2;
					var16 = var3;
				}
				else
				{
					var15 = var3;
					var16 = var2;
				}

				int var12 = var5 / 2 - 2;
				var1.setColor(2172994);
				var1.fillRect(var7, var8, var11, var12);
				int var17 = var7 + 1;

				if(part == 0 || super.width > 132)
				{
					var1.setColor(FRACTION_COLORS[fractionsTurnQueue[var15.fractionPosInTurnQueue]]);
					var1.fillRect(var17, var8 + 1, 3, var12 - 2);
					var17 += 4;
				}

				int bonus = 0;
				String str = null;

				if(part == 0)
				{
					if(var14 != 0 && !var3.canPerformCloseAttack(var2, var2.currentMapPosX, var2.currentMapPosY))
					{
						str = "0-0";
					}
					else
					{
						bonus = var15.getOffenceBonusAgainstUnit(var16);
						str = var15.offenceMin + bonus + "-" + (var15.offenceMax + bonus);
					}
				}
				else if(part == 1)
				{
					bonus = var15.getDefenceBonusAgainstUnit(var16);
					str = "" + (var15.baseDefence + bonus);
				}
				else
				{
					str = "" + var15.rank;
				}

				Renderer.drawGraphicString(var1, str, var17, var8 + 1, 0);

				if(bonus > 0)
				{
					sprArrowIcons.paintFrame(var1, 1, var17 + 1 + Renderer.getGraphicStringWidth((byte)0, str), var8 + var12 / 2, 6);
				}
				else if(bonus < 0)
				{
					sprArrowIcons.paintFrame(var1, 2, var17 + 1 + Renderer.getGraphicStringWidth((byte)0, str), var8 + var12 / 2, 6);
				}

				int var20 = var7 + var11 - 2;

				if(part == 0 && (var15.effectState & 2) != 0)
				{
					sprStatus.paintFrame(var1, 1, var20, var8 + var12 / 2, 10);
					var20 -= sprStatus.width;
				}

				if((part == 0 || part == 1) && (var15.effectState & 1) != 0)
				{
					sprStatus.paintFrame(var1, 0, var20, var8 + var12 / 2, 10);
				}

				var8 += var9;
			}

			var7 += var11;
		}

	}

	public final void drawSoftButton(Graphics var1, int var2, int var3, int var4)
	{
		int var5 = 0;
		byte var6 = 0;
		if(var2 == 1024)
		{
			var6 = 36;
		}
		else if(var2 == 2048)
		{
			var5 = super.width;
			var6 = 40;
		}

		sprButtons.paintFrame(var1, var3, var5, var4, var6);
	}

	public final Unit getUnit(int var1, int var2, byte var3)
	{
		int i = 0;

		for(int count = units.size(); i < count; ++i)
		{
			int var4;
			int var5;

			Unit unit = (Unit)units.elementAt(i);
			
			if(unit.unitState == 1)
			{
				var4 = unit.var_3f2;
				var5 = unit.var_408;
			}
			else
			{
				var4 = unit.currentMapPosX;
				var5 = unit.currentMapPosY;
			}

			if(var1 == var4 && var2 == var5)
			{
				if(var3 == 0)
				{
					if(unit.unitState != 3)
					{
						return unit;
					}
				}
				else if(var3 == 1 && unit.unitState == 3)
				{
					return unit;
				}
			}
		}

		return null;
	}

	public final byte getTileType(int var1, int var2)
	{
		return tilesTypes[mapData[var1][var2]];
	}

	public final void endTurn()
	{
		Renderer.stopCurrentPlayer();
		var_12cb = 0;
		statusBarOffset = 0;
		gameState = 8;
		var_12ff = delayCounter;
	}

	public final void sub_cff()
	{
		if(levelEditorMode != 0)
		{
			return;
		}
		
		fractionKingPositions[currentTurningPlayer][0] = (byte)cursorMapX;
		fractionKingPositions[currentTurningPlayer][1] = (byte)cursorMapY;

		++currentTurn;
		currentTurningPlayer = (byte)((currentTurningPlayer + 1) % turnQueueLength);

		if(playerModes[currentTurningPlayer] == 2)
		{
			sub_cff();
		}
		else
		{
			int var1;
			for(var1 = units.size() - 1; var1 >= 0; --var1)
			{
				Unit unit;
				if((unit = (Unit)units.elementAt(var1)).unitState == 3)
				{
					if(unit.type != 9 && (unit.var_758 = (byte)(unit.var_758 - 1)) <= 0)
					{
						unit.removeThisUnit();
					}
				}
				else
				{
					unit.unitState = 0;
					if((unit.effectState & 1) != 0 && unit.var_79f == currentTurningPlayer)
					{
						unit.removeEffect((byte)1);
					}

					if(unit.fractionPosInTurnQueue == currentTurningPlayer)
					{
						unit.removeEffect((byte)2);
					}

					unit.var_8f7 = 0;
				}
			}

			currentIncome = 0;

			for(var1 = 0; var1 < mapData.length; ++var1)
			{
				for(int var3 = 0; var3 < mapData[var1].length; ++var3)
				{
					if(isBuildingOfFraction(var1, var3, currentTurningPlayer))
					{
						if(getTileType(var1, var3) == 8)
						{
							currentIncome += 30;
						}
						else if(getTileType(var1, var3) == 9)
						{
							currentIncome += 50;
						}
					}
				}
			}

			money[currentTurningPlayer] += currentIncome;

			for(var1 = 0; var1 < buildingData.length; ++var1)
			{
				var_399e[var1] = 0;
			}

			if(playerModes[currentTurningPlayer] == 1)
			{
				moveCursor(fractionKingPositions[currentTurningPlayer][0], fractionKingPositions[currentTurningPlayer][1]);
			}

			cursorPositionChanged = true;
			statusBarNeedsRepaint = true;
			if(playerModes[currentTurningPlayer] == 0)
			{
				sub_1022();
			}
			else
			{
				Unit.speed = Unit.UNIT_SPEED_SLOW;
			}

			if(countUnits(-1, 0, currentTurningPlayer) <= 0 && countCastles(currentTurningPlayer) == 0)
			{
				sub_cff();
			}

		}
	}

	public final boolean sub_d41(int var1, int var2, Unit var3)
	{
		return var3.hasProperty((short)8) && getTileType(var3.currentMapPosX, var3.currentMapPosY) == 8 && !isFractionBuilding(mapData[var3.currentMapPosX][var3.currentMapPosY]);
	}

	public final boolean sub_d5e(int var1, int var2, Unit var3)
	{
		return var3.hasProperty((short)8) && getTileType(var3.currentMapPosX, var3.currentMapPosY) == 8 && isFractionBuilding(mapData[var3.currentMapPosX][var3.currentMapPosY]) && !sub_e68(var3.currentMapPosX, var3.currentMapPosY, playerTeams[var3.fractionPosInTurnQueue]) ? true : var3.hasProperty((short)16) && getTileType(var3.currentMapPosX, var3.currentMapPosY) == 9 && !sub_e68(var3.currentMapPosX, var3.currentMapPosY, playerTeams[var3.fractionPosInTurnQueue]);
	}

	public final void setMapElement(byte var1, int var2, int var3)
	{
		mapData[var2][var3] = var1;
	}

	public final void setBuildingFraction(int var1, int var2, int var3)
	{
		if(isFractionBuilding(mapData[var1][var2]))
		{
			setMapElement((byte)(FRACTION_BUILDINGS + var3 * 2 + (mapData[var1][var2] - FRACTION_BUILDINGS) % 2), var1, var2);
		}
	}

	public final boolean isBuilding(byte tile)
	{
		return (tile >= FRACTION_BUILDINGS && tile < CUSTOM_TILES) || tile == BROKEN_BUILDING;
	}

	public final boolean isFractionBuilding(byte tile)
	{
		return tile >= FRACTION_BUILDINGS && tile < CUSTOM_TILES;
	}

	public final int getBuildingFraction(int var1, int var2)
	{
		return getBuildingFractionEx(var1, var2, mapData);
	}

	public final int getBuildingFractionEx(int var1, int var2, byte[][] var3)
	{
		int fraction = (var3[var1][var2] - FRACTION_BUILDINGS) >> 1;
		return (fraction >= 0 && fraction <= 4) ? fraction : -1;
	}

	public static final int arraySearchBin(byte[] array, byte x)
	{
		int first = 0;
		int last = array.length;

		if(x < array[first] || x > array[last])
		{
			return -1;
		}

		int mid;

		while(first < last)
		{
			mid = (first + last) >> 1;

			if(x <= array[mid])
			{
				last = mid;
			}
			else
			{
				first = mid + 1;
			}
		}

		if(array[last] == x)
		{
			return last;
		}
		else
		{
			return -1;
		}
	}

	public final int getFractionPosInTurnQueue(int var1)
	{
		return var1 != -1 && var1 != 0 ? fractionsPosInTurnQueue[var1] : -1;
	}

	public final boolean sub_e68(int var1, int var2, int var3)
	{
		int var4 = getFractionPosInTurnQueue(getBuildingFraction(var1, var2));
		return var4 > -1 ? var3 == playerTeams[var4] : false;
	}

	public final boolean isBuildingOfFraction(int var1, int var2, int var3)
	{
		return getBuildingFraction(var1, var2) == fractionsTurnQueue[var3];
	}

	public final int countCastles(int var1)
	{
		int var2 = 0;

		for(int var3 = 0; var3 < castleCount; ++var3)
		{
			if(isBuildingOfFraction(castleData[var3][0], castleData[var3][1], var1))
			{
				++var2;
			}
		}

		return var2;
	}

	public final int countUnits(int type, int state, byte turnpos)
	{
		int var5 = 0;
		int i = 0;

		for(int count = units.size(); i < count; ++i)
		{
			Unit unit = (Unit)units.elementAt(i);

			if((type == -1 || unit.type == type) && (state == -1 && unit.unitState != 3 || state == unit.unitState) && (turnpos == -1 || unit.fractionPosInTurnQueue == turnpos))
			{
				++var5;
			}
		}

		return var5;
	}

	public final Unit[] listUnits(int var1, int var2, byte var3)
	{
		Vector var4 = new Vector();
		int var6 = 0;

		for(int var7 = units.size(); var6 < var7; ++var6)
		{
			Unit var5 = (Unit)units.elementAt(var6);
			if((var1 == -1 || var5.type == var1) && (var2 == -1 && var5.unitState != 3 || var2 == var5.unitState) && (var3 == -1 || var5.fractionPosInTurnQueue == var3))
			{
				var4.addElement(var5);
			}
		}

		Unit[] var8 = new Unit[var4.size()];
		var4.copyInto(var8);
		return var8;
	}

	public static final void drawWavedImage(Graphics g, int var1, int var2, int dir, SpriteFrame img, int x, int y, int var7)
	{
		int sizeAlong;
		int sizeAcross;

		if(dir == 0)
		{
			sizeAlong = img.imgwidth;
			sizeAcross = img.imgheight;
		}
		else
		{
			sizeAlong = img.imgheight;
			sizeAcross = img.imgwidth;
		}

		int var10 = sizeAlong / 2;
		int var11 = sizeAcross / 1;
		int frameSize = sizeAlong * var1 / var2;
		int magnitude = sizeAlong * (var2 - var1) / (var2 * 4);
		int angle = 360 * var1 / var2;
		int angstep = 360 * var7 / var11;

		for(int i = 0; i < var11; ++i)
		{
			int offset = magnitude * PaintableObject.sin(angle) >> 10;

			if(dir == 0)
			{
				g.setClip(x + var10 - frameSize / 2 + offset, y + i, frameSize, 1);
				img.paint(g, x + offset, y);
			}
			else
			{
				g.setClip(x + i, y + var10 - frameSize / 2 + offset, 1, frameSize);
				img.paint(g, x, y + offset);
			}

			angle += angstep;
		}

	}

	public static final void drawDrap(Graphics g, int color, int var2, int var3, int var4, int x, int y, int width, int height)
	{
		int segmentWidthMax = width / 8 + 1;
		int segmentHeight = height;
		int var12 = var3 - 7;

		for(int i = 0; i < 8; ++i)
		{
			int var18 = var2 - i;

			if(var18 < 0)
			{
				var18 = 0;
			}

			int segmentWidth;

			if(var18 >= var12)
			{
				segmentWidth = segmentWidthMax;
			}
			else
			{
				segmentWidth = segmentWidthMax * var18 / var12;
			}

			int offset;

			if(var4 == 1)
			{
				offset = i * segmentWidthMax + segmentWidth;
				segmentWidth = segmentWidthMax - segmentWidth;
			}
			else
			{
				offset = i * segmentWidthMax;
			}

			int alpha = 255 * segmentWidth / segmentWidthMax;
			Renderer.setColor(g, alpha << 24 | color);

			for(int var21 = 0; var21 < 1; ++var21)
			{
				g.fillRect(x + offset, y, segmentWidth, segmentHeight);
			}
		}

	}

	public final boolean canBuyUnit(byte var1, int var2, int var3)
	{
		if(skirmishUnitCap > countUnits(-1, -1, currentTurningPlayer) - countUnits(10, -1, currentTurningPlayer) && var1 <= allowedUnits && Unit.UNIT_COST[var1] <= money[currentTurningPlayer] && Unit.UNIT_COST[var1] > 0)
		{
			setArrayValuesEx(mapAlphaData, 0);
			return Unit.fillMoveRangeDataEx(mapAlphaData, var2, var3, Unit.UNIT_MOVE_RANGE[var1], -1, var1, currentTurningPlayer, true);
		}
		else
		{
			return false;
		}
	}

	public final boolean canBuyUnit(Unit var1, int var2, int var3)
	{
		if(skirmishUnitCap > countUnits(-1, -1, currentTurningPlayer) - countUnits(10, -1, currentTurningPlayer) && (var1.type <= allowedUnits || var1.type == 9) && var1.cost <= money[currentTurningPlayer])
		{
			setArrayValuesEx(mapAlphaData, 0);
			return Unit.fillMoveRangeDataEx(mapAlphaData, var2, var3, Unit.UNIT_MOVE_RANGE[var1.type], -1, var1.type, currentTurningPlayer, true);
		}
		else
		{
			return false;
		}
	}

	public final void sub_1022()
	{
		Unit[] var1 = listUnits(-1, 0, currentTurningPlayer);
		var_3aad = new Vector(var1.length);
		int var2 = 0;

		while(var2 < var1.length)
		{
			int var3 = 0;

			while(true)
			{
				if(var3 < var2)
				{
					Unit var4 = (Unit)var_3aad.elementAt(var3);
					byte var5 = var_36cf[var4.type];
					byte var6;
					if((var6 = var_36cf[var1[var2].type]) >= var5 && (var6 != var5 || var1[var2].health >= var4.health))
					{
						++var3;
						continue;
					}

					var_3aad.insertElementAt(var1[var2], var3);
				}

				if(var3 == var2)
				{
					var_3aad.addElement(var1[var2]);
				}

				++var2;
				break;
			}
		}

		var_3903 = new Unit[buildingData.length];
		var_3947 = new byte[buildingData.length];
		Unit.speed = Unit.UNIT_SPEED_FAST;
		var_3864 = 0;
		var_36e4 = 0;
	}

	public final void sub_1043()
	{
		if(PaintableObject.currentRenderer.wasKeyPressed(KEY_LSK))
		{
			createCircleMenu(MAIN_MENU_ITEMS, halfHeight_, height_, this);
			PaintableObject.currentRenderer.clearKeyStates();
		}
		else if(!var_3aed)
		{
			if(var_36e4 == 4)
			{
				if(var_37c2 == null && var_37e4 == null)
				{
					int var18;

					if(sub_d5e(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY, currentSelectedUnit))
					{
						var18 = sub_110d(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY);

						if(var_3a5c != -1 && var_3a5c != var18)
						{
							var_3903[var_3a5c] = var_3903[var18];
							var_3903[var18] = currentSelectedUnit;
						}

						setBuildingFraction(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY, fractionsTurnQueue[currentSelectedUnit.fractionPosInTurnQueue]);
						PaintableObject.currentRenderer.setCurrentDisplayable(createMessageScreen((String)null, PaintableObject.getLocaleString(73), height_, 1000));
						Renderer.startPlayer(9, 1);
						gameState = 9;
						var_12ff = delayCounter;
					}
					else if(sub_d41(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY, currentSelectedUnit))
					{
						var18 = sub_110d(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY);

						if(var_3a5c != -1 && var_3a5c != var18)
						{
							var_3903[var_3a5c] = var_3903[var18];
							var_3903[var18] = currentSelectedUnit;
						}

						setMapElement(FRACTION_BUILDINGS, currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY);
						PaintableObject.currentRenderer.setCurrentDisplayable(createMessageScreen((String)null, PaintableObject.getLocaleString(74), height_, 1000));
						Renderer.startPlayer(9, 1);
						gameState = 0;
						var_12ff = delayCounter;
					}
					else
					{
						gameState = 0;
					}

					currentSelectedUnit.finishMove();
					currentSelectedUnit = null;
					var_36e4 = 0;
				}
				else
				{
					var_36e4 = 5;
					currentSelectedUnit.fillAttackRangeDataEx(mapAlphaData, currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY);
					showAtackRange = true;
					paintMapAlphaDataFlag = true;
					var_38a4 = delayCounter;
					if(var_37c2 != null)
					{
						sprCursor.setExternalFrameSequence(CURSOR_FRAME_SEQUENCES[1]);
						moveCursor(var_37c2.currentMapPosX, var_37c2.currentMapPosY);
					}
					else if(var_37e4 != null)
					{
						moveCursor(var_37e4.currentMapPosX, var_37e4.currentMapPosY);
					}
				}

				drawCursorFlag = true;
			}
			else
			{
				if(var_36e4 == 5)
				{
					if(delayCounter - var_38a4 >= 500L)
					{
						if(var_37c2 != null)
						{
							startAttack(currentSelectedUnit, var_37c2);
						}
						else if(var_37e4 != null)
						{
							sub_726(var_37e4, currentTurningPlayer);
							var_37e4 = null;
							var_36e4 = 7;
							currentSelectedUnit.finishMove();
						}

						paintMapAlphaDataFlag = false;
						showAtackRange = false;
						return;
					}
				}
				else if(var_36e4 == 7)
				{
					if(var_1774 == null)
					{
						var_36e4 = 0;
						gameState = 0;
						return;
					}
				}
				else if(var_36e4 == 6)
				{
					if(delayCounter - var_38a4 >= 1000L)
					{
						var_37c2 = null;
						var_36e4 = 0;
						gameState = 0;
						return;
					}
				}
				else
				{
					if(var_36e4 == 2)
					{
						return;
					}

					if(var_36e4 == 3)
					{
						if(var_3864 == 0)
						{
							if(isMapAtPositionPixels(currentSelectedUnit.currentX + 12, currentSelectedUnit.currentY + 12))
							{
								if(skirmishMode == 0 && currentMission == 7 && currentSelectedUnit == fractionKings[1])
								{
									var_31be = null;
									Unit[] var16;
									if((var16 = listUnits(-1, -1, (byte)0)).length > 0)
									{
										sub_26e(var16[Renderer.randomToRange(var16.length)]);
									}

									var_36e4 = 4;
									return;
								}

								var_3864 = 1;
								var_38a4 = delayCounter;
								return;
							}
						}
						else if(var_3864 == 1)
						{
							if(delayCounter - var_38a4 >= 100L)
							{
								paintMapAlphaDataFlag = true;
								showAtackRange = false;
								var_3864 = 2;
								gameState = 1;
								var_38a4 = delayCounter;
								return;
							}
						}
						else if(var_3864 == 2)
						{
							if(delayCounter - var_38a4 >= 200L)
							{
								cursorMapX = var_3733;
								cursorMapY = var_3781;
								sprCursor.setPosition(var_3733 * 24, var_3781 * 24);
								currentRoute = currentSelectedUnit.findRouteFromPointToPoint(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY, cursorMapX, cursorMapY);
								var_3864 = 3;
								var_38a4 = delayCounter;
								return;
							}
						}
						else if(var_3864 == 3 && delayCounter - var_38a4 >= 200L)
						{
							currentRoute = null;
							currentSelectedUnit.plotRoute(var_3733, var_3781, true);
							var_36e4 = 2;
							var_3864 = 0;
							gameState = 2;
						}

						return;
					}

					Unit var1;
					if(var_3aad.isEmpty())
					{
						var1 = null;
						int var2 = 0;
						int var3 = 6666;
						byte var4 = 0;
						byte var5 = 0;

						int var6;
						byte var8;
						for(var6 = 0; var6 < buildingData.length; ++var6)
						{
							byte var7 = buildingData[var6][0];
							var8 = buildingData[var6][1];
							if(getTileType(var7, var8) == 9 && isBuildingOfFraction(var7, var8, currentTurningPlayer))
							{
								if(var2 == 0)
								{
									var4 = var7;
									var5 = var8;
								}

								++var2;
								int var9 = 0;

								int var10;
								for(var10 = units.size() - 1; var10 >= 0; --var10)
								{
									Unit var11 = (Unit)units.elementAt(var10);
									if(playerTeams[var11.fractionPosInTurnQueue] != playerTeams[currentTurningPlayer] && var11.hasProperty((short)16))
									{
										int var12;
										if((var12 = Math.abs(var11.currentMapPosX - var7) + Math.abs(var11.currentMapPosY - var8)) < var3)
										{
											var3 = var12;
											var4 = var7;
											var5 = var8;
										}

										++var9;
									}
								}

								if(var9 == 0)
								{
									for(var10 = 0; var10 < buildingData.length; ++var10)
									{
										byte var20 = buildingData[var10][0];
										byte var17 = buildingData[var10][1];
										int var13;
										if(getTileType(var20, var17) == 9 && !isBuildingOfFraction(var20, var17, currentTurningPlayer) && (var13 = Math.abs(var20 - var7) + Math.abs(var17 - var8)) < var3)
										{
											var3 = var13;
											var4 = var7;
											var5 = var8;
										}
									}
								}
							}
						}

						if(var2 > 0)
						{
							for(var6 = 0; var6 < fractionsKingCount[currentTurningPlayer]; ++var6)
							{
								if(fractionsAllKings[currentTurningPlayer][var6] != null && fractionsAllKings[currentTurningPlayer][var6].unitState == 3 && canBuyUnit(fractionsAllKings[currentTurningPlayer][var6], var4, var5))
								{
									var1 = buyUnit(fractionsAllKings[currentTurningPlayer][var6], var4, var5);
								}
							}

							if(var1 == null)
							{
								if(countUnits(0, -1, currentTurningPlayer) < 2 && canBuyUnit((byte)0, var4, var5))
								{
									var1 = buyUnit((byte)0, var4, var5);
								}
								else if(countUnits(1, -1, currentTurningPlayer) < 2 && canBuyUnit((byte)1, var4, var5))
								{
									var1 = buyUnit((byte)1, var4, var5);
								}
								else
								{
									var6 = 0;
									int var21 = 0;

									for(var8 = 0; var8 < turnQueueLength; ++var8)
									{
										if(playerTeams[var8] == playerTeams[currentTurningPlayer])
										{
											var6 += countUnits(-1, -1, var8);
										}
										else
										{
											var21 += countUnits(-1, -1, var8);
										}
									}

									if(money[currentTurningPlayer] >= 1000 || countUnits(-1, -1, currentTurningPlayer) < 8 || var6 < var21)
									{
										int var14 = 0;
										byte[] var15 = new byte[12];

										byte var19;
										for(var19 = 1; var19 < 12; ++var19)
										{
											if((countUnits(var19, -1, currentTurningPlayer) < 1 || Unit.UNIT_COST[var19] >= 600) && canBuyUnit(var19, var4, var5))
											{
												var15[var14] = var19;
												++var14;
											}
										}

										if(var14 > 0)
										{
											var19 = var15[Math.abs(Renderer.random()) % var14];
											var1 = buyUnit((byte)var19, var4, var5);
										}
									}
								}
							}
						}

						if(var1 != null)
						{
							sub_109f(var1);
							return;
						}

						var_38d5 = null;
						var_3aad = null;
						endTurn();
						return;
					}

					if(skirmishMode == 0 && currentMission == 7 && fractionKings[1].unitState != 2)
					{
						currentSelectedUnit = fractionKings[1];
						moveCursor(currentSelectedUnit.currentMapPosX, currentSelectedUnit.currentMapPosY);
						var_31be = currentSelectedUnit;
						var_36e4 = 3;
						var_3aad.removeElement(currentSelectedUnit);
						return;
					}

					var1 = var_381f;
					if(var_381f == null)
					{
						var1 = (Unit)var_3aad.elementAt(0);
					}

					sub_109f(var1);
					if(var_381f == null)
					{
						var_3aad.removeElement(var1);
					}
				}

			}
		}
	}

	public final void sub_109f(Unit var1)
	{
		currentSelectedUnit = var1;
		drawCursorFlag = true;
		setArrayValuesEx(mapAlphaData, 0);
		currentSelectedUnit.fillMoveRangeData(mapAlphaData);
		paintMapAlphaDataFlag = false;
		var_38d5 = listUnits(0, -1, currentTurningPlayer);
		int var2 = 0;
		int var3 = fractionKings.length + var_38d5.length + buildingData.length;
		var_395f = new int[var3][5];
		var_39d0 = 0;
		boolean var4 = false;
		int var5 = 10000;
		var_3a15 = -1;
		var_3a34 = -1;
		var_3a41 = -1;
		var_3a5c = -1;

		int var6;
		int var9;
		for(var6 = 0; var6 < var_38d5.length + fractionKings.length; ++var6)
		{
			Unit var7 = null;
			if(var6 >= var_38d5.length)
			{
				if((var7 = fractionKings[var6 - var_38d5.length]) != null)
				{
					if(var7.unitState == 3)
					{
						var7 = null;
					}
					else if(playerTeams[var7.fractionPosInTurnQueue] != playerTeams[currentTurningPlayer] && fractionKings[currentTurningPlayer] == null)
					{
						var_395f[var2][2] += var7.sub_462(var7.currentMapPosX, var7.currentMapPosY, (Unit)null) * 2;
					}
					else if(currentTurn >= 15 && playerTeams[var7.fractionPosInTurnQueue] != playerTeams[currentTurningPlayer] && countUnits(-1, -1, var7.fractionPosInTurnQueue) < 4 && countUnits(-1, -1, currentTurningPlayer) >= 8)
					{
						var_395f[var2][2] += var7.sub_462(var7.currentMapPosX, var7.currentMapPosY, (Unit)null) * 2;
					}
					else if(var7.fractionPosInTurnQueue != currentTurningPlayer)
					{
						var7 = null;
					}
				}
			}
			else if(fractionKings[currentTurningPlayer] != null)
			{
				var7 = var_38d5[var6];
			}

			if(var7 != null)
			{
				var_395f[var2][0] = var7.currentMapPosX;
				var_395f[var2][1] = var7.currentMapPosY;
				if(var7.fractionPosInTurnQueue == currentTurningPlayer)
				{
					Unit[] var8 = var7.getUnitsWithinRange(var7.currentMapPosX, var7.currentMapPosY, 1, 5, (byte)0);

					for(var9 = 0; var9 < var8.length; ++var9)
					{
						if(var8[var9].unitState != 4)
						{
							var_395f[var2][2] += var8[var9].sub_462(var8[var9].currentMapPosX, var8[var9].currentMapPosY, var7);
						}
					}
				}

				if(var_395f[var2][2] > 0)
				{
					var_395f[var2][4] += var7.sub_462(var7.currentMapPosX, var7.currentMapPosY, (Unit)null);
					var_395f[var2][4] += var7.var_8f7;
					if(var_395f[var2][2] > var_39d0)
					{
						var_39d0 = var_395f[var2][2];
					}

					var_395f[var2][3] = Math.abs(var7.currentMapPosX - var1.currentMapPosX) + Math.abs(var7.currentMapPosY - var1.currentMapPosY);
					if(var_395f[var2][3] < 1)
					{
						var_395f[var2][3] = 1;
					}

					if(var_395f[var2][3] < var5)
					{
						var5 = var_395f[var2][3];
					}
				}
				else
				{
					var_395f[var2][2] = -6666;
				}
			}
			else
			{
				var_395f[var2][2] = -6666;
			}

			++var2;
		}

		var6 = 666;
		int var24 = 666;
		int var20 = -1;
		var9 = -1;

		int var10;
		int var16;
		Unit var28;
		for(var10 = 0; var10 < buildingData.length; ++var10)
		{
			byte var11 = buildingData[var10][0];
			byte var12 = buildingData[var10][1];
			byte var13 = getTileType(var11, var12);
			boolean var14 = isBuildingOfFraction(var11, var12, var1.fractionPosInTurnQueue);
			var_395f[var2][2] = -6666;
			if(var14 || var_3903[var10] != null)
			{
				Unit[] var15 = var1.getUnitsWithinRange(var11, var12, 1, 5, (byte)0);
				var_395f[var2][0] = var11;
				var_395f[var2][1] = var12;
				var_395f[var2][2] = 0;

				for(var16 = 0; var16 < var15.length; ++var16)
				{
					if(var15[var16].unitState != 4)
					{
						if(var_3903[var10] != null && !var14)
						{
							var_395f[var2][2] += var15[var16].sub_462(var15[var16].currentMapPosX, var15[var16].currentMapPosY, (Unit)null);
						}
						else if(var13 == 8 && var15[var16].hasProperty((short)8) || var13 == 9 && var15[var16].hasProperty((short)16))
						{
							var_395f[var2][2] += var15[var16].sub_462(var15[var16].currentMapPosX, var15[var16].currentMapPosY, (Unit)null);
						}
					}
				}

				if(var_395f[var2][2] == 0)
				{
					if(var_3903[var10] != null && !var14)
					{
						var_395f[var2][2] = 100;
						var_395f[var2][4] += 2000;
					}
					else
					{
						var_395f[var2][2] = -6666;
					}
				}

				if(var_395f[var2][2] != -6666)
				{
					var_395f[var2][4] += var_399e[var10];
					if(var_395f[var2][2] > var_39d0)
					{
						var_39d0 = var_395f[var2][2];
					}

					var_395f[var2][3] = Math.abs(var11 - var1.currentMapPosX) + Math.abs(var12 - var1.currentMapPosY);
					if(var_395f[var2][3] < 1)
					{
						var_395f[var2][3] = 1;
					}

					if(var_395f[var2][3] < var5)
					{
						var5 = var_395f[var2][3];
					}
				}
			}

			if(sub_e68(var11, var12, playerTeams[var1.fractionPosInTurnQueue]))
			{
				if(((var28 = getUnit(var11, var12, (byte)0)) == null || var28.fractionPosInTurnQueue == var1.fractionPosInTurnQueue) && (var16 = Math.abs(var11 - var1.currentMapPosX) + Math.abs(var12 - var1.currentMapPosY)) < var24)
				{
					var9 = var10;
					var24 = var16;
				}
			}
			else if((var_3903[var10] == null || var_3903[var10] == var1) && (var13 == 8 && var1.hasProperty((short)8) || var13 == 9 && var1.hasProperty((short)16)))
			{
				int var26;
				if((var26 = Math.abs(var11 - var1.currentMapPosX) + Math.abs(var12 - var1.currentMapPosY)) < var6)
				{
					var20 = var10;
					var6 = var26;
				}

				Unit var19 = getUnit(var11, var12, (byte)0);
				if(var26 < var24 && mapAlphaData[var11][var12] > 0 && (var19 == null || var19.fractionPosInTurnQueue == var1.fractionPosInTurnQueue))
				{
					var9 = var10;
					var24 = var26;
				}
			}

			++var2;
		}

		int var21;
		int var25;
		if(var1.health < 50 && var9 != -1)
		{
			if(var9 == var20)
			{
				var_3a5c = var20;
			}

			var_3903[var9] = var1;
			var_3a15 = buildingData[var9][0];
			var_3a34 = buildingData[var9][1];
			setArrayValuesEx(var_39e3, 0);
			Unit.fillMoveRangeDataEx(var_39e3, var_3a15, var_3a34, 10, -1, var1.type, currentTurningPlayer, false);
		}
		else if(fractionKings[currentTurningPlayer] != null && var20 != -1 && (var1.hasProperty((short)8) || var1.hasProperty((short)16)))
		{
			var_3a5c = var20;
			var_3903[var20] = var1;
			var_3a15 = buildingData[var20][0];
			var_3a34 = buildingData[var20][1];
			setArrayValuesEx(var_39e3, 0);
			Unit.fillMoveRangeDataEx(var_39e3, var_3a15, var_3a34, 10, -1, var1.type, currentTurningPlayer, false);
		}
		else
		{
			var10 = -1;
			var21 = -6666;

			for(var25 = 0; var25 < var2; ++var25)
			{
				if(var_395f[var25][2] > -6666)
				{
					if(var_395f[var25][2] > 0)
					{
						var_395f[var25][2] = var_395f[var25][2] * var5 / var_395f[var25][3];
					}

					var_395f[var25][2] -= var_395f[var25][4];
					if(var_395f[var25][2] > var21)
					{
						var21 = var_395f[var25][2];
						var10 = var25;
					}
				}
			}

			if(var10 != -1)
			{
				var25 = var1.sub_462(var1.currentMapPosX, var1.currentMapPosY, (Unit)null);
				if(var10 < var_38d5.length)
				{
					var_38d5[var10].var_8f7 += var25;
				}
				else if(var10 < fractionKings.length + var_38d5.length)
				{
					fractionKings[var10 - var_38d5.length].var_8f7 += var25;
				}
				else
				{
					var_3a41 = var10 - fractionKings.length - var_38d5.length;
					var_399e[var_3a41] += var25;
				}

				var_3a15 = var_395f[var10][0];
				var_3a34 = var_395f[var10][1];
				setArrayValuesEx(var_39e3, 0);
				Unit.fillMoveRangeDataEx(var_39e3, var_3a15, var_3a34, 10, -1, var1.type, currentTurningPlayer, false);
			}
		}

		var10 = -10000;
		var21 = 0;

		for(var25 = mapAlphaData.length; var21 < var25; ++var21)
		{
			int var23 = 0;

			for(int var29 = mapAlphaData[var21].length; var23 < var29; ++var23)
			{
				if(mapAlphaData[var21][var23] > 0 && ((var28 = getUnit(var21, var23, (byte)0)) == null || var28 == var1 || var_381f == null && var28.fractionPosInTurnQueue == var1.fractionPosInTurnQueue && var28.unitState == 0))
				{
					if(!var1.hasProperty((short)512) || var28 == var1)
					{
						Unit[] var17 = var1.getUnitsWithinAttackRange(var21, var23, (byte)0);

						for(int var18 = 0; var18 < var17.length; ++var18)
						{
							if((var16 = sub_10cb(var1, var21, var23, var17[var18], (Unit)null)) > var10)
							{
								var_37e4 = null;
								var_37c2 = var17[var18];
								var10 = var16;
								var_3733 = var21;
								var_3781 = var23;
							}
						}
					}

					if(var1.hasProperty((short)32))
					{
						unitsWithinAttackRange = var1.getUnitsWithinAttackRange(var21, var23, (byte)1);

						for(int var22 = 0; var22 < unitsWithinAttackRange.length; ++var22)
						{
							if((var16 = sub_10cb(var1, var21, var23, (Unit)null, unitsWithinAttackRange[var22])) > var10)
							{
								var_37c2 = null;
								var_37e4 = unitsWithinAttackRange[var22];
								var10 = var16;
								var_3733 = var21;
								var_3781 = var23;
							}
						}
					}

					if((var16 = sub_10cb(var1, var21, var23, (Unit)null, (Unit)null)) > var10)
					{
						var_37c2 = null;
						var_37e4 = null;
						var10 = var16;
						var_3733 = var21;
						var_3781 = var23;
					}
				}
			}
		}

		if(var_3a5c != -1)
		{
			var_3947[var_3a5c] = (byte)(10 - var_39e3[var_3733][var_3781]);
		}

		var_381f = null;
		Unit var27;
		if((var27 = getUnit(var_3733, var_3781, (byte)0)) != null && var27 != var1)
		{
			var_381f = var27;
			var_36e4 = 0;
		}
		else
		{
			moveCursor(var1.currentMapPosX, var1.currentMapPosY);
			var_31be = var1;
			var_36e4 = 3;
		}
	}

	public final int sub_10cb(Unit var1, int var2, int var3, Unit var4, Unit var5)
	{
		int var6 = 0;
		int var7;
		int var8;
		if(var_3a5c != -1 && fractionKings[var1.fractionPosInTurnQueue] != null)
		{
			if(var_3a15 != -1)
			{
				if(var_39e3[var2][var3] > 0)
				{
					var6 = 0 + 100 + 100 * var_39e3[var2][var3] / 10;
				}
				else
				{
					var7 = Math.abs(var_3a15 - var1.currentMapPosX) + Math.abs(var_3a34 - var1.currentMapPosY);
					var8 = Math.abs(var_3a15 - var2) + Math.abs(var_3a34 - var3);
					var6 = 0 + 100 * (var7 - var8) / (var1.baseMoveRange - 1);
					if(TERRAIN_STEPS_REQUIRED[getTileType(var2, var3)] <= 1)
					{
						var6 += 20;
					}
				}
			}

			if(var4 == null && !sub_e68(var2, var3, playerTeams[var1.fractionPosInTurnQueue]))
			{
				if(var1.hasProperty((short)16) && getTileType(var2, var3) == 9)
				{
					var6 += 300;
				}
				else if(var1.hasProperty((short)8) && (getTileType(var2, var3) == 8 || mapData[var2][var3] == 27))
				{
					var6 += 200;
				}
			}
		}

		switch(var1.type)
		{
			case 2:
				if(getTileType(var2, var3) == 5)
				{
					var6 += 25;
				}
				break;
			case 3:
				if(var5 != null)
				{
					var6 += 100;
				}
				break;
			case 4:
				Unit[] var11 = var1.getUnitsWithinRange(var2, var3, 1, 2, (byte)2);
				if(var5 != null)
				{
					var6 += 25 * var11.length;
				}
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
		}

		if(var4 != null)
		{
			if(var4.unitState == 4)
			{
				var7 = getBuildingFraction(var4.currentMapPosX, var4.currentMapPosY);
				var8 = sub_110d(var4.currentMapPosX, var4.currentMapPosY);
				if(var7 != 0 && var8 != -1 && var_3903[var8] == null)
				{
					var6 += var1.sub_462(var2, var3, var4) / 2;
				}
			}
			else
			{
				if(!var4.canPerformCloseAttack(var1, var2, var3))
				{
					var6 += var1.sub_462(var2, var3, var4) * 2;
				}
				else
				{
					var6 += var1.sub_462(var2, var3, var4) * 3 / 2 - var4.sub_462(var2, var3, var1);
				}

				if(var4.type == 9)
				{
					var6 += 25;
				}
				else if(var4.type == 11)
				{
					var6 += 100;
				}
			}
		}

		var6 += TERRAIN_DEFENCE_BONUS[getTileType(var2, var3)];
		if(var1.health < 100 && sub_e68(var2, var3, playerTeams[var1.fractionPosInTurnQueue]))
		{
			var6 += 100 - var1.health;
		}

		var7 = sub_110d(var2, var3);
		int var9;
		if(var_39e3[var2][var3] > 0)
		{
			var8 = var_39e3[var2][var3];
			var9 = 10 - var1.baseMoveRange / 2;
			if(var8 > var9)
			{
				var8 = var9;
			}

			var6 += 50 + 100 * var8 / var9;
		}
		else if(var_3a15 != -1)
		{
			var8 = Math.abs(var_3a15 - var1.currentMapPosX) + Math.abs(var_3a34 - var1.currentMapPosY);
			var9 = Math.abs(var_3a15 - var2) + Math.abs(var_3a34 - var3);
			var6 += 50 * (var8 - var9) / (var1.baseMoveRange - 1);
		}

		Unit var10;
		if(var7 != -1 && (var10 = var_3903[var7]) != null && var10 != var1 && var10.unitState == 0 && var_3947[var7] < var10.baseMoveRange)
		{
			var6 -= 200;
		}

		return var6 += 20 * (Math.abs(var2 - var1.currentMapPosX) + Math.abs(var3 - var1.currentMapPosY)) / (var1.baseMoveRange - 1);
	}

	public final int sub_110d(int var1, int var2)
	{
		for(int var3 = 0; var3 < buildingData.length; ++var3)
		{
			if(buildingData[var3][0] == var1 && buildingData[var3][1] == var2)
			{
				return var3;
			}
		}

		return -1;
	}

	public final void setScriptDelay(int var1)
	{
		scriptUpdateDelayCounter = var1;
		var_3d48 = true;
	}

	public final AuxDisplayable createDialogScreen(String var1, byte var2, byte var3)
	{
		AuxDisplayable var4 = new AuxDisplayable((byte)7, 12);
		int var5 = Renderer.fontABaseLineEx * 3;
		var4.sub_1f4(var1, super.width, var5, var2, var3);
		var4.setPosition(0, super.height - var5, 0);
		PaintableObject.currentRenderer.setCurrentDisplayable(var4);
		return var4;
	}

	public final AuxDisplayable createMessageScreen(String var1, String var2, int var3, int var4)
	{
		return createMessageScreenEx(var1, var2, var3, -1, var4);
	}

	public final AuxDisplayable createMessageScreenEx(String var1, String var2, int var3, int var4, int var5)
	{
		AuxDisplayable var6;
		(var6 = new AuxDisplayable((byte)10, 12)).initMsgAuxDisplayable(var1, var2, super.width, var4);
		var6.setPosition(super.halfWidth, var3 / 2, 3);
		var6.nextDisplayable = this;
		var6.displayTimeCounter = var5;
		return var6;
	}

	public final void initStoryMission()
	{
		var_3c5c = null;
		if(skirmishMode == 0)
		{
			statusBarOffset = FOOTER_HEIGHT;
			var_2319 = true;
			activeFlag = false;
			auxMapNameMessage = createMessageScreen((String)null, currentMapName, super.height, 2000);
			auxMapNameMessage.setPosition(super.halfWidth, super.halfHeight, 3);
			updateAlphaDrap = true;
			inverseDrapAlpha = true;
			drapAlphaValue = 0;
		}

		if(currentMission == 0)
		{
			allowedUnits = 0;
			money[0] = 0;
			money[1] = 0;
			Unit.speed = 4;
			mapStepMax = 2;
			moveMapShowPoint(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
			moveCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
			setIntroMode(2, 3, 3);
			Renderer.startPlayer_(1, 1);
			updateAlphaDrap = false;
			drawCursorFlag = false;
			scriptState = 0;
		}
		else if(currentMission == 1)
		{
			allowedUnits = 1;
			money[0] = 300;
			money[1] = 50;
			Unit.speed = 4;
			mapStepMax = 2;
			fractionKings[0].setKingVariant(2);
			moveMapShowPoint(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
			moveCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
			getUnit(7, 12, (byte)0).plotRoute(7, 10, false);
			getUnit(8, 11, (byte)0).plotRoute(8, 9, false);
			getUnit(9, 12, (byte)0).plotRoute(9, 10, false);
			moveMapAndCursor(7, 3);
			PaintableObject.currentRenderer.setCurrentDisplayable(auxMapNameMessage);
		}
		else if(currentMission == 2)
		{
			allowedUnits = 0;
			money[0] = 0;
			money[1] = 0;
			Unit.speed = 4;
			money[0] = 0;
			moveMapShowPoint(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
			moveCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
			crystallEscortLeader = getUnit(8, 17, (byte)0);
			crystallEscortCrystall = getUnit(8, 18, (byte)0);
			crystallEscortFollower = getUnit(8, 19, (byte)0);
			crystallEscortLeader.plotRoute(8, 15, false);
			crystallEscortCrystall.plotRoute(8, 15, false);
			crystallEscortFollower.plotRoute(8, 15, false);
			fractionKings[0].plotRoute(8, 14, false);
			drawCursorFlag = false;
			PaintableObject.currentRenderer.setCurrentDisplayable(auxMapNameMessage);
			scriptState = 0;
		}
		else if(currentMission == 3)
		{
			allowedUnits = 7;
			Unit.speed = 4;
			money[0] = 400;
			money[1] = 400;
			crystallEscortLeader = Unit.createUnit((byte)0, (byte)0, -1, 5);
			crystallEscortCrystall = Unit.createUnit((byte)2, (byte)0, -2, 5);
			crystallEscortFollower = Unit.createUnit((byte)3, (byte)0, -3, 5);
			crystallEscortLeader.plotRoute(3, 4, false);
			crystallEscortCrystall.plotRoute(4, 4, false);
			crystallEscortFollower.plotRoute(2, 4, false);
			fractionKings[0].plotRoute(3, 3, false);
			moveMapShowPoint(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
			moveCursor(3, 3);
			var_31be = fractionKings[0];
			setIntroMode(3, 3, 3);
			Renderer.startPlayer_(1, 1);
			updateAlphaDrap = false;
			drawCursorFlag = false;
			scriptState = 0;
		}
		else
		{
			Unit var1;
			if(currentMission == 4)
			{
				allowedUnits = 0;
				money[0] = 0;
				money[1] = 0;
				moveMapShowPointPixels(fractionKings[0].currentX + 12, fractionKings[0].currentY + 12);
				moveCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
				Unit.speed = 4;
				(var1 = getUnit(11, 2, (byte)0)).setMapPosition(11, -3);
				var1.plotRoute(11, 2, false);
				(var1 = getUnit(10, 1, (byte)0)).setMapPosition(10, -5);
				var1.plotRoute(10, 1, false);
				(var1 = getUnit(11, 1, (byte)0)).setMapPosition(11, -5);
				var1.plotRoute(11, 1, false);
				(var1 = getUnit(12, 1, (byte)0)).setMapPosition(12, -5);
				var1.plotRoute(12, 1, false);
				(var1 = getUnit(11, 0, (byte)0)).setMapPosition(11, -7);
				var1.plotRoute(11, 0, false);
				(var1 = getUnit(12, 0, (byte)0)).setMapPosition(12, -7);
				var1.plotRoute(12, 0, false);
				PaintableObject.currentRenderer.setCurrentDisplayable(auxMapNameMessage);
				drawCursorFlag = false;
				scriptState = 0;
			}
			else if(currentMission == 5)
			{
				allowedUnits = 7;
				money[0] = 600;
				money[1] = 600;
				fractionKings[0].setKingVariant(2);
				moveMapShowPoint(5, 0);
				moveCursor(5, 0);
				mapStepMax = 4;
				moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
				PaintableObject.currentRenderer.setCurrentDisplayable(auxMapNameMessage);
				drawCursorFlag = false;
				scriptState = 0;
			}
			else
			{
				Unit var2;
				if(currentMission == 6)
				{
					allowedUnits = 8;
					Unit.speed = 4;
					money[0] = 400;
					money[1] = 600;
					var1 = Unit.createUnit((byte)0, (byte)0, 13, -1);
					var2 = Unit.createUnit((byte)1, (byte)0, 13, -1);
					Unit var3 = Unit.createUnit((byte)3, (byte)0, 13, -1);
					Unit var4 = Unit.createUnit((byte)11, (byte)0, 13, -1);
					fractionKings[0].followerUnit = var1;
					var1.followerUnit = var2;
					var2.followerUnit = var3;
					var3.followerUnit = var4;
					fractionKings[0].fillMoveRangeData(mapAlphaData);
					fractionKings[0].plotRoute(14, 3, true);
					moveMapShowPoint(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
					moveCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
					var_31be = fractionKings[0];
					PaintableObject.currentRenderer.setCurrentDisplayable(auxMapNameMessage);
					drawCursorFlag = false;
					scriptState = 0;
				}
				else
				{
					if(currentMission == 7)
					{
						allowedUnits = 8;
						Unit.speed = 4;
						money[0] = 800;
						money[1] = 200;
						(var1 = getUnit(7, 4, (byte)0)).setKingVariant(3);
						fractionKings[1] = var1;
						var2 = getUnit(8, 15, (byte)0);
						fractionKings[0] = var2;
						var2.setKingVariant(0);
						getUnit(6, 15, (byte)0).setKingVariant(2);
						moveMapShowPointPixels(fractionKings[0].currentX + 12, fractionKings[0].currentY + 12);
						moveCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
						setIntroMode(4, 3, 3);
						Renderer.startPlayer_(1, 1);
						updateAlphaDrap = false;
						drawCursorFlag = false;
						scriptState = 0;
					}

				}
			}
		}
	}

	public final void loadScript(int var1) throws IOException
	{
		InputStream is = Renderer.getResourceAsStream("m" + var1 + ".script");

		if(is == null)
		{
			script = null;
			return;
		}

		Vector cases = new Vector();
		Vector lines = null;

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

			if(line[0].charAt(0) == '@')
			{
				line[0] = line[0].substring(1);

				if(line[0].equalsIgnoreCase("case"))
				{
					int n = Integer.parseInt(line[1]);

					while(cases.size() <= n)
					{
						cases.addElement(new Vector());
					}

					lines = (Vector)cases.elementAt(n);
				}
			}
			else
			{
				lines.addElement(line);
			}
		}

		script = new String[cases.size()][][];

		for(int i = 0; i < cases.size(); i++)
		{
			lines = (Vector)cases.elementAt(i);
			script[i] = new String[lines.size()][];
			lines.copyInto(script[i]);
		}

		/*
		for(int i = 0; i < script.length; i++)
		{
			System.out.println("Script case " + i);

			for(int j = 0; j < script[i].length; j++)
			{
				System.out.print("Line " + j + ":");

				for(int k = 0; k < script[i][j].length; k++)
				{
					System.out.print(" " + script[i][j][k]);
				}

				System.out.println();
			}

			System.out.println();
		}
		*/
	}

	public final void updateCustomScript()
	{
		String[][] lines = script[scriptState];

		String[] line;
		int lineindex = 0;
		int nextindex = -1;

		Unit unit = null;

		while(lineindex >= 0 && lineindex < lines.length)
		{
			line = lines[lineindex++];

			/*
			System.out.print("Executing case " + scriptState + " of " + (script.length - 1) + ", line " + (lineindex - 1) + ":");

			for(int i = 0; i < line.length; i++)
			{
				System.out.print(" " + line[i]);
			}

			System.out.println();
			*/

			if(line[0].equalsIgnoreCase("Jump"))
			{
				lineindex += Integer.parseInt(line[1]);
			}
			else if(line[0].equalsIgnoreCase("Alt"))
			{
				nextindex = Integer.parseInt(line[1]);

				if(nextindex == 0)
				{
					nextindex = -1;
				}
				else
				{
					nextindex += lineindex;
				}
			}
			else if(line[0].equalsIgnoreCase("Test"))
			{
				int a = 0;
				int b = 0;
				String test = null;

				if(line[1].equalsIgnoreCase("CurrentPlayer"))
				{
					a = currentTurningPlayer;
				}
				else if(line[1].equalsIgnoreCase("CurrentTurn"))
				{
					a = currentTurn;
				}
				else if(line[1].equalsIgnoreCase("GameState"))
				{
					a = gameState;
				}
				else if(line[1].equalsIgnoreCase("AlphaWindow"))
				{
					a = alphaWindowSize;
				}
				else if(line[1].equalsIgnoreCase("StatusBarOffset"))
				{
					a = statusBarOffset;
				}
				else if(line[1].equalsIgnoreCase("UnitFinishedMove"))
				{
					a = lastFinishedMoveUnit != null ? 1 : 0;
				}
				else if(line[1].equalsIgnoreCase("CountUnits"))
				{
					a = countUnits(Integer.parseInt(line[2]), Integer.parseInt(line[3]), (byte)Integer.parseInt(line[4]));
					b = Integer.parseInt(line[6]);
					test = line[5];
				}

				if(test == null)
				{
					b = Integer.parseInt(line[3]);
					test = line[2];
				}

				if(!evaluateScriptTest(test, a, b))
				{
					lineindex = nextindex;
				}

				nextindex = -1;
			}
			else if(line[0].equalsIgnoreCase("Wait"))
			{
				setScriptDelay(Integer.parseInt(line[1]));
			}
			else if(line[0].equalsIgnoreCase("NextState"))
			{
				scriptState++;
			}
			else if(line[0].equalsIgnoreCase("ShowMapName"))
			{
				PaintableObject.currentRenderer.setCurrentDisplayable(auxMapNameMessage);
			}
			else if(line[0].equalsIgnoreCase("StartPlay"))
			{
				startNormalPlay();
			}
			else if(line[0].equalsIgnoreCase("CompleteMission"))
			{
				completeMission();
			}
			else if(line[0].equalsIgnoreCase("SetFadeEnabled"))
			{
				updateAlphaDrap = "true".equalsIgnoreCase(line[1]);
			}
			else if(line[0].equalsIgnoreCase("SetCursorVisible"))
			{
				drawCursorFlag = "true".equalsIgnoreCase(line[1]);
			}
			else if(line[0].equalsIgnoreCase("SetGameActive"))
			{
				activeFlag = "true".equalsIgnoreCase(line[1]);
			}
			else if(line[0].equalsIgnoreCase("SetFadeValue"))
			{
				drapAlphaValue = Integer.parseInt(line[1]);
			}
			else if(line[0].equalsIgnoreCase("SetMapStepMax"))
			{
				mapStepMax = Integer.parseInt(line[1]);
			}
			else if(line[0].equalsIgnoreCase("SetUnitSpeed"))
			{
				if(line[1].equalsIgnoreCase("slow"))
				{
					Unit.speed = Unit.UNIT_SPEED_SLOW;
				}
				else if(line[1].equalsIgnoreCase("fast"))
				{
					Unit.speed = Unit.UNIT_SPEED_FAST;
				}
				else
				{
					Unit.speed = (byte)Integer.parseInt(line[1]);
				}
			}
			else if(line[0].equalsIgnoreCase("ShowDialog"))
			{
				createDialogScreen(PaintableObject.getLocaleString(Integer.parseInt(line[1])), (byte)Integer.parseInt(line[2]), (byte)Integer.parseInt(line[3]));
			}
			else if(line[0].equalsIgnoreCase("ShowHelp"))
			{
				currentHelp = Integer.parseInt(line[1]);
			}
			else if(line[0].equalsIgnoreCase("Vibrate"))
			{
				Renderer.vibrate(Integer.parseInt(line[1]));
			}
			else if(line[0].equalsIgnoreCase("MoveMapAndCursor"))
			{
				if(line[1].equalsIgnoreCase("king"))
				{
					Unit u1 = fractionKings[Integer.parseInt(line[2])];
					moveMapAndCursor(u1.currentMapPosX, u1.currentMapPosY);
				}
				else
				{
					moveMapAndCursor(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
				}
			}
			else if(line[0].equalsIgnoreCase("GetUnitPlotRoute"))
			{
				getUnit(Integer.parseInt(line[1]), Integer.parseInt(line[2]), (byte)Integer.parseInt(line[3])).plotRoute(Integer.parseInt(line[4]), Integer.parseInt(line[5]), "true".equalsIgnoreCase(line[6]));
			}
			else if(line[0].equalsIgnoreCase("CreateUnitPlotRoute"))
			{
				Unit.createUnit((byte)Integer.parseInt(line[1]), (byte)Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4])).plotRouteEx(Integer.parseInt(line[5]), Integer.parseInt(line[6]), "true".equalsIgnoreCase(line[7]), "true".equalsIgnoreCase(line[8]));
			}
			else if(line[0].equalsIgnoreCase("GetUnit"))
			{
				unit = getUnit(Integer.parseInt(line[1]), Integer.parseInt(line[2]), (byte)Integer.parseInt(line[3]));
			}
			else if(line[0].equalsIgnoreCase("RemoveUnit"))
			{
				unit.removeThisUnit();
			}
			else if(line[0].equalsIgnoreCase("ScheduleUnitAnimationStop"))
			{
				unit.scheduleIdleAnimationStop(Integer.parseInt(line[1]));
			}
			else if(line[0].equalsIgnoreCase("CreateSpriteAtUnit"))
			{
				Sprite sprite = null;

				if(line[1].equalsIgnoreCase("Smoke"))
				{
					sprite = sprSmoke;
				}
				else if(line[1].equalsIgnoreCase("Spark"))
				{
					sprite = sprSpark;
				}
				else if(line[1].equalsIgnoreCase("RedSpark"))
				{
					sprite = sprRedSpark;
				}

				createSimpleSparkSprite(sprite, unit.currentX, unit.currentY, Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]));
			}
		}
	}

	private boolean evaluateScriptTest(String test, int a, int b)
	{
		if(test.equals("="))
		{
			return a == b;
		}
		else if(test.equals("!="))
		{
			return a != b;
		}
		else if(test.equals(">"))
		{
			return a > b;
		}
		else if(test.equals(">="))
		{
			return a >= b;
		}
		else if(test.equals("<"))
		{
			return a < b;
		}
		else if(test.equals("<="))
		{
			return a <= b;
		}
		else
		{
			return false;
		}
	}

	public final void updateScript()
	{
		if(levelEditorMode != 0)
		{
			return;
		}
		
		if(scriptUpdateDelayCounter <= 0 || (scriptUpdateDelayCounter -= 1) <= 0)
		{
			if(var_3d57)
			{
				if(scriptState == 0)
				{
					var_19c3 = true;
					combatDrapValue = 0;
					gameState = 11;
					scriptState = 1;
				}

			}
			else
			{
				int var2;
				if(skirmishMode == 1)
				{
					if(scriptState == 100)
					{
						Renderer.startPlayer_(FRACTION_BACKGROUND_MUSIC[fractionsTurnQueue[currentTurningPlayer]], 0);
						auxObjectivesMessage.setDrawSoftButtonFlag((byte)0, true);
						auxObjectivesMessage.setNextDisplayable((PaintableObject)null);
						PaintableObject.currentRenderer.setCurrentDisplayable(auxObjectivesMessage);
						++scriptState;
					}
					else
					{
						if(scriptState == 101)
						{
							byte var10 = -1;
							var2 = -1;
							boolean var17 = true;

							for(int var18 = 0; var18 < turnQueueLength; ++var18)
							{
								if(playerModes[var18] != 2 && (fractionKings[var18] != null && fractionKings[var18].unitState != 3 || countCastles(var18) != 0))
								{
									var2 = var18;
									if(var10 != -1 && var10 != playerTeams[var18])
									{
										var17 = false;
										break;
									}

									var10 = playerTeams[var18];
								}
							}

							if(var17)
							{
								activeFlag = false;
								drawCursorFlag = false;
								String var13 = PaintableObject.getReplacedLocaleString(38, "" + (playerTeams[var2] + 1));
								String var5 = PaintableObject.getReplacedLocaleString(81, var13) + "\n(";

								for(int var19 = 0; var19 < turnQueueLength; ++var19)
								{
									if(playerModes[var19] != 2 && playerTeams[var19] == playerTeams[var2])
									{
										var5 = var5 + " " + PaintableObject.getLocaleString(88 + fractionsTurnQueue[var19]) + " ";
									}
								}

								var5 = var5 + ")";
								AuxDisplayable var16;
								(var16 = createMessageScreenEx((String)null, var5, height_, halfHeight_, -1)).setNextDisplayable(this);
								PaintableObject.currentRenderer.setCurrentDisplayable(var16);
								if(playerModes[var2] == 1)
								{
									Renderer.startPlayer_(6, 1);
								}
								else
								{
									Renderer.startPlayer_(7, 1);
								}

								setScriptDelay(15);
								++scriptState;
								return;
							}
						}
						else if(scriptState == 102)
						{
							var_19c3 = true;
							combatDrapValue = 0;
							gameState = 11;
							++scriptState;
						}

					}
				}
				else if(mode == 1 && skirmishMode == 0 && scriptState != -1)
				{
					if(mapTargetX != -1)
					{
						if(!isMapAtPosition(mapTargetX, mapTargetY))
						{
							return;
						}

						mapTargetX = -1;
						mapTargetY = -1;
					}

					if(gameState != 11)
					{
						boolean var1 = true;

						for(var2 = 0; var2 < fractionsKingCount[0]; ++var2)
						{
							if(fractionsAllKings[0][var2].unitState != 3)
							{
								var1 = false;
								break;
							}
						}

						if(var1 && countCastles(0) == 0)
						{
							sub_131d();
							return;
						}
					}

					if(script != null)
					{
						updateCustomScript();
					}
					else
					{
						Unit var11;

						if(currentMission == 0)
						{
							switch(scriptState)
							{
								case 0:
									++scriptState;
									break;
								case 1:
									PaintableObject.currentRenderer.setCurrentDisplayable(auxMapNameMessage);
									setScriptDelay(10);
									++scriptState;
									break;
								case 2:
									updateAlphaDrap = true;
									getUnit(0, 8, (byte)0).plotRoute(3, 8, false);
									getUnit(1, 9, (byte)0).plotRoute(4, 9, false);
									getUnit(0, 10, (byte)0).plotRoute(3, 10, false);
									moveMapAndCursor(5, 9);
									break;
								case 3:
									moveMapAndCursor(9, 3);
									drawCursorFlag = true;
									break;
								case 4:
									setScriptDelay(10);
									mapStepMax = 12;
									Unit.speed = Unit.UNIT_SPEED_SLOW;
									++scriptState;
									break;
								case 5:
									createDialogScreen(PaintableObject.getLocaleString(221), (byte)2, (byte)4);
									setScriptDelay(10);
									++scriptState;
									break;
								case 6:
									Unit var12 = getUnit(9, 3, (byte)0);
									Renderer.vibrate(100);
									var12.scheduleIdleAnimationStop(400);
									createSimpleSparkSprite(sprRedSpark, var12.currentX, var12.currentY, 0, 0, 2, 50);
									setScriptDelay(10);
									++scriptState;
									break;
								case 7:
									createDialogScreen(PaintableObject.getLocaleString(222), (byte)2, (byte)4);
									++scriptState;
									break;
								case 8:
									var11 = getUnit(9, 3, (byte)0);
									createSimpleSparkSprite(sprSpark, var11.currentX, var11.currentY, 0, 0, 1, 50);
									createSimpleSparkSprite(sprSmoke, var11.currentX, var11.currentY, 0, -3, 1, 100);
									var11.removeThisUnit();
									setScriptDelay(20);
									++scriptState;
									break;
								case 9:
									drawCursorFlag = false;
									moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
									break;
								case 10:
									setScriptDelay(10);
									++scriptState;
									break;
								case 11:
									createDialogScreen(PaintableObject.getLocaleString(223), (byte)0, (byte)4);
									setScriptDelay(5);
									++scriptState;
									break;
								case 12:
									createDialogScreen(PaintableObject.getLocaleString(224), (byte)5, (byte)4);
									++scriptState;
									break;
								case 13:
									startNormalPlay();
									++scriptState;
									break;
								case 14:
									if(statusBarOffset == 0)
									{
										currentHelp = 0;
										++scriptState;
									}
									break;
								case 15:
									if(currentTurningPlayer == 0 && gameState == 1 && alphaWindowSize == 0)
									{
										currentHelp = 1;
										++scriptState;
									}
									break;
								case 16:
									if(currentTurningPlayer == 0 && lastFinishedMoveUnit != null)
									{
										currentHelp = 2;
										++scriptState;
									}
									break;
								case 17:
									if(currentTurningPlayer == 0 && lastFinishedMoveUnit != null)
									{
										currentHelp = 3;
										++scriptState;
									}
									break;
								case 18:
									if(countUnits(-1, 2, (byte)0) >= 3)
									{
										currentHelp = 4;
										++scriptState;
									}
									else if(currentTurn >= 1)
									{
										++scriptState;
									}
									break;
								case 19:
									if(currentTurn >= 2)
									{
										currentHelp = 5;
										++scriptState;
									}
									break;
								case 20:
									currentHelp = 6;
									++scriptState;
									break;
								case 21:
									if(gameState == 1 && currentTurningPlayer == 0)
									{
										currentHelp = 7;
										++scriptState;
									}
									break;
								case 22:
									if(countUnits(-1, -1, (byte)1) == 0)
									{
										activeFlag = false;
										setScriptDelay(20);
										++scriptState;
									}
									break;
								case 23:
									moveMapAndCursor(1, 1);
									break;
								case 24:
									Unit.createUnit((byte)1, (byte)1, 1, 1).plotRouteEx(1, 2, false, true);
									setScriptDelay(10);
									++scriptState;
									break;
								case 25:
									moveMapAndCursor(10, 10);
									break;
								case 26:
									Unit.createUnit((byte)0, (byte)1, 10, 10).plotRouteEx(10, 9, false, true);
									setScriptDelay(10);
									++scriptState;
									break;
								case 27:
									moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
									break;
								case 28:
									createDialogScreen(PaintableObject.getLocaleString(225), (byte)5, (byte)4);
									setScriptDelay(5);
									++scriptState;
									break;
								case 29:
									createDialogScreen(PaintableObject.getLocaleString(226), (byte)0, (byte)4);
									activeFlag = true;
									++scriptState;
									break;
								case 30:
									if(countUnits(-1, -1, (byte)1) == 0 && gameState == 0)
									{
										activeFlag = false;
										drawCursorFlag = false;
										setScriptDelay(30);
										++scriptState;
									}
									break;
								case 31:
									drawAlphaDrap = true;
									drapAlphaValue = 0;
									setScriptDelay(20);
									++scriptState;
									break;
								case 32:
									createDialogScreen(PaintableObject.getLocaleString(227), (byte)2, (byte)4);
									setScriptDelay(10);
									++scriptState;
									break;
								case 33:
									createDialogScreen(PaintableObject.getLocaleString(228), (byte)0, (byte)4);
									setScriptDelay(5);
									++scriptState;
									break;
								case 34:
									createDialogScreen(PaintableObject.getLocaleString(229), (byte)2, (byte)4);
									setScriptDelay(5);
									++scriptState;
									break;
								case 35:
									createDialogScreen(PaintableObject.getLocaleString(230), (byte)0, (byte)4);
									setScriptDelay(15);
									++scriptState;
									break;
								case 36:
									completeMission();
							}
						}
						else if(currentMission == 1)
						{
							switch(scriptState)
							{
								case 1:
									setScriptDelay(10);
									++scriptState;
									break;
								case 2:
									mapStepMax = 4;
									moveMapAndCursor(12, 3);
									break;
								case 3:
									createDialogScreen(PaintableObject.getLocaleString(231), (byte)1, (byte)4);
									setScriptDelay(10);
									++scriptState;
									break;
								case 4:
									createDialogScreen(PaintableObject.getLocaleString(232), (byte)3, (byte)4);
									moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
									break;
								case 5:
									createDialogScreen(PaintableObject.getLocaleString(233), (byte)5, (byte)4);
									moveMapAndCursor(7, 3);
									break;
								case 6:
									Unit.speed = 2;

									crystallEscortLeader = Unit.createUnit((byte)0, (byte)1, 7, 3);
									crystallEscortCrystall = Unit.createUnit((byte)11, (byte)1, 7, 3);
									crystallEscortFollower = Unit.createUnit((byte)0, (byte)1, 7, 3);

									crystallEscortLeader.followerUnit = crystallEscortCrystall;
									crystallEscortCrystall.followerUnit = crystallEscortFollower;

									crystallEscortLeader.plotRoute(6, -2, false);

									setScriptDelay(30);
									++scriptState;
									break;
								case 7:
									if(crystallEscortFollower.currentMapPosX == 6 && crystallEscortFollower.currentMapPosY == 1)
									{
										Unit.speed = 4;
										templeWarrior = Unit.createUnit((byte)0, (byte)0, 7, 3);
										templeWarrior.plotRoute(6, 2, false);
										crystallEscortCrystall.followerUnit = null;
										createDialogScreen(PaintableObject.getLocaleString(234), (byte)2, (byte)4);
										++scriptState;
									}
									break;
								case 8:
									if(templeWarrior.unitState != 1)
									{
										Renderer.vibrate(100);
										templeWarrior.scheduleIdleAnimationStop(400);
										createSimpleSparkSprite(sprRedSpark, templeWarrior.currentX, templeWarrior.currentY, 0, 0, 2, 50);
										setScriptDelay(10);
										++scriptState;
									}
									break;
								case 9:
									createDialogScreen(PaintableObject.getLocaleString(235), (byte)2, (byte)4);
									setScriptDelay(5);
									++scriptState;
									break;
								case 10:
									createSimpleSparkSprite(sprSmoke, templeWarrior.currentX, templeWarrior.currentY, 0, -3, 1, 100);
									createSimpleSparkSprite(sprSpark, templeWarrior.currentX, templeWarrior.currentY, 0, 0, 1, 50);
									templeWarrior.removeThisUnit();
									templeWarrior = null;
									setScriptDelay(15);
									++scriptState;
									break;
								case 11:
									Unit.speed = Unit.UNIT_SPEED_SLOW;
									crystallEscortLeader.removeThisUnit();
									crystallEscortCrystall.removeThisUnit();
									crystallEscortLeader = null;
									crystallEscortCrystall = null;
									crystallEscortFollower = null;
									mapStepMax = 12;
									moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
									break;
								case 12:
									createDialogScreen(PaintableObject.getLocaleString(236), (byte)5, (byte)4);
									++scriptState;
									break;
								case 13:
									createDialogScreen(PaintableObject.getLocaleString(237), (byte)1, (byte)4);
									++scriptState;
									break;
								case 14:
									moveMapAndCursor(3, 5);
									break;
								case 15:
									createDialogScreen(PaintableObject.getLocaleString(238), (byte)5, (byte)4);
									++scriptState;
									break;
								case 16:
									moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
									break;
								case 17:
									currentHelp = 8;
									++scriptState;
									break;
								case 18:
									currentHelp = 9;
									++scriptState;
									break;
								case 19:
									startNormalPlay();
									++scriptState;
									break;
								case 20:
									if(gameState == 9 && currentTurningPlayer == 0)
									{
										currentHelp = 10;
										++scriptState;
									}
									break;
								case 21:
									currentHelp = 11;
									++scriptState;
									break;
								case 22:
									if(fractionKings[0].unitState == 3 || fractionKings[1].unitState == 3)
									{
										currentHelp = 12;
										++scriptState;
									}
									break;
								case 23:
									if(countUnits(-1, -1, (byte)1) == 0 && countCastles(1) == 0)
									{
										activeFlag = false;
										setScriptDelay(20);
										++scriptState;
									}
									break;
								case 24:
									moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
									break;
								case 25:
									createDialogScreen(PaintableObject.getLocaleString(239), (byte)1, (byte)4);
									++scriptState;
									break;
								case 26:
									createDialogScreen(PaintableObject.getLocaleString(240), (byte)5, (byte)4);
									setScriptDelay(10);
									++scriptState;
									break;
								case 27:
									completeMission();
							}
						}
						else
						{
							Unit var3;
							Unit var4;
							Unit[] var14;
							if(currentMission == 2)
							{
						 label597:
								switch(scriptState)
								{
									case 0:
										if(crystallEscortLeader.unitState != 1)
										{
											crystallEscortLeader.plotRoute(7, 14, false);
											++scriptState;
										}
										break;
									case 1:
										if(crystallEscortCrystall.unitState != 1)
										{
											crystallEscortCrystall.plotRoute(7, 15, false);
											setScriptDelay(20);
											++scriptState;
										}
										break;
									case 2:
										Unit.speed = Unit.UNIT_SPEED_SLOW;
										crystallEscortLeader = null;
										crystallEscortCrystall = null;
										crystallEscortFollower = null;
										createDialogScreen(PaintableObject.getLocaleString(241), (byte)5, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 3:
										createDialogScreen(PaintableObject.getLocaleString(242), (byte)0, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 4:
										createDialogScreen(PaintableObject.getLocaleString(243), (byte)5, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 5:
										currentHelp = 14;
										++scriptState;
										break;
									case 6:
										startNormalPlay();
										++scriptState;
										break;
									case 7:
									case 8:
										if(scriptState == 7 && currentTurningPlayer == 0 && statusBarOffset == 0 && countUnits(-1, 3, (byte)-1) >= 1)
										{
											currentHelp = 15;
											++scriptState;
										}

										var14 = listUnits(-1, 2, (byte)0);
										var2 = 0;

										while(true)
										{
											if(var2 >= var14.length)
											{
												break label597;
											}

											if(var14[var2].currentMapPosX <= 4 || var14[var2].currentMapPosY <= 10)
											{
												setScriptDelay(10);
												activeFlag = false;
												drawCursorFlag = false;
												scriptState = 9;
												break label597;
											}

											++var2;
										}
									case 9:
										moveMapAndCursor(0, 8);
										Unit.createUnit((byte)5, (byte)1, -1, 8).plotRoute(0, 8, false);
										Unit.createUnit((byte)5, (byte)1, -2, 7).plotRoute(1, 7, false);
										setScriptDelay(20);
										break;
									case 10:
										moveMapAndCursor(8, 6);
										Unit.createUnit((byte)5, (byte)1, 12, 6).plotRoute(8, 6, false);
										setScriptDelay(20);
										break;
									case 11:
										moveMapAndCursor(2, 1);
										Unit.createUnit((byte)5, (byte)1, 1, -2).plotRoute(1, 2, false);
										Unit.createUnit((byte)5, (byte)1, 3, -2).plotRoute(3, 2, false);
										Unit.createUnit((byte)4, (byte)1, 2, -1).plotRoute(2, 1, false);
										setScriptDelay(20);
										break;
									case 12:
										createDialogScreen(PaintableObject.getLocaleString(244), (byte)5, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 13:
										moveMapAndCursor(4, 8);
										setScriptDelay(15);
										break;
									case 14:
										var11 = Unit.createUnit((byte)2, (byte)1, 3, 8);
										var3 = Unit.createUnit((byte)2, (byte)1, 4, 7);
										var4 = Unit.createUnit((byte)2, (byte)1, 5, 8);
										createSimpleSparkSprite(sprSpark, var11.currentX, var11.currentY, 0, 0, 1, 50);
										createSimpleSparkSprite(sprSpark, var3.currentX, var3.currentY, 0, 0, 1, 50);
										createSimpleSparkSprite(sprSpark, var4.currentX, var4.currentY, 0, 0, 1, 50);
										setScriptDelay(10);
										++scriptState;
										break;
									case 15:
										createDialogScreen(PaintableObject.getLocaleString(245), (byte)5, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 16:
										createDialogScreen(PaintableObject.getLocaleString(246), (byte)-1, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 17:
										createDialogScreen(PaintableObject.getLocaleString(247), (byte)0, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 18:
										PaintableObject.currentRenderer.setCurrentDisplayable(createMessageScreen((String)null, PaintableObject.getLocaleString(248), height_, 1500));
										++scriptState;
										break;
									case 19:
										getUnit(3, 8, (byte)0).removeThisUnit();
										getUnit(4, 7, (byte)0).removeThisUnit();
										getUnit(5, 8, (byte)0).removeThisUnit();
										var11 = Unit.createUnit((byte)2, (byte)0, 3, 8);
										var3 = Unit.createUnit((byte)2, (byte)0, 4, 7);
										var4 = Unit.createUnit((byte)2, (byte)0, 5, 8);
										createSimpleSparkSprite(sprSpark, var11.currentX, var11.currentY, 0, 0, 1, 50);
										createSimpleSparkSprite(sprSpark, var3.currentX, var3.currentY, 0, 0, 1, 50);
										createSimpleSparkSprite(sprSpark, var4.currentX, var4.currentY, 0, 0, 1, 50);
										setScriptDelay(10);
										++scriptState;
										break;
									case 20:
										activeFlag = true;
										drawCursorFlag = true;
										currentHelp = 13;
										++scriptState;
										break;
									case 21:
										if(countUnits(-1, -1, (byte)1) == 0)
										{
											activeFlag = false;
											setScriptDelay(10);
											++scriptState;
										}
										break;
									case 22:
										completeMission();
								}
							}
							else if(currentMission == 3)
							{
								switch(scriptState)
								{
									case 0:
										PaintableObject.currentRenderer.setCurrentDisplayable(auxMapNameMessage);
										updateAlphaDrap = true;
										++scriptState;
										break;
									case 1:
										if(fractionKings[0].unitState != 1)
										{
											var_31be = null;
											setScriptDelay(20);
											++scriptState;
										}
										break;
									case 2:
										drawAlphaDrap = true;
										updateAlphaDrap = true;
										drapAlphaValue = 0;
										setScriptDelay(20);
										++scriptState;
										break;
									case 3:
										createDialogScreen(PaintableObject.getLocaleString(249), (byte)2, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 4:
										createDialogScreen(PaintableObject.getLocaleString(250), (byte)0, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 5:
										createDialogScreen(PaintableObject.getLocaleString(251), (byte)5, (byte)4);
										moveCursor(13, 3);
										moveMapShowPointPixels(312, 72);
										fractionKings[0].setMapPosition(7, 1);
										crystallEscortLeader.setMapPosition(5, 4);
										crystallEscortCrystall.setMapPosition(7, 5);
										crystallEscortFollower.setMapPosition(3, 3);
										crystallEscortLeader = null;
										crystallEscortCrystall = null;
										crystallEscortFollower = null;
										mapStepMax = 2;
										setScriptDelay(5);
										++scriptState;
										break;
									case 6:
										drawAlphaDrap = false;
										inverseDrapAlpha = true;
										drapAlphaValue = 0;
										setScriptDelay(5);
										++scriptState;
										break;
									case 7:
										moveMapAndCursor(13, 10);
										break;
									case 8:
										Unit.speed = 2;
										mapStepMax = 4;
										crystallEscortLeader = getUnit(10, 10, (byte)0);
										crystallEscortLeader.plotRoute(6, 10, false);
										moveCursor(6, 10);
										moveMapAndCursor(6, 10);
										break;
									case 9:
										if(crystallEscortLeader.unitState != 1)
										{
											crystallEscortLeader = null;
											drawCursorFlag = true;
											setScriptDelay(10);
											++scriptState;
										}
										break;
									case 10:
										moveCursor(4, 9);
										moveMapAndCursor(4, 9);
										createSimpleSparkSprite(sprRedSpark, 96, 216, 0, 0, 1, 50);
										setScriptDelay(15);
										break;
									case 11:
										var_16e5 = Unit.createUnitEx((byte)0, (byte)0, 4, 9, false);
										var_16e5.type = -1;
										var_16e5.unitState = 4;
										var_17d5 = 6;
										setScriptDelay(20);
										++scriptState;
										break;
									case 12:
										createDialogScreen(PaintableObject.getLocaleString(252), (byte)5, (byte)4);
										moveMapAndCursor(7, 1);
										break;
									case 13:
										createDialogScreen(PaintableObject.getLocaleString(253), (byte)0, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 14:
										currentHelp = 17;
										++scriptState;
										break;
									case 15:
										startNormalPlay();
										++scriptState;
										break;
									case 16:
										if(countUnits(-1, -1, (byte)1) == 0 && countCastles(1) == 0)
										{
											setScriptDelay(15);
											activeFlag = false;
											++scriptState;
										}
										break;
									case 17:
										createDialogScreen(PaintableObject.getLocaleString(254), (byte)5, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 18:
										createDialogScreen(PaintableObject.getLocaleString(255), (byte)0, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 19:
										completeMission();
								}
							}
							else if(currentMission == 4)
							{
								if(var_3c5c == null)
								{
									var_3c5c = listUnits(11, -1, (byte)0)[0];
								}

								if(scriptState == 25 && var_3c5c.currentMapPosX >= 15 && var_3c5c.currentMapPosY >= 11 && var_3c5c.unitState == 2)
								{
									setScriptDelay(10);
									activeFlag = false;
									drawCursorFlag = false;
									scriptState = 26;
									return;
								}

								if(var_3c5c.unitState == 3)
								{
									var_3c5c = null;
									sub_131d();
									return;
								}

						 label580:
								switch(scriptState)
								{
									case 0:
										setScriptDelay(50);
										++scriptState;
										break;
									case 1:
										createDialogScreen(PaintableObject.getLocaleString(256), (byte)5, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 2:
										createDialogScreen(PaintableObject.getLocaleString(257), (byte)0, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 3:
										startNormalPlay();
										++scriptState;
										break;
									case 4:
										var14 = listUnits(-1, -1, (byte)0);
										var2 = 0;

										while(true)
										{
											if(var2 >= var14.length)
											{
												break label580;
											}

											if(var14[var2].unitState == 2 && var14[var2].currentMapPosX <= 8)
											{
												activeFlag = false;
												setScriptDelay(5);
												++scriptState;
												break label580;
											}

											++var2;
										}
									case 5:
										drawCursorFlag = false;
										moveMapAndCursor(4, 4);
										break;
									case 6:
										Unit.createUnit((byte)10, (byte)1, 4, 4).plotRouteEx(4, 1, false, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 7:
										Unit.createUnit((byte)1, (byte)1, 4, 4).plotRouteEx(5, 2, false, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 8:
										Unit.createUnit((byte)10, (byte)1, 4, 4).plotRouteEx(4, 3, false, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 9:
										createDialogScreen(PaintableObject.getLocaleString(258), (byte)5, (byte)4);
										moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
										break;
									case 10:
										activeFlag = true;
										drawCursorFlag = true;
										++scriptState;
										break;
									case 11:
										var14 = listUnits(-1, -1, (byte)0);
										var2 = 0;

										while(true)
										{
											if(var2 >= var14.length)
											{
												break label580;
											}

											if(var14[var2].unitState == 2 && var14[var2].currentMapPosY >= 7)
											{
												activeFlag = false;
												drawCursorFlag = false;
												moveMapAndCursor(6, 10);
												break label580;
											}

											++var2;
										}
									case 12:
										Unit.createUnit((byte)1, (byte)1, 6, 10).plotRouteEx(5, 10, false, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 13:
										Unit.createUnit((byte)5, (byte)1, 6, 10).plotRouteEx(7, 8, false, true);
										setScriptDelay(15);
										++scriptState;
										break;
									case 14:
										Unit.createUnit((byte)5, (byte)1, 6, 10).plotRouteEx(7, 9, false, true);
										activeFlag = true;
										drawCursorFlag = true;
										++scriptState;
										break;
									case 15:
										var14 = listUnits(-1, -1, (byte)0);
										var2 = 0;

										while(true)
										{
											if(var2 >= var14.length)
											{
												break label580;
											}

											if(var14[var2].unitState == 2 && var14[var2].currentMapPosX >= 8 && var14[var2].currentMapPosY >= 6)
											{
												activeFlag = false;
												drawCursorFlag = false;
												moveMapAndCursor(12, 5);
												break label580;
											}

											++var2;
										}
									case 16:
										Unit.createUnit((byte)5, (byte)1, 12, 5).plotRouteEx(12, 7, false, true);
										setScriptDelay(15);
										++scriptState;
										break;
									case 17:
										Unit.createUnit((byte)6, (byte)1, 12, 5).plotRouteEx(12, 6, false, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 18:
										Unit.createUnit((byte)5, (byte)1, 12, 5).plotRouteEx(12, 5, false, true);
										activeFlag = true;
										drawCursorFlag = true;
										++scriptState;
										break;
									case 19:
										var14 = listUnits(-1, -1, (byte)0);
										var2 = 0;

										while(true)
										{
											if(var2 >= var14.length)
											{
												break label580;
											}

											if(var14[var2].unitState == 2 && var14[var2].currentMapPosX >= 15 && var14[var2].currentMapPosY >= 8)
											{
												activeFlag = false;
												drawCursorFlag = false;
												moveMapAndCursor(18, 8);
												break label580;
											}

											++var2;
										}
									case 20:
										Unit.createUnit((byte)5, (byte)1, 18, 8).plotRouteEx(16, 10, false, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 21:
										Unit.createUnit((byte)6, (byte)1, 18, 8).plotRouteEx(17, 10, false, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 22:
										Unit.createUnit((byte)5, (byte)1, 18, 8).plotRouteEx(18, 10, false, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 23:
										Unit.createUnit((byte)1, (byte)1, 18, 8).plotRouteEx(18, 9, false, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 24:
										createDialogScreen(PaintableObject.getLocaleString(259), (byte)0, (byte)4);
										activeFlag = true;
										drawCursorFlag = true;
										++scriptState;
										break;
									case 25:
										if(countUnits(-1, -1, (byte)1) == 0)
										{
											activeFlag = false;
											drawCursorFlag = false;
											setScriptDelay(10);
											++scriptState;
										}
										break;
									case 26:
										completeMission();
										++scriptState;
								}
							}
							else if(currentMission == 5)
							{
								switch(scriptState)
								{
									case 0:
										setScriptDelay(10);
										++scriptState;
										break;
									case 1:
										createDialogScreen(PaintableObject.getLocaleString(260), (byte)1, (byte)4);
										++scriptState;
										break;
									case 2:
										startNormalPlay();
										++scriptState;
										break;
									case 3:
										if(countUnits(-1, -1, (byte)1) == 0 && countCastles(1) == 0)
										{
											activeFlag = false;
											drawCursorFlag = false;
											setScriptDelay(15);
											++scriptState;
										}
										break;
									case 4:
										moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
										break;
									case 5:
										createDialogScreen(PaintableObject.getLocaleString(261), (byte)0, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 6:
										completeMission();
								}
							}
							else if(currentMission == 6)
							{
								if(scriptState <= 10)
								{
									if(var_3c5c == null)
									{
										var_3c5c = listUnits(11, -1, (byte)0)[0];
									}

									if(var_3c5c.unitState == 3)
									{
										var_3c5c = null;
										sub_131d();
										return;
									}
								}

								switch(scriptState)
								{
									case 0:
										if(fractionKings[0].unitState != 1)
										{
											var_31be = null;
											setScriptDelay(10);
											++scriptState;
										}
										break;
									case 1:
										createDialogScreen(PaintableObject.getLocaleString(262), (byte)5, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 2:
										createDialogScreen(PaintableObject.getLocaleString(263), (byte)0, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 3:
										var14 = listUnits(-1, -1, (byte)0);

										for(var2 = 0; var2 < var14.length; ++var2)
										{
											var14[var2].followerUnit = null;
										}

										startNormalPlay();
										++scriptState;
										break;
									case 4:
										if(currentTurn >= 2)
										{
											setScriptDelay(15);
											activeFlag = false;
											drawCursorFlag = false;
											moveMapAndCursor(11, 7);
										}
										break;
									case 5:
										(var11 = Unit.createUnit((byte)5, (byte)1, 11, 8)).fillMoveRangeData(mapAlphaData);
										var11.plotRoute(14, 7, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 6:
										(var3 = Unit.createUnit((byte)0, (byte)1, 11, 8)).fillMoveRangeData(mapAlphaData);
										var3.plotRoute(13, 7, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 7:
										(var4 = Unit.createUnit((byte)3, (byte)1, 11, 8)).fillMoveRangeData(mapAlphaData);
										var4.plotRoute(12, 7, true);
										setScriptDelay(10);
										++scriptState;
										break;
									case 8:
										Unit.createUnit((byte)1, (byte)1, 11, 8).plotRoute(13, 8, false);
										setScriptDelay(20);
										++scriptState;
										break;
									case 9:
										createDialogScreen(PaintableObject.getLocaleString(264), (byte)5, (byte)4);
										activeFlag = true;
										drawCursorFlag = true;
										moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
										break;
									case 10:
										boolean var6 = false;
										Unit[] var7 = listUnits(-1, 2, (byte)0);

										for(int var15 = 0; var15 < var7.length; ++var15)
										{
											if(var7[var15].currentMapPosX <= 9 || var7[var15].currentMapPosY >= 10)
											{
												var6 = true;
												break;
											}
										}

										if(var6 || countUnits(-1, -1, (byte)1) == 0)
										{
											drawCursorFlag = false;
											activeFlag = false;
											setScriptDelay(10);
											++scriptState;
										}
										break;
									case 11:
										crystallEscortLeader = listUnits(11, -1, (byte)0)[0];
										crystallEscortCrystall = Unit.createUnit((byte)8, (byte)1, mapWidth, crystallEscortLeader.currentMapPosY);
										moveMapAndCursor(mapWidth - 1, crystallEscortLeader.currentMapPosY);
										drawCursorFlag = false;
										break;
									case 12:
										crystallEscortCrystall.plotRoute(crystallEscortLeader.currentMapPosX, crystallEscortLeader.currentMapPosY, false);
										setScriptDelay(5);
										++scriptState;
										break;
									case 13:
										createDialogScreen(PaintableObject.getLocaleString(265), (byte)5, (byte)4);
										var_31be = crystallEscortCrystall;
										++scriptState;
										break;
									case 14:
										if(crystallEscortCrystall.unitState != 1)
										{
											createDialogScreen(PaintableObject.getLocaleString(266), (byte)0, (byte)4);
											crystallEscortCrystall.plotRoute(-1, crystallEscortCrystall.currentMapPosY, false);
											setScriptDelay(3);
											++scriptState;
										}
										break;
									case 15:
										crystallEscortLeader.plotRoute(-1, crystallEscortCrystall.currentMapPosY, false);
										++scriptState;
										break;
									case 16:
										if(crystallEscortCrystall.unitState != 1)
										{
											setScriptDelay(10);
											crystallEscortLeader.removeThisUnit();
											crystallEscortCrystall.removeThisUnit();
											moveCursor(0, crystallEscortCrystall.currentMapPosY);
											crystallEscortLeader = null;
											crystallEscortCrystall = null;
											var_31be = null;
											++scriptState;
										}
										break;
									case 17:
										moveMapAndCursor(1, 9);
										break;
									case 18:
										fractionKings[1] = Unit.createUnit((byte)9, (byte)1, -2, 8);
										fractionKings[1].plotRoute(0, 8, false);
										Unit.createUnit((byte)0, (byte)1, -1, 8).plotRoute(3, 8, false);
										Unit.createUnit((byte)0, (byte)1, -1, 10).plotRoute(1, 10, false);
										Unit.createUnit((byte)8, (byte)1, -3, 7).plotRoute(4, 8, false);
										Unit.createUnit((byte)8, (byte)1, -3, 11).plotRoute(2, 10, false);
										Unit.createUnit((byte)4, (byte)1, -2, 9).plotRoute(2, 9, false);
										Unit.createUnit((byte)6, (byte)1, -4, 9).plotRoute(4, 9, false);
										Unit.createUnit((byte)6, (byte)1, -6, 9).plotRoute(5, 10, false);
										setScriptDelay(50);
										++scriptState;
										break;
									case 19:
										createDialogScreen(PaintableObject.getLocaleString(267), (byte)3, (byte)4);
										++scriptState;
										break;
									case 20:
										moveMapAndCursor(13, 14);
										break;
									case 21:
										Unit.createUnit((byte)0, (byte)1, 13, 14).plotRoute(12, 14, false);
										setScriptDelay(5);
										++scriptState;
										break;
									case 22:
										Unit.createUnit((byte)6, (byte)1, 13, 14).plotRoute(14, 14, false);
										setScriptDelay(5);
										++scriptState;
										break;
									case 23:
										Unit.createUnit((byte)2, (byte)1, 13, 14).plotRoute(13, 12, false);
										setScriptDelay(5);
										++scriptState;
										break;
									case 24:
										Unit.createUnit((byte)3, (byte)1, 13, 14).plotRoute(13, 15, false);
										setScriptDelay(15);
										++scriptState;
										break;
									case 25:
										createDialogScreen(PaintableObject.getLocaleString(268), (byte)5, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 26:
										createDialogScreen(PaintableObject.getLocaleString(269), (byte)0, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 27:
										moveMapAndCursor(13, 17);
										break;
									case 28:
										Unit var8;
										(var8 = Unit.createUnit((byte)9, (byte)0, 13, 18)).setKingVariant(2);
										var8.plotRoute(13, 16, false);
										Unit.createUnit((byte)6, (byte)0, 12, 18).plotRoute(12, 16, false);
										Unit.createUnit((byte)8, (byte)0, 14, 19).plotRoute(14, 16, false);
										Unit.createUnit((byte)4, (byte)0, 13, 19).plotRoute(13, 17, false);
										Unit.createUnit((byte)1, (byte)0, 12, 19).plotRoute(12, 17, false);
										setScriptDelay(20);
										++scriptState;
										break;
									case 29:
										createDialogScreen(PaintableObject.getLocaleString(270), (byte)1, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 30:
										moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
										break;
									case 31:
										currentHelp = 18;
										++scriptState;
										break;
									case 32:
										activeFlag = true;
										drawCursorFlag = true;
										auxObjectivesMessage = createMessageScreen(PaintableObject.getLocaleString(121 + currentMission), PaintableObject.getLocaleString(138), height_, -1);
										auxObjectivesMessage.setDrawSoftButtonFlag((byte)0, true);
										auxObjectivesMessage.setNextDisplayable((PaintableObject)null);
										PaintableObject.currentRenderer.setCurrentDisplayable(auxObjectivesMessage);
										++scriptState;
										break;
									case 33:
										if(countUnits(-1, -1, (byte)1) == 0 && countCastles(1) == 0)
										{
											activeFlag = false;
											drawCursorFlag = false;
											setScriptDelay(10);
											++scriptState;
										}
										break;
									case 34:
										completeMission();
								}
							}
							else if(currentMission == 7)
							{
								switch(scriptState)
								{
									case 0:
										PaintableObject.currentRenderer.setCurrentDisplayable(auxMapNameMessage);
										updateAlphaDrap = true;
										++scriptState;
										break;
									case 1:
										if(!inverseDrapAlpha)
										{
											createDialogScreen(PaintableObject.getLocaleString(271), (byte)0, (byte)4);
											++scriptState;
										}
										break;
									case 2:
										moveMapAndCursor(fractionKings[1].currentMapPosX, fractionKings[1].currentMapPosY);
										break;
									case 3:
										createDialogScreen(PaintableObject.getLocaleString(272), (byte)4, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 4:
										createDialogScreen(PaintableObject.getLocaleString(273), (byte)1, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 5:
										createDialogScreen(PaintableObject.getLocaleString(274), (byte)4, (byte)4);
										++scriptState;
										break;
									case 6:
										crystallEscortLeader = getUnit(5, 2, (byte)0);
										crystallEscortLeader.plotRoute(7, 2, false);
										++scriptState;
										break;
									case 7:
										if(crystallEscortLeader.unitState != 1)
										{
											crystallEscortLeader.removeThisUnit();
											crystallEscortLeader = null;
											crystallEscortCrystall = getUnit(7, 3, (byte)0);
											crystallEscortCrystall.plotRoute(7, 2, false);
											++scriptState;
										}
										break;
									case 8:
										if(crystallEscortCrystall.unitState != 1)
										{
											crystallEscortCrystall.removeThisUnit();
											crystallEscortCrystall = null;
											crystallEscortFollower = getUnit(9, 2, (byte)0);
											crystallEscortFollower.plotRoute(7, 2, false);
											++scriptState;
										}
										break;
									case 9:
										if(crystallEscortFollower.unitState != 1)
										{
											crystallEscortFollower.removeThisUnit();
											crystallEscortFollower = null;
											fractionKings[1].plotRoute(7, 2, false);
											setScriptDelay(20);
											++scriptState;
										}
										break;
									case 10:
										moveMapAndCursor(9, 15);
										drawCursorFlag = true;
										break;
									case 11:
										AuxDisplayable var9;
										(var9 = createMessageScreen((String)null, PaintableObject.getLocaleString(279), height_, 2000)).setPosition(halfWidth_, 2, 17);
										PaintableObject.currentRenderer.setCurrentDisplayable(var9);
										++scriptState;
										break;
									case 12:
										var_29c1 = false;
										sub_26e(getUnit(9, 15, (byte)0));
										++scriptState;
										break;
									case 13:
										if(var_2985 >= 2)
										{
											createDialogScreen(PaintableObject.getLocaleString(275), (byte)0, (byte)4);
											getUnit(9, 15, (byte)0).removeThisUnit();
											var_29c1 = true;
											setScriptDelay(20);
											++scriptState;
										}
										break;
									case 14:
										drawCursorFlag = false;
										createDialogScreen(PaintableObject.getLocaleString(276), (byte)4, (byte)4);
										++scriptState;
										break;
									case 15:
										createDialogScreen(PaintableObject.getLocaleString(277), (byte)1, (byte)4);
										setScriptDelay(10);
										++scriptState;
										break;
									case 16:
										drawCursorFlag = true;
										mapStepMax = 4;
										moveMapAndCursor(3, 9);
										break;
									case 17:
										moveMapAndCursor(13, 4);
										break;
									case 18:
										mapStepMax = 12;
										createDialogScreen(PaintableObject.getLocaleString(278), (byte)5, (byte)4);
										moveMapAndCursor(fractionKings[0].currentMapPosX, fractionKings[0].currentMapPosY);
										break;
									case 19:
										startNormalPlay();
										++scriptState;
										break;
									case 20:
										if(var_29de)
										{
											drawCursorFlag = false;
											activeFlag = false;
											setScriptDelay(20);
											++scriptState;
										}
										break;
									case 21:
										drawAlphaDrap = true;
										drapAlphaValue = 0;
										updateAlphaDrap = true;
										++scriptState;
										break;
									case 22:
										if(drapAlphaValue >= 16)
										{
											moveMapShowPoint(7, 2);
											moveCursor(7, 2);
											sub_58e();
											fractionKings[1] = Unit.createUnit((byte)9, (byte)1, 7, 2);
											fractionKings[1].setKingVariant(3);
											Unit.createUnit((byte)9, (byte)0, 6, 3);
											Unit.createUnit((byte)9, (byte)0, 8, 3).setKingVariant(2);
											Unit.createUnit((byte)0, (byte)0, 6, 1);
											Unit.createUnit((byte)0, (byte)0, 8, 1);
											setScriptDelay(10);
											++scriptState;
											Renderer.startPlayer_(8, 0);
										}
										break;
									case 23:
										drawAlphaDrap = false;
										inverseDrapAlpha = true;
										drapAlphaValue = 0;
										++scriptState;
										break;
									case 24:
										if(drapAlphaValue >= 16)
										{
											createDialogScreen(PaintableObject.getLocaleString(281), (byte)4, (byte)4);
											setScriptDelay(15);
											++scriptState;
										}
										break;
									case 25:
										createDialogScreen(PaintableObject.getLocaleString(282), (byte)0, (byte)4);
										setScriptDelay(8);
										++scriptState;
										break;
									case 26:
										createDialogScreen(PaintableObject.getLocaleString(283), (byte)4, (byte)4);
										setScriptDelay(15);
										++scriptState;
										break;
									case 27:
										createSimpleSparkSprite(sprSpark, 168, 48, 0, 0, 1, 50);
										units.removeElement(fractionKings[1]);
										setScriptDelay(15);
										++scriptState;
										break;
									case 28:
										Renderer.vibrate(400);
										shakeMap(5000);
										setScriptDelay(10);
										++scriptState;
										break;
									case 29:
										createDialogScreen(PaintableObject.getLocaleString(284), (byte)5, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 30:
										createDialogScreen(PaintableObject.getLocaleString(285), (byte)2, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 31:
										createDialogScreen(PaintableObject.getLocaleString(286), (byte)1, (byte)4);
										setScriptDelay(5);
										++scriptState;
										break;
									case 32:
										createDialogScreen(PaintableObject.getLocaleString(287), (byte)0, (byte)4);
										drawAlphaDrap = true;
										drapAlphaValue = 0;
										++scriptState;
										break;
									case 33:
										if(drapAlphaValue >= 16)
										{
											setScriptDelay(10);
											shakeMapFlag = false;
											++scriptState;
										}
										break;
									case 34:
										setIntroMode(5, 2, 2);
										++scriptState;
										break;
									case 35:
										Renderer.startPlayer_(0, 0);
										sub_a6b(PaintableObject.getLocaleString(288));
										++scriptState;
										break;
									case 36:
										var_3e0c = 0;
										gameState = 14;
										++scriptState;
								}
							}
						}
					}

					lastFinishedMoveUnit = null;
				}
			}
		}
	}

	public final void startNormalPlay()
	{
		auxMapNameMessage = null;
		mapStepMax = 12;
		Unit.speed = Unit.UNIT_SPEED_SLOW;
		auxObjectivesMessage.setDrawSoftButtonFlag((byte)0, true);
		auxObjectivesMessage.setDrawSoftButtonFlag((byte)1, false);
		PaintableObject.currentRenderer.setCurrentDisplayable(auxObjectivesMessage);
		drawCursorFlag = true;
		activeFlag = true;
		var_2319 = false;
		Renderer.startPlayer_(2, 0);
	}

	public final void moveMapAndCursor(int var1, int var2)
	{
		mapTargetX = var1;
		mapTargetY = var2;
		moveCursor(var1, var2);
		++scriptState;
	}

	public final void completeMission()
	{
		Renderer.stopCurrentPlayer();
		Renderer.startPlayer(6, 1);
		PaintableObject.currentRenderer.setCurrentDisplayable(createMessageScreen((String)null, PaintableObject.getLocaleString(72), height_, 3000));
		var_12ff = delayCounter;
		scriptState = -1;
		var_3e0c = 0;
		gameState = 10;
	}

	public final void sub_131d()
	{
		var_3d57 = true;
		scriptState = 0;
		setScriptDelay(20);
		Renderer.stopCurrentPlayer();
		Renderer.startPlayer(7, 1);
	}

	public final void createCombatAnimation(Unit attackerUnit, Unit victimUnit)
	{
		try
		{
			System.gc();

			var_4138 = height_ - var_3622;
			combatDrawDrapFlag = true;
			combatDrapValue = 0;
			var_3f02 = false;

			currentAttackUnit = attackerUnit;
			currentAttackVictimUnit = victimUnit;

			Renderer.loadResources();

			combatAttackerUnitAnimation = new CombatAnimation(this, attackerUnit, (CombatAnimation)null);
			combatVictimUnitAnimation = new CombatAnimation(this, victimUnit, combatAttackerUnitAnimation);

			combatAttackerUnitAnimation.currentAttackerSideCombatAnimation = combatVictimUnitAnimation;

			attackerUnit.attackUnit(victimUnit);

			if(victimUnit.canPerformCloseAttack(attackerUnit, attackerUnit.currentMapPosX, attackerUnit.currentMapPosY))
			{
				victimUnit.attackUnit(attackerUnit);
				victimUnitResponded = true;
			}
			else
			{
				victimUnitResponded = false;
			}
	
			combatAttackerUnitAnimation.updatedUnitHealth = (byte)attackerUnit.health;
			combatAttackerUnitAnimation.updatedUnitCharCount = (byte)attackerUnit.getCharacterCount();

			combatVictimUnitAnimation.updatedUnitHealth = (byte)victimUnit.health;
			combatVictimUnitAnimation.updatedUnitCharCount = (byte)victimUnit.getCharacterCount();

			Renderer.startPlayer(FRACTION_BATTLE_MUSIC[fractionsTurnQueue[currentTurningPlayer]], 0);
			mode = 2;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public final void addCombatAnimationSprite(Sprite var1)
	{
		combatAnimationSprites.addElement(var1);
	}

	public final void removeCombatAnimationSprite(Sprite var1)
	{
		combatAnimationSprites.removeElement(var1);
	}

	public final void updateCombatAnimation()
	{
		if(shakeMapFlag && delayCounter - shakeMapDelayCounterStart >= shakeMapTime)
		{
			shakeMapFlag = false;
		}

		int var1;

		for(var1 = 0; var1 < combatAnimationSprites.size(); ++var1)
		{
			((Sprite)combatAnimationSprites.elementAt(var1)).update();
		}

		for(var1 = 0; var1 < combatAnimationSprites.size(); ++var1)
		{
			Sprite var2 = (Sprite)combatAnimationSprites.elementAt(var1);

			if(!var2.active)
			{
				removeCombatAnimationSprite(var2);
			}
		}

		combatAttackerUnitAnimation.update();
		combatVictimUnitAnimation.update();

		if(combatDrawDrapFlag)
		{
			++combatDrapValue;

			if(combatDrapValue >= 16)
			{
				combatDrawDrapFlag = false;
				combatAttackerUnitAnimation.sub_1f();
			}

			combatHeaderNeedsRepaint = true;
			combatFooterNeedsRepaint = true;
		}
		else
		{
			if(var_3f02)
			{
				if(delayCounter - var_3eaa >= 300L)
				{
					combatAnimationSprites.removeAllElements();
					combatVictimUnitAnimation = null;
					combatAttackerUnitAnimation = null;
					activeEffects = new Vector();
					finishAttack();
					mode = 1;
					Renderer.stopCurrentPlayer();
					Renderer.startPlayer(FRACTION_BACKGROUND_MUSIC[fractionsTurnQueue[currentTurningPlayer]], 0);
					PaintableObject.currentRenderer.clearKeyStates();
					statusBarNeedsRepaint = true;
					tileIconNeedsRepaint = true;
					return;
				}
			}
			else if(combatAttackerUnitAnimation.var_226)
			{
				if(victimUnitResponded && combatVictimUnitAnimation.updatedUnitHealth > 0)
				{
					if(!combatVictimUnitAnimation.var_1fd)
					{
						combatVictimUnitAnimation.sub_1f();
					}

					if(combatVictimUnitAnimation.var_226)
					{
						var_3f02 = true;
						var_3eaa = delayCounter;
						return;
					}
				}
				else
				{
					var_3f02 = true;
					var_3eaa = delayCounter;
				}
			}

		}
	}

	public final void paintCombatAnimation(Graphics g)
	{
		int tx = 0;
		int ty = 0;
		
		if(shakeMapFlag)
		{
			tx = Renderer.random() % 10;
			ty = Renderer.random() % 3;
		}

		g.translate(0, var_3622);
		g.setClip(0, 0, width_, var_4138);
		
		combatAttackerUnitAnimation.paintTerrain(g, tx, ty);
		combatVictimUnitAnimation.paintTerrain(g, tx + halfWidth_, ty);
		
		g.setColor(0);
		g.fillRect(halfWidth_ - 1 + tx, 0, 2, var_4138);
		
		combatAttackerUnitAnimation.paintShadows(g);
		combatVictimUnitAnimation.paintShadows(g);
		
		Vector vector = new Vector(combatAnimationSprites.size());

		int i;
		Sprite sprite;
		
		for(i = 0; i < combatAnimationSprites.size(); ++i)
		{
			sprite = (Sprite)combatAnimationSprites.elementAt(i);

			int j = 0;
			
			if(sprite.var_379)
			{
				vector.addElement(sprite);
			}
			else
			{
				for(j = 0; j < vector.size(); ++j)
				{
					Sprite var8 = (Sprite)vector.elementAt(j);
					
					if(var8.var_379 || sprite.currentY + sprite.height < var8.currentY + var8.height)
					{
						vector.insertElementAt(sprite, j);
						break;
					}
				}
			}

			if(j == vector.size())
			{
				vector.addElement(sprite);
			}
		}

		combatAnimationSprites = vector;

		for(i = 0; i < combatAnimationSprites.size(); ++i)
		{
			sprite = (Sprite)combatAnimationSprites.elementAt(i);

			if(sprite.var_3b9 == 0)
			{
				g.setClip(0, 0, halfWidth_, var_4138);
			}
			else if(sprite.var_3b9 == 1)
			{
				g.setClip(halfWidth_, 0, halfWidth_, var_4138);
			}
			else
			{
				g.setClip(0, 0, width_, var_4138);
			}

			sprite.paint(g, 0, sprite.bounceValue);
		}

		g.translate(0, -var_3622);
		g.setClip(0, 0, super.width, super.height);

		if(combatFooterNeedsRepaint)
		{
			combatFooterNeedsRepaint = false;
			i = super.height - FOOTER_HEIGHT;

			g.setColor(14672074);
			g.fillRect(0, i, super.width, FOOTER_HEIGHT);

			AuxDisplayable.drawMenuFrame(g, 0, i, super.width, FOOTER_HEIGHT, 0);

			g.setClip(0, 0, super.width, super.height);
			combatAttackerUnitAnimation.drawCurrentUnitHealth(g);

			g.translate(halfWidth_, 0);
			combatVictimUnitAnimation.drawCurrentUnitHealth(g);

			g.translate(-halfWidth_, 0);
		}

		if(combatHeaderNeedsRepaint)
		{
			combatHeaderNeedsRepaint = false;
			drawCombatHeader(g, combatAttackerUnitAnimation.currentUnit, combatVictimUnitAnimation.currentUnit, 0);
		}

		if(combatDrawDrapFlag)
		{
			drawDrap(g, 0, combatDrapValue, 16, 1, 0, 0, super.width, super.height);
		}

	}

	public final void shakeMap(int var1)
	{
		shakeMapFlag = true;
		shakeMapTime = (long)var1;
		shakeMapDelayCounterStart = delayCounter;
	}

	public final AuxDisplayable sub_14a5(String[] var1, PaintableObject var2)
	{
		AuxDisplayable var3;
		if(var_4217.length > 0)
		{
			AuxDisplayable var4;
			(var4 = (var3 = new AuxDisplayable((byte)11, 0)).createTitleAuxDisplayable(PaintableObject.getLocaleString(46))).titleIcon = sfmMenuIcons[6];
			var3.initVerticalListAuxDisplayable(var_4217, width_ / 2, (height_ + var4.currentHeight) / 2, width_, height_ - var4.currentHeight, 3, 4);
		}
		else
		{
			(var3 = new AuxDisplayable((byte)10, 0)).createTitleAuxDisplayable(PaintableObject.getLocaleString(46)).titleIcon = sfmMenuIcons[6];
			var3.initMsgAuxDisplayable((String)null, PaintableObject.getLocaleString(52), width_, -1);
		}

		var3.setNextDisplayable(var2);
		PaintableObject.currentRenderer.setCurrentDisplayable(var3);
		return var3;
	}

	public final void sub_14de(byte[] var1)
	{
		try
		{
			DataInputStream var2;
			(var2 = new DataInputStream(new ByteArrayInputStream(var1))).readLong();
			switch(var2.readInt())
			{
				case 10001:
					var2.readUTF();
					var2.readInt();
					var2.readUTF();
					var2.readInt();
					var2.readUTF();
					var2.readInt();
					var2.readUTF();
					int var6 = var2.readInt();
					var2.readUTF();
					var2.readUTF();
					int var7;
					if(var_44e6 == 0)
					{
						var7 = var2.readInt() / 2;
						var_4217 = new String[var7];
						var_4263 = new String[var7];
						var_4277 = new String[var7];
						boolean var8 = false;

						for(int var9 = 0; var9 < var7; ++var9)
						{
							var_4217[var9] = var2.readUTF();
							var_4263[var9] = var2.readUTF();
						}

						var_2df9 = sub_14a5(var_4217, var_44bb);
					}
					else if(var_44e6 == 1)
					{
						var2.readInt();
						var_4277[var_4191] = var2.readUTF();
						AuxDisplayable var16;
						(var16 = createMessageScreenEx(var_4217[var_4191], var_4277[var_4191], height_, height_ / 2, -1)).setNextDisplayable(var_44bb);
						PaintableObject.currentRenderer.setCurrentDisplayable(var16);
					}
					else if(var_44e6 == 2)
					{
						var7 = var2.readInt() / 3;
						var_4289 = new String[var7];
						var_42a0 = new String[var7];
						var_42c2 = new int[var7];

						for(int var11 = 0; var11 < var7; ++var11)
						{
							var_4289[var11] = var2.readUTF();
							var_42a0[var11] = var2.readUTF();
							var_42c2[var11] = Integer.parseInt(var2.readUTF());
						}

						createDownloadableSkirmishLevelsListAuxDisplayable(var_44bb);
						PaintableObject.currentRenderer.setCurrentDisplayable(auxDownloadableSkirmishLevelsContainer);
					}
					else if(var_44e6 == 3)
					{
						String var15 = var_4289[var_41ef];
						byte[] var12 = new byte[var6];
						var2.readFully(var12);
						addDownloadedSkirmishLevel(var15, var12);
						createDownloadableSkirmishLevelsListAuxDisplayable(auxDownloadableSkirmishLevelsContainer.nextDisplayable);
						AuxDisplayable var14;
						(var14 = createMessageScreen((String)null, PaintableObject.getReplacedLocaleString(45, var15), height_, 2000)).setNextDisplayable(auxDownloadableSkirmishLevelsContainer);
						PaintableObject.currentRenderer.setCurrentDisplayable(var14);
					}

					var_44bb = null;
					return;
				default:
					var2.close();
			}
		}
		catch(Exception var10)
		{
			var10.printStackTrace();
		}

		AuxDisplayable var13;
		(var13 = createMessageScreen((String)null, PaintableObject.getLocaleString(44), height_, -1)).setNextDisplayable(var_44bb);
		var_44bb = null;
		PaintableObject.currentRenderer.setCurrentDisplayable(var13);
	}

	public final void sub_1508(int var1, String var2, String var3, PaintableObject var4)
	{
		try
		{
			var_44e6 = var1;
			var_44bb = var4;
			var_4482 = false;
			var_43a8 = new ByteArrayOutputStream();
			var_43e4 = new DataOutputStream(var_43a8);
			var_43e4.writeInt(10001);
			var_43e4.writeUTF(provisionHighScorePortalCode);
			var_43e4.writeUTF(provisionHighScoreGameCode);
			var_442b = var_43a8.size();
			var_43e4.writeUTF("resourceName");
			var_43e4.writeUTF(var2);
			var_43e4.writeUTF("languageCode");
			var_43e4.writeUTF(var3);
			var_43e4.writeUTF("maxChunkSize");
			var_43e4.writeUTF("1024");
			var_43e4.writeUTF("chunk");
			var_43e4.writeUTF("0");
			var_43e4.writeUTF("requestId");
			var_43e4.writeUTF("0");
			AuxDisplayable var5;
			(var5 = createMessageScreen((String)null, PaintableObject.getLocaleString(43), height_, -1)).setNextDisplayable(var4);
			PaintableObject.currentRenderer.setCurrentDisplayable(var5);
			(new Thread(this)).start();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public final void run()
	{
		byte[] var1 = null;
		HttpConnection var2 = null;

		try
		{
			byte[] var3 = var_43a8.toByteArray();
			var_43a8.close();
			var_43e4.close();
			var2 = (HttpConnection)Connector.open(provisionHighScoreURL);

			if("J4d28g1Kl490".length() > 0)
			{
				byte[] var4 = "J4d28g1Kl490".getBytes();

				for(int var5 = var_442b; var5 < var3.length; ++var5)
				{
					var3[var5] ^= var4[var5 % var4.length];
				}
			}

			var2.setRequestMethod("POST");
			var2.setRequestProperty("Content-Length", Integer.toString(var3.length));
			var2.setRequestProperty("Connection", "close");
			OutputStream var20;
			(var20 = var2.openOutputStream()).write(var3);
			var20.flush();
			var20.close();
			DataInputStream var22 = var2.openDataInputStream();
			int var6;
			if((var6 = var2.getResponseCode()) != 200)
			{
				throw new Exception("" + var6);
			}

			ByteArrayOutputStream var7 = new ByteArrayOutputStream();
			boolean var8 = false;

			int var21;
			while((var21 = var22.read()) != -1)
			{
				var7.write(var21);
			}

			var1 = var7.toByteArray();
			var7.close();
			var22.close();
		}
		catch(Exception var18)
		{
			var18.printStackTrace();
		}
		finally
		{
			if(var2 != null)
			{
				try
				{
					var2.close();
				}
				catch(Exception var17)
				{
					var17.printStackTrace();
				}
			}

		}

		if(!var_4482)
		{
			sub_14de(var1);
		}

	}

	public final void deleteDownloadedSkirmishLevel(int var1)
	{
		int var2 = downloadedSkirmishLevelNumbers[var1];
		--downloadedSkirmishLevelCount;
		String[] var3 = new String[downloadedSkirmishLevelCount];
		int[] var4 = new int[downloadedSkirmishLevelCount];
		System.arraycopy(downloadedSkirmishLevelNames, 0, var3, 0, var1);
		System.arraycopy(downloadedSkirmishLevelNames, var1 + 1, var3, var1, downloadedSkirmishLevelCount - var1);
		System.arraycopy(downloadedSkirmishLevelNumbers, 0, var4, 0, var1);
		System.arraycopy(downloadedSkirmishLevelNumbers, var1 + 1, var4, var1, downloadedSkirmishLevelCount - var1);
		downloadedSkirmishLevelNames = var3;
		downloadedSkirmishLevelNumbers = var4;
		Renderer.deleteRMSRecord("download", var2);
		availableRMSDownloadSize = Renderer.getAvailableRMSSize("download");
		saveDownloadedSkirmishLevelSettings();

		for(int var5 = 0; var5 < 3; ++var5)
		{
			if(saveCurrentMission[var5] == var2 + skirmishLevelsNames.length)
			{
				saveCurrentFraction[var5] = -1;
				saveCurrentMission[var5] = -1;
				saveInfoStrings[var5] = "\n" + PaintableObject.getLocaleString(79) + "\n ";
				Renderer.setRMSData("save", var5, new byte[0]);
			}
		}

	}

	public final void addDownloadedSkirmishLevel(String var1, byte[] var2)
	{
		String[] var3 = new String[downloadedSkirmishLevelCount+1];
		int[] var4 = new int[downloadedSkirmishLevelCount+1];
		System.arraycopy(downloadedSkirmishLevelNames, 0, var3, 0, downloadedSkirmishLevelCount);
		System.arraycopy(downloadedSkirmishLevelNumbers, 0, var4, 0, downloadedSkirmishLevelCount);
		downloadedSkirmishLevelNames = var3;
		downloadedSkirmishLevelNumbers = var4;
		downloadedSkirmishLevelNumbers[downloadedSkirmishLevelCount] = Renderer.addRMSRecord("download", var2);
		downloadedSkirmishLevelNames[downloadedSkirmishLevelCount] = var1;
		++downloadedSkirmishLevelCount;
		availableRMSDownloadSize = Renderer.getAvailableRMSSize("download");
		saveDownloadedSkirmishLevelSettings();
	}

	public final void saveDownloadedSkirmishLevelSettings()
	{
		try
		{
			ByteArrayOutputStream var1 = new ByteArrayOutputStream();
			DataOutputStream var2;
			(var2 = new DataOutputStream(var1)).writeInt(downloadedSkirmishLevelCount);
	
			for(int var3 = 0; var3 < downloadedSkirmishLevelCount; ++var3)
			{
				var2.writeInt(downloadedSkirmishLevelNumbers[var3]);
				var2.writeUTF(downloadedSkirmishLevelNames[var3]);
			}
	
			Renderer.setRMSData("settings", 2, var1.toByteArray());
			var2.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	public void commandAction(Command c, Displayable dp)
	{
		if(dp == frmNewLevel)
		{
			if(c == cmdOK)
			{
				if(tfLevelName.size() == 0 ||
				   tfMapWidth.size() == 0 ||
				   tfMapHeight.size() == 0)
				{
					return;
				}

				int mw = Integer.parseInt(tfMapWidth.getString());
				int mh = Integer.parseInt(tfMapHeight.getString());

				if(mw <= 0 || mh <= 0)
				{
					return;
				}

				cgFillWith.getSelectedFlags(selflags);

				int selcount = 0;

				for(int i = 0; i < selflags.length; i++)
				{
					if(selflags[i])
					{
						selcount++;
					}
				}

				if(selcount == 0)
				{
					return;
				}

				/*
				byte[] fill = new byte[selcount];
				selcount = 0;

				for(byte i = 0; i < selflags.length; i++)
				{
					if(selflags[i])
					{
						fill[selcount++] = i;
					}
				}
				*/

				try
				{
					PaintableObject.currentRenderer.setCurrent(PaintableObject.currentRenderer);
					PaintableObject.currentRenderer.setCurrentDisplayable(this);

					skirmishMode = 1;
					levelEditorMode = 1;

					paintLoadingStringFlag = true;
					PaintableObject.currentRenderer.repaintAndService();
					loadMainGameResources();
					createLevel(tfLevelName.getString(), mw, mh, tileProbabilities);
					paintLoadingStringFlag = false;
					gameState = 0;

					destroyNewLevelForm();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(c == cmdProbabilities)
			{
				System.arraycopy(tileProbabilities, 0, tilePrevProbabilities, 0, tileProbabilities.length);
				itemStateChanged(gTileSelector);
				PaintableObject.currentRenderer.setCurrent(frmProbabilities);
			}
			else if(c == cmdSelectAll)
			{
				for(int i = 0; i < selflags.length; i++)
				{
					selflags[i] = true;
				}

				cgFillWith.setSelectedFlags(selflags);
				itemStateChanged(cgFillWith);
			}
			else if(c == cmdDeselectAll)
			{
				for(int i = 0; i < selflags.length; i++)
				{
					selflags[i] = false;
				}

				cgFillWith.setSelectedFlags(selflags);
				itemStateChanged(cgFillWith);
			}
			else
			{
				PaintableObject.currentRenderer.setCurrent(PaintableObject.currentRenderer);
				PaintableObject.currentRenderer.setCurrentDisplayable(this);
				destroyNewLevelForm();
			}
		}
		else if(dp == frmFillSetup)
		{
			if(c == cmdOK)
			{
				cgFillWith.getSelectedFlags(selflags);

				int selcount = 0;

				for(int i = 0; i < selflags.length; i++)
				{
					if(selflags[i])
					{
						selcount++;
					}
				}

				if(selcount == 0)
				{
					return;
				}

				/*
				byte[] fill = new byte[selcount];
				selcount = 0;

				for(byte i = 0; i < selflags.length; i++)
				{
					if(selflags[i])
					{
						fill[selcount++] = i;
					}
				}
				*/

				itemStateChanged(cgFillWith);

				PaintableObject.currentRenderer.setCurrent(PaintableObject.currentRenderer);
				PaintableObject.currentRenderer.setCurrentDisplayable(this);

				//fillLevel(tileProbabilities);
			}
			else if(c == cmdProbabilities)
			{
				System.arraycopy(tileProbabilities, 0, tilePrevProbabilities, 0, tileProbabilities.length);
				itemStateChanged(gTileSelector);
				PaintableObject.currentRenderer.setCurrent(frmProbabilities);
			}
			else if(c == cmdSelectAll)
			{
				for(int i = 0; i < selflags.length; i++)
				{
					selflags[i] = true;
				}

				cgFillWith.setSelectedFlags(selflags);
				itemStateChanged(cgFillWith);
			}
			else if(c == cmdDeselectAll)
			{
				for(int i = 0; i < selflags.length; i++)
				{
					selflags[i] = false;
				}

				cgFillWith.setSelectedFlags(selflags);
				itemStateChanged(cgFillWith);
			}
			else
			{
				PaintableObject.currentRenderer.setCurrent(PaintableObject.currentRenderer);
				PaintableObject.currentRenderer.setCurrentDisplayable(this);
			}
		}
		else if(dp == frmRename)
		{
			if(c == cmdOK)
			{
				if(tfLevelName.size() == 0)
				{
					return;
				}

				PaintableObject.currentRenderer.setCurrent(PaintableObject.currentRenderer);
				PaintableObject.currentRenderer.setCurrentDisplayable(this);

				currentMapName = tfLevelName.getString();
			}
			else if(c == cmdUniqueName)
			{
				tfLevelName.setString(Long.toString(System.currentTimeMillis(), Character.MAX_RADIX).toUpperCase());
			}
			else
			{
				PaintableObject.currentRenderer.setCurrent(PaintableObject.currentRenderer);
				PaintableObject.currentRenderer.setCurrentDisplayable(this);
			}
		}
		else if(dp == frmResize)
		{
			if(c == cmdOK)
			{
				if(tfDeltaLeft.size() == 0 ||
				   tfDeltaRight.size() == 0 ||
				   tfDeltaTop.size() == 0 ||
				   tfDeltaBottom.size() == 0)
				{
					return;
				}

				resizeLevel(Integer.parseInt(tfDeltaLeft.getString()), Integer.parseInt(tfDeltaRight.getString()), Integer.parseInt(tfDeltaTop.getString()), Integer.parseInt(tfDeltaBottom.getString()), tileProbabilities);
			}
			
			PaintableObject.currentRenderer.setCurrent(PaintableObject.currentRenderer);
			PaintableObject.currentRenderer.setCurrentDisplayable(this);
		}
		else if(dp == frmProbabilities)
		{
			if(c == cmdOK)
			{
				cgFillWith.getSelectedFlags(selflags);

				for(int i = 0; i < tileProbabilities.length; i++)
				{
					selflags[i] = tileProbabilities[i] > 0;
				}

				cgFillWith.setSelectedFlags(selflags);
				itemStateChanged(cgFillWith);
			}
			else
			{
				System.arraycopy(tilePrevProbabilities, 0, tileProbabilities, 0, tileProbabilities.length);
			}

			if(frmFillSetup != null)
			{
				PaintableObject.currentRenderer.setCurrent(frmFillSetup);
			}
			else if(frmNewLevel != null)
			{
				PaintableObject.currentRenderer.setCurrent(frmNewLevel);
			}
		}
		else if(dp == frmRandomSelect)
		{
			if(c == cmdOK)
			{
				int[] prob = new int[] { 100 - gCoverageFactor.getValue(), 100 };

				for(int x = 0; x < mapWidth; x++)
				{
					for(int y = 0; y < mapHeight; y++)
					{
						if(mapAlphaData[x][y] > 0)
						{
							mapAlphaData[x][y] = (byte)Renderer.randomFromProbabilities(prob);
						}
					}
				}
			}

			PaintableObject.currentRenderer.setCurrent(PaintableObject.currentRenderer);
			PaintableObject.currentRenderer.setCurrentDisplayable(this);
		}
		else if(dp == tbConsole)
		{
			if(c == cmdOK)
			{
				try
				{
					String[] args = Renderer.tokenizeString(tbConsole.getString().toLowerCase(), ' ');

					if(args[0].equals("win"))
					{
						if(currentMission == 7)
						{
							var_29de = true;
						}
						else
						{
							completeMission();
						}
					}
					else if(args[0].equals("fps"))
					{
						PaintableObject.currentRenderer.setFPS(Integer.parseInt(args[1]));
					}
					else if(args[0].equals("money"))
					{
						if(args[1].equals("add"))
						{
							if(args[2].equals("player"))
							{
								money[currentTurningPlayer] += Integer.parseInt(args[3]);
							}
							else if(args[2].equals("enemy"))
							{
								for(int i = 0; i < 4; i++)
								{
									if(playerModes[i] == 0)
									{
										money[i] += Integer.parseInt(args[3]);
									}
								}
							}
							else if(args[2].equals("all"))
							{
								for(int i = 0; i < 4; i++)
								{
									money[i] += Integer.parseInt(args[3]);
								}
							}
						}
						else if(args[1].equals("remove"))
						{
							if(args[2].equals("player"))
							{
								money[currentTurningPlayer] -= Integer.parseInt(args[3]);
							}
							else if(args[2].equals("enemy"))
							{
								for(int i = 0; i < 4; i++)
								{
									if(playerModes[i] == 0)
									{
										money[i] -= Integer.parseInt(args[3]);
									}
								}
							}
							else if(args[2].equals("all"))
							{
								for(int i = 0; i < 4; i++)
								{
									money[i] -= Integer.parseInt(args[3]);
								}
							}
						}
					}
					else if(args[0].equals("occupy"))
					{
						if(args[1].equals("house"))
						{
							if(args[2].equals("civilian"))
							{
								for(int x = 0; x < mapWidth; x++)
								{
									for(int y = 0; y < mapHeight; y++)
									{
										if(getTileType(x, y) == 8 && getBuildingFraction(x, y) == 0)
										{
											setBuildingFraction(x, y, fractionsTurnQueue[currentTurningPlayer]);
										}
									}
								}
							}
							else if(args[2].equals("all"))
							{
								for(int x = 0; x < mapWidth; x++)
								{
									for(int y = 0; y < mapHeight; y++)
									{
										if(getTileType(x, y) == 8)
										{
											setBuildingFraction(x, y, fractionsTurnQueue[currentTurningPlayer]);
										}
									}
								}
							}
						}
						else if(args[1].equals("castle"))
						{
							if(args[2].equals("civilian"))
							{
								for(int x = 0; x < mapWidth; x++)
								{
									for(int y = 0; y < mapHeight; y++)
									{
										if(getTileType(x, y) == 9 && getBuildingFraction(x, y) == 0)
										{
											setBuildingFraction(x, y, fractionsTurnQueue[currentTurningPlayer]);
										}
									}
								}
							}
							else if(args[2].equals("all"))
							{
								for(int x = 0; x < mapWidth; x++)
								{
									for(int y = 0; y < mapHeight; y++)
									{
										if(getTileType(x, y) == 9)
										{
											setBuildingFraction(x, y, fractionsTurnQueue[currentTurningPlayer]);
										}
									}
								}
							}
						}
					}
					else if(args[0].equals("unit"))
					{
						if(args[1].equals("reset"))
						{
							if(args[2].equals("current"))
							{
								Unit unit = getUnit(cursorMapX, cursorMapY, (byte)0);

								if(unit != null)
								{
									unit.unitState = 0;
								}
							}
							else if(args[2].equals("player"))
							{
								for(int i = 0; i < units.size(); i++)
								{
									Unit unit = (Unit)units.elementAt(i);

									if(unit.fractionPosInTurnQueue == currentTurningPlayer)
									{
										unit.unitState = 0;
									}
								}
							}
							else if(args[2].equals("enemy"))
							{
								for(int i = 0; i < units.size(); i++)
								{
									Unit unit = (Unit)units.elementAt(i);

									if(unit.fractionPosInTurnQueue != currentTurningPlayer)
									{
										unit.unitState = 0;
									}
								}
							}
							else if(args[2].equals("all"))
							{
								for(int i = 0; i < units.size(); i++)
								{
									Unit unit = (Unit)units.elementAt(i);
									unit.unitState = 0;
								}
							}
						}
						else if(args[1].equals("promote"))
						{
							if(args[2].equals("current"))
							{
								Unit unit = getUnit(cursorMapX, cursorMapY, (byte)0);

								if(unit != null)
								{
									unit.setRank((byte)Integer.parseInt(args[3]));
								}
							}
							else if(args[2].equals("player"))
							{
								byte rank = (byte)Integer.parseInt(args[3]);

								for(int i = 0; i < units.size(); i++)
								{
									Unit unit = (Unit)units.elementAt(i);

									if(unit.fractionPosInTurnQueue == currentTurningPlayer)
									{
										unit.setRank(rank);
									}
								}
							}
							else if(args[2].equals("enemy"))
							{
								byte rank = (byte)Integer.parseInt(args[3]);

								for(int i = 0; i < units.size(); i++)
								{
									Unit unit = (Unit)units.elementAt(i);

									if(unit.fractionPosInTurnQueue != currentTurningPlayer)
									{
										unit.setRank(rank);
									}
								}
							}
							else if(args[2].equals("all"))
							{
								byte rank = (byte)Integer.parseInt(args[3]);

								for(int i = 0; i < units.size(); i++)
								{
									Unit unit = (Unit)units.elementAt(i);
									unit.setRank(rank);
								}
							}
						}
					}
				}
				catch(Exception e)
				{
					return;
				}
			}

			PaintableObject.currentRenderer.setCurrent(PaintableObject.currentRenderer);
			PaintableObject.currentRenderer.setCurrentDisplayable(this);
		}
	}

	public void itemStateChanged(Item item)
	{
		if(item == cgFillWith)
		{
			cgFillWith.getSelectedFlags(selflags);

			int maxprob = 0;

			for(int i = 0; i < tileProbabilities.length; i++)
			{
				if(tileProbabilities[i] > maxprob)
				{
					maxprob = tileProbabilities[i];
				}
			}

			if(maxprob == 0)
			{
				maxprob = 100;
			}

			//int probsum = 0;

			for(int i = 0; i < selflags.length; i++)
			{
				if(selflags[i])
				{
					if(tileProbabilities[i] <= 0)
					{
						tileProbabilities[i] = maxprob;
					}

					//probsum += tileProbabilities[i];
				}
				else
				{
					tileProbabilities[i] = 0;
				}
			}

			/*
			if(probsum > 0)
			{
				int percent;

				for(int i = 0; i < tileProbabilities.length; i++)
				{
					if(tileProbabilities[i] > 0)
					{

						percent = tileProbabilities[i] * 100 / probsum;
					}
					else
					{
						percent = 0;
					}

					cgFillWith.set(i, " " + Integer.toString(i + 1) + ", " + percent + "%", cgFillWith.getImage(i));
				}
			}
			else
			{
				for(int i = 0; i < tileProbabilities.length; i++)
				{
					cgFillWith.set(i, " " + Integer.toString(i + 1) + ", 0%", cgFillWith.getImage(i));
				}
			}
			*/
		}
		else if(item == gTileSelector)
		{
			gTileSelector.setLabel(PaintableObject.getReplacedLocaleString(308, Integer.toString(gTileSelector.getValue() + 1)));
			imgTile.setImage(cgFillWith.getImage(gTileSelector.getValue()));
			tfProbability.setString(Integer.toString(tileProbabilities[gTileSelector.getValue()]));
		}
		else if(item == tfProbability)
		{
			if(tfProbability.size() > 0)
			{
				tileProbabilities[gTileSelector.getValue()] = Integer.parseInt(tfProbability.getString());
			}
		}
		else if(item == gCoverageFactor)
		{
			gCoverageFactor.setLabel(PaintableObject.getReplacedLocaleString(315, Integer.toString(gCoverageFactor.getValue()) + "%"));
		}
	}

	public void createRenameForm()
	{
		if(frmRename != null)
		{
			return;
		}

		frmRename = new Form(PaintableObject.getLocaleString(304));

		tfLevelName = new TextField(PaintableObject.getLocaleString(294), currentMapName, 100, TextField.ANY);

		frmRename.append(tfLevelName);

		frmRename.addCommand(cmdOK);
		frmRename.addCommand(cmdUniqueName);
		frmRename.addCommand(cmdCancel);
		frmRename.setCommandListener(this);
	}

	public void destroyRenameForm()
	{
		frmRename = null;
		tfLevelName = null;
	}

	public void createResizeForm()
	{
		if(frmResize != null)
		{
			return;
		}

		frmResize = new Form(PaintableObject.getLocaleString(306));

		tfDeltaLeft = new TextField(PaintableObject.getLocaleString(310), "0", 20, TextField.NUMERIC);
		tfDeltaRight = new TextField(PaintableObject.getLocaleString(311), "0", 20, TextField.NUMERIC);
		tfDeltaTop = new TextField(PaintableObject.getLocaleString(312), "0", 20, TextField.NUMERIC);
		tfDeltaBottom = new TextField(PaintableObject.getLocaleString(313), "0", 20, TextField.NUMERIC);

		frmResize.append(tfDeltaLeft);
		frmResize.append(tfDeltaRight);
		frmResize.append(tfDeltaTop);
		frmResize.append(tfDeltaBottom);

		frmResize.addCommand(cmdOK);
		frmResize.addCommand(cmdCancel);
		frmResize.setCommandListener(this);
	}

	public void destroyResizeForm()
	{
		frmResize = null;
		tfDeltaLeft = null;
		tfDeltaRight = null;
		tfDeltaTop = null;
		tfDeltaBottom = null;
	}

	public void createRandomSelectForm()
	{
		if(frmRandomSelect != null)
		{
			return;
		}

		frmRandomSelect = new Form(PaintableObject.getLocaleString(307));

		gCoverageFactor = new Gauge("", true, 100, 100);
		gCoverageFactor.setLayout(Item.LAYOUT_LEFT | Item.LAYOUT_EXPAND);

		itemStateChanged(gCoverageFactor);

		frmRandomSelect.append(gCoverageFactor);

		frmRandomSelect.addCommand(cmdOK);
		frmRandomSelect.addCommand(cmdCancel);
		frmRandomSelect.setCommandListener(this);
		frmRandomSelect.setItemStateListener(this);
	}

	public void destroyRandomSelectForm()
	{
		frmRandomSelect = null;
		gCoverageFactor = null;
	}

	public void createFillSetupForm()
	{
		if(frmFillSetup != null)
		{
			return;
		}

		frmFillSetup = new Form(PaintableObject.getLocaleString(303));

		cgFillWith = new ChoiceGroup(PaintableObject.getLocaleString(297), Choice.MULTIPLE);

		if(tileProbabilities == null)
		{
			tileProbabilities = new int[sfmTiles.length];

			for(int i = 0; i < tileProbabilities.length; i++)
			{
				tileProbabilities[i] = 100;
			}
		}

		tilePrevProbabilities = new int[sfmTiles.length];
		selflags = new boolean[sfmTiles.length];

		for(int i = 0; i < sfmTiles.length; i++)
		{
			cgFillWith.append(" " + PaintableObject.getReplacedLocaleString(308, Integer.toString(i + 1)) + " ", sfmTiles[i].getFrame());
			cgFillWith.setSelectedIndex(i, tileProbabilities[i] > 0);
		}

		itemStateChanged(cgFillWith);

		frmFillSetup.append(cgFillWith);

		frmFillSetup.addCommand(cmdOK);
		frmFillSetup.addCommand(cmdProbabilities);
		frmFillSetup.addCommand(cmdSelectAll);
		frmFillSetup.addCommand(cmdDeselectAll);
		frmFillSetup.addCommand(cmdCancel);
		frmFillSetup.setCommandListener(this);
		frmFillSetup.setItemStateListener(this);

		createProbabilityForm();
	}

	public void destroyFillSetupForm()
	{
		frmFillSetup = null;
		cgFillWith = null;

		destroyProbabilityForm();
	}

	public void createNewLevelForm()
	{
		frmNewLevel = new Form(PaintableObject.getLocaleString(292));

		tfLevelName = new TextField(PaintableObject.getLocaleString(294), Long.toString(System.currentTimeMillis(), Character.MAX_RADIX).toUpperCase(), 100, TextField.ANY);

		int mw = (super.width + 23) / 24;
		int mh = (super.height - FOOTER_HEIGHT + 23) / 24;

		tfMapWidth = new TextField(PaintableObject.getLocaleString(295), Integer.toString(mw), 3, TextField.NUMERIC);
		tfMapHeight = new TextField(PaintableObject.getLocaleString(296), Integer.toString(mh), 3, TextField.NUMERIC);
		
		cgFillWith = new ChoiceGroup(PaintableObject.getLocaleString(297), Choice.MULTIPLE);

		if(tileProbabilities == null)
		{
			tileProbabilities = new int[sfmTiles.length];

			for(int i = 0; i < tileProbabilities.length; i++)
			{
				tileProbabilities[i] = 100;
			}
		}
		
		tilePrevProbabilities = new int[sfmTiles.length];
		selflags = new boolean[sfmTiles.length];

		for(int i = 0; i < sfmTiles.length; i++)
		{
			cgFillWith.append(" " + PaintableObject.getReplacedLocaleString(308, Integer.toString(i + 1)) + " ", sfmTiles[i].getFrame());
			cgFillWith.setSelectedIndex(i, tileProbabilities[i] > 0);
		}

		itemStateChanged(cgFillWith);

		frmNewLevel.append(tfLevelName);
		frmNewLevel.append(tfMapWidth);
		frmNewLevel.append(tfMapHeight);
		frmNewLevel.append(cgFillWith);

		frmNewLevel.addCommand(cmdOK);
		frmNewLevel.addCommand(cmdProbabilities);
		frmNewLevel.addCommand(cmdSelectAll);
		frmNewLevel.addCommand(cmdDeselectAll);
		frmNewLevel.addCommand(cmdCancel);
		frmNewLevel.setCommandListener(this);
		frmNewLevel.setItemStateListener(this);

		createProbabilityForm();
	}

	public void destroyNewLevelForm()
	{
		frmNewLevel = null;
		tfLevelName = null;
		tfMapWidth = null;
		tfMapHeight = null;
		cgFillWith = null;
	}

	public void createProbabilityForm()
	{
		if(frmProbabilities != null)
		{
			return;
		}
		
		frmProbabilities = new Form(PaintableObject.getLocaleString(304));

		imgTile = new ImageItem(null, null, Item.LAYOUT_LEFT, null);
		imgTile.setLayout(Item.LAYOUT_LEFT | Item.LAYOUT_EXPAND);

		gTileSelector = new Gauge("", true, sfmTiles.length - 1, 0);
		gTileSelector.setLayout(Item.LAYOUT_LEFT | Item.LAYOUT_EXPAND);

		tfProbability = new TextField(PaintableObject.getLocaleString(309), "", 20, TextField.NUMERIC);
		tfProbability.setLayout(Item.LAYOUT_LEFT | Item.LAYOUT_EXPAND);

		frmProbabilities.append(imgTile);
		frmProbabilities.append(gTileSelector);
		frmProbabilities.append(tfProbability);

		frmProbabilities.addCommand(cmdOK);
		frmProbabilities.addCommand(cmdCancel);
		frmProbabilities.setCommandListener(this);
		frmProbabilities.setItemStateListener(this);
	}

	public void destroyProbabilityForm()
	{
		tileProbabilities = null;
		tilePrevProbabilities = null;
		frmProbabilities = null;
		tfProbability = null;
		gTileSelector = null;
		imgTile = null;
	}

	public void disableLevelEditor()
	{
		destroyFillSetupForm();
		destroyRenameForm();
		destroyResizeForm();
		destroyRandomSelectForm();
		levelEditorMode = 0;
	}

	public void createConsoleForm()
	{
		if(tbConsole != null)
		{
			return;
		}

		tbConsole = new TextBox(PaintableObject.getLocaleString(314), "", 100, TextField.ANY);

		tbConsole.addCommand(cmdOK);
		tbConsole.addCommand(cmdCancel);
		tbConsole.setCommandListener(this);
	}
}
