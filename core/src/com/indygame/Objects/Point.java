package com.indygame.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by artem on 07.07.16.
 */
public class Point {
    private Vector3 position, velocity;
    public static final int beginVelocity = 700;
    private float angle;
    private Texture point;
    public int quantityBouncing = 0;
    private int coef = 0;
    public boolean isGameOn;

    public Point(int x, int y, int angle)
    {
        position = new Vector3(x, y ,0);
        velocity = new Vector3(0, 0, 0);
        this.angle = (float) ((float) (angle / 180.0) * Math.PI);
        coef = 1;
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
            velocity.add((beginVelocity + coef) * MathUtils.cos(angle), (beginVelocity + coef) * MathUtils.sin(angle), 0);
            if (quantityBouncing % 5 == 0 && quantityBouncing > 0) {
                coef += 50;
            }
            velocity.scl(dt);
            position.add(velocity.x, velocity.y, 0);
        }

    }
}
