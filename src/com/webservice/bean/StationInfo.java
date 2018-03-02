package com.webservice.bean;

public class StationInfo {
	private String subid;
	private String invertid;
	private String subname;
	private double latitude;
	private double longitude;
	private String address;
	private String company;
	private String telnumber;
	private String image;
	public String getSubid() {
		return subid;
	}
	public void setSubid(String subid) {
		this.subid = subid;
	}
	public String getInvertid() {
		return invertid;
	}
	public void setInvertid(String invertid) {
		this.invertid = invertid;
	}
	public String getSubname() {
		return subname;
	}
	public void setSubname(String subname) {
		this.subname = subname;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTelnumber() {
		return telnumber;
	}
	public void setTelnumber(String telnumber) {
		this.telnumber = telnumber;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public StationInfo(String subid, String invertid, String subname,
			double latitude, double longitude, String address, String company,
			String telnumber, String image) {
		super();
		this.subid = subid;
		this.invertid = invertid;
		this.subname = subname;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.company = company;
		this.telnumber = telnumber;
		this.image = image;
	}
	
	public StationInfo() {

	}
	@Override
	public String toString() {
		return "StationInfo [subid=" + subid + ", invertid=" + invertid
				+ ", subname=" + subname + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", address=" + address
				+ ", company=" + company + ", telnumber=" + telnumber
				+ ", image=" + image + "]";
	}

	
	
}
