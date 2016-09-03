package com.indygame.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, Indygame.width, Indygame.height);
        background = new Texture("bCol.png");
        Help = new Texture("Help.jpg");
        Achiv = new Texture("Achievements.jpg");
        AU = new Texture("AboutUs.jpg");
        Rec = new Texture("Record.jpg");
        Res = new Texture("Reset.jpg");
        Stats = new Texture("Statistics.jpg");
        Start = new Texture("Start.jpg");
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
        sb.draw(Start, (Indygame.width / 2) - (Start.getWidth() / 2), Indygame.height / 4 * 3);
        //sb.draw(Help, (Indygame.width/ 2) - (Help.getWidth() / 2), Indygame.height / 4);
        //sb.draw(Achiv, (Indygame.width / 2) - (Achiv.getWidth() / 2), Indygame.height / 8 * 5);
        //sb.draw(AU, (Indygame.width / 2) - (AU.getWidth() / 2), Indygame.height / 8 *7);
        //sb.draw(Res, (Indygame.width / 2) - (Res.getWidth() / 2), Indygame.height / 2);
        //sb.draw(Stats, (Indygame.width / 2) - (Stats.getWidth() / 2), Indygame.height / 4 * 3);
        sb.draw(Rec, (Indygame.width / 2) - (Rec.getWidth() / 2), Indygame.height / 4);
        sb.draw(AU, (Indygame.width / 2) - (Start.getWidth() / 2), Indygame.height / 8);
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

