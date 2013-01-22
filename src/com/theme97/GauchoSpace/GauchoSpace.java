/**
 * 
 */
package com.theme97.GauchoSpace;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author Michael Fong, Bohan Lin, Daniel Ly, Spencer Pao, Daniel Reinhart
 *
 */
public class GauchoSpace extends BasicGame {
	
	public GauchoSpace() {
		super("Gaucho Space");
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
	}
	
	/**
	 * @param args Unused.
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new GauchoSpace());
		app.setDisplayMode(800,  600,  false);
		app.start();
	}

}
