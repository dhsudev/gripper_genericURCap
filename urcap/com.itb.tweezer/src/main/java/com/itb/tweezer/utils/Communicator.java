package com.itb.tweezer.utils;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Communicator implements Runnable {
    private String host;
    private int port;
    private boolean isConnected;
    private Socket socket;
    private byte[] outputBuffer = new byte[32];
    private byte[] inputBuffer = new byte[32];
    private ReentrantLock readBuffLock = new ReentrantLock();
    private ReentrantLock writeBuffLock = new ReentrantLock();

    private boolean debugMode = false;

    public Communicator(String host, int port, boolean debugMode) {
        this.host = host;
        this.port = port;
        this.debugMode = debugMode;
    }

    public Communicator(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean getIsConnected() { return this.isConnected; }

    /* **********************************
    * BUFFERS MANIPULATION              *
    * Read/write using locks            *
    *************************************/
    public byte[] getInputBuffer() {
        byte[] buffer = new byte[32];
        readBuffLock.lock();
        try {
            buffer = inputBuffer;       
        } finally {
            readBuffLock.unlock();
        }
        return buffer;
    }

    public void setOutputBuffer(byte[] buffer) {
        writeBuffLock.lock();
        try {
            outputBuffer = buffer;       
        } finally {
            writeBuffLock.unlock();
        }
    }


    /* **********************************
    * COMMUNICATION UTILS               *
    * Utils to write/read with          *
    * the sockets                       *
    *************************************/
    private void open() {
        try {
            System.out.println("Opening socket...");
            socket = new Socket(host, port);
            isConnected = true;
            System.out.println("Socket opened");
        } catch (IOException e) {
            System.err.println("Error opening socket: " + e.getMessage());
            isConnected = false;
        }
    }

    private void close() {
        try {
            if (socket != null) {
                socket.close();
                System.out.println("Socket closed.");
            }
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
        isConnected = false;
    }

    private void read() {
        try {
            if (socket != null && socket.getInputStream().available() > 0) {
                byte[] buffer = new byte[32];
                int bytesRead = socket.getInputStream().read(buffer);
                if (bytesRead > 0) {
                    debug("Data received: " + buffer);
                    readBuffLock.lock();
                    try {
                        inputBuffer = buffer;       
                    } finally {
                        readBuffLock.unlock();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from socket: " + e.getMessage());
        }
    }

    private void send() {
        try {
            if (socket != null) {
            	writeBuffLock.lock();
                try {
                	socket.getOutputStream().write(outputBuffer);
                    socket.getOutputStream().flush();
                    debug("Sent outputBuff: " + outputBuffer);       
                } finally {
                    writeBuffLock.unlock();
                }
            }
        } catch (IOException e) {
            System.err.println("Error sending data: " + e.getMessage());
        }
    }

    /* **********************************
    * COMMUNICATION LOOP                *
    * Methods for init communication    *
    * with a thread                     *
    *************************************/

    // Opens the socket, and while connected, reads and sends data each 200ms
    @Override
    public void run() {
        open();
        while (isConnected) {
            read();
            sleep(200);
            send();
            sleep(200);
        }
        close();
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void start() {
        new Thread(this).start();
    }
    
    public void setIsConnected(Boolean isConnected) {
		this.isConnected = isConnected;
	}

    // Function to debug the communication
    public void debug(String str) {
        if (debugMode) {
            System.out.println(str);
        }
    }
}

