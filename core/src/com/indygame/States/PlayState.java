package com.indygame.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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
    private Texture finger;
    private Texture finalB;
    private Texture menu;
    public Texture fScreen;
    private boolean isGameOn;
    private boolean getTouch;
    private boolean isGameOver = false;
    private int angle;
    private int highscore;
    float  cX, cY;

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

        platformDown = new Platform(MathUtils.random(Indygame.width - 80 / 2), Indygame.PosMin + 5, new Texture("platformHD.png"), 90);
        platformUp = new Platform(MathUtils.random(Indygame.width - 80 / 2), Indygame.PosMax - 20 - 5, new Texture("platformHU.png"), 270);
        platformLeft = new Platform(5, MathUtils.random(Indygame.width - 80 / 2) + Indygame.PosMin, new Texture("platformVL.png"), 0);
        platformRight = new Platform(Indygame.width - 10 - 5 - 20 / 2, MathUtils.random(Indygame.width - 80 / 2) + Indygame.PosMin, new Texture("platformVR.png"), 180);

        frame = new Texture("frame.png");
        finger = new Texture("finger.png");
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
            if (touchPos.x >= Indygame.width - 110 && touchPos.x <= Indygame.width - 10 && touchPos.y >= 10 && touchPos.y <= 110)
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

    protected void Scroll() {
        platformDown.getPosition().x = touchPos.x + platformDown.savePos.x;
        platformUp.getPosition().x = platformDown.getPosition().x + cX;
        platformLeft.getPosition().y = touchPos.y + platformLeft.savePos.y;
        platformRight.getPosition().y = platformLeft.getPosition().y + cY;

        if(platformRight.getPosition().y > Indygame.PosMax - 80)
            platformRight.getPosition().y = Indygame.PosMin + (platformRight.getPosition().y - Indygame.PosMax + 80);
        else if(platformRight.getPosition().y < Indygame.PosMin)
            platformRight.getPosition().y = Indygame.PosMax - (Indygame.PosMin - platformRight.getPosition().y) - 80;

        if(platformLeft.getPosition().y > Indygame.PosMax - 80)
            platformLeft.getPosition().y = Indygame.PosMin + (platformLeft.getPosition().y - Indygame.PosMax + 80);
        else if(platformLeft.getPosition().y < Indygame.PosMin)
            platformLeft.getPosition().y = Indygame.PosMax - (Indygame.PosMin - platformLeft.getPosition().y) - 80;

        if(platformUp.getPosition().x > Indygame.width - 80)
            platformUp.getPosition().x = platformUp.getPosition().x - Indygame.width + 80;
        else if(platformUp.getPosition().x < 0)
            platformUp.getPosition().x = Indygame.width + platformUp.getPosition().x - 80;

        if(platformDown.getPosition().x > Indygame.width - 80)
            platformDown.getPosition().x = platformDown.getPosition().x - Indygame.width + 80;
        else if(platformDown.getPosition().x < 0)
            platformDown.getPosition().x = Indygame.width + platformDown.getPosition().x - 80;
    }

    @Override
    public void update(float dt) {
        handleInput();
        PlatformPos();
        Scroll();
        point.update(dt);
        if (point.getPosition().x <= platformLeft.getPlatform().getWidth() + platformLeft.getPosition().x)
        {
            point.setCentralPosition();
            platformLeft.setCentralPosition();
            if (point.collides(platformLeft) && point.lastCollide != 3 && point.centralPosition.y <= platformLeft.centralPosition.y + 41 && point.centralPosition.y >= platformLeft.centralPosition.y - 41) {
                point.angleMirrorRotation(platformLeft.getAngle());
                point.quantityBouncing++;
                point.lastCollide = 3;
            }
        }
        if (point.getPosition().x >= platformRight.getPosition().x - point.getPoint().getWidth())
        {
            point.setCentralPosition();
            platformRight.setCentralPosition();
            if (point.collides(platformRight) && point.lastCollide != 1 && point.centralPosition.y <= platformRight.centralPosition.y + 41 && point.centralPosition.y >= platformRight.centralPosition.y - 41) {
                point.angleMirrorRotation(platformRight.getAngle());
                point.quantityBouncing++;
                point.lastCollide = 1;
            }
        }
        if (point.getPosition().y <=  platformDown.getPosition().y + platformDown.getPlatform().getHeight())
        {
            point.setCentralPosition();
            platformDown.setCentralPosition();
            if (point.collides(platformDown) && point.lastCollide != 2 && point.centralPosition.x <= platformDown.centralPosition.x + 41 && point.centralPosition.x >= platformDown.centralPosition.x - 41) {
                point.angleMirrorRotation(platformDown.getAngle());
                point.quantityBouncing++;
                point.lastCollide = 2;
            }
        }
        if (point.getPosition().y >= platformUp.getPosition().y - point.getPoint().getHeight())
        {
            point.setCentralPosition();
            platformUp.setCentralPosition();
            if (point.collides(platformUp) && point.lastCollide != 4 && point.centralPosition.x <= platformUp.centralPosition.x + 41 && point.centralPosition.x >= platformUp.centralPosition.x - 41) {
                point.angleMirrorRotation(platformUp.getAngle());
                point.quantityBouncing++;
                point.lastCollide = 4;
            }
        }
        if (point.getPosition().x < 15 || point.getPosition().x > Indygame.width - point.getPoint().getWidth() - 15 || point.getPosition().y < Indygame.PosMin + 15 || point.getPosition().y > Indygame.PosMax - point.getPoint().getHeight() - 15)
        {
            isGameOn = false;
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
        hScore.draw(sb, "Highscore: " + highscore, Indygame.width - 20 - 90, Indygame.height - 50);
        score.draw(sb,"Score: " + point.quantityBouncing, 20, Indygame.height - 50);
        sb.draw(frame,-1,Indygame.PosMin);
        sb.draw(platformDown.getPlatform(), platformDown.getPosition().x, platformDown.getPosition().y);
        sb.draw(platformUp.getPlatform(), platformUp.getPosition().x, platformUp.getPosition().y);
        sb.draw(platformLeft.getPlatform(), platformLeft.getPosition().x, platformLeft.getPosition().y);
        sb.draw(platformRight.getPlatform(), platformRight.getPosition().x, platformRight.getPosition().y);

        if (!isGameOn)
        {
            if(isGameOver)
            {
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
