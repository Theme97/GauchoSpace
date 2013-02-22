package com.GauchoSpace.Motion;

import org.newdawn.slick.geom.Vector2f;

import com.GauchoSpace.GameField;
import com.GauchoSpace.IMotion;

public class Wait implements IMotion {
	private Vector2f pos;
	private int frames;
	
	public Wait(Vector2f pos, int frames) {
		this.pos = pos;
		this.frames = frames;
	}
	
	public Wait(float x, float y, int frames) {
		this(new Vector2f(x, y), frames);
	}
	
	@Override
	public Vector2f update(GameField field, int ticks) {
		if (frames > 0) {
			frames--;
			return pos;
		} else {
			return null;
		}
	}
}
