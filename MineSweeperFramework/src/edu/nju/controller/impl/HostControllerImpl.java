package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.OperationState;
import edu.nju.controller.service.HostControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.network.host.HostInHandlerImpl;
import edu.nju.network.host.HostServiceImpl;

public class HostControllerImpl implements HostControllerService{

	HostServiceImpl host;
	HostInHandlerImpl hostH;
	GameModelImpl game;
	ChessBoardModelImpl chess;
	ParameterModelImpl para;
	
	@Override
	public boolean serviceetupHost() {
		// TODO Auto-generated method stub
		host = new HostServiceImpl();
		hostH = new HostInHandlerImpl();
		game = (GameModelImpl) OperationQueue.getGameModel();
		chess = (ChessBoardModelImpl) OperationQueue.getChessBoardModel();
		para = (ParameterModelImpl)chess.parameterModel;
		
		OperationQueue.operationState = OperationState.HOST;
		
		game.addObserver(host);
		chess.addObserver(host);
		para.addObserver(host);
		if(host.init(hostH)){
			System.out.println("Connecting!!!");
			game.startGame();
		}
		return true;
	}
	
	public boolean stopConnection(){
		host.close();
		host = null;
		game.deleteObserver(host);
		chess.deleteObserver(host);
		para.deleteObserver(host);
		hostH = null;
		OperationQueue.operationState = OperationState.SINGLE;
		OperationQueue.getGameModel().startGame();
		return true;
	}

}
