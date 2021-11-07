package final_udp_chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Reciever implements Runnable{
	DatagramSocket socket;
	byte buffer[];


	public Reciever(DatagramSocket socket) {
		this.socket = socket;
		buffer = new byte[1024];

	}

	public void run() {
		while (true) {
			try {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String received = new String(packet.getData(), 1, packet.getLength() - 1).trim();
				System.out.println(received);

			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
}
