package ru.footmade.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GLCleaner {
	public static void clearARGB(int color) {
		float alpha = (float)((color & 0xff000000) >> 24) / 0xff;
		float red = (float)((color & 0xff0000) >> 16) / 0xff;
		float green = (float)((color & 0xff00) >> 8) / 0xff;
		float blue = (float)(color & 0xff) / 0xff;
		Gdx.gl.glClearColor(red, green, blue, alpha);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
