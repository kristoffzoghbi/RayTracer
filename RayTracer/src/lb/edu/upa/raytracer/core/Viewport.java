package lb.edu.upa.raytracer.core;

public class Viewport {

	public int x, y;
	public int width, height;

	public Viewport(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Matrix4 getViewportMatrix()
	{
		Matrix4 m = new Matrix4(
				(float)width/2.0f, 0.0f, (float)width/2.0f+(float)x, 0.0f,
				0.0f,   (float)height/2.0f,  (float)height/2.0f+(float)y, 0.0f,
				0.0f, 0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f);
		
		return m;
	}
}
