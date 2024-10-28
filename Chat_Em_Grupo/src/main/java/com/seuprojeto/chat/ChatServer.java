package com.seuprojeto.chat;

import java.io.*;
import java.net.*;
import java.util.*;
public class ChatServer {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Servidor iniciado na porta " + PORT);

            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente adicionado!");
                ClientHandler handler = new ClientHandler(clientSocket, this);
                clientHandlers.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void broadcast(String message, ClientHandler excludeHandler){
            for(ClientHandler handler : clientHandlers){
                if(handler != excludeHandler){
                    handler.sendMessage(message);
                }
            }
    }
    public static void removeClient(ClientHandler handler){
        clientHandlers.remove(handler);
        System.out.println("Desconectado.");
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start();
    }
}
