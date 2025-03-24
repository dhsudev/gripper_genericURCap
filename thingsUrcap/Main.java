import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.awt.event.*;

class View {
    // Instance fields for UI components and communicator
    private JLabel label;
    private final Communicator comm;
    
    // Constants for button configuration
    private static final Color BUTTON_COLOR = new Color(128, 0, 128);
    private static final Dimension BUTTON_SIZE = new Dimension(120, 50);
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 255);
    
    public View(String host, int port) {
        this.comm = new Communicator(host, port);
    }

    // Updates the label text with the current width value
    private void updateLabel() {
        label.setText(comm.getWidth() + " mm");
    }

    // Handles button actions for opening/closing
    private void handleButtons(boolean open) {
        if (open) {
            comm.setMode((byte) 1);
        } else {
            comm.setMode((byte) 2);
        }
    }

    // Creates a styled button with common settings
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(BUTTON_SIZE);
        return button;
    }
    
    // Creates the buttons panel with action listeners
    private JPanel createButtonsPanel() {
        JButton openButton = createStyledButton("Open", BUTTON_COLOR);
        JButton closeButton = createStyledButton("Close", BUTTON_COLOR);
        JButton connectButton = createStyledButton("Connect", Color.GREEN);
        
        openButton.addActionListener(e -> handleButtons(true));
        closeButton.addActionListener(e -> handleButtons(false));
        connectButton.addActionListener(e -> comm.start());
        
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
        panel.setOpaque(false);
        panel.add(openButton);
        panel.add(closeButton);
        panel.add(connectButton);

        // Timer to check if a button is still pressed
        Timer buttonPressTimer = new Timer(100, new ActionListener() {
            private boolean isButtonPressed = false;
            public void actionPerformed(ActionEvent e) {
                // Check if any button is pressed
                if (openButton.getModel().isPressed() || closeButton.getModel().isPressed()) {
                    if (!isButtonPressed) {
                        isButtonPressed = true;
                        System.out.println("A button is pressed.");
                        if(openButton.getModel().isPressed()){
                            handleButtons(true);
                        } else {
                            handleButtons(false);
                        }
                    }
                } else {
                    if (isButtonPressed) {
                        isButtonPressed = false;
                        System.out.println("No button is pressed.");
                        comm.setMode((byte) 0);
                    }
                }
            }
        });
        buttonPressTimer.start();
        return panel;
    }

    public void createUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Toolbar muy cutre :3");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 250);
            frame.setLayout(new GridBagLayout());
    
            // Create and configure the label
            label = new JLabel("Connect to the gripper!");
            label.setFont(new Font("Arial", Font.BOLD, 18));
    
            // Layout for label and buttons
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.insets = new Insets(10, 0, 20, 0);
            gbc.gridy = 0;
            frame.add(label, gbc);
    
            gbc.gridy = 1;
            frame.add(createButtonsPanel(), gbc);
    
            frame.getContentPane().setBackground(BACKGROUND_COLOR);
            frame.setVisible(true);
    
            // Start timer for the label
            Timer timer = new Timer(500, e -> updateLabel());
            timer.start();
            
        });
    }
}

public class Main {
    public static void main(String[] args) {
        View view = new View("localhost", 12345);
        view.createUI();
    }
}

