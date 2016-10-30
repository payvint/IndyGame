package com.indygame.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.indygame.Indygame;
import com.indygame.Objects.Platform;
import com.indygame.Objects.Point;

//import javafx.scene.text.Text;

public class PlayState extends State {

    private Point point;
    private Texture background;
    private Platform platformUp;
    private Platform platformDown;
    private Platform platformRight;
    private Platform platformLeft;
    private Texture frame;
    //private Texture finger;
    private Texture finalB;
    private Texture menu;
    private Texture fScreen;
    private boolean isGameOn;
    private boolean getTouch;
    private boolean isGameOver = false;
    private int angle;
    private int highscore;
    private float  cX, cY;

    BitmapFont score;
    BitmapFont hScore;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        angle = MathUtils.random(360);
        while((angle > 80 && angle < 100) || (angle > 170 && angle < 190) || (angle > 260 && angle < 280) || angle > 350 || angle < 10)
            angle = MathUtils.random(360);
        point = new Point(Indygame.width / 2 - 35, (int) ((Indygame.PosMax + Indygame.PosMin) / 2 - 35), angle);
        camera.setToOrtho(false, Indygame.width, Indygame.height);
        background = new Texture("bCol.png");
        menu = new Texture("Menu.png");

        platformDown = new Platform(MathUtils.random(Indygame.width - 80), Indygame.PosMin, new Texture("platformHD.png"), 90);
        platformUp = new Platform(MathUtils.random(Indygame.width - 80), Indygame.PosMax - 20, new Texture("platformHU.png"), 270);
        platformLeft = new Platform(0, MathUtils.random(Indygame.width - 80) + Indygame.PosMin, new Texture("platformVL.png"), 0);
        platformRight = new Platform(Indygame.width - 10 - 20 / 2, MathUtils.random(Indygame.width - 80) + Indygame.PosMin, new Texture("platformVR.png"), 180);

        frame = new Texture("frame.png");
        //finger = new Texture("finger.png");
        fScreen = new Texture("finalScreen.png");
        finalB = new Texture("finalB.png");
        isGameOn = false;

        cX = platformDown.getPosition().x - platformUp.getPosition().x;
        cY = platformLeft.getPosition().y - platformRight.getPosition().y;

        score = new BitmapFont();
        hScore = new BitmapFont();
        highscore = Indygame.prefs.getInteger("highscore");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() && isGameOver == true)
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x > Indygame.width + 40 && touchPos.x < Indygame.width/2 - 10 && touchPos.y > Indygame.height/2 - 140 && touchPos.y < Indygame.height/2 + 140)
            {
                isGameOver = false;
                gsm.set(new PlayState(gsm));
                isGameOn = true;
                point.isGameOn = true;
            }
        }
        if (Gdx.input.justTouched() && isGameOver == false)
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x >= Indygame.width - 110 && touchPos.x <= Indygame.width - 10 && touchPos.y >= 10 && touchPos.y <= 110 && !isGameOn)
            {
                gsm.set(new MenuState(gsm));
            }
            isGameOn = true;
            point.isGameOn = true;
        }
        if(Gdx.input.justTouched() && isGameOver == true)
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x > Indygame.width/2 + 10 && touchPos.x < Indygame.width - 40 && touchPos.y > Indygame.height/2 - 140 && touchPos.y < Indygame.height/2 + 140)
            {
                gsm.set(new MenuState(gsm));
            }
        }

    }

    protected void PlatformPos()
    {
        if(Gdx.input.isTouched() && isGameOn)
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            platformLeft.update(touchPos, getTouch);
            platformDown.update(touchPos, getTouch);
            platformRight.update(touchPos, getTouch);
            platformUp.update(touchPos, getTouch);
            getTouch = true;
        }
        else
            getTouch = false;
    }

    @Override
    public void update(float dt) {
        handleInput();
        point.update(dt);
        PlatformPos();
        //Scroll();
        platformUp.collides(point);
        platformDown.collides(point);
        platformRight.collides(point);
        platformLeft.collides(point);

        if (point.getPosition().x < 15 || point.getPosition().x > Indygame.width - point.getPoint().getWidth() - 15 || point.getPosition().y < Indygame.PosMin + 15 || point.getPosition().y > Indygame.PosMax - point.getPoint().getHeight() - 15)
        {
            isGameOn = false;
            point.isGameOn = false;
            isGameOver = true;
            //gsm.set(new PlayState(gsm));
        }
        if (highscore < point.quantityBouncing)
        {
            highscore = point.quantityBouncing;
            Indygame.prefs.putInteger("highscore", highscore);
            Indygame.prefs.flush();
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, Indygame.width, Indygame.height);
        sb.draw(frame, 0,Indygame.PosMin);

        sb.draw(platformDown.getPlatform(), platformDown.getPosition().x, platformDown.getPosition().y);
        sb.draw(platformUp.getPlatform(), platformUp.getPosition().x, platformUp.getPosition().y);
        sb.draw(platformLeft.getPlatform(), platformLeft.getPosition().x, platformLeft.getPosition().y);
        sb.draw(platformRight.getPlatform(), platformRight.getPosition().x, platformRight.getPosition().y);

        sb.draw(platformDown.getPlatform(), platformDown.getPosition2().x, platformDown.getPosition2().y);
        sb.draw(platformUp.getPlatform(), platformUp.getPosition2().x, platformUp.getPosition2().y);
        sb.draw(platformLeft.getPlatform(), platformLeft.getPosition2().x, platformLeft.getPosition2().y);
        sb.draw(platformRight.getPlatform(), platformRight.getPosition2().x, platformRight.getPosition2().y);

        sb.draw(background, 0, Indygame.PosMax, Indygame.width, Indygame.height);
        sb.draw(background, 0, Indygame.PosMax - Indygame.height - Indygame.width, Indygame.width, Indygame.height);

        hScore.draw(sb, "Highscore: " + highscore, Indygame.width - 20 - 90, Indygame.height - 50);
        score.draw(sb,"Score: " + point.quantityBouncing, 20, Indygame.height - 50);

        if (!isGameOn)
        {
            if(isGameOver)
            {
                sb.draw(point.getPoint(), point.getPosition().x, point.getPosition().y);
                sb.draw(finalB,0, 0, Indygame.width, Indygame.height);
                sb.draw(fScreen, 40, 260);
                score.draw(sb, "Score: " + point.quantityBouncing, Indygame.width / 2 - 40, Indygame.height / 2 + 150);
            }
            else
            {
                sb.draw(menu,   Indygame.width - 110, 10);
            }
            if(Gdx.input.justTouched() && isGameOver == true)
            {
                isGameOver = false;
                gsm.set(new PlayState(gsm));
            }
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
