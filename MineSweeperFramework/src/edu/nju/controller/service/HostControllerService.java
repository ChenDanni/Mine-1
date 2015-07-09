package edu.nju.controller.service;

import edu.nju.view.MainFrame;

public interface HostControllerService {
	/**
	 * 作为主机建立网络连接
	 * @return
	 */
	public boolean serviceetupHost();
	public boolean stopConnection(MainFrame ui);
}
