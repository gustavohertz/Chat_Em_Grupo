package com.seuprojeto.chat;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ChatServer server;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket clientSocket, ChatServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            // Inicializa streams de entrada e saída para comunicação com o cliente
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;
            // Loop para receber mensagens do cliente
            while ((message = in.readLine()) != null) {
                System.out.println("Mensagem recebida: " + message);
                server.broadcast(message, this); // Envia a mensagem para os outros clientes
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Desconecta e remove o cliente quando o loop é encerrado
            server.removeClient(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message); // Envia uma mensagem para o cliente
    }
}
