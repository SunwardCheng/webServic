package com.webservice.testdata;


import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.webservice.bean.StationData;


@WebService
public interface GetData {
	/**
	 * 数据库中获取数据
	 * @param queryString
	 * @return
	 */
	/*@WebMethod
	public String getData(String queryString);
	*/
	
	/**
	 * 查询某一个电站某一个逆变器的一天运行数据
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */

	@WebMethod
	public String getDayDataByInvertId(String sub_id, String invert_id, String datetime); 
	
	/**
	 * 查询某一个电站一天的运行数据
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@WebMethod
	public String getDayDataBySubId(String sub_id,String datetime);//根据电站id查询一天的运行数据
	
	@WebMethod
	public  String AverageOfInverters(String sub_id, String datetime,
			List<StationData> invert_data);
	/**
	 * 查询某一个电站某个逆变器一个月的发电量
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */
	@WebMethod
	public String getMonthDataByInvertId(String sub_id,String invert_id,String datetime);//根据电站id和逆变器id该逆变器查询一个月的运行数据
	
	/**
	 * 查询某个一电站一个月的发电量
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@WebMethod
	public String getMonthDataBySubId(String sub_id,String datetime);//根据电站id查询一个月的运行数据
	
	/**
	 * 查询某一个电站某一个逆变器一年的发电量
	 * 根据电站id和逆变器id该逆变器查询一年的运行数据
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */
	@WebMethod                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	public String getYearDataByInvertId(String sub_id,String invert_id,String datetime);
	
	/**
	 * 查询某个电站一年的发电量
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@WebMethod                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	public String getYearDataBySubId(String sub_id,String datetime);
	
	/**
	 * 查询某一个电站某一个逆变器历年的发电量
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */
	@WebMethod                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	public String getYearsDataByInvertId(String sub_id,String invert_id);//根据电站id和逆变器id该逆变器查询一年的运行数据
	
	/**
	 * 查询某个电站历年的发电量
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@WebMethod                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	public String getYearsDataBySubId(String sub_id);//根据电站id查询一年的运行数据
	
	/**
	 * 所有电站的信息
	 * @return
	 */
	@WebMethod
	public String getSubstationInfo();
	
	/**
	 * 得到图片转为byte类型
	 * @return
	 */
	@WebMethod 
	public byte[][] getImage();
	
/*	@WebMethod
	public  HashMap<String, Boolean> getAllTableNames();
	
	@WebMethod
	public String findServerPath();*/
	
}
