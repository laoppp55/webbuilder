package com.bizwink.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Counter {

	    public static void writeFile(String filename,int count){
	       
	        try{
	            PrintWriter  out=new PrintWriter(new FileWriter(filename));
	            out.println(count);
	            out.close();
	        }catch(IOException e){
	             e.printStackTrace();
	        }
	    }
	   
	    public static int readFile(String filename) {
            int posi = filename.indexOf(" ");
            if (posi>-1) filename = filename.substring(0,posi).trim();
	        File f = new File(filename);
	        int count = 0;
	        if(!f.exists()){
	             writeFile(filename, 0);
	        }
	        try{
	            BufferedReader in = new BufferedReader(new FileReader(f));
	            try{
	                count = Integer.parseInt(in.readLine());   
	            }catch(NumberFormatException e){
	                e.printStackTrace();   
	        }catch(IOException  e){
	            e.printStackTrace();
	             }
	        }
	        catch(FileNotFoundException e) {
	            e.printStackTrace();
	            }
	        return count;
	    }
}
