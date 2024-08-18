package com.digimortal.chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	
	private static List<ClientHandler> clients = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = new ServerSocket(15000);
		System.out.println("Server started. Waiting for connections.");
		
		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected." + clientSocket);
			
			ClientHandler clientThread = new ClientHandler(clientSocket, clients);
			clients.add(clientThread);
			new Thread(clientThread).start();
		}
		
		
	}
}
