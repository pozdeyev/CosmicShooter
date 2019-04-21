package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.base.ScalerTouchUpButton;
import ru.geekbrains.math.Rect;

public class ButtonExit extends ScalerTouchUpButton {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("Btclose"));
        setHeightProporsion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.02f);
        setRight(worldBounds.getRight() - 0.02f);
    }

    @Override
    protected void action() {
        Gdx.app.exit();
    }

}
