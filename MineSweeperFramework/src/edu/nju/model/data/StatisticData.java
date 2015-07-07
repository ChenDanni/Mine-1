package edu.nju.model.data;

import edu.nju.model.po.StaPOs;

/**
 * 负责进行统计数据获取和记录操作
 * @author Wangy
 *
 */
public class StatisticData {
	
	private StaPOs statistic;
	
	public StaPOs getStatistic(){
		return statistic;
	}
	
	public boolean saveStatistic(StaPOs statistic){
		this.statistic = statistic;
		return true;
	}

}