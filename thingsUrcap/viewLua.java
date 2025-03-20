
//package com.itb.tweezer.utils;
import javax.swing.*;
import java.awt.*;

//import com.itb.tweezer.utils.*;
class View {
    private static JLabel label; // Label to display text
    Communicator comm = new Communicator("localhost", 12345);
    // Function that updates the label text
    private void updateLabel(Boolean open) {
        if (open) {
            comm.setMode((byte) 1);
        } else {
            comm.setMode((byte) 2);
        }
        label.setText(comm.getWidth() + " mm");
    }
    // Function to create a styled button
    private static JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(color); // Purple
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(120, 50));

        return button;
    }
    public void connectWithGripper(){
        comm.start();
    }
    public void createUI() {
        SwingUtilities.invokeLater(() -> {
            // Create frame
            JFrame frame = new JFrame("Toolbar muy cutre :3");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 250);
            frame.setLayout(new GridBagLayout()); // Center elements

            // Label that updates on button click
            label = new JLabel("Click a button!");
            label.setFont(new Font("Arial", Font.BOLD, 18));

            // Create buttons
            JButton open = createStyledButton("Open", new Color(128, 0, 128));
            JButton close = createStyledButton("Close", new Color(128, 0, 128));
            JButton connect = createStyledButton("connect", Color.green);

            // Add action listeners
            open.addActionListener(e -> updateLabel(true));
            close.addActionListener(e -> updateLabel(false));
            connect.addActionListener(e -> connectWithGripper());

            // Panel to hold buttons
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1, 2, 20, 0)); // Space between buttons
            panel.setOpaque(false);
            panel.add(open);
            panel.add(close);
            panel.add(connect);

            // Layout settings
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 0, 20, 0); // Margin
            frame.add(label, gbc); // Add label

            gbc.gridy = 1;
            frame.add(panel, gbc); // Add buttons

            frame.getContentPane().setBackground(new Color(240, 240, 255)); // Light background
            frame.setVisible(true);
        });
    }
}

public class Main {
    private static JLabel label; // Label to display text

    public static void main(String[] args) {
        View v = new View();
        v.createUI();
    }

    

}

