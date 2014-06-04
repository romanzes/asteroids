package ru.footmade.asteroids.entity;

public class Ship extends MyPolygonSprite {
	private static final int COLOR = 0x0000FFFF;
	
	public static final float MAX_ANGULAR_VELOCITY = (float) (Math.PI);
	public static final float MIN_ANGULAR_VELOCITY = (float) (Math.PI / 6);
	
	private static final float SHIP_WIDTH = 0.1f;
	private static final float SHIP_HEIGHT = 0.15f;
	private static final float ACCELERATION = 0.2f;
	private static final float TOP_SPEED = 0.4f;
	
	public float speed;
	public float acceleration;
	public float topSpeed;
	
	public static final int STATE_ALIVE = 0;
	public static final int STATE_DESTROYED = 1;
	public int state = STATE_ALIVE;
	
	private static float[] getVertices(float scale) {
		float shipWidth = scale * SHIP_WIDTH;
		float shipHeight = scale * SHIP_HEIGHT;
		float[] vertices = { 0, -shipHeight / 4, -shipWidth / 2, -shipHeight / 2, 0, shipHeight / 2, shipWidth / 2, -shipHeight / 2 };
		return vertices;
	}
	
	public Ship(float scale) {
		super(COLOR, getVertices(scale));
		
		acceleration = scale * ACCELERATION;
		topSpeed = scale * TOP_SPEED;
	}
	
	public void update(float interval) {
		speed += acceleration * interval;
		if (speed > topSpeed)
			speed = topSpeed;
	}
}
