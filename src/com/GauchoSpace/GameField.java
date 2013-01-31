package com.GauchoSpace;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.GauchoSpace.Players.*;

public class GameField {
	private LevelManager levelManager;
	private ICharacter character;
	private ICharacter boss;
	private Collection<ICharacter> enemies;
	private Collection<IBullet> playerBullets;
	private Collection<IBullet> enemyBullets;
	private Image uiBackground;
	private Image fieldBackground;
	private boolean paused;
	private int lives;
	private int score;
	private int width;
	private int height;
	private float fps;
	
	public GameField() throws SlickException {
		levelManager = new LevelManager(this);
		character = new TestPlayer(this);
		boss = null;
		enemies = new Vector<ICharacter>();
		playerBullets = new Vector<IBullet>();
		enemyBullets = new Vector<IBullet>();
		uiBackground = new Image("res/background_ui.png");
		fieldBackground = new Image("res/background_field.jpg");
		paused = false;
		lives = 3;
		score = 0;
		width = 858;
		height = 1000;
		fps = 0.0f;
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
		
		// render order: enemies, boss, player, player bullets, enemy bullets
		for (ICharacter enemy : enemies) enemy.render(gc, game, graphics);
		if (boss != null) boss.render(gc, game, graphics);
		character.render(gc, game, graphics);
		for (IBullet bullet : playerBullets) bullet.render(gc, game, graphics);
		for (IBullet bullet : enemyBullets) bullet.render(gc, game, graphics);
		
		// pause screen
		if (paused) {
			graphics.setColor(new Color(0, 0, 0, 192));
			graphics.fillRect(0, 0, width, height);
		}
		
		// reset transformations and clips
		graphics.resetTransform();
		graphics.clearWorldClip();
		
		// draw UI
		
		// ghetto debug/FPS counters
		graphics.setColor(Color.white);
		graphics.drawString("blt: " + enemyBullets.size(), 1080, 976);
		graphics.drawString(String.format("gfx: %d\nfps: %.2f", gc.getFPS(), fps), 1180, 976);
	}
	
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// moving avg for FPS
		fps = (1000f / delta) * 0.2f + fps * 0.8f;
		
		// pause check
		boolean pauseToggle = gc.getInput().isKeyPressed(Input.KEY_ESCAPE);
		if (pauseToggle) paused = !paused;
		if (paused) return;
		
		// update level
		levelManager.update(gc, game, delta);
		
		// move character
		character.update(gc, game, delta);
		boolean checkCollisions = !character.getInvincibility();
		
		// move enemies
		if (boss != null) boss.update(gc, game, delta);
		for (ICharacter enemy : enemies) enemy.update(gc, game, delta);
		
		// move bullets
		Iterator<IBullet> i = enemyBullets.iterator();
		while (i.hasNext()) {
			IBullet bullet = i.next();
			bullet.update(gc, game, delta);
			
			if (bullet.isDeletable()) {
				i.remove();
			} else if (checkCollisions && bullet.isColliding(character)) {
				// handle player collision
				System.out.println("got hit!");
				i.remove();
			}
		}
	}
	
	public void addPlayerBullet(IBullet bullet) {
		playerBullets.add(bullet);
	}
	
	public Collection<IBullet> getPlayerBullets() {
		return playerBullets;
	}
	
	public void addEnemyBullet(IBullet bullet) {
		enemyBullets.add(bullet);
	}
	
	public Collection<IBullet> getEnemyBullets() {
		return enemyBullets;
	}
}
