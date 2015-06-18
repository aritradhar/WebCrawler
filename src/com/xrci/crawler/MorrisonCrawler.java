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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xrci.crawler.env.ENV;

public class MorrisonCrawler 
{
	public static void main(String[] args)
	{
		FileWriter fw = null;
		try 
		{
			fw = new FileWriter("MorrisonsTaxonomyTree_Flower.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ENV.setProxy();
		
		File input = new File("C:\\Users\\w4j3yyfd\\Desktop\\Morrisons Taxonomy trees\\MG_Flower.html");
		Document document = null;
		try 
		{
			//document = Jsoup.parse(input, "UTF-8", "https://groceries.morrisons.com/webshop/getCategories.do?tags=|105651|104268&index=0&view=shelf&dnr=y");
			//document = Jsoup.parse(input, "UTF-8", "https://groceries.morrisons.com/webshop/getCategories.do?tags=|105651|104268&index=0&view=shelf&dnr=y");
			//document = Jsoup.parse(input, "UTF-8", "https://groceries.morrisons.com/webshop/getCategories.do?tags=%7C105651%7C103644&Asidebar=1");
			//document = Jsoup.parse(input, "UTF-8", "https://groceries.morrisons.com/webshop/getCategories.do?tags=|105651|102838&Asidebar=1");
			//document = Jsoup.parse(input, "UTF-8", "https://groceries.morrisons.com/webshop/getCategories.do?tags=|105651|102063&Asidebar=1");
			//document = Jsoup.parse(input, "UTF-8", "https://groceries.morrisons.com/webshop/getCategories.do?tags=|105651|103423&Asidebar=1");
			//document = Jsoup.parse(input, "UTF-8", "https://groceries.morrisons.com/webshop/getCategories.do?tags=%7C105651%7C104162&Asidebar=1");
			//document = Jsoup.parse(input, "UTF-8", "https://groceries.morrisons.com/webshop/getCategories.do?tags=%7C105651%7C102207&Asidebar=1");
			document = Jsoup.parse(input, "UTF-8", "https://groceries.morrisons.com/webshop/getCategories.do?tags=%7C105651%7C150516&Asidebar=1");
			
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//String url = "https://groceries.morrisons.com/webshop/getCategories.do?tags=|105651|104268&Asidebar=1";
		//Document document = Jsoup.connect(url).timeout(3000).userAgent("Mozilla").get();
		
		Elements answerers = document.select("a[href]");
		
		int count = 0 ;
		int tot = answerers.size();
		
		for (Element answerer : answerers) 
		{
		    //System.out.println("div text: " + answerer.text());
		    String link =  answerer.attr("abs:href");
		    
		    if(!link.contains("/webshop/product"))
		    	continue;
		    
		    if(Page.visited.contains(link))
		    	continue;
		    
		    Page.visited.add(link);
		    
		    //System.out.println("## LINK : " + link);
		    
		    Document doc = null;
		    
		    int i = 0;
		    boolean success = false;
		    while(i < ENV.TIMEOUT_RETRY)
		    {
		    	try 
		    	{
		    		doc = Jsoup.connect(link).timeout(3000).userAgent("Mozilla").get();
		    		success = true;
		    		break;
		    		
		    	}
		    	catch(SocketTimeoutException ex)
		    	{
		    		System.err.println("Timeout happen retry .. " + i);
		    	}
		    	catch(SocketException ex)
		    	{
		    		System.err.println("Socket exception happen retry .. " + i);
		    	}
		    	catch(HttpStatusException ex)
		    	{
		    		System.err.println("address not found. Skipped...");
		    		doc = null;
		    		break;
		    	}
		    	catch(MalformedURLException ex)
		    	{
		    		System.err.println("Malformed URL. Skipped...");
		    		doc = null;
		    	}
		    	catch (IOException e1) 
		    	{				
					e1.printStackTrace();
				}
		    	finally
		    	{
		    		i++;
		    		if(!success && i > ENV.TIMEOUT_RETRY)
		    		{
		    			try 
		    			{
							fw.close();
						} 
		    			catch (IOException e1) 
		    			{
							e1.printStackTrace();
						}
		    		}
		    	}
		    }
		    if(doc == null)
		    {
		    	System.err.println("Document can't be reached skipped...");
		    	continue;
		    }
		    
		    Elements categories = doc.getElementsByClass("categories");
		    //System.out.println("%% Product Name : " + doc.title());
		    
		    if(categories == null)
		    {
		    	System.err.println("Null categories skip");
		    	continue;
		    }
		    
		    for(Element e : categories)
		    {
		    	String productName = doc.title();
		    	productName = productName.replace("Morrisons: ", "").replace("(Product Information)", "");
		    	
		    	for(Element child : e.children())
		    	{
		    		//System.out.println(child.text() + "/" + productName);
		    		try 
		    		{
						fw.append(child.text() + "/" + productName + " > " + link + "\n");
					} 
		    		catch (IOException e1) 
		    		{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		count++;    		
		    		if(count %10 == 0)
		    			System.out.println(count);
		    	}
		    }
		}
		
		try 
		{
			fw.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		System.out.println("parse complete");
		
	}
}
