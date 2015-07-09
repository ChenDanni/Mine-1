package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.OperationState;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.controller.service.HostControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.model.impl.StatisticModelImpl;
import edu.nju.network.host.HostInHandlerImpl;
import edu.nju.network.host.HostServiceImpl;
import edu.nju.view.MainFrame;
import edu.nju.view.RecordDialogs;

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
		OperationQueue.host = this;
		
		game.addObserver(host);
		chess.addObserver(host);
		para.addObserver(host);
		if(host.init(hostH)){
			System.out.println("Connecting!!!");
			game.startGame();
		}
		return true;
	}
	
	public boolean stopConnection(MainFrame ui){
		host.close();
		host = null;
		game.deleteObserver(host);
		chess.deleteObserver(host);
		para.deleteObserver(host);	
		
		StatisticModelImpl statisticModel = new StatisticModelImpl();
 		ParameterModelImpl mineNumberModel = new ParameterModelImpl();
 		ChessBoardModelImpl mineBoardModel = new ChessBoardModelImpl(mineNumberModel);
		GameModelImpl gameModel = new GameModelImpl(statisticModel,mineBoardModel);		
 		
		gameModel.addObserver(ui);
 		mineNumberModel.addObserver(ui.getMineNumberLabel());
 		//已标雷数
 		mineNumberModel.addObserver(ui.mineNum);
 		mineBoardModel.addObserver(ui.getMineBoard());
 		//记录数据用
 		ui.recordDialog = new RecordDialogs(ui.getMainFrame(), statisticModel.statisticDao.getStatistic(), statisticModel);
 		
 		OperationQueue.gameModel = gameModel;
 		OperationQueue.chessBoard = mineBoardModel;
 		
		hostH = null;
		OperationQueue.operationState = OperationState.SINGLE;
		OperationQueue.addMineOperation(new StartGameOperation());
		return true;
	}

}
