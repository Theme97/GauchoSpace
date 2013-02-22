package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Enemy implements ICharacter {
	private GameField field;
	private Image sprite;
	private Vector2f pos;
	private IMotion motion;
	private int radius;
	private int health;
	private boolean invincible;
	private boolean deletable;
	
	private UpdateHandler updateHandler;
	private RenderHandler renderHandler;
	
	public Enemy(GameField field, Image sprite, int radius, int health) {
		this.field = field;
		this.sprite = sprite;
		this.radius = radius;
		this.health = health;
	}
	
	public IMotion getMotion() {
		return motion;
	}
	
	public void setMotion(IMotion motion) {
		this.motion = motion;
	}
	
	public void setInvincibility(boolean invincible) {
		this.invincible = invincible;
	}
	
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
	
	public void setUpdateHandler(UpdateHandler handler) {
		this.updateHandler = handler;
	}
	
	public void setRenderHandler(RenderHandler handler) {
		this.renderHandler = handler;
	}
	
	@Override
	public Vector2f getPos() {
		return pos.copy();
	}
	
	@Override
	public void setPos(Vector2f pos) {
		this.pos = pos;
	}
	
	@Override
	public int getRadius() {
		return radius;
	}
	
	@Override
	public int getHealth() {
		return health;
	}
	
	@Override
	public boolean getInvincibility() {
		return invincible || (health <= 0);
	}
	
	@Override
	public void tookDamage(int damage) {
		health -= damage;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) {
		if (sprite != null && pos != null) {
			// pre-render
			if (renderHandler != null) renderHandler.preRender(field, graphics);
			
			// draw sprite
			sprite.drawCentered(pos.x, pos.y);
			
			// post-render
			if (renderHandler != null) renderHandler.postRender(field, graphics);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// update position according to motion
		if (motion != null) {
			Vector2f newpos = motion.update(field, field.getTicks());
			if (newpos != null) {
				pos = newpos;
			} else {
				motion = null;
			}
		}
		
		// call update handler
		if (updateHandler != null) updateHandler.onUpdate(field, field.getTicks());
	}

	@Override
	public boolean isDeletable() {
		return health <= 0;
	}
}
