package com.webservice.testdata;


import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.webservice.bean.StationData;


@WebService
public interface GetData {
	/**
	 * ���ݿ��л�ȡ����
	 * @param queryString
	 * @return
	 */
	/*@WebMethod
	public String getData(String queryString);
	*/
	
	/**
	 * ��ѯĳһ����վĳһ���������һ����������
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */

	@WebMethod
	public String getDayDataByInvertId(String sub_id, String invert_id, String datetime); 
	
	/**
	 * ��ѯĳһ����վһ�����������
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@WebMethod
	public String getDayDataBySubId(String sub_id,String datetime);//���ݵ�վid��ѯһ�����������
	
	@WebMethod
	public  String AverageOfInverters(String sub_id, String datetime,
			List<StationData> invert_data);
	/**
	 * ��ѯĳһ����վĳ�������һ���µķ�����
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */
	@WebMethod
	public String getMonthDataByInvertId(String sub_id,String invert_id,String datetime);//���ݵ�վid�������id���������ѯһ���µ���������
	
	/**
	 * ��ѯĳ��һ��վһ���µķ�����
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@WebMethod
	public String getMonthDataBySubId(String sub_id,String datetime);//���ݵ�վid��ѯһ���µ���������
	
	/**
	 * ��ѯĳһ����վĳһ�������һ��ķ�����
	 * ���ݵ�վid�������id���������ѯһ�����������
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */
	@WebMethod                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	public String getYearDataByInvertId(String sub_id,String invert_id,String datetime);
	
	/**
	 * ��ѯĳ����վһ��ķ�����
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@WebMethod                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	public String getYearDataBySubId(String sub_id,String datetime);
	
	/**
	 * ��ѯĳһ����վĳһ�����������ķ�����
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */
	@WebMethod                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	public String getYearsDataByInvertId(String sub_id,String invert_id);//���ݵ�վid�������id���������ѯһ�����������
	
	/**
	 * ��ѯĳ����վ����ķ�����
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@WebMethod                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	public String getYearsDataBySubId(String sub_id);//���ݵ�վid��ѯһ�����������
	
	/**
	 * ���е�վ����Ϣ
	 * @return
	 */
	@WebMethod
	public String getSubstationInfo();
	
	/**
	 * �õ�ͼƬתΪbyte����
	 * @return
	 */
	@WebMethod 
	public byte[][] getImage();
	
/*	@WebMethod
	public  HashMap<String, Boolean> getAllTableNames();
	
	@WebMethod
	public String findServerPath();*/
	
}
