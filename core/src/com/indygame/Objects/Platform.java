package com.indygame.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.indygame.Indygame;

/**
 * Created by artem on 07.07.16.
 */
public class Platform {

    private Vector2 position, position2;
    private Texture platform;
    private float angleInt;
    private float angle;
    public Vector2 savePos;
    public Vector2 centralPosition, centralPosition2;
    private int number;

    public float getAngle() {
        return angleInt;
    }

    public Texture getPlatform() {
        return platform;
    }

    public Platform(float x, float y, Texture platform, float angle) {
        position = new Vector2(x, y);
        if (angle % 180 == 0)
            position2 = new Vector2(x, -100);
        else
            position2 = new Vector2(-100, y);
        savePos = new Vector2(x, y);
        this.platform = platform;
        this.angleInt = angle;
        number = ((int) (this.angleInt) / 90) + 1;
        angle %= 180;
        this.angle = (float) ((float) (angle / 180.0) * Math.PI);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getPosition2() { return position2; }

    public void setCentralPosition() {
        double beta;
        if (angle > 0 && angle <= 90) {
            beta = ((this.angle - Math.atan(4)) + MathUtils.PI * 4) % (MathUtils.PI * 2);
        } else {
            beta = ((Math.atan(4) - this.angle) + MathUtils.PI * 4) % (MathUtils.PI * 2);
        }
        double diagonal = Math.sqrt(platform.getWidth() * platform.getWidth() + platform.getHeight() * platform.getHeight());
        centralPosition = new Vector2(position.x + (float) (Math.cos(beta) * (diagonal / 2.0)), position.y + (float) (Math.sin(beta) * (diagonal / 2.0)));
        centralPosition2 = new Vector2(position2.x + (float) (Math.cos(beta) * (diagonal / 2.0)), position2.y + (float) (Math.sin(beta) * (diagonal / 2.0)));
    }

    private void Scroll(Vector3 touchPos, boolean getTouch)
    {
        if (angle % 180 == 0)
        {
            if (!getTouch)
                savePos.set(0, position.y - touchPos.y);
            position.y = touchPos.y + savePos.y;
            if (position.y > Indygame.PosMax - 80)
            {
                position2.y = position.y - Indygame.PosMax + Indygame.PosMin;
            }
            if (position.y < Indygame.PosMin)
            {
                position2.y = position.y + Indygame.PosMax - Indygame.PosMin;
            }
            if (position2.y > Indygame.PosMin && position2.y < Indygame.PosMax - 80)
            {
                position.y = position2.y;
                position2.y = -100;
            }
            if (position2.y > Indygame.PosMax || position2.y < Indygame.PosMin - 80)
                position2.y = -100;
            if (position.y > Indygame.PosMin && position.y < Indygame.PosMax - 80)
                position2.y = -100;
        }
        else
        {
            if (!getTouch)
                savePos.set(position.x - touchPos.x, 0);
            position.x = touchPos.x + savePos.x;
            if (position.x > Indygame.width - 80)
            {
                position2.x = position.x - Indygame.width;
            }
            if (position.x < 0)
            {
                position2.x = Indygame.width + position.x;
            }
            if (position2.x > 0 && position2.x < Indygame.width - 80)
            {
                position.x = position2.x;
                position2.x = -100;
            }
            if (position2.x > Indygame.width || position2.x < -80)
                position2.x = -100;
            if (position.x > 0 && position.x < Indygame.width - 80)
                position2.x = -100;
        }
    }

    public void collides(Point point)
    {
        setCentralPosition();
        if (point.collides(this) && point.lastCollide != number)
        {
            point.angleMirrorRotation(this.angleInt);
            point.quantityBouncing++;
            point.lastCollide = number;
        }
    }

    public void update(Vector3 touchPos, boolean getTouch)
    {
        Scroll(touchPos, getTouch);
    }

}
