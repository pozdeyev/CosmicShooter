package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ButtonExit;
import ru.geekbrains.sprite.ButtonPlay;
import ru.geekbrains.sprite.Ship;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int QSTAR = 128;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private TextureAtlas gameatlas;
    private Star starList[];
    private Ship mainship;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/nebulabg.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAddAtlas.tpack");
        gameatlas = new TextureAtlas("textures/mainAtlas.tpack");
        starList = new Star[QSTAR];
        for (int i=0; i<starList.length; i++){
            starList[i]=new Star(atlas);
        }

        mainship = new Ship(gameatlas);

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        mainship.resize (worldBounds);
        for (Star star : starList) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }


    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        gameatlas.dispose();

    }



    private void update(float delta) {
        for (Star star :starList) {
            star.update(delta);
        }
        mainship.update(delta);
    }

    private void draw() {
        batch.begin();
        background.draw(batch); //батчер фона
        mainship.draw(batch); //батчер корабля
        for (Star star :starList) {
            star.draw(batch);
        }


        batch.end();
    }
}
