package com.webservice.bean;

import java.util.HashMap;
import java.util.List;

public class StationDatas {
	private List<StationData> stationDatas;
	private StationData[] stationDataArr;
	private HashMap<String, StationData> maps;
	public List<StationData> getStationDatas() {
		return stationDatas;
	}
	public void setStationDatas(List<StationData> stationDatas) {
		this.stationDatas = stationDatas;
	}
	public StationData[] getStationDataArr() {
		return stationDataArr;
	}
	public void setStationDataArr(StationData[] stationDataArr) {
		this.stationDataArr = stationDataArr;
	}
	public HashMap<String, StationData> getMaps() {
		return maps;
	}
	public void setMaps(HashMap<String, StationData> maps) {
		this.maps = maps;
	}
	
	
}
