package com.itb.tweezer.toolbar;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;

public class TweezerToolbarContribution implements SwingToolbarContribution {

	private final ToolbarContext context;
	
	TweezerToolbarContribution(ToolbarContext context) {
		this.context = context;
	}
	

	@Override
	public void openView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildUI(JPanel jPanel) {
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		
		jPanel.add(createHeader());
		jPanel.add(createVerticalSpace());
		jPanel.add(createButtons());

	}


	private Component createButtons() {
		// TODO Auto-generated method stub
		return null;
	}


	private Component createVerticalSpace() {
		// TODO Auto-generated method stub
		return null;
	}


	private Component createHeader() {
		// TODO Auto-generated method stub
		return null;
	}
}
