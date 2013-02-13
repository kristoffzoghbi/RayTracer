package lb.edu.upa.raytracer.socket.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import android.util.Log;

public class ClientServiceThread extends Thread {
	Socket m_clientSocket;
	int m_clientID = -1;
	String c1_ip = null;
	String c2_ip = null;
	boolean m_bRunThread = true;
	public static boolean shouldStop = false;

	ClientServiceThread(Socket s, int clientID) {
		m_clientSocket = s;
		m_clientID = clientID;
	}
	

	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
	
		Log.d("Client accepted:", "Accepted Client : ID - " + m_clientID
				+ " : Address - "
				+ m_clientSocket.getInetAddress().getHostName());

		try {
			in = new BufferedReader(new InputStreamReader(m_clientSocket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(m_clientSocket.getOutputStream()));

			while (MainActivity.getNOCNX() < 3) {
				
				String clientCommand = in.readLine();
				
				Log.d("Client Says :", "#" + clientCommand);

				out.println(clientCommand);
				if (MainActivity.getClient1Id() == 0) {
					MainActivity.setClient1Id(Integer.valueOf(clientCommand));
					MainActivity.setClient1Ip(m_clientSocket.getInetAddress()
							.getHostName());
				} else {
					MainActivity.setClient2Id(Integer.valueOf(clientCommand));
					MainActivity.setClient2Ip(m_clientSocket.getInetAddress()
							.getHostName());
					Log.e("c1", "c1 " + MainActivity.getClient1Id());
					Log.e("c2", "c2 " + MainActivity.getClient2Id());

				}

				if(MainActivity.getNOCNX() == 2 && MainActivity.MYNUM > MainActivity.getClient1Id() && MainActivity.MYNUM > MainActivity.getClient2Id())
				{
					Log.d("YOU ARE", "The winner");
					MainActivity.setHost(MainActivity.getMyIp());
					MainActivity.mHandler.sendEmptyMessageDelayed(1, 5000);
				}

				else if (MainActivity.getNOCNX() == 2 && MainActivity.getClient1Id() > MainActivity.getClient2Id() ) {
					Log.d("YOU ARE", "The looser");
					MainActivity.setHost(MainActivity.getClient1Ip());
					MainActivity.mHandler.sendEmptyMessageDelayed(0, 10000);
				}
				else if (MainActivity.getNOCNX() == 2)
					{
						Log.d("YOU ARE", "The looser");
						MainActivity.setHost(MainActivity.getClient2Ip());
						MainActivity.mHandler.sendEmptyMessageDelayed(0, 10000);
					}
					
				out.flush();
				MainActivity.setNOCNX();
				
				if(shouldStop) {
					try {
						if(in != null && out != null){
							in.close();
							out.close();
						}
						m_clientSocket.close();
						System.out.println("...Stopped");
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
				
	}
	
}