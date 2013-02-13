package lb.edu.upa.raytracer.scene;

import lb.edu.upa.raytracer.core.Matrix4;
import lb.edu.upa.raytracer.core.Vector;
import lb.edu.upa.raytracer.core.Vertex;


public interface SceneObject {
	
	public class Intersection
	{
		public Vertex origin;
		public Vector normal;
	}
	
	public Matrix4 getModelMatrix();
	public void setModelMatrix(Matrix4 modelMatrix);
	public abstract Intersection getIntersectionAtRay(Vector direction, Vertex origin);
	
}
