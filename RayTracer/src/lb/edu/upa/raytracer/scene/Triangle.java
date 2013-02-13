package lb.edu.upa.raytracer.scene;

import lb.edu.upa.raytracer.core.Matrix4;
import lb.edu.upa.raytracer.core.Vector;
import lb.edu.upa.raytracer.core.Vertex;

public class Triangle implements SceneObject{

	private Matrix4 modelMatrix = new Matrix4();
	private Vector normal;
	Vertex v1,v2,v3;
	
	public Triangle(Vertex v1, Vertex v2, Vertex v3){
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		Vector vector1 = new Vector(v1);
		Vector vector2 = new Vector(v2);
		Vector vector3 = new Vector(v3);
		Vector vectorf1 = vector2.substract(vector1);
		Vector vectorf2 = vector3.substract(vector1);
//		vector1.w = vector2.w = vector3.w = 1;
		normal = vectorf1.cross(vectorf2);
		normal.normalize();
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
		
		
		   Vector IntersectPos;

		   Vector R1 = new Vector(origin);
		   Vector R2 = new Vector(direction.multiply(10000));
		   
		   Vector P1 = new Vector(v1);
		   Vector P2 = new Vector(v2);
		   Vector P3 = new Vector(v3);
		   
		    // Find distance from LP1 and LP2 to the plane defined by the triangle
		    float Dist1 = (R1.substract(P1)).dot( normal );
		    float Dist2 = (R2.substract(P1)).dot( normal );

		    if ( (Dist1 * Dist2) >= 0.0f) { 
		        //SFLog(@"no cross"); 
		        return null;
		    } // line doesn't cross the triangle.

		    if ( Dist1 == Dist2) { 
		        //SFLog(@"parallel"); 
		        return null; 
		    } // line and plane are parallel

		    // Find point on the line that intersects with the plane
		    IntersectPos = R2.substract(R1).multiply(-Dist1/(Dist2-Dist1)).add(R1);
		    
		    // Find if the interesection point lies inside the triangle by testing it against all edges
		    Vector vTest;

		    vTest = normal.cross( P2.substract(P1) );
		    if ( vTest.dot( IntersectPos.substract(P1)) < 0.0f ) { 
		        //SFLog(@"no intersect P2-P1"); 
		        return null;
		    }

		    vTest = normal.cross( P3.substract(P2) );
		    if ( vTest.dot( IntersectPos.substract(P2)) < 0.0f ) { 
		        //SFLog(@"no intersect P3-P2"); 
		        return null; 
		    }

		    vTest = normal.cross( P1.substract(P3) );
		    if ( vTest.dot( IntersectPos.substract(P1)) < 0.0f ) { 
		        //SFLog(@"no intersect P1-P3"); 
		        return null; 
		    }

		    Intersection i = new Intersection();
		    i.normal = normal.negate();
		    i.origin = new Vertex(IntersectPos);

		    return i;
		
		/*
		Intersection i = new Intersection();
		i.normal = normal;
		
		
		float dray = -( direction.x * origin.x + direction.y*origin.y + direction.z*origin.z);
		float dplane = -(v1.x * normal.x + v1.y * normal.y + v1.z * normal.z);

		float ap = normal.x;
		float bp = normal.y;
		float cp = normal.z;
		float ad = origin.x; // direction.x;
		float bd = origin.y; //direction.y;
		float cd = origin.z; //direction.z;
		
		float x = ((bp * bd) + (cp * cd) + dplane - dray)/(ad - ap);
		float y = ((ap * ad) + (cp * cd) + dplane - dray)/(bd - bp);
		float z = ((bp * bd) + (ap * ad) + dplane - dray)/(cd - cp);
		 
		
		
		float t = normal.dot(new Vector(v1).substract(new Vector(origin)));
	    t /= normal.dot(direction);
	    
//	    Vector p1 = new Vector(, 0, )
	     
		i.origin = new Vertex(x, y, z);
		
		float maxx, maxy, maxz;
		float minx, miny, minz;
		
		maxx = Math.max(Math.max(v1.x, v2.x), v3.x);
		maxy = Math.max(Math.max(v1.y, v2.y), v3.y);
		maxz = Math.max(Math.max(v1.z, v2.z), v3.z);
		
		minx = Math.min(Math.min(v1.x, v2.x), v3.x);
		miny = Math.min(Math.min(v1.y, v2.y), v3.y);
		minz = Math.min(Math.min(v1.z, v2.z), v3.z);
		
		boolean bx, by, bz;
		bx = x <= maxx && x >= minx;
		by = y <= maxy && y >= miny;
		bz = z <= maxz && z >= minz;
		
		if(bx & by & bz)
			return i;
		
		return null;
		
		*/
		
		
	}

}
