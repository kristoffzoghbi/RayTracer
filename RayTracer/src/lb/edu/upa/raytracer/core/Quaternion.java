package lb.edu.upa.raytracer.core;

public class Quaternion {

	public float m_w;
	public float m_z;
	public float m_y;
	public float m_x;

	public Quaternion() {
		m_x = m_y = m_z = 0.0f;
		m_w = 1.0f;
	}

	public Quaternion(float m_x, float m_y, float m_z, float m_w) {
		this.m_x = m_x;
		this.m_y = m_y;
		this.m_z = m_z;
		this.m_w = m_w;
	}

	public Quaternion(Quaternion q) {
		this.set(q);
	}

	public void set(Quaternion q) {
		this.m_x = q.m_x;
		this.m_y = q.m_y;
		this.m_z = q.m_z;
		this.m_w = q.m_w;
	}

	public float getLength() {
		return (float) Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z + m_w * m_w);
	}

	public void normalize() {
		float length = getLength();
		this.m_x /= length;
		this.m_y /= length;
		this.m_z /= length;
		this.m_w /= length;
	}

	public void createFromAxisAngle(float x, float y, float z, float angle) {

		/*
		 * float sinAngle; degrees *= 0.5f; Vector vn = new Vector(x, y, z);
		 * vn.normalize();
		 * 
		 * sinAngle = (float) Math.sin(degrees);
		 * 
		 * m_x = (vn.x * sinAngle); m_y = (vn.y * sinAngle); m_z = (vn.z *
		 * sinAngle); m_w = (float) Math.cos(degrees);
		 */
		float result = (float) Math.sin(angle / 2.0f);

		m_w = (float) Math.cos(angle / 2.0f);
		m_x = (float) (x * result);
		m_y = (float) (y * result);
		m_z = (float) (z * result);

	}

	public void createFromEulerAngle(float x, float y, float z) {
		Quaternion qx = new Quaternion();
		Quaternion qy = new Quaternion();
		Quaternion qz = new Quaternion();
		Quaternion qr = new Quaternion();

		qx.m_w = (float) Math.cos(x / 2.0f);
		qx.m_x = (float) Math.sin(x / 2.0f);
		qx.m_y = 0.0f;
		qx.m_z = 0.0f;

		qy.m_w = (float) Math.cos(y / 2.0f);
		qy.m_x = 0.0f;
		qy.m_y = (float) Math.sin(y / 2.0f);
		qy.m_z = 0.0f;

		qz.m_w = (float) Math.cos(z / 2.0f);
		qz.m_x = 0.0f;
		qz.m_y = 0.0f;
		qz.m_z = (float) Math.sin(z / 2.0f);

		qr = qx.multiply(qy).multiply(qz);

		m_w = qr.m_w;
		m_x = qr.m_x;
		m_y = qr.m_y;
		m_z = qr.m_z;
	}

	public void conjugate() {
		m_x = -m_x;
		m_y = -m_y;
		m_z = -m_z;
	}

	public Matrix4 getMatrix() {
		float pMatrix[] = new float[16];

		// First row
		pMatrix[0] = 1.0f - 2.0f * (m_y * m_y + m_z * m_z);
		pMatrix[1] = 2.0f * (m_x * m_y + m_z * m_w);
		pMatrix[2] = 2.0f * (m_x * m_z - m_y * m_w);
		pMatrix[3] = 0.0f;

		// Second row
		pMatrix[4] = 2.0f * (m_x * m_y - m_z * m_w);
		pMatrix[5] = 1.0f - 2.0f * (m_x * m_x + m_z * m_z);
		pMatrix[6] = 2.0f * (m_z * m_y + m_x * m_w);
		pMatrix[7] = 0.0f;

		// Third row
		pMatrix[8] = 2.0f * (m_x * m_z + m_y * m_w);
		pMatrix[9] = 2.0f * (m_y * m_z - m_x * m_w);
		pMatrix[10] = 1.0f - 2.0f * (m_x * m_x + m_y * m_y);
		pMatrix[11] = 0.0f;

		// Fourth row
		pMatrix[12] = 0.0f;
		pMatrix[13] = 0.0f;
		pMatrix[14] = 0.0f;
		pMatrix[15] = 1.0f;

		return new Matrix4(pMatrix);
	}

	public Quaternion multiply(Quaternion q) {
		Quaternion r = new Quaternion();

		r.m_w = m_w * q.m_w - m_x * q.m_x - m_y * q.m_y - m_z * q.m_z;
		r.m_x = m_w * q.m_x + m_x * q.m_w + m_y * q.m_z - m_z * q.m_y;
		r.m_y = m_w * q.m_y + m_y * q.m_w + m_z * q.m_x - m_x * q.m_z;
		r.m_z = m_w * q.m_z + m_z * q.m_w + m_x * q.m_y - m_y * q.m_x;

		return (r);
	}

	public void multiplyMerge(Quaternion q) {
		Quaternion res = this.multiply(q);
		this.set(res);
	}

	public Vector getLocalXAxis() {
		return new Vector(1.0f - 2.0f * m_y * m_y - 2.0f * m_z * m_z, 2.0f
				* m_x * m_y + 2.0f * m_w * m_z, 2.0f * m_x * m_z - 2.0f * m_w
				* m_y);
	}

	public Vector getLocalYAxis() {
		return new Vector(2.0f * m_x * m_y - 2.0f * m_w * m_z, 1.0f - 2.0f
				* m_x * m_x - 2.0f * m_z * m_z, 2.0f * m_y * m_z + 2.0f * m_w
				* m_x);
	}

	public Vector getLocalZAxis() {
		return new Vector(2.0f * m_x * m_z + 2.0f * m_w * m_y, 2.0f * m_y * m_z
				- 2.0f * m_w * m_x, 1.0f - 2.0f * m_x * m_x - 2.0f * m_y * m_y);
	}
}
