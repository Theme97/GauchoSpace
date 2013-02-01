package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Sound;


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
	private GauchoSpace.STATE selection;
	private GauchoSpace.STATE optionSelected;
	private GauchoSpace.STATE soundTracker = GauchoSpace.STATE.NONE;
	
	public MainMenuState(GauchoSpace.STATE state) {
		stateID = state.ordinal();
	}
	
	@Override
	public int getID() {
		return stateID;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("data/mainmenu.png");
		playText = new Image("data/play.png");
		statsText = new Image("data/stats.png");
		quitText = new Image("data/quit.png");
		normalModeText = new Image("data/normalmode.png");
		survivalModeText = new Image("data/survivalmode.png");
		backText = new Image("data/back.png");
		selector = new Image("data/selector.png");
		selectFx = new Sound("data/menuselect.wav");
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
		if (selection == GauchoSpace.STATE.GAMEPLAY) {
			selector.draw(50, 495);
		} else if (selection == GauchoSpace.STATE.OPTIONS_MENU) {
			selector.draw(50,585);
		} else if (selection == GauchoSpace.STATE.EXIT_STATE) {
			selector.draw(50, 675);
		}
		// TODO Auto-generated method stub
		
	}	
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		optionSelected = selection;
	}	
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		// boundary box for buttons
		if (newx > 50 && newx < 550) {
			if (newy > 495 && newy < 580) {
				selection = GauchoSpace.STATE.GAMEPLAY;
			} else if (newy > 580 && newy < 670){
				selection = GauchoSpace.STATE.OPTIONS_MENU;
			} else if (newy > 675 && newy < 755) {
				// exits game
				selection = GauchoSpace.STATE.EXIT_STATE;
			} else {
				selection = GauchoSpace.STATE.NONE;
			}
		}
		else {
			selection = GauchoSpace.STATE.NONE;
		}
		selectSoundTracker(selection);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame game, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
			if (optionSelected == GauchoSpace.STATE.GAMEPLAY) {
				// enters mode selection if not yet there
				// otherwise enters gameplay mode
				if (gameModeSelection == 1) {
					game.enterState(2);
				}
				gameModeSelection = 1;
				optionSelected = GauchoSpace.STATE.NONE;
			} else if (optionSelected == GauchoSpace.STATE.OPTIONS_MENU) {
				// enters stats/options state
				// game.enterState(3);
			} else if (optionSelected == GauchoSpace.STATE.EXIT_STATE) {
				// goes back if in mode selection menu
				// otherwise exits
				if(gameModeSelection == 1) {
					gameModeSelection = 0;
					optionSelected = GauchoSpace.STATE.NONE;
				}
				else{
					System.exit(0);
				}
			}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		selection = GauchoSpace.STATE.NONE;
		optionSelected = selection;
	}
	
	// plays sound effect when mouse hovers over a button only once
	public void selectSoundTracker(GauchoSpace.STATE arg0){
		if(arg0 != soundTracker){
			if (arg0 != GauchoSpace.STATE.NONE) {
				selectFx.play();
			}
			soundTracker = arg0;
		}
	}
		
}

