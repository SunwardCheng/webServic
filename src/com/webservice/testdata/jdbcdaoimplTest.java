package com.webservice.testdata;

import java.util.List;

import org.junit.Test;

import com.webservice.bean.StationData;
import com.webservice.dao.jdbcDAOImpl;

public class jdbcdaoimplTest {
	
	@Test
	public void test() {
		jdbcDAOImpl<StationData> staDaoImpl=new jdbcDAOImpl<StationData>();
		String query="select * from mininverter20160315 where substid=? and inverterid=?";
		List<StationData>datas=staDaoImpl.getForList(query, 1,1);
		System.out.println(datas.get(0));
	}

}
