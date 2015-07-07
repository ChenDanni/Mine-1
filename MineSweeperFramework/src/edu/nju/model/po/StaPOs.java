package edu.nju.model.po;

import java.io.Serializable;
import java.util.ArrayList;

public class StaPOs implements Serializable{
	
	private ArrayList<StaPO> stas;
	
	public StaPOs(ArrayList<StaPO> stas){
		this.stas = stas;
	}
}
