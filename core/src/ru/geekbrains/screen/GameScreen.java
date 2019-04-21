package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.audio.Music;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.EnemiesCreator;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int QSTAR = 64;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Music gamemusic;
    private Sound soundBullet; //звук снаряда

    private Star starList[];
    private MainShip mainShip;
    private EnemiesCreator enemiesCreator;


    private EnemyShipPool enemyShipPool;
    private BulletPool bulletPool;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/nebulabg.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        starList = new Star[QSTAR];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();


        mainShip = new MainShip(atlas, bulletPool);

        this.gamemusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gamemusic.mp3"));
        this.soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));

        //Воспроизводим музыку по кругу и запускаем воспроизведение
        this.gamemusic.setLooping(true);
        this.gamemusic.play();

        //Новый пул вражеских кораблей
        this.enemyShipPool = new EnemyShipPool();
        //Инициализируем создателя вражеских кораблей
        this.enemiesCreator = new EnemiesCreator(enemyShipPool, worldBounds, atlas, soundBullet);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        mainShip.resize(worldBounds);

        for (Star star : starList) {
            star.resize(worldBounds);
        }


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        freeAllDestroyedActiveSprites();
        draw();
    }


    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        gamemusic.dispose();
    }


    private void update(float delta) {
        for (Star star : starList) {
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        enemiesCreator.generateEnemies(delta);
    }

    private void freeAllDestroyedActiveSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch); //батчер фона

        for (Star star : starList) {
            star.draw(batch);
        }

        mainShip.draw(batch); //батчер корабля
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }


    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }


}
