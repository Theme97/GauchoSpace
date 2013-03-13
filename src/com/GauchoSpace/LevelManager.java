package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class LevelManager {
	private GameField field;
	private ILevel level;
	private int ticks;
	
	public LevelManager(GameField field) {
		this.field = field;
	}
	
	public void load(ILevel level) {
		if (level != null) this.level = level;
		ticks = 0;
	}
	
	public void reset() {
		ticks = 0;
	}
	
	public void renderBackground(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		level.renderBackground(field, this, graphics);
	}
	
	public void renderForeground(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		level.renderForeground(field, this, graphics);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if (level != null) {
			level.update(field, this, ticks);
			ticks++;
		}
	}
	
	public int getTicks() {
		return ticks;
	}
}
