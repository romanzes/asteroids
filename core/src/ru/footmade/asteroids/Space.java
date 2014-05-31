package ru.footmade.asteroids;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Space {
	private static final float STAR_INTERVAL = 0.1f;
	
	private int viewportWidth, viewportHeight;
	
	public Ship ship;
	public Vector2[] stars;
	
	public Space(int viewportWidth, int viewportHeight) {
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
		ship = new Ship(viewportWidth, viewportHeight);
		createStars();
	}
	
	private void createStars() {
		Random random = new Random();
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
	
	public void update(float interval) {
		ship.update(interval);
		float distance = ship.speed * interval;
		move(new Vector2(0, distance));
	}
	
	public void rotateShip(float dAngle) {
		for (Vector2 star : stars) {
			star.rotateRad(dAngle);
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
	}
}
