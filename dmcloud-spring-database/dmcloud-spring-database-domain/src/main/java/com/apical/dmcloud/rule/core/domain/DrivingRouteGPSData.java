package com.apical.dmcloud.rule.core.domain;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import com.apical.dmcloud.AbstractIDEntity;

/**
 * 行车路线的经纬度信息
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_driving_route_lnglat_map")
public class DrivingRouteGPSData extends AbstractIDEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 经度
	 */
	@Column(name = "LNG")
	private Double longitude;
	
	/**
	 * 纬度
	 */
	@Column(name = "LAT")
	private Double latitude;
	
	/**
	 * 路线id
	 */
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ROUTE_ID")
	private Long routeId;
	
	/**
	 * 获取经度
	 * @return 经度
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * 设置经度
	 * @param longitude 经度
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 获取纬度
	 * @return 纬度
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * 设置纬度
	 * @param latitude 纬度
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * 获取路线id
	 * @return 路线id
	 */
	public Long getRouteId() {
		return routeId;
	}

	/**
	 * 设置路线id
	 * @param routeId 路线id
	 */
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	
	@Override
	public void save()
	{
		super.save();
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"DrivingRouteGPSData"};
	}
	
	/**
	 * 根据id来删除经纬度信息
	 * @param id 经纬度信息id
	 * @return 是否删除成功
	 */
	public static boolean deleteById(long id)
	{
		String jpql = "delete from DrivingRouteGPSData _data where _data.id=:id";
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
	 * 删除路线的所有经纬度信息
	 * @param routeId 路线id
	 * @return 删除是否成功
	 */
	public static boolean deleteAllByRouteId(long routeId)
	{
		String jpql = "delete from DrivingRouteGPSData _data where _data.routeId=:routeId";
		getRepository().createJpqlQuery(jpql.toString())
				.addParameter("routeId", routeId)
				.executeUpdate();
		return true;
	}

	/**
	 * 查询路线的经纬度信息数量
	 * @param routeId 路线id
	 * @return 信息数量
	 */
	public static long countAllByRouteId(long routeId)
	{
		String jpql = "select count(_data.id) from DrivingRouteGPSData _data where _data.routeId=:routeId";
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("routeId", routeId)
				.singleResult();
		return count;
	}
	
	/**
	 * 查询路线的经纬度信息
	 * @param routeId 路线id
	 * @return 经纬度信息
	 */
	public static List<DrivingRouteGPSData> queryAllByRouteId(long routeId)
	{
		String jpql = "select _data from DrivingRouteGPSData _data where _data.routeId=:routeId";
		List<DrivingRouteGPSData> datas = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("routeId", routeId)
				.list();
		
		return datas;
	}
	
	/**
	 * 查询路线的经纬度信息
	 * @param routeId 路线id
	 * @param pageCount 页数（pageCount >= 1）
     * @param pageSize 页面大小（pageSize >= 1）
	 * @return 经纬度信息
	 */
	public static List<DrivingRouteGPSData> queryAllByRouteIdInPage(long routeId, int pageCount, int pageSize)
	{
		String jpql = "select _data from DrivingRouteGPSData _data where _data.routeId=:routeId";
		List<DrivingRouteGPSData> datas = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("routeId", routeId)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize)
				.list();
		
		return datas;
	}
}
