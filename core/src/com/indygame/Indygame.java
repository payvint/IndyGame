package com.indygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Indygame extends ApplicationAdapter {
	SpriteBatch batch;
	public static final int width = 720;
	public static final int height = 1280;
	Vector3 touchPos;
	OrthographicCamera mainCamera;
	float X, Y, K;
	int upOrDown;
	boolean isPointMove;
	Texture finger;
	Texture frame;
	Texture point;
	Texture platformH;
	Texture platformV;
	//Texture bColor;

	//Music onGame;

	//Rectangle bCol;
	Rectangle Point;
	Rectangle p_r;
	Rectangle p_l;
	Rectangle p_u;
	Rectangle p_d;

	@Override
	public void create () {
		//TouchPosition
		touchPos = new Vector3();
		//Cam
		mainCamera = new OrthographicCamera();
		mainCamera.setToOrtho(false, width, height);
		//Picture
		//bColor = new Texture("bCol");
		batch = new SpriteBatch();
		finger = new Texture("finger.png");
		frame = new Texture("frame.png");
		point = new Texture("point.png");
		platformH = new Texture("platformH.png");
		platformV = new Texture("platformV.png");
		//Sound
		//onGame = Gdx.audio.newMusic(Gdx.files.internal("MACINTOSH PLUS.mp3"));

		//onGame.setLooping(true);
		//onGame.play();
		//Rectangle
		//bCol = new Rectangle();
		//bCol.set(width / 2, height / 2, width , height);
		//Point
		Point = new Rectangle();
		Point.x = width / 2 - 100 / 2;
		Point.y = height / 2 - 100 / 2;
		X = Point.x;
		Y = Point.y;
		Point.width = 100;
		Point.height = 100;
		isPointMove = false;
		//Platform
		p_r = new Rectangle();
		//p_r.set(width - 10 - 20 / 2, height / 2 - 80 / 2, 20 , 80);
		p_r.set(width - 10 - 20 / 2, MathUtils.random(height - 80 / 2), 20, 80);

		p_l = new Rectangle();
		//p_l.set(10 + 20 / 2, height / 2 - 80 / 2, 20 , 80);
		p_l.set(10 + 20 / 2, MathUtils.random(height - 80 / 2), 20 , 80);

		p_u = new Rectangle();
		//p_u.set(width / 2 - 20 / 2, height - 10 - 80 / 2, 80 , 20);
		p_u.set(MathUtils.random(width - 20 / 2), height - 10 - 80 / 2, 20 , 80);

		p_d = new Rectangle();
		//p_d.set(width / 2 - 20 / 2, 10 + 80 / 2, 80 , 20);
		p_d.set(MathUtils.random(width - 20 / 2), 10 + 80 / 2, 20 , 80);

	}

	public void spawnPlatform()
	{
		p_r = new Rectangle();
		p_r.x = width - 10 - 20 / 2;
		p_r.y = 10 + MathUtils.random(height - 80);
		p_r.width = 20;
		p_r.height = 80;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mainCamera.update();

		batch.setProjectionMatrix(mainCamera.combined);
		batch.begin();

		//batch.draw(bColor,bCol.x, bCol.y);
		batch.draw(point, Point.x, Point.y);
		batch.draw(platformV, p_r.x, p_r.y);
		
		batch.draw(platformV, p_l.x, p_l.y);
		batch.draw(platformH, p_u.x, p_u.y);
		batch.draw(platformH, p_d.x, p_d.y);

		batch.end();
		if(Gdx.input.isTouched())
		{
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			mainCamera.unproject(touchPos);
			X = touchPos.x - 100 / 2;
			Y = touchPos.y - 100 / 2;
			p_r.y = Y;
			p_l.y = Y;
			p_d.x = X;
			p_u.x = X;
			//Point direction (K = tg of angle, upOrDown = up half-plane or down)
			/*if (!isPointMove)
			{
				K = (MathUtils.random(10000) - 5000) / 100;
				upOrDown = MathUtils.random(2);
			}*/
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
