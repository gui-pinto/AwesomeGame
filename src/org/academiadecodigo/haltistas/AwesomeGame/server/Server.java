package org.academiadecodigo.haltistas.AwesomeGame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private ArrayList<ServerHelper> serverHelpers;
    private ServerGrid serverGrid;
    private int name=0;


    public Server(int portNumber) {

        try {
            serverSocket = new ServerSocket(portNumber);
            executorService = Executors.newCachedThreadPool();
            serverHelpers = new ArrayList<>();
            serverGrid = new ServerGrid(this);
            new Thread(serverGrid).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {

        while (true) {

            try {
                System.out.println("Waiting for connection");
                Socket clientSocket = serverSocket.accept();

                ServerHelper helper = new ServerHelper(name, clientSocket, this);
                name++;

                executorService.submit(helper);
                serverHelpers.add(helper);

                System.out.println("connected player " + (serverHelpers.indexOf(helper) + 1));

                synchronized (serverGrid) {
                    if (serverHelpers.size() == 2) {
                        serverGrid.notifyAll();
                        return;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean notEnoughPlayers() {
        return serverHelpers.size() < 2;
    }

    public void broadcast(String msg) {
        synchronized (serverHelpers) {

            for (ServerHelper h : serverHelpers) {

                h.sendMsg(msg);
            }
        }
    }

    public void receivedMsg(String msg) {
        serverGrid.receiveMsg(msg);

    }

}

