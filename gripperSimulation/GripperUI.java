import javax.swing.*;
import java.awt.*;

public class GripperUI {

    private JFrame frame;
    private JLabel statusLabel;
    private JLabel widthLabel;
    private GripperPanel gripperPanel;
    private JButton startButton;
    private JButton stopButton;
    private GripperServer server;
    private Thread serverThread;
    // Persist the actual state even when the server stops.
    private int[] actualState = new int[]{0, 0};
    
    public GripperUI() {
        frame = new JFrame("Gripper Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        statusLabel = new JLabel("Status: Waiting for connection");
        widthLabel = new JLabel("Graph Width: -");
        topPanel.add(statusLabel);
        topPanel.add(widthLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        gripperPanel = new GripperPanel(actualState);
        gripperPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.add(gripperPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start Server");
        stopButton = new JButton("Stop Server");
        stopButton.setEnabled(false);
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startServer());
        stopButton.addActionListener(e -> stopServer());

        frame.setVisible(true);
    }

    private void startServer() {
        // Pass the persisted actualState to the new server
        server = new GripperServer(this, actualState);
        serverThread = new Thread(server);
        serverThread.setDaemon(true);
        serverThread.start();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        setStatus("Running...");
    }

    public void serverStopped() {
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private void stopServer() {
        if (server != null) {
            // Update the persisted state from the server before stopping
            actualState = server.getActualState();
            server.stop();
        }
    }

    public void setStatus(String status) {
        statusLabel.setText("Status: " + status);
    }

    public void setWidth(int width) {
        widthLabel.setText("Graph Width: " + width);
        // Optionally update our persisted state if the width matters.
        gripperPanel.setActualState(server.getActualState());
        gripperPanel.repaint();
    }
}
