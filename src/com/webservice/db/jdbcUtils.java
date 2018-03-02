package com.webservice.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC�����Ĺ�����
 * @author Administrator
 *
 */
public class jdbcUtils {
	
	/**
	 * Ĭ�ϲ��ύ����
	 * @param connection
	 */
	public static void beginTran(Connection connection){
		if (connection!=null) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * �ύ����
	 * @param connection
	 */
	public static void commitTran(Connection connection){
		if (connection!=null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void rollback(Connection connection){
		if (connection!=null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * �ͷ�Connection����
	 * @param connection
	 */
	public static void releaseAll(Connection connection,Statement statement,ResultSet resultSet){
		try {
			if (connection!=null) {
				connection.close();
			}
			if (statement!=null) {
				statement.close();
			}
			if (resultSet!=null) {
				resultSet.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static DataSource dataSource=null;
	
	static{
		//����Դֻ�ܱ�����һ��
		dataSource=new ComboPooledDataSource("stationMVCApp");
	}
	/**
	 * ��������Դ��һ��Connection����
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
}
