package com.webservice.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.webservice.db.jdbcUtils;

/**
 * 使用QueryRunner 提供其具体的实现
 * @author Administrator
 *
 * @param <T>子类需传入的泛型
 */
public class jdbcDAOImpl<T> implements DAO<T> {
	private QueryRunner queryRunner=new QueryRunner();
	
	private Class<T> clazz;
	
	public jdbcDAOImpl(){
		Type superClass=getClass().getGenericSuperclass();
		if (superClass instanceof ParameterizedType) {
			ParameterizedType parameterizedType=(ParameterizedType) superClass;
			
			Type [] typeArgs=parameterizedType.getActualTypeArguments();
			
			if(typeArgs!=null&&typeArgs.length >0){
				if (typeArgs[0]instanceof Class) {
					clazz=(Class<T>) typeArgs[0];
				}
			}
		}
	}

	@Override
	public void update(String sql, Object... args) {
		Connection connection=null;
		try {
			connection=jdbcUtils.getConnection();
			queryRunner.update(connection,sql, args);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			jdbcUtils.releaseAll(connection,null,null);
		}
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	@Override
	public <T> T getOne(String sql, Object... args) {
		Connection connection=null;
		try {
			connection = jdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanHandler<T>((Class<T>) clazz), args);
		} catch (SQLException e) {
			e.printStackTrace();
		
		}finally{
			jdbcUtils.releaseAll(connection,null,null);
		}
		return null;
	}

	@Override
	public List<T> getForList(String sql, Object... args) {
		Connection connection=null;
		try {
			connection = jdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), args);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			jdbcUtils.releaseAll(connection,null,null);
		}
		return null;
	}

	@Override
	public <E> E getForValue(String sql, Object... args) {
		Connection connection=null;
		
		try {
			connection=jdbcUtils.getConnection();
			return  queryRunner.query(connection,sql,new ScalarHandler<E>(),args);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtils.releaseAll(connection,null,null);
		}
		return null;
	}
	
	@Override
	public void batch(String sql, Object[]... args) {
		Connection connection=null;
		
		try {
			connection=jdbcUtils.getConnection();
			jdbcUtils.beginTran(connection);//开始事务
			
			queryRunner.batch(connection, sql, args);
			
			jdbcUtils.commitTran(connection);//提交事务
			
		} catch (Exception e) {
			jdbcUtils.rollback(connection);//回滚事务
			e.printStackTrace();
		}finally{
			jdbcUtils.releaseAll(connection,null,null);
		}
	}
	


}
