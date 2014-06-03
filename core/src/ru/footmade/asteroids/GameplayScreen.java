package ru.footmade.asteroids;

import ru.footmade.asteroids.entity.Asteroid;
import ru.footmade.asteroids.entity.Bullet;
import ru.footmade.asteroids.entity.Ship;
import ru.footmade.asteroids.entity.Space;
import ru.footmade.asteroids.util.GLCleaner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class GameplayScreen extends ScreenAdapter {
	private int scrW, scrH;
	
	private static final int BACKGROUND_COLOR = 0xff000000;
	private static final Color SHIP_COLOR = Color.BLUE;
	private static final Color STAR_COLOR = Color.WHITE;
	private static final Color ASTEROID_COLOR = Color.ORANGE;
	private static final Color BULLET_COLOR = Color.RED;
	
	private ShapeRenderer renderer;
	
	private Space space;
	
	@Override
	public void show() {
		scrW = Gdx.graphics.getWidth();
		scrH = Gdx.graphics.getHeight();
		
		renderer = new ShapeRenderer();
		renderer.translate(scrW / 2, scrH / 2, 0);
		space = new Space(scrW, scrH);
	}
	
	@Override
	public void render(float delta) {
		processInput(delta);
		space.update(delta);
		
		GLCleaner.clearARGB(BACKGROUND_COLOR);
		renderer.begin(ShapeType.Point);
		renderSpace();
		renderer.end();
		renderer.begin(ShapeType.Line);
		renderAsteroids();
		renderShip();
		renderBullets();
		renderer.end();
	}
	
	private void renderSpace() {
		renderer.setColor(STAR_COLOR);
		for (Vector2 star : space.stars) {
			renderer.point(star.x, star.y, 0);
		}
	}
	
	private void renderAsteroids() {
		renderer.setColor(ASTEROID_COLOR);
		for (Asteroid asteroid: space.asteroids) {
			renderer.polygon(asteroid.getTransformedVertices());
		}
	}
	
	private void renderShip() {
		renderer.setColor(SHIP_COLOR);
		renderer.polygon(space.ship.getTransformedVertices());
	}
	
	private void renderBullets() {
		renderer.setColor(BULLET_COLOR);
		for (Bullet bullet : space.bullets) {
			float bulletLength = space.scale * Bullet.LENGTH;
			float bulletWidth = space.scale * Bullet.WIDTH;
			Vector2 bulletEnd = new Vector2(bullet).mulAdd(bullet.velocity, bulletLength / bullet.velocity.len());
			renderer.rectLine(bullet, bulletEnd, bulletWidth);
		}
	}
	
	private void processInput(float delta) {
		float angularVelocity = -Ship.MAX_ANGULAR_VELOCITY * Gdx.input.getAccelerometerX() / 10;
		if (Math.abs(angularVelocity) >= Ship.MIN_ANGULAR_VELOCITY)
			space.rotateShip(angularVelocity * delta);
		if (Gdx.input.justTouched()) {
			space.fire();
		}
	}
	
	@Override
	public void dispose() {
		renderer.dispose();
	}
}
