package edu.nju.model.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import edu.nju.model.data.StatisticData;
import edu.nju.model.po.StaPOs;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;

public class StatisticModelImpl extends BaseModel implements StatisticModelService{
	
	public StatisticData statisticDao;
	
	public StatisticModelImpl(){
		//初始化Dao
		statisticDao = new StatisticData();
		
		//读文件
		FileInputStream fin = null;
		ObjectInputStream oin = null;
		
		try {
			fin = new FileInputStream(new File("data.txt"));
			oin = new ObjectInputStream(fin);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object ob = null;
		try {
			ob = oin.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if(ob != null){
			statisticDao.saveStatistic((StaPOs) ob);
			System.out.println("sucess!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}else {
			System.out.println("not found-----------------------------");
		}
		
	}

	@Override
	public void recordStatistic(GameResultState result, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showStatistics() {
		// TODO Auto-generated method stub
		
	}

}
