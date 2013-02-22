package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Sound;


public class GameOverState extends BasicGameState {
	int stateID = 0;
	
	public GameOverState(int state) {
		stateID = state;
	}
	
	@Override
	public int getID() {
		return stateID;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) throws SlickException {
		//graphics.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		graphics.drawString("GAME OVER", gc.getWidth() / 2 - 100, gc.getHeight() / 2);
	}	
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			game.enterState(GauchoSpace.MAIN_MENU);
		}
	}
}

