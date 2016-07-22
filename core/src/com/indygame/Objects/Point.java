package com.indygame.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by artem on 07.07.16.
 */
public class Point {
    private Vector2 position;
    private Vector2 velocity;
    private int beginVelocity = 200;
    private float angle;
    private int angleInt;
    private Texture point;
    public int quantityBouncing = 0;
    private int coefficient = 0;
    public boolean isGameOn;
    private Rectangle pointRectangle;
    private boolean added = false;

    public Point(float x, float y, int angle)
    {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        this.angle = (float) ((float) (angle / 180.0) * Math.PI);
        angleInt = angle;
        point = new Texture("point.png");
        pointRectangle = new Rectangle(position.x, position.y, point.getWidth(), point.getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getPoint() {
        return point;
    }

    public void update(float dt)
    {
        if (isGameOn) {
            velocity.add((beginVelocity + coefficient) * MathUtils.cos(angle), (beginVelocity + coefficient) * MathUtils.sin(angle));
            if (quantityBouncing % 5 == 0 && quantityBouncing > 0 && !added) {
                coefficient += 25;
                added = true;
            }
            if (quantityBouncing % 5 == 1)
            {
                added = false;
            }
            velocity.scl(dt);
            position.add(velocity.x, velocity.y);
            pointRectangle.setPosition(position.x, position.y);

        }

    }
    public boolean collides(Rectangle platform)
    {
        return pointRectangle.overlaps(platform);
    }

    public void angleMirrorRotation(int angle)
    {
        this.angleInt = (this.angleInt + 360 - 180) % 360;
        this.angleInt += ((angle - this.angleInt) * 2 + 720) % 360;
        this.angle = (float) ((float) (this.angleInt / 180.0) * Math.PI);
    }
}
