package ru.geekbrains.sprite;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyShipPool;
import ru.geekbrains.utils.Regions;

public class EnemiesCreator {

    private float generateEnemyShipInterval = 4f;
    private float generateEnemyShipTimer;

    private Rect worldBounds; //класс границы
    private Sound bulletSound; //звук

    //скорость врага
    private final Vector2 enemySpeed = new Vector2(0f, -0.08f);

    //текстура
    private final TextureRegion[] enemyRegion;
    private final EnemyShipPool enemyPool;

    //конструктор
    public EnemiesCreator(EnemyShipPool enemyPool, Rect worldBounds, TextureAtlas atlas, Sound bulletSound) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        this.bulletSound = bulletSound;

        TextureRegion textureRegion1 = atlas.findRegion("enemy1");
        this.enemyRegion = Regions.split(textureRegion1, 1, 2, 2);
    }


    public void generateEnemies(float delta) {

        generateEnemyShipTimer += delta;
        if (generateEnemyShipTimer >= generateEnemyShipInterval) {
            generateEnemyShipTimer = 0f;
            Enemy enemy = enemyPool.obtain();

            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getWidth(),
                    worldBounds.getRight() - enemy.getWidth());
            enemy.setBottom(worldBounds.getTop());
            enemy.sets(this, enemyRegion, enemySpeed, 0.1f, worldBounds, 3);
        }

    }

}
