import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GripperServer implements Runnable {

    private GripperUI ui;
    private boolean keepGoing = true;
    private int[] actualState = {0, 42};

    public GripperServer(GripperUI ui) {
        this.ui = ui;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            ui.setStatus("Waiting for connection...");
            Socket clientSocket = serverSocket.accept();
            ui.setStatus("Connected to " + clientSocket.getInetAddress());

            clientSocket.getOutputStream().write(new byte[]{(byte) actualState[0], (byte) actualState[1]});
            Thread.sleep(200);

            while (keepGoing) {
                try {
                    byte[] buffer = new byte[32];
                    int bytesRead = clientSocket.getInputStream().read(buffer);

                    if (bytesRead > 31) {
                        int[] receivedState = new int[32];
                        for (int i = 0; i < 32; i++) {
                            receivedState[i] = buffer[i] & 0xFF;
                        }
                        if (receivedState[0] == 0) {
                            System.out.println("STOP!");
                        } else if (receivedState[0] == 1) {
                            actualState[1]++;
                        } else if (receivedState[0] == 2) {
                            actualState[1]--;
                        } else {
                            System.out.println("Stopping server...");
                            keepGoing = false;
                            break;
                        }
                    }
                    ui.setWidth(actualState[1]);
                    clientSocket.getOutputStream().write(new byte[]{(byte) actualState[0], (byte) actualState[1]});
                    Thread.sleep(200);
                } catch (IOException e) {
                    System.err.println("Connection reset by client: " + e.getMessage());
                    ui.setStatus("Connection reset");
                    keepGoing = false;
                    break;
                }
            }
            clientSocket.close();
            ui.setStatus("Connection closed");
            ui.serverStopped();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            ui.setStatus("Error");
            ui.serverStopped();
        }
    }

    public void stop() {
        keepGoing = false;
    }

    public int[] getActualState() {
        return actualState;
    }
}
