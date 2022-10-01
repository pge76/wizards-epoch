package de.snafu.wizardsepoch.scenes;

import de.snafu.wizardsepoch.KeyboardListener;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;


public class LevelEditorScene extends Scene {
    private float timeToChangeScene = 2.0f;
    private boolean changingScene = false;

    public LevelEditorScene() {
    }

    @Override
    public void update(float dt) {
        if (!changingScene && KeyboardListener.isKeyPressed(GLFW_KEY_SPACE)) {
            changingScene = true;
        }

        if (changingScene && timeToChangeScene > 0.0f) {
            timeToChangeScene -= dt;
        } else {
            if (changingScene && timeToChangeScene < 0) {
                changingScene = false;
                timeToChangeScene = SCENE_SWITCH_TIME;
                SceneManager.changeToScene(SceneType.OVERWORLD);
            }
        }
    }

    @Override
    public SceneType getType() {
        return SceneType.LEVELEDITOR;
    }


}
