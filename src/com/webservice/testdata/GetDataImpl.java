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
	 * ��վ��Ϣʵ����
	 */
	public static StationInfoImpl stationInfoImpl = new StationInfoImpl();
	
	/**
	 * ��վ����ʵ����
	 */
	public static StationDataImpl stationDataImpl = new StationDataImpl();
	
	/**
	 * ���ݿ��л�ȡ����
	 * @param queryString
	 * @return
	 */
	
	public String getData(String queryString,Object...args) {

		String h=null;//Сʱ
		String m=null;//����
		String time=null;
		List<StationData> stationDataList = stationDataImpl.getAll(queryString, args);
		
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=null;
		
		for(int i=0;i<stationDataList.size();i++){
			jsonObject=new JSONObject();
			//�м�¼ʱ����¼���浽�����в����뵽������
			//����ȡ�ü�¼�ĸ������ֵ
			h=stationDataList.get(i).getYmd()/60+"";
			m=stationDataList.get(i).getYmd()%60+"";
			if (Integer.parseInt(m)<10) {
				m="0"+m;
			}
			if (Integer.parseInt(h)<10) {
				h="0"+h;
			}
			
			time=h+":"+m+":"+"00";
			jsonObject.put("substid",stationDataList.get(i).getSubstid());//��վid
			jsonObject.put("inverterid",stationDataList.get(i).getInverterid());//�����id
			jsonObject.put("hm",time);//ÿ5���Ӽ�������ݿ��Ӧ�ֶ�Ϊymd
			jsonObject.put("voltage",stationDataList.get(i).getData1());//������ѹ,���ݿ��Ӧ�ֶ�Ϊdata1
			jsonObject.put("electricity",stationDataList.get(i).getData4());//��������
			jsonObject.put("ele_power",stationDataList.get(i).getData10());//��������
			jsonObject.put("dc_voltage",stationDataList.get(i).getData15());//ֱ����ѹ
			jsonObject.put("dc_electricity",stationDataList.get(i).getData17());//ֱ������
			jsonObject.put("dc_ele_power",stationDataList.get(i).getData19());//ֱ������
			jsonObject.put("gener_capacity",stationDataList.get(i).getData22());//������
			
			jsonArray.add(jsonObject);
		}
			
			return jsonArray.toString();
	}
	/**
	 * 
	 * �жϱ��Ƿ����
	 * param ��ѯ�����
	 * */
		public boolean isExist(String datetime) {
			
			String tableName="";
			tableName="mininverter"+datetime;
			Connection con=null;
			ResultSet rs=null;
			try {
				con=jdbcUtils.getConnection();//��ȡ���ݿ�����	
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
	 * ��ѯĳһ����վĳһ���������һ����������
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
		
		jsonArray=getData(query,sub_id,invert_id);//��ȡ����
		
		return jsonArray;
	
	
	}
	
	/**
	 * ��ѯĳһ����վһ�����������
	 * Ϊ�˱��ڽ����ݴ���JSONArray�У���Ҫ�뽫������ά������
	 * �ڴ���JSONArray��
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@Override
	@WebMethod
	public String getDayDataBySubId(String sub_id, String datetime) {
		
		String[]dateStrings=datetime.split("-");
		String query_invert="";//��ѯһ������վ�м��������
		datetime="";
		
		List<StationData> invert_data=null;//һ����վ��������ͺ�
		
		String total = "";//����ŵ�վ���������һ����������
		
		for(int i=0;i<dateStrings.length;i++){
			datetime=datetime+dateStrings[i];
		}
		
		//��ѯһ����վ�м��������
		query_invert="select distinct substid,inverterid from mininverter"
				+datetime+" where substid=?";
		
		invert_data=stationDataImpl.getAll(query_invert,sub_id);//��ȡһ����վ��Ӧ�����������
		
		total = AverageOfInverters(sub_id, datetime, invert_data);
			
			return total;
		}
	
	
	public String AverageOfInverters(String sub_id, String datetime,
			List<StationData> invert_data) {
		String[][] dataArray=null;//����������������ƽ��ֵ
		String query="";//��ѯ����վ�������������
		String data;//�������һ������
		String invertid="";//�������
		
		JSONObject jsonObject=null;//���ÿ�������������
		JSONObject meanObject=new JSONObject();//���һ����վ�������������ƽ��ֵ
		JSONArray totalArray = new JSONArray();//����ŵ�վ���������һ����������
		
		//����ÿ����������ݣ����ƽ��ֵ
		for(int i=0;i<invert_data.size();i++){//���������
			
			invertid=JSONObject.fromObject(//����������
					invert_data.get(i)).get("inverterid").toString();
			
			//��ѯÿ����������� 
			query="select * from mininverter"+datetime+
					" where substid=? and inverterid=? ";
			
			data=getData(query,sub_id,invertid);//�õ���i�����������
			
			JSONArray data_jsonArray=JSONArray.fromObject(data);//ת��ΪJSONArray����
			
			if (i==0) {
				dataArray=new String[data_jsonArray.size()][8];//��һ�η���ʱ���������С
				
			}	
			Iterator iterator=data_jsonArray.iterator();
			int j=0;
			while (iterator.hasNext()) {
				
				jsonObject=(JSONObject) iterator.next();//ÿ��JSONObject�����һ������
				dataArray=judgeNull(dataArray, j);//�����ʼֵ��null����Ϊ"0"
				
				dataArray[j][0]=jsonObject.get("hm").toString();
				
				
				//���������������ƽ��ֵ������������Ҫ��ƽ��ֵ
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
				
				
				if (invert_data.size()-1==i) {//���������ݼ���JSONArray��
					meanObject.put("hm", dataArray[j][0]);
					meanObject.put("voltage",dataArray[j][1]);//������ѹ
					meanObject.put("electricity",dataArray[j][2]);//��������
					meanObject.put("ele_power",dataArray[j][3]);//��������
					meanObject.put("dc_voltage",dataArray[j][4]);//ֱ����ѹ
					meanObject.put("dc_electricity",dataArray[j][5]);//ֱ������
					meanObject.put("dc_ele_power",dataArray[j][6]);//ֱ������
					meanObject.put("gener_capacity",dataArray[j][7]);//������
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
	 * ��ѯĳһ����վĳ�������һ���µķ�����
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */

public String getMonthDataByInvertId(String sub_id, String invert_id,String datetime) {
		
		String[]dateStrings=datetime.split("-");
		String yearmonth=dateStrings[0]+dateStrings[1];//��ȡ�·�
		double total=0;//һ���µķ�������
		int row=0;
		
		JSONArray dayArray=null;//һ�������
		JSONObject dayLast=null;//һ������һ������
		JSONObject daysObject=new JSONObject();//һ����ÿ��ķ�����
		HashMap<String, Boolean> getTables=null;
		
		getTables=getAllTableNames();//�õ����ݿ������еı���
		String str = "";
		String tableName=null;
		
		for (int j = 1; j <=31; j++) {
			if(j<10){
				str = "0"+j;
			}else{
				str = String.valueOf(j);
			}
			
			tableName="mininverter"+yearmonth+str;//����
			
			if(getTables.containsKey(tableName)){//�������ݿ����и�������ݱ�
				dayArray=JSONArray.fromObject(//��ȡһ�������
						getDayDataByInvertId(sub_id, invert_id, datetime+"-"+str));
				
				row=dayArray.size();
				
				if (row>0) {   //�����ж�Ӧid������
					dayLast=JSONObject //��һ������һ��תΪJSONObject����
							.fromObject(dayArray.get(row-1));
					
					total+=Double.parseDouble(dayLast //һ���µķ�������,�������һ���Ƿ���������������
							.get("gener_capacity").toString());
					
					daysObject.put(j,Double//һ��ķ�������
							.parseDouble(dayLast.get("gener_capacity").toString()));
				}
			}
		}
		
		daysObject.put("month_gener_capacity", total);//һ���µķ�������
		
		return daysObject.toString();
	}

	/**
	 * ��ѯĳ��һ��վһ���µķ�����
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@Override
	@WebMethod
	public String getMonthDataBySubId(String sub_id,String datetime) {
		
		String[]dateStrings=datetime.split("-");
		String yearmonth=dateStrings[0]+dateStrings[1];//��ȡ�·�
		double total=0;//һ���µķ�������
		
		JSONArray dayArray=null;//һ�������
		JSONObject dayLast=null;
		JSONObject daysObject=new JSONObject();//һ����ÿ��ķ�����
		HashMap<String, Boolean> getTables=null;//������б�
		
		int row=0;
		String str = "";
		String tableName="";

		getTables=getAllTableNames();//�õ����ݿ������еı���
		
		for (int j = 1; j <=31; j++) {
			if(j<10){
				str = "0"+j;
			}else{
				str = String.valueOf(j);
			}
			
				/////////
				///����///
				////////
			tableName="mininverter"+yearmonth+str;
			
			if(getTables.containsKey(tableName)){//�������ݿ����и�������ݱ�
				dayArray=JSONArray.fromObject(//��ȡһ�������
						getDayDataBySubId(sub_id, datetime+"-"+str));
				
				row=dayArray.size();
				
				if (row>0) {   //�����ж�Ӧid������
					dayLast=JSONObject //��һ������һ��תΪJSONObject����
							.fromObject(dayArray.get(row-1));
					
					total+=Double.parseDouble(dayLast //һ���µķ�������,�������һ���Ƿ���������������
							.get("gener_capacity").toString());

					daysObject.put(j,Double//һ��ķ�������
							.parseDouble(dayLast.get("gener_capacity").toString()));
				}
			}
		}
		
		daysObject.put("month_gener_capacity", total);//һ���µķ�������
		
		return daysObject.toString();
	}
	
	/**
	 * ��ѯĳһ����վĳһ�������һ��ķ�����
	 * @param sub_id
	 * @param invert_id
	 * @param datetime
	 * @return
	 */
	@WebMethod
	public String getYearDataByInvertId(String sub_id,
			String invert_id, String datetime) {
		
		double month_total_capacity=0;//ÿ���µ��ܷ�����
		double total=0;//һ��ķ�������
		String str="";
		
		JSONObject month_capacity=null;//һ���µķ�������
		JSONObject monthsObject=new JSONObject();//ÿ���µķ�������
		
		for(int i=1;i<=12;i++){
			
			if(i<10){
				str = "0"+i;
			}else{
				str = String.valueOf(i);
			}
			
			month_capacity=JSONObject.fromObject(//��ȡһ���µķ�������
					getMonthDataByInvertId(sub_id,invert_id,datetime+"-"+str));
			
			//��ΪgetMonthDataByInvertId()���������������·�������key��valueֵ��
			//��һ���·���������Ϊ��ʱ��month_capacity.size()=1
			
		if (month_capacity.size()>1) {
			
			month_total_capacity=Double.parseDouble(//��ȡ���µķ�������
					month_capacity.get("month_gener_capacity").toString());
			
			total+=month_total_capacity;//�ܷ�����
			
			monthsObject.put(i,month_total_capacity);//ÿ���µķ�������
			}else {
				monthsObject.put(i, 0.0);
			}
		
		}
		
		monthsObject.put("year_gener_capacity", total);//һ��ķ�������
		
		return monthsObject.toString();
	}
	
	/**
	 * ��ѯĳ����վһ��ķ�����
	 * @param sub_id
	 * @param datetime
	 * @return
	 */
	@Override
	@WebMethod
	public String getYearDataBySubId(String sub_id, String datetime) {
		
		double month_total_capacity=0;//ÿ���µ��ܷ�����
		double total=0;//һ��ķ�������
		String str="";
		
		JSONObject month_capacity=null;//һ���µķ�������
		JSONObject monthsObject=new JSONObject();//ÿ���µķ�������
		
		for(int i=1;i<=12;i++){
			if(i<10){
				str = "0"+i;
			}else{
				str = String.valueOf(i);
			}
			
			month_capacity=JSONObject.fromObject(//��ȡһ���µķ�������
					getMonthDataBySubId(sub_id,datetime+"-"+str));
			
		if (month_capacity.size()>1) {
			month_total_capacity=Double.parseDouble(//��ȡ���µķ�������
					month_capacity.get("month_gener_capacity").toString());
			
			total+=month_total_capacity;//һ��ķ�������
			monthsObject.put(i,month_total_capacity);//ÿ���µķ�������
			
			}else {
				monthsObject.put(i, 0.0);
			}
		}
		
		monthsObject.put("year_gener_capacity", total);//һ��ķ�������
		
		return monthsObject.toString();
	}

	@Override
	public String getYearsDataByInvertId(String sub_id,String invert_id) {
		
		double data=0;//ÿ��ķ�����
		double total=0;//����ķ�������
		
		JSONObject year_capacity=null;//һ��ķ�������
		JSONObject yearsObject=new JSONObject();//ÿ��ķ�������
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(df.format(new Date()));//��ȡ��ǰϵͳ���
		String str="";
		
		for(int i=2010;i<=year;i++){
			
			str=String.valueOf(i);
			
			year_capacity=JSONObject.fromObject(//��ȡһ��ķ�������
					getYearDataByInvertId(sub_id, invert_id, str));
			
			if(year_capacity.size()>1){
				data=Double.parseDouble(year_capacity//�õ�����ķ�������
						.get("year_gener_capacity").toString());
				
				total+=data;//���귢������
				
				yearsObject.put(i, data);//ÿ��ķ�����
				
			}else {
				yearsObject.put(i, 0.0);
			}
		}
		yearsObject.put("total_gener_capacity", total);//����ķ����ۼ�����
		
		return yearsObject.toString();
	}

	@Override
	public String getYearsDataBySubId(String sub_id) {
		
		double data=0;//ÿ��ķ�����
		double total=0;//����ķ�������
		
		JSONObject year_capacity=null;//һ��ķ�������
		JSONObject yearsObject=new JSONObject();//ÿ��ķ�������
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(df.format(new Date()));//��ȡ��ǰϵͳ���
		String str="";
		
		for(int i=2010;i<=year;i++){
			
			str=String.valueOf(i);
			
			year_capacity=JSONObject.fromObject(//��ȡһ��ķ�������
					getYearDataBySubId(sub_id, str));
			if(year_capacity.size()>1){
				
				data=Double.parseDouble(year_capacity//�õ�����ķ�������
						.get("year_gener_capacity").toString());
				
				total+=data;
				
				yearsObject.put(i, data);//ÿ��ķ���������
				
			}else {
				yearsObject.put(i, 0.0);
			}
		}
		yearsObject.put("total_gener_capacity", total);//����ķ����ۼ�����
		
		return yearsObject.toString();
	}
	/**
	 * ��ȡ��վ��Ϣ
	 */
	@Override
	public String getSubstationInfo() {
		
		String sql="select * from elec_station";

		List<StationInfo> stationInfoList = stationInfoImpl.getAll(sql);

		JSONObject stationInfo=null;//���һ����վ��Ϣ
		JSONArray stationsArray=new JSONArray();//������е�վ��Ϣ
		
		String path=findServerPath();
		
		for (int i = 0; i < stationInfoList.size(); i++) {
			
			//�м�¼ʱ����¼���浽�����в����뵽������
			//����ȡ�ü�¼�ĸ������ֵ
			stationInfo=new JSONObject();
			
			
			stationInfo.put("substid",stationInfoList.get(i).getSubid());//��վid
			stationInfo.put("invertid",stationInfoList.get(i).getInvertid());//������ͺ�
			stationInfo.put("subname",stationInfoList.get(i).getSubname());//��վ��
			stationInfo.put("latitude",stationInfoList.get(i).getLatitude());//����
			stationInfo.put("longitude",stationInfoList.get(i).getLongitude());//γ��
			stationInfo.put("address",stationInfoList.get(i).getAddress());//��վ��ַ
			stationInfo.put("company",stationInfoList.get(i).getCompany());//��˾��
			stationInfo.put("telnumber",stationInfoList.get(i).getTelnumber());//�绰
			stationInfo.put("imageurl",path+stationInfoList.get(i).getImage());//��վͼƬurl
			
			
			stationsArray.add(stationInfo);
		}
		return stationsArray.toString();
	}
	

	/**
	 * �õ���ƬתΪbyte����
	 */
	@Override
	public byte[][] getImage() {
		
		JSONArray stationsdata=JSONArray.//�õ����е�վ��Ϣ
				fromObject(getSubstationInfo());
		JSONObject stationInfo=null;
		
		int size=stationsdata.size();
		byte[][] databyte=new byte[size][];//�洢��Ƭ��ά����
		
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
     * ��ȡ�������˵�webapps·�� 
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
	  * ��ȡָ�����ݿ���û������б��� 
	  * @param conn �������ݿ���� 
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
				  // �����б�  
				  ResultSet rest = dbmd.getTables(null, null, null, new String[] { "TABLE" });  
				  // ��� table_name  
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
		 * �ж�����ĳ��ֵ�Ƿ�Ϊ��
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
