package com.webservice.dao;

import java.sql.Connection;
import java.util.List;

/**
 * 访问数据的DAO接口
 * 里面定义好各种访问数据库的方法
 * @author Administrator
 * @param T:DAO 处理的实体类的类型
 */
public interface DAO <T>{
	/**
	 * INSERT ,UPDATE ,DELETE
	 * @param con 数据库连接
	 * @param sql SQL语句
	 * @param args 填充占位符的可变参数
	 */
	public void update(String sql,Object...args);
	
	/**
	 * 返回一个T的对象
	 * @param con
	 * @param sql
	 * @param args
	 * @return
	 */

	public <T> 	T getOne(String sql, Object...args);
	
	/**
	 * 返回T的一个集合
	 * @param con
	 * @param sql
	 * @param args
	 * @return
	 */

	public <T> List<T> getForList(String sql,Object...args);
	
	/**
	 * 返回具体的一个值，如总人数，某个人的姓名
	 * @param con
	 * @param sql
	 * @param args
	 * @return
	 */
	public <E> E getForValue(String sql,Object... args);
	
	/**
	 * 批量处理的方法
	 * @param con
	 * @param sql
	 * @param args：填充占位符的Object[]类型的可变参数
	 */
	public void batch(String sql,Object[]...args);

}
