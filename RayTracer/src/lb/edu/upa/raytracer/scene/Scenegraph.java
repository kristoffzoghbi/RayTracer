package lb.edu.upa.raytracer.scene;

import java.util.ArrayList;


public class Scenegraph
{
	private ArrayList <SceneObject> objects = new ArrayList<SceneObject>();
	private ArrayList <Light> lights = new ArrayList<Light>();
	
	public Scenegraph()
	{
		
	}
	
	public static Scenegraph loadFromFile(String file)
	{
		
		return null;
	}
	
	public void addObject(SceneObject object)
	{
		this.objects.add(object);
	}
	
	public void addLight(Light light)
	{
		this.lights.add(light);
	}

	public ArrayList<SceneObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<SceneObject> objects) {
		this.objects = objects;
	}

	public ArrayList<Light> getLights() {
		return lights;
	}

	public void setLights(ArrayList<Light> lights) {
		this.lights = lights;
	}
	
}
