package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CosmicShooter extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	//TextureRegion region;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("nebulabackground.jpg");
		//region = new TextureRegion(img, 50,50,150,150);
		//region.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
	}

	@Override
	public void render () {

		int width = Gdx.app.getGraphics().getWidth();
		int height = Gdx.app.getGraphics().getHeight();

		//Gdx.gl.glClearColor(0.25f,0.15f, 0.37f,  1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		batch.draw(background,0,0,width, height);

		//batch.setColor(0.666f,0.777f,0.13f,1f);
		//batch.draw(region,100,50,100,100);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
