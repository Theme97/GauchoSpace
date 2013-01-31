package com.GauchoSpace.Levels;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import com.GauchoSpace.GameField;
import com.GauchoSpace.IBullet;
import com.GauchoSpace.ILevel;
import com.GauchoSpace.LevelManager;
import com.GauchoSpace.Bullets.*;

public class Level1 implements ILevel {
	@Override
	public void update(GameField field, LevelManager manager, int ticks) {
		int x = field.getWidth() / 2;
		field.addEnemyBullet(new LinearBullet(field, x, 100, 5, ticks * 3, 5, Color.red));
		field.addEnemyBullet(new LinearBullet(field, x, 100, 5, ticks * 3 + 91, 5, Color.green));
		field.addEnemyBullet(new LinearBullet(field, x, 100, 5, ticks * 3 + 182, 5, Color.blue));
		
		if (ticks % 50 == 0) {
			float angle = (float)field.getPlayer().getPos().sub(new Vector2f(x, 100)).getTheta();
			field.addEnemyBullet(new AcceleratedBullet(field, x, 100, 20, -0.21f, angle, 50, 5, Color.white));
		}
	}
}
