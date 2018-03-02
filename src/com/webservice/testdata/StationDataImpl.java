package com.webservice.testdata;

import java.util.List;

import com.webservice.bean.StationData;
import com.webservice.dao.StationDataDAO;
import com.webservice.dao.jdbcDAOImpl;

/**
 * 电站数据的操作
 * @author Administrator
 *
 */
public class StationDataImpl extends jdbcDAOImpl<StationData> implements StationDataDAO {
	
	@Override
	public List<StationData> getAll(String sql,Object... args) {
		return getForList(sql, args);
	}

	@Override
	public void update(String sql,StationData stationData) {
		update(sql, stationData.getSubstid(),stationData.getCollectorid(),stationData.getInverterid(),
				stationData.getData1(),stationData.getData4(),stationData.getData10(),stationData.getData15(),
				stationData.getData17(),stationData.getData19(),stationData.getData22());

	}

	@Override
	public StationData get(String sql,Object ...args ) {
		return getOne(sql, args);
	}

	@Override
	public void delete(String sql,Object ...args ) {
		delete(sql, args);

	}

}
