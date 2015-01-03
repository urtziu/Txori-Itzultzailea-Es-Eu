import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.auth.AccessToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Laguntzaileak {
	public static String TestuOna;
	public static  QueryResult bilaketa(String Bilak_testua, int metodoa) throws TwitterException{
		
		Twitter twitter = new TwitterFactory().getInstance();
		//tokena
		twitter.setOAuthConsumer("l7oMMgZ9UPuPRiZ5Cw1ncg", "QwEH95p9SpApZUnW4xOdFVbuYdjaDBeuDDiJ7TAzGEk");//consumer key, comsumer secret
		twitter.setOAuthAccessToken(new AccessToken("95424083-X6DrhPTB9bsTeNY9zhBeO0hBj7GLuT4McKuE7ptqs","bGIiuRzFIF2eeMQghPQgZ2tIXwDmmU0fLiXmyfN0aSko4"));
		
		
		//System.out.println(metodoa);
		if(metodoa==1){
			/*List<Status> statusList = twitter.getUserTimeline("sanjeevpk");
			for (Status status : statusList) {
				System.out.println(status.toString());

			}*/
			Query query2 = new Query("from:"+Bilak_testua);
			QueryResult result = twitter.search(query2);
			return result;
		}else{
			if(metodoa==4){
				Bilak_testua= "@"+Bilak_testua;
			}
			if(metodoa==2){
				Bilak_testua= "#"+Bilak_testua;
			}
			if(metodoa==3){
				Bilak_testua= Bilak_testua;
			}
			
			
			Query query = new Query(Bilak_testua);
			
		    QueryResult result = twitter.search(query);

		    //System.out.println("Bilaketak datoz hemen.");
		    return result;
		}
		
	    
		
    }
	public static String informazioa(java.util.List<Status> Estaduak,Integer indizea){
		User erab = Estaduak.get(indizea).getUser();
		Status est = Estaduak.get(indizea);
		String rt="Ez.";
		String rtorigin="---";
		String Erabiltzailea = "Erabiltzaileari buruz: \nEzizena: @"+erab.getScreenName()+ "\nBenetako izena: "+erab.getName()+"\nDeskripzioa: " + erab.getDescription() +"\n";
		String Tweeta = "---------\nTxoriari buruz: \nTestua: "+est.getText()+"\nEguna eta ordua:"+ est.getCreatedAt()+ "\n";
		
		
		if (est.isRetweet()){
			rt="Bai.";
			rtorigin =est.getRetweetedStatus().getText();
		}
		String Extrak = "---------\nExtrak: \nRetweet bat al da? "+rt+"\nRetweeta bada, testu originala: "+rtorigin+"\n";
		return Erabiltzailea + Tweeta + Extrak;
	}
	public static String kendutildeak(String input) {
	    // Cadena de caracteres original a sustituir.
	    String original = "��������������u�������������������";
	    // Cadena de caracteres ASCII que reemplazar�n los originales.
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    String output = input;
	    for (int i=0; i<original.length(); i++) {
	        // Reemplazamos los caracteres especiales.
	        output = output.replace(original.charAt(i), ascii.charAt(i));
	    }
	    return output;
	}
	
	


	public static ArrayList<String>  RegexeFindText(String est,String pre) {
		   
			ArrayList<String> emaitza= new ArrayList<String>();	
			
		
	      // Input for matching the regexe pattern
	      String input = est;
	      // Regexe to be matched
	      String regexe = pre;
	   
	      // Step 1: Allocate a Pattern object to compile a regexe
	      Pattern pattern = Pattern.compile(regexe);
	      //Pattern pattern = Pattern.compile(regexe, Pattern.CASE_INSENSITIVE);  // case-insensitive matching
	   
	      // Step 2: Allocate a Matcher object from the compiled regexe pattern,
	      //         and provide the input to the Matcher
	      Matcher matcher = pattern.matcher(input);
	   
	      // Step 3: Perform the matching and process the matching result
	   
	      // Use method find()
	      while (matcher.find()) {     // find the next match
	         //System.out.println("find() found the pattern \"" + matcher.group()
	            //   + "\" starting at index " + matcher.start()
	              // + " and ending at index " + matcher.end());
	         emaitza.add(matcher.group());
	        
	      }
	   /*
	      // Use method matches()
	      if (matcher.matches()) {
	         System.out.println("matches() found the pattern \"" + matcher.group()
	               + "\" starting at index " + matcher.start()
	               + " and ending at index " + matcher.end());
	      } else {
	         System.out.println("matches() found nothing");
	      }
	   
	      // Use method lookingAt()
	      if (matcher.lookingAt()) {
	         System.out.println("lookingAt() found the pattern \"" + matcher.group()
	               + "\" starting at index " + matcher.start()
	               + " and ending at index " + matcher.end());
	      } else {
	         System.out.println("lookingAt() found nothing");
	      }
	      */
	      return emaitza;
	   }
	
	public static int countWords(String s){

	    int wordCount = 0;

	    boolean word = false;
	    int endOfLine = s.length() - 1;

	    for (int i = 0; i < s.length(); i++) {
	        // if the char is a letter, word = true.
	        if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
	            word = true;
	            // if char isn't a letter and there have been letters before,
	            // counter goes up.
	        } else if (!Character.isLetter(s.charAt(i)) && word) {
	            wordCount++;
	            word = false;
	            // last word of String; if it doesn't end with a non letter, it
	            // wouldn't count without this.
	        } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
	            wordCount++;
	        }
	    }
	    return wordCount;
	}
	public static String proposamenak(String testua){
		
		
		String originala=testua;
	
    	
    	String[] hitzak = testua.split("\\s+|,\\s*|\\.\\s*");
		for (String ss: hitzak){
			if (ss.matches("")){
				
			}else{
				//System.out.println(ss +" hitza ondo dago? " + forinf.CheckSpell(ss));
				Boolean momentukoa = forinf.CheckSpell(ss);
				if (momentukoa ==true){
					
				}else{
					List<?> proposamena = forinf.getSuggestions(ss, 1);
					String test=ss;
					if (proposamena.isEmpty()){
						
					}else{
						test= proposamena.get(0).toString();
					}
					String KodetuTest = null; 
					byte[] utf8BytesStream;
					try {
						utf8BytesStream = test.getBytes("UTF8");
						 KodetuTest = new String(utf8BytesStream, "UTF8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println("Proposamenak: "+KodetuTest.toString());
					//System.out.println("Proposamenak: "+proposamena.toString());
					
					//ordezkatu testu originalean gaizki dauden hitzak, aurreneko proposamenarekin:
					//TESTU ONA HEMEN DAGO, ITZULI BEHAR DENA ----> ORIGINALA aldagaian.
					if (proposamena.isEmpty()==false){
						
						if (proposamena.get(0).toString().equals("h")){
							
							
						}else{
							
							originala=originala.replace(ss,proposamena.get(0).toString());
						}
						
						//System.out.println("Aldaketa egin da: "+ss+" hitza, honegatik -> "+proposamena.get(0).toString());
						//emaitza.addTest(originala);
						
					}
					
					
				}
			}
		}
		return originala;
	}
	public static boolean InfAlaFor (String originala)
	{
		//Testua emaitza =null;
		//emaitza.addTest(originala);
		String word=originala;
		//kendu karaktere arraroak.
		word=word.replace(".", "");
    	word=word.replace("..", "");
    	word=word.replace("...", "");
    	word=word.replace(":", "");
    	word=word.replace("\"", "");
    	word=word.replace("'", "");
    	word=word.replace(";", "");
    	word=word.replace(",", "");
    	word=word.replace("/", "");
    	word=word.replace("�", "");
    	word=word.replace("�", "");
    	word=word.replace("-", "");
    	word=word.replace("[", "");
    	word=word.replace("]", "");
    	word=word.replace("+", "");
    	word=word.replace("?", "");
    	word=word.replace("�", "");
    	word=word.replace("�", "");
    	word=word.replace("!", "");
    	word=word.replace("*", "");
    	word=word.replace("(", "");
    	word=word.replace(")", "");
    	word=word.replace("{", "");
    	word=word.replace("}", "");
    	
    	double onak=0;
		double esaldia=countWords(word);
    	
    	String[] hitzak = word.split(" ");
		for (String ss: hitzak){
			if (ss.matches("")){
				
			}else{
				//System.out.println(ss +" hitza ondo dago? " + forinf.CheckSpell(ss));
				Boolean momentukoa = forinf.CheckSpell(ss);
				if (momentukoa ==true){
					onak=onak+1;
				}/*else{
					List<?> proposamena = forinf.getSuggestions(ss, 1);
					//System.out.println("Proposamenak: "+proposamena.toString());
					
					//ordezkatu testu originalean gaizki dauden hitzak, aurreneko proposamenarekin:
					//TESTU ONA HEMEN DAGO, ITZULI BEHAR DENA ----> ORIGINALA aldagaian.
					if (proposamena.isEmpty()==false){
						originala=originala.replace(ss,proposamena.get(0).toString());
						//System.out.println("Aldaketa egin da: "+ss+" hitza, honegatik -> "+proposamena.get(0).toString());
						//emaitza.addTest(originala);
						
					}
					
					
				}	*/
				
			}
			
			
		}
		TestuOna=originala;
		double denera = onak/esaldia;
		//System.out.println("Esaldia: " +esaldia+ " .Onak: "+onak+" .Denera: "+denera+" hau da portzentaia.");
		if (denera > 0.6){
			return true;
			//emaitza.addBool(true);
		}else{
			return false;
			//emaitza.addBool(false);
		}
		//return emaitza;
		
		
	}

	public static String TestuaTratatu (Status est){
		
		String tratatua = null;
		String originala = est.getText();
		String momentua = null;
		ArrayList<String> retw ;
		ArrayList<String> arroba ;
		ArrayList<String> hash ;
		
		//momentua = kendutildeak(originala);
		momentua = originala;
		retw=Laguntzaileak.RegexeFindText(momentua, "RT\\w*");
		arroba=Laguntzaileak.RegexeFindText(momentua, "@\\w*");
		hash=Laguntzaileak.RegexeFindText(momentua, "#\\w*");
		if (retw.isEmpty()){
			
		}else{
			for (int n=0; n<retw.size();n++){
				momentua= momentua.replaceAll("RT", "");// kendu RT guztiak.
				momentua= momentua.replaceFirst(":", "");
				//System.out.println("RT: "+retw.get(n));
			}
		}
		if (arroba.isEmpty()){
			
		}else{
			for (int n=0; n<arroba.size();n++){
				momentua= momentua.replaceAll(arroba.get(n), "");// kendu @blabla guztiak.
				//System.out.println("@: "+arroba.get(n));
			}
		}
		if (hash.isEmpty()){
			
		}else{
			
			for (int n=0; n<hash.size();n++){
				//System.out.println(hash.get(n));
				String proba = hash.get(n).replace("#", "");
				boolean ondo=forinf.CheckSpell(proba);
				if(ondo==false){
					momentua= momentua.replaceAll(hash.get(n), "");// kendu #karkar guztiak.
				}else{
					momentua=momentua.replaceAll(hash.get(n), proba);
				}
				
				//System.out.println("momentua: "+momentua);
			}
		}
		momentua= momentua.replaceAll("\"", "'");
		//System.out.println(momentua);
		
		tratatua=momentua;
		TestuOna=tratatua;
		
		return tratatua;
	}
	public static void fitx_idatzi (Status est, User erab, String itzulitakoa){
		
		
		
		final HashtagEntity[] hashtagak = est.getHashtagEntities();
		
		
		final URLEntity[] linkak = est.getURLEntities();
		
		
		File fileExample = new File(".\\Fitxategiak\\EmaitzakAUTOMATIKOAK.txt");
        PrintStream idatzi = null;
        FileOutputStream fos = null;
        try {
			fos = new FileOutputStream(fileExample, true);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		idatzi = new PrintStream(fos);
       
		if(fileExample.exists() && !fileExample.isDirectory()) { 
			
			idatzi.println("Originala: @"+erab.getScreenName().toString()+": "+est.getText().toString()+"\r \n");
			
			String hash ="";
			for (HashtagEntity i : hashtagak) {
				hash=hash+" #"+i.getText();
		    }
			String link ="";
			for (URLEntity i : linkak) {
				link=link+" "+i.getExpandedURL();
		    }
			String ret="";
			if (est.isRetweet()){
				ret=ret+" RT @"+est.getRetweetedStatus().getUser().toString() +": ";
				
			}
			
			idatzi.println("Itzulpena: @"+erab.getScreenName().toString()+": "+ret+" "+itzulitakoa+" "+hash+" "+link+"\r \n");
			idatzi.close();
			
			//System.out.println("Ondo gorde dira AUTOMATIKOAK fitxategian.");  
			
		}

		
		
	}
public static void fitx_idatzi2 ( String erab, String itzulitakoa, String fitx, String orig){
		

		File fileExample = new File(".\\Fitxategiak\\itzulita_"+fitx );
        PrintStream idatzi = null;
        FileOutputStream fos = null;
        try {
			fos = new FileOutputStream(fileExample, true);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		idatzi = new PrintStream(fos);
       
		if(fileExample.exists() && !fileExample.isDirectory()) { 
			
			idatzi.println("Originala: @"+erab+": "+orig+"\r \n");

			
			idatzi.println("Itzulpena: @"+erab+": "+itzulitakoa+" \r \n");
			idatzi.close();
			
			//System.out.println("Ondo gorde dira AUTOMATIKOAK fitxategian.");  
			
		}

		
		
	}
	public static String itzuli (){
		String itzulitakoa = null;
		try {
			
			
			String ItzuliTest = Laguntzaileak.TestuOna;
			
			
			Process process = Runtime.getRuntime().exec("perl .\\bin\\matxinEsEu.pl \""+ItzuliTest +"\"");
			process.waitFor();
			if(process.exitValue() == 0)
			{
				//System.out.println("Command Successful");
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = null;
					while ((line = in.readLine()) != null) {
						//System.out.println(line);
						
						itzulitakoa = line;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			else
			{
				//System.out.println("Arazoa egon da testua itzultzen AUTOMATIKOKI..");
				
			}
			
		}
		catch(Exception e1)
		{
			//System.out.println("Exception: "+ e1.toString());
		}
		return itzulitakoa;
	}
	public static void Automat  (Status Estaduak){

		
		User erab = Estaduak.getUser();
		Status est = Estaduak;

		String itzulitest;
		/*
		String originala ;
		if (est.isRetweet()){
			originala=est.getRetweetedStatus().getText();
			
		}else{
			originala=est.getText();
		}*/
		
		String tratatua=Laguntzaileak.TestuaTratatu(est);
		

		boolean formala =Laguntzaileak.InfAlaFor(tratatua);
		
		if (formala==true) {
			itzulitest= itzuli();	
			
		}else{
			
			itzulitest= itzuli();
		}
		
		fitx_idatzi (est, erab, itzulitest);
		
		
		
	}
	public static String[] csvIrak(String path) {
		
		String csvFile = path;
		//String csvFile = "/Users/mkyong/Downloads/GeoIPCountryWhois.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\t";
		String[] txioak = null;
		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
	 
			        // use comma as separator
				txioak = line.split(cvsSplitBy);
	 
				//System.out.println("Txioa [hizkuntza= " + txioak[0]  + " , txioa=" + txioak[1] +  " , erabiltzailea=" + txioak[2] + "]");
				return txioak;
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
		//System.out.println("Done .csv irakurketa.");
		return txioak;
	  }
	 
	
	
}
