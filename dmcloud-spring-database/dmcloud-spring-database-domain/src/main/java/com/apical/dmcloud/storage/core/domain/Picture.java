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
import com.apical.dmcloud.storage.core.PictureNotExistException;

@Entity
@Table(name = "cl_picture")
public class Picture extends Resource
{
	/**
	 * serialVersionUID = -456326603715574826L
	 */
	private static final long serialVersionUID = -456326603715574826L;

	@Override
	public String[] businessKeys()
	{
		// TODO Auto-generated method stub
		return new String[]{"Picture"};
	}
	
	/**
	 * 返回资源的类型
	 * @return ResourceType 资源类型
	 */
	@Override
	public ResourceType getType()
	{
		return ResourceType.Picture;
	}
	
	/**
	 * 通过id来获取图片信息
	 * @param id 图片id
	 * @return 图片信息对象
	 */
	public static Picture get(long id)
	{
		return getRepository().get(Picture.class, id);
	}
	
	/**
	 * 删除图片信息
	 * @param pictureId 资源id
	 * @return 删除是否成功
	 */
	public static boolean deleteById(long pictureId)
	{
		String jpql = "delete from Picture _picture"
				+ " where _picture.id=:id";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", pictureId)
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
	 * 是否存在图片信息
	 * @param pictureId 资源id
	 * @return 是否存在
	 */
	public static boolean existById(long pictureId)
	{
		String sql = "select 1 from Picture _picture"
				+ " where _picture.id = :id";
		Integer count = getRepository().createSqlQuery(sql.toString())
				.addParameter("id", pictureId)
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
	 * 获取图片列表
	 * @param ids id列表
	 * @return 图片列表
	 */
	public static List<Picture> getByIdList(List<Long> ids)
	{
		if(ids != null && ids.size() > 0)
		{
			String jpql = "select _picture from Picture _picture"
					+ " where _picture.id in (:ids)";
			List<Picture> pictures = getRepository().createJpqlQuery(jpql.toString())
					.addParameter("ids", ids)
					.list();
			return pictures;
		}
		else
		{
			return new ArrayList<Picture>();
		}
	}
	
	/**
	 * 获取图片的所属设备id
	 * @param id 图片id
	 * @return 设备id
	 * @throws PictureNotExistException 
	 */
	public static long getDeviceId(long id) throws PictureNotExistException
	{
		String jpql = "select _picture.deviceId from Picture _picture"
				+ " where _picture.id=:id";
		Long deviceId = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("id", id)
                .singleResult();
    	if(deviceId == null)
    	{
    		throw new PictureNotExistException("picure:" + id + " not exist!");
    	}
    	
    	return deviceId;
	}
	
	/**
	 * 获取图片的存储信息id
	 * @param id 图片id
	 * @return 存储信息id
	 */
	public static Long getResourceStorageInfoId(long id)
	{
		String jpql = "select _picture.resourceStorageInfoId from Picture _picture"
				+ " where _picture.id=:id";
		Long infoid = getRepository().createJpqlQuery(jpql.toString())
    			.addParameter("id", id)
                .singleResult();
    	
    	return infoid;
	}
	
	/**
	 * 修改图片的文件名
	 * @param id 图片id
	 * @param filename 文件名
	 */
	public static boolean saveFileNameById(long id, String filename)
	{
		String jpql = "update Picture _picture set _picture.filename=:filename"
				+ " where _picture.id=:id";
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
	 * 获取图片的文件名
	 * @param id 图片id
	 * @return 文件名
	 */
	public static String getFileNameById(long id)
	{
		String jpql = "select _picture.filename from Picture _picture"
				+ " where _picture.id=:id";
		String filename = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", id)
				.singleResult();
		return filename;
	}
	
	/**
	 * 修改图片的描述信息
	 * @param id 图片id
	 * @param description 描述信息
	 */
	public static boolean saveDescriptionById(long id, String description)
	{
		String jpql = "update Picture _picture set _picture.description=:description"
				+ " where _picture.id=:id";
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
	 * 获取图片的描述信息
	 * @param id 图片id
	 * @return 描述信息
	 */
	public static String getDescriptionById(long id)
	{
		String jpql = "select _picture.description from Picture _picture"
				+ " where _picture.id=:id";
		String description = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", id)
				.singleResult();
		return description;
	}
	
	/**
	 * 获取车辆有图片的天数
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
			sql = "select count(1) from (select 1 from cl_picture where VEHICLE_ID=:vehicleId"
					+ " group by UPLOADDATE) f";
			break;
			
		case Normal:
			sql = "select count(1) from (select 1 from cl_picture where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=0 group by UPLOADDATE) f";
			break;
			
		case Alarm:
			sql = "select count(1) from (select 1 from cl_picture where VEHICLE_ID=:vehicleId"
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
	 * 获取车辆有图片的天数
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
			sql = "select count(1) from (select 1 from cl_picture where VEHICLE_ID=:vehicleId"
					+ " and UPLOADDATE between :beginDate and :endDate group by UPLOADDATE) f";
			break;
			
		case Normal:
			sql = "select count(1) from (select 1 from cl_picture where VEHICLE_ID=:vehicleId and IS_URGENT=0"
					+ " and UPLOADDATE between :beginDate and :endDate group by UPLOADDATE) f";
			break;
			
		case Alarm:
			sql = "select count(1) from (select 1 from cl_picture where VEHICLE_ID=:vehicleId and IS_URGENT=1"
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
	 * 获取车辆按天索引信息
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @return 按天索引信息
	 */
	@SuppressWarnings("rawtypes")
	public static List<ResourceIndexInDay> queryAllDaysByVehicleId(long vehicleId, ResourceSearchType type)
	{
		String sql = null;
		switch(type)
		{
		case All:
			sql = "select count(id),UPLOADDATE from cl_picture"
					+ " where VEHICLE_ID=:vehicleId"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Normal:
			sql = "select count(id),UPLOADDATE from cl_picture"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=0"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Alarm:
			sql = "select count(id),UPLOADDATE from cl_picture"
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
	 * 获取车辆按天索引信息
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return 按天索引信息
	 */
	@SuppressWarnings("rawtypes")
	public static List<ResourceIndexInDay> queryAllDaysByVehicleIdAndTimerange(long vehicleId,
			ResourceSearchType type, Date startDate, Date endDate)
	{
		String sql = null;
		switch(type)
		{
		case All:
			sql = "select count(id),UPLOADDATE from cl_picture"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Normal:
			sql = "select count(id),UPLOADDATE from cl_picture"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=0"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Alarm:
			sql = "select count(id),UPLOADDATE from cl_picture"
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
			sql = "select count(id),UPLOADDATE from cl_picture"
					+ " where VEHICLE_ID=:vehicleId"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Normal:
			sql = "select count(id),UPLOADDATE from cl_picture"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=0"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Alarm:
			sql = "select count(id),UPLOADDATE from cl_picture"
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
	 * @param startDate 起始日期
	 * @param endDate 结束日期
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
			sql = "select count(id),UPLOADDATE from cl_picture"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE"
					+ " order by UPLOADDATE desc";
			break;
			
		case Normal:
			sql = "select count(id),UPLOADDATE from cl_picture"
					+ " where VEHICLE_ID=:vehicleId"
					+ " and IS_URGENT=0 and UPLOADDATE between :beginDate and :endDate"
					+ " group by UPLOADDATE order by UPLOADDATE desc";
			break;
			
		case Alarm:
			sql = "select count(id),UPLOADDATE from cl_picture"
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
	 * 按天获取车辆的图片
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param year 年份
	 * @param month 月份
	 * @param day 天数
	 * @return 图片
	 * @throws ParseException 
	 */
	public static List<Picture> queryAllPicturesByVehicleId(long vehicleId, ResourceSearchType type,
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
			jpql = "select _picture from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.uploadDate=:uploaddate"
					+ " order by _picture.uploadTime desc";
			break;
			
		case Normal:
			jpql = "select _picture from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.isUrgent=false"
					+ " and _picture.uploadDate=:uploaddate"
					+ " order by _picture.uploadTime desc";
			break;
			
		case Alarm:
			jpql = "select _picture from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.isUrgent=true"
					+ " and _picture.uploadDate=:uploaddate"
					+ " order by _picture.uploadTime desc";
			break;
			
		default:
			break;
		}
		
		if(jpql != null)
		{
			List<Picture> pictures = getRepository().createJpqlQuery(jpql)
					.addParameter("vehicleId", vehicleId)
					.addParameter("uploaddate", date)
					.list();
	
			return pictures;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 按天分页获取车辆的图片
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param year 年份
	 * @param month 月份
	 * @param day 天数
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 图片
	 * @throws ParseException 
	 */
	public static List<Picture> queryAllPicturesByVehicleIdInPage(long vehicleId, ResourceSearchType type,
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
			jpql = "select _picture from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.uploadDate=:uploaddate"
					+ " order by _picture.uploadTime desc";
			break;
			
		case Normal:
			jpql = "select _picture from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.isUrgent=false"
					+ " and _picture.uploadDate=:uploaddate"
					+ " order by _picture.uploadTime desc";
			break;
			
		case Alarm:
			jpql = "select _picture from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.isUrgent=true"
					+ " and _picture.uploadDate=:uploaddate"
					+ " order by _picture.uploadTime desc";
			break;
			
		default:
			break;
		}
		
		if(jpql != null)
		{
			List<Picture> pictures = getRepository().createJpqlQuery(jpql)
					.addParameter("vehicleId", vehicleId)
					.addParameter("uploaddate", date)
					.setMaxResults(pageSize)
					.setFirstResult((pageCount - 1) * pageSize)
					.list();
	
			return pictures;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 获取图片数量
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return 数量
	 */
	public static long countAllPicturesByVehicleIdAndTimerange(long vehicleId, ResourceSearchType type, Short cameraId,
			Date startDate, Date endDate)
	{
		/*String jpql = null;
		switch(type)
		{
		case All:
			jpql = "select count(_picture) from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.uploadTime>=:beginDate"
					+ " and _picture.uploadTime<=:endDate"
					+ " order by _picture.uploadTime desc";
			break;
			
		case Normal:
			jpql = "select count(_picture) from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.isUrgent=false"
					+ " and _picture.uploadTime>=:beginDate"
					+ " and _picture.uploadTime<=:endDate"
					+ " order by _picture.uploadTime desc";
			break;
			
		case Alarm:
			jpql = "select count(_picture) from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.isUrgent=true"
					+ " and _picture.uploadTime>=:beginDate"
					+ " and _picture.uploadTime<=:endDate"
					+ " order by _picture.uploadTime desc";
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
		jpql.append("select count(_picture) from Picture _picture")
				.append(" where _picture.vehicleId=:vehicleId");
		if(cameraId != null){
			jpql.append(" and _picture.cameraId=:cameraId");
		}
		if(ResourceSearchType.Normal.equals(type)){
			jpql.append(" and _picture.isUrgent=false");
		}else if(ResourceSearchType.Alarm.equals(type)){
			jpql.append(" and _picture.isUrgent=true");
		}
		jpql.append(" and _picture.uploadTime>=:beginDate")
				.append(" and _picture.uploadTime<=:endDate")
				.append(" order by _picture.uploadTime desc");
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.addParameter("cameraId", cameraId)
				.addParameter("beginDate", startDate)
				.addParameter("endDate", endDate)
				.singleResult();
		return count;
	}
	
	/**
	 * 按天分页获取车辆的图片
	 * @param vehicleId 车辆id
	 * @param type 搜索类型
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 图片
	 * @throws ParseException 
	 */
	public static List<Picture> queryAllPicturesByVehicleIdInPage(long vehicleId, ResourceSearchType type, Short cameraId,
			Date startDate, Date endDate, int pageCount, int pageSize) throws ParseException
	{
		/*String jpql = null;
		switch(type)
		{
		case All:
			jpql = "select _picture from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.uploadTime>=:beginDate"
					+ " and _picture.uploadTime<=:endDate"
					+ " order by _picture.uploadTime desc";
			break;
			
		case Normal:
			jpql = "select _picture from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.isUrgent=false"
					+ " and _picture.uploadTime>=:beginDate"
					+ " and _picture.uploadTime<=:endDate"
					+ " order by _picture.uploadTime desc";
			break;
			
		case Alarm:
			jpql = "select _picture from Picture _picture"
					+ " where _picture.vehicleId=:vehicleId"
					+ " and _picture.isUrgent=true"
					+ " and _picture.uploadTime>=:beginDate"
					+ " and _picture.uploadTime<=:endDate"
					+ " order by _picture.uploadTime desc";
			break;
			
		default:
			break;
		}
		
		if(jpql != null)
		{
			List<Picture> pictures = getRepository().createJpqlQuery(jpql)
					.addParameter("vehicleId", vehicleId)
					.addParameter("beginDate", startDate)
					.addParameter("endDate", endDate)
					.setMaxResults(pageSize)
					.setFirstResult((pageCount - 1) * pageSize)
					.list();
	
			return pictures;
		}
		else
		{
			return null;
		}*/
		StringBuilder jpql = new StringBuilder();
		jpql.append("select _picture from Picture _picture")
				.append(" where _picture.vehicleId=:vehicleId");
		if(cameraId != null){
			jpql.append(" and _picture.cameraId=:cameraId");
		}
		if(ResourceSearchType.Normal.equals(type)){
			jpql.append(" and _picture.isUrgent=false");
		}else if(ResourceSearchType.Alarm.equals(type)){
			jpql.append(" and _picture.isUrgent=true");
		}
		jpql.append(" and _picture.uploadTime>=:beginDate")
				.append(" and _picture.uploadTime<=:endDate")
				.append(" order by _picture.uploadTime desc");
		List<Picture> pictures = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("vehicleId", vehicleId)
				.addParameter("cameraId", cameraId)
				.addParameter("beginDate", startDate)
				.addParameter("endDate", endDate)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return pictures;
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
			
			BigInteger picturecount = (BigInteger)obs[0];
			day.setPictureCount(picturecount.longValue());
			
			java.sql.Date date = (java.sql.Date)obs[1];
			day.setYear(date.getYear() + 1900);
			day.setMonth(date.getMonth() + 1);
			day.setDay(date.getDate());
			
			days.add(day);
		}
		return days;
	}
}
