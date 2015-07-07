package edu.nju.view;

import javax.swing.JDialog;
import javax.swing.JFrame;

import edu.nju.model.po.StaPOs;


public class RecordDialogs {
	
	JFrame frame;
	JDialog dialog;
	StaPOs stas;
	
	public RecordDialogs(JFrame frame, StaPOs stas){
		this.frame = frame;
		this.stas = stas;
		init();
	}
	
	private void init(){
		dialog = new JDialog(frame, "record", true);
	}
}
