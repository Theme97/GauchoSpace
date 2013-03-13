package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public interface IBullet {
	public int getDamage();
	public Vector2f getPos();
	public boolean isDeletable();
	public boolean isColliding(ICharacter character);
	
	public void onCollision(ICharacter target);
	public void destroy();
	
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics);
	public void update(GameContainer gc, StateBasedGame game, int delta);
}
