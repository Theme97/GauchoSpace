package com.GauchoSpace.Players;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.GauchoSpace.GameField;
import com.GauchoSpace.ICharacter;

public class TestPlayer implements ICharacter {
	private GameField field;
	private Image sprite;
	
	private boolean invincible;
	private boolean slow;
	
	private Vector2f pos;
	private int radius;
	
	private int speed_fast;
	private int speed_slow;
	
	public TestPlayer(GameField field) throws SlickException {
		this.field = field;
		invincible = false;
		slow = false;
		sprite = new Image("res/char_test.png");
		speed_fast = 5;
		speed_slow = 2;
		pos = new Vector2f();
		radius = 3;
	}
	
	public Vector2f getPos() {
		return pos;
	}
	
	public void setPos(Vector2f pos) {
		this.pos = pos;
	}
	
	public int getHealth() {
		return 1;
	}
	
	public boolean getInvincibility() {
		return invincible;
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) {
		int x = (int)pos.x;
		int y = (int)pos.y;
		
		sprite.drawCentered(x, y);
		
		if (slow) {
			// TODO let's just make this an image
			Color oldColor = graphics.getColor();
			graphics.setColor(Color.red);
			graphics.fillArc(x - radius, y - radius, radius * 2, radius * 2, 0, 360);
			graphics.setColor(Color.white);
			graphics.fillArc(x - radius + 1, y - radius + 1, radius * 2 - 2, radius * 2 - 2, 0, 360);
			graphics.setColor(oldColor);
		}
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
		pos.add(vel.normalise().scale(speed));
		
		// clamps
		pos.x = Math.min(Math.max(pos.x, 12), field.getWidth() - 12);
		pos.y = Math.min(Math.max(pos.y, 20), field.getHeight() - 20);
	}
}
