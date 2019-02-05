package no.hvl.dat110.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {

	private DataOutputStream outStream; // for writing bytes to the TCP connection
	private DataInputStream inStream; // for reading bytes from the TCP connection
	private Socket socket; // socket for the underlying TCP connection

	public Connection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream(socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {

		// TODO
		// encapsulate the data contained in the message and write to the output stream
		byte[] messageData = message.encapsulate(); 
		
		try {
			outStream.write(messageData);
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}

	}

	public Message receive() {

		Message message = null;
		byte[] recvbuf = new byte[MessageConfig.SEGMENTSIZE];
		
		try {
			
			inStream.read(recvbuf, 0, MessageConfig.SEGMENTSIZE);
			message = new Message(); 
			message.decapsulate(recvbuf);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO
		// read a segment from the input stream and decapsulate into message

		return message;

	}

	// close the connection by closing streams and the underlying socket
	public void close() {

		try {

			outStream.close();
			inStream.close();

			socket.close();
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}