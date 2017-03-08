package com.apical.dmcloud.alarm.core.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.apical.dmcloud.AbstractIDEntity;

/**
 * 用户文件重传表
 * @author qiuzeng
 *
 */
@Entity
@Table(name = "cl_retransmission_device")
public class DeviceRetransmission extends AbstractIDEntity
{
	/**
	 * serialVersionUID = -6130726841568650710L;
	 */
	private static final long serialVersionUID = -6130726841568650710L;

	/**
	 * 设备ID
	 */
	@Column(name = "DEVICE_ID")
	private Long deviceId = null;
	
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
	 * 文件大小
	 */
	@Column(name = "FILETYPE")
	private Integer fileType = 0;
	
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
	
	public DeviceRetransmission()
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
		return new String[]{"DeviceRetransmission"};
	}
	
	/**
	 * 根据id来获取重传信息
	 * @param id 重传信息id
	 * @return 重传信息
	 */
	public static DeviceRetransmission getById(long id)
	{
		return get(DeviceRetransmission.class, id);
	}
	
	/**
	 * 根据id来删除重传信息
	 * @param id 重传信息id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from DeviceRetransmission _record where _record.id=:id";
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
	 * 通过设备id，来删除所有的重传信息
	 * @param deviceId 设备id
	 * @return 是否删除成功
	 */
	public static boolean removeAllByDeviceId(long deviceId)
	{
		String jpql = "delete from DeviceRetransmission _point where _point.deviceId=:deviceId";
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
	 * 获取设备重传信息的数量
	 * @param deviceId 设备id
	 * @return 重传信息数量
	 */
	public static long countAllByDeviceId(long deviceId)
	{
		String jpql = "select count(_record.id) from DeviceRetransmission _point where _point.deviceId=:deviceId";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("deviceId", deviceId)
				.singleResult();
		return count;
	}
}
