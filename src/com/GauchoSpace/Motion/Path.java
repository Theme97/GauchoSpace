package com.GauchoSpace.Motion;

import java.util.List;
import java.util.Vector;

import org.newdawn.slick.geom.Vector2f;

import com.GauchoSpace.GameField;
import com.GauchoSpace.IMotion;

public class Path implements IMotion {
	private List<IMotion> motions;
	
	public Path(IMotion... motions) {
		this.motions = new Vector<IMotion>();
		for (IMotion motion : motions) this.motions.add(motion); 
	}
	
	public Path(List<IMotion> motions) {
		this.motions = motions;
	}
	
	@Override
	public Vector2f update(GameField field, int ticks) {
		Vector2f pos = null;
		while (!motions.isEmpty()) {
			pos = motions.get(0).update(field, ticks);
			if (pos != null) break;
			motions.remove(0);
		}
		return pos;
	}
}
