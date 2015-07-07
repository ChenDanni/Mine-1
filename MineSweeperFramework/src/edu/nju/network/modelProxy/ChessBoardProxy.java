package edu.nju.network.modelProxy;

import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;

public class ChessBoardProxy extends ModelProxy implements ChessBoardModelService{

	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excavate(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mark(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
		
	}

}
