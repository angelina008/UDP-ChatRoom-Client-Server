package final_udp_chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class ServerClass implements Runnable{
	public final static int PORT = 1219;
	private final static int BUFFER = 256;

	private DatagramSocket socket;
	private HashMap<String, Integer> clients;
	
	public ServerClass() {
		try {
			socket = new DatagramSocket(PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		System.out.println("Server is running and is listening on port " + PORT);
		System.out.println("For logging in enter 'name: your name'");
		System.out.println("For list of logged clients enter 'list:'");
		System.out.println("For sending private message enter 'nameOfReciever:TheirName: YourName: your message'");
		System.out.println("For public message enter 'public:YourName: your message'");
		clients = new HashMap<String, Integer>();
	}

	@Override
	public void run() {
		byte[] buffer = new byte[BUFFER];
		while (true) {
			try {
				
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);

				String message = new String(buffer, 0, buffer.length).trim();

				InetAddress clientAddress = packet.getAddress();
				int client_port = packet.getPort();
				String []messageParts = message.split(":");
				
				if(messageParts[0].matches("name")) {
					clients.put(messageParts[1], client_port);
					System.out.println(" Welcome in the chat : " + messageParts[1]);
					byte[] data = (" Welcome in the chat : " + messageParts[1]).getBytes();
					packet = new DatagramPacket(data, data.length, clientAddress, client_port);
					socket.send(packet);
					
				}

				if(messageParts[0].matches("list")) {					
                    			Set<String> clientList = clients.keySet();
                   			 String response = "";
                   			 for (String clientOne : clientList) {
                       				 response = response + clientOne + ",";
                   			 }
                    
                   			byte[] data = (response).getBytes();
					packet = new DatagramPacket(data, data.length, clientAddress, client_port);
					socket.send(packet);
				}
				
				if(messageParts[0].matches("nameOfReciever")) {
					if(clients.containsKey(messageParts[1])) {
					String nameOfReciever = messageParts[1];
					String nameOfSender = messageParts[2];
                    			String toSendMessage = nameOfSender + ":" + messageParts[3];

                    			int toPort = clients.get(nameOfReciever);
                    
                    			byte[] data = (toSendMessage).getBytes();
                    			packet = new DatagramPacket(data, data.length, clientAddress, toPort);
    					socket.send(packet);
    					byte[] data2 = ("Your message is sent").getBytes();
    					packet = new DatagramPacket(data2, data2.length, clientAddress, client_port);
    					socket.send(packet);
                    		} else {
                       			byte[] data = ("Client does not exist").getBytes();
    					packet = new DatagramPacket(data, data.length, clientAddress, client_port);
    					socket.send(packet);
                    	}
					
				}
				if(messageParts[0].matches("public")) {
					String nameOfSender = messageParts[1];
                    			String toSendMessage = nameOfSender + ":" + messageParts[2];
                   			System.out.println(toSendMessage);
					byte[] data2 = ("Your message is sent").getBytes();
					packet = new DatagramPacket(data2, data2.length, clientAddress, client_port);
					socket.send(packet);
				}
				
				
			}catch(Exception e ) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public static void main(String [] args) {
		
		ServerClass server = new ServerClass();
		server.run();
	}


}
