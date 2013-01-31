package com.GauchoSpace;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameField {
	private ICharacter character;
	private Collection<IBullet> playerBullets;
	private Collection<IBullet> enemyBullets;
	private Image uiBackground;
	private Image fieldBackground;
	private int lives;
	private int score;
	private int width;
	private int height;
	private float fps;
	
	public GameField() throws SlickException {
		character = new CharacterTest(this);
		playerBullets = new Vector<IBullet>();
		enemyBullets = new Vector<IBullet>();
		uiBackground = new Image("res/background_ui.png");
		fieldBackground = new Image("res/background_field.jpg");
		width = 858;
		height = 1000;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) {
		// scale resolution
		//graphics.scale(0.5f, 0.5f);
		
		// draw UI background
		uiBackground.draw();
		
		// prep playing field
		graphics.translate(12, 12);
		graphics.setWorldClip(0, 0, width, height);
		
		// draw field background
		fieldBackground.draw();
		
		// render characters and bullets
		character.render(gc, game, graphics);
		for (IBullet bullet : playerBullets) bullet.render(gc, game, graphics);
		for (IBullet bullet : enemyBullets) bullet.render(gc, game, graphics);
		
		// reset transformations and clips
		graphics.resetTransform();
		graphics.clearWorldClip();
		
		// draw UI
		
		// ghetto FPS counters
		graphics.drawString(String.format("gfx: %d\nfps: %.2f", gc.getFPS(), fps), 1180, 976);
	}
	
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// moving avg for FPS
		fps = (1000f / delta) * 0.2f + fps * 0.8f;
		
		// move character
		character.update(gc, game, delta);
		boolean checkCollisions = !character.getInvincibility();
		
		// move enemies
		
		// move bullets
		Iterator<IBullet> i = playerBullets.iterator();
		while (i.hasNext()) {
			IBullet bullet = i.next();
			bullet.update(gc, game, delta);
			
			if (bullet.isDeletable()) {
				i.remove();
			} else if (checkCollisions && bullet.isColliding(character)) {
				// handle player collision
			}
		}
	}
}
