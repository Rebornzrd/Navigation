package com.emildesign.navigationapplication;

public class myPOI
{
    private String mLabel;
    private String mlocatoin;
    private String mfund_detail;
    private String mphone;
    private Double mLatitude;
    private Double mLongitude;

    public myPOI(String label,String location, String fund_detail, String phone, double latitude, double longitude)
    {
        this.mLabel = label;
        this.mlocatoin=location;
        this.mfund_detail=fund_detail;
        this.mphone=phone;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }
    
    
   


	public String getmLabel()
    {
        return mLabel;
    }
    public void setmLabel(String mLabel)
    {
        this.mLabel = mLabel;
    }
    
    
    public String getmLocation(){
    	return mlocatoin;
    }
    public void setmLocation(String mlocation){
    	this.mlocatoin=mlocation;
    }
    
    
    public String getmFund_detail(){
    	return mfund_detail;
    }
    public void setmFund_(String mfund_detail){
    	this.mfund_detail=mfund_detail;
    }
    
    
    public String getmPhone(){
    	return this.mphone;
    }
    public void setmPhone(String mPhone){
    	this.mphone=mPhone;
    }
    
    
    public Double getmLatitude()
    {
        return mLatitude;
    }
    public void setmLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    
    public Double getmLongitude()
    {
        return mLongitude;
    }
    public void setmLongitude(Double mLongitude)
    {
        this.mLongitude = mLongitude;
    }
}
