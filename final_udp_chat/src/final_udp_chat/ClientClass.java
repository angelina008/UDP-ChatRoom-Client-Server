package final_udp_chat;

import java.net.DatagramSocket;

public class ClientClass{
	
		public static void main(String args[]) throws Exception {
			System.out.println("For logging in enter 'name: your name'");
			System.out.println("For list of logged clients enter 'list:'");
			System.out.println("For sending private message enter 'nameOfReciever:TheirName: YourName: your message'");
			System.out.println("For public message enter 'public:YourName: your message'");
			DatagramSocket socket = new DatagramSocket();
			Reciever receiver = new Reciever(socket);
			Sender sender = new Sender(socket);
			Thread receiverThread = new Thread(receiver);
			Thread senderThread = new Thread(sender);
			receiverThread.start();
			senderThread.start();
		}
	

}
