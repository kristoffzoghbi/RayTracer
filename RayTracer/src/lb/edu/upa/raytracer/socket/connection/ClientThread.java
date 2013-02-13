package lb.edu.upa.raytracer.socket.connection;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

public class ClientThread implements Runnable {
	String ip;
	int MYNUM;
	public ClientThread(String ip, int MYNUM) {
	       this.ip = ip;
	       this.MYNUM = MYNUM;
	   }
	public void run() {
		try {
			Socket socket;
			InetAddress serverAddr = InetAddress.getByName(ip);
			socket = new Socket(serverAddr, 8080);
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			out.println(MYNUM);
			Log.d("mynum", "Sent: "+MYNUM);
		} catch (Exception e) {
			Log.e("MainActivity", "C: Error", e);
		}
	}
}