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
	public static final int MAINMENUSTATE = 1;
	public static final int GAMEPLAYSTATE = 2;
	
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
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE));
	}
}
