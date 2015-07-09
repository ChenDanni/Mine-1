/*
 *
 * TODO To manage menu action
 */
package edu.nju.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.nju.controller.impl.ClientControllerImpl;
import edu.nju.controller.impl.HostControllerImpl;
import edu.nju.controller.impl.MenuControllerImpl;
import edu.nju.controller.impl.SettingControllerImpl;
import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.OperationState;
import edu.nju.controller.service.ClientControllerService;
import edu.nju.controller.service.HostControllerService;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.controller.service.SettingControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.network.client.ClientInHandlerImpl;
import edu.nju.network.client.ClientServiceImpl;
import edu.nju.network.host.HostInHandlerImpl;
import edu.nju.network.host.HostServiceImpl;
import edu.nju.network.modelProxy.ChessBoardProxy;
import edu.nju.network.modelProxy.ChessLabelProxy;
import edu.nju.network.modelProxy.GameModelProxy;
import edu.nju.view.CustomDialog;
import edu.nju.view.MainFrame;
 

public class MenuListener implements ActionListener{

	private MainFrame ui;
	MenuControllerService menuController = new MenuControllerImpl();
	SettingControllerService settingController = new SettingControllerImpl();
	ClientControllerService clientController;
	HostControllerService hostController = new HostControllerImpl();
	
	public MenuListener(MainFrame ui){
		this.ui = ui;
		clientController = new ClientControllerImpl(ui);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ui.getMenuItem("start")) {//生成游戏，默认生成简单游戏
			menuController.startGame();
		} else if (e.getSource() == ui.getMenuItem("easy")) {//生成简单游戏
			settingController.setEasyGameLevel();
			menuController.startGame();
		} else if (e.getSource() == ui.getMenuItem("hard")) {//生成中等游戏
			settingController.setHardGameLevel();
			menuController.startGame();
		} else if (e.getSource() == ui.getMenuItem("hell")) {//生成大型游戏
			settingController.setHellGameLevel();
			menuController.startGame();
		} else if (e.getSource() == ui.getMenuItem("custom")) {//生成定制游戏，需要向controller传递棋盘的高、宽和雷数
			if(OperationQueue.operationState == OperationState.CLIENT) return;
			CustomDialog customDialog = new CustomDialog(ui.getMainFrame());
			customDialog.show();
			settingController.setCustomizedGameLevel(customDialog.getHeight(), customDialog.getWidth(), customDialog.getMineNumber());
			menuController.startGame();
		} else if (e.getSource() == ui.getMenuItem("exit")) {
			System.exit(0);
		} else if (e.getSource() == ui.getMenuItem("record")) {//统计胜率信息
			ui.recordDialog.show();
		}else if(e.getSource() == ui.getMenuItem("host")){//注册成为主机
			hostController.serviceetupHost();
		}else if(e.getSource() == ui.getMenuItem("client")){//注册成为客户端
			clientController.setupClient("127.0.0.1");	
		}else if (e.getSource() == ui.getMenuItem("offline")) {
			if (OperationQueue.operationState == OperationState.CLIENT) {
				clientController.stopConnection();
			}else if (OperationQueue.operationState == OperationState.HOST) {
				hostController.stopConnection();	
			}
		}
	}


}