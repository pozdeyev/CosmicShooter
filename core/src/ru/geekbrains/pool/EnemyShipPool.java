package ru.geekbrains.pool;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.Enemy;

public class EnemyShipPool extends SpritesPool<Enemy> {
    @Override
    protected Enemy newObject() {
        return new Enemy();
    }
}
