package lb.edu.upa.raytracer.core;


public class Camera {

	float FOV;
	float nearClip, farClip;
	float aspectRatio;

	Quaternion orientation;
	Vector position;
	
	float yaw, pitch, roll;

	public Camera(float FOV, float aspectRatio, float nearClip, float farClip) {
		position = new Vector(0, 0, 0);
		yaw = pitch = roll = 0.0f;
		orientation = new Quaternion();
		
		this.FOV = FOV;
		this.aspectRatio = aspectRatio;
		this.nearClip = nearClip;
		this.farClip = farClip;
	}

	private void _updateOrientation()
	{
		Quaternion qpitch = new Quaternion();
		Quaternion qyaw = new Quaternion();
		Quaternion qroll = new Quaternion();

		qpitch.createFromEulerAngle(pitch, 0, 0);
		qyaw.createFromEulerAngle(0, yaw, 0);
		qroll.createFromEulerAngle(0, 0, roll);

		orientation = qpitch.multiply(qyaw).multiply(qroll);
	}
	
	public Matrix4 _frustum(float left, float right, float bottom, float top)
	{

	    float temp, temp2, temp3, temp4;
	    temp = 2.0f * nearClip;
	    temp2 = right - left;
	    temp3 = top - bottom;
	    temp4 = farClip - nearClip;
	    
	    Matrix4 m = new Matrix4(
	    		temp / temp2,
	    0.0f,
	    0.0f,
	    0.0f,
	    0.0f,
	    temp / temp3,
	    0.0f,
	    0.0f,
	    (right + left) / temp2,
	    (top + bottom) / temp3,
	    (-farClip - nearClip) / temp4,
	    -1.0f,
	    0.0f,
	    0.0f,
	    (-temp * farClip) / temp4,
	    0.0f);
	    
	    return m;
	}
	
	public Matrix4 getProjectionMatrix() {

	    float ymax, xmax;
	    ymax = nearClip * (float)Math.tan(FOV * Math.PI / 360.0f);
	    xmax = ymax * aspectRatio;
	    
	    return _frustum(-xmax, xmax, -ymax, ymax);
		
		/*
		// Degrees to Radiant
		float angle = (float)((FOV / 180.0f) * Utils.PI);
		float result = (float)(Math.sin(angle / 2.0f));

		float f = 1.0f / (float) Math.tan(result / 2.0f);
		Matrix4 m = new Matrix4(f / aspectRatio, 0, 0, 0, 0, f, 0, 0, 0, 0,
				(farClip + nearClip) / (nearClip - farClip),
				(2.0f * farClip * nearClip) / (nearClip - farClip), 0, 0, -1, 0);
		return m;
		*/
	}

	public Vector getPosition() {
		return new Vector(position);
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public Matrix4 getViewMatrix() {
		_updateOrientation();
		Matrix4 viewMatrix = orientation.getMatrix();
		viewMatrix.translate(new Vector(-position.x, -position.y, -position.z));
		return viewMatrix;
	}

	public void addYaw(float amount) {
		yaw += amount;

		if (yaw > 360.0f)
			yaw -= 360.0f;
		else if (yaw < -360.0f)
			yaw += 360.0f;
		_updateOrientation();
	}

	public void addPitch(float amount) {
		pitch += amount;

		if (pitch > 360.0f)
			pitch -= 360.0f;
		else if (pitch < -360.0f)
			pitch += 360.0f;
		_updateOrientation();
	}

	public void addRoll(float amount) {
		roll += amount;

		if (roll > 360.0f)
			roll -= 360.0f;
		else if (roll < -360.0f)
			roll += 360.0f;
		_updateOrientation();
	}

	public void moveForward(float amount) {
		
		Vector direction = getLookAt();
		direction.multiplyMerge(-amount);
		position.addMerge(direction);
	}

	public Vector getLookAt()
	{
		Vector direction = new Vector(
				2 * (orientation.m_x * orientation.m_z - orientation.m_w
						* orientation.m_y), 2 * (orientation.m_y
						* orientation.m_z + orientation.m_w * orientation.m_x),
				1 - 2 * (orientation.m_x * orientation.m_x + orientation.m_y
						* orientation.m_y));
		return direction;
	}
	
	public void strafe(float amount) {

	}
}
