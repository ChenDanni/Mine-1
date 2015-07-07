package edu.nju.model.po;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class IOoperation {
	
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
		sta = new StaPO(0, 0, "custom");
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
	
	public static StaPOs read(){
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
		return (StaPOs)ob;
	}
	public static void print(StaPOs stass){
		ArrayList<StaPO> stas = stass.stas;
		for (int i = 0; i < stas.size()	; i++) {
			StaPO sta = stas.get(i);
			System.out.println(sta.getLevel()+":"+sta.getSum());
		}
	}
	
}
