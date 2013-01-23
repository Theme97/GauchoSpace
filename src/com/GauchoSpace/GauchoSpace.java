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
	public static enum STATE {
		MAIN_MENU,
		OPTIONS_MENU,
		GAMEPLAY
	}
	
	public GauchoSpace() {
		super("Gaucho Space");
	}
	
	/**
	 * @param args Unused.
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new GauchoSpace());
		app.setDisplayMode(800,  600,  false);
		app.start();
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MainMenuState(STATE.MAIN_MENU));
		this.addState(new GameplayState(STATE.GAMEPLAY));
	}
}
