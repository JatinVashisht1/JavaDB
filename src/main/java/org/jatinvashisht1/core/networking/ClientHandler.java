package org.jatinvashisht1.core.networking;

import org.jatinvashisht1.core.commandexecutors.CommandExecutor;
import org.jatinvashisht1.core.commandexecutors.CommandRouter;
import org.jatinvashisht1.core.storageengine.StorageEngine;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;
    private final StorageEngine storageEngine;
    private static final String PING = "PING";
    private static final String PONG = "PONG";
    private static final String QUIT = "QUIT";

    public ClientHandler(Socket socket, StorageEngine storageEngine) {
        this.clientSocket = socket;
        this.storageEngine = storageEngine;
    }

    @Override
    public void run() {
        try(
                clientSocket;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            System.out.println("Handling client connection from " + clientSocket.getRemoteSocketAddress());
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String cleanLine = line.trim();
                if (cleanLine.equalsIgnoreCase(PING)) {
                    printWriter.println(PONG);
                }
                else if (cleanLine.equalsIgnoreCase(QUIT)) {
                    printWriter.println("Goodbye!");
                    clientSocket.close();
                    break;
                } else {
                    try {
                        CommandExecutor commandExecutor = CommandRouter.parse(cleanLine, storageEngine);
                        String response = commandExecutor.executeCommand();
                        printWriter.println(response);
                    } catch (IllegalArgumentException ex) {
                        printWriter.println("ERR " + ex.getMessage());
                    } catch (Exception ex) {
                        printWriter.println("ERR Internal Server Error");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
