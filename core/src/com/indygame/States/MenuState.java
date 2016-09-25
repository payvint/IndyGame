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
            if(touchPos.x > (Indygame.width / 2) - (Start.getWidth() / 2) && touchPos.x < (Indygame.width / 2) + (Start.getWidth() / 2) && touchPos.y > Indygame.height / 4 * 3 && touchPos.y < Indygame.height / 4 * 3 + Start.getHeight())
            {
                gsm.set(new PlayState(gsm));
            }
            if(touchPos.y <= 150 && AUs == false)
            {
                AUs = true;
            }
            if(AUs == true && touchPos.y >= 20 && touchPos.y <= 120 && touchPos.x >= 20 && touchPos.x <= 120)
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
            sb.draw(Start, (Indygame.width / 2) - (Start.getWidth() / 2), Indygame.height / 4 * 3);
            sb.draw(Rec, (Indygame.width / 2) - (Rec.getWidth() / 2), Indygame.height / 4*2);
            sb.draw(AU, (Indygame.width / 2) - (Start.getWidth() / 2), 0);
        }
        else if(AUs == true){
            sb.draw(Menu, 20, 20);
            AboutUs.draw(sb, "Created by: \nArtem(Asterix) Payvin\nDanylo(Pinkpony008) Lamanov",5,400);
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

