package final_udp_chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender implements Runnable{
	public final static int PORT = 1219;
	private DatagramSocket socket;
	private String hostName;
	public BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	Sender(DatagramSocket sock) {
		socket = sock;
		hostName = "localhost";
	}

	private void sendMessage(String s) throws Exception {
		byte buffer[] = s.getBytes();
		InetAddress address = InetAddress.getByName(hostName);
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
		socket.send(packet);
	}

	public void run() {
		
		while (true) {
			try {
				sendMessage(in.readLine());
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}

	}
}
