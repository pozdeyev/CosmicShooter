package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final int HP = 10;
    private static final int INVALID_POINTER = -1;

    private boolean pressedRight;
    private boolean pressedLeft;
    private int rightPointer = INVALID_POINTER;
    private int leftPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool =explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.shootSound = shootSound;
        this.reloadInterval = 0.2f;
        this.bulletV.set(0f, 0.5f);
        this.bulletHeight = 0.015f;
        this.damage = 1;
        this.hp=HP;
        this.speed0.set(0.5f, 0);
        setHeightProporsion(0.15f);

    }

    public void reset(){
        flushDestroyed();
        hp=HP;
        pos.x=worldBounds.pos.x;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.04f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        reloadTimer += delta;

        //Стреляем до тех пок пока корабль жив
        if ((reloadTimer >= reloadInterval) && !this.isDestroyed()) {
            reloadTimer = 0f;
            shoot();
        }

        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {

        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();

        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }

        }
        return false;
    }


    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else stop();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:

                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else stop();
                break;
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
        return false;
    }


    private void moveRight() {
        speed.set(speed0);
    }

    private void moveLeft() {
        speed.set(speed0).rotate(180);
    }

    private void stop() {
        speed.setZero();
    }


    //метод определяющий пересечение пули с кораблем
    public boolean BulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom());
    }

}
