package ru.footmade.asteroids.entity;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends Vector2 {
	private static final long serialVersionUID = 3855901023651557268L;
	
	public static final float LENGTH = 0.1f;
	public static final float WIDTH = 0.01f;
	private static final float SPEED = 1f;
	
	public Vector2 velocity;
	
	public Bullet(float scale, Vector2 position, float directionAngle) {
		super(position);
		float velocityMagnitude = scale * SPEED;
		velocity = new Vector2((float) Math.sin(directionAngle) * velocityMagnitude,
				(float) Math.cos(directionAngle) * velocityMagnitude);
	}
	
	public void update(float interval) {
		mulAdd(velocity, interval);
	}
}
