package lb.edu.upa.raytracer.scene;

import lb.edu.upa.raytracer.core.Matrix4;
import lb.edu.upa.raytracer.core.Vector;
import lb.edu.upa.raytracer.core.Vertex;

public class Light {
	
	private Matrix4 modelMatrix = new Matrix4();
	private Vertex position;
	private float intensity;
	
	public Light(Vertex position, float intensity)
	{
		this.position = position;
		this.intensity = intensity;
	}
	
	public Matrix4 getModelMatrix() {
		return modelMatrix;
	}

	public void setModelMatrix(Matrix4 modelMatrix) {
		this.modelMatrix = modelMatrix;
	}

	public Vertex getPosition() {
		Vector newPosition = new Vector(position);
		newPosition.multiplyMerge(modelMatrix);
		return new Vertex(newPosition);
	}

	public void setPosition(Vertex position) {
		this.position = position;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
}
