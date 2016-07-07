package com.indygame.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.indygame.Indygame;

/**
 * Created by artem on 06.07.16.
 */
public class MenuState extends State {

    private Texture background;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bCol.png");
        camera.setToOrtho(false, Indygame.width, Indygame.height);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, Indygame.width, Indygame.height);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
