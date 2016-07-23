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
    public Vector2 centralPosition;
    public int lastCollibe;

    public Point(float x, float y, int angle)
    {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        this.angle = (float) ((float) (angle / 180.0) * Math.PI);
        angleInt = angle;
        point = new Texture("point.png");
        lastCollibe = 0;
        /*double beta, angleMod = (float) ((float) ((angle % 180) / 180.0) * Math.PI);
        if (angle % 180 > 0 && angle % 180 <= 90)
        {
            beta = ((angleMod - Math.atan(this.point.getWidth() / this.point.getHeight())) + MathUtils.PI * 4) % (MathUtils.PI * 2);
        }
        else
        {
            beta = ((Math.atan(this.point.getWidth() / this.point.getHeight()) - angleMod) + MathUtils.PI * 4) % (MathUtils.PI * 2);
        }
        double diagonal = Math.sqrt(this.point.getWidth() * this.point.getWidth() + this.point.getHeight() * this.point.getHeight());
        centralPosition = new Vector2(x + (float)(Math.cos(beta) * (diagonal / 2.0)), y + (float)(Math.sin(beta) * (diagonal / 2.0)));*/
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

    public void setCentralPosition()
    {
        double beta = Math.atan(this.point.getHeight() / this.point.getWidth());

        double diagonal = Math.sqrt(this.point.getWidth() * this.point.getWidth() + this.point.getHeight() * this.point.getHeight());
        centralPosition = new Vector2(position.x + (float)(Math.cos(beta) * (diagonal / 2.0)), position.y + (float)(Math.sin(beta) * (diagonal / 2.0)));
    }

    public boolean collides(Platform platform)
    {
        double x = platform.centralPosition.x - centralPosition.x;
        double xConstant = 20;
        double y = platform.centralPosition.y - centralPosition.y;
        double yConstant = 45;
        double distance = Math.sqrt(x * x + y * y);
        double maxDistance = Math.sqrt(xConstant * xConstant + yConstant * yConstant);
        return maxDistance >= distance;
    }

    public void angleMirrorRotation(int angle)
    {
        this.angleInt = (this.angleInt + 360 - 180) % 360;
        this.angleInt += ((angle - this.angleInt) * 2 + 720) % 360;
        this.angle = (float) ((float) (this.angleInt / 180.0) * Math.PI);
    }
}
