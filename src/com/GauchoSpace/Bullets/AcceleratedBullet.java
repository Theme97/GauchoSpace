package com.GauchoSpace.Bullets;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.GauchoSpace.GameField;
import com.GauchoSpace.IBullet;
import com.GauchoSpace.ICharacter;

public class AcceleratedBullet implements IBullet {
	private GameField field;
	private boolean deletable;
	private Vector2f pos;
	private Vector2f vel;
	private Vector2f acc;
	private float velLimit;
	private int radius;
	private Color color;
	private int clipSize;
	
	public AcceleratedBullet(GameField field, float x, float y, float vel, float acc, float angle, float velLimit, int radius, Color color) {
		this.field = field;
		this.pos = new Vector2f(x, y);
		this.vel = new Vector2f(angle).scale(vel);
		this.acc = new Vector2f(angle).scale(acc);
		this.velLimit = velLimit;
		this.radius = radius;
		this.color = color;
		deletable = false;
		clipSize = 64;
	}
	
	@Override
	public int getDamage() {
		return 1;
	}
	
	@Override
	public boolean isDeletable() {
		return deletable;
	}

	@Override
	public boolean isColliding(ICharacter character) {
		Vector2f charPos = character.getPos();
		int charRad = character.getRadius();
		
		// part 1: fast collision check with AABB
		// vertical axes first
		if (pos.y + radius < charPos.y - charRad) return false;
		if (pos.y - radius > charPos.y + charRad) return false;
		// horizontal axes last
		if (pos.x + radius < charPos.x - charRad) return false;
		if (pos.x - radius > charPos.x + charRad) return false;
		
		// part 2: precise collision check
		float dx = pos.x - charPos.x;
		float dy = pos.y - charPos.y;
		float dr = radius + charRad;
		return dx*dx + dy*dy < dr*dr;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) {
		int x = (int)pos.x;
		int y = (int)pos.y;
		graphics.setColor(color);
		graphics.drawArc(x - radius - 1, y - radius - 1, radius * 2 + 2, radius * 2 + 2, 0, 360);
		graphics.fillArc(x - radius, y - radius, radius * 2, radius * 2, 0, 360);
		graphics.setColor(Color.white);
		graphics.fillArc(x - radius + 1, y - radius + 1, radius * 2 - 2, radius * 2 - 2, 0, 360);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		if (acc != null) {
			vel.add(acc);
			if (vel.lengthSquared() > velLimit*velLimit) {
				acc = null;
				vel.normalise().scale(velLimit);
			}
		}
		
		pos.add(vel);
		
		if (pos.x < -clipSize) {
			deletable = true;
		} else if (pos.x > field.getWidth() + clipSize) {
			deletable = true;
		} else if (pos.y < -clipSize) {
			deletable = true;
		} else if (pos.y > field.getHeight() + clipSize) {
			deletable = true;
		}
	}
}
