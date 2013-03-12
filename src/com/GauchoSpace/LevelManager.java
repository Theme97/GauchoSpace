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
	}
	
	public void load(ILevel level) {
		if (level != null) this.level = level;
		ticks = 0;
	}
	
	public void reset() {
		ticks = 0;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		// do we need this?
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
