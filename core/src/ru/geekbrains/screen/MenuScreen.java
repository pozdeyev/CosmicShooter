package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ButtonExit;
import ru.geekbrains.sprite.ButtonPlay;
import ru.geekbrains.sprite.Star;

public class MenuScreen extends BaseScreen {

    private Game game;

    private static final int QSTAR = 128;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Star starList[];
    private Music menumusic;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/nebulabg.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAddAtlas.tpack");
        starList = new Star[QSTAR];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new Star(atlas);
        }

        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);

        this.menumusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menumusic.mp3"));

        //Воспроизводим музыку по кругу и запускаем воспроизведение
        this.menumusic.setLooping(true);
        this.menumusic.play();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starList) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (Star star : starList) {
            star.update(delta);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        menumusic.dispose();
    }

    private void draw() {
        batch.begin();
        background.draw(batch); //батчер фона
        for (Star star : starList) {
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonPlay.touchUp(touch, pointer);
        return false;
    }

}
