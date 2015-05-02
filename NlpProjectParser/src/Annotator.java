package com.studentconnect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Annotator {

	public static void main(String[] args) throws Exception {

		List <String> urls = new ArrayList<String>();
		FileWriter writer = new FileWriter(new File("F:/gre_formatted.txt"));
		
		BufferedReader reader = new BufferedReader(new FileReader("F://greoutput.txt"));
		String str = null;

		while ((str = reader.readLine()) != null) {
			String strr[] = str.split(" ");

			int total = 0;
			int qnt = 0;
			int vrb = 0;
			boolean flag = false;
			String univ = "";
			String stat = "";
			for( int i = 0;i<strr.length;i++){
				String strr2[] = strr[i].split("/");
				
				if(strr2.length > 1){
					if( flag==false && strr2[1].equals("O") && isNumeric( strr2[0])  ){
						if(total == 0){
							total = Integer.parseInt( strr2[0]);
						}else if(qnt == 0){
							qnt = Integer.parseInt( strr2[0]);
						} else{
							vrb = Integer.parseInt( strr2[0]);
							flag = true;
						}
					}else{

						if ( strr2[0].equals("Accepted") )
							strr2[0] = "Admit";
						if( strr2[0].equals("Admit") ||  strr2[0].equals("Reject")){
							stat =  strr2[0];
 							JSONObject object = new JSONObject();
							object.put("univname", univ);
							object.put("qnt", qnt);
							object.put("vrb", vrb);
							object.put("stat", stat);
							System.out.println(object.toString());
							  univ = "";
							  stat = "";
						}else if (strr2[1].equals("ORGANIZATION")){
							univ = univ+" "+ strr2[0];
						}
					}
				}
			}
	 
 		}
	}

	public static boolean isNumeric(String str){
		boolean value = true;
		try{
			Integer.parseInt(str);
		}catch(Exception e){
			value = false;
		}
		return value;
	}
}
