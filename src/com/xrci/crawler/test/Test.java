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


package com.xrci.crawler.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xrci.crawler.env.ENV;

public class Test 
{
	public static void main(String[] args) throws IOException 
	{
		ENV.setProxy();
		
		String url = "http://stackoverflow.com/questions/3152138";
		Document document = Jsoup.connect(url).timeout(3000).userAgent("Mozilla").get();

		Element question = document.select("#question .post-text p").first();
		System.out.println("Question: " + question.text());

		Elements answerers = document.select("#answers .user-details a");
		
		for (Element answerer : answerers) 
		{
		    System.out.println("Answerer: " + answerer.text());
		}
	}
}
