package com.indygame.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by artem on 07.07.16.
 */
public class Platform {

    private Vector3 position;
    private Texture platform;

    public Texture getPlatform() {
        return platform;
    }

    public Platform(int x, float y, Texture platform)
    {
        position = new Vector3(x, y, 0);
        this.platform = platform;
    }

    public Vector3 getPosition() {
        return position;
    }
}
