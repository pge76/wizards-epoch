package de.snafu.wizardsepoch;

import lombok.extern.log4j.Log4j2;

import static org.lwjgl.glfw.GLFW.*;

@Log4j2
public class KeyboardListener {
    private static KeyboardListener instance;
    private int key;
    private int action;


    public static void keyCallback(long window, int key, int ignoredScancode, int action, int ignoredMods) {
        get().key = key;
        get().action = action;

        if (get().key == GLFW_KEY_ESCAPE && get().action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true);
        }
    }

    public static KeyboardListener get() {
        if (instance == null) {
            instance = new KeyboardListener();
        }
        return instance;
    }
}
