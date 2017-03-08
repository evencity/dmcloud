package com.apical.dmcloud.rule.middle.core.domain;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import com.apical.dmcloud.commons.infra.memcached.MiddleEntryCacheService;
import com.apical.dmcloud.middle.infra.AbstractIDEntity;

/**
 * 区域或路线的经纬度信息
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.DrivingAreaGPSData")
@Table(name = "cl_driving_area_lnglat_map")
public class DrivingAreaGPSData extends AbstractIDEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 经度
	 */
	@Column(name = "LNG")
	private Float longitude;
	
	/**
	 * 纬度
	 */
	@Column(name = "LAT")
	private Float latitude;
	
	/**
	 * 区域id
	 */
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "AREA_ID")
	private Long areaId;
	
	/**
	 * 获取经度
	 * @return 经度
	 */
	public Float getLongitude() {
		return longitude;
	}

	/**
	 * 设置经度
	 * @param longitude 经度
	 */
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	/**
	 * 获取纬度
	 * @return 纬度
	 */
	public Float getLatitude() {
		return latitude;
	}

	/**
	 * 设置纬度
	 * @param latitude 纬度
	 */
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	/**
	 * 获取区域id
	 * @return 区域id
	 */
	public Long getAreaId() {
		return areaId;
	}

	/**
	 * 设置区域id
	 * @param areaId 区域id
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"DrivingAreaGPSData"};
	}
	
	/**
	 * 根据id来删除经纬度信息
	 * @param id 经纬度信息id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from Middle.DrivingAreaGPSData _data"
				+ " where _data.id=:id";
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
	 * 删除区域或路线的所有经纬度信息
	 * @param areaId 路线或区域id
	 * @return 删除是否成功
	 */
	public static boolean deleteAllByAreaId(long areaId)
	{
		String jpql = "delete from Middle.DrivingAreaGPSData _data"
				+ " where _data.areaId=:areaId";
		getRepository().createJpqlQuery(jpql.toString())
				.addParameter("areaId", areaId)
				.executeUpdate();
		return true;
	}

	/**
	 * 查询区域或者路线的经纬度信息数量
	 * @param areaId 区域路线id
	 * @return 信息数量
	 */
	public static long countAllByAreaId(long areaId)
	{
		String jpql = "select count(_data.id) from Middle.DrivingAreaGPSData _data"
				+ " where _data.areaId=:areaId";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("areaId", areaId)
				.singleResult();
		return count;
	}
	
	/**
	 * 查询区域或者路线的经纬度信息
	 * @param areaId 区域路线id
	 * @return 经纬度信息
	 */
	public static List<DrivingAreaGPSData> queryAllByAreaId(long areaId)
	{
		List<DrivingAreaGPSData> datas =  MiddleEntryCacheService.getGPSData_BelongRule(DrivingAreaGPSData.class, areaId);
		if(datas == null){
			String jpql = "select _data from Middle.DrivingAreaGPSData _data"
					+ " where _data.areaId=:areaId";
				datas = getRepository().createJpqlQuery(jpql.toString())
					.addParameter("areaId", areaId)
					.list();
			if(datas != null){
				MiddleEntryCacheService.saveGPSData_BelongRule(DrivingAreaGPSData.class, areaId, datas);
			}
			
		}
		return datas;
	}
	
	/**
	 * 查询区域或者路线的经纬度信息
	 * @param areaId 区域路线id
	 * @param pageCount 页数（pageCount >= 1）
     * @param pageSize 页面大小（pageSize >= 1）
	 * @return 经纬度信息
	 */
	public static List<DrivingAreaGPSData> queryAllByAreaIdInPage(long areaId, int pageCount, int pageSize)
	{
		String jpql = "select _data from Middle.DrivingAreaGPSData _data"
				+ " where _data.areaId=:areaId";
		List<DrivingAreaGPSData> datas = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("areaId", areaId)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return datas;
	}
}
