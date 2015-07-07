package edu.nju.model.po;

import java.io.Serializable;


public class StaPO implements Serializable{
	
	private int sum;
	private int win;
	private double winRate;
	private String level;
	
	public StaPO(int sum, int win, String level){
		this.sum = sum;
		this.win = win;
		this.level = level;
	}
	
	public void addSum(){
		this.sum ++;
		this.winRate = win/sum;
	}
	
	public void addWin(){
		this.win ++;
		this.sum ++;
		this.winRate = win/sum;
	}
	
	public int getSum(){
		return sum;
	}
	
	public int getWin(){
		return win;
	}
	
	public String getLevel(){
		return level;
	}
	
	public double getWinRate(){
		return winRate;
	}
}
