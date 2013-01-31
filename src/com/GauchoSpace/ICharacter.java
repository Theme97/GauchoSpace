package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public interface ICharacter {
	public Vector2f getPos();
	public void setPos(Vector2f pos);
	
	public int getRadius();
	
	public int getHealth();
	public boolean getInvincibility();
	
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics);
	public void update(GameContainer gc, StateBasedGame game, int delta);
}
