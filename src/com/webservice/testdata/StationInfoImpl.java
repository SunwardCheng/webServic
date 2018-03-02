package com.webservice.testdata;

import java.util.List;

import com.webservice.bean.StationInfo;
import com.webservice.dao.StationInfoDAO;
import com.webservice.dao.jdbcDAOImpl;

/**
 * 对电站的信息操作
 * @author Administrator
 *
 */
public class StationInfoImpl extends jdbcDAOImpl<StationInfo> implements StationInfoDAO {

	@Override
	public List<StationInfo> getAll(String sql) {
		
		return getForList(sql);
	}

	@Override
	public void update(String sql,StationInfo StationInfo) {
		update(sql, StationInfo.getSubid(),StationInfo.getInvertid(),StationInfo.getSubname(),
				StationInfo.getLatitude(),StationInfo.getLongitude(),StationInfo.getAddress(),
				StationInfo.getCompany(),StationInfo.getTelnumber(),StationInfo.getImage());
	}

	@Override
	public StationInfo get(String sql,String substid) {
		return getOne(sql, substid);
	}

	@Override
	public void delete(String sql,String substid) {
		update(sql, substid);
	}

}
