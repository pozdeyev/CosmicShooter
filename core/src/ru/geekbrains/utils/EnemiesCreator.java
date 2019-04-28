package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.sprite.Enemy;

public class EnemiesCreator {

    private static final float ENEMY_SMALL_HEIGHT=0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT=0.01f;
    private static final float ENEMY_SMALL_BULLET_VY=-0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL=2f;
    private static final int  ENEMY_SMALL_HP= 1;


    private static final float ENEMY_MEDIUM_HEIGHT=0.1f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT=0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY=-0.25f;
    private static final int ENEMY_MEDIUM_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL=3f;
    private static final int  ENEMY_MEDIUM_HP= 5;


    private static final float ENEMY_BIG_HEIGHT=0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT=0.04f;
    private static final float ENEMY_BIG_BULLET_VY=-0.3f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL=3f;
    private static final int  ENEMY_BIG_HP= 10;



    private float generateEnemyShipInterval = 4f;
    private float generateEnemyShipTimer;

    private Rect worldBounds;
    private final TextureRegion[] enemySmallRegion;
    private final TextureRegion[] enemyMediumRegion;
    private final TextureRegion[] enemyBigRegion;
    private final TextureRegion bulletRegion;
    private final EnemyShipPool enemyPool;

    //скорость врага
    private final Vector2 enemySmallSpeed = new Vector2(0f, -0.2f);
    private final Vector2 enemyMediumSpeed = new Vector2(0f, -0.1f);
    private final Vector2 enemyBigSpeed = new Vector2(0f, -0.05f);


    //конструктор
    public EnemiesCreator(TextureAtlas atlas,EnemyShipPool enemyPool, Rect worldBounds) {
        TextureRegion enemy0=atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(enemy0,1,2,2);
        TextureRegion enemy1=atlas.findRegion("enemy1");
        this.enemyMediumRegion = Regions.split(enemy1,1,2,2);
        TextureRegion enemy2=atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(enemy2,1,2,2);

        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.enemyPool = enemyPool;
        this.worldBounds=worldBounds;
    }

    public void generateEnemies(float delta) {

        generateEnemyShipTimer += delta;
        if (generateEnemyShipTimer >= generateEnemyShipInterval) {
            generateEnemyShipTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();

            if (type < 0.5f) {

                enemy.set(enemySmallRegion,
                        enemySmallSpeed,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP);

            } else if (type < 0.8f) {
                enemy.set(enemyMediumRegion,
                        enemyMediumSpeed,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_DAMAGE,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HP);
            } else {
                enemy.set(enemyBigRegion,
                        enemyBigSpeed,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_DAMAGE,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP);
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
