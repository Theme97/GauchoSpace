package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class CharacterTest implements ICharacter {
	private GameField field;
	private boolean invincible;
	private boolean slow;
	private Image sprite;
	private int speed_fast;
	private int speed_slow;
	private Vector2f pos;
	
	public CharacterTest(GameField field) throws SlickException {
		this.field = field;
		invincible = false;
		slow = false;
		sprite = new Image("res/char_test.png");
		speed_fast = 5;
		speed_slow = 2;
		pos = new Vector2f(50.0f, 50.0f);
	}
	
	public GameField getGameField() {
		return field;
	}
	
	public void setGameField(GameField field) {
		this.field = field;
	}
	
	public Vector2f getPosition() {
		return pos;
	}
	
	public void setPosition(Vector2f position) {
		this.pos = position;
	}
	
	public boolean getInvincibility() {
		return invincible;
	}
	
	public void setInvincibility(boolean invincibility) {
		this.invincible = invincibility;
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) {
		sprite.drawCentered(pos.x, pos.y);
	}

	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// check keys
		Input input = gc.getInput();
		
		// slow toggle
		slow = input.isKeyDown(Input.KEY_LSHIFT);
		int speed = (slow ? speed_slow : speed_fast);
		
		// movement
		Vector2f vel = new Vector2f(0, 0);
		if (input.isKeyDown(Input.KEY_LEFT)) vel.x -= 1;
		if (input.isKeyDown(Input.KEY_RIGHT)) vel.x += 1;
		if (input.isKeyDown(Input.KEY_UP)) vel.y -= 1;
		if (input.isKeyDown(Input.KEY_DOWN)) vel.y += 1;
		vel.normalise().scale(speed);
		pos.add(vel);
	}
}
