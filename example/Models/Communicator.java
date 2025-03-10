package org.example.Models;

public class Communicator {
    // Socket communication
    private int port;
    private String host;


    // Data managment
    private boolean isConnected;
    private byte data;
    private int with;
    private Action action;

    Communicator(boolean isConnected, byte data, int with, Action action) {
        this.isConnected = isConnected;
        this.data = data;
        this.with = with;
        this.action = action;
    }

    private void mainLoop() {
        // Start Listening from the server


        // If there is input, read

        // If the user ones to do something, write

    }

    private byte read() {
        byte newData;

        //read from the socket

        newData = 10;

        return newData;
    }

    private void write(byte newData) {
        // Sent output to the socket
    }

    private void open() {

    }

    private void close() {

    }

}
