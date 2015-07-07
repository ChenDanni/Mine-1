package edu.nju.model.impl;

import java.util.ArrayList;

import edu.nju.model.data.StatisticData;
import edu.nju.model.po.IOoperation;
import edu.nju.model.po.StaPO;
import edu.nju.model.po.StaPOs;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;

public class StatisticModelImpl extends BaseModel implements StatisticModelService{
	
	public StatisticData statisticDao;
	
	public StatisticModelImpl(){
		//初始化Dao
		statisticDao = new StatisticData();
		
		//读文件
		StaPOs ob = IOoperation.read();
		statisticDao.saveStatistic(ob);
		
	}

	@Override
	public void recordStatistic(GameResultState result, int time, String level) {
		// TODO Auto-generated method stub
		
		StaPOs stas = statisticDao.getStatistic();
		ArrayList<StaPO> staList = stas.stas;
		
		//更新数据
		for (int i = 0; i < staList.size(); i++) {
			StaPO sta = staList.get(i);
			if (sta.getLevel().equals(level)) {
				if (result == GameResultState.SUCCESS) {
					sta.addWin();
				}else {
					sta.addSum();
				}
			}
		}
		IOoperation.print(statisticDao.getStatistic());
		
		//写入文件
		IOoperation.write(stas);
		
	}

	@Override
	public void showStatistics() {
		// TODO Auto-generated method stub
		statisticDao.saveStatistic(IOoperation.read());
	}
	

}
