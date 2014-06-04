package ru.footmade.asteroids;

import ru.footmade.asteroids.entity.Space;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MyGdxGame extends Game {
	private static Game self;
	private static Space space;
	
	public static Game getSelf() {
		return self;
	}
	
	public static Space getSpace() {
		if (space == null)
			space = new Space(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return space;
	}
	
	public static void startGame() {
		getSpace().reset();
		getSelf().setScreen(new GameplayScreen());
	}
	
	public static void gameOver() {
		getSelf().setScreen(new GameOverScreen());
	}
	
	@Override
	public void create () {
		self = this;
		startGame();
	}
}
