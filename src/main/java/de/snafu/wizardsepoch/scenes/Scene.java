package de.snafu.wizardsepoch.scenes;

import de.snafu.wizardsepoch.Camera;
import org.joml.Vector2f;

public abstract class Scene {
    public static float SCENE_SWITCH_TIME = 2.0f;

    protected Camera camera;

    public Scene() {
        camera = new Camera(new Vector2f());
    }

    public abstract void update(float dt);

    public abstract SceneType getType();

    public abstract void init();
}
