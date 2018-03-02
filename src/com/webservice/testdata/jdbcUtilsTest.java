package com.webservice.testdata;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.webservice.db.jdbcUtils;

public class jdbcUtilsTest {

	@Test
	public void testBeginTran() {
		fail("Not yet implemented");
	}

	@Test
	public void testCommitTran() {
		fail("Not yet implemented");
	}

	@Test
	public void testRollback() {
		fail("Not yet implemented");
	}

	@Test
	public void testReleaseAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetConnection() {
		Connection connection=null;
		try {
			connection = jdbcUtils.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(connection);
	}

}
