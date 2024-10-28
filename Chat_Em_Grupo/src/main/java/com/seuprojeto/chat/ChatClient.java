package com.seuprojeto.chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ChatClient(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Conectado ao servidor de chat!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        // Thread para receber mensagens do servidor
        new Thread(() -> {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println("Servidor: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Scanner para enviar mensagens para o servidor
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            out.println(message); // Envia a mensagem para o servidor
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 12345);
        client.start();
    }
}
