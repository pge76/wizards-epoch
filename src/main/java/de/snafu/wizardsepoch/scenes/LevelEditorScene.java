package de.snafu.wizardsepoch.scenes;

import de.snafu.wizardsepoch.util.FileUtils;
import de.snafu.wizardsepoch.util.ShaderProgram;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30C.*;

public class LevelEditorScene extends Scene {

    //@formatter:off
    private final float[] vertexArray = {
            // screen   bottomleft = -1,-1
            // position          // color
             0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f, 1.0f, // bottom right red
            -0.5f,  0.5f, 0.0f,  0.0f, 1.0f, 0.0f, 1.0f, // top left green
             0.5f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f, 1.0f, // top right blue
            -0.5f, -0.5f, 0.0f,  1.0f, 1.0f, 0.0f, 1.0f  // bottom left yellow
    };

    // counter clockwise
    private final int[] elementArray = {
        2,1,0, // top right triangle
        0,1,3  // bottom left triangle
    };
    private ShaderProgram sp;
    //@formatter:on
    private int vaoId;

    public LevelEditorScene() {
    }

    @Override
    public void update(float dt) {
        sp.bind();

        glBindVertexArray(vaoId);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        sp.unbind();
    }

    @Override
    public SceneType getType() {
        return SceneType.LEVELEDITOR;
    }

    @Override
    public void init() {
        try {
            sp = new ShaderProgram();
            sp.createVertexShader(FileUtils.loadResource("assets/shaders/vertex.glsl"));
            sp.createFragementShader(FileUtils.loadResource("assets/shaders/fragment.glsl"));
            sp.link();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            // create float buffer of vertices
            FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
            vertexBuffer.put(vertexArray).flip();


            int vboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

            // create the indicies buffer
            IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
            elementBuffer.put(elementArray).flip();

            int eboId = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

            int positionSize = 3;
            int colorSize = 4;
            int vertexSizeBytes = (positionSize + colorSize) * Float.BYTES;

            // position 0 im vertex shader
            glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
            glEnableVertexAttribArray(0);

            // position 1 im vertex shader
            glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * Float.BYTES);
            glEnableVertexAttribArray(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
