package lb.edu.upa.raytracer.core;

public class Matrix4 {

	float m[] = new float[16];

	public Matrix4() {
		identity();
	}

	public Matrix4(Matrix4 mat) {
		this.set(mat);
	}

	public Matrix4(float _m[]) {
		m[0] = _m[0];
		m[1] = _m[1];
		m[2] = _m[2];
		m[3] = _m[3];
		m[4] = _m[4];
		m[5] = _m[5];
		m[6] = _m[6];
		m[7] = _m[7];
		m[8] = _m[8];
		m[9] = _m[9];
		m[10] = _m[10];
		m[11] = _m[11];
		m[12] = _m[12];
		m[13] = _m[13];
		m[14] = _m[14];
		m[15] = _m[15];
	}

	public Matrix4(float m11, float m12, float m13, float m14, float m21,
			float m22, float m23, float m24, float m31, float m32, float m33,
			float m34, float m41, float m42, float m43, float m44) {
		m[0] = m11;
		m[1] = m12;
		m[2] = m13;
		m[3] = m14;
		m[4] = m21;
		m[5] = m22;
		m[6] = m23;
		m[7] = m24;
		m[8] = m31;
		m[9] = m32;
		m[10] = m33;
		m[11] = m34;
		m[12] = m41;
		m[13] = m42;
		m[14] = m43;
		m[15] = m44;
	}

	public void set(Matrix4 mat)
	{
		m[0] = mat.m[0];
		m[1] = mat.m[1];
		m[2] = mat.m[2];
		m[3] = mat.m[3];
		m[4] = mat.m[4];
		m[5] = mat.m[5];
		m[6] = mat.m[6];
		m[7] = mat.m[7];
		m[8] = mat.m[8];
		m[9] = mat.m[9];
		m[10] = mat.m[10];
		m[11] = mat.m[11];
		m[12] = mat.m[12];
		m[13] = mat.m[13];
		m[14] = mat.m[14];
		m[15] = mat.m[15];
	}
	
	public boolean equals(Matrix4 mat) {
		return ((m[ 0] == mat.m[ 0]) && (m[ 1] == mat.m[ 1]) && (m[ 2] == mat.m[ 2]) && (m[ 3] == mat.m[ 3]) &&
				(m[ 4] == mat.m[ 4]) && (m[ 5] == mat.m[ 5]) && (m[ 6] == mat.m[ 6]) && (m[ 7] == mat.m[ 7]) &&
				(m[ 8] == mat.m[ 8]) && (m[ 9] == mat.m[ 9]) && (m[10] == mat.m[10]) && (m[11] == mat.m[11]) &&
				(m[12] == mat.m[12]) && (m[13] == mat.m[13]) && (m[14] == mat.m[14]) && (m[15] == mat.m[15]));
	}

	public Matrix4 substract(Matrix4 mat) {
		return new Matrix4(m[0] - mat.m[0], m[1] - mat.m[1], m[2] - mat.m[2], m[3]
				- mat.m[3], m[4] - mat.m[4], m[5] - mat.m[5], m[6] - mat.m[6],
				m[7] - mat.m[7], m[8] - mat.m[8], m[9] - mat.m[9], m[10]
						- mat.m[10], m[11] - mat.m[11], m[12] - mat.m[12],
				m[13] - mat.m[13], m[14] - mat.m[14], m[15] - mat.m[15]);
	}

	public Matrix4 add(Matrix4 mat) {
		return new Matrix4(m[0] + mat.m[0], m[1] + mat.m[1], m[2] + mat.m[2], m[3]
				+ mat.m[3], m[4] + mat.m[4], m[5] + mat.m[5], m[6] + mat.m[6],
				m[7] + mat.m[7], m[8] + mat.m[8], m[9] + mat.m[9], m[10]
						+ mat.m[10], m[11] + mat.m[11], m[12] + mat.m[12],
				m[13] + mat.m[13], m[14] + mat.m[14], m[15] + mat.m[15]);
	}

	public Matrix4 multiply(Matrix4 mat) {
		return new Matrix4(m[0] * mat.m[0] + m[4] * mat.m[1] + m[8] * mat.m[2]
				+ m[12] * mat.m[3], m[1] * mat.m[0] + m[5] * mat.m[1] + m[9]
				* mat.m[2] + m[13] * mat.m[3], m[2] * mat.m[0] + m[6]
				* mat.m[1] + m[10] * mat.m[2] + m[14] * mat.m[3], m[3]
				* mat.m[0] + m[7] * mat.m[1] + m[11] * mat.m[2] + m[15]
				* mat.m[3], m[0] * mat.m[4] + m[4] * mat.m[5] + m[8] * mat.m[6]
				+ m[12] * mat.m[7], m[1] * mat.m[4] + m[5] * mat.m[5] + m[9]
				* mat.m[6] + m[13] * mat.m[7], m[2] * mat.m[4] + m[6]
				* mat.m[5] + m[10] * mat.m[6] + m[14] * mat.m[7], m[3]
				* mat.m[4] + m[7] * mat.m[5] + m[11] * mat.m[6] + m[15]
				* mat.m[7], m[0] * mat.m[8] + m[4] * mat.m[9] + m[8]
				* mat.m[10] + m[12] * mat.m[11], m[1] * mat.m[8] + m[5]
				* mat.m[9] + m[9] * mat.m[10] + m[13] * mat.m[11], m[2]
				* mat.m[8] + m[6] * mat.m[9] + m[10] * mat.m[10] + m[14]
				* mat.m[11], m[3] * mat.m[8] + m[7] * mat.m[9] + m[11]
				* mat.m[10] + m[15] * mat.m[11], m[0] * mat.m[12] + m[4]
				* mat.m[13] + m[8] * mat.m[14] + m[12] * mat.m[15], m[1]
				* mat.m[12] + m[5] * mat.m[13] + m[9] * mat.m[14] + m[13]
				* mat.m[15], m[2] * mat.m[12] + m[6] * mat.m[13] + m[10]
				* mat.m[14] + m[14] * mat.m[15], m[3] * mat.m[12] + m[7]
				* mat.m[13] + m[11] * mat.m[14] + m[15] * mat.m[15]);
	}

	public void addMerge(Matrix4 mat) {
		m[0] += mat.m[0];
		m[1] += mat.m[1];
		m[2] += mat.m[2];
		m[3] += mat.m[3];
		m[4] += mat.m[4];
		m[5] += mat.m[5];
		m[6] += mat.m[6];
		m[7] += mat.m[7];
		m[8] += mat.m[8];
		m[9] += mat.m[9];
		m[10] += mat.m[10];
		m[11] += mat.m[11];
		m[12] += mat.m[12];
		m[13] += mat.m[13];
		m[14] += mat.m[14];
		m[15] += mat.m[15];
	}

	public void substractMerge(Matrix4 mat) {
		m[0] -= mat.m[0];
		m[1] -= mat.m[1];
		m[2] -= mat.m[2];
		m[3] -= mat.m[3];
		m[4] -= mat.m[4];
		m[5] -= mat.m[5];
		m[6] -= mat.m[6];
		m[7] -= mat.m[7];
		m[8] -= mat.m[8];
		m[9] -= mat.m[9];
		m[10] -= mat.m[10];
		m[11] -= mat.m[11];
		m[12] -= mat.m[12];
		m[13] -= mat.m[13];
		m[14] -= mat.m[14];
		m[15] -= mat.m[15];
	}

	public void multiplyMerge(Matrix4 mat) {
		Matrix4 tmp = this.multiply(mat);
		this.set(tmp);
	}

	public Matrix4 substract(float c) {
		return new Matrix4(m[0] - c, m[1] - c, m[2] - c, m[3] - c, m[4] - c, m[5]
				- c, m[6] - c, m[7] - c, m[8] - c, m[9] - c, m[10] - c, m[11]
				- c, m[12] - c, m[13] - c, m[14] - c, m[15] - c);
	}

	public Matrix4 add(float c) {
		return new Matrix4(m[0] + c, m[1] + c, m[2] + c, m[3] + c, m[4] + c, m[5]
				+ c, m[6] + c, m[7] + c, m[8] + c, m[9] + c, m[10] + c, m[11]
				+ c, m[12] + c, m[13] + c, m[14] + c, m[15] + c);
	}

	public Matrix4 multiply(float c) {
		return new Matrix4(m[0] * c, m[1] * c, m[2] * c, m[3] * c, m[4] * c, m[5]
				* c, m[6] * c, m[7] * c, m[8] * c, m[9] * c, m[10] * c, m[11]
				* c, m[12] * c, m[13] * c, m[14] * c, m[15] * c);
	}

	public Matrix4 divide(float c) {
		return new Matrix4(m[0] / c, m[1] / c, m[2] / c, m[3] / c, m[4] / c, m[5]
				/ c, m[6] / c, m[7] / c, m[8] / c, m[9] / c, m[10] / c, m[11]
				/ c, m[12] / c, m[13] / c, m[14] / c, m[15] / c);
	}

	public void substractMerge(float c) {
		m[0] -= c;
		m[1] -= c;
		m[2] -= c;
		m[3] -= c;
		m[4] -= c;
		m[5] -= c;
		m[6] -= c;
		m[7] -= c;
		m[8] -= c;
		m[9] -= c;
		m[10] -= c;
		m[11] -= c;
		m[12] -= c;
		m[13] -= c;
		m[14] -= c;
		m[15] -= c;
	}

	public void addMerge(float c) {
		m[0] += c;
		m[1] += c;
		m[2] += c;
		m[3] += c;
		m[4] += c;
		m[5] += c;
		m[6] += c;
		m[7] += c;
		m[8] += c;
		m[9] += c;
		m[10] += c;
		m[11] += c;
		m[12] += c;
		m[13] += c;
		m[14] += c;
		m[15] += c;
	}

	public void multiplyMerge(float c) {

		m[0] *= c;
		m[1] *= c;
		m[2] *= c;
		m[3] *= c;
		m[4] *= c;
		m[5] *= c;
		m[6] *= c;
		m[7] *= c;
		m[8] *= c;
		m[9] *= c;
		m[10] *= c;
		m[11] *= c;
		m[12] *= c;
		m[13] *= c;
		m[14] *= c;
		m[15] *= c;
	}

	public void divideMerge(float c) {
		c = 1.0f / c;
		m[0] *= c;
		m[1] *= c;
		m[2] *= c;
		m[3] *= c;
		m[4] *= c;
		m[5] *= c;
		m[6] *= c;
		m[7] *= c;
		m[8] *= c;
		m[9] *= c;
		m[10] *= c;
		m[11] *= c;
		m[12] *= c;
		m[13] *= c;
		m[14] *= c;
		m[15] *= c;
	}

	public boolean different(Matrix4 mat) {
		return !((m[0] == mat.m[0]) && (m[1] == mat.m[1]) && (m[2] == mat.m[2])
				&& (m[3] == mat.m[3]) && (m[4] == mat.m[4])
				&& (m[5] == mat.m[5]) && (m[6] == mat.m[6])
				&& (m[7] == mat.m[7]) && (m[8] == mat.m[8])
				&& (m[9] == mat.m[9]) && (m[10] == mat.m[10])
				&& (m[11] == mat.m[11]) && (m[12] == mat.m[12])
				&& (m[13] == mat.m[13]) && (m[14] == mat.m[14]) && (m[15] == mat.m[15]));
	}

	public Vector multiply(Vector v) {
		return new Vector(m[0] * v.x + m[4] * v.y + m[8] * v.z + m[12] * v.w, m[1]
				* v.x + m[5] * v.y + m[9] * v.z + m[13] * v.w, m[2] * v.x
				+ m[6] * v.y + m[10] * v.z + m[14] * v.w, m[3] * v.x + m[7]
				* v.y + m[11] * v.z + m[15] * v.w);
	}

	public void identity() {
		m[0] = 1.0f;
		m[4] = 0.0f;
		m[8] = 0.0f;
		m[12] = 0.0f;
		m[1] = 0.0f;
		m[5] = 1.0f;
		m[9] = 0.0f;
		m[13] = 0.0f;
		m[2] = 0.0f;
		m[6] = 0.0f;
		m[10] = 1.0f;
		m[14] = 0.0f;
		m[3] = 0.0f;
		m[7] = 0.0f;
		m[11] = 0.0f;
		m[15] = 1.0f;
	}

	public void zero() {
		m[0] = 0.0f;
		m[4] = 0.0f;
		m[8] = 0.0f;
		m[12] = 0.0f;
		m[1] = 0.0f;
		m[5] = 0.0f;
		m[9] = 0.0f;
		m[13] = 0.0f;
		m[2] = 0.0f;
		m[6] = 0.0f;
		m[10] = 0.0f;
		m[14] = 0.0f;
		m[3] = 0.0f;
		m[7] = 0.0f;
		m[11] = 0.0f;
		m[15] = 0.0f;
	}

	public void transpose() {
		Matrix4 tmp = new Matrix4(m[0], m[4], m[8], m[12], m[1], m[5], m[9], m[13],
				m[2], m[6], m[10], m[14], m[3], m[7], m[11], m[15]);
		this.set(tmp);
	}

	public boolean inverse() {
		float c11, c12, c13, c14;
		float c21, c22, c23, c24;
		float c31, c32, c33, c34;
		float c41, c42, c43, c44;

		c11 = (m[5] * m[10] * m[15] + m[9] * m[14] * m[7] + m[13] * m[6]
				* m[11])
				- (m[7] * m[10] * m[13] + m[11] * m[14] * m[5] + m[15] * m[6]
						* m[9]);
		c12 = (m[1] * m[10] * m[15] + m[9] * m[14] * m[3] + m[13] * m[2]
				* m[11])
				- (m[3] * m[10] * m[13] + m[11] * m[14] * m[1] + m[15] * m[2]
						* m[9]);
		c13 = (m[1] * m[6] * m[15] + m[5] * m[14] * m[3] + m[13] * m[2] * m[7])
				- (m[3] * m[6] * m[13] + m[7] * m[14] * m[1] + m[15] * m[2]
						* m[5]);
		c14 = (m[1] * m[6] * m[11] + m[5] * m[10] * m[3] + m[9] * m[2] * m[7])
				- (m[3] * m[6] * m[9] + m[7] * m[10] * m[1] + m[11] * m[2]
						* m[5]);

		float determinant = m[0] * c11 - m[4] * c12 + m[8] * c13 - m[12] * c14;
		if (determinant == 0.0) {
			identity();
			return false;
		}
		float invDeterminant = 1.0f / determinant;

		c21 = (m[4] * m[10] * m[15] + m[8] * m[14] * m[7] + m[12] * m[6]
				* m[11])
				- (m[7] * m[10] * m[12] + m[11] * m[14] * m[4] + m[15] * m[6]
						* m[8]);
		c22 = (m[0] * m[10] * m[15] + m[8] * m[14] * m[3] + m[12] * m[2]
				* m[11])
				- (m[3] * m[10] * m[12] + m[11] * m[14] * m[0] + m[15] * m[2]
						* m[8]);
		c23 = (m[0] * m[6] * m[15] + m[4] * m[14] * m[3] + m[12] * m[2] * m[7])
				- (m[3] * m[6] * m[12] + m[7] * m[14] * m[0] + m[15] * m[2]
						* m[4]);
		c24 = (m[0] * m[6] * m[11] + m[4] * m[10] * m[3] + m[8] * m[2] * m[7])
				- (m[3] * m[6] * m[8] + m[7] * m[10] * m[0] + m[11] * m[2]
						* m[4]);
		c31 = (m[4] * m[9] * m[15] + m[8] * m[13] * m[7] + m[12] * m[5] * m[11])
				- (m[7] * m[9] * m[12] + m[11] * m[13] * m[4] + m[15] * m[5]
						* m[8]);
		c32 = (m[0] * m[9] * m[15] + m[8] * m[13] * m[3] + m[12] * m[1] * m[11])
				- (m[3] * m[9] * m[12] + m[11] * m[13] * m[0] + m[15] * m[1]
						* m[8]);
		c33 = (m[0] * m[5] * m[15] + m[4] * m[13] * m[3] + m[12] * m[1] * m[7])
				- (m[3] * m[5] * m[12] + m[7] * m[13] * m[0] + m[15] * m[1]
						* m[4]);
		c34 = (m[0] * m[5] * m[11] + m[4] * m[9] * m[3] + m[8] * m[1] * m[7])
				- (m[3] * m[5] * m[8] + m[7] * m[9] * m[0] + m[11] * m[1]
						* m[4]);
		c41 = (m[4] * m[9] * m[14] + m[8] * m[13] * m[6] + m[12] * m[5] * m[10])
				- (m[6] * m[9] * m[12] + m[10] * m[13] * m[4] + m[14] * m[5]
						* m[8]);
		c42 = (m[0] * m[9] * m[14] + m[8] * m[13] * m[2] + m[12] * m[1] * m[10])
				- (m[2] * m[9] * m[12] + m[10] * m[13] * m[0] + m[14] * m[1]
						* m[8]);
		c43 = (m[0] * m[5] * m[14] + m[4] * m[13] * m[2] + m[12] * m[1] * m[6])
				- (m[2] * m[5] * m[12] + m[6] * m[13] * m[0] + m[14] * m[1]
						* m[4]);
		c44 = (m[0] * m[5] * m[10] + m[4] * m[9] * m[2] + m[8] * m[1] * m[6])
				- (m[2] * m[5] * m[8] + m[6] * m[9] * m[0] + m[10] * m[1]
						* m[4]);

		m[0] = c11 * invDeterminant;
		m[1] = -c12 * invDeterminant;
		m[2] = c13 * invDeterminant;
		m[3] = -c14 * invDeterminant;
		m[4] = -c21 * invDeterminant;
		m[5] = c22 * invDeterminant;
		m[6] = -c23 * invDeterminant;
		m[7] = c24 * invDeterminant;
		m[8] = c31 * invDeterminant;
		m[9] = -c32 * invDeterminant;
		m[10] = c33 * invDeterminant;
		m[11] = -c34 * invDeterminant;
		m[12] = -c41 * invDeterminant;
		m[13] = c42 * invDeterminant;
		m[14] = -c43 * invDeterminant;
		m[15] = c44 * invDeterminant;

		return true;
	}

	public void translate(Vector t) {
		this.multiplyMerge(Matrix4.getTranslation(t));
	}

	public static Matrix4 getTranslation(Vector t) {
		Matrix4 m = new Matrix4();
		m.identity();
		m.setEntry(12, t.x);
		m.setEntry(13, t.y);
		m.setEntry(14, t.z);
		m.setEntry(15, 1.0f);
		return m;
	}

	public void translate(float x, float y, float z) {
		this.multiplyMerge(Matrix4.getTranslation(x, y, z));
	}

	public static Matrix4 getTranslation(float x, float y, float z) {
		Matrix4 m = new Matrix4();
		m.identity();
		m.setEntry(12, x);
		m.setEntry(13, y);
		m.setEntry(14, z);
		m.setEntry(15, 1.0f);
		return m;
	}

	public void rotate(float angle, float x, float y, float z) {
		this.multiplyMerge(Matrix4.getRotation(angle, x, y, z));
	}

	public static Matrix4 getRotation(float angle, float x, float y, float z) {
		float sinAngle = (float) Math.sin(Utils.deg2Rad(angle));
		float cosAngle = (float) Math.cos(Utils.deg2Rad(angle));

		Matrix4 m = new Matrix4();
		m.identity();

		if (x != 0) {
			m.setEntry(5, cosAngle);
			m.setEntry(6, sinAngle);
			m.setEntry(9, -sinAngle);
			m.setEntry(10, cosAngle);
		} else if (y != 0) {
			m.setEntry(0, cosAngle);
			m.setEntry(2, -sinAngle);
			m.setEntry(8, sinAngle);
			m.setEntry(10, cosAngle);
		} else if (z != 0) {
			m.setEntry(0, cosAngle);
			m.setEntry(1, sinAngle);
			m.setEntry(4, -sinAngle);
			m.setEntry(5, cosAngle);
		}
		return m;
	}
	
	public void scale(float sx, float sy, float sz) {
		this.multiplyMerge(Matrix4.getScaling(sx, sy, sz));
	}

	public static Matrix4 getScaling(float sx, float sy, float sz) {
		Matrix4 m = new Matrix4();
		m.identity();

		m.setEntry(0, sx);
		m.setEntry(5, sy);
		m.setEntry(10, sz);
		return m;
	}

	public float getEntry(int index) {
		return m[index];
	}

	public void setEntry(int index, float value) {
		m[index] = value;
	}
	
	public float [] toArray()
	{
		return this.m;
	}

}
