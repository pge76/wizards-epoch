package de.snafu.wizardsepoch;

import de.snafu.wizardsepoch.scenes.SceneManager;
import de.snafu.wizardsepoch.util.Time;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL30C.glClearColor;
import static org.lwjgl.opengl.GLUtil.setupDebugMessageCallback;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Wizards Epoch
 */
@Log4j2
public class MainGame {

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 960;
    private long window;

    public static void main(String[] args) {
        log.traceEntry();
        log.info("LWJGL version {}", Version.getVersion());
        log.traceExit();

        try {
            new MainGame().run();
        } catch (Exception e) {
            log.error(e);
        }
    }


    @SuppressWarnings("resource") // suppress warnings about ignored Autoclosables from the setCallback Methods
    private void init() {
        //GLFWErrorCallback.createPrint(new PrintStream(new LoggingOutputStream(log, Level.ERROR))).set();
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Wizards Epoch", NULL, NULL);

        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetKeyCallback(window, KeyboardListener::keyCallback);
        glfwSetCursorPosCallback(window, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseListener::mouseScrollCallback);


        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            assert vidmode != null;
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );

            glfwMakeContextCurrent(window);
            // enable v-sync
            glfwSwapInterval(1);
            glfwShowWindow(window);
        }

        GL.createCapabilities();
        setupDebugMessageCallback(System.err);
        SceneManager.init();
    }

    private void loop() {
        float frameBeginTime;
        float frameEndTime;
        float deltaTime;

        frameBeginTime = Time.getTime();


        if (KeyboardListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(window, true);
        }

        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwPollEvents();


            frameEndTime = Time.getTime();
            deltaTime = frameEndTime - frameBeginTime;
            frameBeginTime = frameEndTime;

            if (deltaTime > 0.0f) {
                SceneManager.getCurrentScene().update(deltaTime);
            }
            MouseListener.endFrame();
            KeyboardListener.endFrame();
            glfwSwapBuffers(window);
        }

    }

    private void run() {
        init();
        loop();

        glfwDestroyWindow(window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
}
