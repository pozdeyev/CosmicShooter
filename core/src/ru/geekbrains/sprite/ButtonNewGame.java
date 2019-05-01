package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScalerTouchUpButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonNewGame extends ScalerTouchUpButton {


    private Game game;

    public ButtonNewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
        setHeightProporsion(0.08f);

    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setLeft(worldBounds.getLeft());

    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen(game));
    }

}
