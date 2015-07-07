package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.OperationState;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.GameVO;
import edu.nju.view.RecordDialog;

public class GameModelImpl extends BaseModel implements GameModelService{
	
	private StatisticModelService statisticModel;
	private ChessBoardModelService chessBoardModel;
	
	private List<GameLevel> levelList;
	
	private GameState gameState;
	private int width;
	private int height;
	private int mineNum;
	private String level;
	
	private GameResultState gameResultStae;
	private int time;
	
	private long startTime;

	public GameModelImpl(StatisticModelService statisticModel, ChessBoardModelService chessBoardModel){
		this.statisticModel = statisticModel;
		this.chessBoardModel = chessBoardModel;
		gameState = GameState.RUN;
		
		chessBoardModel.setGameModel(this);
		
		levelList = new ArrayList<GameLevel>();
		levelList.add(new GameLevel(0,"大",30,16,99));
		levelList.add(new GameLevel(1,"中",16,16,40));
		levelList.add(new GameLevel(2,"小",9,9,10));
		
		level = "小";
	}

	@Override
	public boolean startGame() {
		// TODO Auto-generated method stub
		gameState = GameState.RUN;
		startTime = Calendar.getInstance().getTimeInMillis();
		
		GameLevel gl = null;
		for(GameLevel tempLevel : levelList){
			if(tempLevel.getName().equals(level)){
				gl = tempLevel;
				break;
			}
		}
		if(gl == null&&width==0&&height == 0)
			gl = levelList.get(2);
		
		if(gl != null){
			height = gl.getWidth();
			width = gl.getHeight();
			mineNum = gl.getMineNum();
		}
		
		this.chessBoardModel.initialize(width, height, mineNum);
		
		super.updateChange(new UpdateMessage("start",this.convertToDisplayGame()));
		return true;
	}
	
	@Override
	public boolean gameOver(GameResultState result) {
		// TODO Auto-generated method stub
		
//		OperationQueue.isRunning = false;
		
		this.gameState = GameState.OVER;
		this.gameResultStae = result;
		this.time = (int)(Calendar.getInstance().getTimeInMillis() - startTime)/1000;
		
		String levelNew;
		if (level.equals("小")) {
			levelNew = "easy";
		}else if (level.equals("中")) {
			levelNew = "hard";
		}else if (level.equals("大")) {
			levelNew = "hell";
		}else {
			levelNew = "custom";
		}
		
		String key = null;
		if (OperationQueue.operationState == OperationState.SINGLE) {
			this.statisticModel.recordStatistic(result, time, levelNew);
			if (gameResultStae == GameResultState.FAIL) {
				key = "end_Fail";
			}else {
				key = "end_Win";
			}
		}else {
			if (OperationQueue.nowOperation.isClient) {
				if (gameResultStae == GameResultState.FAIL) {
					key = "end_Client_Fail";
				}else {
					key = "end_Client_Win";
				}
			}else {
				if (gameResultStae == GameResultState.FAIL) {
					key = "end_Host_Fail";
				}else {
					key = "end_Host_Win";
				}
			}
		}
		
		super.updateChange(new UpdateMessage(key,this.convertToDisplayGame()));		
		return false;
	}

	@Override
	public boolean setGameLevel(String level) {
		// TODO Auto-generated method stub
		//输入校验
		this.level = level;
		return true;
	}

	@Override
	public boolean setGameSize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		//输入校验
		this.width = width;
		this.height = height;
		this.mineNum = mineNum;
		return true;
	}
	
	private GameVO convertToDisplayGame(){
		return new GameVO(gameState, width, height,level, gameResultStae, time);
	}

	@Override
	public List<GameLevel> getGameLevel() {
		// TODO Auto-generated method stub
		return this.levelList;
	}

}