package com.indygame.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.indygame.Indygame;
import com.indygame.Objects.Point;

/**
 * Created by artem on 06.07.16.
 */
public class PlayState extends State {

    private Point point;
    private Texture background;
    private Texture platformHorizontalUp;
    private Texture platformHorizontalDown;
    private Texture platformVerticalRight;
    private Texture platformVerticalLeft;
    private Texture frame;
    private Texture finger;
    private boolean isGameOn;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        point = new Point(Indygame.width / 2 - 50, Indygame.height / 2 - 50, MathUtils.random(360));
        background = new Texture("bCol.png");
        platformHorizontalDown = new Texture("platformHD.png");
        platformHorizontalUp = new Texture("platformHU.png");
        platformVerticalLeft = new Texture("platformVL.png");
        platformVerticalRight = new Texture("platformVR.png");
        frame = new Texture("frame.png");
        finger = new Texture("finger.png");
        isGameOn = false;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
        {
            isGameOn = true;
            point.isGameOn = true;
        }
    }

    @Override
    public void update(float dt) {
        if (!isGameOn) {
            handleInput();
        }
        point.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Indygame.width, Indygame.height);

        if (!isGameOn) {
            sb.draw(finger, Indygame.width / 2 - finger.getWidth() / 2, Indygame.height / 2 - finger.getHeight() / 2);
        }
        else
        {
            sb.draw(point.getPoint(), point.getPosition().x, point.getPosition().y);
        }
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();;
    }
}
