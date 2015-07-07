package edu.nju.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.impl.UpdateMessage;

public class MineNumAlreadyLabel extends JLabel implements Observer{
	private int mines;

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		UpdateMessage updateMessage = (UpdateMessage) arg;
		String key;
		if (OperationQueue.isClient) {
			key = "mineNumC";
		}else {
			key = "mineNumH";
		}
		if(updateMessage.getKey().equals(key)){
			
			int mines = (Integer) updateMessage.getValue();
			this.setMines(mines);
			this.setText(mines+"");
		}
		
	}
	public void setMines(int mines){
		this.mines = mines;
	}
}
