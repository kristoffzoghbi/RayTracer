package lb.edu.upa.raytracer.scene;

import java.util.ArrayList;

import lb.edu.upa.raytracer.core.Matrix4;
import lb.edu.upa.raytracer.core.Vector;
import lb.edu.upa.raytracer.core.Vertex;

public class Sphere implements SceneObject {

	private Matrix4 modelMatrix = new Matrix4();

	private Vertex center;
	private float radius;
	
	public Sphere(float radius, Vertex center)
	{
		this.radius = radius;
		this.center = center;
	}
	
	@Override
	public Matrix4 getModelMatrix() {
		return modelMatrix;
	}

	@Override
	public void setModelMatrix(Matrix4 modelMatrix) {
		this.modelMatrix = modelMatrix;
	}

	@Override
	public Intersection getIntersectionAtRay(Vector direction, Vertex origin) {

		Intersection intersection = new Intersection();

		Vector newCenter = new Vector(this.center);
		newCenter.w = 1;
		newCenter.multiplyMerge(modelMatrix);
		
		ArrayList<Vertex> points = getIntersection(direction,origin);
		if(points.size() == 0)
		{
			return null;
		}
		else if(points.size() == 1)
		{
			Vector normal1 = new Vector(points.get(0));
			normal1.substractMerge(newCenter);
			normal1.normalize();
			
			intersection.normal = normal1;
			intersection.origin = points.get(0);
		}
		else
		{
			Vector normal1 = new Vector(points.get(0));
			Vector normal2 = new Vector(points.get(1));
			
			normal1 = newCenter.substract(normal1);
			normal2 = newCenter.substract(normal2);
			normal1.normalize();
			normal2.normalize();
			
			float angle1 = getAngle(normal1, direction);
			float angle2 = getAngle(normal2, direction);
			
			if(angle1 >= angle2)
			{
				intersection.origin = points.get(1);
				intersection.normal = normal2;
			}
			else
			{
				intersection.origin = points.get(0);
				intersection.normal = normal1;
			}
		}
		
		return intersection;
	}

	public float getAngle(Vector v1, Vector v2)
	{
		double dot = v1.dot(v2);
		dot = dot > 1 ? 1 : dot;
		dot = dot < -1 ? -1 : dot;
		
		float smallest = (float) Math.acos(dot);
		return smallest;
	}
	
	public ArrayList<Vertex> getIntersection(Vector direction,
			Vertex origin) {
		
		Vector center = new Vector(this.center);
		center.w = 1;
		center.multiplyMerge(modelMatrix);
		
		ArrayList<Vertex> intersections = new ArrayList<Vertex>();

		// intersection rayon/sphere
		// calcule A, B and C coefficients
		Vector dis = new Vector(origin.x - center.x, origin.y - center.y,
				origin.z - center.z);
		float a = direction.x * direction.x + direction.y * direction.y
				+ direction.z * direction.z;
		float b = 2.0f * (direction.x * dis.x + direction.y * dis.y + direction.z
				* dis.z);
		float c = dis.x * dis.x + dis.y * dis.y + dis.z * dis.z
				- (radius * radius);

		// Find discriminant
		float disc = b * b - 4 * a * c;

		// if discriminant is negative there are no real roots, so return
		// false as ray misses sphere
		if (disc < 0)
			return intersections;

		if (disc < Float.MIN_NORMAL) {
			float t = (-b) / (2 * a);
			Vertex p1 = LinearCombination(1.0f, origin, t, direction);
			intersections.add(p1);
		} else {
			float sq_D = (float) Math.sqrt(disc);
			float t1 = (-b + sq_D) / (2 * a);
			Vertex p1 = LinearCombination(1.0f, origin, t1, direction);

			float t2 = (-b - sq_D) / (2 * a);
			Vertex p2 = LinearCombination(1.0f, origin, t2, direction);

			intersections.add(p1);
			intersections.add(p2);
		}

		return intersections;
	}

	public static Vertex LinearCombination(float a, Vertex op1, float b,
			Vector op2) {
		float x = a * op1.x + b * op2.x;
		float y = a * op1.y + b * op2.y;
		float z = a * op1.z + b * op2.z;
		return new Vertex(x, y, z);
	}

}
