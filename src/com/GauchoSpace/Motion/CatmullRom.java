package com.GauchoSpace.Motion;

import java.util.List;
import org.newdawn.slick.geom.Vector2f;

import com.GauchoSpace.GameField;
import com.GauchoSpace.IMotion;

public class CatmullRom implements IMotion {
	private List<Vector2f> points;
	int size;
	float curTime;
	float stepTime;
	
	public CatmullRom(List<Vector2f> points, int frames) {
		this.points = points;
		this.size = points.size() - 2;
		this.curTime = 0f;
		this.stepTime = (float)(size - 1) / frames;
	}
	
	public static Vector2f calc(Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3, float t) {
		float t2 = t * t;
		float t3 = t2 * t;
		float m0 = (-0.5f * t3) + (        t2) + (-0.5f * t)         ;
		float m1 = ( 1.5f * t3) + (-2.5f * t2)               + (1.0f);
		float m2 = (-1.5f * t3) + ( 2.0f * t2) + ( 0.5f * t)         ;
		float m3 = ( 0.5f * t3) + (-0.5f * t2)                       ;
		return new Vector2f(
				(m0 * p0.x) + (m1 * p1.x) + (m2 * p2.x) + (m3 * p3.x),
				(m0 * p0.y) + (m1 * p1.y) + (m2 * p2.y) + (m3 * p3.y)
				);
	}
	
	public static Vector2f calc(List<Vector2f> points, float time) {
		if (points.size() != 4) throw new Error("wrong list size in CatmullRom.calc()"); // TODO
		return calc(points.get(0), points.get(1), points.get(2), points.get(3), time);
	}
	
	@Override
	public Vector2f update(GameField field, int ticks) {
		if (curTime < size - 1) {
			int index = (int)Math.floor(curTime);
			index = Math.min(index, size - 2);
			
			Vector2f pos = calc(points.subList(index, index + 4), curTime % 1);
			curTime += stepTime;
			
			return pos;
		} else {
			return null;
		}
	}
}
