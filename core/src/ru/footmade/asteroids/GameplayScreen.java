package ru.footmade.asteroids;

import ru.footmade.asteroids.entity.Ship;
import ru.footmade.asteroids.entity.Space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class GameplayScreen extends ScreenAdapter {
	private Space space;
	private GameplayRenderer renderer;
	
	@Override
	public void show() {
		space = MyGdxGame.getSpace();
		renderer = new GameplayRenderer();
	}
	
	@Override
	public void render(float delta) {
		processInput(delta);
		space.update(delta);
		renderer.render(space);
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
