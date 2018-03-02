package com.webservice.testdata;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.webservice.bean.StationInfo;

public class StationDataInfoTest {

	@Test
	public void testGetAll() {
		StationInfoImpl stationInfoImpl = new StationInfoImpl();
		String sql="select * from elec_station";

		List<StationInfo> stationInfoList = stationInfoImpl.getAll(sql);
		System.out.println(stationInfoList);
	}

	@Test
	public void testUpdateStringStationInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

}
