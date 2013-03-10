package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Sound;
import org.newdawn.slick.Music;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.Color;



public class GameOverState extends BasicGameState {
	int stateID = 0;
	boolean exit = false;
	
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
		/*Input input = gc.getInput();
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			game.enterState(GauchoSpace.MAIN_MENU, new FadeOutTransition(Color.black),
                    new FadeInTransition(Color.black));
		}*/
		if (exit) game.enterState(GauchoSpace.MAIN_MENU, new FadeOutTransition(Color.black),
                new FadeInTransition(Color.black));
	}
	
	@Override
	public void keyPressed(int key, char c){
		if (key == Input.KEY_Z || key == Input.KEY_ENTER)
			exit = true;
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		exit = true;
	}	
}

