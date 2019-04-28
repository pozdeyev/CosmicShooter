package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Bullet;

public class Ship extends Sprite {

    protected Sound shootSound;
    protected Rect worldBounds;
    protected Vector2 speed;
    protected Vector2 speed0;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;


    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        this.speed = new Vector2();
        this.speed0 = new Vector2();
        this.bulletV = new Vector2();
    }

    public Ship() {
        this.speed = new Vector2();
        this.speed0 = new Vector2();
        this.bulletV = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(speed, delta);
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.sets(this, bulletRegion, pos, bulletV, bulletHeight,
                worldBounds, damage);
        shootSound.play();
    }

}
