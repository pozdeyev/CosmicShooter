package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Bullet;

import ru.geekbrains.sprite.Explosion;



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

    protected float damageAnimateInterval = 0.1f;
    protected float damageAnimateTimer = damageAnimateInterval;
    protected ExplosionPool explosionPool;


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
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;
        }
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.sets(this, bulletRegion, pos, bulletV, bulletHeight,
                worldBounds, damage);
        shootSound.play();
    }

    public void damage(int damage) {
        frame = 1;
        damageAnimateTimer = 0f;
        hp -= damage;
        if (hp <= 0) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
        hp = 0;
    }


    public void destroyAid() {
        super.destroy();
        hp = 0;
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(this.getHeight(), this.pos);
    }

    public int getHp() {
        return hp;
    }

    public Vector2 getSpeed() {
        return speed;
    }


}
