package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.sprite.Enemy;


public class EnemiesCreators {

    //Параметры врагов
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

    //Параметры аптечек
    private static final float FIRSTAID_SMALL_HEIGHT = 0.05f;
    private static final int FIRSTAID_SMALL_AIDCAPACITY = 50;
    private static final int FIRSTAID_SMALL_HP = 50;

    private static final float FIRSTAID_BIG_HEIGHT = 0.1f;
    private static final int FIRSTAID_BIG_AIDCAPACITY = 10;
    private static final int FIRSTAID_BIG_HP = 50;


    private float generateEnemyShipInterval = 4f;
    private float generateEnemyShipTimer;

    private float generateFirstAidInterval = 10f;
    private float generateFirstAidTimer;


    private Rect worldBounds;
    private final TextureRegion[] enemySmallRegion;
    private final TextureRegion[] enemyMediumRegion;
    private final TextureRegion[] enemyBigRegion;
    private final TextureRegion[] aidRegion;

    private final TextureRegion bulletRegion;


    private final EnemyShipPool enemyPool;

    private int stage =1;

    //скорость врага
    private float enemySmallSpeed = -0.2f;
    private float enemyMediumSpeed = -0.1f;
    private float enemyBigSpeed = -0.05f;

    //скорость аптечки
    private final Vector2 aidSmallSpeed = new Vector2(0f, -0.4f);
    private final Vector2 aidBigSpeed = new Vector2(0f, -0.1f);

    //конструктор
    public EnemiesCreators(TextureAtlas atlas,TextureAtlas atlasAid, EnemyShipPool enemyPool, Rect worldBounds) {
        TextureRegion enemy0=atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(enemy0,1,2,2);
        TextureRegion enemy1=atlas.findRegion("enemy1");
        this.enemyMediumRegion = Regions.split(enemy1,1,2,2);
        TextureRegion enemy2=atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(enemy2,1,2,2);

        TextureRegion aid=atlasAid.findRegion("aid");
        this.aidRegion = Regions.split(aid,1,2,2);

        this.bulletRegion = atlas.findRegion("bulletEnemy");

        this.enemyPool = enemyPool;
        this.worldBounds=worldBounds;
    }

    public void generateEnemies(float delta, int frags) {
        stage = frags/10 +1 ;
        generateEnemyShipTimer += delta;
        generateFirstAidTimer += delta;

        if (generateEnemyShipTimer >= generateEnemyShipInterval) {
            generateEnemyShipTimer = 0f;

            Enemy enemy = enemyPool.obtain();

            float type = (float) Math.random();

            if (type < 0.5f) {

                enemy.set(enemySmallRegion,
                        enemySmallSpeed*0.5f*stage,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY*0.5f*stage,
                        ENEMY_SMALL_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP);

            } else if (type < 0.8f) {
                enemy.set(enemyMediumRegion,
                        enemyMediumSpeed*0.5f*stage,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_DAMAGE*stage,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HP);
            } else {
                enemy.set(enemyBigRegion,
                        enemyBigSpeed,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY*stage,
                        (int)Math.round(ENEMY_BIG_DAMAGE*stage*0.5f),
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP);
            }



            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());

        }


        if (generateFirstAidTimer >= generateFirstAidInterval) {
            generateFirstAidTimer = 0f;

            Enemy enemy = enemyPool.obtain();;

            float type = (float) Math.random();

            if (type < 0.5f) {

            enemy.setAid (aidRegion,
                    aidSmallSpeed,
                    FIRSTAID_SMALL_HEIGHT,
                    FIRSTAID_SMALL_HP,
                    FIRSTAID_SMALL_AIDCAPACITY
            );
              } else if (type < 0.8f) {
                enemy.setAid (aidRegion,
                            aidBigSpeed,
                          FIRSTAID_BIG_HEIGHT,
                          FIRSTAID_BIG_HP,
                          FIRSTAID_BIG_AIDCAPACITY
                 );
              }

              enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(),
                      worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());

        }

    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}
