package com.itb.tweezer.toolbar;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.ur.urcap.api.contribution.toolbar.ToolbarConfiguration;
import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarService;
import com.ur.urcap.api.domain.data.DataModel;

public class TweezerToolbarService implements SwingToolbarService {
	
	@Override
	public SwingToolbarContribution createToolbar(ToolbarContext context) { 
		return new TweezerToolbarContribution(context);
	}
	
	@Override
	public Icon getIcon() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/icons/tweezerIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	}

	@Override
	public void configureContribution(ToolbarConfiguration configuration) {
		configuration.setToolbarHeight(200);
		
	}
}
