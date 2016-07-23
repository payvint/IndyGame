package com.indygame.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.indygame.Indygame;
import com.indygame.Objects.Platform;
import com.indygame.Objects.Point;

import javafx.scene.text.Text;

/**
 * Created by artem on 06.07.16.
 */
public class PlayState extends State {

    private Point point;
    private Texture background;
    private Platform platformUp;
    private Platform platformDown;
    private Platform platformRight;
    private Platform platformLeft;
    private Texture frame;
    private Texture finger;
    private boolean isGameOn;
    private boolean getTouch;
    private int angle;
    //touch control
    //Vector3 touchPos;
    float  cX, cY;

    BitmapFont score;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        angle = MathUtils.random(360);
        while((angle > 80 && angle < 100) || (angle > 170 && angle < 190) || (angle > 260 && angle < 280) || (angle > 350 && angle < 10))
            angle = MathUtils.random(360);
        point = new Point(Indygame.width / 2 - 35, (int) ((Indygame.PosMax + Indygame.PosMin) / 2 - 35), angle);
        camera.setToOrtho(false, Indygame.width, Indygame.height);
        background = new Texture("bCol.png");

        platformDown = new Platform(MathUtils.random(Indygame.width - 80 / 2), Indygame.PosMin, new Texture("platformHD.png"), 90);
        platformUp = new Platform(MathUtils.random(Indygame.width - 80 / 2), Indygame.PosMax , new Texture("platformHU.png"), 270);
        platformLeft = new Platform(0, MathUtils.random(Indygame.width - 80 / 2) + Indygame.PosMin, new Texture("platformVL.png"), 0);
        platformRight = new Platform(Indygame.width - 10 - 20 / 2, MathUtils.random(Indygame.width - 80 / 2) + Indygame.PosMin, new Texture("platformVR.png"), 180);

        frame = new Texture("frame.png");
        finger = new Texture("finger.png");
        isGameOn = false;

        cX = platformDown.getPosition().x - platformUp.getPosition().x;
        cY = platformLeft.getPosition().y - platformRight.getPosition().y;
        //touchPos = new Vector3();

        score = new BitmapFont();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
        {
            isGameOn = true;
            point.isGameOn = true;
        }
    }

    protected void PlatformPos()
    {
        if(Gdx.input.isTouched() && isGameOn)
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (!getTouch)
            {
                platformDown.savePos.set(platformDown.getPosition().x - touchPos.x, 0);
                platformLeft.savePos.set(0, platformLeft.getPosition().y - touchPos.y);
                getTouch = true;
            }
        }
        else
            getTouch = false;
    }

    protected void Scroll()
    {
        platformDown.getPosition().x = touchPos.x + platformDown.savePos.x;
        platformUp.getPosition().x = platformDown.getPosition().x + cX;
        platformLeft.getPosition().y = touchPos.y + platformLeft.savePos.y;
        platformRight.getPosition().y = platformLeft.getPosition().y + cY;

        if(platformRight.getPosition().y > Indygame.PosMax - 60)
            platformRight.getPosition().y = Indygame.PosMin + (platformRight.getPosition().y - Indygame.PosMax + 60);
        else if(platformRight.getPosition().y < Indygame.PosMin)
            platformRight.getPosition().y = Indygame.PosMax - (Indygame.PosMin - platformRight.getPosition().y) - 60;

        if(platformLeft.getPosition().y > Indygame.PosMax - 60)
            platformLeft.getPosition().y = Indygame.PosMin + (platformLeft.getPosition().y - Indygame.PosMax + 60);
        else if(platformLeft.getPosition().y < Indygame.PosMin)
            platformLeft.getPosition().y = Indygame.PosMax - (Indygame.PosMin - platformLeft.getPosition().y) - 60;

        if(platformUp.getPosition().x > Indygame.width - 80)
            platformUp.getPosition().x = platformUp.getPosition().x - Indygame.width + 80;
        else if(platformUp.getPosition().x < 0)
            platformUp.getPosition().x = Indygame.width + platformUp.getPosition().x - 80;

        if(platformDown.getPosition().x > Indygame.width - 80)
            platformDown.getPosition().x = platformDown.getPosition().x - Indygame.width + 80;
        else if(platformDown.getPosition().x < 0)
            platformDown.getPosition().x = Indygame.width + platformDown.getPosition().x - 80;

        platformDown.rectangle.setPosition(platformDown.getPosition().x, platformDown.getPosition().y);
        platformUp.rectangle.setPosition(platformUp.getPosition().x, platformUp.getPosition().y);
        platformLeft.rectangle.setPosition(platformLeft.getPosition().x, platformLeft.getPosition().y);
        platformRight.rectangle.setPosition(platformRight.getPosition().x, platformRight.getPosition().y);
    }

    @Override
    public void update(float dt) {
        handleInput();
        PlatformPos();
        Scroll();
        point.update(dt);
            if (point.getPosition().x <= 21)
            {
                point.setCentralPosition();
                platformLeft.setCentralPosition();
                if (point.collides(platformLeft) && point.lastCollibe != 3) {
                    point.angleMirrorRotation(platformLeft.getAngle());
                    point.quantityBouncing++;
                    point.lastCollibe = 3;
                }
            }
            if (point.getPosition().x >= Indygame.width - 21 - point.getPoint().getWidth())
            {
                point.setCentralPosition();
                platformRight.setCentralPosition();
                if (point.collides(platformRight) && point.lastCollibe != 1) {
                    point.angleMirrorRotation(platformRight.getAngle());
                    point.quantityBouncing++;
                    point.lastCollibe = 1;
                }
            }
            if (point.getPosition().y <=  Indygame.PosMin + 21)
            {
                point.setCentralPosition();
                platformDown.setCentralPosition();
                if (point.collides(platformDown) && point.lastCollibe != 2) {
                    point.angleMirrorRotation(platformDown.getAngle());
                    point.quantityBouncing++;
                    point.lastCollibe = 2;
                }
            }
            if (point.getPosition().y >= Indygame.PosMax + platformUp.getPlatform().getHeight() - 21 - point.getPoint().getHeight())
            {
                point.setCentralPosition();
                platformUp.setCentralPosition();
                if (point.collides(platformUp) && point.lastCollibe != 4)
                {
                    point.angleMirrorRotation(platformUp.getAngle());
                    point.quantityBouncing++;
                    point.lastCollibe = 4;
                }
            }
        if (point.getPosition().x < 15 || point.getPosition().x > Indygame.width - point.getPoint().getWidth() - 15 || point.getPosition().y < Indygame.PosMin + 15 || point.getPosition().y > Indygame.PosMax + platformUp.getPlatform().getHeight() - point.getPoint().getHeight() - 15)
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
        score.draw(sb,"Score: " + point.quantityBouncing, 20, Indygame.height - 50);
        sb.draw(platformDown.getPlatform(), platformDown.getPosition().x, platformDown.getPosition().y);
        sb.draw(platformUp.getPlatform(), platformUp.getPosition().x, platformUp.getPosition().y);
        sb.draw(platformLeft.getPlatform(), platformLeft.getPosition().x, platformLeft.getPosition().y);
        sb.draw(platformRight.getPlatform(), platformRight.getPosition().x, platformRight.getPosition().y);

        if (!isGameOn) {
            sb.draw(finger, Indygame.width / 2, Indygame.PosMin / 2);
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
