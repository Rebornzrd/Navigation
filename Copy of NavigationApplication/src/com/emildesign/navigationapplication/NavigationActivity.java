package com.emildesign.navigationapplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;



public class NavigationActivity extends FragmentActivity implements OnMarkerClickListener{

	
	
	private static final LatLng STARTINGPOINT = new LatLng(40.915823, -73.121903);
	private static final LatLng DESTINATION = new LatLng(40.915962, -73.126264);
	private GoogleMap map;
	private SupportMapFragment fragment;
	private LatLngBounds latlngBounds;
	private ImageButton bNavigation;
	private ImageButton bSearch;
	private Polyline newPolyline;
	private boolean isTravelingToDestination = false;
	private int width, height;
	private double LAT,LNG;
    private EditText et ;
    private static String location ;
	private static double DLAT = 40.915962;
	private static double DLNG = -73.126264;
	private LinearLayout BottomButton;
	private LinearLayout myDetailedListView;
	private LinearLayout ListViewMainButton;
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private TextView text4;
	private ImageView ListDetialedImage;
	private ListView listView;
	private ArrayList<String> list;
	private general_Service gen_service= new general_Service();
	private SBU_dailyLife_service daily_service= new SBU_dailyLife_service();
	private Collection<myPOI> myMarkerCollection;
	private HashMap<Marker, myPOI> mMarkersHashMap = new HashMap<Marker, myPOI>();
	private boolean BottomBarFlag=false;
	private boolean ListViewFlag=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	//	et = (EditText)findViewById(R.id.et_location);         
           //get reference to find the button
		//location = et.getText().toString();
       
         //get reference to EditText
		setContentView(R.layout.activity_navigation);				
		getSreenDimanstions();
	    fragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
		map = fragment.getMap(); 	
		
		map.setMyLocationEnabled(true);
		
      
		BottomButton=(LinearLayout)findViewById(R.id.BottomButton);
		bNavigation = (ImageButton) findViewById(R.id.bNavigation);	
		bSearch = (ImageButton) findViewById(R.id.bSearch);	
		 et = (EditText) findViewById(R.id.et_location);
		   map.setOnMarkerClickListener(this);
		LocationManager lm =  (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener ll = new mylocationlistener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,ll);   
		
		
		    bSearch.setOnClickListener(new View.OnClickListener() {
		    	@Override
			        public void onClick(View v) {
		    		
		    		   map.clear();
		    		   gen_service.gen_flag=false;
		    		   daily_service.daily_flag=false;
		    		   BottomButton.setVisibility(View.INVISIBLE);
		    		   
			    	  location = et.getText().toString();
			   
			   
            if(gen_service.CheckGen(location)){ 
            	gen_service.gen_flag=true;
	       myMarkerCollection=gen_service.getTargetPOIOfGen(location);
	        }
            else if (daily_service.CheckDaily(location)){
            	daily_service.daily_flag=true;
           myMarkerCollection=daily_service.getTargetPOIOfDaily(location);
            }
            else 
            myMarkerCollection = null;
            
            	 
	              for(myPOI marker_element: myMarkerCollection){
				    
		              MarkerOptions option= new MarkerOptions()
		                .position((new LatLng(marker_element.getmLatitude(), marker_element.getmLongitude())))
					      .title(marker_element.getmLabel());
		                   Marker currentMarker = map.addMarker(option);
		                   mMarkersHashMap.put(currentMarker, marker_element);
		                   LatLng  DESTINATION =new LatLng (DLAT,DLNG);
	  		               map.moveCamera(CameraUpdateFactory.newLatLngZoom(DESTINATION, 13)); 
	                   	
	       			     }	  
		    	}});
		   
		    map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
				
				@Override
				public void onMapClick(LatLng arg0) {
					if(ListViewFlag==false)
				         BottomButton.setVisibility(View.INVISIBLE);
					else if(ListViewFlag==true){
						 
						 myDetailedListView.setVisibility(View.INVISIBLE);
						 ListViewFlag=false;
					}
				}
			});
		    
		    
              BottomButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ListViewFlag=true;      
					myDetailedListView.setVisibility(View.VISIBLE);
					
				}
			   });
		    
		    
		    
		    
		    bNavigation.setOnClickListener(new View.OnClickListener() {	
																	
			

			@Override
			public void onClick(View v) {										
				

				LatLng  DESTINATION =new LatLng (DLAT,DLNG);
				LatLng  STARTINGPOINT = new LatLng(LAT,LNG);
				 map.moveCamera(CameraUpdateFactory.newLatLngZoom(STARTINGPOINT, 13));
				if (!isTravelingToDestination)
				{										
					
					findDirections(  STARTINGPOINT.latitude,  STARTINGPOINT.longitude,DESTINATION.latitude, DESTINATION.longitude, GMapV2Direction.MODE_WALKING);
				}
				
			}
		});
		
		
		
	}
	
	
    class mylocationlistener implements LocationListener{


	@Override
	public  void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if(location != null)
		{
			double lon = location.getLongitude();
			double lat = location.getLatitude();
			 LAT = lat;
			 LNG = lon;
			
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub 
		
	}
	

	}
    public  boolean onMarkerClick(final Marker marker) {
    	marker.showInfoWindow();
        myPOI currentPOI= mMarkersHashMap.get(marker);
        DLAT=currentPOI.getmLatitude();
        DLNG=currentPOI.getmLongitude();
        text1=(TextView)findViewById(R.id.textView1);
		text2=(TextView)findViewById(R.id.textView2);
        BottomButton=(LinearLayout)findViewById(R.id.BottomButton);
        BottomBarFlag=true;
        // initialize Detailed Information ListView LinearLayout
        myDetailedListView=(LinearLayout)findViewById(R.id.myDetailedListView);
        ListViewMainButton=(LinearLayout)findViewById(R.id.ListViewMainButton);
        text3=(TextView)findViewById(R.id.textView3);
        text4=(TextView)findViewById(R.id.textView4);
        listView=(ListView)findViewById(R.id.listview);
       
        if(gen_service.gen_flag==true)
        {    // Set the customized information on BottomBar
	        text1.setText(gen_service.firstTextInfoOfGen(currentPOI));
	        text2.setText(gen_service.secondTextInfoOfGen(currentPOI));
	        BottomButton.setVisibility(View.VISIBLE);
	        
	        // Set the customized information on ListView
	        text3.setText(gen_service.firstTextInfoOfGen(currentPOI));
	        text4.setText(gen_service.secondTextInfoOfGen(currentPOI));
	        list=daily_service.getTargetListView(currentPOI);
	        SBU_dailyLife_service.StableArrayAdapter adapter = daily_service.new StableArrayAdapter(NavigationActivity.this, android.R.layout.simple_list_item_1,list);
	        listView.setAdapter(adapter);
        }
        
        if(daily_service.daily_flag==true)
        {
        	 text1.setText(daily_service.firstTextInfoOfDaily(currentPOI));
             text2.setText(daily_service.secondTextInfoOfDaily(currentPOI));
             BottomButton.setVisibility(View.VISIBLE);
             
             text3.setText(daily_service.firstTextInfoOfDaily(currentPOI));
             text4.setText(daily_service.secondTextInfoOfDaily(currentPOI));
             ListDetialedImage=(ImageView)findViewById(R.id.ListViewImage);
             ListDetialedImage.setImageResource(R.drawable.sac);
             list=daily_service.getTargetListView(currentPOI);
             SBU_dailyLife_service.StableArrayAdapter adapter = daily_service.new StableArrayAdapter(NavigationActivity.this, android.R.layout.simple_list_item_1,list);
             listView.setAdapter(adapter);	
        }

		return false ;
    }
	
	@Override
	protected void onResume() {
		
		super.onResume();
    	latlngBounds = createLatLngBoundsObject(STARTINGPOINT, DESTINATION);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));

	}

	public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
		PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.BLUE);

		for(int i = 0 ; i < directionPoints.size() ; i++) 
		{          
			rectLine.add(directionPoints.get(i));
		}
		if (newPolyline != null)
		{
			newPolyline.remove();
		}
		newPolyline = map.addPolyline(rectLine);
		if (isTravelingToDestination)
		{
			latlngBounds = createLatLngBoundsObject(STARTINGPOINT, DESTINATION);
	        map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 300));
		}
		//else
		//{
		//	latlngBounds = createLatLngBoundsObject(AMSTERDAM, FRANKFURT);
	  //      map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
		//}
		
	}
	
	
	
	

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.navigation, menu);
        return true;
}

	
	
	
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	    case R.id.bNormal:
	       map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	        break;
	    case R.id.bSatellite:
	    	map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	        break;
	    case R.id.bHybrid:
	    	map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	        break;
	    case R.id.bTerrain:
	    	map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
	        break;
	   
	        }
	        return super.onOptionsItemSelected(item);
	    }
	

	
	private void getSreenDimanstions()
	{
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth(); 
		height = display.getHeight(); 
	}
	
	private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
	{
		if (firstLocation != null && secondLocation != null)
		{
			LatLngBounds.Builder builder = new LatLngBounds.Builder();    
			builder.include(firstLocation).include(secondLocation);
			
			return builder.build();
		}
		return null;
	}
	
	public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
		map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
		map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
		map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
		map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);
		
		GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
		asyncTask.execute(map);	
	}
}
