//*************************************************************************************
//*********************************************************************************** *
//author Aritra Dhar 																* *
//Research Engineer																  	* *
//Xerox Research Center India													    * *
//Bangalore, India																    * *
//--------------------------------------------------------------------------------- * * 
///////////////////////////////////////////////// 									* *
//The program will do the following:::: // 											* *
///////////////////////////////////////////////// 									* *
//version 1.0 																		* *
//*********************************************************************************** *
//*************************************************************************************


package com.xrci.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MergeTrees 
{
	public static final String input = "C:\\Taxonomy Tree";
	public static final String output = "Merged_tree.txt";
	
	
	public static void main(String[] args) throws IOException 
	{
		File[] files = new File(input).listFiles();
		FileWriter fw = new FileWriter(output);
		
		BufferedReader br = null;
		int i = 0;
		for(File f : files)
		{
			System.out.println("File processing " + f);
			br = null;
			br = new BufferedReader(new FileReader(f));
			String s = "";
			String subStr = "", subStr_ = "";;
			while((s = br.readLine()) != null)
			{
				subStr = s.substring(0, s.indexOf("https")).replaceAll(" / ", ">").replaceAll("/", ">").trim();
				subStr_ = s.substring(s.indexOf("https"), s.length()).trim();
				//System.out.println(subStr + subStr_);
				fw.append((subStr + subStr_).replaceAll(" >", ">").trim().concat("\n"));
				i++;
			}
		}
		
		br.close();
		fw.close();
		System.out.println("Total line passed : " + i);
		System.out.println("done..");
	}
}
