package lb.edu.upa.raytracer;

import lb.edu.upa.raytracer.core.Camera;
import lb.edu.upa.raytracer.core.Matrix4;
import lb.edu.upa.raytracer.core.Pixel;
import lb.edu.upa.raytracer.core.Vector;
import lb.edu.upa.raytracer.core.Vertex;
import lb.edu.upa.raytracer.core.Viewport;
import lb.edu.upa.raytracer.scene.Light;
import lb.edu.upa.raytracer.scene.SceneObject.Intersection;
import lb.edu.upa.raytracer.scene.Scenegraph;

public class Renderer {

	Viewport viewport;
	Scenegraph scene;
	Camera camera;

	public Renderer(Scenegraph scene, Viewport viewport, Camera camera) {
		this.scene = scene;
		this.viewport = viewport;
		this.camera = camera;
	}

	public Pixel renderPixel(int x, int y) {

		/*
		 * Matrix4 combined = new Matrix4(viewport.getViewportMatrix());
		 * combined.multiplyMerge(camera.getProjectionMatrix());
		 * combined.multiplyMerge(camera.getViewMatrix());
		 * //combined.multiplyMerge(group.getModelMatrix());
		 */

		Vector pixelRay = getRay(x, y, 1.0f, camera.getViewMatrix(),
				camera.getProjectionMatrix(), viewport);

		float val = 0;

		for (int i = 0; i < scene.getObjects().size(); i++) {
			for (int j = 0; j < scene.getLights().size(); j++) {
				Intersection intersection = scene
						.getObjects()
						.get(i)
						.getIntersectionAtRay(pixelRay,
								new Vertex(camera.getPosition()));
				if (intersection != null) {
					Light l = scene.getLights().get(j);
					Vector lightRay = new Vector(intersection.origin);
					lightRay.substractMerge(new Vector(l.getPosition()));
					lightRay.w = 1;
					
					intersection.normal.normalize();
					lightRay.normalize();
					
					float d = getDistance(intersection.origin, l.getPosition());
					val += l.getIntensity()
							* (lightRay.dot(intersection.normal))
							/ (0.5 * d * d + d + 2);
				}
			}
		}

		return new Pixel(val);
	}

	public float getDistance(Vertex a, Vertex b) {
		return (float) Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y)
				* (b.y - a.y) + (b.z - a.z) * (b.z - a.z));
	}

	public Vector getRay(float winX, float winY, float winZ,
			Matrix4 modelViewMatrix, Matrix4 projectionMatrix, Viewport viewPort) {
		// Transformation matrices

		Vector in = new Vector();
		Vector out;

		// Calculation for inverting a matrix, compute projection x modelview
		// and store in A[16]
		Matrix4 A = projectionMatrix.multiply(modelViewMatrix);
		if (!A.inverse())
			return null;

		// Transformation of normalized coordinates between -1 and 1
		in.x = (winX - (float) viewport.x) / (float) viewport.width * 2.0f
				- 1.0f;
		in.y = (winY - (float) viewport.y) / (float) viewport.height * 2.0f
				- 1.0f;
		in.z = 2.0f * winZ - 1.0f;
		in.w = 1.0f;

		// Objects coordinates
		out = in.multiply(A);

		if (out.w == 0.0)
			return null;

		out.w = 1.0f / out.w;

		Vector result = new Vector(out.x * out.w, out.y * out.w, out.z * out.w);
		result.normalize();
		return result;
	}
}
