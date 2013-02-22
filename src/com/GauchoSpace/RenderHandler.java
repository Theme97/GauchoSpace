package com.GauchoSpace;

import org.newdawn.slick.Graphics;

public interface RenderHandler {
	public void preRender(GameField field, Graphics graphics);
	public void postRender(GameField field, Graphics graphics);
}
