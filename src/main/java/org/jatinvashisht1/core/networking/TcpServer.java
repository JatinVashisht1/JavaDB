package org.jatinvashisht1.core.networking;

import org.jatinvashisht1.aofmanager.AofManager;
import org.jatinvashisht1.core.storageengine.StorageEngine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TcpServer {
    private final int port;
    private final StorageEngine memoryStorageEngine;
    private final AofManager aofManager;
    private boolean isRunning = false;
    private ServerSocket serverSocket;

    public TcpServer(StorageEngine storageEngine, int port, AofManager aofManager) throws IOException {
        this.memoryStorageEngine = storageEngine;
        this.port = port;
        this.aofManager = aofManager;
    }

    public void start() throws IOException {
        isRunning = true;
        serverSocket = new ServerSocket(port);

        System.out.println("JavaDB Server started on port " + port);

        try {
            while (isRunning) {
                Socket socket = serverSocket.accept();
                Thread.startVirtualThread(new ClientHandler(socket, memoryStorageEngine, aofManager));
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
