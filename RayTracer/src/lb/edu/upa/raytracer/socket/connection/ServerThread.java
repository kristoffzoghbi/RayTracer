package lb.edu.upa.raytracer.socket.connection;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {
	private int i = 0;

	public void run() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(MainActivity.SERVERPORT);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				ClientServiceThread cliThread = new ClientServiceThread(
						clientSocket, i++);
				cliThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}