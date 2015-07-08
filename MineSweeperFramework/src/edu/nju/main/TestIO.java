package edu.nju.main;

import edu.nju.model.po.IOoperation;

public class TestIO {
	public static void main(String [] args){
		IOoperation.clear();
		double temp = (double)1/7;
		System.out.println(Double.parseDouble(String.format("%.3f", temp)));
	}
}
