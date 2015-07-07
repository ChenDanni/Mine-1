package edu.nju.view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.state.DisplayBlockState;
import edu.nju.model.vo.BlockVO;
import edu.nju.view.listener.CoreListener;

@SuppressWarnings("serial")
public class MineBoardPanel extends JPanel implements Observer {
	
	static MyButton[][] jLabelButtons;
	private static int rows;//height
	private static int columns;//width
 	private CoreListener coreListener;
 	public MineBoardPanel(){
		
	}
	/*
	 * 根据高和宽新建棋盘，并且将每一个雷格监听到coreListener
	 */
	public MineBoardPanel(int rows,int columns){
  		coreListener = new CoreListener();
		MineBoardPanel.rows = rows;
		MineBoardPanel.columns = columns;
		jLabelButtons = new MyButton[rows][columns];
		this.setLayout(null);
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				jLabelButtons[i][j] = new MyButton(j,i);
				jLabelButtons[i][j].setBounds(bodyMarginOther + j * buttonSize,
						bodyMarginNorth + i * buttonSize, buttonSize,
						buttonSize);
				jLabelButtons[i][j].setIcon(Images.UNCLICKED);
				this.add(jLabelButtons[i][j]);
				jLabelButtons[i][j].addMouseListener(coreListener);//将雷格监听到coreListener
			}
		}
	}
	

	@Override
	public void update(Observable o, Object arg) { 
		//如果棋盘发生变化要体现在这边
		UpdateMessage updateMessage = (UpdateMessage) arg;
		if(updateMessage.getKey().equals("excute")){
			System.out.println("mark changed");
			List<BlockVO> changedCells = (List<BlockVO>) updateMessage.getValue();
			BlockVO displayBlock;
            for(int i=0;i<changedCells.size();i++){
            	displayBlock = changedCells.get(i);
            	DisplayBlockState state = displayBlock.getState();
            	jLabelButtons[displayBlock.getY()][displayBlock.getX()]
            			.setIcon(Images.getImageIconByState(state));
            	jLabelButtons[displayBlock.getY()][displayBlock.getX()].repaint();
            }
		}//处理客户端标记操作，有点繁。。
		else if(updateMessage.getKey().equals("excute_client")){
			System.out.println("mark changed");
			List<BlockVO> changedCells = (List<BlockVO>) updateMessage.getValue();
			BlockVO displayBlock;
            for(int i=0;i<changedCells.size();i++){
            	displayBlock = changedCells.get(i);
            	DisplayBlockState state = displayBlock.getState();
            	ImageIcon icon;
            	if (state == DisplayBlockState.FLAG) {
					icon = Images.MARKED_MINE_BLUE;
				}else {
					icon = Images.getImageIconByState(state);
				}
            	jLabelButtons[displayBlock.getY()][displayBlock.getX()]
            			.setIcon(icon);
            	jLabelButtons[displayBlock.getY()][displayBlock.getX()].repaint();
            }
		}
	}

	


	public static MyButton[][] getjLabelButtons() {
		return jLabelButtons;
	}



	public static void setjLabelButtons(MyButton[][] jLabelButtons) {
		MineBoardPanel.jLabelButtons = jLabelButtons;
	}



	public int getRows() {
		return rows;
	}



	public void setRows(int rows) {
		MineBoardPanel.rows = rows;
	}



	public int getColumns() {
		return columns;
	}



	public void setColumns(int columns) {
		MineBoardPanel.columns = columns;
	}
	
	private final int buttonSize = 16;

	private final int bodyMarginNorth = 20;

	private final int bodyMarginOther = 12;

}
