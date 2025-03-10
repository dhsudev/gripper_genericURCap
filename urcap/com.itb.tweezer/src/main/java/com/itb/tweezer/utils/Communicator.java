package com.itb.tweezer.utils;

public class Communicator implements Runnable{
    // Socket communication
    private int port;
    private String host;

    private ReentrantLock byteLock = new ReentrantLock();
    // Data managment
    private boolean isConnected;
    private byte[] data;
    private int width;
    private Action action;
    private ip = "localhost";
    private port = 8080;

    Communicator(boolean isConnected,  int width, Action action) {
        this.isConnected = isConnected;
        this.width = width;
        this.action = action;
        this.data = new byte[32];
    }

    public boolean getIsConnected(){return this.isConnected;}
    public int getWidht(){return this.width;}
    public Action getAction(){return this.action;}


    private void mainLoop() {
        open();
        while(isConnected){
            read();
            // sleep
            send();
            // sleep
        }
        close();
    }
    private void setData(byte[] newData){
        dataLock.lock();
        try {
            this.data = newData;
        } finally {
            lock.unlock();
        }
    }

    private byte read() {
        byte newData;
        System.out.println("Estic llegint dades");
        //llegir del socket
        setData(newData);
    }

    private void send() {
        
    }

    public void close(){
        System.out.println("Estic tancant el socket");
        skc.close();
    }

    public void open(){
        System.out.println("Estic obrint el socket");
        this.sck = new Socket(ip, port);
    }

}
