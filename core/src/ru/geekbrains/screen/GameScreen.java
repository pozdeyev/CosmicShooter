package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.audio.Music;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.utils.EnemiesCreator;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int QSTAR = 64;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Music gamemusic;
    private Sound soundBullet; //звук снаряда
    private Sound laserSound; //звук лазера


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

        //звезды
        starList = new Star[QSTAR];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new Star(atlas);
        }

        gamemusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gamemusic.mp3"));
        soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));


        //Воспроизводим музыку по кругу и запускаем воспроизведение
        gamemusic.setLooping(true);
        gamemusic.play();

        //Новый пул пуль
        bulletPool = new BulletPool();
        mainShip = new MainShip(atlas, bulletPool, laserSound);
        //Новый пул вражеских кораблей
        enemyShipPool = new EnemyShipPool(bulletPool, soundBullet, worldBounds, mainShip);

        //Инициализируем создателя вражеских кораблей
        enemiesCreator = new EnemiesCreator(atlas, enemyShipPool, worldBounds);
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
        checkCollisions();
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
        soundBullet.dispose();
        laserSound.dispose();
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

    private void checkCollisions() {

        //Записываем список вражеских объектов
        List<Enemy> enemyList = enemyShipPool.getActiveObjects();

        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }

            //определяем расстояние от корабля до врага
            float minimumDist = enemy.getHalfWidth() + mainShip.getHalfWidth();

            //Если расстояние между объектами меньше - уничтожаем объект
            if (enemy.pos.dst2(mainShip.pos) < minimumDist * minimumDist) {

                //уничтожаем врага
                enemy.destroy();
                //уничтожаем корабль, так как произошло столкновение
                System.out.println("Столкновение кораблей");
                mainShip.destroy();
                return;
            }
        }

        //Записываем список вражеских объектов
        List<Bullet> bulletList = bulletPool.getActiveObjects();


        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.BulletCollision(bullet)) {
                //уничтожаем пулю
                bullet.destroy();
                System.out.println("Попадание вражеской пули");
                //уничтожаем корабль
                mainShip.destroy();
            }
        }


        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }

            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.BulletCollision(bullet)) {
                    System.out.println("Попадание пули во врага");
                    enemy.destroy();
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        break;
                    }
                }
            }
        }

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

    if (!mainShip.isDestroyed()) {
        mainShip.draw(batch);
    } else {

        }

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
