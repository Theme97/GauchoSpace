package com.GauchoSpace;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class BossManager {
	private ICharacter boss;
	
	private int maxLife;
	private int timeout;
	
	private float bonusPoints;
	private float bonusDecay;
	
	private int attackTicks;
	private long attackTimeStart;
	
	private final Color uiColor = new Color(216, 216, 216);
	
	public void setBoss(ICharacter boss) {
		this.boss = boss;
	}
	
	public void startAttack(int life, int timeoutSeconds) {
		maxLife = life;
		timeout = timeoutSeconds;
		
		bonusPoints = 100000;
		bonusDecay = 10;
		
		attackTicks = 0;
		attackTimeStart = System.currentTimeMillis();
	}
	
	public ICharacter getBoss() {
		return boss;
	}
	
	public void setBonusPoints(int points) {
		bonusPoints = (float)points;
	}
	
	public void setBonusDecay(float decay) {
		bonusDecay = decay;
	}
	
	public boolean checkCollision(IBullet bullet) {
		return (boss != null) && bullet.isColliding(boss);
	}
	
	public void renderBoss(GameContainer gc, StateBasedGame game, Graphics graphics) {
		if (boss != null) boss.render(gc, game, graphics);
	}
	
	public void renderUI(GameContainer gc, StateBasedGame game, Graphics graphics) {
		if (boss != null) {
			graphics.setColor(uiColor);
			graphics.fillRect(20, 8, 798 * (float)boss.getHealth() / maxLife, 4);
			graphics.drawString(Integer.toString(timeout - attackTicks / 60), 832, 2);
			if (bonusPoints > 0)
				graphics.drawString("Bonus: " + (int)Math.floor(bonusPoints * 10), 710, 22);
			else
				graphics.drawString("Bonus: Failed", 710, 22);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		if (boss != null) {
			boss.update(gc, game, delta);
			
			attackTicks++;
			if (bonusPoints > 0) bonusPoints -= bonusDecay;
		}
	}
}
