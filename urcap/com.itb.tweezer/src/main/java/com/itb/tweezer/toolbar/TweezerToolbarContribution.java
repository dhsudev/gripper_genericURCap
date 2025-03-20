package com.itb.tweezer.toolbar;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.itb.tweezer.utils.Communicator;
import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;

public class TweezerToolbarContribution implements SwingToolbarContribution {

	// Communication
	private Communicator comm;
	private static String ip = "172.30.3.149";
	
	// URCap
	private final ToolbarContext context;
	
	// GUI
    private JLabel displayLabel; // Label to display input
	
	TweezerToolbarContribution(ToolbarContext context) {	
		this.context = context;
		this.comm = new Communicator(this.ip, 12345);
	}
	
	@Override
	public void buildUI(JPanel jPanel) {
		
		
		// Create UI
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        // Add header
        jPanel.add(createHeader("Tweezer Toolbar"));
        jPanel.add(createVerticalSpace(10));

        // Add label to display input
        jPanel.add(createWidthLabel());
        jPanel.add(createVerticalSpace(10));

        
        jPanel.add(createButtons());

	}

	private Component createWidthLabel() {
		// Get the with and display
        displayLabel = new JLabel(comm.getWidth() + " mm");
        displayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		return displayLabel;
	}
	
	// Function that send data
	private void onClickButton(Boolean isOpen) {
		if (isOpen) {
			comm.setMode((byte) 1);
		} else {
			comm.setMode((byte) 2); 
		}
		displayLabel.setText(comm.getWidth() + " mm");
	}

	private Component createHeader(String title) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel(title);
		box.add(label);
		
		return box;
	}

	private Component createButtons() {
    	JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(createButton("Open", true));
        buttonPanel.add(createVerticalSpace(10)); // Add space between buttons
        buttonPanel.add(createButton("Close", false));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return buttonPanel;
	}

	private Component createButton(String label, Boolean open) {
        JButton button = new JButton(label);

        // On click the button change mode
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	onClickButton(open);
            }
        });

        return button;
    }
	
	// For the moment I'm not using this.
	private Icon getIcon(String icon) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/icons/" + icon + ".webp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(image.getScaledInstance(25, -1, Image.SCALE_SMOOTH));
	}

	private Component createVerticalSpace(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}
	
	
	@Override
	public void openView() {
		// Open socket
		comm.start();

	}

	@Override
	public void closeView() {
		comm.setIsConnected(false);
	}
	
}

