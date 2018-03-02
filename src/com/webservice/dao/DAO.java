package com.webservice.dao;

import java.sql.Connection;
import java.util.List;

/**
 * �������ݵ�DAO�ӿ�
 * ���涨��ø��ַ������ݿ�ķ���
 * @author Administrator
 * @param T:DAO �����ʵ���������
 */
public interface DAO <T>{
	/**
	 * INSERT ,UPDATE ,DELETE
	 * @param con ���ݿ�����
	 * @param sql SQL���
	 * @param args ���ռλ���Ŀɱ����
	 */
	public void update(String sql,Object...args);
	
	/**
	 * ����һ��T�Ķ���
	 * @param con
	 * @param sql
	 * @param args
	 * @return
	 */

	public <T> 	T getOne(String sql, Object...args);
	
	/**
	 * ����T��һ������
	 * @param con
	 * @param sql
	 * @param args
	 * @return
	 */

	public <T> List<T> getForList(String sql,Object...args);
	
	/**
	 * ���ؾ����һ��ֵ������������ĳ���˵�����
	 * @param con
	 * @param sql
	 * @param args
	 * @return
	 */
	public <E> E getForValue(String sql,Object... args);
	
	/**
	 * ��������ķ���
	 * @param con
	 * @param sql
	 * @param args�����ռλ����Object[]���͵Ŀɱ����
	 */
	public void batch(String sql,Object[]...args);

}
