package ru.footmade.asteroids.entity;

import java.util.Random;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Asteroid extends Polygon {
	private static final float MAX_ANGULAR_VELOCITY = (float) Math.PI;
	
	public static final float RADIUS = 0.15f;
	private static final float MAX_VELOCITY = 0.4f;
	private static final float EXPLOSION_VELOCITY = 0.1f;
	
	private static final int VERTEX_COUNT = 7;
	
	private float scale;
	
	public float angularVelocity;
	public Vector2 velocity;
	
	public static final int SIZE_SMALL = 1;
	public static final int SIZE_LARGE = 2;
	
	public int size;
	
	private static float[] getVertices(float scale) {
		float angularStep = (float) (Math.PI * 2 / VERTEX_COUNT);
		float radius = scale * RADIUS;
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
	
	private Asteroid() {
		super();
		velocity = new Vector2();
		size = SIZE_SMALL;
	}
	
	public Asteroid(float scale, Vector2 position, float velocityAngle) {
		super(getVertices(scale));
		this.scale = scale;
		setPosition(position.x, position.y);
		Random random = new Random();
		angularVelocity = random.nextFloat() * MAX_ANGULAR_VELOCITY * 2 - MAX_ANGULAR_VELOCITY;
		float velocityMagnitude = random.nextFloat() * scale * MAX_VELOCITY;
		velocity = new Vector2((float) Math.cos(velocityMagnitude) * velocityMagnitude,
				(float) Math.sin(velocityAngle) * velocityMagnitude);
		size = SIZE_LARGE;
	}
	
	public void update(float interval) {
		rotate(angularVelocity * interval * 180 / (float) Math.PI);
		setPosition(getX() + velocity.x * interval, getY() + velocity.y * interval);
	}
	
	public Asteroid[] split() {
		float[] vertices = getTransformedVertices();
		Asteroid[] result = new Asteroid[VERTEX_COUNT];
		for (int i = VERTEX_COUNT - 1, j = 0; j < VERTEX_COUNT; i = j++) {
			float[] newVertices = { 0, 0, vertices[i * 2] - getX(), vertices[i * 2 + 1] - getY(),
					vertices[j * 2] - getX(), vertices[j * 2 + 1] - getY() };
			result[j] = new Asteroid();
			result[j].setVertices(newVertices);
			result[j].setOrigin(0, 0);
			result[j].setPosition(getX(), getY());
			Vector2 newVelocity = new Vector2(velocity)
					.add(new Vector2(newVertices[2], newVertices[3]).nor().scl(scale * EXPLOSION_VELOCITY));
			result[j].velocity = newVelocity;
		}
		return result;
	}
}
