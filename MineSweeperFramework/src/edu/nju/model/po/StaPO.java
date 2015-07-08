package edu.nju.model.po;

import java.io.Serializable;


public class StaPO implements Serializable{
	
	private int sum;
	private int win;
	private double winRate;
	private String level;
	private int time;
	
	public StaPO(int sum, int win, String level, int time){
		this.sum = sum;
		this.win = win;
		this.level = level;
		this.time = time;
	}
	
	public void addSum(){
		this.sum ++;
		double temp = (double)win/sum;
		this.winRate = Double.parseDouble(String.format("%.1f", temp*100));
	}
	
	public void addWin(){
		this.win ++;
		this.sum ++;
		double temp = (double)win/sum;
		this.winRate = Double.parseDouble(String.format("%.1f", temp*100));
	}
	
	public boolean setTime(int time){
		if (time<this.time) {
			this.time = time;
			return true;
		}
		return false;
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
	public int getTime(){
		return this.time;
	}
}
