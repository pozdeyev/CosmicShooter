package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class Enemy extends Ship {

    private enum State {DESCENT, FIGHT};
    private State state;
    private Vector2 descentSpeed;

    private MainShip mainShip;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect wordBounds, MainShip mainShip){
        this.mainShip = mainShip;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = wordBounds;
        this.shootSound = shootSound;
        this.descentSpeed = new Vector2(0,-0.3f);
    }

    @Override
    public void update(float delta) {
    super.update(delta);

    if(getTop()<worldBounds.getTop()){
        state = State.FIGHT;
        speed.set(speed0);
    }

    if (state == State.FIGHT){
        reloadTimer +=delta;
        if (reloadTimer>=reloadInterval) {
            reloadTimer = 0f;
             shoot();
        }
    }

if (getBottom()<worldBounds.getBottom()){
    destroy();
    mainShip.damage(damage);
}


    }

    public void set(
            TextureRegion[] region,
            Vector2 speed0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = region;
        this.speed0.set(speed0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set (0,bulletVY);
        this.damage = bulletDamage;
        this.reloadInterval=reloadInterval;
        setHeightProporsion(height);
        this.hp = hp;
        speed.set(descentSpeed);
        reloadTimer=reloadInterval;
        state=State.DESCENT;

    }

    public boolean BulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y);
    }


}