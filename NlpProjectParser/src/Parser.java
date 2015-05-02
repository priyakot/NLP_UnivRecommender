import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;


public class Parser {
 public static void main(String[] args) {
	 File file = new File("/Users/priyakotwal/Downloads/crawlnlp_DEV.txt");
	 File file2 = new File("/Users/priyakotwal/Documents/sharedUbuntu/project/data/crawlnlp_TEST.txt");
	 //File file2 = new File("/Users/priyakotwal/Desktop/544ProjectTest.txt");

		
	
	 BufferedReader reader = null;
	try {
		FileWriter fw  =  new FileWriter(file2);
		reader = new BufferedReader(new FileReader(file));
		String str = null;
		
		while((str = reader.readLine()) != null)
		 {
			JSONObject mainobject = new JSONObject(str);
           // JSONArray array = mainobject.getJSONArray("univ");
           // for (int i = 0; i < array.length(); i++) {
            //	JSONObject mainobject2 = (JSONObject) array.get(i);
            	String uniName = mainobject.getString("univname").replace(" ", "");
            	String status = mainobject.getString("stat").replace(" ", "");
            	String prg = mainobject.getString("pgm").replace(" ", "");
            	//String mjr = mainobject.getString("mjr").replace(" ", "");
            	Integer qnt = 0;
            	Integer vrb = 0;
            	if(mainobject.get("qnt")!=null||(Integer) mainobject.get("qnt")!=0|| mainobject.get("qnt")!="")
            	{
            		 qnt = mainobject.getInt("qnt");
            	}
            	if(mainobject.get("vrb")!=null||(Integer) mainobject.get("vrb")!=0|| mainobject.get("vrb")!="")
            	{
            		 vrb = mainobject.getInt("vrb");
            	}
            	/**System.out.println(uniName+"_"+status
            			+" tof:"+(String) mainobject.get("tof")
            			+" prg:"+prg
            			+" mjr:"+mjr
            			+" awa:"+(String) mainobject.get("awa")
            			+" qnt:"+(String) mainobject.get("qnt")
            			+" vrb:"+(String) mainobject.get("vrb")
            			+" grd:"+(String) mainobject.get("grd")
            			+" scl:"+(String) mainobject.get("scl"));**/
            	if(uniName!=null && (String) mainobject.get("awa")!=null && status!=null && qnt!=0 && vrb!=0)
            	{
            	String output = uniName+"_"+status
            			+" prg:"+prg
            			//+" tof:"+(String) mainobject.get("tof")
            			//+" mjr:"+mjr
            			+" awa:"+(String) mainobject.get("awa")
            			+" qnt:"+qnt
            			+" vrb:"+vrb;
            			//+" grd:"+(String) mainobject.get("grd")
            			//+" scl:"+(String) mainobject.get("scl");
            	fw.write(output+"\n");
            	fw.flush();
            	}
            	System.out.println("Done");
            }
		
		
		// }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
}
}
