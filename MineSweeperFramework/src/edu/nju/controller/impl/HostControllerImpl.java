package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.service.HostControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.network.host.HostInHandlerImpl;
import edu.nju.network.host.HostServiceImpl;

public class HostControllerImpl implements HostControllerService{

	@Override
	public boolean serviceetupHost() {
		// TODO Auto-generated method stub
		HostServiceImpl host = new HostServiceImpl();
		HostInHandlerImpl hostH = new HostInHandlerImpl();
		GameModelImpl game = (GameModelImpl) OperationQueue.getGameModel();
		ChessBoardModelImpl chess = (ChessBoardModelImpl) OperationQueue.getChessBoardModel();
		ParameterModelImpl para = (ParameterModelImpl)chess.parameterModel;
		game.addObserver(host);
		chess.addObserver(host);
		para.addObserver(host);
		if(host.init(hostH)){
			System.out.println("Connecting!!!");
			game.startGame();
		}
		return true;
	}

}
