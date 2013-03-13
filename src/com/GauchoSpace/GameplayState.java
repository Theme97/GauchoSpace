package com.GauchoSpace;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.Image;

public class GameplayState extends BasicGameState {
	private int stateID;
	private GameField field;
	private int selection;
	private int optionSelected;
	private int soundTracker;
	private Image selector;
	private Sound selectFx;
	private Sound menuEnterFx;

	public final static int CONTINUED = 1;
	public final static int QUIT = 2;
	public final static int RESUME = 3;
	public final static int FORCEDQUIT = 4;

	public GameplayState(int state) throws SlickException {
		super();
		this.stateID = state;

		field = GameField.getInstance();
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		gc.setMinimumLogicUpdateInterval(16);
		selectFx = new Sound("res/menuselect.wav");
		menuEnterFx = new Sound("res/menuEnter.wav");
		selector = new Image("res/miniselector.png");
		selector.setAlpha(.5f);
		soundTracker = -1;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) throws SlickException {
		field.render(gc, game, graphics);
		switch (selection) {
			case CONTINUED:
				selector.draw(220, 490);
				break;
			case QUIT:
				selector.draw(455, 490);
				break;
			case RESUME:
				selector.draw(225, 520, 225, 75);
				break;
			case FORCEDQUIT:
				selector.draw(565, 520);
				break;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		field.update(gc, game, delta);
		// used for continue screen buttons
		switch (optionSelected) {
			case CONTINUED:
				selection = -1;
				field.changeContinued(CONTINUED);
				optionSelected = -1;
				break;
			case QUIT:
				selection = -1;
				field.changeContinued(QUIT);
				optionSelected = -1;
				break;
			case RESUME:
				selection = -1;
				field.setPaused(false);
				optionSelected = -1;
				break;
			case FORCEDQUIT: 
				selection = -1;
				game.enterState(GauchoSpace.MAIN_MENU,
						new FadeOutTransition(Color.black),
						new FadeInTransition(Color.black));
				optionSelected = -1;
				break;
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		if (field.getPaused()) {
			if (newy > 520 && newy < 595) {
				if (newx > 220 && newx < 445) {
					selection = RESUME;
				} else if (newx > 565 && newx < 715) {
					selection = FORCEDQUIT;
				} else selection = -1;
			}
			else selection = -1;
		}
		// boundary box for continue screen buttons
		if (field.returnLives() <= 0){
			if (newy > 490 && newy < 560) {
				if (newx > 220 && newx < 370) {
					selection = CONTINUED;
				} else if (newx > 455 && newx < 605){
					selection = QUIT;
				} else selection = -1;
			}
			else selection = -1;
		}
		selectSoundTracker(selection);
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		if (field.returnLives() <= 0 || field.getPaused()){
			if (optionSelected != selection) menuEnterFx.play(1, .4f);
			optionSelected = selection;
		}
	}	

	// plays sound effect when mouse hovers over a button only once
	public void selectSoundTracker(int arg0) {
		if(arg0 != soundTracker) {
			if (arg0 != -1) {
				selectFx.play(1, .2f);
			}
			soundTracker = arg0;
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		if (field.returnContinued() == QUIT) {
			switch (key) {
				case Input.KEY_BACK:
				case Input.KEY_DELETE:
					String name = field.getName();
					if (name.length() > 0) {
						field.setName(name.substring(0, name.length() - 1));
					}
					break;

				case Input.KEY_ENTER:
					field.setGameOver();
					break;

				default:
					if (!Character.isLetterOrDigit(c) && key != Input.KEY_UNDERLINE && key != Input.KEY_MINUS) return;

					String name2 = field.getName();
					if (name2.length() > 16) return;
					field.setName(field.getName() + c);
			}
		} else if (field.returnLives() <= 0) {
			switch (key) {
				case Input.KEY_LEFT:
				case Input.KEY_RIGHT:
					selection = (selection == CONTINUED) ? QUIT : CONTINUED;
					selectSoundTracker(selection);
					break;

				case Input.KEY_Z:
					if (optionSelected != selection) menuEnterFx.play(1, .4f);
					optionSelected = selection;
					break;
			}
		} else if (field.getPaused()) {
			switch (key) {
				case Input.KEY_LEFT:
				case Input.KEY_RIGHT:
					selection = (selection == RESUME) ? FORCEDQUIT : RESUME;
					selectSoundTracker(selection);
					break;
					
				case Input.KEY_Z:
					if (optionSelected != selection) menuEnterFx.play(1, .4f);
					optionSelected = selection;
					break;
				case Input.KEY_ESCAPE:
					selection = -1;
					break;
			}
		}
	}
}
