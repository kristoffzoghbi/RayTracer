package lb.edu.upa.raytracer.core;

public class Vector {
	public float x, y, z, w;

	public Vector() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		w = 0.0f;
	}

	public Vector(float _x, float _y, float _z) {
		x = _x;
		y = _y;
		z = _z;
		w = 0.0f;
	}

	public Vector(float _x, float _y, float _z, float _w) {
		x = _x;
		y = _y;
		z = _z;
		w = _w;
	}

	public Vector(Vector v) {
		x = v.x;
		y = v.y;
		z = v.z;
		w = v.w;
	}
	
	public Vector(Vertex v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public void set(Vector v) {
		x = v.x;
		y = v.y;
		z = v.z;
		w = v.w;
	}

	public Vector substract(Vector v) {
		return new Vector(x - v.x, y - v.y, z - v.z);
	}

	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y, z + v.z);
	}

	public Vector add(float c) {
		return new Vector(x + c, y + c, z + c);
	}

	public Vector substract(float c) {
		return new Vector(x - c, y - c, z - c);
	}

	public Vector multiply(float c) {
		return new Vector(x * c, y * c, z * c);
	}
	
	public void multiplyMerge(Matrix4 matrix)
	{
		float []m = matrix.m;
	        this.x = m[0]*x + m[4]*y + m[8]*z + m[12]*w ;
	        this.y = m[1]*x + m[5]*y + m[9]*z + m[13]*w;
	        this.z = m[2]*x + m[6]*y + m[10]*z + m[14]*w;
	        this.w = m[3]*x + m[7]*y + m[11]*z + m[15]*w;
	}
	
	public Vector multiply(Matrix4 matrix)
	{
		Vector v = new Vector();
		float []m = matrix.m;
	        v.x = m[0]*x + m[4]*y + m[8]*z + m[12]*w ;
	        v.y = m[1]*x + m[5]*y + m[9]*z + m[13]*w;
	        v.z = m[2]*x + m[6]*y + m[10]*z + m[14]*w;
	        v.w = m[3]*x + m[7]*y + m[11]*z + m[15]*w;
	        
	    return v;
	}
	public Vector divide(float c) {
		c = 1.0f / c;
		return new Vector(x * c, y * c, z * c);
	}

	public void normalize()
	{
		float length = getLength();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		this.w /= length;
	}
	
	public void addMerge(Vector v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}

	public void substractMerge(Vector v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}

	public void addMerge(float c) {
		x += c;
		y += c;
		z += c;
	}

	public void substractMerge(float c) {
		x -= c;
		y -= c;
		z -= c;
	}

	public void multiplyMerge(float c) {
		x *= c;
		y *= c;
		z *= c;
	}

	public void divideMerge(float c) {
		c = 1.0f / c;
		x *= c;
		y *= c;
		z *= c;
	}

	public boolean equals(Vector v) {
		return ((x == v.x) && (y == v.y) && (z == v.z));
	}

	public boolean different(Vector v) {
		return ((x != v.x) || (y != v.y) || (z != v.z));
	}

	public Vector negate() {
		return new Vector(-x, -y, -z, w);
	}

	public float dot(Vector v) {
		return (x * v.x + y * v.y + z * v.z);
	}

	public Vector cross(Vector v) {
		float dx = y * v.z - v.y * z;
		float dy = z * v.x - v.z * x;
		float dz = x * v.y - v.x * y;
		return new Vector(dx, dy, dz);
	}

	public float getLength() {
		return (float)Math.sqrt(x * x + y * y + z * z);
	}
}
