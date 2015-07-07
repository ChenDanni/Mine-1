package edu.nju.controller.msgqueue.operation;

import java.io.Serializable;

public abstract class MineOperation implements Serializable{
	//用于网络不同标记，区分是否为客户端操作
	public boolean isClient = false;
	
	public abstract void execute();
}
