package ru.footmade.asteroids;

import ru.footmade.asteroids.entity.Space;
import ru.footmade.asteroids.util.TextureUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameOverScreen extends ScreenAdapter {
	private static final int FADE_COLOR = 0xFF00005D;
	
	private int scrW, scrH;
	private Space space;
	private GameplayRenderer renderer;
	private SpriteBatch batch;
	private Texture fade;
	private Texture refresh;
	private Stage stage;
	
	@Override
	public void show() {
		scrW = Gdx.graphics.getWidth();
		scrH = Gdx.graphics.getHeight();
		space = MyGdxGame.getSpace();
		renderer = new GameplayRenderer();
		batch = new SpriteBatch();
		fade = TextureUtils.getMonochromeTexture(FADE_COLOR);
		
		refresh = new Texture(Gdx.files.internal("refresh.png"));
		refresh.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stage = new Stage();
		Button refreshButton = new Button(new TextureRegionDrawable(new TextureRegion(refresh)));
		refreshButton.setSize(scrW / 3, scrW / 3);
		refreshButton.setPosition((scrW - refreshButton.getWidth()) / 2, refreshButton.getHeight() / 2);
		refreshButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.startGame();
			}
		});
		stage.addActor(refreshButton);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void render(float delta) {
		space.update(delta);
		stage.act();
		renderer.render(space);
		batch.begin();
		batch.draw(fade, 0, 0, scrW, scrH);
		batch.end();
		stage.draw();
	}
}
