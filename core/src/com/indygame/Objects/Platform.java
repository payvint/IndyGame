package com.indygame.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
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
    private int angleInt;
    private float angle;
    public Vector2 savePos;
    public Vector2 centralPosition;

    public int getAngle() {
        return angleInt;
    }

    public Texture getPlatform() {
        return platform;
    }

    public Platform(float x, float y, Texture platform, int angle)
    {
        position = new Vector2(x, y);
        savePos = new Vector2(x, y);
        this.platform = platform;
        this.angleInt = angle;
        angle %= 180;
        this.angle = (float) ((float) (angle / 180.0) * Math.PI);
        /*double beta;
        if (angle > 0 && angle <= 90)
        {
            beta = ((this.angle - Math.atan(platform.getWidth() / platform.getHeight())) + MathUtils.PI * 4) % (MathUtils.PI * 2);
        }
        else
        {
            beta = ((Math.atan(platform.getWidth() / platform.getHeight()) - this.angle) + MathUtils.PI * 4) % (MathUtils.PI * 2);
        }
        double diagonal = Math.sqrt(platform.getWidth() * platform.getWidth() + platform.getHeight() * platform.getHeight());
        centralPosition = new Vector2(x + (float)(Math.cos(beta) * (diagonal / 2.0)), y + (float)(Math.sin(beta) * (diagonal / 2.0)));*/
        rectangle = new Rectangle(position.x, position.y, this.platform.getWidth(), this.platform.getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setCentralPosition()
    {
        double beta;
        if (angle > 0 && angle <= 90)
        {
            beta = ((this.angle - Math.atan(4)) + MathUtils.PI * 4) % (MathUtils.PI * 2);
        }
        else
        {
            beta = ((Math.atan(4) - this.angle) + MathUtils.PI * 4) % (MathUtils.PI * 2);
        }
        double diagonal = Math.sqrt(platform.getWidth() * platform.getWidth() + platform.getHeight() * platform.getHeight());
        centralPosition = new Vector2(position.x + (float)(Math.cos(beta) * (diagonal / 2.0)), position.y + (float)(Math.sin(beta) * (diagonal / 2.0)));
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
