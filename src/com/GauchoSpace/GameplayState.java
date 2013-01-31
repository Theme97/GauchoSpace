package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState {
	private int stateID;
	private GameField field;
	
	public GameplayState(GauchoSpace.STATE state) throws SlickException {
		super();
		this.stateID = state.ordinal();
		
		field = new GameField();
	}
	
	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		gc.setMinimumLogicUpdateInterval(16);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) throws SlickException {
		field.render(gc, game, graphics);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		field.update(gc, game, delta);
	}
}
