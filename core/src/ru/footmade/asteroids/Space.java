package ru.footmade.asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Space {
	private static final float ACTIVE_SPACE_RELATIVE_RADIUS = 1.5f;
	private static final float STAR_INTERVAL = 0.1f;
	private static final int ASTEROID_COUNT = 8;
	
	private final Random random = new Random();
	
	private int viewportWidth, viewportHeight;
	private float screenRadius;
	private float activeSpaceRadius; // objects are destroyed outside this radius
	
	public Ship ship;
	public Vector2[] stars;
	public List<Asteroid> asteroids = new ArrayList<Asteroid>();
	
	public Space(int viewportWidth, int viewportHeight) {
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
		screenRadius = Vector2.len(viewportWidth, viewportHeight) / 2;
		activeSpaceRadius = screenRadius * ACTIVE_SPACE_RELATIVE_RADIUS;
		ship = new Ship(viewportWidth, viewportHeight);
		createStars();
		manageAsteroids();
	}
	
	private void createStars() {
		int cellCountW = (int) (1 / STAR_INTERVAL) + 1;
		int cellCountH = (int) (viewportHeight / viewportWidth / STAR_INTERVAL) + 1;
		float cellW = viewportWidth / cellCountW;
		float cellH = viewportHeight / cellCountH;
		stars = new Vector2[cellCountW * cellCountH];
		for (int i = 0; i < cellCountW; i++) {
			for (int j = 0; j < cellCountH; j++) {
				float relX = random.nextFloat() * cellW - viewportWidth / 2;
				float relY = random.nextFloat() * cellH - viewportHeight / 2;
				stars[j * cellCountW + i] = new Vector2(i * cellW + relX, j * cellH + relY);
			}
		}
	}
	
	private void manageAsteroids() {
		for (int i = 0; i < asteroids.size(); i++) {
			Asteroid asteroid = asteroids.get(i);
			if (Vector2.len(asteroid.getX(), asteroid.getY()) > activeSpaceRadius) {
				asteroids.remove(i--);
			}
		}
		while (asteroids.size() < ASTEROID_COUNT) {
			boolean satisfy = false;
			int tries = 0;
			while (!satisfy && tries < 10) {
				float radius = random.nextFloat() * (activeSpaceRadius - screenRadius) + screenRadius;
				float angle = random.nextFloat() * (float) Math.PI * 2;
				float x = (float) Math.cos(angle) * radius;
				float y = (float) Math.sin(angle) * radius;
				satisfy = true;
				for (Asteroid asteroid : asteroids) {
					if (Vector2.dst(x, y, asteroid.getX(), asteroid.getY()) < viewportWidth * Asteroid.RADIUS * 2) {
						satisfy = false;
						break;
					}
				}
				if (satisfy) {
					float velocityAngle = (float) Math.atan2(x, y);
					Asteroid asteroid = new Asteroid(viewportWidth, viewportHeight, new Vector2(x, y), velocityAngle);
					asteroids.add(asteroid);
				} else
					tries++;
			}
			if (tries >= 10)
				break;
		}
	}
	
	public void update(float interval) {
		ship.update(interval);
		for (Asteroid asteroid : asteroids) {
			asteroid.update(interval);
		}
		float distance = ship.speed * interval;
		move(new Vector2(0, distance));
		manageAsteroids();
	}
	
	public void rotateShip(float dAngle) {
		for (Vector2 star : stars) {
			star.rotateRad(dAngle);
		}
		for (Asteroid asteroid : asteroids) {
			Vector2 position = new Vector2(asteroid.getX(), asteroid.getY());
			position.rotateRad(dAngle);
			asteroid.setPosition(position.x, position.y);
			asteroid.rotate(dAngle * 180 / (float) Math.PI);
			asteroid.velocity.rotateRad(dAngle);
		}
	}
	
	private void move(Vector2 distance) {
		for (Vector2 star : stars) {
			star.sub(distance);
			if (star.x < -viewportWidth / 2)
				star.x += viewportWidth;
			else if (star.x > viewportWidth / 2)
				star.x -= viewportWidth;
			if (star.y < - viewportHeight / 2)
				star.y += viewportHeight;
			else if (star.y > viewportHeight / 2)
				star.y -= viewportHeight;
		}
		for (Asteroid asteroid : asteroids) {
			asteroid.setPosition(asteroid.getX() - distance.x, asteroid.getY() - distance.y);
		}
	}
}
