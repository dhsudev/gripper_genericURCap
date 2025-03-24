import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GripperServer implements Runnable {

    private GripperUI ui;
    // The gripper's actual state
    private int[] actualState;
    private boolean running = true;

    public GripperServer(GripperUI ui, int[] initialState) {
        this.ui = ui;
        // Copy the state so that changes here do not affect the UI’s saved state immediately.
        this.actualState = (initialState != null) ? initialState.clone() : new int[]{0, 0};
    }

    // Returns the current actual state
    public int[] getActualState() {
        return actualState.clone();
    }
    
    // Setter for actualState[1] that ensures it is not negative
    private void setGraphWidth(int width) {
        actualState[1] = (width < 0) ? 0 : width;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            ui.setStatus("Waiting for connection...");
            Socket clientSocket = serverSocket.accept();
            ui.setStatus("Connected to " + clientSocket.getInetAddress());

            clientSocket.getOutputStream().write(new byte[]{(byte) actualState[0], (byte) actualState[1]});
            Thread.sleep(200);

            while (running) {
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
                            // Increase width using setter
                            setGraphWidth(actualState[1] + 1);
                        } else if (receivedState[0] == 2) {
                            // Decrease width using setter; setter will prevent negative values
                            setGraphWidth(actualState[1] - 1);
                        } else {
                            System.out.println("Stopping server...");
                            running = false;
                            break;
                        }
                    }
                    ui.setWidth(actualState[1]);
                    clientSocket.getOutputStream().write(new byte[]{(byte) actualState[0], (byte) actualState[1]});
                    Thread.sleep(200);
                } catch (IOException e) {
                    System.err.println("Connection reset by client: " + e.getMessage());
                    ui.setStatus("Connection reset");
                    running = false;
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
        running = false;
    }
}
