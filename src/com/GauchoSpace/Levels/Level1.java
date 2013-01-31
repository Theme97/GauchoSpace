package com.GauchoSpace.Levels;

import org.newdawn.slick.Color;

import com.GauchoSpace.GameField;
import com.GauchoSpace.IBullet;
import com.GauchoSpace.ILevel;
import com.GauchoSpace.LevelManager;
import com.GauchoSpace.Bullets.LinearBullet;

public class Level1 implements ILevel {
	@Override
	public void update(GameField field, LevelManager manager, int ticks) {
		int x = field.getWidth() / 2;
		field.addEnemyBullet(new LinearBullet(field, x, 40, 5, ticks * 3, 5, Color.red));
		field.addEnemyBullet(new LinearBullet(field, x, 40, 5, ticks * 3 + 91, 5, Color.green));
		field.addEnemyBullet(new LinearBullet(field, x, 40, 5, ticks * 3 + 182, 5, Color.blue));
	}
}
