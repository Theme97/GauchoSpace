package com.GauchoSpace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
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
		if (selection == CONTINUED)
			selector.draw(220, 490);
		else if (selection == QUIT) 
			selector.draw(455, 490);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		field.update(gc, game, delta);
		// used for continue screen buttons
		if (optionSelected == CONTINUED){
			selection = -1;
			field.changeContinued(CONTINUED);
			optionSelected = -1;
		}
		else if (optionSelected == QUIT){
			selection = -1;
			field.changeContinued(QUIT);
			optionSelected = -1;
		}
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		// boundary box for continue screen buttons
		if (field.returnLives() <= 0){
			if (newy > 490 && newy < 560) {
				if (newx > 220 && newx < 370) {
					selection = CONTINUED;
				} else if (newx > 455 && newx < 605){
					selection = QUIT;
				} else {
					selection = -1;
				}
			}
			else {
				selection = -1;
			}		
		}
		selectSoundTracker(selection);
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		if (field.returnLives() <= 0){
			if (optionSelected != selection) menuEnterFx.play(1, .4f);
			optionSelected = selection;
		}
	}	
	
	// plays sound effect when mouse hovers over a button only once
	public void selectSoundTracker(int arg0){
		if(arg0 != soundTracker){
			if (arg0 != -1) {
				selectFx.play(1, .2f);
			}
			soundTracker = arg0;
		}
	}
	
	@Override
	public void keyPressed(int key, char c){
		if (field.returnContinued() == QUIT){
			if ((key == Input.KEY_BACK || key == Input.KEY_DELETE) && field.getNameLength() > 0) {
				field.setName(field.getName().substring(0,field.getNameLength()-1));
			}
			else if (key == Input.KEY_ENTER) field.setGameOver();
			else if ((key == Input.KEY_UNDERLINE || key == Input.KEY_MINUS || Character.isLetterOrDigit(c)) &&
					field.getNameLength() <= 16) 
				field.setName(field.getName() + c);
		}
		else if (field.returnLives() <= 0){
			if (key == Input.KEY_LEFT || key == Input.KEY_RIGHT) {
				if (selection == CONTINUED)	selection = QUIT;
				else if (selection == QUIT) selection = CONTINUED;
				else selection = CONTINUED;
				selectSoundTracker(selection);
			}
			else if (key == Input.KEY_Z) {
				if (optionSelected != selection) menuEnterFx.play(1, .4f);
				optionSelected = selection;
			}
		}
	}
}
