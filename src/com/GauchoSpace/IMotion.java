package com.GauchoSpace;

import org.newdawn.slick.geom.Vector2f;

public interface IMotion {
	public Vector2f update(GameField field, int ticks);
}
