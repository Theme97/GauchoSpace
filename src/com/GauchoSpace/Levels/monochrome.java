package com.GauchoSpace.Levels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.GauchoSpace.GameField;
import com.GauchoSpace.IBullet;
import com.GauchoSpace.ICharacter;
import com.GauchoSpace.ILevel;
import com.GauchoSpace.LevelManager;

public class monochrome implements ILevel {
	private Music music;
	private long time;
	private Queue<TimedAction> actions;
	private double nextActionTime;

	private int leftPulseTicks;
	private Image leftPulseImage;
	private Collection<PulseBullet> leftPulseBullets;

	private int rightPulseTicks;
	private Image rightPulseImage;
	private Collection<PulseBullet> rightPulseBullets;
	
	private Image bottomPulseImage;
	private int bottomPulseTicks;

	private boolean pulseFlow;
	
	private ResourceHandler resources;
	private Image image01;
	private Image image02;

	public monochrome() {
		try {
			music = new Music("res/monochrome/music.ogg");
			leftPulseImage = new Image("res/monochrome/pulse.png");
			rightPulseImage = leftPulseImage.getFlippedCopy(true, false);
			bottomPulseImage = new Image("res/monochrome/pulse_bottom.png");
			image01 = new Image("res/monochrome/bg/01.png");
			image02 = new Image("res/monochrome/bg/02.png");
			
			resources = new ResourceHandler();
			resources.put("pulse bullet", "res/monochrome/pulse_bullet.png");
			resources.put("gravity bullet", "res/monochrome/gravity_bullet.png");
			
			Image synthBullets = new Image("res/monochrome/synth_bullet.png", false, Image.FILTER_LINEAR);
			SpriteSheet spriteSheet = new SpriteSheet(synthBullets.getScaledCopy(2), 24, 22);
			resources.put("synth 0", spriteSheet.getSprite(0, 0));
			resources.put("synth 1", spriteSheet.getSprite(1, 0));
			resources.put("synth 2", spriteSheet.getSprite(2, 0));
			resources.put("synth 3", spriteSheet.getSprite(3, 0));
			resources.put("synth 4", spriteSheet.getSprite(4, 0));
			resources.put("synth 5", spriteSheet.getSprite(5, 0));
			resources.put("synth 6", spriteSheet.getSprite(6, 0));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		leftPulseBullets = new ArrayList<PulseBullet>();
		rightPulseBullets = new ArrayList<PulseBullet>();

		Action pulseFromLeft = new Action() {
			@Override
			public void perform(GameField field) { pulseFromLeft(field); }
		};

		Action pulseFromRight = new Action() {
			@Override
			public void perform(GameField field) { pulseFromRight(field); }
		};
		
		Action pulseFromBottom = new Action() {
			@Override
			public void perform(GameField field) { pulseFromBottom(field); }
		};

		Action enableFlow = new Action() {
			@Override
			public void perform(GameField field) { setPulseFlow(true); }
		};

		actions = new PriorityQueue<TimedAction>(100, new Comparator<TimedAction>() {
			public int compare(TimedAction a, TimedAction b) {
				return Double.valueOf(a.getTime()).compareTo(Double.valueOf(b.getTime()));
			}
		});

		// 1
		actions.add(new TimedAction(  0.540, pulseFromBottom));
		actions.add(new TimedAction(  0.540, pulseFromLeft));  // kick
		actions.add(new TimedAction(  1.400, pulseFromBottom));
		actions.add(new TimedAction(  1.400, pulseFromRight)); // snare

		// 2
		actions.add(new TimedAction(  2.254, pulseFromBottom));
		actions.add(new TimedAction(  2.254, pulseFromLeft));  // kick
		actions.add(new TimedAction(  2.683, pulseFromLeft));  // kick
		actions.add(new TimedAction(  3.112, pulseFromBottom));
		actions.add(new TimedAction(  3.112, pulseFromRight)); // snare

		// 3
		actions.add(new TimedAction(  3.969, pulseFromBottom));
		actions.add(new TimedAction(  3.969, pulseFromLeft)); // kick
		actions.add(new TimedAction(  4.825, pulseFromBottom));
		actions.add(new TimedAction(  4.825, pulseFromRight)); // snare

		// 4
		actions.add(new TimedAction(  5.683, pulseFromBottom));
		actions.add(new TimedAction(  5.683, pulseFromLeft)); // kick
		actions.add(new TimedAction(  6.111, pulseFromLeft)); // kick
		actions.add(new TimedAction(  6.541, pulseFromBottom));
		actions.add(new TimedAction(  6.541, pulseFromRight)); // snare

		// 5
		actions.add(new TimedAction(  7.397, pulseFromBottom));
		actions.add(new TimedAction(  7.397, pulseFromLeft)); // kick
		actions.add(new TimedAction(  8.253, pulseFromBottom));
		actions.add(new TimedAction(  8.253, pulseFromRight)); // snare

		// 6
		actions.add(new TimedAction(  9.111, pulseFromBottom));
		actions.add(new TimedAction(  9.111, pulseFromLeft)); // kick
		actions.add(new TimedAction(  9.541, pulseFromLeft)); // kick
		actions.add(new TimedAction(  9.969, pulseFromBottom));
		actions.add(new TimedAction(  9.969, pulseFromRight)); // snare

		// 7
		actions.add(new TimedAction( 10.825, pulseFromBottom));
		actions.add(new TimedAction( 10.825, pulseFromLeft)); // kick
		actions.add(new TimedAction( 11.684, pulseFromBottom));
		actions.add(new TimedAction( 11.684, pulseFromRight)); // snare

		// 8
		actions.add(new TimedAction( 12.539, pulseFromBottom));
		actions.add(new TimedAction( 12.539, pulseFromLeft)); // kick
		actions.add(new TimedAction( 12.969, pulseFromLeft)); // kick
		actions.add(new TimedAction( 13.396, pulseFromBottom));
		actions.add(new TimedAction( 13.396, pulseFromRight)); // snare
		actions.add(new TimedAction( 13.825, pulseFromRight)); // snare

		// 9
		actions.add(new TimedAction( 14.254, pulseFromBottom));
		actions.add(new TimedAction( 14.254, pulseFromLeft)); // kick
		actions.add(new TimedAction( 15.111, pulseFromBottom));
		actions.add(new TimedAction( 15.111, pulseFromRight)); // snare

		// 10
		actions.add(new TimedAction( 15.970, pulseFromBottom));
		actions.add(new TimedAction( 15.970, pulseFromLeft)); // kick
		actions.add(new TimedAction( 16.401, pulseFromLeft)); // kick
		actions.add(new TimedAction( 16.827, pulseFromBottom));
		actions.add(new TimedAction( 16.827, pulseFromRight)); // snare

		// 11
		actions.add(new TimedAction( 17.684, pulseFromBottom));
		actions.add(new TimedAction( 17.684, pulseFromLeft)); // kick
		actions.add(new TimedAction( 18.542, pulseFromBottom));
		actions.add(new TimedAction( 18.542, pulseFromRight)); // snare

		// 12
		actions.add(new TimedAction( 19.398, pulseFromBottom));
		actions.add(new TimedAction( 19.398, pulseFromLeft)); // kick
		actions.add(new TimedAction( 19.829, pulseFromLeft)); // kick
		actions.add(new TimedAction( 20.254, pulseFromBottom));
		actions.add(new TimedAction( 20.254, pulseFromRight)); // snare

		// 13
		actions.add(new TimedAction( 21.111, pulseFromBottom));
		actions.add(new TimedAction( 21.111, pulseFromLeft)); // kick
		actions.add(new TimedAction( 21.970, pulseFromBottom));
		actions.add(new TimedAction( 21.970, pulseFromRight)); // snare

		// 14
		actions.add(new TimedAction( 22.826, pulseFromBottom));
		actions.add(new TimedAction( 22.826, pulseFromLeft)); // kick
		actions.add(new TimedAction( 23.253, pulseFromLeft)); // kick
		actions.add(new TimedAction( 23.683, pulseFromBottom));
		actions.add(new TimedAction( 23.683, pulseFromRight)); // snare

		// 15
		actions.add(new TimedAction( 24.541, pulseFromBottom));
		actions.add(new TimedAction( 24.541, pulseFromLeft)); // kick
		actions.add(new TimedAction( 25.397, pulseFromBottom));
		actions.add(new TimedAction( 25.397, pulseFromRight)); // snare

		// 16
		actions.add(new TimedAction( 26.254, pulseFromBottom));
		actions.add(new TimedAction( 26.254, pulseFromLeft)); // kick
		actions.add(new TimedAction( 26.683, pulseFromLeft)); // kick
		actions.add(new TimedAction( 27.111, pulseFromBottom));
		actions.add(new TimedAction( 27.111, pulseFromRight)); // snare
		actions.add(new TimedAction( 27.326, pulseFromRight)); // snare
		actions.add(new TimedAction( 27.468, pulseFromRight)); // snare
		actions.add(new TimedAction( 27.539, pulseFromRight)); // snare
		actions.add(new TimedAction( 27.754, pulseFromRight)); // snare

		// 17
		actions.add(new TimedAction( 27.968, enableFlow)); // FLOW
		
		actions.add(new TimedAction( 27.968, pulseFromBottom));
		actions.add(new TimedAction( 27.968, pulseFromLeft)); // kick
		actions.add(new TimedAction( 28.397, pulseFromRight)); // snare
		actions.add(new TimedAction( 28.718, pulseFromLeft)); // kick
		actions.add(new TimedAction( 28.802, pulseFromBottom));
		actions.add(new TimedAction( 29.028, pulseFromLeft)); // kick
		actions.add(new TimedAction( 29.251, pulseFromRight)); // snare
		
		actions.add(new TimedAction( 27.968        , synthPattern(2)));
		actions.add(new TimedAction( 27.968 + 0.321, synthPattern(3)));
		actions.add(new TimedAction( 27.968 + 0.643, synthPattern(4)));
		actions.add(new TimedAction( 27.968 + 0.964, synthPattern(5)));
		actions.add(new TimedAction( 27.968 + 1.286, synthPattern(0)));
		actions.add(new TimedAction( 27.968 + 1.500, synthPattern(1)));

		// 18
		actions.add(new TimedAction( 29.684, pulseFromBottom));
		actions.add(new TimedAction( 29.684, pulseFromLeft)); // kick
		actions.add(new TimedAction( 30.108, pulseFromRight)); // snare
		actions.add(new TimedAction( 30.432, pulseFromLeft)); // kick
		actions.add(new TimedAction( 30.536, pulseFromBottom));
		actions.add(new TimedAction( 30.753, pulseFromLeft)); // kick
		actions.add(new TimedAction( 30.968, pulseFromRight)); // snare
		
		actions.add(new TimedAction( 29.684        , synthPattern(2)));
		actions.add(new TimedAction( 29.684 + 0.321, synthPattern(3)));
		actions.add(new TimedAction( 29.684 + 0.643, synthPattern(4)));
		actions.add(new TimedAction( 29.684 + 0.964, synthPattern(5)));
		actions.add(new TimedAction( 29.684 + 1.286, synthPattern(0)));
		actions.add(new TimedAction( 29.684 + 1.500, synthPattern(1)));

		// 19
		actions.add(new TimedAction( 31.397, pulseFromBottom));
		actions.add(new TimedAction( 31.397, pulseFromLeft)); // kick
		actions.add(new TimedAction( 31.824, pulseFromRight)); // snare
		actions.add(new TimedAction( 32.148, pulseFromLeft)); // kick
		actions.add(new TimedAction( 32.268, pulseFromBottom));
		actions.add(new TimedAction( 32.461, pulseFromLeft)); // kick
		actions.add(new TimedAction( 32.683, pulseFromRight)); // snare
		
		actions.add(new TimedAction( 31.397        , synthPattern(2)));
		actions.add(new TimedAction( 31.397 + 0.321, synthPattern(3)));
		actions.add(new TimedAction( 31.397 + 0.643, synthPattern(4)));
		actions.add(new TimedAction( 31.397 + 0.964, synthPattern(5)));
		actions.add(new TimedAction( 31.397 + 1.286, synthPattern(0)));
		actions.add(new TimedAction( 31.397 + 1.500, synthPattern(1)));

		// 20
		actions.add(new TimedAction( 33.109, pulseFromBottom));
		actions.add(new TimedAction( 33.109, pulseFromLeft)); // kick
		actions.add(new TimedAction( 33.543, pulseFromRight)); // snare
		actions.add(new TimedAction( 33.862, pulseFromLeft)); // kick
		actions.add(new TimedAction( 33.963, pulseFromBottom));
		actions.add(new TimedAction( 34.181, pulseFromLeft)); // kick
		actions.add(new TimedAction( 34.396, pulseFromRight)); // snare
		
		actions.add(new TimedAction( 33.109        , synthPattern(2)));
		actions.add(new TimedAction( 33.109 + 0.321, synthPattern(3)));
		actions.add(new TimedAction( 33.109 + 0.643, synthPattern(4)));
		actions.add(new TimedAction( 33.109 + 0.964, synthPattern(5)));
		actions.add(new TimedAction( 33.109 + 1.286, synthPattern(0)));
		actions.add(new TimedAction( 33.109 + 1.500, synthPattern(1)));
		
		// 21
		actions.add(new TimedAction( 34.825, pulseFromBottom));
		actions.add(new TimedAction( 34.825, pulseFromLeft)); // kick
		actions.add(new TimedAction( 35.255, pulseFromRight)); // snare
		actions.add(new TimedAction( 35.577, pulseFromLeft)); // kick
		actions.add(new TimedAction( 35.695, pulseFromBottom));
		actions.add(new TimedAction( 35.889, pulseFromLeft)); // kick
		actions.add(new TimedAction( 36.112, pulseFromRight)); // snare
		
		actions.add(new TimedAction( 34.825        , synthPattern(2)));
		actions.add(new TimedAction( 34.825 + 0.321, synthPattern(3)));
		actions.add(new TimedAction( 34.825 + 0.643, synthPattern(4)));
		actions.add(new TimedAction( 34.825 + 0.964, synthPattern(5)));
		actions.add(new TimedAction( 34.825 + 1.286, synthPattern(0)));
		actions.add(new TimedAction( 34.825 + 1.500, synthPattern(1)));
		
		// 22
		actions.add(new TimedAction( 36.540, pulseFromBottom));
		actions.add(new TimedAction( 36.540, pulseFromLeft)); // kick
		actions.add(new TimedAction( 36.969, pulseFromRight)); // snare
		actions.add(new TimedAction( 37.291, pulseFromLeft)); // kick
		actions.add(new TimedAction( 37.425, pulseFromBottom));
		actions.add(new TimedAction( 37.612, pulseFromLeft)); // kick
		actions.add(new TimedAction( 37.827, pulseFromRight)); // snare
		
		actions.add(new TimedAction( 36.540        , synthPattern(2)));
		actions.add(new TimedAction( 36.540 + 0.321, synthPattern(3)));
		actions.add(new TimedAction( 36.540 + 0.643, synthPattern(4)));
		actions.add(new TimedAction( 36.540 + 0.964, synthPattern(5)));
		actions.add(new TimedAction( 36.540 + 1.286, synthPattern(0)));
		actions.add(new TimedAction( 36.540 + 1.500, synthPattern(1)));

		// done!
		nextActionTime = actions.peek().getTime();
	}

	@Override
	public void update(GameField field, LevelManager manager, int ticks) {
		// init
		if (ticks == 0) {
			music.play();
			time = System.currentTimeMillis();
		}

		// clean up dead pulse bullets
		{
			Iterator<PulseBullet> i;

			// left
			i = leftPulseBullets.iterator();
			while (i.hasNext())
				if (i.next().isDeletable())
					i.remove();

			// right
			i = rightPulseBullets.iterator();
			while (i.hasNext())
				if (i.next().isDeletable())
					i.remove();
		}

		// perform next action in queue
		if (nextActionTime > -1) {
			double elapsedTime = (double)(System.currentTimeMillis() - time) * 0.001;
			while (nextActionTime <= elapsedTime) {
				Action action = actions.poll().getAction();
				action.perform(field);

				if (actions.isEmpty()) {
					nextActionTime = -1;
					break;
				}

				nextActionTime = actions.peek().getTime();
			}
		}
	}

	@Override
	public void renderBackground(GameField field, LevelManager manager, Graphics graphics) {
		float elapsedTime = (float)(System.currentTimeMillis() - time) * 0.001f;
		
		if (0.540 <= elapsedTime && elapsedTime <= 7.397) { // 6.857
			float t = elapsedTime - 0.540f;
			if (t <= 0.9) {
				image01.setAlpha(t);
			} else if (6.057 <= t) {
				image01.setAlpha(6.857f - t);
			}
			graphics.drawImage(image01, 100 + t*12.7f, 150 + t*7.3f);
		}
		
		if (7.397 <= elapsedTime && elapsedTime <= 14.254) { // 6.857
			float t = elapsedTime - 7.397f;
			if (t <= 0.9) {
				image02.setAlpha(t);
			} else if (6.057 <= t) {
				image02.setAlpha(6.857f - t);
			}
			graphics.drawImage(image02, 40 + t*2.9f, 200 + t*7.3f);
		}
	}

	@Override
	public void renderForeground(GameField field, LevelManager manager, Graphics graphics) {
		if (leftPulseTicks > 0) {
			leftPulseImage.setAlpha((float)leftPulseTicks / 45);
			graphics.drawImage(leftPulseImage, 0, 0);
			leftPulseTicks--;
		}

		if (rightPulseTicks > 0) {
			rightPulseImage.setAlpha((float)rightPulseTicks / 45);
			graphics.drawImage(rightPulseImage, 778, 0);
			rightPulseTicks--;
		}
		
		if (bottomPulseTicks > 0) {
			bottomPulseImage.setAlpha((float)bottomPulseTicks / 40);
			graphics.drawImage(bottomPulseImage, 0, 960);
			bottomPulseTicks--;
		}
	}

	private void pulseFromLeft(GameField field) {
		leftPulseTicks = 60;

		for (int i = 0; i < 3; i++) {
			PulseBullet bullet = new PulseBullet(1, pulseFlow);
			leftPulseBullets.add(bullet);
			field.addEnemyBullet(bullet);
		}

		for (PulseBullet bullet : leftPulseBullets) bullet.pulse();
	}

	private void pulseFromRight(GameField field) {
		rightPulseTicks = 60;

		for (int i = 0; i < 3; i++) {
			PulseBullet bullet = new PulseBullet(-1, pulseFlow);
			rightPulseBullets.add(bullet);
			field.addEnemyBullet(bullet);
		}

		for (PulseBullet bullet : rightPulseBullets) bullet.pulse();
	}

	private void setPulseFlow(boolean flow) {
		pulseFlow = flow;
		for (PulseBullet bullet : leftPulseBullets)  bullet.flow(flow);
		for (PulseBullet bullet : rightPulseBullets) bullet.flow(flow);
	}
	
	private void pulseFromBottom(GameField field) {
		bottomPulseTicks = 100;
		
		float x = (float)Math.random() * 758 + 50f;
		float angle = (float)Math.random() * 15 + 82.5f;
		field.addEnemyBullet(new GravityBullet(x, angle - 5));
		field.addEnemyBullet(new GravityBullet(x, angle    ));
		field.addEnemyBullet(new GravityBullet(x, angle + 5));
	}
	
	private Action synthPattern(final int i) {
		return new Action() {
			@Override
			public void perform(GameField field) {
				field.addEnemyBullet(new SynthBullet(i));
			}
		};
	}

	/* ------- *
	 * Bullets *
	 * ------- */
	private class PulseBullet implements IBullet {
		private Vector2f pos;
		private int direction;
		private Image sprite;
		private boolean dead;

		private float speed;
		private boolean flow;

		public PulseBullet(int direction, boolean flow) {
			this.direction = direction;
			this.flow = flow;
			pos = new Vector2f((direction == 1) ? -5 : 863, (float)Math.random() * 1000);

			sprite = resources.get("pulse bullet").copy();
			sprite.setCenterOfRotation(6, 9);
			sprite.rotate(90 * direction);
		}

		public void pulse() {
			speed = 10f;
		}

		public void flow(boolean flow) {
			this.flow = flow;
		}

		@Override
		public int getDamage() {
			return 1;
		}

		@Override
		public Vector2f getPos() {
			return pos.copy();
		}

		@Override
		public boolean isDeletable() {
			if (dead) return true;

			boolean deletable = (direction == 1) ? (pos.x > 863) : (pos.x < -5);
			if (deletable) destroy(); // TODO hack
			return deletable;
		}

		public void destroy() {
			dead = true;
		}

		@Override
		public boolean isColliding(ICharacter character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void render(GameContainer gc, StateBasedGame game, Graphics graphics) {
			graphics.drawImage(sprite, pos.x - 9, pos.y - 5);
		}

		@Override
		public void update(GameContainer gc, StateBasedGame game, int delta) { 
			pos.x += speed * direction;
			speed = Math.max(speed - 3, flow ? 1f : 0f);
		}
	}
	
	private class GravityBullet implements IBullet {
		private Vector2f pos;
		private Vector2f vel;
		private Vector2f accel;
		private Image sprite;
		
		public GravityBullet(float x, float angle) {
			pos = new Vector2f(x, 1002);
			vel = new Vector2f(-angle).scale(10);
			accel = new Vector2f(0, 0.1f);
			
			sprite = resources.get("gravity bullet").copy();
			sprite.setCenterOfRotation(4, 9);
		}
		
		@Override
		public int getDamage() {
			return 1;
		}

		@Override
		public Vector2f getPos() {
			return pos.copy();
		}

		@Override
		public boolean isDeletable() {
			return (pos.y > 1005); 
		}

		@Override
		public boolean isColliding(ICharacter character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void render(GameContainer gc, StateBasedGame game, Graphics graphics) {
			sprite.setRotation((float)vel.getTheta() + 90);
			Vector2f drawPos = pos.copy().sub(vel.copy().scale(0.5f));
			graphics.drawImage(sprite, drawPos.x, drawPos.y);
		}

		@Override
		public void update(GameContainer gc, StateBasedGame game, int delta) {
			pos.add(vel);
			vel.add(accel);
		}
	}
	
	private class SynthBullet implements IBullet {
		private Vector2f pos;
		private Image sprite;
		
		public SynthBullet(int i) {
			pos = new Vector2f((float)Math.random() * 858, -2);
			sprite = resources.get("synth " + i);
		}
		
		@Override
		public int getDamage() {
			return 1;
		}

		@Override
		public Vector2f getPos() {
			return pos.copy();
		}

		@Override
		public boolean isDeletable() {
			return (pos.y > 1010);
		}

		@Override
		public boolean isColliding(ICharacter character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void render(GameContainer gc, StateBasedGame game, Graphics graphics) {
			graphics.drawImage(sprite, pos.x - 12, pos.y - 11);
		}

		@Override
		public void update(GameContainer gc, StateBasedGame game, int delta) {
			pos.y += 3;
		}
	}

	/* ------------------------------------ *
	 * TimedAction class & Action interface *
	 * ------------------------------------ */
	private class TimedAction {
		private double time;
		private Action action;

		TimedAction(double time, Action action) {
			this.time = time;
			this.action = action;
		}

		public double getTime() {
			return time;
		}

		public Action getAction() {
			return action;
		}
	}

	private interface Action {
		public void perform(GameField field);
	}
	
	/* --------------------- *
	 * ResourceHandler class *
	 * --------------------- */
	private class ResourceHandler {
		private HashMap<String, Image> resources;
		
		ResourceHandler() {
			resources = new HashMap<String, Image>();
		}
		
		public boolean put(String ref) {
			try {
				return put(ref, new Image(ref));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		public boolean put(String name, String ref) {
			try {
				return put(name, new Image(ref));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		public boolean put(String name, Image image) {
			if (!resources.containsKey(name)) {
				resources.put(name, image);
				return true;
			} else {
				return false;
			}
		}
		
		public Image get(String name) {
			return resources.get(name);
		}
	}
}
