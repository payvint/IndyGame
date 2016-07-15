package com.indygame.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    //touch control
    Vector3 touchPos;
    float  cX, cY;

    BitmapFont score;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        point = new Point(Indygame.width / 2 - 35, (int) ((Indygame.PosMax + Indygame.PosMin) / 2 - 35), MathUtils.random(360));
        camera.setToOrtho(false, Indygame.width, Indygame.height);
        background = new Texture("bCol.png");
        platformDown = new Platform(Indygame.width / 2 - 80 / 2, Indygame.PosMin, new Texture("platformHD.png"), 90);
        platformUp = new Platform(Indygame.width / 2 - 80 / 2, Indygame.PosMax, new Texture("platformHU.png"), 270);
        platformLeft = new Platform(0, Indygame.width / 2 - 80 / 2 + Indygame.PosMin, new Texture("platformVL.png"), 0);
        platformRight = new Platform(Indygame.width - 20, Indygame.width / 2 - 80 / 2 + Indygame.PosMin, new Texture("platformVR.png"), 180);
        frame = new Texture("frame.png");
        finger = new Texture("finger.png");
        isGameOn = false;

        cX = platformDown.getPosition().x - platformUp.getPosition().x;
        cY = platformLeft.getPosition().y - platformRight.getPosition().y;
        touchPos = new Vector3();

        score = new BitmapFont();
    }

    private String toString(int quantityBouncing) {
        return ""+quantityBouncing+"";
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
        {
            isGameOn = true;
            point.isGameOn = true;
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            platformDown.savePos.set(platformDown.getPosition().x - touchPos.x, 0, 0);
            platformLeft.savePos.set(0, platformLeft.getPosition().y - touchPos.y, 0);
        }
    }

    protected void PlatformPos()
    {
        if(Gdx.input.isTouched())
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            platformDown.getPosition().x = touchPos.x + platformDown.savePos.x;
            //platformDown.getPosition().x = touchPos.x;
            platformUp.getPosition().x = platformDown.getPosition().x + cX;
            platformLeft.getPosition().y = touchPos.y + platformLeft.savePos.y;
            //platformLeft.getPosition().y = touchPos.y;
            platformRight.getPosition().y = platformLeft.getPosition().y + cY;


            if(platformRight.getPosition().y > Indygame.PosMax - 80/2)
                platformRight.getPosition().y = Indygame.PosMin + (platformRight.getPosition().y - Indygame.PosMax) + 80/2;
            else if(platformRight.getPosition().y < Indygame.PosMin + 80/2)
                platformRight.getPosition().y = Indygame.PosMax - (Indygame.PosMin - platformRight.getPosition().y) - 80/2;

            if(platformLeft.getPosition().y > Indygame.PosMax - 80/2)
                platformLeft.getPosition().y = Indygame.PosMin + (platformLeft.getPosition().y - Indygame.PosMax) + 80/2;
            else if(platformLeft.getPosition().y < Indygame.PosMin + 80/2)
                platformLeft.getPosition().y = Indygame.PosMax - (Indygame.PosMin - platformLeft.getPosition().y) - 80/2;

            if(platformUp.getPosition().x > Indygame.width - 80/2)
                platformUp.getPosition().x = platformUp.getPosition().x-Indygame.width;
            else if(platformUp.getPosition().x < 80/2)
                platformUp.getPosition().x = Indygame.width + platformUp.getPosition().x;

            if(platformDown.getPosition().x > Indygame.width - 80/2)
                platformDown.getPosition().x = platformDown.getPosition().x-Indygame.width;
            else if(platformDown.getPosition().x < 80/2)
                platformDown.getPosition().x = Indygame.width + platformDown.getPosition().x;

        }
        platformDown.rectangle.setPosition(platformDown.getPosition().x, platformDown.getPosition().y);
        platformUp.rectangle.setPosition(platformUp.getPosition().x, platformUp.getPosition().y);
        platformLeft.rectangle.setPosition(platformLeft.getPosition().x, platformLeft.getPosition().y);
        platformRight.rectangle.setPosition(platformRight.getPosition().x, platformRight.getPosition().y);

    }

    @Override
    public void update(float dt) {
        handleInput();
        PlatformPos();
        point.update(dt);
        if (point.getPosition().x < Indygame.width - 19 || point.getPosition().x > 19 || point.getPosition().y > Indygame.PosMin + 19 || point.getPosition().y < Indygame.PosMax + platformUp.getPlatform().getHeight() - 19) {
            if (point.collides(platformDown.getRectangle())) {
                point.angleMirrorRotation(platformDown.getAngle());
                point.quantityBouncing++;
            }
            if (point.collides(platformUp.getRectangle())) {
                point.angleMirrorRotation(platformUp.getAngle());
                point.quantityBouncing++;
            }
            if (point.collides(platformLeft.getRectangle())) {
                point.angleMirrorRotation(platformLeft.getAngle());
                point.quantityBouncing++;
            }
            if (point.collides(platformRight.getRectangle())) {
                point.angleMirrorRotation(platformRight.getAngle());
                point.quantityBouncing++;
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
        //PlatformPos();
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
