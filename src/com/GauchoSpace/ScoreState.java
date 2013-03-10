package com.GauchoSpace;

import com.GauchoSpace.ScoreRecord;
import com.GauchoSpace.ScoreTableLoader;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class ScoreState extends BasicGameState{
    private int stateID;
    private ArrayList<ScoreRecord> scores;
    private Image background;
    private Image selector;
    private Font font;
    private TrueTypeFont ttf;
	private Sound selectFx;
	private Sound menuEnterFx;
    private int soundTracker;
    private int selection;
    private int optionSelected;

    public final static int BACK = 1;
    
    public ScoreState(int state) throws SlickException {
		super();
		this.stateID = state;
	}
    
    @Override
    public int getID() {
    	return stateID;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame game)
    		throws SlickException{
    	background = new Image("res/Scoreboard.jpg");
    	selector = new Image("res/selector.png");
		font = new Font("Trajan Pro", Font.BOLD, 30);
		ttf = new TrueTypeFont(font, true);
		selectFx = new Sound("res/menuselect.wav");
		menuEnterFx = new Sound("res/menuEnter.wav");
    	selector.setAlpha(.5f);
		
    	try {
    		ScoreTableLoader stl = new ScoreTableLoader("scores.txt");
    		scores = new ArrayList<ScoreRecord>(stl.loadScoreTable());
    	}	catch (FileNotFoundException e) {
    			e.printStackTrace();
    	}	catch (IOException e) {
    			e.printStackTrace();
    	}
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics graphics)
    		throws SlickException{
    	background.draw();
    	// sorts the high scores from highest to lowest
    	Collections.sort(scores);
    	
    	// draws the top 20 scores
    	int counter = 1;
    	for (ScoreRecord score: scores) {
    		if (counter >= 21)
    			break;
    		else if (counter <= 10){
    			ttf.drawString(100, 80 + counter * 78, counter + " - " + score.getName());
    			ttf.drawString(630 - ttf.getWidth(score.getPoints().toString()), 80 + counter * 78, score.getPoints().toString());
    		}	
    		else if (counter <= 20 && counter > 10){
    			ttf.drawString(660, 80 + (counter - 10) * 78, counter + " - " + score.getName());
    			ttf.drawString(1190 - ttf.getWidth(score.getPoints().toString()), 80 + (counter - 10) * 78, score.getPoints().toString());
    		}
    		counter++;
    	}
    	if (selection == BACK){
    		selector.draw(400, 910);
    	}
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta)
    		throws SlickException {
    	
    	try {
    		ScoreTableLoader stl = new ScoreTableLoader("scores.txt");
    		scores = new ArrayList<ScoreRecord>(stl.loadScoreTable());
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		if (optionSelected == BACK) {
			game.enterState(GauchoSpace.MAIN_MENU, new FadeOutTransition(Color.black),
                    new FadeInTransition(Color.black));
			optionSelected = -1;
			selection = -1;
		}
    }
    
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		// boundary box for continue screen buttons
			if (newy > 910 && newy < 990) {
				if (newx > 400 && newx < 900) {
					selection = BACK;
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
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		if (optionSelected != selection) menuEnterFx.play(1, .4f);
		optionSelected = selection;
	}	
	
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
		if (key == Input.KEY_UP || key == Input.KEY_DOWN || key == Input.KEY_LEFT || key == Input.KEY_RIGHT) {
			selection = BACK;
			selectSoundTracker(selection);
		}
		else if (key == Input.KEY_Z) {
			if (optionSelected != selection) menuEnterFx.play(1, .4f);
			optionSelected = selection;
		}
	}
}
