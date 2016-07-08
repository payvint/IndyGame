package com.indygame.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.indygame.Indygame;
import com.indygame.Objects.Platform;
import com.indygame.Objects.Point;

/**
 * Created by artem on 06.07.16.
 */
public class PlayState extends State {

    private Point point;
    private Texture background;
    private Platform platformHorizontalUp;
    private Platform platformHorizontalDown;
    private Platform platformVerticalRight;
    private Platform platformVerticalLeft;
    private Texture frame;
    private Texture finger;
    private boolean isGameOn;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        point = new Point(Indygame.width / 2 - 50, Indygame.height / 2 - 50, MathUtils.random(360));
        camera.setToOrtho(false, Indygame.width, Indygame.height);
        background = new Texture("bCol.png");
        platformHorizontalDown = new Platform(MathUtils.random(Indygame.width - 80 / 2), Indygame.PosMin, new Texture("platformHD.png"), 90);
        platformHorizontalUp = new Platform(MathUtils.random(Indygame.width - 80 / 2), Indygame.PosMax, new Texture("platformHU.png"), 270);
        platformVerticalLeft = new Platform(0, MathUtils.random(Indygame.width - 80 / 2) + Indygame.PosMin, new Texture("platformVL.png"), 0);
        platformVerticalRight = new Platform(Indygame.width - 10 - 20 / 2, MathUtils.random(Indygame.width - 80 / 2) + Indygame.PosMin, new Texture("platformVR.png"), 180);
        frame = new Texture("frame.png");
        finger = new Texture("finger.png");
        isGameOn = false;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched())
        {
            isGameOn = true;
            point.isGameOn = true;
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        point.update(dt);
        if (point.collides(platformHorizontalDown.getRectangle()))
        {
            point.angleMirrorRotation(platformHorizontalDown.getAngle());
            point.quantityBouncing++;
        }
        if (point.collides(platformHorizontalUp.getRectangle()))
        {
            point.angleMirrorRotation(platformHorizontalUp.getAngle());
            point.quantityBouncing++;
        }
        if (point.collides(platformVerticalLeft.getRectangle()))
        {
            point.angleMirrorRotation(platformVerticalLeft.getAngle());
            point.quantityBouncing++;
        }
        if (point.collides(platformVerticalRight.getRectangle()))
        {
            point.angleMirrorRotation(platformVerticalRight.getAngle());
            point.quantityBouncing++;
        }
        if (point.getPosition().x < 0 || point.getPosition().x > Indygame.width - point.getPoint().getWidth() || point.getPosition().y < Indygame.PosMin || point.getPosition().y > Indygame.PosMax)
        {
            gsm.set(new PlayState(gsm));
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, Indygame.width, Indygame.height);
        sb.draw(platformHorizontalDown.getPlatform(), platformHorizontalDown.getPosition().x, platformHorizontalDown.getPosition().y);
        sb.draw(platformHorizontalUp.getPlatform(), platformHorizontalUp.getPosition().x, platformHorizontalUp.getPosition().y);
        sb.draw(platformVerticalLeft.getPlatform(), platformVerticalLeft.getPosition().x, platformVerticalLeft.getPosition().y);
        sb.draw(platformVerticalRight.getPlatform(), platformVerticalRight.getPosition().x, platformVerticalRight.getPosition().y);

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
