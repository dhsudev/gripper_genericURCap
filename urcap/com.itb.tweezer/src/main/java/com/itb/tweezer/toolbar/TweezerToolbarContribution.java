package com.itb.tweezer.toolbar;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;

public class TweezerToolbarContribution implements SwingToolbarContribution {

	private final ToolbarContext context;
	
	// GUI
	private JButton[] controlButtons = new JButton[2];
	
	TweezerToolbarContribution(ToolbarContext context) {
		this.context = context;
	}
	
	@Override
	public void buildUI(JPanel jPanel) {
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		
		jPanel.add(createHeader("Tweezer Toolbar"));
		jPanel.add(createVerticalSpace(10));
		jPanel.add(createButtons());

	}
	
	private Component createHeader(String title) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel(title);
		
		return box.add(label);
	}

	private Component createButtons() {
		JPanel buttonJPanle = new JPanel();
		GroupLayout layout = new GroupLayout(buttonJPanle);
		buttonJPanle.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JLabel openL = new JLabel("Open");
		JLabel closeL = new JLabel("Close");
		
		Component openC = createComunicationButton(controlButtons[0], openL, true);
		Component closeC = createComunicationButton(controlButtons[1], closeL, false);
		
		
		
		return null;
	}
	
	private Component createComunicationButton(JButton button, Icon icon, boolean isOpening) {
		button = new JButton(icon);
		
		return null;
	}
	
	private Icon getIcon(String icon) {
		BufferedImage image = null;
		// toDo: download icons and copy code to the suficiated
	}

	private Component createVerticalSpace(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}
	
	@Override
	public void openView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub

	}

}
