package org.jatinvashisht1;

import org.jatinvashisht1.core.networking.TcpServer;
import org.jatinvashisht1.core.storageengine.MemoryStorageEngine;
import org.jatinvashisht1.core.storageengine.StorageEngine;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Booting JavaDB");

        StorageEngine memoryEngine = new MemoryStorageEngine();
        int port = 6380;

        try {
            TcpServer server = new TcpServer(memoryEngine, port);
            server.start();
        } catch (IOException e) {
            System.err.println("CRITICAL FATAL: Failed to bind to port " + port);
            e.printStackTrace();
        }
    }
}
