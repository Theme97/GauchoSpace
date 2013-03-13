package com.GauchoSpace;

import org.newdawn.slick.Graphics;

public interface ILevel {
	public void renderBackground(GameField field, LevelManager manager, Graphics graphics);
	public void renderForeground(GameField field, LevelManager manager, Graphics graphics);
	public void update(GameField field, LevelManager manager, int ticks);
}
