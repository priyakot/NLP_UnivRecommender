package com.studentconnect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class FeatureExtracter {

	static int count = 0;
	public static void main(String[] args) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader("F://crawlnlp.txt"));
		FileWriter writer = new FileWriter(new File("F:/crawlnlp_DEV.txt"));
		FileWriter writer2 = new FileWriter(new File("F:/crawlnlp_TEST.txt"));

		Map < Integer, Integer> quant = new HashMap<Integer, Integer>();
		Map < Integer, Integer> verbal = new HashMap<Integer, Integer>();

		List<String> univlist = new ArrayList<String>();

		populatemap(quant, verbal);

		Map < String, Integer > map = new HashMap <String,  Integer >() ;

		String str = null;

		Map<String, Integer> cntmap = new TreeMap<String, Integer>();
		
		while ((str = reader.readLine()) != null) {

			JSONObject mainobject = new JSONObject(str);
			JSONArray array = mainobject.getJSONArray("univs");

			for (int i = 0; i < array.length(); i++) {
				JSONObject mainobject2 = new JSONObject(str);
				mainobject2.remove("univs");
				JSONObject univobject = (JSONObject)array.get(i);
				String univname = ((String)univobject.get("nam")).toLowerCase();

				univname = univname.replace("-", " ").replace(",", " ").replace("  ", " ")
						.replace("   ", " ");
				mainobject2.put("univname", univname );
				String stat = (String) univobject.get("stat");
				mainobject2.put("stat", stat );
				String pgmname = (String)mainobject2.get("pgm");
				String majorname = (String)mainobject2.get("mjr");
				
				if( !cntmap.containsKey(pgmname)){
					cntmap.put(pgmname, 1);
				}else{
					int ct = cntmap.get(pgmname);
					cntmap.put(pgmname, ct+1);
				}
				if(majorname.equals("Computer Science") && pgmname.equals("MS")){
					pgmname=majorname;
					mainobject2.put("pgm",pgmname);
					count++;
				}
				if(majorname.equals("MIS") ){
					pgmname=majorname;
					mainobject2.put("pgm",pgmname);
					count++;
				}
				if(!univlist.contains(univname))
					univlist.add(univname);

				mainobject2.remove("top");
				mainobject2.remove("scl");
				mainobject2.remove("grd");
				mainobject2.remove("tof");
				//mainobject2.remove("awa");

				String strq = (String)mainobject2.get("qnt");
				int qnt = 0;
				if( strq.length()!=0)
					qnt = Integer.parseInt(strq);

				String strv = (String)mainobject2.get("vrb");

				int vrb = 0;
				if( strv.length()!=0)
					vrb = Integer.parseInt(strv);

				if( qnt > 200 ){
					if( qnt %10 != 0 )
						qnt = (qnt/10) * 10;
					qnt = getQnt(qnt, quant);
				}
				if(vrb > 270 )
					vrb = getVrb(vrb, verbal);

				mainobject2.put("qnt", qnt);
				mainobject2.put("vrb", vrb);
				int  total = qnt+ vrb;
				mainobject2.put("total", total);

				if(stat.equals("Admit")){
					String univmjr = univname + pgmname;
					if( !map.containsKey( univmjr ) )
					{
						map.put(univmjr, total);
					}else{
						int maptotal = map.get(univmjr);
						if( total < maptotal)
							map.put(univmjr, total);
					}
				}
				if(univobject.get("stat").equals("Result not available")){
					writer2.write(mainobject2.toString()+"\n");
					writer2.flush();
				}else{
					writer.write(mainobject2.toString()+"\n");
					writer.flush();
				}
			}
		}

		moredata(univlist, writer, cntmap);
		transferdata(map, writer, cntmap);
		
		for (Entry entry : cntmap.entrySet()) {
			System.out.println(entry.getKey() +"," + entry.getValue());
		}
		System.out.println( " Count : " + count);
	}

	public static void transferdata(Map < String, Integer > map, FileWriter writer, Map < String, Integer > cntmap) throws Exception{

		BufferedReader reader = new BufferedReader(new FileReader("F:/crawlnlp_TEST.txt"));

		String str =  null;
		while ((str = reader.readLine()) != null) {
			JSONObject mainobject2 = new JSONObject(str);
			String univname = ((String)mainobject2.get("univname")).toLowerCase();

			univname = univname.replace("-", " ").replace(",", " ").replace("  ", " ")
					.replace("   ", " ");
			String pgmname = (String)mainobject2.get("pgm");
			String univmjr = univname + pgmname;
			int total = (int)mainobject2.get("total");
			
 			String majorname = (String)mainobject2.get("mjr");
			
 			
 			
			if(majorname.equals("Computer Science") && pgmname.equals("MS")){
				pgmname=majorname;
				mainobject2.put("pgm",pgmname);
				count++;
			}
			if(majorname.equals("MIS") ){
				pgmname=majorname;
				mainobject2.put("pgm",pgmname);
				count++;
			}
			if( map.containsKey(univmjr) ){
				int totl = map.get(univmjr)-2;
				if( total > totl){
					String stat = (String)mainobject2.get("stat");
					if( stat.equals("Result not available"))
						mainobject2.put("stat", "Admit");
					//System.out.println(mainobject2);
					if( !cntmap.containsKey(pgmname)){
						cntmap.put(pgmname, 1);
					}else{
						int ct = cntmap.get(pgmname);
						cntmap.put(pgmname, ct+1);
					}
					writer.write(mainobject2.toString()+"\n");
					writer.flush();
				}
			} 
		}
	}
	public static void moredata(List<String> univlist, FileWriter writer, Map < String, Integer > cntmap ) throws Exception {

		Collections.sort(univlist);

		BufferedReader reader = new BufferedReader(new FileReader("F://newdata.txt"));
		String str = null;
		int cnt = 1;
		FileWriter writer2 = new FileWriter(new File("F:/newdata_formatted.txt"));

		while ((str = reader.readLine()) != null) {
			JSONObject object = new JSONObject();

			String strar[] = str.split("\t");

			String univname = strar[0].trim().toLowerCase();
			//String u1 = univname.replace("-", " ");
			//String u2 = u1.replace(",", " ");

			univname = univname.replace("-", " ").replace(",", " ").replace("  ", " ")
					.replace("   ", " "); 
			univname = normalize(univname);

			object.put("univname", univname.replace("-", " ").replace(",", " ").replace("  ", " ")
					.replace("   ", " "));
			object.put("stat", strar[1].trim());
			String pgm = getPgm( strar[3].trim() );
			object.put("pgm", pgm );
			if( !cntmap.containsKey(pgm)){
				cntmap.put(pgm, 1);
			}else{
				int ct = cntmap.get(pgm);
				cntmap.put(pgm, ct+1);
			}
			object.put("vrb", String.valueOf(Integer.parseInt(strar[4].trim())-Integer.parseInt(strar[5].trim())));
			object.put("qnt", strar[5].trim());
			//System.out.println(object);
			object.put("awa", "3");
			writer2.write(object.toString()+"\n");
			writer2.flush();

			if(univlist.contains(univname)){
				writer.write(object.toString()+"\n");
				writer .flush();
			}else{
				//System.out.println(univname);
			}
		}
	}

	public static String normalize(String str){
		if(str.equals("northeastern university boston"))
			return "northeastern university";
		if( str.equals("university of texas at dallas"))
			return "university of texas dallas";
		if( str.equals("ohio state university"))
			return "ohio state university columbus";
		if( str.equals("university of florida gainesville"))
			return "university of florida";
		return str;
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

	public static void populatemap(Map < Integer, Integer> quant  , Map < Integer, Integer> verbal){
		quant.put(780, 163);
		quant.put(760, 160);
		quant.put(750, 159);
		quant.put(740, 158);
		quant.put(730, 157);
		quant.put(720, 156);
		quant.put(710, 155);
		quant.put(700, 155);
		quant.put(690, 154);
		quant.put(680, 153);
		quant.put(670, 152);
		quant.put(660, 152);
		quant.put(650, 151);
		quant.put(640, 151);
		quant.put(630, 150);

		quant.put(620, 149);
		quant.put(610, 149);
		quant.put(600, 148);
		quant.put(590, 148);
		quant.put(580, 147);
		quant.put(570, 147);
		quant.put(560, 146);
		quant.put(550, 146);
		quant.put(540, 145);
		quant.put(530, 145);
		quant.put(520, 144);
		quant.put(510, 144);
		quant.put(500, 144);

		quant.put(480, 143);
		quant.put(490, 143);
		quant.put(470, 142);
		quant.put(460, 142);
		quant.put(450, 141);
		quant.put(440, 141);
		quant.put(430, 141);
		quant.put(420, 140);
		quant.put(410, 140);
		quant.put(400, 140);

		quant.put(380, 139);
		quant.put(390, 139);
		quant.put(370, 138);
		quant.put(360, 138);
		quant.put(350, 138);
		quant.put(330, 137);
		quant.put(340, 137);
		quant.put(320, 136);
		quant.put(310, 136);
		quant.put(300, 136);


		verbal.put(800, 170);
		verbal.put(790, 170);
		verbal.put(780, 170);
		verbal.put(770, 170);
		verbal.put(760, 170);
		verbal.put(750, 169);
		verbal.put(740, 169);
		verbal.put(730, 168);
		verbal.put(720, 168);
		verbal.put(710, 167);
		verbal.put(700, 166);
		verbal.put(690, 165);
		verbal.put(680, 165);
		verbal.put(670, 164);
		verbal.put(660, 164);
		verbal.put(650, 163);
		verbal.put(640, 162);
		verbal.put(630, 162);		
		verbal.put(620, 161);	
		verbal.put(610, 160);	
		verbal.put(600, 160);
		verbal.put(590, 159);
		verbal.put(580, 158);
		verbal.put(570, 158);
		verbal.put(560, 157);
		verbal.put(550, 156);
		verbal.put(540, 156);
		verbal.put(530, 155);
		verbal.put(520, 154);
		verbal.put(510, 154);
		verbal.put(500, 153);

		/**/
		verbal.put(490, 152);
		verbal.put(480, 152);
		verbal.put(470, 151);
		verbal.put(460, 151);
		verbal.put(450, 150);
		verbal.put(440, 149);
		verbal.put(430, 149);
		verbal.put(420, 148);
		verbal.put(410, 147);
		verbal.put(400, 146);
		verbal.put(390, 146);
		verbal.put(380, 145);
		verbal.put(370, 144);
		verbal.put(360, 143);
		verbal.put(350, 142);
		verbal.put(340, 141);
		verbal.put(330, 140);
		verbal.put(320, 139);
		verbal.put(310, 138);
		verbal.put(300, 138);

		verbal.put(290, 137);
		verbal.put(280, 136);
		verbal.put(270, 135);
		verbal.put(260, 134);
		verbal.put(250, 133);
		verbal.put(240, 132);
		verbal.put(230, 131);
		verbal.put(220, 130);
		verbal.put(210, 130);
		verbal.put(200, 130);



	}
	public static int getQnt(int qnt, Map < Integer, Integer> quant  ){
		if( quant.containsKey(qnt))
			return quant.get(qnt);

		if( qnt == 800)
			qnt = (int)( Math.random() * 4) + 166;
		else if( qnt == 790 )
			qnt = (int)( Math.random() * 2) + 164;
		else if( qnt == 770 )
			qnt = (int)( Math.random() * 2) + 161;
		else if( qnt == 700 || qnt == 710 )
			qnt =  156;
		else if( qnt == 720 )
			qnt =  156;
		return qnt;
	}
	public static int getVrb(int vrb, Map < Integer, Integer> verbal  ){
		if( verbal.containsKey(vrb))
			return verbal.get(vrb);
		return vrb;
	}
}
