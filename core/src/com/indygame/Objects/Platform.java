package com.indygame.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by artem on 07.07.16.
 */
public class Platform {

    private Vector3 position;
    private Texture platform;
    public Rectangle rectangle;
    private int angle;
    public Vector3 savePos;

    public int getAngle() {
        return angle;
    }

    public Texture getPlatform() {
        return platform;
    }

    public Platform(int x, float y, Texture platform, int angle)
    {
        position = new Vector3(x, y, 0);
        savePos = new Vector3(x, y, 0);
        this.platform = platform;
        this.angle = angle;
        rectangle = new Rectangle(position.x, position.y, this.platform.getWidth(), this.platform.getHeight());
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
