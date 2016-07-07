package com.indygame.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by artem on 06.07.16.
 */
public abstract class State {

    Vector3 mouse;
    protected GameStateManager gsm;

    public State(GameStateManager gsm)
    {
        this.gsm = gsm;
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

}
