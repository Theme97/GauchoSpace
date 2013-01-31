package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface IBullet {
	public boolean isDeletable();
	public boolean isColliding(ICharacter character);
	
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics);
	public void update(GameContainer gc, StateBasedGame game, int delta);
}
