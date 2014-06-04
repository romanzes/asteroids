package ru.footmade.asteroids.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextureUtils {
	private static final Map<Integer, Texture> monochromeCache = new HashMap<Integer, Texture>();
	
	public static Texture getMonochromeTexture(int rgba) {
		Texture existing = monochromeCache.get(rgba);
		if (existing == null) {
			Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			pixmap.setColor(rgba);
			pixmap.fill();
			Texture result = new Texture(pixmap);
			monochromeCache.put(rgba, result);
			return result;
		} else {
			return existing;
		}
	}
}
