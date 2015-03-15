package com.emildesign.navigationapplication;

import java.util.Collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class general_Service {
        
		public  Multimap <String,myPOI> gen_map=ArrayListMultimap.create();
		public  boolean gen_flag=false;
		public general_Service (){
		   
			gen_map.put("sac",new myPOI("sac", "Student Activity Center", " ","631-681-0000", 40.914505, -73.124266));
		}
		
		public String firstTextInfoOfGen(myPOI POI_element){
			  String str="Location:"+POI_element.getmLocation();
			  return str;
		}
		public String secondTextInfoOfGen(myPOI POI_element){
			 String str="Contact Phone:"+POI_element.getmPhone();
			  return str;
		}
		
		public Collection<myPOI> getTargetPOIOfGen(String str){
			Collection<myPOI> myPOICollection = gen_map.get(str);
			return myPOICollection;
		}
		
		public boolean CheckGen(String str){
			 return gen_map.containsKey(str);
		}
  }
	
	
	
	
	
	
