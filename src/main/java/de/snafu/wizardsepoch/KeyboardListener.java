package de.snafu.wizardsepoch;

import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

@Log4j2
public class KeyboardListener {
    private static KeyboardListener instance;

    private final boolean[] keyPressed = new boolean[350];
    private final boolean[] keyBeginPress = new boolean[350];

    public static void keyCallback(long ignoredWindow, int key, int scancode, int action, int ignoredMods) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
            get().keyBeginPress[key] = true;
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
            get().keyBeginPress[key] = false;
        }
        logKeyPress(key, action, scancode);
    }

    private static void logKeyPress(int key, int action, int scancode) {
        String actionStr = switch (action) {
            case GLFW_PRESS -> "pressed";
            case GLFW_RELEASE -> "released";
            case GLFW_REPEAT -> "repeated";
            default -> Integer.toString(action);
        };
        log.trace("key: {} scancode: {} {}", key, scancode, actionStr);
    }

    public static KeyboardListener get() {
        if (instance == null) {
            instance = new KeyboardListener();
        }
        return instance;
    }

    public static void endFrame() {
        Arrays.fill(get().keyBeginPress, false);
    }

    public static boolean isKeyPressed(int keyCode) {
        return get().keyPressed[keyCode];
    }

    public static boolean isKeyBeginPressed(int keyCode) {
        return get().keyBeginPress[keyCode];
    }

}
