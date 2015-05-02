package com.studentconnect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.JSONObject;

public class SpecialDataFormatter {

	public static void main(String[] args) throws Exception {
		
		System.out.println(" testting");
		
		BufferedReader reader = new BufferedReader(new FileReader("F://newdata.txt"));
		String str = null;
		int cnt = 1;
		FileWriter writer2 = new FileWriter(new File("F:/newdata_formatted.txt"));

		while ((str = reader.readLine()) != null) {
			JSONObject object = new JSONObject();
			
			String strar[] = str.split("\t");
			
			object.put("univname", strar[0].trim());
			object.put("stat", strar[1].trim());
			String pgm = getPgm( strar[3].trim() );
			object.put("pgm", pgm );
			object.put("vrb", String.valueOf(Integer.parseInt(strar[4].trim())-Integer.parseInt(strar[5].trim())));
			object.put("qnt", strar[5].trim());
			System.out.println(object);
			writer2.write(object.toString()+"\n");
			writer2.flush();
		}
	}
	
	public static String getPgm(String pgm){
		
		if( pgm.contains("Mechanical") || pgm.contains("mechanical") 
				|| pgm.contains("Mechanics") || pgm.contains("mech"))
			return "Mechanical Engineering";

		if( pgm.contains("Biomedical"))
			return "Biomedical Engineering";

		if( pgm.contains("Civil") || pgm.contains("civil"))
			return "Civil Engineering";
		
		if( pgm.contains("Industrial"))
			return "Industrial Engineering";
		if( pgm.contains("ee") || pgm.contains("EE") || pgm.contains("Embedded") || pgm.contains("electrical") || pgm.contains("ECE") || pgm.contains("Electrical"))
			return "Electrical Engineering";
		if( pgm.contains("cs") || pgm.contains("CS") || pgm.contains("ce") ||  pgm.contains("CE") || pgm.contains("cse")
				|| pgm.contains("CIS") || pgm.contains("IT") || pgm.contains("Computer")|| pgm.contains("computer")
				|| pgm.contains("Software"))
			return "Computer Science";
		
		return pgm;
	}
}
