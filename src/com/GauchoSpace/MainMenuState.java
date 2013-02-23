package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Sound;
import org.newdawn.slick.Music;


public class MainMenuState extends BasicGameState {
	int stateID = 0;
	int gameModeSelection = 0;
	private Image background;
	private Image selector;
	private Image playText;
	private Image statsText;
	private Image quitText;
	private Image normalModeText;
	private Image survivalModeText;
	private Image backText;
	private Sound selectFx;
	private Sound menuEnterFx;
	private Music backgroundMusic;
	private int selection;
	private int optionSelected;
	private int soundTracker;
	
	public MainMenuState(int state) {
		stateID = state;
	}
	
	@Override
	public int getID() {
		return stateID;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("res/mainmenu.png");
		playText = new Image("res/play.png");
		statsText = new Image("res/stats.png");
		quitText = new Image("res/quit.png");
		normalModeText = new Image("res/normalmode.png");
		survivalModeText = new Image("res/survivalmode.png");
		backText = new Image("res/back.png");
		selector = new Image("res/selector.png");
		selectFx = new Sound("res/menuselect.wav");
		menuEnterFx = new Sound("res/menuEnter.wav");
		backgroundMusic = new Music("res/Undercover.ogg");
		backgroundMusic.loop();
		soundTracker = -1;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw();
		// changes alpha value of selector
		selector.setAlpha((float) .5);
		// draws buttons
		if(gameModeSelection == 0){
			playText.draw(50, 495);
			statsText.draw(50, 585);
			quitText.draw(50, 675);
		}
		else {
			normalModeText.draw(50, 495);
			survivalModeText.draw(50, 585);
			backText.draw(50,675);
		}
		// draws selector
		if (selection == GauchoSpace.GAMEPLAY) {
			selector.draw(50, 495);
		} else if (selection == GauchoSpace.OPTIONS_MENU) {
			selector.draw(50,585);
		} else if (selection == GauchoSpace.EXIT_STATE) {
			selector.draw(50, 675);
		}
		// TODO Auto-generated method stub
		
	}	
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		if (optionSelected != selection) menuEnterFx.play(1, .4f);
		optionSelected = selection;
	}	
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		// boundary box for buttons
		if (newx > 50 && newx < 550) {
			if (newy > 495 && newy < 580) {
				selection = GauchoSpace.GAMEPLAY;
			} else if (newy > 580 && newy < 670){
				selection = GauchoSpace.OPTIONS_MENU;
			} else if (newy > 675 && newy < 755) {
				// exits game
				selection = GauchoSpace.EXIT_STATE;
			} else {
				selection = -1;
			}
		}
		else {
			selection = -1;
		}
		selectSoundTracker(selection);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame game, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
			if (optionSelected == GauchoSpace.GAMEPLAY) {
				// enters mode selection if not yet there
				// otherwise enters gameplay mode
				if (gameModeSelection == 1) {
					game.enterState(GauchoSpace.GAMEPLAY);
					backgroundMusic.stop();
				}
				gameModeSelection = 1;
				optionSelected = -1;
			} else if (optionSelected == GauchoSpace.OPTIONS_MENU) {
				// enters stats/options state
				// game.enterState(3);
			} else if (optionSelected == GauchoSpace.EXIT_STATE) {
				// goes back if in mode selection menu
				// otherwise exits
				if(gameModeSelection == 1) {
					gameModeSelection = 0;
					optionSelected = -1;
				}
				else{
					System.exit(0);
				}
			}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		selection = -1;
		optionSelected = selection;
	}
	
	// plays sound effect when mouse hovers over a button only once
	public void selectSoundTracker(int arg0){
		if(arg0 != soundTracker){
			if (arg0 != -1) {
				selectFx.play(1, .2f);
			}
			soundTracker = arg0;
		}
	}
}

