package Installation;

import java.net.URL;

import com.ur.urcap.api.contribution.DaemonContribution;
import com.ur.urcap.api.contribution.DaemonService;

public class TweezerDaemonService implements DaemonService {

	private DaemonContribution daemonContribution;
	
	@Override
	public void init(DaemonContribution daemon) {
		this.daemonContribution = daemon;
		//toDo: trucar el porces del daemon
		
	}

	@Override
	public URL getExecutable() {
		// TODO executar el daemon
		return null;
	}
	
	public DaemonContribution getDaemonContribution() {
		return this.daemonContribution;
	}
}
