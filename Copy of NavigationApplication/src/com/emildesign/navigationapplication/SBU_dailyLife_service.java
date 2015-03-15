package com.emildesign.navigationapplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class SBU_dailyLife_service {
     	
	public  Multimap <String,myPOI> daily_map=ArrayListMultimap.create();
	public boolean daily_flag= false; 
	
	public SBU_dailyLife_service (){
		daily_map.put("food",new myPOI("ignite","Stony brook union", "9:00am--10:00pm", "631-681-0001", 40.916905, -73.122228));
		daily_map.put("food",new myPOI("Starbuck","Roth Food Court", "8:00am--11:00pm", "631-681-0002", 40.910946, -73.123774));
	}
		
	public Collection<myPOI> getTargetPOIOfDaily(String str){
		Collection<myPOI> myPOICollection = daily_map.get(str);
		return myPOICollection;
	}
	
	public String firstTextInfoOfDaily(myPOI POI_element){
		  String str="Location:"+POI_element.getmLocation();
		  return str;
	}
	public String secondTextInfoOfDaily(myPOI POI_element){
		 String str="Available Time:"+POI_element.getmFund_detail();
		  return str;
	}
	public boolean CheckDaily(String str){
		 return daily_map.containsKey(str);
	}
	
     public ArrayList<String> getTargetListView(myPOI POI_element){
		  
		 String[] values = new String[] {"Name:  "+POI_element.getmLabel(),"Location:  "+POI_element.getmLocation(),
				 "Available Time:  "+POI_element.getmFund_detail(),"Contact Phone:  "+POI_element.getmPhone(),"1","2","3"};

			    final ArrayList<String> list = new ArrayList<String>();
			    for (int i = 0; i < values.length; ++i) {
			      list.add(values[i]);
			     } 

			        return list;
			      }
			      

			        class StableArrayAdapter extends ArrayAdapter<String> {

			        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

			        public StableArrayAdapter(Context context, int textViewResourceId,
			            List<String> objects) {
			          super(context, textViewResourceId, objects);
			          for (int i = 0; i < objects.size(); ++i) {
			            mIdMap.put(objects.get(i), i);
			          }
			        }

			        @Override
			        public long getItemId(int position) {
			          String item = getItem(position);
			          return mIdMap.get(item);
			        }

			        @Override
			        public boolean hasStableIds() {
			          return true;
			        }

			      }
	 
}
