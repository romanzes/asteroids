package ru.footmade.asteroids.entity;

import ru.footmade.asteroids.util.TextureUtils;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;

public class MyPolygonSprite extends PolygonSprite {
	private static PolygonRegion getPolygon(int rgba, float[] vertices) {
		short[] triangles = new EarClippingTriangulator().computeTriangles(vertices).items;
		return new PolygonRegion(new TextureRegion(TextureUtils.getMonochromeTexture(rgba)), vertices, triangles);
	}
	
	public MyPolygonSprite(int rgba, float[] vertices) {
		super(getPolygon(rgba, vertices));
		verticesAlloc = new float[vertices.length];
	}
	
	private final float[] verticesAlloc;
	
	public float[] getOnlyVertices() {
		float[] vertices = getVertices();
		int verticeCount = vertices.length / 5;
		for (int i = 0; i < verticeCount; i++) {
			verticesAlloc[i * 2] = vertices[i * 5];
			verticesAlloc[i * 2 + 1] = vertices[i * 5 + 1];
		}
		return verticesAlloc;
	}
	
	/** Returns whether an x, y pair is contained within the polygon. */
	public boolean contains (float x, float y) {
		final float[] vertices = getOnlyVertices();
		final int numFloats = vertices.length;
		int intersects = 0;

		for (int i = 0; i < numFloats; i += 2) {
			float x1 = vertices[i];
			float y1 = vertices[i + 1];
			float x2 = vertices[(i + 2) % numFloats];
			float y2 = vertices[(i + 3) % numFloats];
			if (((y1 <= y && y < y2) || (y2 <= y && y < y1)) && x < ((x2 - x1) / (y2 - y1) * (y - y1) + x1)) intersects++;
		}
		return (intersects & 1) == 1;
	}
}
