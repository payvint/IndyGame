package com.indygame.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.indygame.Indygame;

public class MenuState extends State {

    private Texture background;
    private Texture Help;
    private Texture Achiv;
    private Texture AU;
    private Texture Rec;
    private Texture Stats;
    private Texture Start;
    private Texture Res;
    private boolean AUs;
    private Texture Menu;
    private BitmapFont AboutUs;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, Indygame.width, Indygame.height);
        background = new Texture("blueScreen.png");
        Help = new Texture("Help.jpg");
        Achiv = new Texture("Achievements.jpg");
        AU = new Texture("AU.png");
        Rec = new Texture("rec.png");
        Res = new Texture("Reset.jpg");
        Stats = new Texture("Statistics.jpg");
        Start = new Texture("start.png");
        Menu = new Texture("Menu.png");
        AUs = false;
        AboutUs = new BitmapFont();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if(touchPos.y > Indygame.height - 100 && touchPos.y < Indygame.height)
            {
                gsm.set(new PlayState(gsm));
            }
            if(touchPos.y <= 100 && AUs == false)
            {
                AUs = true;
            }
            else if(AUs == true && (touchPos.y >= 20 && touchPos.y <= 120 && touchPos.x >= 20 && touchPos.x <= 120))
            {
                AUs = false;
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, Indygame.width, Indygame.height);
        if(AUs == false){
            sb.draw(Start, 0, Indygame.height - 100);
            sb.draw(Rec, 0, Indygame.height - 200);
            sb.draw(AU, 0, 0);
        }
        else if(AUs == true){
            sb.draw(Menu, 20, 20);
            AboutUs.draw(sb, "Created by: \nArtem(Asterisk) Payvin\nDanylo(Pinkpony008) Lamanov",5,400);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        Help.dispose();
        Achiv.dispose();
        AU.dispose();
        Rec.dispose();
        Res.dispose();
        Stats.dispose();
        Start.dispose();

    }
}

