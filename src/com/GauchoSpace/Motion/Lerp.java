package com.GauchoSpace.Motion;

import org.newdawn.slick.geom.Vector2f;

import com.GauchoSpace.GameField;
import com.GauchoSpace.IMotion;

public class Lerp implements IMotion {
	private Vector2f start;
	private Vector2f end;
	int curFrame;
	int frames;
	
	public Lerp(Vector2f start, Vector2f end, int frames) {
		this.start = start;
		this.end = end;
		this.curFrame = 0;
		this.frames = frames;
	}
	
	public Lerp(float x1, float y1, float x2, float y2, int frames) {
		this(new Vector2f(x1, y1), new Vector2f(x2, y2), frames);
	}
	
	public static Vector2f calc(Vector2f start, Vector2f end, float time) {
		return new Vector2f(
				start.x + time * (end.x - start.x),
				start.y + time * (end.y - start.y)
				);
	}
	
	@Override
	public Vector2f update(GameField field, int ticks) {
		if (curFrame <= frames) {
			return Lerp.calc(start, end, (float)curFrame++ / frames);
		} else {
			return null;
		}
	}
}
