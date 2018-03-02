package com.webservice.testdata;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.stream.FileImageInputStream;
import javax.jws.WebMethod;
import javax.jws.WebService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.webservice.db.jdbcUtils;
import com.webservice.bean.StationData;
import com.webservice.bean.StationInfo;
@WebService
public class GetDataImpl implements GetData {
	/**
	 * 电站信息实体类
	 */
	public static StationInfoImpl stationInfoImpl = new StationInfoImpl();
	
	/**
	 * 电站数据实体类
	 */
	public static StationDataImpl stationDataImpl = new StationDataImpl();
	
	/**
	 * 数据库中获取数据
	 * @param queryString
	 * @return
	 */
	
	public String getData(String queryString,Object...args) {

		String h=null;//小时
		String m=null;//分钟
		String time=null;
		List<StationData> stationDataList = stationDataImpl.getAll(queryString, args);
		
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=null;
		
		for(int i=0;i<stationDataList.size();i++){
			jsonObject=new JSONObject();
			//有记录时将记录保存到对象中并加入到容器中
			//首先取得记录的各个域的值
			h=stationDataList.get(i).getYmd()/60+"";
			m=stationDataList.get(i).getYmd()%60+"";
			if (Integer.parseInt(m)<10) {
				m="0"+m;
			}
			if (Integer.parseInt(h)<10) {
				h="0"+h;
			}
			
			time=h+":"+m+":"+"00";
			jsonObject.put("substid",stationDataList.get(i).getSubstid());//电站id
			jsonObject.put("inverterid",stationDataList.get(i).getInverterid());//逆变器id
			jsonObject.put("hm",time);//每5分钟间隔，数据库对应字段为ymd
			jsonObject.put("voltage",stationDataList.get(i).getData1());//交流电压,数据库对应字段为data1
			jsonObject.put("electricity",stationDataList.get(i).getData4());//交流电流
			jsonObject.put("ele_power",stationDataList.get(i).getData10());//交流功率
			jsonObject.put("dc_voltage",stationDataList.get(i).getData15());//直流电压
			jsonObject.put("dc_electricity",stationDataList.get(i).getData17());//直流电流
			jsonObject.put("dc_ele_power",stationDataList.get(i).getData19());//直流功率
			jsonObject.put("gener_capacity",stationDataList.get(i).getData22());//发电量
			
			jsonArray.add(jsonObject);
		}
			
			return jsonArray.toString();
	}
	/**
	 * 
	 * 判断表是否存在
	 * param 查询表语句
	 * */
		public boolean isExist(String datetime) {
			
			String tableName="";
			tableName="mininverter"+datetime;
			Connection con=null;
			ResultSet rs=null;
			try {
				con=jdbcUtils.getConnection();//获取数据库连接	
				rs = con.getMetaData()
						.getTables(null, null, tableName, null);
				
				if (rs.next()) {  
					return true;  
				}else {  
					return false;  
				}  
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}finally{
				jdbcUtils.releaseAll(con, null,rs);
			}
		}
		
	/**
	 * 查询某一个电站某一个逆变器的一天运行数据
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */
	@Override
	@WebMethod
	public String getDayDataByInvertId(String sub_id,String invert_id,String datetime){
		
		String[]dateStrings=datetime.split("-");
		String query="";
		String jsonArray=null;
		
		for(int i=0;i<dateStrings.length;i++){
			query=query+dateStrings[i];
		}
		
		query="select * from mininverter"+query+
				" where substid=? and inverterid= ?";
		
		jsonArray=getData(query,sub_id,invert_id);//获取数据
		
		return jsonArray;
	
	
	}
	
	/**
	 * 查询某一个电站一天的运行数据
	 * 为了便于将数据存入JSONArray中，需要想将其存入二维数组中
	 * 在存入JSONArray中
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@Override
	@WebMethod
	public String getDayDataBySubId(String sub_id, String datetime) {
		
		String[]dateStrings=datetime.split("-");
		String query_invert="";//查询一个发电站有几个逆变器
		datetime="";
		
		List<StationData> invert_data=null;//一个电站的逆变器型号
		
		String total = "";//发存放电站所有逆变器一天运行数据
		
		for(int i=0;i<dateStrings.length;i++){
			datetime=datetime+dateStrings[i];
		}
		
		//查询一个电站有几种逆变器
		query_invert="select distinct substid,inverterid from mininverter"
				+datetime+" where substid=?";
		
		invert_data=stationDataImpl.getAll(query_invert,sub_id);//获取一个电站对应逆变器号数据
		
		total = AverageOfInverters(sub_id, datetime, invert_data);
			
			return total;
		}
	
	
	public String AverageOfInverters(String sub_id, String datetime,
			List<StationData> invert_data) {
		String[][] dataArray=null;//存放所有逆变器数据平均值
		String query="";//查询发电站逆变器发电数据
		String data;//逆变器的一天数据
		String invertid="";//逆变器号
		
		JSONObject jsonObject=null;//存放每个逆变器的数据
		JSONObject meanObject=new JSONObject();//存放一个电站所有逆变器数据平均值
		JSONArray totalArray = new JSONArray();//发存放电站所有逆变器一天运行数据
		
		//遍历每个逆变器数据，求得平均值
		for(int i=0;i<invert_data.size();i++){//逆变器个数
			
			invertid=JSONObject.fromObject(//获得逆变器号
					invert_data.get(i)).get("inverterid").toString();
			
			//查询每个逆变器数据 
			query="select * from mininverter"+datetime+
					" where substid=? and inverterid=? ";
			
			data=getData(query,sub_id,invertid);//得到第i个逆变器数据
			
			JSONArray data_jsonArray=JSONArray.fromObject(data);//转化为JSONArray数组
			
			if (i==0) {
				dataArray=new String[data_jsonArray.size()][8];//第一次访问时声明数组大小
				
			}	
			Iterator iterator=data_jsonArray.iterator();
			int j=0;
			while (iterator.hasNext()) {
				
				jsonObject=(JSONObject) iterator.next();//每个JSONObject对象存一组数据
				dataArray=judgeNull(dataArray, j);//如果初始值是null，赋为"0"
				
				dataArray[j][0]=jsonObject.get("hm").toString();
				
				
				//求所有逆变器数据平均值，发电量不需要求平均值
				dataArray[j][1] = String.valueOf(Sum(dataArray[j][1] , Double
						.parseDouble(jsonObject.get("voltage").toString())/invert_data.size()
						));
				
				dataArray[j][2] = String.valueOf(Sum(dataArray[j][2] , Double
						.parseDouble(jsonObject.get("electricity").toString())/invert_data.size()
						));
				
				dataArray[j][3] = String.valueOf(Sum(dataArray[j][3] , Double
						.parseDouble(jsonObject.get("ele_power").toString())/invert_data.size()
						));
				
				dataArray[j][4] = String.valueOf(Sum(dataArray[j][4] , Double
						.parseDouble(jsonObject.get("dc_voltage").toString())/invert_data.size()
						));
				
				dataArray[j][5] = String.valueOf(Sum(dataArray[j][5] , Double
						.parseDouble(jsonObject.get("dc_electricity").toString())/invert_data.size()
						));
				
				dataArray[j][6]=String.valueOf(Sum(dataArray[j][6] , Double
						.parseDouble(jsonObject.get("dc_ele_power").toString())/invert_data.size()
						));
				
				dataArray[j][7]=String.valueOf(Sum(dataArray[j][7] , Double
						.parseDouble(jsonObject.get("gener_capacity").toString())
						));
				
				
				if (invert_data.size()-1==i) {//将最终数据加入JSONArray中
					meanObject.put("hm", dataArray[j][0]);
					meanObject.put("voltage",dataArray[j][1]);//交流电压
					meanObject.put("electricity",dataArray[j][2]);//交流电流
					meanObject.put("ele_power",dataArray[j][3]);//交流功率
					meanObject.put("dc_voltage",dataArray[j][4]);//直流电压
					meanObject.put("dc_electricity",dataArray[j][5]);//直流电流
					meanObject.put("dc_ele_power",dataArray[j][6]);//直流功率
					meanObject.put("gener_capacity",dataArray[j][7]);//发电量
					totalArray.add(meanObject);
				}
				j++;
				}
			}
		return totalArray.toString();
	}
	
	public double Sum(String args1,double args2){
		double a = Double.parseDouble(args1);
		return a+args2;
	}
	
	/**
	 * 查询某一个电站某个逆变器一个月的发电量
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */

public String getMonthDataByInvertId(String sub_id, String invert_id,String datetime) {
		
		String[]dateStrings=datetime.split("-");
		String yearmonth=dateStrings[0]+dateStrings[1];//获取月份
		double total=0;//一个月的发电总量
		int row=0;
		
		JSONArray dayArray=null;//一天的数据
		JSONObject dayLast=null;//一天的最后一行数据
		JSONObject daysObject=new JSONObject();//一个月每天的发电量
		HashMap<String, Boolean> getTables=null;
		
		getTables=getAllTableNames();//得到数据库中所有的表名
		String str = "";
		String tableName=null;
		
		for (int j = 1; j <=31; j++) {
			if(j<10){
				str = "0"+j;
			}else{
				str = String.valueOf(j);
			}
			
			tableName="mininverter"+yearmonth+str;//表名
			
			if(getTables.containsKey(tableName)){//表名数据库中有该天的数据表
				dayArray=JSONArray.fromObject(//获取一天的数据
						getDayDataByInvertId(sub_id, invert_id, datetime+"-"+str));
				
				row=dayArray.size();
				
				if (row>0) {   //表中有对应id的数据
					dayLast=JSONObject //将一天的最后一行转为JSONObject对象
							.fromObject(dayArray.get(row-1));
					
					total+=Double.parseDouble(dayLast //一个月的发电总量,数组最后一行是发电量最大的数据行
							.get("gener_capacity").toString());
					
					daysObject.put(j,Double//一天的发电总量
							.parseDouble(dayLast.get("gener_capacity").toString()));
				}
			}
		}
		
		daysObject.put("month_gener_capacity", total);//一个月的发电总量
		
		return daysObject.toString();
	}

	/**
	 * 查询某个一电站一个月的发电量
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@Override
	@WebMethod
	public String getMonthDataBySubId(String sub_id,String datetime) {
		
		String[]dateStrings=datetime.split("-");
		String yearmonth=dateStrings[0]+dateStrings[1];//获取月份
		double total=0;//一个月的发电总量
		
		JSONArray dayArray=null;//一天的数据
		JSONObject dayLast=null;
		JSONObject daysObject=new JSONObject();//一个月每天的发电量
		HashMap<String, Boolean> getTables=null;//存放所有表
		
		int row=0;
		String str = "";
		String tableName="";

		getTables=getAllTableNames();//得到数据库中所有的表名
		
		for (int j = 1; j <=31; j++) {
			if(j<10){
				str = "0"+j;
			}else{
				str = String.valueOf(j);
			}
			
				/////////
				///表名///
				////////
			tableName="mininverter"+yearmonth+str;
			
			if(getTables.containsKey(tableName)){//表名数据库中有该天的数据表
				dayArray=JSONArray.fromObject(//获取一天的数据
						getDayDataBySubId(sub_id, datetime+"-"+str));
				
				row=dayArray.size();
				
				if (row>0) {   //表中有对应id的数据
					dayLast=JSONObject //将一天的最后一行转为JSONObject对象
							.fromObject(dayArray.get(row-1));
					
					total+=Double.parseDouble(dayLast //一个月的发电总量,数组最后一行是发电量最大的数据行
							.get("gener_capacity").toString());

					daysObject.put(j,Double//一天的发电总量
							.parseDouble(dayLast.get("gener_capacity").toString()));
				}
			}
		}
		
		daysObject.put("month_gener_capacity", total);//一个月的发电总量
		
		return daysObject.toString();
	}
	
	/**
	 * 查询某一个电站某一个逆变器一年的发电量
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */
	@WebMethod
	public String getYearDataByInvertId(String sub_id,
			String invert_id, String datetime) {
		
		double month_total_capacity=0;//每个月的总发电量
		double total=0;//一年的发电总量
		String str="";
		
		JSONObject month_capacity=null;//一个月的发电数据
		JSONObject monthsObject=new JSONObject();//每个月的发电数据
		
		for(int i=1;i<=12;i++){
			
			if(i<10){
				str = "0"+i;
			}else{
				str = String.valueOf(i);
			}
			
			month_capacity=JSONObject.fromObject(//获取一个月的发电数据
					getMonthDataByInvertId(sub_id,invert_id,datetime+"-"+str));
			
			//因为getMonthDataByInvertId()方法在最后添加了月发电总量key和value值，
			//当一个月发电量数据为空时，month_capacity.size()=1
			
		if (month_capacity.size()>1) {
			
			month_total_capacity=Double.parseDouble(//获取当月的发电总量
					month_capacity.get("month_gener_capacity").toString());
			
			total+=month_total_capacity;//总发电量
			
			monthsObject.put(i,month_total_capacity);//每个月的发电总量
			}else {
				monthsObject.put(i, 0.0);
			}
		
		}
		
		monthsObject.put("year_gener_capacity", total);//一年的发电总量
		
		return monthsObject.toString();
	}
	
	/**
	 * 查询某个电站一年的发电量
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@Override
	@WebMethod
	public String getYearDataBySubId(String sub_id, String datetime) {
		
		double month_total_capacity=0;//每个月的总发电量
		double total=0;//一年的发电总量
		String str="";
		
		JSONObject month_capacity=null;//一个月的发电数据
		JSONObject monthsObject=new JSONObject();//每个月的发电数据
		
		for(int i=1;i<=12;i++){
			if(i<10){
				str = "0"+i;
			}else{
				str = String.valueOf(i);
			}
			
			month_capacity=JSONObject.fromObject(//获取一个月的发电数据
					getMonthDataBySubId(sub_id,datetime+"-"+str));
			
		if (month_capacity.size()>1) {
			month_total_capacity=Double.parseDouble(//获取当月的发电总量
					month_capacity.get("month_gener_capacity").toString());
			
			total+=month_total_capacity;//一年的发电总量
			monthsObject.put(i,month_total_capacity);//每个月的发电总量
			
			}else {
				monthsObject.put(i, 0.0);
			}
		}
		
		monthsObject.put("year_gener_capacity", total);//一年的发电总量
		
		return monthsObject.toString();
	}

	@Override
	public String getYearsDataByInvertId(String sub_id,String invert_id) {
		
		double data=0;//每年的发电量
		double total=0;//历年的发电总量
		
		JSONObject year_capacity=null;//一年的发电数据
		JSONObject yearsObject=new JSONObject();//每年的发电数据
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(df.format(new Date()));//获取当前系统年份
		String str="";
		
		for(int i=2010;i<=year;i++){
			
			str=String.valueOf(i);
			
			year_capacity=JSONObject.fromObject(//获取一年的发电数据
					getYearDataByInvertId(sub_id, invert_id, str));
			
			if(year_capacity.size()>1){
				data=Double.parseDouble(year_capacity//得到该年的发电总量
						.get("year_gener_capacity").toString());
				
				total+=data;//历年发电总量
				
				yearsObject.put(i, data);//每年的发电量
				
			}else {
				yearsObject.put(i, 0.0);
			}
		}
		yearsObject.put("total_gener_capacity", total);//历年的发电累计总量
		
		return yearsObject.toString();
	}

	@Override
	public String getYearsDataBySubId(String sub_id) {
		
		double data=0;//每年的发电量
		double total=0;//历年的发电总量
		
		JSONObject year_capacity=null;//一年的发电数据
		JSONObject yearsObject=new JSONObject();//每年的发电数据
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(df.format(new Date()));//获取当前系统年份
		String str="";
		
		for(int i=2010;i<=year;i++){
			
			str=String.valueOf(i);
			
			year_capacity=JSONObject.fromObject(//获取一年的发电数据
					getYearDataBySubId(sub_id, str));
			if(year_capacity.size()>1){
				
				data=Double.parseDouble(year_capacity//得到该年的发电总量
						.get("year_gener_capacity").toString());
				
				total+=data;
				
				yearsObject.put(i, data);//每年的发电总量、
				
			}else {
				yearsObject.put(i, 0.0);
			}
		}
		yearsObject.put("total_gener_capacity", total);//历年的发电累计总量
		
		return yearsObject.toString();
	}
	/**
	 * 获取电站信息
	 */
	@Override
	public String getSubstationInfo() {
		
		String sql="select * from elec_station";

		List<StationInfo> stationInfoList = stationInfoImpl.getAll(sql);

		JSONObject stationInfo=null;//存放一个电站信息
		JSONArray stationsArray=new JSONArray();//存放所有电站信息
		
		String path=findServerPath();
		
		for (int i = 0; i < stationInfoList.size(); i++) {
			
			//有记录时将记录保存到对象中并加入到容器中
			//首先取得记录的各个域的值
			stationInfo=new JSONObject();
			
			
			stationInfo.put("substid",stationInfoList.get(i).getSubid());//电站id
			stationInfo.put("invertid",stationInfoList.get(i).getInvertid());//逆变器型号
			stationInfo.put("subname",stationInfoList.get(i).getSubname());//电站名
			stationInfo.put("latitude",stationInfoList.get(i).getLatitude());//经度
			stationInfo.put("longitude",stationInfoList.get(i).getLongitude());//纬度
			stationInfo.put("address",stationInfoList.get(i).getAddress());//电站地址
			stationInfo.put("company",stationInfoList.get(i).getCompany());//公司名
			stationInfo.put("telnumber",stationInfoList.get(i).getTelnumber());//电话
			stationInfo.put("imageurl",path+stationInfoList.get(i).getImage());//电站图片url
			
			
			stationsArray.add(stationInfo);
		}
		return stationsArray.toString();
	}
	

	/**
	 * 得到照片转为byte类型
	 */
	@Override
	public byte[][] getImage() {
		
		JSONArray stationsdata=JSONArray.//得到所有电站信息
				fromObject(getSubstationInfo());
		JSONObject stationInfo=null;
		
		int size=stationsdata.size();
		byte[][] databyte=new byte[size][];//存储照片二维数组
		
		for(int i=0;i<size;i++){
			
		FileImageInputStream inputStream=null;
		
		try {
			
			stationInfo=JSONObject.fromObject(stationsdata.getString(i));
			inputStream=new FileImageInputStream(new File(stationInfo.get("imageurl").toString()));
			ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
			
			byte[] buff=new byte[1024];
			int numBytesRead=0;
			
			while((numBytesRead=inputStream.read(buff))!=-1){
				outputStream.write(buff, 0, numBytesRead);
			}
			outputStream.flush();
			
			databyte[i]=outputStream.toByteArray();
			outputStream.close();
			inputStream.close();
			
		} catch (FileNotFoundException ex1) {
		      ex1.printStackTrace();
	    }catch (IOException ex1) {
	      ex1.printStackTrace();
	    }
		
		}
		
		return databyte;
	}   
	
	/** 
     * 获取服务器端的webapps路径 
     * @return 
     */  
	public String findServerPath(){    
	    String classPath = getClass().getClassLoader().getResource("/").getPath();   
	    try {    
	        classPath =URLDecoder.decode(classPath, "gb2312");    
	    } catch (UnsupportedEncodingException e) {    
	        e.printStackTrace();    
	    }    
	    String[] strPath = classPath.split("/");    
	    String path = "";    
	    for(int i = 0;i < strPath.length ; i++){    
	        if(i > 0 && i <= 4){    
	            path = path + strPath[i]+"/";    
	        }    
	    }    
	    return path+"webapps/";    
	}   
	
	/** 
	  * 获取指定数据库和用户的所有表名 
	  * @param conn 连接数据库对象 
	  * @return 
	  */  
	public  HashMap<String, Boolean> getAllTableNames() {  
		 
		  HashMap<String, Boolean> tableNames = new HashMap<String, Boolean>();  
		  Connection con= null;
		try {
			con = jdbcUtils.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		  
		  if (con != null) {  
			  try {  
				  DatabaseMetaData dbmd = con.getMetaData();  
				  // 表名列表  
				  ResultSet rest = dbmd.getTables(null, null, null, new String[] { "TABLE" });  
				  // 输出 table_name  
				  while (rest.next()) {  
					  String tableSchem = rest.getString(3);  
					  tableNames.put(tableSchem, true);
				  }  
			  } catch (SQLException e) {  
				  e.printStackTrace();  
			  }finally{
				  jdbcUtils.releaseAll( con,null, null);
			  }
		  }  
		  return tableNames;  
	 }  
	 

		/**
		 * 判断数组某个值是否为空
		 * @param args
		 * @param i
		 * @return
		 */
		public String[][] judgeNull(String[][] args,int i){
			for (int j = 0; j < args[i].length; j++) {
				if (args[i][j]==null) {
					args[i][j]="0";
				}
			}
			return args;
			
		}
	 
}
