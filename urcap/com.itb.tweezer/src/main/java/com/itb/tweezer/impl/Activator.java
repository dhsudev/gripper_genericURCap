package com.itb.tweezer.impl;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.itb.tweezer.TweezerProgramNodeService;
import com.itb.tweezer.toolbar.TweezerToolbarService;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarService;

/**
 * Hello world activator for the OSGi bundle URCAPS contribution
 *
 */
public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Activator says Hello World!");
		bundleContext.registerService(SwingToolbarService.class, new TweezerToolbarService(), null);
		bundleContext.registerService(SwingProgramNodeService.class, new TweezerProgramNodeService(), null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Activator says Goodbye World!");
	}
}

