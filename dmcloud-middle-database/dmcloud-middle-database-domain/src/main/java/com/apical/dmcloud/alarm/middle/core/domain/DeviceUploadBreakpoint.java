package com.apical.dmcloud.alarm.middle.core.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apical.dmcloud.middle.infra.AbstractVersionEntity;

/**
 * 设备断点信息表
 * @author qiuzeng
 *
 */
@Entity(name = "Middle.DeviceUploadBreakpoint")
@Table(name = "cl_breakpoint_device")
public class DeviceUploadBreakpoint extends AbstractVersionEntity
{
	/**
	 * serialVersionUID = 5725476884392729974L;
	 */
	private static final long serialVersionUID = 5725476884392729974L;

	/**
	 * 设备ID
	 */
	@Column(name = "DEVICE_ID")
	private Long deviceId = null;
	
	/**
	 * 服务器端文件存储路径
	 */
	@Column(name = "SERVER_PATH")
	private String serverFilePath = null;
	
	/**
	 * 客户端文件存储路径
	 */
	@Column(name = "CLIENT_PATH")
	private String clientFilePath = null;
	
	/**
	 * 文件大小
	 */
	@Column(name = "FILESIZE")
	private Long fileSize = null;
	
	/**
	 * 文件类型
	 */
	@Column(name = "FILETYPE")
	private Integer fileType = 0;
	
	/**
	 * 断点日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIME")
	private Date breakpointTime = null;
	
	/**
	 * 断点结束偏移量
	 */
	@Column(name = "OFFSET")
	private Long breakpointOffset = null;
	
	/**
	 * 上传百分比
	 */
	@Column(name = "PERCENT")
	private Float uploadPercent = null;
	
	/**
	 * 经度
	 */
	@Column(name = "LONGITUDE")
	private Float longitude = null;
	
	/**
	 * 纬度
	 */
	@Column(name = "LATITUDE")
	private Float latitude = null;
	
	public DeviceUploadBreakpoint()
	{
	}
	
	/**
	 * 获取设备ID
	 * @return 设备ID
	 */
	public Long getDeviceId() {
		return deviceId;
	}

	/**
	 * 设置设备ID
	 * @param deviceId 设备ID
	 */
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	/**
	 * 获取服务器端文件存储路径
	 * @return 服务器端文件存储路径
	 */
	public String getServerFilePath()
	{
		return serverFilePath;
	}

	/**
	 * 设置服务器端文件存储路径
	 * @param serverFilePath 服务器端文件存储路径
	 */
	public void setServerFilePath(String serverFilePath)
	{
		this.serverFilePath = serverFilePath;
	}

	/**
	 * 获取客户端文件存储路径
	 * @return 客户端文件存储路径
	 */
	public String getClientFilePath()
	{
		return clientFilePath;
	}

	/**
	 * 设置客户端文件存储路径
	 * @param clientFilePath 客户端文件存储路径
	 */
	public void setClientFilePath(String clientFilePath)
	{
		this.clientFilePath = clientFilePath;
	}

	/**
	 * 获取断点时间
	 * @return 断点时间
	 */
	public Date getBreakpointTime()
	{
		return breakpointTime;
	}

	/**
	 * 设置断点时间
	 * @param breakpointTime 断点时间
	 */
	public void setBreakpointTime(Date breakpointTime)
	{
		this.breakpointTime = breakpointTime;
	}

	/**
	 * 获取文件大小
	 * @return 文件大小
	 */
	public Long getFileSize()
	{
		return fileSize;
	}

	/**
	 * 设置文件大小
	 * @param fileSize - 文件大小
	 */
	public void setFileSize(Long fileSize)
	{
		this.fileSize = fileSize;
	}

	/**
	 * 获取文件类型
	 * @return 文件类型
	 */
	public Integer getFileType()
	{
		return fileType;
	}

	/**
	 * 设置文件类型
	 * @param fileType 文件类型
	 */
	public void setFileType(Integer fileType)
	{
		this.fileType = fileType;
	}

	/**
	 * 获取断点结束偏移量
	 * @return 断点结束偏移量
	 */
	public Long getBreakpointOffset()
	{
		return breakpointOffset;
	}

	/**
	 * 设置断点结束偏移量
	 * @param breakpointOffset - 断点结束偏移量
	 */
	public void setBreakpointOffset(Long breakpointOffset)
	{
		this.breakpointOffset = breakpointOffset;
	}

	/**
	 * 获取上传百分比
	 * @return 上传百分比
	 */
	public Float getUploadPercent()
	{
		return uploadPercent;
	}

	/**
	 * 设置上传百分比
	 * @param uploadPercent - 上传百分比
	 */
	public void setUploadPercent(Float uploadPercent)
	{
		this.uploadPercent = uploadPercent;
	}

	/**
	 * 获取经度
	 * @return 经度
	 */
	public Float getLongitude()
	{
		return longitude;
	}

	/**
	 * 设置经度
	 * @param longitude 经度
	 */
	public void setLongitude(Float longitude)
	{
		this.longitude = longitude;
	}

	/**
	 * 获取纬度
	 * @return 纬度
	 */
	public Float getLatitude()
	{
		return latitude;
	}

	/**
	 * 设置纬度
	 * @param latitude 纬度
	 */
	public void setLatitude(Float latitude)
	{
		this.latitude = latitude;
	}
	
	@Override
	public String[] businessKeys()
	{
		// TODO Auto-generated method stub
		return new String[]{"DeviceUploadBreakpoint"};
	}
	
	/**
	 * 根据id来获取断点信息
	 * @param id 断点信息id
	 * @return 断点信息
	 */
	public static DeviceUploadBreakpoint getById(long id)
	{
		return get(DeviceUploadBreakpoint.class, id);
	}
	
	/**
	 * 根据id来删除断点信息
	 * @param id 断点信息id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from Middle.DeviceUploadBreakpoint _record where _record.id=:id";
		int count = getRepository().createJpqlQuery(jpql.toString())
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
	 * 根据设备id和服务器端文件路径，来删除断点信息
	 * @param deviceId 设备id
	 * @param serverFilePath 服务器端文件路径
	 * @return 是否删除成功
	 */
	public static boolean deleteByServerFilePath(long deviceId, String serverFilePath)
	{
		String jpql = "delete from Middle.DeviceUploadBreakpoint _point where _point.deviceId=:productId and _point.serverFilePath=:filepath";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.addParameter("filepath", serverFilePath)
				.executeUpdate();
		if(count > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 根据产品id和服务器端文件路径，来判断断点信息是否存在
	 * @param deviceId 设备id
	 * @param serverFilePath 服务器端文件路径
	 * @return 是否存在
	 */
	public static boolean existByServerFilePath(long deviceId, String serverFilePath)
	{
		String jpql = "select count(_point.id) from Middle.DeviceUploadBreakpoint _point where _point.deviceId=:deviceId and _point.serverFilePath=:serverFilePath";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.addParameter("filepath", serverFilePath)
				.singleResult();
		if(count > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 更新断点信息
	 * @param point 断点信息
	 * @return 是否存在
	 */
	public static boolean updateUploadBreakpoint(DeviceUploadBreakpoint point)
	{
		String jpql = "update Middle.DeviceUploadBreakpoint _point set"
				+ " _point.breakpointOffset=:offset"
				+ ",_point.uploadPercent=:percent"
				+ " where _point.deviceId=:deviceId and _point.directory=:filepath";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("offset", point.getBreakpointOffset())
				.addParameter("percent", point.getUploadPercent())
				.addParameter("deviceId", point.getDeviceId())
				.executeUpdate();
		if(count > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过设备id，来删除所有的断点信息
	 * @param deviceId 设备id
	 * @return 是否删除成功
	 */
	public static boolean removeAllByDeviceId(long deviceId)
	{
		String jpql = "delete from Middle.DeviceUploadBreakpoint _point where _point.deviceId=:deviceId";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.executeUpdate();
		if(count > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过设备id和服务器端文件路径，来获取断点信息
	 * @param deviceId 设备id
	 * @param serverFilePath 服务器端文件路径
	 * @return 断点信息
	 */
	public static List<DeviceUploadBreakpoint> queryAllByServerFilePath(long deviceId,
			String serverFilePath)
	{
		String jpql = "select _point from Middle.DeviceUploadBreakpoint _point where _point.deviceId=:deviceId and _point.serverFilePath=:filepath order by _record.breakpointTime desc";
		List<DeviceUploadBreakpoint> points = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.addParameter("filepath", serverFilePath)
				.list();
		return points;
	}
	
	/**
	 * 获取设备断点信息的数量
	 * @param deviceId 设备id
	 * @return 断点信息数量
	 */
	public static long countAllByDeviceId(long deviceId)
	{
		String jpql = "select count(_record.id) from Middle.DeviceUploadBreakpoint _point where _point.deviceId=:deviceId";
		Long count = getRepository().createJpqlQuery(jpql)
				.addParameter("deviceId", deviceId)
				.singleResult();
		return count;
	}
	
	/**
	 * 获取设备所有的断点信息
	 * @param deviceId 设备id
	 * @return 断点信息
	 */
	public static List<DeviceUploadBreakpoint> queryAllByDeviceId(long deviceId)
	{
		String jpql = "select _record from Middle.DeviceUploadBreakpoint _point where _point.deviceId=:deviceId";
		List<DeviceUploadBreakpoint> records = getRepository().createJpqlQuery(jpql)
				.addParameter("deviceId", deviceId)
				.list();
		return records;
	}
	
	/**
	 * 分页获取设备的断点信息
	 * @param deviceId 设备id
	 * @param pageCount 页数
	 * @param pageSize 页面大小
	 * @return 断点信息
	 */
	public static List<DeviceUploadBreakpoint> queryAllByDeviceIdInPage(long deviceId,
			int pageCount, int pageSize)
	{
		String jpql = "select _record from Middle.DeviceUploadBreakpoint _point where _point.deviceId=:deviceId";
		List<DeviceUploadBreakpoint> records = getRepository().createJpqlQuery(jpql)
				.addParameter("deviceId", deviceId)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		return records;
	}
}
