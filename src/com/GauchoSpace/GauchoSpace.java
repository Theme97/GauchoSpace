/**
 * 
 */
package com.GauchoSpace;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Michael Fong, Bohan Lin, Daniel Ly, Spencer Pao, Daniel Reinhart
 *
 */
public class GauchoSpace extends StateBasedGame {
	public final static int MAIN_MENU = 1;
	public final static int GAMEPLAY = 2;
	public final static int GAMEOVER = 3;
	public final static int SCORE_STATE = 4;
	public final static int EXIT_STATE = 5;
	/*
	public static enum STATE {
		MAIN_MENU,
		OPTIONS_MENU,
		GAMEPLAY,
		EXIT_STATE,
		NONE // used for the main menu
	}
	*/
	public GauchoSpace() {
		super("Gaucho Space");
	}
	
	/**
	 * @param args Unused.
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new GauchoSpace());
		app.setDisplayMode(1280,  1024,  false);
		app.setShowFPS(false);
		app.start();
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MainMenuState(MAIN_MENU));
		this.addState(new GameplayState(GAMEPLAY));
		this.addState(new GameOverState(GAMEOVER));
		this.addState(new ScoreState(SCORE_STATE));
	}
}
