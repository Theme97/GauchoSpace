package com.GauchoSpace.Motion;

import java.util.List;
import java.util.Vector;

import org.newdawn.slick.geom.Vector2f;

import com.GauchoSpace.GameField;
import com.GauchoSpace.IMotion;

public class Bezier implements IMotion {
	private List<Vector2f> points;
	int curFrame;
	int frames;
	
	public Bezier(List<Vector2f> points, int frames) {
		this.points = points;
		this.curFrame = 0;
		this.frames = frames;
	}
	
	public static Vector2f calc(List<Vector2f> points, float time) {
		int size = points.size();
		
		if (size > 2) {
			List<Vector2f> points2 = new Vector<Vector2f>();
			for (int i = 0; i < size - 1; i++) {
				points2.add(new Vector2f(Lerp.calc(points.get(i), points.get(i + 1), time)));
			}
			return calc(points2, time);
		} else if (size == 2) {
			return Lerp.calc(points.get(0), points.get(1), time);
		} else {
			return null;
		}
	}
	
	@Override
	public Vector2f update(GameField field, int ticks) {
		if (curFrame <= frames) {
			return calc(points, (float)curFrame++ / frames);
		} else {
			return null;
		}
	}
}
