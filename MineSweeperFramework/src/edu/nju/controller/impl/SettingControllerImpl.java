package edu.nju.controller.impl;

import javax.swing.JFrame;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.OperationState;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.service.SettingControllerService;
import edu.nju.view.CustomDialog;

public class SettingControllerImpl implements SettingControllerService{

	@Override
	public boolean setEasyGameLevel() {
		// TODO Auto-generated method stub
		if(OperationQueue.operationState == OperationState.CLIENT) return false;
		OperationQueue.getGameModel().setGameLevel("小");
		return true;
	}

	@Override
	public boolean setHardGameLevel() {
		// TODO Auto-generated method stub
		if(OperationQueue.operationState == OperationState.CLIENT) return false;
		OperationQueue.getGameModel().setGameLevel("中");
		return true;
	}

	@Override
	public boolean setHellGameLevel() {
		// TODO Auto-generated method stub
		if(OperationQueue.operationState == OperationState.CLIENT) return false;
		OperationQueue.getGameModel().setGameLevel("大");
		return true;
	}

	@Override
	public boolean setCustomizedGameLevel(int height, int width, int nums) {
		// TODO Auto-generated method stub
		if(OperationQueue.operationState == OperationState.CLIENT) return false;
		OperationQueue.getGameModel().setGameLevel("一般");
		OperationQueue.getGameModel().setGameSize(width, height, nums);
		return true;
	}

}
