package de.snafu.wizardsepoch;

import lombok.Data;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

@Data
public class Camera {

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Vector2f position;

    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f); // Screencoords  x = 32*40 units, y = 32*21 units
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f); // view of the camera
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f); // up angle of camera
        this.viewMatrix.identity();
        this.viewMatrix = viewMatrix.lookAt(
                new Vector3f(position.x, position.y, 20.f), // Camera Position in Worldspace
                cameraFront.add(position.x, position.y, 0.0f),
                cameraUp
        );
        return this.viewMatrix;
    }
}
