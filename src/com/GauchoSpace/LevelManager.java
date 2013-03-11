package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import com.GauchoSpace.Levels.*;

public class LevelManager {
	private GameField field;
	private ILevel level;
	private int ticks;
	
	public LevelManager(GameField field) {
		this.field = field;
		level = new monochrome();
		ticks = 0;
	}
	
	public void renderBackground(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		level.renderBackground(field, this, graphics);
	}
	
	public void renderForeground(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		level.renderForeground(field, this, graphics);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		level.update(field, this, ticks);
		ticks++;
	}
	
	public int getTicks() {
		return ticks;
	}
}
