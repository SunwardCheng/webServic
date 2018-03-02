package com.webservice.testdata;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class GetDataTest {


	@Test
	public void testGetMonthDataByInvertId() {
		GetDataImpl getDataImpl=new GetDataImpl();
		String month=getDataImpl.getDayDataBySubId("1",  "2016-02-20");
		System.out.println(month);
	}

	@Test
	public void testGetYearDataByInvertId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetYearDataBySubId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetYearsDataByInvertId() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindServerPath() {
		GetDataImpl getDataImpl=new GetDataImpl();
		String infoString = getDataImpl.getSubstationInfo();
		System.out.println(infoString);
	}

	@Test
	public void testGetAllTableNames() {
		GetDataImpl getDataImpl=new GetDataImpl();
		
		Map<String, Boolean> jsonArray=getDataImpl.getAllTableNames();
		System.out.println(jsonArray.toString());
		
	}

}
