package ru.footmade.asteroids.entity;

import java.util.Random;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Asteroid extends Polygon {
	private static final float MAX_ANGULAR_VELOCITY = (float) Math.PI;
	
	// parameters are relative to viewport width
	public static final float RADIUS = 0.15f;
	private static final float MAX_VELOCITY = 0.4f;
	
	private static final int VERTEX_COUNT = 7;
	
	public float angularVelocity;
	public Vector2 velocity;
	
	private static float[] getVertices(int viewportWidth, int viewportHieght) {
		float angularStep = (float) (Math.PI * 2 / VERTEX_COUNT);
		float radius = viewportWidth * RADIUS;
		Random random = new Random();
		float[] vertices = new float[VERTEX_COUNT * 2];
		for (int i = 0; i < VERTEX_COUNT; i++) {
			float vertexAngle = angularStep * i;
			float vertexRadius = random.nextFloat() * radius / 2 + radius / 2;
			vertices[i * 2] = (float) (Math.cos(vertexAngle) * vertexRadius);
			vertices[i * 2 + 1] = (float) (Math.sin(vertexAngle) * vertexRadius);
		}
		return vertices;
	}
	
	public Asteroid(int viewportWidth, int viewportHeight, Vector2 position, float velocityAngle) {
		super(getVertices(viewportWidth, viewportHeight));
		setPosition(position.x, position.y);
		Random random = new Random();
		angularVelocity = random.nextFloat() * MAX_ANGULAR_VELOCITY * 2 - MAX_ANGULAR_VELOCITY;
		float velocityMagnitude = random.nextFloat() * viewportWidth * MAX_VELOCITY;
		velocity = new Vector2((float) Math.cos(velocityMagnitude) * velocityMagnitude,
				(float) Math.sin(velocityAngle) * velocityMagnitude);
	}
	
	public void update(float interval) {
		rotate(angularVelocity * interval * 180 / (float) Math.PI);
		setPosition(getX() + velocity.x * interval, getY() + velocity.y * interval);
	}
}
