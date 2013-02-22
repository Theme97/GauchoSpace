package com.GauchoSpace.Levels;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.GauchoSpace.Enemy;
import com.GauchoSpace.GameField;
import com.GauchoSpace.IBullet;
import com.GauchoSpace.ILevel;
import com.GauchoSpace.LevelManager;
import com.GauchoSpace.UpdateHandler;
import com.GauchoSpace.Bullets.*;
import com.GauchoSpace.Motion.Bezier;
import com.GauchoSpace.Motion.CatmullRom;
import com.GauchoSpace.Motion.Lerp;

public class Level1 implements ILevel {
	@Override
	public void update(GameField field, LevelManager manager, int ticks) {
		// set up player
		if (ticks == 0) {
			field.getPlayer().setPos(new Vector2f(field.getWidth() / 2, field.getHeight() - 50));
		}
		
		// first wave
		if (ticks == 120) {
			ArrayList<Vector2f> points = new ArrayList<Vector2f>();
			points.add(new Vector2f(1327, 300));
			points.add(new Vector2f(878, 300));
			points.add(new Vector2f(429, 300));
			points.add(new Vector2f(429, 700));
			points.add(new Vector2f(-20, 700));
			points.add(new Vector2f(-479, 700));
			
			try {
				Enemy enemy = new TestEnemy2(field, new Image("res/enemy_test.png"), 20, 500);
				enemy.setMotion(new CatmullRom(points, 300));
				field.addEnemy(enemy);
			} catch (SlickException e) {
				e.printStackTrace(); // TODO
			}
		}
		
		// second wave
		if (ticks > 420 && ticks <= 570 && ticks % 30 == 0) {
			try {
				Enemy enemy = new TestEnemy(field, new Image("res/enemy_test.png"), 20, 100);
				enemy.setMotion(new Lerp(-20, 100, field.getWidth() + 20, 100, 300));
				field.addEnemy(enemy);
			} catch (SlickException e) {
				e.printStackTrace(); // TODO
			}
		}
		
		// third wave
		if (ticks > 600 && ticks <= 780 && ticks % 30 == 0) {
			try {
				ArrayList<Vector2f> points = new ArrayList<Vector2f>();
				points.add(new Vector2f(878, 500));
				points.add(new Vector2f(654, 1000));
				points.add(new Vector2f(429, -1000));
				points.add(new Vector2f(205, 1000));
				points.add(new Vector2f(-20, 500));
				
				Enemy enemy = new TestEnemy(field, new Image("res/enemy_test.png"), 20, 100);
				enemy.setMotion(new Bezier(points, 300));
				field.addEnemy(enemy);
			} catch (SlickException e) {
				e.printStackTrace(); // TODO
			}
		}
	}
}

class TestEnemy extends Enemy {
	int spawntime;
	
	TestEnemy(GameField field, Image sprite, int radius, int health) {
		super(field, sprite, radius, health);
		
		spawntime = field.getTicks();
		
		setUpdateHandler(new UpdateHandler() {
			@Override
			public void onUpdate(GameField field, int ticks) {
				updateHandler(field, ticks);
			}
		});
	}
	
	void updateHandler(GameField field, int totalTicks) {
		int ticks = totalTicks - spawntime;
		if (ticks >= 60 && ticks <= 240 && ticks % 30 == 0) {
			Vector2f pos = getPos();
			float angle = (float)field.getPlayer().getPos().sub(pos).getTheta();
			field.addEnemyBullet(new LinearBullet(field, pos.x, pos.y, 5, angle - 2, 5, Color.blue));
			field.addEnemyBullet(new LinearBullet(field, pos.x, pos.y, 5, angle + 2, 5, Color.blue));
		}
	}
}

class TestEnemy2 extends Enemy {
	int spawntime;
	
	TestEnemy2(GameField field, Image sprite, int radius, int health) {
		super(field, sprite, radius, health);
		
		spawntime = field.getTicks();
		
		setUpdateHandler(new UpdateHandler() {
			@Override
			public void onUpdate(GameField field, int ticks) {
				updateHandler(field, ticks);
			}
		});
	}
	
	void updateHandler(GameField field, int totalTicks) {
		int ticks = totalTicks - spawntime;
		if (ticks % 10 == 0) {
			Vector2f pos = getPos();
			field.addEnemyBullet(new AcceleratedBullet(field, pos.x, pos.y, 0, 0.1f, 90, 30, 3, Color.green));
		}
	}
}