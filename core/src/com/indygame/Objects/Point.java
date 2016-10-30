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
    private float angle;
    private float angleInt;
    private Texture point;
    public int quantityBouncing = 0;
    private int coefficient = 0;
    public boolean isGameOn;
    private boolean added = false;
    public Vector2 centralPosition;
    public int lastCollide;
    private int sigma = 20;
    private static final int beginVelocity = 150;

    public Point(float x, float y, float angle) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        this.angle = (float) ((float) (angle / 180.0) * Math.PI);
        angleInt = angle;
        point = new Texture("point.png");
        lastCollide = 0;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getPoint() {
        return point;
    }

    private int increment(int quantityBouncing) {
        if (quantityBouncing <= 25)
            return 15;
        else if (quantityBouncing <= 50)
            return 10;
        else
        {
            sigma /= 2;
            return sigma;
        }
    }

    public void update(float dt) {
        if (isGameOn) {
            velocity.add((beginVelocity + coefficient) * MathUtils.cos(angle), (beginVelocity + coefficient) * MathUtils.sin(angle));
            if (quantityBouncing % 5 == 0 && quantityBouncing > 0 && !added) {
                coefficient += increment(quantityBouncing);
                added = true;
            }
            if (quantityBouncing % 5 == 1)
            {
                added = false;
            }
            velocity.scl(dt);
            position.add(velocity.x, velocity.y);
        }

    }

    public void setCentralPosition() {
        double beta = Math.atan(this.point.getHeight() / this.point.getWidth());

        double diagonal = Math.sqrt(this.point.getWidth() * this.point.getWidth() + this.point.getHeight() * this.point.getHeight());
        centralPosition = new Vector2(position.x + (float)(Math.cos(beta) * (diagonal / 2.0)), position.y + (float)(Math.sin(beta) * (diagonal / 2.0)));
    }

    public boolean collides(Platform platform) {
        setCentralPosition();
        double x = platform.centralPosition.x - centralPosition.x;
        double x2 = platform.centralPosition2.x - centralPosition.x;
        double y = platform.centralPosition.y - centralPosition.y;
        double y2 = platform.centralPosition2.y - centralPosition.y;
        double dist = Math.sqrt(x * x + y * y);
        double dist2 = Math.sqrt(x2 * x2 + y2 * y2);
        float beta = (float) Math.abs(Math.acos(Math.abs(x) / dist));
        float beta2 = (float) Math.abs(Math.acos(Math.abs(x2) / dist2));
        int angle = (int) platform.getAngle() % 180;

        if (angle > 90) {
            angle = 180 - angle;
            beta += (float) ((float) ((180 - angle) / 180.0) * Math.PI);
            beta2 += (float) ((float) ((180 - angle) / 180.0) * Math.PI);
            beta = Math.abs(beta);
            beta2 = Math.abs(beta2);
        }
        else {
            beta = Math.abs(beta - (float) ((float) (angle / 180.0) * Math.PI));
            beta2 = Math.abs(beta2 - (float) ((float) (angle / 180.0) * Math.PI));
        }
        double horizontal = Math.sin(beta) * dist;
        double horizontal2 = Math.sin(beta2) * dist2;
        double vertical = Math.cos(beta) * dist;
        double vertical2 = Math.cos(beta2) * dist2;
        return (vertical <= 10 + point.getWidth() / 2 + (beginVelocity + coefficient) / 15 - 10 && horizontal <= 41) || (vertical2 <= 10 + point.getWidth() / 2 + (beginVelocity + coefficient) / 15 - 10 && horizontal2 <= 41);
    }

    public void angleMirrorRotation(float angle) {
        this.angleInt = (this.angleInt + 360 - 180) % 360;
        this.angleInt += ((angle - this.angleInt) * 2 + 720) % 360;
        this.angle = (float) ((float) (this.angleInt / 180.0) * Math.PI);
    }

    public void angleCircleRotation(Platform platform) {
        float angle = platform.getAngle();
        if (angle == 0 || angle == 180)
        {
            angle += Math.cos((angle * Math.PI) / 180) * (centralPosition.y - platform.centralPosition.y) / 2;
        }
        else
        {
            angle -= Math.sin((angle * Math.PI) / 180) * (centralPosition.x - platform.centralPosition.x);
        }
        angle = (angle + 720) % 360;
        angleMirrorRotation(angle);
    }
}
