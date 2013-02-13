package lb.edu.upa.raytracer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import lb.edu.upa.raytracer.core.Camera;
import lb.edu.upa.raytracer.core.Pixel;
import lb.edu.upa.raytracer.core.Vertex;
import lb.edu.upa.raytracer.core.Viewport;
import lb.edu.upa.raytracer.scene.Light;
import lb.edu.upa.raytracer.scene.Scenegraph;
import lb.edu.upa.raytracer.scene.Sphere;
import lb.edu.upa.raytracer.scene.Triangle;
import lb.edu.upa.raytracer.socket.connection.MainActivity;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class RayTracerActivity extends Activity {

	private boolean isLeader;
	public Socket c1socket;
	public Socket c2socket;
	public static Bitmap bitmap;
	private ProgressDialog pb;
	private String host = " 10.10.10.10";
	private static int width;
	public static Handler handler;
	private static int height;
	private float sphereradius, pointX, pointY, pointZ;
	private float nearClip, farClip, FOV, position;
	private boolean isSecondBest = false;
	ArrayList<Sphere> spheres = new ArrayList<Sphere>();
	ArrayList<Triangle> planes = new ArrayList<Triangle>();
	ArrayList<Light> lightsources = new ArrayList<Light>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initHandler();
		Bundle extra = getIntent().getExtras();
		isLeader = extra.getBoolean("leader");
		host = extra.getString("host");
		loadFromXML();
		Log.v("Leader", host);
		if (isLeader) {
			doLeaderWork();
			Toast.makeText(getBaseContext(), "Leader " + host, 10).show();
		} else {
			if (MainActivity.getMYNUM() > MainActivity.getClient1Id() || MainActivity.getMYNUM() > MainActivity.getClient2Id()) {
				isSecondBest = true;
				doClient1Work();
				Toast.makeText(getBaseContext(), "Second Best " + host, 10).show();
			} else {
				doClient2Work();
				Toast.makeText(getBaseContext(), "NOT Second Best " + host, 10).show();
			}

		}
	}

	public void initHandler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					ImageView iv = (ImageView) findViewById(R.id.imageView1);
					iv.setImageBitmap(bitmap);
					break;
				case 2:
					pb.dismiss();
					break;
				}
			}
		};
	}

	public void Calculate(int width, int heightstart, int heightend,
			boolean isClient) {

		Log.v("Calculate",String.valueOf(width) + "," + String.valueOf(heightstart)+ " ," + String.valueOf(heightend));
		Camera camera = new Camera(FOV, (float) width / (float) height,
				nearClip, farClip);
		camera.moveForward(position);

		Scenegraph scene = new Scenegraph();
		for (int i = 0; i < spheres.size(); i++) {
			Sphere s = spheres.get(i);
			scene.addObject(s);
		}
		for (int j = 0; j < planes.size(); j++) {
			Triangle t = planes.get(j);
			scene.addObject(t);
		}
		for (int z = 0; z < lightsources.size(); z++) {
			Light l = lightsources.get(z);
			scene.addLight(l);
		}

		Viewport view = new Viewport(0, 0, width, height);
		Renderer r = new Renderer(scene, view, camera);

		// Create an empty, mutable bitmap
		bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
		for (int i = 0; i < width; i++) {
			for (int j = heightstart; j < heightend; j++) {
				Pixel pix = r.renderPixel(i, j);
				// Log.v(String.valueOf(i) + " / " + String.valueOf(j),
				// String.valueOf(pix.getRgb()));

				if (pix.getRgb() > 0) {
					int val = (int) (pix.getRgb());

					val = val > 255 ? 255 : val;
					val = val < 0 ? 0 : val;
					if (isClient && isSecondBest) {
						String tosend = String.valueOf(i) + ","
								+ String.valueOf(j) + "," + String.valueOf(val);
						write1toserver(tosend);
					}
					if (isClient && !isSecondBest) {
						String tosend = String.valueOf(i) + ","
								+ String.valueOf(j) + "," + String.valueOf(val);
						write2toserver(tosend);
					}
					else if (!isClient) {
						bitmap.setPixel(i, j, Color.rgb(val, val, val));
					}
				}
//				} else {
//					if (isClient && isSecondBest) {
//						String tosend = String.valueOf(i) + ","
//								+ String.valueOf(j) + ","
//								+ String.valueOf(0xff000000);
//						write1toserver(tosend);
//					} 
//					if (isClient && !isSecondBest) {
//						String tosend = String.valueOf(i) + ","
//								+ String.valueOf(j) + ","
//								+ String.valueOf(0xff000000);
//						write2toserver(tosend);
//					} 
//					else if (!isClient) {
//						bitmap.setPixel(i, j, 0xff000000);
//					}
//				}
				
			}
			handler.sendEmptyMessage(1);
		}
		handler.sendEmptyMessage(2);
	}

	public void doLeaderWork() {

		pb = ProgressDialog.show(RayTracerActivity.this, "", "Ray Tracing ...",
				true, true);
		Thread s1Thread = new Thread(new Server1Thread());
		s1Thread.start();
		Thread s2Thread = new Thread(new Server2Thread());
		s2Thread.start();
		new Thread() {
			@Override
			public void run() {
				super.run();
				Calculate(RayTracerActivity.width,
						2 * RayTracerActivity.height / 3,
						RayTracerActivity.height, false);
			}
		}.start();

	}

	public void doClient1Work() {
		pb = ProgressDialog.show(RayTracerActivity.this, "", "Ray Tracing ...",
				true, true);
		Thread cThread = new Thread(new Client1Thread(host,
				MainActivity.getMYNUM()));
		cThread.start();
	}

	public void doClient2Work() {
		pb = ProgressDialog.show(RayTracerActivity.this, "", "Ray Tracing ...",
				true, true);
		Thread cThread = new Thread(new Client2Thread(host,
				MainActivity.getMYNUM()));
		cThread.start();
	}

	public void loadFromXML() {
		try {

			XmlPullParser xpp = getResources().getXml(R.xml.scene);

			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("scene")) {
						width = Integer.parseInt(xpp.getAttributeValue(0));
						height = Integer.parseInt(xpp.getAttributeValue(1));
						xpp.next();
					}
					if (xpp.getName().equals("sphere")) {

						sphereradius = Float.parseFloat(xpp
								.getAttributeValue(0));
						pointX = Float.parseFloat(xpp.getAttributeValue(1));
						pointY = Float.parseFloat(xpp.getAttributeValue(2));
						pointZ = Float.parseFloat(xpp.getAttributeValue(3));
						Vertex center = new Vertex(pointX, pointY, pointZ);
						Sphere s = new Sphere(sphereradius, center);
						spheres.add(s);
					}
					if (xpp.getName().equals("plan")) {

						Vertex v1 = new Vertex(Float.parseFloat(xpp
								.getAttributeValue(0)), Float.parseFloat(xpp
								.getAttributeValue(1)), Float.parseFloat(xpp
								.getAttributeValue(2)));
						Vertex v2 = new Vertex(Float.parseFloat(xpp
								.getAttributeValue(3)), Float.parseFloat(xpp
								.getAttributeValue(4)), Float.parseFloat(xpp
								.getAttributeValue(5)));
						Vertex v3 = new Vertex(Float.parseFloat(xpp
								.getAttributeValue(6)), Float.parseFloat(xpp
								.getAttributeValue(7)), Float.parseFloat(xpp
								.getAttributeValue(8)));
						Triangle t = new Triangle(v1, v2, v3);
						planes.add(t);

					}
					if (xpp.getName().equals("camera")) {
						nearClip = Float.parseFloat(xpp.getAttributeValue(0));
						farClip = Float.parseFloat(xpp.getAttributeValue(1));
						FOV = Float.parseFloat(xpp.getAttributeValue(2));
						position = Float.parseFloat(xpp.getAttributeValue(3));
					}
					if (xpp.getName().equals("lum")) {

						Vertex position = new Vertex(Float.parseFloat(xpp
								.getAttributeValue(1)), Float.parseFloat(xpp
								.getAttributeValue(2)), Float.parseFloat(xpp
								.getAttributeValue(3)));
						Light l = new Light(position, Float.parseFloat(xpp
								.getAttributeValue(0)));
						lightsources.add(l);
					} else
						xpp.next();
				}
				xpp.next();
			}
		} catch (Throwable t) {
			Log.v("XML parser", t.toString());
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}

	public class Client1ServiceThread extends Thread {
		Socket m_clientSocket;
		int m_clientID = -1;
		String c1_ip = null;
		String c2_ip = null;
		boolean m_bRunThread = true;

		Client1ServiceThread(Socket s, int clientID) {
			m_clientSocket = s;
			m_clientID = clientID;
		}

		public void run() {
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(
						m_clientSocket.getInputStream()));

				while (true) {
					String clientCommand = in.readLine();
					String tosend2 = clientCommand.substring(0,
							clientCommand.indexOf(","));
					int a = Integer.parseInt(tosend2);
					String tosend3 = clientCommand.substring(
							clientCommand.indexOf(",") + 1,
							clientCommand.lastIndexOf(","));
					int b = Integer.parseInt(tosend3);
					String tosend4 = clientCommand.substring(clientCommand
							.lastIndexOf(",") + 1);
					int c = Integer.parseInt(tosend4);
					RayTracerActivity.bitmap.setPixel(a, b, Color.rgb(c, c, c));
					RayTracerActivity.handler.sendEmptyMessage(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public class Client2ServiceThread extends Thread {
		Socket m_clientSocket;
		int m_clientID = -1;
		String c1_ip = null;
		String c2_ip = null;
		boolean m_bRunThread = true;

		Client2ServiceThread(Socket s, int clientID) {
			m_clientSocket = s;
			m_clientID = clientID;
		}

		public void run() {
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(
						m_clientSocket.getInputStream()));

				while (true) {
					String clientCommand = in.readLine();
					String tosend2 = clientCommand.substring(0,
							clientCommand.indexOf(","));
					int a = Integer.parseInt(tosend2);
					String tosend3 = clientCommand.substring(
							clientCommand.indexOf(",") + 1,
							clientCommand.lastIndexOf(","));
					int b = Integer.parseInt(tosend3);
					String tosend4 = clientCommand.substring(clientCommand
							.lastIndexOf(",") + 1);
					int c = Integer.parseInt(tosend4);
					RayTracerActivity.bitmap.setPixel(a, b, Color.rgb(c, c, c));
					RayTracerActivity.handler.sendEmptyMessage(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class Client1Thread implements Runnable {
		String ip;
		int MYNUM;

		public Client1Thread(String ip, int MYNUM) {
			this.ip = ip;
			this.MYNUM = MYNUM;
		}

		public void run() {
			String command;
			BufferedReader in;
			try {
				InetAddress serverAddr = InetAddress.getByName(ip);
				c1socket = new Socket(serverAddr, 4445);
				in = new BufferedReader(new InputStreamReader(
						c1socket.getInputStream()));
				command = in.readLine();
				String command2 = command.substring(0, command.indexOf(","));
				int a = Integer.parseInt(command2);
				String tosend3 = command.substring(command.indexOf(",") + 1);
				int b = Integer.parseInt(tosend3);

				if (command != null) {
						Calculate(RayTracerActivity.width, a, b, true);
				}
			} catch (Exception e) {
				Log.e("MainActivity", "C: Error", e);
			}
		}
	}

	public class Client2Thread implements Runnable {
		String ip;
		int MYNUM;

		public Client2Thread(String ip, int MYNUM) {
			this.ip = ip;
			this.MYNUM = MYNUM;
		}

		public void run() {
			String command;
			BufferedReader in;
			try {
				InetAddress serverAddr = InetAddress.getByName(ip);
				c2socket = new Socket(serverAddr, 4446);
				in = new BufferedReader(new InputStreamReader(
						c2socket.getInputStream()));
				command = in.readLine();
				String command2 = command.substring(0, command.indexOf(","));
				int a = Integer.parseInt(command2);
				String tosend3 = command.substring(command.indexOf(",") + 1);
				int b = Integer.parseInt(tosend3);

				if (command != null) {
						Calculate(RayTracerActivity.width, a, b, true);
				}
			} catch (Exception e) {
				Log.e("MainActivity", "C: Error", e);
			}
		}
	}

	private void write1toserver(String value) {
		try {
			// Log.d("ClientActivity", "C: Sending command.");
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(c1socket.getOutputStream())), true);
			out.println(value);
			// Log.d("ClientActivity", "C: Sent.");
		} catch (Exception e) {
			Log.e("ClientActivity", "S: Error", e);
		}
	}

	private void write2toserver(String value) {
		try {
			// Log.d("ClientActivity", "C: Sending command.");
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(c2socket.getOutputStream())), true);
			out.println(value);
			// Log.d("ClientActivity", "C: Sent.");
		} catch (Exception e) {
			Log.e("ClientActivity", "S: Error", e);
		}
	}

	public class Server1Thread implements Runnable {
		private int i = 0;

		public void run() {
			ServerSocket serverSocket;
			PrintWriter out;
			try {
				serverSocket = new ServerSocket(4445);
//				while (true) {
					Socket clientSocket = serverSocket.accept();
					out = new PrintWriter(new OutputStreamWriter(
							clientSocket.getOutputStream()));
					out.println("0," + RayTracerActivity.width / 3);
					out.flush();
					Client1ServiceThread cliThread = new Client1ServiceThread(
							clientSocket, i++);
					cliThread.start();
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class Server2Thread implements Runnable {
		private int i = 0;
		public void run() {
			ServerSocket serverSocket;
			PrintWriter out;
			try {
				serverSocket = new ServerSocket(4446);
//				while (true) {
					Socket clientSocket = serverSocket.accept();
					out = new PrintWriter(new OutputStreamWriter(
							clientSocket.getOutputStream()));
					out.println(RayTracerActivity.width / 3 + "," + 2* RayTracerActivity.width / 3);
					out.flush();
					Client2ServiceThread cliThread = new Client2ServiceThread(
							clientSocket, i++);
					cliThread.start();
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
}