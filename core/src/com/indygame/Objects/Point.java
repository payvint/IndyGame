package com.indygame.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by artem on 07.07.16.
 */
public class Point {
    private Vector3 position;
    public Vector3 velocity;
    public static final int beginVelocity = 700;
    private float angle;
    private Texture point;
    public int quantityBouncing = 0;
    private int coefficient = 0;
    public boolean isGameOn;
    private Rectangle pointRectangle;
    public int indX = 1, indY = 1;

    public Point(int x, int y, int angle)
    {
        position = new Vector3(x, y ,0);
        velocity = new Vector3(0, 0, 0);
        this.angle = (float) ((float) (angle / 180.0) * Math.PI);
        point = new Texture("point.png");
        pointRectangle = new Rectangle(position.x, position.y, point.getWidth(), point.getHeight());
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getPoint() {
        return point;
    }

    public void update(float dt)
    {
        if (isGameOn) {
            velocity.add(indX * (beginVelocity + coefficient) * MathUtils.cos(angle), indY * (beginVelocity + coefficient) * MathUtils.sin(angle), 0);
            if (quantityBouncing % 5 == 0 && quantityBouncing > 0) {
                coefficient += 50;
            }
            velocity.scl(dt);
            position.add(velocity.x, velocity.y, 0);
            pointRectangle.setPosition(position.x, position.y);

        }

    }
    public boolean collides(Rectangle platform)
    {
        return pointRectangle.overlaps(platform);
    }
}
