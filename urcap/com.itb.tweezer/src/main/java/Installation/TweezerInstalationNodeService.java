package Installation;

import java.io.InputStream;

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.InstallationNodeService;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;

public class TweezerInstalationNodeService implements InstallationNodeService{
	
	public TweezerInstalationNodeService() {
	
	}
	
	@Override
	public InstallationNodeContribution createInstallationNode(URCapAPI api, DataModel model) {
		return new TweezerInstallationNodeContribution(model);
	}
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getHTML() {
		// TODO Auto-generated method stub
		return null;
	}

}
