package com.indygame.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by artem on 07.07.16.
 */
public class Point {
    private Vector3 position, velocity;
    public static final int beginVelocity = 500;
    private float angle;
    private Texture point;
    public boolean isGameOn;

    public Point(int x, int y, int angle)
    {
        position = new Vector3(x, y ,0);
        velocity = new Vector3(0, 0, 0);
        this.angle = (float) ((float) (angle / 180.0) * Math.PI);
        point = new Texture("point.png");
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public Texture getPoint() {
        return point;
    }

    public void update(float dt)
    {
        if (isGameOn) {
            velocity.add(beginVelocity * MathUtils.cos(angle), beginVelocity * MathUtils.sin(angle), 0);

            velocity.scl(dt);
            position.add(velocity.x, velocity.y, 0);
        }

    }
}
