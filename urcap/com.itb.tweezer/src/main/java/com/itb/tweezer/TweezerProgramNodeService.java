package com.itb.tweezer;

import java.io.InputStream;
import java.util.Locale;

import javax.swing.text.Style;

import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;

public class TweezerProgramNodeService
		implements SwingProgramNodeService<TweezerProgramNodeContribution, TweezerProgramNodeView> {

	public TweezerProgramNodeService() {
	
	}

	@Override
	public String getId() {
		return "TweezerSwingNode";
	}

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		configuration.setChildrenAllowed(true);
	}

	@Override
	public String getTitle() {
		return "Tweezer Manager";
	}

	@Override
	public TweezerProgramNodeView createView(ViewAPIProvider apiProvider) {
		SystemAPI systemAPI = apiProvider.getSystemAPI();
		Style style = systemAPI.getSoftwareVersion().getMajorVersion() >= 5 ? new V5Style() : new V3Style();
		return new TweezerProgramNodeView(style);
	}

	@Override
	public TweezerProgramNodeContribution createNode(
			ProgramAPIProvider apiProvider,
			TweezerProgramNodeView view,
			DataModel model,
			CreationContext context) {
		return new TweezerProgramNodeContribution(apiProvider, view, model);
	}
}
