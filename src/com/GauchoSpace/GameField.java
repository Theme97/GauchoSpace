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
	private Image continueButton;
	private boolean paused;
	private int continued;
	private int lives;
	private int score;
	private int width;
	private int height;
	private float fps;
	private float yDisplacement;
	private float timer;
	
	private boolean cheatInvincibility;
	
	public GameField() throws SlickException {
		levelManager = new LevelManager(this);
		character = new TestPlayer(this);
		boss = null;
		enemies = new Vector<ICharacter>();
		playerBullets = new Vector<IBullet>();
		enemyBullets = new Vector<IBullet>();
		uiBackground = new Image("res/background_ui.png");
		fieldBackground = new Image("res/background_field.jpg");
		continueButton = new Image("res/continue.png");
		paused = false;
		lives = 3;
		score = 0;
		width = 858;
		height = 1000;
		fps = 0.0f;
		yDisplacement = 0;
		timer = 0.0f;
		continued = -1;
	}
	
	public ICharacter getPlayer() {
		return character;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getTicks() {
		return levelManager.getTicks();
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
		//fieldBackground.draw();
		
		// draw animated background
		graphics.drawImage(fieldBackground, -550, yDisplacement);
		graphics.drawImage(fieldBackground, -550, yDisplacement - fieldBackground.getHeight());
		if (yDisplacement >= fieldBackground.getHeight()) yDisplacement = 0;
		
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
		
		// continue? screen
		if (lives <= 0) {
			graphics.setColor(new Color(0, 0, 0, 192));
			graphics.fillRect(0, 0, width, height);
			continueButton.draw(200, 400);
		}
		
		// reset transformations and clips
		graphics.resetTransform();
		graphics.clearWorldClip();
		
		// draw UI
		graphics.setColor(Color.white);
		graphics.drawString("Score: " + score, 900, 10);
		graphics.drawString("Timer: " + (int)Math.floor(timer / 1000), 900, 30);
		graphics.drawString("Lives: " + lives, 900, 50);
		
		// cheats
		if (cheatInvincibility) graphics.drawString("Invincibility ON", 900, 500);
		
		// ghetto debug/FPS counters
		graphics.drawString("blt: " + enemyBullets.size() + "\n   : " + playerBullets.size(), 1080, 976);
		graphics.drawString(String.format("gfx: %d\nfps: %.2f", gc.getFPS(), fps), 1180, 976);
	}

	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// moving avg for FPS
		fps = (1000f / delta) * 0.2f + fps * 0.8f;
		
		// cheats
		if (gc.getInput().isKeyPressed(Input.KEY_I)) cheatInvincibility = !cheatInvincibility; 
		
		// continue screen check
		if (lives <= 0) {
			if (continued == GameplayState.CONTINUED){
				lives = 3;
				score++;
				continued = -1;
			}
			else if (continued == GameplayState.QUIT){
				game.enterState(GauchoSpace.GAMEOVER);
				reset();
				continued = -1;
			}
			return;
		}
		
		// pause check
		boolean pauseToggle = gc.getInput().isKeyPressed(Input.KEY_ESCAPE);
		if (pauseToggle) paused = !paused;
		
		if (paused) return;
		
		// updates background and timer
		yDisplacement = yDisplacement + 1f;
		timer += delta;
		
		// update level
		levelManager.update(gc, game, delta);
		
		// move character
		character.update(gc, game, delta);
		boolean checkCollisions = !cheatInvincibility && !character.getInvincibility();
		
		// move enemies
		if (boss != null) boss.update(gc, game, delta);
		for (ICharacter enemy : enemies) enemy.update(gc, game, delta);
		
		// move bullets
		Iterator<IBullet> i;
		Iterator<ICharacter> j;
		
		// - 1: player bullets
		i = playerBullets.iterator();
		while (i.hasNext()) {
			IBullet bullet = i.next();
			bullet.update(gc, game, delta);
			
			// first, general enemies
			j = enemies.iterator();
			while (j.hasNext()) {
				ICharacter enemy = j.next();
				if (bullet.isColliding(enemy)) {
					enemy.tookDamage(bullet.getDamage());
				}
			}
			
			if (bullet.isDeletable()) {
				i.remove();
				continue;
			}
			
			// next, boss
			if (boss != null) {
				if (bullet.isColliding(boss)) {
					boss.tookDamage(bullet.getDamage());
					if (boss.isDeletable()) {
						game.enterState(GauchoSpace.GAMEOVER);
						reset();
						return;
					}
				}
			}
		}
		
		// - 2: enemy bullets
		i = enemyBullets.iterator();
		while (i.hasNext()) {
			IBullet bullet = i.next();
			bullet.update(gc, game, delta);
			
			if (bullet.isDeletable()) {
				i.remove();
			} else if (checkCollisions && bullet.isColliding(character)) {
				// handle player collision
				lives--;
				/*if (lives <= 0) {
					if (continued == GameplayState.CONTINUED){
						lives = 3;
						score++;
					}
					else if (continued == GameplayState.QUIT){
						game.enterState(GauchoSpace.GAMEOVER);
						reset();
					}
					return;
				} else {*/
					character.tookDamage(bullet.getDamage());
					i.remove();
				// }
			}
		}
		
		// clean up enemies
		for (j = enemies.iterator(); j.hasNext();)
			if (j.next().isDeletable())
				j.remove();
	}
	
	public void addEnemy(ICharacter enemy) {
		enemies.add(enemy);
	}
	
	public void setBoss(ICharacter boss) {
		this.boss = boss;
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
	
	public int returnContinued(){
		return continued;
	}
	
	public int returnLives(){
		return lives;
	}
	
	public void changeContinued(int value){
		continued = value;
	}
	
	// Resets the gamefield
	public void reset(){
		boss = null;
		enemies = new Vector<ICharacter>();
		playerBullets = new Vector<IBullet>();
		enemyBullets = new Vector<IBullet>();
		lives = 3;
		paused = false;
		lives = 3;
		score = 0;
		fps = 0.0f;
		yDisplacement = 0;
		timer = 0.0f;
		continued = -1;
		levelManager.reset();
	}
}
