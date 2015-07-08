package edu.nju.model.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ParameterModelService;

public class ParameterModelImpl extends BaseModel implements ParameterModelService{
	
	private int maxMine;
	public int mineNum;
	public int hostNum;
	public int clientNum;

	@Override
	public boolean setMineNum(int num) {
		// TODO Auto-generated method stub
		mineNum = num;
		maxMine = num;
		
		hostNum = 0;
		clientNum = 0;
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		super.updateChange(new UpdateMessage("mineNumH", 0));
		super.updateChange(new UpdateMessage("mineNumC", 0));
		return true;
	}

	@Override
	public boolean addMineNum() {
		// TODO Auto-generated method stub
		mineNum++;
		int send = 0;
		String key;
		
		if(mineNum>maxMine){
			mineNum--;
			return false;
		}
		
		if (OperationQueue.nowOperation.isClient) {
			clientNum--;
			send = clientNum;
			key = "mineNumC";
		}else {
			hostNum--;
			send = hostNum;
			key = "mineNumH";
		}
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		super.updateChange(new UpdateMessage(key, send));
		return true;
	}

	@Override
	public boolean minusMineNum() {
		// TODO Auto-generated method stub
		mineNum--;
		int send = 0;
		String key;
		
		if(mineNum<0){
			mineNum++;
			return false;
		}
		
		if (OperationQueue.nowOperation.isClient) {
			clientNum++;
			send = clientNum;
			key = "mineNumC";
		}else {
			hostNum++;
			send = hostNum;
			key = "mineNumH";
		}
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		super.updateChange(new UpdateMessage(key, send));
		return true;
	}

}
