package com.webservice.dao;

import java.util.List;

import com.webservice.bean.StationData;



public interface StationDataDAO {
	
	public List<StationData> getAll(String sql,Object... args);
	
	public void update(String sql,StationData stationData);
	
	public StationData get(String sql,Object ...args );
	
	public void delete(String sql,Object ...args );
}
