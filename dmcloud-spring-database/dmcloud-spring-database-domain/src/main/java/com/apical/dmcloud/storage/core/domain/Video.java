package com.apical.dmcloud.storage.core.domain;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.apical.dmcloud.commons.infra.ResourceSearchType;
import com.apical.dmcloud.commons.infra.ResourceType;
import com.apical.dmcloud.storage.core.VideoNotExistException;

@Entity
@Table(name = "cl_video")
public class Video extends Resource
{
	/**
	 * serialVersionUID = -456326603715574826L
	 */
	private static final long serialVersionUID = -456326603715574826L;

	@Override
	public String[] businessKeys()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 返回资源的类型
	 * @return ResourceType 资源类型
	 */
	@Override
	public ResourceType getType()
	{
		return ResourceType.Video;
	}
	
	/**
	 * 通过id来获取视频信息
	 * @param id 视频id
	 * @return 视频信息对象
	 */
	public static Video get(long id)
	{
		return getRepository().get(Video.class, id);
	}
	
	/**
	 * 删除视频信息
	 * @param videoId 资源id
	 * @return 视频是否成功
	 */
	public static boolean deleteById(long videoId)
	{
		String jpql = "delete from Video _video where _video.id=:id";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", videoId)
				.executeUpdate();
		if(count == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 是否存在视频信息
	 * @param videoId 视频id
	 * @return 是否存在
	 */
	public static boolean existById(long videoId)
	{
		String sql = "select 1 from Video _video where _video.id = :id";
		Integer count = getRepository().createSqlQuery(sql.toString())
				.addParameter("id", videoId)
				.singleResult();
		if(count != null && count == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 获取视频列表
	 * @param ids id列表
	 * @return 视频列表
	 */
	public static List<Video> getByIdList(List<Long> ids)
	{
		if(ids != null && ids.size() > 0)
		{
			String jpql = "select _video from Video _video where _video.id in (:ids)";
			List<Video> videos = getRepository().createJpqlQuery(jpql.toString())
					.addParameter("ids", ids)
					.list();
			return videos;
		}
		else
		{
			return new ArrayList<Video>();
		}
	}
	
	/**
	 * 获取视频的所属设备id
	 * @param id 视频id
	 * @return 设备id
	 * @throws VideoNotExistException 
	 */
	public static long getDeviceId(long id) throws VideoNotExistException
	{
		String jpql = "select _video.deviceId from Video _video where _video.id=:id";
		Long deviceId = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("id", id)
                .singleResult();
    	if(deviceId == null)
    	{
    		throw new VideoNotExistException("picure:" + id + " not exist!");
    	}
    	
    	return deviceId;
	}
	
	/**
	 * 获取视频的存储信息id
	 * @param id 视频id
	 * @return 存储信息id
	 */
	public static Long getResourceStorageInfoId(long id)
	{
		String jpql = "select _video.resourceStorageInfoId from Video _video where _video.id=:id";
		Long infoid = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("id", id)
                .singleResult();
    	
    	return infoid;
	}
	
	/**
	 * 修改视频的文件名
	 * @param id 视频id
	 * @param filename 文件名
	 */
	public static boolean saveFileNameById(long id, String filename)
	{
		String jpql = "update Video _video set _video.filename=:filename where _video.id=:id";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("filename", filename)
				.addParameter("id", id)
				.executeUpdate();
		if(count == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 获取视频的文件名
	 * @param id 视频id
	 * @return 文件名
	 */
	public static String getFileNameById(long id)
	{
		String jpql = "select _video.filename from Video _video where _video.id=:id";
		String filename = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", id)
				.singleResult();
		return filename;
	}
	
	/**
	 * 修改视频的描述信息
	 * @param id 视频id
	 * @param description 描述信息
	 */
	public static boolean saveDescriptionById(long id, String description)
	{
		String jpql = "update Video _video set _video.description=:description where _video.id=:id";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("description", description)
				.addParameter("id", id)
				.executeUpdate();
		if(count == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 获取视频的描述信息
	 * @param id 视频id
	 * @return 描述信息
	 */
	public static String getDescriptionById(long id)
	{
		String jpql = "select _video.description from Video _video where _video.id=:id";
		String description = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", id)
				.singleResult();
		return description;
	}
	
	/**
	 * 获取车辆有视频的天数
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @return 天数
	 */
	public static long countAllDaysByVehicleId(long vehicleId, ResourceSearchType type)
	{
		String sql = null;
		switch(type)
		{
		case All:
			sql = "select count(1) from (select 1 from cl_video where VEHICLE_ID=:vehicleId"
					+ " group by UPLOADDATE) f";
			break;
			
		case Normal:
			sql = "select count(1) from (select 1 from cl_video where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=0 group by UPLOADDATE) f";
			break;
			
		case Alarm:
			sql = "select count(1) from (select 1 from cl_video where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=1 group by UPLOADDATE) f";
			break;
			
		default:
			break;
		}
		
		if(sql != null)
		{
			BigInteger bi = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
					.singleResult();
			return bi.longValue();
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * 获取车辆有视频的天数
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return 天数
	 */
	public static long countAllDaysByVehicleIdAndTimerange(long vehicleId, ResourceSearchType type,
			Date startDate, Date endDate)
	{
		String sql = null;
		switch(type)
		{
		case All:
			sql = "select count(1) from (select 1 from cl_video where VEHICLE_ID=:vehicleId"
					+ " and UPLOADDATE between :beginDate and :endDate group by UPLOADDATE) f";
			break;
			
		case Normal:
			sql = "select count(1) from (select 1 from cl_video where VEHICLE_ID=:vehicleId and IS_URGENT=0"
					+ " and UPLOADDATE between :beginDate and :endDate group by UPLOADDATE) f";
			break;
			
		case Alarm:
			sql = "select count(1) from (select 1 from cl_video where VEHICLE_ID=:vehicleId and IS_URGENT=1"
					+ " and UPLOADDATE between :beginDate and :endDate group by UPLOADDATE) f";
			break;
			
		default:
			break;
		}
		
		if(sql != null)
		{
			BigInteger bi = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
					.addParameter("beginDate", startDate)
					.addParameter("endDate", endDate)
					.singleResult();
			return bi.longValue();
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * 获取车辆视频信息
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @return 报警视频信息
	 */
	@SuppressWarnings({ "rawtypes" })
	public static List<ResourceIndexInDay> queryAllDaysByVehicleId(long vehicleId, ResourceSearchType type)
	{
		String sql = null;
		switch(type)
		{
		case All:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Normal:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=0"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Alarm:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=1"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		default:
			break;
		}
		
		if(sql != null)
		{
			List list = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
					.list();
			List<ResourceIndexInDay> days = generateDayIndex(vehicleId, list);
			
			return days;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 获取车辆视频信息
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return 报警视频信息
	 */
	@SuppressWarnings({ "rawtypes" })
	public static List<ResourceIndexInDay> queryAllDaysByVehicleIdAndTimerange(long vehicleId,
			ResourceSearchType type, Date startDate, Date endDate)
	{
		String sql = null;
		switch(type)
		{
		case All:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Normal:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=0"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Alarm:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=1"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		default:
			break;
		}
		
		if(sql != null)
		{
			List list = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
					.addParameter("beginDate", startDate)
					.addParameter("endDate", endDate)
					.list();
			List<ResourceIndexInDay> days = generateDayIndex(vehicleId, list);
			
			return days;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 分页获取车辆按天索引信息
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 按天索引信息
	 */
	@SuppressWarnings("rawtypes")
	public static List<ResourceIndexInDay> queryAllDaysByVehicleIdInPage(long vehicleId, ResourceSearchType type,
			int pageCount, int pageSize)
	{
		String sql = null;
		switch(type)
		{
		case All:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Normal:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=0"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Alarm:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=1"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		default:
			break;
		}
		
		if(sql != null)
		{
			List list = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
					.setMaxResults(pageSize)
					.setFirstResult((pageCount - 1) * pageSize)
					.list();
			
			List<ResourceIndexInDay> days = generateDayIndex(vehicleId, list);
	
			return days;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 分页获取车辆按天索引信息
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 按天索引信息
	 */
	@SuppressWarnings("rawtypes")
	public static List<ResourceIndexInDay> queryAllDaysByVehicleIdAndTimerangeInPage(long vehicleId,
			ResourceSearchType type, Date startDate, Date endDate, int pageCount, int pageSize)
	{
		String sql = null;
		switch(type)
		{
		case All:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Normal:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=0"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Alarm:
			sql = "select count(id),UPLOADDATE from cl_video"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=1"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		default:
			break;
		}
		
		if(sql != null)
		{
			List list = getRepository().createSqlQuery(sql)
					.addParameter("vehicleId", vehicleId)
					.addParameter("beginDate", startDate)
					.addParameter("endDate", endDate)
					.setMaxResults(pageSize)
					.setFirstResult((pageCount - 1) * pageSize)
					.list();
			
			List<ResourceIndexInDay> days = generateDayIndex(vehicleId, list);
	
			return days;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 按天获取车辆的视频
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param year 年份
	 * @param month 月份
	 * @param day 天数
	 * @return 视频
	 * @throws ParseException 
	 */
	public static List<Video> queryAllVideosByVehicleId(long vehicleId, ResourceSearchType type,
			int year, int month, int day) throws ParseException
	{
		String dateContext = Integer.toString(year) + "-" + String.format("%02d", month) + "-" +
				String.format("%02d", day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(dateContext);
		
		String jpql = null;
		switch(type)
		{
		case All:
			jpql = "select _video from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.uploadDate=:uploaddate"
					+ " order by _video.uploadTime desc";
			break;
			
		case Normal:
			jpql = "select _video from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.isUrgent=false"
					+ " and _video.uploadDate=:uploaddate"
					+ " order by _video.uploadTime desc";
			break;
			
		case Alarm:
			jpql = "select _video from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.isUrgent=true"
					+ " and _video.uploadDate=:uploaddate"
					+ " order by _video.uploadTime desc";
			break;
			
		default:
			break;
		}
		
		if(jpql != null)
		{
			List<Video> videos = getRepository().createJpqlQuery(jpql)
					.addParameter("vehicleId", vehicleId)
					.addParameter("uploaddate", date)
					.list();
	
			return videos;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 按天分页获取车辆的视频
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param year 年份
	 * @param month 月份
	 * @param day 天数
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 视频
	 * @throws ParseException 
	 */
	public static List<Video> queryAllVideosByVehicleIdInPage(long vehicleId, ResourceSearchType type,
			int year, int month, int day, int pageCount, int pageSize) throws ParseException
	{
		String dateContext = Integer.toString(year) + "-" + String.format("%02d", month) + "-" +
				String.format("%02d", day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(dateContext);
		
		String jpql = null;
		switch(type)
		{
		case All:
			jpql = "select _video from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.uploadDate=:uploaddate"
					+ " order by _video.uploadTime desc";
			break;
			
		case Normal:
			jpql = "select _video from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.isUrgent=false"
					+ " and _video.uploadDate=:uploaddate"
					+ " order by _video.uploadTime desc";
			break;
			
		case Alarm:
			jpql = "select _video from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.isUrgent=true"
					+ " and _video.uploadDate=:uploaddate"
					+ " order by _video.uploadTime desc";
			break;
			
		default:
			break;
		}
		
		if(jpql != null)
		{
			List<Video> videos = getRepository().createJpqlQuery(jpql)
					.addParameter("vehicleId", vehicleId)
					.addParameter("uploaddate", date)
					.setMaxResults(pageSize)
					.setFirstResult((pageCount - 1) * pageSize)
					.list();
	
			return videos;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 获取视频数量
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return 数量
	 */
	public static long countAllVideosByVehicleIdAndTimerange(long vehicleId, ResourceSearchType type, Short cameraId,
			Date startDate, Date endDate)
	{
		/*String jpql = null;
		switch(type)
		{
		case All:
			jpql = "select count(_video) from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.uploadTime>=:beginDate"
					+ " and _video.uploadTime<=:endDate"
					+ " order by _video.uploadTime desc";
			break;
			
		case Normal:
			jpql = "select count(_video) from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.isUrgent=false"
					+ " and _video.uploadTime>=:beginDate"
					+ " and _video.uploadTime<=:endDate"
					+ " order by _video.uploadTime desc";
			break;
			
		case Alarm:
			jpql = "select count(_video) from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.isUrgent=true"
					+ " and _video.uploadTime>=:beginDate"
					+ " and _video.uploadTime<=:endDate"
					+ " order by _video.uploadTime desc";
			break;
			
		default:
			break;
		}
		
		if(jpql != null)
		{
			Long count = getRepository().createJpqlQuery(jpql)
					.addParameter("vehicleId", vehicleId)
					.addParameter("beginDate", startDate)
					.addParameter("endDate", endDate)
					.singleResult();
			return count;
		}
		else
		{
			return 0;
		}*/
		StringBuilder jpql  = new StringBuilder();
		jpql.append("select count(_video) from Video _video")
				.append(" where _video.vehicleId=:vehicleId");
		if(cameraId != null){
			jpql.append(" and _video.cameraIndex=:cameraIndex");
		}
		if(ResourceSearchType.Normal.equals(type)){
			jpql.append(" and _video.isUrgent=false");
		}else if(ResourceSearchType.Alarm.equals(type)){
			jpql.append(" and _video.isUrgent=true");
		}
		jpql.append(" and _video.uploadTime>=:beginDate")
				.append(" and _video.uploadTime<=:endDate")
				.append(" order by _video.uploadTime desc");
		Long count = 0L;
		if(cameraId != null){
			count = getRepository().createJpqlQuery(jpql.toString())
					.addParameter("vehicleId", vehicleId)
					.addParameter("cameraIndex", cameraId)
					.addParameter("beginDate", startDate)
					.addParameter("endDate", endDate)
					.singleResult();
		}else{
			count = getRepository().createJpqlQuery(jpql.toString())
					.addParameter("vehicleId", vehicleId)
					.addParameter("beginDate", startDate)
					.addParameter("endDate", endDate)
					.singleResult();
		}
		return count;
	}
	
	/**
	 * 按天分页获取车辆的视频
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 视频
	 * @throws ParseException 
	 */
	public static List<Video> queryAllVideosByVehicleIdInPage(long vehicleId, ResourceSearchType type, Short cameraId,
			Date startDate, Date endDate, int pageCount, int pageSize) throws ParseException
	{
		/*switch(type)
		{
		case All:
			jpql = "select _video from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.uploadTime>=:beginDate"
					+ " and _video.uploadTime<=:endDate"
					+ " order by _video.uploadTime desc";
			break;
			
		case Normal:
			jpql = "select _video from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.isUrgent=false"
					+ " and _video.uploadTime>=:beginDate"
					+ " and _video.uploadTime<=:endDate"
					+ " order by _video.uploadTime desc";
			break;
			
		case Alarm:
			jpql = "select _video from Video _video"
					+ " where _video.vehicleId=:vehicleId"
					+ " and _video.isUrgent=true"
					+ " and _video.uploadTime>=:beginDate"
					+ " and _video.uploadTime<=:endDate"
					+ " order by _video.uploadTime desc";
			break;
			
		default:
			break;
		}*/
		StringBuilder jpql = new StringBuilder();
		jpql.append("select _video from Video _video")
				.append(" where _video.vehicleId=:vehicleId");
		if(cameraId != null){
			jpql.append(" and _video.cameraIndex=:cameraIndex");
		}
		if(ResourceSearchType.Normal.equals(type)){
			jpql.append(" and _video.isUrgent=false");
		}else if(ResourceSearchType.Alarm.equals(type)){
			jpql.append(" and _video.isUrgent=true");
		}
		jpql.append(" and _video.uploadTime>=:beginDate")
				.append(" and _video.uploadTime<=:endDate")
				.append(" order by _video.uploadTime desc");
		List<Video> videos = null;
		if(cameraId != null){
			videos = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.addParameter("cameraIndex", cameraId)
				.addParameter("beginDate", startDate)
				.addParameter("endDate", endDate)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		}else{
			videos = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.addParameter("beginDate", startDate)
				.addParameter("endDate", endDate)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		}

		return videos;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	private static List<ResourceIndexInDay> generateDayIndex(long vehicleId, List list)
	{
		List<ResourceIndexInDay> days = new ArrayList<ResourceIndexInDay>();
		for(Iterator it = list.iterator(); it.hasNext();)
		{
			Object[] obs = (Object[])it.next();
			ResourceIndexInDay day = new ResourceIndexInDay();
			day.setVehicleId(vehicleId);
			
			BigInteger videocount = (BigInteger)obs[0];
			day.setVideoCount(videocount.longValue());
			
			java.sql.Date date = (java.sql.Date)obs[1];
			day.setYear(date.getYear() + 1900);
			day.setMonth(date.getMonth() + 1);
			day.setDay(date.getDate());
			
			days.add(day);
		}
		return days;
	}
}
