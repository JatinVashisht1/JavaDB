package org.jatinvashisht1.core;

import org.jatinvashisht1.core.storageengine.MemoryStorageEngine;
import org.jatinvashisht1.core.storageengine.StorageEngine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TcpServer {
    private final int port;
    private final StorageEngine memoryStorageEngine;
    private boolean isRunning = false;
    private ServerSocket serverSocket;

    public TcpServer(StorageEngine storageEngine, int port) throws IOException {
        this.memoryStorageEngine = storageEngine;
        this.port = port;
    }

    public void start() throws IOException {
        isRunning = true;
        serverSocket = new ServerSocket(port);

        System.out.println("JavaDB Server started on port " + port);

        try {
            while (isRunning) {
                Socket socket = serverSocket.accept();
                Thread.startVirtualThread(new ClientHandler(socket, memoryStorageEngine));
            }
        } catch (SocketException ex) {
            if (!isRunning) {
                System.out.println("Server shutting down gracefully...");
            } else {
                System.err.println("Unexpected network error: " + ex.getMessage());
            }
        }
    }

    public void stop() throws IOException {
        isRunning = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }
}
