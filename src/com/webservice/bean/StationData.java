package com.webservice.bean;


public class StationData{
	private String substid;
	private String collectorid;
	private String inverterid;
	private int ymd;
	private String data1;//交流电压
	private String data4;//交流电流
	private String data10;//交流功率
	private String data15;//直流电压
	private String data17;//直流电流
	private String data19;//直流功率
	private String data22;//发电量
	
	public String getSubstid() {
		return substid;
	}
	public void setSubstid(String substid) {
		this.substid = substid;
	}
	public String getCollectorid() {
		return collectorid;
	}
	public void setCollectorid(String collectorid) {
		this.collectorid = collectorid;
	}
	public String getInverterid() {
		return inverterid;
	}
	public void setInverterid(String inverterid) {
		this.inverterid = inverterid;
	}
	public int getYmd() {
		return ymd;
	}
	public void setYmd(int ymd) {
		this.ymd = ymd;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData4() {
		return data4;
	}
	public void setData4(String data4) {
		this.data4 = data4;
	}
	public String getData10() {
		return data10;
	}
	public void setData10(String data10) {
		this.data10 = data10;
	}
	public String getData15() {
		return data15;
	}
	public void setData15(String data15) {
		this.data15 = data15;
	}
	public String getData17() {
		return data17;
	}
	public void setData17(String data17) {
		this.data17 = data17;
	}
	public String getData19() {
		return data19;
	}
	public void setData19(String data19) {
		this.data19 = data19;
	}
	public String getData22() {
		return data22;
	}
	public void setData22(String data22) {
		this.data22 = data22;
	}
	
	public StationData(String substid, String collectorid, String inverterid,
			int ymd, String data1, String data4, String data10, String data15,
			String data17, String data19, String data22) {
		super();
		this.substid = substid;
		this.collectorid = collectorid;
		this.inverterid = inverterid;
		this.ymd = ymd;
		this.data1 = data1;
		this.data4 = data4;
		this.data10 = data10;
		this.data15 = data15;
		this.data17 = data17;
		this.data19 = data19;
		this.data22 = data22;
	}
	public StationData() {

	}
	@Override
	public String toString() {
		return "StationData [substid=" + substid + ", collectorid="
				+ collectorid + ", inverterid=" + inverterid + ", ymd=" + ymd
				+ ", data1=" + data1 + ", data4=" + data4 + ", data10="
				+ data10 + ", data15=" + data15 + ", data17=" + data17
				+ ", data19=" + data19 + ", data22=" + data22 + "]";
	}
	
	
	
	
	
	
	
}
