package com.itb.tweezer;

import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class TweezerProgramNodeContribution implements InstallationNodeContribution {
	private final KeyboardInpuntFactory keyboardFactory;
	private final TweezerProgramNodeView view;
	private DataModel model ;
	
	//toDo: implement each functio for addresing the tweezer IP addres.
	public TweezerProgramNodeContribution(DataModel model,TweezerProgramNodeView view,InstallationAPIProvider apiProvider) {
		this.keyboardFactory = apiProvider.getUserInterfaceAPI().getUserInteraction().getKeyboardInpuntFactory();
		this.model = model ;
		this.view = view ;
		}

	@Override
	public void openView() {
	}

	@Override
	public void closeView() {
	}

	@Override
	public void generateScript(ScriptWriter writer) {

	}

}
