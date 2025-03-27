package com.itb.tweezer;


import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.data.ProgramAPIProvider;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.contribution.ProgramNodeContribution;

public class TweezerProgramNodeContribution implements ProgramNodeContribution {
	private final KeyboardInpuntFactory keyboardFactory;
	private final TweezerProgramNodeView view;
	private DataModel model;

	// toDo: implement each functio for addresing the tweezer IP addres.
	public TweezerProgramNodeContribution(DataModel model, TweezerProgramNodeView view,
	ProgramAPIProvider apiProvider) {
		this.keyboardFactory = apiProvider.getUserInterfaceAPI().getUserInteraction().getKeyboardInpuntFactory();
		this.model = model;
		this.view = view;
	}
	@Override
	public boolean isDefined () {
	return "yes" ;
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

