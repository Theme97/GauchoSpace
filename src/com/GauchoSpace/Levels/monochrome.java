package com.GauchoSpace.Levels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
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

	private boolean pulseFlow;

	public monochrome() {
		try {
			music = new Music("res/monochrome/music.ogg");
			leftPulseImage = new Image("res/monochrome/pulse.png");
			rightPulseImage = leftPulseImage.getFlippedCopy(true, false);
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

		Action enableFlow = new Action() {
			@Override
			public void perform(GameField field) { setPulseFlow(true); }
		};

		actions = new LinkedList<TimedAction>();

		// 1
		actions.add(new TimedAction(  0.540, pulseFromLeft));  // kick
		actions.add(new TimedAction(  1.400, pulseFromRight)); // snare

		// 2
		actions.add(new TimedAction(  2.254, pulseFromLeft));  // kick
		actions.add(new TimedAction(  2.683, pulseFromLeft));  // kick
		actions.add(new TimedAction(  3.112, pulseFromRight)); // snare

		// 3
		actions.add(new TimedAction(  3.969, pulseFromLeft));  // kick
		actions.add(new TimedAction(  4.825, pulseFromRight)); // snare

		// 4
		actions.add(new TimedAction(  5.683, pulseFromLeft));  // kick
		actions.add(new TimedAction(  6.111, pulseFromLeft));  // kick
		actions.add(new TimedAction(  6.541, pulseFromRight)); // snare

		// 5
		actions.add(new TimedAction(  7.397, pulseFromLeft));  // kick
		actions.add(new TimedAction(  8.253, pulseFromRight)); // snare

		// 6
		actions.add(new TimedAction(  9.111, pulseFromLeft));  // kick
		actions.add(new TimedAction(  9.541, pulseFromLeft));  // kick
		actions.add(new TimedAction(  9.969, pulseFromRight)); // snare

		// 7
		actions.add(new TimedAction( 10.825, pulseFromLeft));  // kick
		actions.add(new TimedAction( 11.684, pulseFromRight)); // snare

		// 8
		actions.add(new TimedAction( 12.539, pulseFromLeft));  // kick
		actions.add(new TimedAction( 12.969, pulseFromLeft));  // kick
		actions.add(new TimedAction( 13.396, pulseFromRight)); // snare
		actions.add(new TimedAction( 13.825, pulseFromRight)); // snare

		// 9
		actions.add(new TimedAction( 14.254, pulseFromLeft));  // kick
		actions.add(new TimedAction( 15.111, pulseFromRight)); // snare

		// 10
		actions.add(new TimedAction( 15.970, pulseFromLeft));  // kick
		actions.add(new TimedAction( 16.401, pulseFromLeft));  // kick
		actions.add(new TimedAction( 16.827, pulseFromRight)); // snare

		// 11
		actions.add(new TimedAction( 17.684, pulseFromLeft));  // kick
		actions.add(new TimedAction( 18.542, pulseFromRight)); // snare

		// 12
		actions.add(new TimedAction( 19.398, pulseFromLeft));  // kick
		actions.add(new TimedAction( 19.829, pulseFromLeft));  // kick
		actions.add(new TimedAction( 20.254, pulseFromRight)); // snare

		// 13
		actions.add(new TimedAction( 21.111, pulseFromLeft));  // kick
		actions.add(new TimedAction( 21.970, pulseFromRight)); // snare

		// 14
		actions.add(new TimedAction( 22.826, pulseFromLeft));  // kick
		actions.add(new TimedAction( 23.253, pulseFromLeft));  // kick
		actions.add(new TimedAction( 23.683, pulseFromRight)); // snare

		// 15
		actions.add(new TimedAction( 24.541, pulseFromLeft));  // kick
		actions.add(new TimedAction( 25.397, pulseFromRight)); // snare

		// 16
		actions.add(new TimedAction( 26.254, pulseFromLeft));  // kick
		actions.add(new TimedAction( 26.683, pulseFromLeft));  // kick
		actions.add(new TimedAction( 27.111, pulseFromRight)); // snare
		actions.add(new TimedAction( 27.326, pulseFromRight)); // snare
		actions.add(new TimedAction( 27.468, pulseFromRight)); // snare
		actions.add(new TimedAction( 27.539, pulseFromRight)); // snare
		actions.add(new TimedAction( 27.754, pulseFromRight)); // snare

		// 17
		actions.add(new TimedAction( 27.968, enableFlow));     // FLOW
		actions.add(new TimedAction( 27.968, pulseFromLeft));  // kick
		actions.add(new TimedAction( 28.397, pulseFromRight)); // snare
		actions.add(new TimedAction( 28.718, pulseFromLeft));  // kick
		actions.add(new TimedAction( 29.028, pulseFromLeft));  // kick
		actions.add(new TimedAction( 29.251, pulseFromRight)); // snare

		// 18
		actions.add(new TimedAction( 29.684, pulseFromLeft));  // kick
		actions.add(new TimedAction( 30.108, pulseFromRight)); // snare
		actions.add(new TimedAction( 30.432, pulseFromLeft));  // kick
		actions.add(new TimedAction( 30.753, pulseFromLeft));  // kick
		actions.add(new TimedAction( 30.968, pulseFromRight)); // snare

		// 19
		actions.add(new TimedAction( 31.397, pulseFromLeft));  // kick
		actions.add(new TimedAction( 31.824, pulseFromRight)); // snare
		actions.add(new TimedAction( 32.148, pulseFromLeft));  // kick
		actions.add(new TimedAction( 32.461, pulseFromLeft));  // kick
		actions.add(new TimedAction( 32.683, pulseFromRight)); // snare

		// 20
		actions.add(new TimedAction( 33.109, pulseFromLeft));  // kick
		actions.add(new TimedAction( 33.543, pulseFromRight)); // snare
		actions.add(new TimedAction( 33.862, pulseFromLeft));  // kick
		actions.add(new TimedAction( 34.181, pulseFromLeft));  // kick
		actions.add(new TimedAction( 34.396, pulseFromRight)); // snare

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
		// TODO Auto-generated method stub
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

			try {
				sprite = new Image("res/monochrome/pulse_bullet.png");
				sprite.setCenterOfRotation(6, 9);
				sprite.rotate(90 * direction);
			} catch (SlickException e) {
				e.printStackTrace();
			}
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

			boolean deletable = (direction == 1) ? (pos.x > 858) : (pos.x < 0);
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
			graphics.drawImage(sprite, (int)pos.x, (int)pos.y);
		}

		@Override
		public void update(GameContainer gc, StateBasedGame game, int delta) { 
			pos.x += speed * direction;
			speed = Math.max(speed - 3, flow ? 1f : 0f);
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
}
