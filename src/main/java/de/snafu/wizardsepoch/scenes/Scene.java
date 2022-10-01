package de.snafu.wizardsepoch.scenes;

public abstract class Scene {
    public static float SCENE_SWITCH_TIME = 2.0f;

    public Scene() {
        // empty constructor
    }

    public abstract void update(float dt);

    public abstract SceneType getType();
}
