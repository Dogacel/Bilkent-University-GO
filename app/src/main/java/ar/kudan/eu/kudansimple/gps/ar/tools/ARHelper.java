package ar.kudan.eu.kudansimple.gps.ar.tools;

import android.view.MotionEvent;

import com.jme3.math.Matrix4f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;

import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageNode;
import eu.kudan.kudan.ARView;

/**
 * Credits go to user wman @ kudan.ar help forums.
 */
public class ARHelper {

    static class Ray {
        Vector3f origin = new Vector3f();
        Vector3f direction = new Vector3f(0, 0, 1);
    }

    /**
     * Gets the touch ray on the ARWorld
     * @param arView current View
     * @param x touch coordinate x
     * @param y touch coordinate y
     * @return Ray of the touch.
     */
    private static Ray getTouchRay(ARView arView, float x, float y) {
        int currentCamWidth = arView.getWidth();
        int currentCamHeight = arView.getHeight();

        Matrix4f projectionMatrix = arView.getContentViewPort().getCamera().getProjectionMatrix();
        Matrix4f viewMatrix = arView.getContentViewPort().getCamera().getWorld().getFullTransform();

        // ray start
        Vector4f startNormalised = new Vector4f(
                (2.0f * x) / currentCamWidth - 1.0f,
                1.0f - (2.0f * y) / currentCamHeight,
                -1f,
                1f);
        Vector4f startEyeRay = projectionMatrix.invert().mult(startNormalised);
        Vector4f startWorldRay = viewMatrix.invert().mult(startEyeRay);
        Vector4f rayStart = startWorldRay.mult(1f / startWorldRay.w);

        // ray end
        Vector4f endNormalised = new Vector4f(
                (2.0f * x) / currentCamWidth - 1.0f,
                1.0f - (2.0f * y) / currentCamHeight,
                1f,
                1f);
        Vector4f endEyeRay = projectionMatrix.invert().mult(endNormalised);
        Vector4f endWorldRay = viewMatrix.invert().mult(endEyeRay);
        Vector4f rayEnd = endWorldRay.mult(1f / endWorldRay.w);


        Ray ray = new Ray();
        ray.origin = new Vector3f(rayStart.getX(), rayStart.getY(), rayStart.getZ());

        Vector4f direction = rayStart.subtract(rayEnd).normalize();
        ray.direction = new Vector3f(direction.getX(), direction.getY(), direction.getZ());

        return ray;
    }

    /**
     * Check whether a node is selected or not
     * @param arView current view
     * @param node node to be checked.
     * @param e motion event
     * @param objectSize size of the object.
     * @return is it clicked ?
     */
    public static Boolean isNodeSelected(ARView arView, GPSImageNode node, MotionEvent e, int objectSize) {
        Ray ray = getTouchRay(arView, e.getRawX(), e.getRawY());

        Vector3f nodePosition = node.getFullPosition();

        Vector3f k = ray.origin.subtract(nodePosition);
        float b = k.dot(ray.direction);
        float r = objectSize / 2;
        float c = k.dot(k) - r * r;
        float d = b * b - c;
        if (d >= 0) {
            return true;
        }

        return false;
    }
}
