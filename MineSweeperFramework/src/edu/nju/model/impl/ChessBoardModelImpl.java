package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.List;

import com.sun.java.swing.plaf.windows.resources.windows;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.po.BlockPO;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.ParameterModelService;
import edu.nju.model.state.BlockState;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;

public class ChessBoardModelImpl extends BaseModel implements ChessBoardModelService{
	
	private GameModelService gameModel;
	public ParameterModelService parameterModel;
	
	private BlockPO[][] blockMatrix;

	//用于结束游戏时锁住棋盘。。
	private boolean locked;
	//棋盘雷数
	private int mineNum = 0;
	
	public ChessBoardModelImpl(ParameterModelService parameterModel){
		this.parameterModel = parameterModel;
	}

	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		/********************简单示例初始化方法，待完善********************/
		blockMatrix = new BlockPO[width][height];
		setBlock(mineNum);
		this.mineNum = mineNum;
		
		locked = false;
		
		this.parameterModel.setMineNum(mineNum);
		/***********请在删除上述内容的情况下，完成自己的内容****************/
		
		this.printBlockMatrix();
		
		return false;
	}

	@Override
	public boolean excavate(int x, int y) {
		// TODO Auto-generated method stub
		/********************简单示例挖开方法，待完善********************/
		if(blockMatrix == null)
			return false;
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		List<BlockPO> queue = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		
		if(block.getState() == BlockState.FLAG) return false;
		if(locked) return false;
		
		block.setState(BlockState.CLICK);
		blocks.add(block);
		
		GameState gameState = GameState.RUN;
		if(block.isMine()){
			gameState = GameState.OVER;
			this.gameModel.gameOver(GameResultState.FAIL);
			locked = true;
			this.chessBoardOver(blocks);
		}else {
			int head = 0;
			int rear = -1;
			int[] dx = {-1,-1,-1,0,0,1,1,1};
			int[] dy = {-1,0,1,-1,1,-1,0,1};
			boolean[][] inqueue = new boolean[blockMatrix.length][blockMatrix[0].length];
			for (int i = 0; i < inqueue.length; i++) {
				for (int j = 0; j < inqueue[i].length; j++) {
					inqueue[i][j] = false;
				}
			}
			if(block.getMineNum() == 0) {
				queue.add(block);
				//blocks.add(block);
				inqueue[block.getX()][block.getY()] = true;
				rear ++;
			}
			while(head <= rear){
				BlockPO headblock = queue.get(head);
				for (int i = 0; i < dx.length; i++) {
					if (headblock.getX()+dx[i]<0 ||
							headblock.getX()+dx[i]>=blockMatrix.length||
							headblock.getY()+dy[i]<0||
							headblock.getY()+dy[i]>=blockMatrix[0].length) {
						continue;
					}
					BlockPO temp = blockMatrix[headblock.getX()+dx[i]][headblock.getY()+dy[i]];
					if (!temp.isMine() && temp.getState()!=BlockState.FLAG) {
						temp.setState(BlockState.CLICK);
						blocks.add(temp);
						if (temp.getMineNum() == 0 && !inqueue[temp.getX()][temp.getY()]) {
							queue.add(temp);
							inqueue[temp.getX()][temp.getY()] = true;
							rear ++;
						}
					}
				}
//				System.out.println("rear:"+rear);
				head ++;
			}
		}
		
		if (checkWin(blocks)) {
			gameState = GameState.OVER;
			this.gameModel.gameOver(GameResultState.SUCCESS);
		}
		
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));
		/***********请在删除上述内容的情况下，完成自己的内容****************/
		return true;
	}
	
	@Override
	public boolean mark(int x, int y) {
		// TODO Auto-generated method stub
		/********************简单示例标记方法，待完善********************/
		if(blockMatrix == null)
			return false;
		if(locked) return false;
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		 
		BlockState state = block.getState();
		if(state == BlockState.UNCLICK){
			if(this.parameterModel.minusMineNum())
				block.setState(BlockState.FLAG);
			//this.parameterModel.minusMineNum();
		}
		else if(state == BlockState.FLAG){
			block.setState(BlockState.UNCLICK);
			this.parameterModel.addMineNum();
		}
		
		blocks.add(block);	
		String op;
		//区分客户端和主机的标记操作
		if (OperationQueue.nowOperation.isClient) {
			op = "excute_client";
		}else {
			op = "excute";
		}
		super.updateChange(new UpdateMessage(op,this.getDisplayList(blocks, GameState.RUN)));
		/***********请在删除上述内容的情况下，完成自己的内容****************/
		
		return true;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		// TODO Auto-generated method stub
		/***********请在此处完成快速挖开方法实现****************/
		if(blockMatrix == null)
			return false;
		if(locked) return false;
		
		BlockPO block = blockMatrix[x][y];
		
		if(block.getState() != BlockState.CLICK) return false;

		int[] dx = {-1,-1,-1,0,0,1,1,1};
		int[] dy = {-1,0,1,-1,1,-1,0,1};
		
		int num = 0;
		
		//统计标记数量
		for (int i = 0; i < dy.length; i++) {
			if(block.getX()+dx[i]>=blockMatrix.length ||
					block.getX()+dx[i]<0 ||
					block.getY()+dy[i]>=blockMatrix[0].length ||
					block.getY()+dy[i]<0)
			continue;
			BlockPO temp = blockMatrix[block.getX()+dx[i]][block.getY()+dy[i]];
			if (temp.getState() == BlockState.FLAG){
				num ++;
			}
		}
		
		//若标记等于当前数值,挖
		boolean isMine = false;
		if (num == block.getMineNum()) {
			for (int i = 0; i < dy.length; i++) {
				if(block.getX()+dx[i]>=blockMatrix.length ||
						block.getX()+dx[i]<0 ||
						block.getY()+dy[i]>=blockMatrix[0].length ||
						block.getY()+dy[i]<0)
				continue;
				BlockPO temp = blockMatrix[block.getX()+dx[i]][block.getY()+dy[i]];
				if (temp.getState() == BlockState.UNCLICK) {
					locked = false;
					excavate(temp.getX(), temp.getY());
					if (locked) {
						isMine = true;
					}
				}
			}
		}
		if (isMine) {
			locked = true;
		}
		
		return true;
	}

	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 初始化BlockMatrix中的Block，并随机设置mineNum颗雷
	 * 同时可以为每个Block设定附近的雷数
	 * @param mineNum
	 * @return
	 */
	private boolean setBlock(int mineNum){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		//int index = 0;
 		
		//初始化及布雷
		for(int i = 0 ; i<width; i++){
			for (int j = 0 ; j< height; j++){
				blockMatrix[i][j] = new BlockPO(i,j);
				//放置雷，并设定block附近雷数，现有放置方法为固定方法，请添加随机实现
				/*
				index ++;
				if(index == 2){
					if(mineNum>0){
						if(i>3&&j>3){
							blockMatrix[i-1][j-1].setMine(true);
						
							addMineNum(i-1,j-1);
							mineNum--;
						}
					}
					index = 0;
				}
				*/
				
			}
		}

		//随机出雷的位置
		int[] randomX = new int[mineNum];
		int[] randomY = new int[mineNum];
		for (int i = 0; i < mineNum; i++) {
			randomX[i] = (int)(Math.random()*width);
			randomY[i] = (int)(Math.random()*height);
			
			boolean existed = false;
			for (int j = 0; j < i; j++) {
				if (randomX[j] == randomX[i] && randomY[j] == randomY[i]) {
					existed = true;
				}
			}
			
			if (existed) {
				i--;
				continue;
			}else {
				blockMatrix[randomX[i]][randomY[i]].setMine(true);
				addMineNum(randomX[i], randomY[i]);
			}
		}
		
		return false;
	}
	
	
	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 将(i,j)位置附近的Block雷数加1
	 * @param i
	 * @param j
	 */
	private void addMineNum(int i, int j){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int tempI = i-1;		
		
		for(;tempI<=i+1;tempI++){
			int tempJ = j-1;
			for(;tempJ<=j+1;tempJ++){
				if((tempI>-1&&tempI<width)&&(tempJ>-1&&tempJ<height)){
//					System.out.println(i+";"+j+":"+tempI+";"+tempJ+":");
					blockMatrix[tempI][tempJ].addMine();
				}
			}
		}
		
	}
	
	/**
	 * 将逻辑对象转化为显示对象
	 * @param blocks
	 * @param gameState
	 * @return
	 */
	private List<BlockVO> getDisplayList(List<BlockPO> blocks, GameState gameState){
		List<BlockVO> result = new ArrayList<BlockVO>();
		for(BlockPO block : blocks){
			if(block != null){
				BlockVO displayBlock = block.getDisplayBlock(gameState);
				if(displayBlock.getState() != null)
				result.add(displayBlock);
			}
		}
		return result;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
		this.gameModel = gameModel;
	}
	
	private void printBlockMatrix(){
		for(BlockPO[] blocks : this.blockMatrix){
			for(BlockPO b :blocks){
				String p = b.getMineNum()+"";
				if(b.isMine())
					p="*";
				System.out.print(p);
			}
			System.out.println();
		}
	}
	
	private void chessBoardOver(List<BlockPO> blocks){
		for (int i = 0; i < blockMatrix.length; i++) {
			for (int j = 0; j < blockMatrix[0].length; j++) {
				BlockPO temp = blockMatrix[i][j];
				if (temp.getState() == BlockState.FLAG || temp.isMine()) {
					blocks.add(temp);
				}
			}
		}
		//super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, GameState.OVER)));
	}
	
	private boolean checkWin(List<BlockPO> blocks){
		for (int i = 0; i < blockMatrix.length; i++) {
			for (int j = 0; j < blockMatrix[0].length; j++) {
				BlockPO block = blockMatrix[i][j];
				if (block.getState() == BlockState.UNCLICK && !block.isMine()) {
					return false;
				}else if (block.getState() == BlockState.UNCLICK && block.isMine()) {
					blocks.add(block);
				}
			}
		}
		
		return true;
	}
}
