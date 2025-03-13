import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Communicator implements Runnable {
    private String host;
    private int port;
    private boolean isConnected;
    private byte[] data;
    private int width;
    private byte mode;
    private Socket socket;
    private ReentrantLock byteLock = new ReentrantLock();

    public Communicator(String host, int port, int width) {
        this.host = host;
        this.port = port;
        this.isConnected = true;
        this.width = width;
        this.mode = 0; // Default mode
        this.data = new byte[32];
        updateData();
    }

    public boolean getIsConnected() { return this.isConnected; }
    public int getWidth() { return this.width; }
    public byte getMode() { return this.mode; }

    public void setWidth(int width) {
        this.width = width;
        updateData();
    }

    public void setMode(byte mode) {
        this.mode = mode;
        updateData();
    }

    private void updateData() {
        byteLock.lock();
        try {
            data[0] = mode;
            data[1] = (byte) width;
        } finally {
            byteLock.unlock();
        }
    }
    private void updateState() {
        byteLock.lock();
        try {
            mode = data[0];       
            width = data[1];       
        } finally {
            byteLock.unlock();
        }
    }

    @Override
    public void run() {
        open();
        while (isConnected) {
            read();
            updateState();
            sleep(500);
            updateData();
            send();
            sleep(500);
        }
        close();
    }

    private void open() {
        try {
            System.out.println("Opening socket...");
            socket = new Socket(host, port);
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
                    System.out.println("Data received.");
                    // save in arr using lock
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from socket: " + e.getMessage());
        }
    }

    private void send() {
        try {
            if (socket != null) {
                socket.getOutputStream().write(data);
                socket.getOutputStream().flush();
                System.out.println("Data sent: Mode=" + data[0] + ", Width=" + data[1]);
            }
        } catch (IOException e) {
            System.err.println("Error sending data: " + e.getMessage());
        }
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

    public static void main(String[] args) {
        Communicator comm = new Communicator("localhost", 12345, 50);
        comm.start();

        try {
            Thread.sleep(2000);  // Wait for the thread to start
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Changing mode to 1 (open)");
        comm.setMode((byte) 1);
        //comm.setWidth(100);

        try {
            Thread.sleep(3000);  // Wait a few seconds to send data
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Changing mode to 2 (close)");
        comm.setMode((byte) 2);

        try {
            Thread.sleep(2000);  // Allow the last packet to be sent before terminating
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        comm.isConnected = false; // Close the connection
    }
}
