package com.webservice.dao;

import java.util.List;

import com.webservice.bean.StationInfo;


public interface StationInfoDAO {
	
	public List<StationInfo> getAll(String sql);
	
	public void update(String sql,StationInfo StationInfo);
	
	public StationInfo get(String sql,String substid);
	
	public void delete(String sql,String substid);
}
