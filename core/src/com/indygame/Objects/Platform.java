package com.indygame.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by artem on 07.07.16.
 */
public class Platform {

    private Vector2 position;
    private Texture platform;
    public Rectangle rectangle;
    private int angle;
    public Vector2 savePos;
    public Vector2 centralPosition;

    public int getAngle() {
        return angle;
    }

    public Texture getPlatform() {
        return platform;
    }

    public Platform(float x, float y, Texture platform, int angle)
    {
        position = new Vector2(x, y);
        savePos = new Vector2(x, y);
        this.platform = platform;
        this.angle = angle;
        int angleRot = angle % 90;
        rectangle = new Rectangle(position.x, position.y, this.platform.getWidth(), this.platform.getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
