package com.GauchoSpace;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.Sound;
import org.newdawn.slick.Music;
import org.newdawn.slick.Input;

import com.GauchoSpace.Levels.Level1;
import com.GauchoSpace.Players.TestPlayer;


public class MainMenuState extends BasicGameState {
	int stateID = 0;
	int gameModeSelection = 0;
	private Image background;
	private Image selector;
	private Image playText;
	private Image statsText;
	private Image quitText;
	private Image normalModeText;
	private Image survivalModeText;
	private Image backText;
	private Sound selectFx;
	private Sound menuEnterFx;
	private Music backgroundMusic;
	private int selection;
	private int optionSelected;
	private int soundTracker;
	
	public MainMenuState(int state) {
		stateID = state;
	}
	
	@Override
	public int getID() {
		return stateID;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		background = new Image("res/mainmenu.png");
		playText = new Image("res/play.png");
		statsText = new Image("res/scoreboardtext.png");
		quitText = new Image("res/quit.png");
		normalModeText = new Image("res/normalmode.png");
		survivalModeText = new Image("res/survivalmode.png");
		backText = new Image("res/back.png");
		selector = new Image("res/selector.png");
		selectFx = new Sound("res/menuselect.wav");
		menuEnterFx = new Sound("res/menuEnter.wav");
		backgroundMusic = new Music("res/Undercover.ogg");
		backgroundMusic.loop();
		soundTracker = -1;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics) throws SlickException {
		background.draw();

		// changes alpha value of selector
		selector.setAlpha(.5f);
		// draws buttons
		if(gameModeSelection == 0){
			playText.draw(50, 495);
			statsText.draw(50, 585);
			quitText.draw(50, 675);
		}
		else {
			normalModeText.draw(50, 495);
			survivalModeText.draw(50, 585);
			backText.draw(50,675);
		}
		
		// draws selector
		if (selection == GauchoSpace.GAMEPLAY) {
			selector.draw(50, 495);
		} else if (selection == GauchoSpace.SCORE_STATE) {
			selector.draw(50,585);
		} else if (selection == GauchoSpace.EXIT_STATE) {
			selector.draw(50, 675);
		}
	}	
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		if (optionSelected != selection) menuEnterFx.play(1, .4f);
		optionSelected = selection;
	}	
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		// boundary box for buttons
		if (newx > 50 && newx < 550) {
			if (newy > 495 && newy < 580) {
				selection = GauchoSpace.GAMEPLAY;
			} else if (newy > 580 && newy < 670){
				selection = GauchoSpace.SCORE_STATE;
			} else if (newy > 675 && newy < 755) {
				// exits game
				selection = GauchoSpace.EXIT_STATE;
			} else {
				selection = -1;
			}
		}
		else {
			selection = -1;
		}
		selectSoundTracker(selection);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		if (optionSelected == GauchoSpace.GAMEPLAY) {
			// enters mode selection if not yet there
			// otherwise enters gameplay mode
			if (gameModeSelection == 1) {
				GameField field = GameField.getInstance();
				field.init(new TestPlayer(field), new Level1(), 0);
				
				game.enterState(GauchoSpace.GAMEPLAY);
				backgroundMusic.stop();
			}
			gameModeSelection = 1;
			optionSelected = -1;
		} else if (optionSelected == GauchoSpace.SCORE_STATE) {
			// enters high score state
				if(gameModeSelection == 0) {
					game.enterState(GauchoSpace.SCORE_STATE, new FadeOutTransition(Color.black),
		                    new FadeInTransition(Color.black));
			}
		} else if (optionSelected == GauchoSpace.EXIT_STATE) {
			// goes back if in mode selection menu
			// otherwise exits
			if(gameModeSelection == 1) {
				gameModeSelection = 0;
				optionSelected = -1;
			}
			else{
				System.exit(0);
			}
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		if (!backgroundMusic.playing()) backgroundMusic.loop();
		selection = -1;
		optionSelected = selection;
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
		if (key == Input.KEY_UP) {
			if (selection == GauchoSpace.GAMEPLAY) selection = GauchoSpace.EXIT_STATE;
			else if (selection == GauchoSpace.SCORE_STATE) selection = GauchoSpace.GAMEPLAY;
			else if (selection == GauchoSpace.EXIT_STATE) selection = GauchoSpace.SCORE_STATE;
			else selection = GauchoSpace.EXIT_STATE;
			selectSoundTracker(selection);
		}
		else if (key == Input.KEY_DOWN) {
			if (selection == GauchoSpace.GAMEPLAY) selection = GauchoSpace.SCORE_STATE;
			else if (selection == GauchoSpace.SCORE_STATE) selection = GauchoSpace.EXIT_STATE;
			else if (selection == GauchoSpace.EXIT_STATE) selection = GauchoSpace.GAMEPLAY;
			else selection = GauchoSpace.EXIT_STATE;
			selectSoundTracker(selection);
		}
		else if (key == Input.KEY_Z){
			if (optionSelected != selection) menuEnterFx.play(1, .4f);
			optionSelected = selection;
		}
	}
}

