package com.GauchoSpace;

import com.GauchoSpace.ScoreRecord;
import com.GauchoSpace.ScoreTableLoader;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
import org.newdawn.slick.TrueTypeFont;
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
	private Image enterName;
	private boolean paused;
	private boolean gameover;
	private int continued;
	private int lives;
	private int score;
	private int hiScore;
	private int width;
	private int height;
	private float fps;
	private float yDisplacement;
	private float timer;
	private TrueTypeFont ttf;
	private boolean cheatInvincibility;
	private String name;

	/* ------------ *
	 * Constructors *
	 * ------------ */
	protected GameField() throws SlickException {
		levelManager = new LevelManager(this);
		character = new TestPlayer(this);
		uiBackground = new Image("res/background_ui.png");
		fieldBackground = new Image("res/background_field.jpg");
		continueButton = new Image("res/continue.png");
		enterName = new Image("res/nametext.png");
		ttf = new TrueTypeFont(new Font("Verdana", Font.BOLD, 30), true);
		width = 858;
		height = 1000;
	}

	public void init(ICharacter character, ILevel level, int score) {
		this.character = character;
		this.score = score;

		levelManager.load(level);

		boss = null;
		enemies = new Vector<ICharacter>();
		playerBullets = new Vector<IBullet>();
		enemyBullets = new Vector<IBullet>();
		paused = false;
		gameover = false;
		continued = -1;
		lives = 3;
		score = 0;
		hiScore = getHiScore();
		yDisplacement = 0;
		timer = 0.0f;
		name = "";
	}

	/* --------- *
	 * Singleton *
	 * --------- */
	private static GameField instance = null;

	public static GameField getInstance() {
		if (instance == null) {
			try {
				instance = new GameField();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/* ----------------- *
	 * Getters & Setters *
	 * ----------------- */
	// getters
	public ICharacter getPlayer() { return character; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getTicks() { return levelManager.getTicks(); }
	public Collection<IBullet> getPlayerBullets() { return playerBullets; }
	public Collection<IBullet> getEnemyBullets() { return enemyBullets; }
	public int returnContinued() { return continued; }
	public int returnLives() { return lives; }
	public int returnScore() { return score; }

	private int getHiScore() {
		int hiScore = 0;
		try {
			ScoreTableLoader stl = new ScoreTableLoader("scores.txt");
			ArrayList<ScoreRecord> scores = stl.loadScoreTable();
			if (!scores.isEmpty()) {
				ScoreRecord score = scores.get(0);
				hiScore = score.getPoints();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hiScore;
	}

	public String getName() { return name; }
	public int getNameLength() { return name.length(); }

	// setters
	public void addEnemy(ICharacter enemy) {
		enemies.add(enemy);
	}

	public void setBoss(ICharacter boss) {
		this.boss = boss;
	}

	public void addPlayerBullet(IBullet bullet) {
		playerBullets.add(bullet);
	}

	public void addEnemyBullet(IBullet bullet) {
		enemyBullets.add(bullet);
	}

	public void changeContinued(int value) {
		continued = value;
	}

	// Saves score to the save file
	private void writeScore() {
		try {
			ScoreTableLoader stl = new ScoreTableLoader("scores.txt");
			ArrayList<ScoreRecord> scores = stl.loadScoreTable();
			ScoreRecord sr = new ScoreRecord(name, score);
			scores.add(sr);
			stl.saveScoreTable(scores);
			stl = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setGameOver() {
		gameover = true;
	}

	/* -------- *
	 * render() *
	 * -------- */
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
		if (lives <= 0 && !gameover && continued != GameplayState.QUIT) {
			graphics.setColor(new Color(0, 0, 0, 192));
			graphics.fillRect(0, 0, width, height);
			continueButton.draw(200, 400);
		}

		// Gameover/enter name screen
		if (continued == GameplayState.QUIT) {
			graphics.setColor(new Color(0, 0, 0, 192));
			graphics.fillRect(0, 0, width, height);
			enterName.draw(175, 400);
			ttf.drawString(175, 475, name);
		}

		// reset transformations and clips
		graphics.resetTransform();
		graphics.clearWorldClip();

		// draw UI
		graphics.setColor(Color.white);
		ttf.drawString(1125, 142, Integer.toString((score > hiScore) ? score : hiScore)); 
		ttf.drawString(1078, 192, Integer.toString(score));
		//ttf.drawString(1065, 192, "" + (int)Math.floor(timer / 1000));
		ttf.drawString(1055, 292, Integer.toString(lives));

		// cheats
		if (cheatInvincibility) graphics.drawString("Invincibility ON", 900, 500);

		// ghetto debug/FPS counters
		graphics.drawString("blt: " + enemyBullets.size() + "\n   : " + playerBullets.size(), 1080, 976);
		graphics.drawString(String.format("gfx: %d\nfps: %.2f", gc.getFPS(), fps), 1180, 976);
	}

	/* -------- *
	 * update() *
	 * -------- */
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// moving avg for FPS
		fps = (1000f / delta) * 0.2f + fps * 0.8f;

		// gameover check
		if (continued == GameplayState.QUIT) {
			if (gameover) {
				game.enterState(GauchoSpace.GAMEOVER);
				writeScore();
				continued = -1;
			}
			return;
		}

		// cheats
		if (gc.getInput().isKeyPressed(Input.KEY_I)) cheatInvincibility = !cheatInvincibility; 

		// continue screen check
		if (lives <= 0) {
			if (continued == GameplayState.CONTINUED){
				lives = 3;
				score++;
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
					score += 10;
					bullet.onCollision(enemy);
					enemy.tookDamage(bullet.getDamage());
					
					if (bullet.isDeletable()) break;
				}
			}

			// next, boss
			if (boss != null && !bullet.isDeletable()) {
				if (bullet.isColliding(boss)) {
					score += 10;
					bullet.onCollision(boss);
					boss.tookDamage(bullet.getDamage());
					if (boss.isDeletable()) {
						continued = GameplayState.QUIT;
						return;
					}
				}
			}
			
			if (bullet.isDeletable()) {
				bullet.destroy();
				i.remove();
			}
		}

		// - 2: enemy bullets
		i = enemyBullets.iterator();
		while (i.hasNext()) {
			IBullet bullet = i.next();
			bullet.update(gc, game, delta);

			if (!bullet.isDeletable()) {
				if (checkCollisions && bullet.isColliding(character)) {
					// handle player collision
					if (lives > 0) lives --;
					bullet.onCollision(character);
					character.tookDamage(bullet.getDamage());
				}
			}
			
			if (bullet.isDeletable()) {
				bullet.destroy();
				i.remove();
			}
		}

		// clean up enemies
		j = enemies.iterator();
		while (j.hasNext()) {
			ICharacter enemy = j.next();
			if (enemy.isDeletable()) {
				score += 100;
				enemy.destroy();
				j.remove();
			}
		}
	}
}
