package ru.footmade.asteroids;

import java.util.List;

import ru.footmade.asteroids.entity.Asteroid;
import ru.footmade.asteroids.entity.Bullet;
import ru.footmade.asteroids.entity.Ship;
import ru.footmade.asteroids.entity.Space;
import ru.footmade.asteroids.util.GLCleaner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class GameplayRenderer implements Disposable {
	private int scrW, scrH;
	
	private static final int BACKGROUND_COLOR = 0xff000000;
	private static final Color SHIP_COLOR = Color.BLUE;
	private static final Color STAR_COLOR = Color.WHITE;
	private static final Color ASTEROID_COLOR = Color.ORANGE;
	private static final Color BULLET_COLOR = Color.RED;
	
	private ShapeRenderer renderer;
	
	public GameplayRenderer() {
		scrW = Gdx.graphics.getWidth();
		scrH = Gdx.graphics.getHeight();
		
		renderer = new ShapeRenderer();
		renderer.translate(scrW / 2, scrH / 2, 0);
	}
	
	public void render(Space space) {
		GLCleaner.clearARGB(BACKGROUND_COLOR);
		renderer.begin(ShapeType.Point);
		renderSpace(space);
		renderer.end();
		renderer.begin(ShapeType.Line);
		renderAsteroids(space.asteroids);
		renderShip(space.ship);
		renderBullets(space.bullets, space.scale);
		renderer.end();
	}
	
	private void renderSpace(Space space) {
		renderer.setColor(STAR_COLOR);
		for (Vector2 star : space.stars) {
			renderer.point(star.x, star.y, 0);
		}
	}
	
	private void renderAsteroids(List<Asteroid> asteroids) {
		renderer.setColor(ASTEROID_COLOR);
		for (Asteroid asteroid: asteroids) {
			renderer.polygon(asteroid.getTransformedVertices());
		}
	}
	
	private void renderShip(Ship ship) {
		if (ship.state != Ship.STATE_ALIVE)
			return;
		renderer.setColor(SHIP_COLOR);
		renderer.polygon(ship.getTransformedVertices());
	}
	
	private void renderBullets(List<Bullet> bullets, float scale) {
		renderer.setColor(BULLET_COLOR);
		for (Bullet bullet : bullets) {
			float bulletLength = scale * Bullet.LENGTH;
			float bulletWidth = scale * Bullet.WIDTH;
			Vector2 bulletEnd = new Vector2(bullet).mulAdd(bullet.velocity, bulletLength / bullet.velocity.len());
			renderer.rectLine(bullet, bulletEnd, bulletWidth);
		}
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}
}
