package lb.edu.upa.raytracer.core;

public class Vertex {

	public float x, y, z;

	public Vertex(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vertex(Vector vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}
}
