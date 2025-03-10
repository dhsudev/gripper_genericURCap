package com.itb.tweezer.toolbar;

import javax.swing.Icon;

import com.ur.urcap.api.contribution.toolbar.ToolbarConfiguration;
import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarService;
import com.ur.urcap.api.domain.data.DataModel;

import Installation.TweezerDaemonService;

public class TweezerToolbarService implements SwingToolbarService {

	private TweezerDaemonService tds;
	
	public TweezerToolbarService(TweezerDaemonService tds) {
		this.tds = tds;
	}
	
	//toDo: problems al pasar el data model
	@Override
	public SwingToolbarContribution createToolbar(ToolbarContext context) { 
		return new TweezerToolbarContribution(this.tds, context);
	}
	
	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void configureContribution(ToolbarConfiguration configuration) {
		configuration.setToolbarHeight(200);
		
	}
}
