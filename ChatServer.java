
//https://hackr.io/blog/how-to-build-a-java-chat-app
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	
	private static List<ClientHandler> clients = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		int numberOfThreads = 0;
		int numberOfThreadsTemp = 0;
		boolean serverUsed = false;

		ServerSocket serverSocket = new ServerSocket(15000);
		System.out.println("Server started. Waiting for connections.");
		
		numberOfThreadsTemp = Thread.activeCount();
		System.out.println("Base of threads: " + numberOfThreadsTemp);

		while (true) {

			/*Tried to add automation off closing the server when Threads are back to 1 and 
			* server is already used by someone, but the while loop stops at listening off new 
			* clients. 
			* 
			* Maybe make a connection listening thread and a thread counting active threads?
			*
			* Works as is, but doesn't shutdown automatically
			*/
			numberOfThreadsTemp = Thread.activeCount();
			System.out.println("Log: " + numberOfThreadsTemp);
			if (numberOfThreadsTemp == 1 && serverUsed == true) {
				System.out.println("Chats are over for now, exiting.");
				System.exit(0);
				
			}

			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected." + clientSocket);
			
			ClientHandler clientThread = new ClientHandler(clientSocket, clients);
			clients.add(clientThread);
			new Thread(clientThread).start();

			numberOfThreadsTemp = Thread.activeCount();
			if (numberOfThreadsTemp != numberOfThreads ) {
				serverUsed = true;
				numberOfThreads = numberOfThreadsTemp;
				numberOfThreadsTemp = numberOfThreads - 1;
				System.out.println("Number of participants: " + numberOfThreadsTemp);
			}


		}
		
		
	}
}
