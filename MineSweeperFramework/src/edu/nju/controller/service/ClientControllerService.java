package edu.nju.controller.service;

import com.sun.j3d.utils.applet.MainFrame;

public interface ClientControllerService {
	/**
	 * 作为客户端建立网络连接。
	 * @param ip
	 * @return
	 */
	public boolean setupClient(String ip);
	public boolean stopConnection();
}
