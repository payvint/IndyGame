package com.indygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.indygame.States.GameStateManager;
import com.indygame.States.MenuState;
import com.indygame.States.PlayState;

public class Indygame extends ApplicationAdapter {

	public static final int width = 480;
	public static final int height = 800;

	private GameStateManager gsm;
	private SpriteBatch batch;
	public static boolean isGameOn;


	//Lay
	//public static float PosMin = (height - width) / 2;
	//public static float PosMax = (height - width) / 2 + width;
	public static float PosMin = height - 100 - width; // 220
	public static float PosMax = height - 100; // 700
	public static Preferences prefs;// = Gdx.app.getPreferences("MySettings");

	@Override
	public void create () {
		isGameOn = false;
		prefs = Gdx.app.getPreferences("MySettings");
		if (!prefs.contains("highscore")) {
			prefs.putInteger("highscore", 0);
		}
		prefs.flush();
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0,0,1,1);
		gsm.push(new PlayState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}
