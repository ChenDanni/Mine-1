package edu.nju.model.po;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Clear {
	
	static ArrayList<StaPO> stas;
	static StaPO sta;
	
	public static void clear(){
		stas = new ArrayList<StaPO>();
		sta = new StaPO(0, 0, "easy");
		stas.add(sta);
		sta = new StaPO(0, 0, "hard");
		stas.add(sta);
		sta = new StaPO(0, 0, "hell");
		stas.add(sta);
		sta = new StaPO(0, 0, "costum");
		stas.add(sta);
		
		write(new StaPOs(stas));
	}
	
	public static void write(StaPOs stas){
		FileOutputStream fout = null;
		ObjectOutputStream oout = null;
		try {
			fout = new FileOutputStream(new File("data.txt"));
			oout = new ObjectOutputStream(fout);
			
			oout.writeObject(stas);
			oout.flush();
			oout.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
