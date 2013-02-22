package com.GauchoSpace.Motion;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.geom.Vector2f;

import com.GauchoSpace.GameField;
import com.GauchoSpace.IMotion;

public class Path implements IMotion {
	private List<IMotion> motions;
	
	public Path() {
		this.motions = new ArrayList<IMotion>();
	}
	
	public Path(IMotion... motions) {
		this.motions = new ArrayList<IMotion>();
		for (IMotion motion : motions) this.motions.add(motion); 
	}
	
	public Path(List<IMotion> motions) {
		this.motions = motions;
	}
	
	public void add(IMotion motion) {
		motions.add(motion);
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
