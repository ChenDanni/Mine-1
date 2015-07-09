package edu.nju.controller.impl;


import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.OperationState;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.service.ClientControllerService;
import edu.nju.network.client.ClientInHandlerImpl;
import edu.nju.network.client.ClientServiceImpl;
import edu.nju.network.modelProxy.ChessBoardProxy;
import edu.nju.network.modelProxy.ChessLabelProxy;
import edu.nju.network.modelProxy.GameModelProxy;
import edu.nju.view.MainFrame;

public class ClientControllerImpl implements ClientControllerService{
	
	private MainFrame ui;
	ClientServiceImpl client;
	ClientInHandlerImpl clientH;
	
	public ClientControllerImpl(edu.nju.view.MainFrame ui){
		this.ui = ui;
	}

	@Override
	public boolean setupClient(String ip) {
		// TODO Auto-generated method stub
		client = new ClientServiceImpl();
		clientH = new ClientInHandlerImpl();
		
		GameModelProxy gameProxy = new GameModelProxy(client);
		ChessBoardProxy chessProxy = new ChessBoardProxy();
		ChessLabelProxy chessLabelProxy = new ChessLabelProxy();
					
		OperationQueue.net = client;
		OperationQueue.operationState = OperationState.CLIENT;
		
		clientH.addObserver(gameProxy);
		clientH.addObserver(chessProxy);
		clientH.addObserver(chessLabelProxy);
		
		gameProxy.addObserver(ui);
		chessProxy.addObserver(ui.getMineBoard());
		chessLabelProxy.addObserver(ui.getMineNumberLabel());
		chessLabelProxy.addObserver(ui.mineNum);
		
		client.init(ip, clientH);
		return true;
	}
	
	public boolean stopConnection(){
		client.close();
		client = null;
		clientH.deleteObservers();
		clientH = null;
		OperationQueue.operationState = OperationState.SINGLE;
		OperationQueue.getGameModel().startGame();
		return true;
	}

}
