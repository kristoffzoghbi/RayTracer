package lb.edu.upa.raytracer.socket.connection;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

import lb.edu.upa.raytracer.R;
import lb.edu.upa.raytracer.RayTracerActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText server1Ip;
	private EditText server2Ip;
	private TextView myIp;
	private Button connectPhones;
	private static int NOCNX = 1;
	private String server1IpAddress = "";
	private String server2IpAddress = "";
	private static String host; 
	private boolean connectedc1 = false;
	public static final int SERVERPORT = 8080;
	static Context context;
	public static Handler mHandler;

	private static int Client1Id = 0;
	public static int getNOCNX() {
		return NOCNX;
	}

	public static void setNOCNX() {
		NOCNX++;
	}
	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		MainActivity.host = host;
	}

	public static int getClient1Id() {
		return Client1Id;
	}

	public static int getMYNUM() {
		return MYNUM;
	}

	public static void setMYNUM(int mYNUM) {
		MYNUM = mYNUM;
	}

	public static void setClient1Id(int client1Id) {
		Client1Id = client1Id;
	}

	public static int getClient2Id() {
		return Client2Id;
	}

	public static void setClient2Id(int client2Id) {
		Client2Id = client2Id;
	}

	private static int Client2Id = 0;

	public static Context getContext() {
		return context;
	}

	public static String getClient1Ip() {
		return Client1Ip;
	}

	public static void setClient1Ip(String client1Ip) {
		Client1Ip = client1Ip;
	}

	public static String getClient2Ip() {
		return Client2Ip;
	}


	public static void setClient2Ip(String client2Ip) {
		Client2Ip = client2Ip;
	}

	public static String MyIp = null;

	public static String getMyIp() {
		return MyIp;
	}

	public static void setMyIp(String myIp) {
		MyIp = myIp;
	}

	public static String Client1Ip = null;
	public static String Client2Ip = null;

	static int MYNUM = 0;
	int C1NUM = 0;
	int C2NUM = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client);
		context = getBaseContext();
		server1Ip = (EditText) findViewById(R.id.server1_ip);
		server2Ip = (EditText) findViewById(R.id.server2_ip);
		connectPhones = (Button) findViewById(R.id.connect_phones);
		connectPhones.setOnClickListener(connectListener);
		myIp = (TextView) findViewById(R.id.user_status);
		myIp.setText(getLocalIpAddress());
		setMyIp(getLocalIpAddress());
		Random random = new Random();
		MYNUM = Math.abs(random.nextInt());
		Thread startlisten = new Thread(new ServerThread());
		startlisten.start();
		handlemessages();
	}

	private OnClickListener connectListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!connectedc1) {
				connectPhones.setEnabled(false);
				connectPhones.setText("Connecting ...");
				server1IpAddress = server1Ip.getText().toString();
				server2IpAddress = server2Ip.getText().toString();
				if (!server1IpAddress.equals("") && !server2IpAddress.equals("")) {
					Thread c1Thread = new Thread(new ClientThread(server1IpAddress, MYNUM));
					c1Thread.start();
					Thread c2Thread = new Thread(new ClientThread(server2IpAddress, MYNUM));
					c2Thread.start();
					connectedc1 = true;
				}
			}
		}
	};

	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						Toast.makeText(getBaseContext(),
								inetAddress.getHostAddress().toString(),
								Toast.LENGTH_SHORT).show();
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("ServerActivity", ex.toString());
		}
		return null;
	}

	private void handlemessages() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					Intent go = new Intent(MainActivity.this,RayTracerActivity.class);
					go.putExtra("leader", false);
					go.putExtra("host", getHost());
					startActivity(go);
					break;
				case 1:
					Intent goleader = new Intent(MainActivity.this, RayTracerActivity.class);
					goleader.putExtra("leader", true);
					goleader.putExtra("host", getHost());
					startActivity(goleader);
					break;

				}

			}
		};

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}
	
	public static void showMessage(String message) {
		Toast.makeText(MainActivity.getContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

}
