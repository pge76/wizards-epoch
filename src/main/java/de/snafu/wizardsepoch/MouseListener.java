package de.snafu.wizardsepoch;

import lombok.extern.log4j.Log4j2;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

@Log4j2
public class MouseListener {
    private static MouseListener instance;
    private final boolean[] mouseButtonPressed = new boolean[3];
    private double scrollX;
    private double scrollY;
    private double xPos;
    private double yPos;
    private double lastX;
    private double lastY;

    private boolean isDragging;

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static MouseListener get() {
        if (instance == null) {
            instance = new MouseListener();
        }
        return instance;
    }

    public static void mousePosCallback(long ignoredWindow, double xPos, double yPos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xPos;
        get().yPos = yPos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2]; // the mouse is dragging, if we have any button pressed, and the mouse positon changed
    }

    public static void mouseButtonCallback(long ignoredWindow, int button, int action, int ignoredMods) {
        if (action == GLFW_PRESS) {
            setButtonPressed(button);
        } else if (action == GLFW_RELEASE) {
            setButtonReleased(button);
            get().isDragging = false;
        }
        log.debug(get());
    }

    public static void mouseScrollCallback(long ignoredWindow, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    private static void setButtonPressed(int button) {
        if (button < get().mouseButtonPressed.length) { // ignore additional mousebuttons
            get().mouseButtonPressed[button] = true;
        }
    }

    private static void setButtonReleased(int button) {
        if (button < get().mouseButtonPressed.length) {  // ignore additional mousebuttons
            get().mouseButtonPressed[button] = false;
        }
    }

    public static boolean isMouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) {  // ignore additional mousebuttons
            return get().mouseButtonPressed[button];
        } else {
            return false;
        }
    }

    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static double getDx() {
        return get().lastX - get().xPos;
    }

    public static double getDy() {
        return get().lastY - get().yPos;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    @Override
    public String toString() {
        return String.format("x:%.0f|y:%.0f\tdx:%.0f|dy:%.0f\tlx:%.0f|ly:%.0f\tdrag:%b", get().xPos, get().yPos, get().lastX, get().lastY, getDx(), getDy(), isDragging());
    }
}
