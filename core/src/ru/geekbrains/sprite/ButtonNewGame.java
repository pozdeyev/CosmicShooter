package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScalerTouchUpButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonNewGame extends ScalerTouchUpButton {

private static final float HEIGHT=0.05f;
private static final float TOP=-0.012f;

    private GameScreen screen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen screen) {
        super(atlas.findRegion("button_new_game"));
        this.screen =screen;
        setHeightProporsion(HEIGHT);
        setTop(TOP);

    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setLeft(worldBounds.getLeft());

    }

    @Override
    protected void action() {
        screen.reset();
        //game.setScreen(new GameScreen(game));
    }

}
