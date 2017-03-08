package com.apical.dmcloud.vehicle.middle.core.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.apical.dmcloud.middle.infra.AbstractIDEntity;

/**
 * 违规信息表
 * @author qiuzeng
 *
 */

@Entity(name = "Middle.Violation")
@Table(name = "cl_violation")
public class Violation extends AbstractIDEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 举报用户ID
	 */
	@Column(name = "USERID")
	private Long userId = null;
	
	/**
	 * 上报时间
	 */
	@Column(name = "TIME")
	private Date time = null;
	
	/**
	 * 违规时间
	 */
	@Column(name = "VIO_TIME")
	private Date violationTime = null;
	
	/**
	 * 违规地点
	 */
	@Column(name = "LOCATION")
	private String place = null;
	
	/**
	 * 违规情况
	 */
	@Column(name = "DETAILS")
	private String details = null;
	
	/**
	 * 违规车辆车牌号
	 */
	@Column(name = "VIHECLE")
	private String vehicleNumber = null;
	
	/**
	 * 举报人
	 */
	@Column(name = "PERSON")
	private String person = null;
	
	/**
	 * 举报人联系电话
	 */
	@Column(name = "PHONE")
	private String phone = null;

	public Violation()
	{
	}
	
	/**
	 * 获取举报用户ID
	 * @return 举报用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置举报用户ID
	 * @param userId 举报用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取上报时间
	 * @return 上报时间
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * 设置上报时间
	 * @param time 上报时间
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * 获取违规时间
	 * @return 违规时间
	 */
	public Date getViolationTime() {
		return violationTime;
	}

	/**
	 * 设置违规时间
	 * @param violationTime 违规时间
	 */
	public void setViolationTime(Date violationTime) {
		this.violationTime = violationTime;
	}

	/**
	 * 获取违规地点
	 * @return 违规地点
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * 设置违规地点
	 * @param place 违规地点
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * 获取违规情况
	 * @return 违规情况
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * 设置违规情况
	 * @param details 违规情况
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * 获取车牌号
	 * @return 车牌号
	 */
	public String getVehicleNumber() {
		return vehicleNumber;
	}

	/**
	 * 设置车牌号
	 * @param vehicleNumber 车牌号
	 */
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	/**
	 * 获取举报人
	 * @return 举报人
	 */
	public String getPerson() {
		return person;
	}

	/**
	 * 设置举报人
	 * @param person 举报人
	 */
	public void setPerson(String person) {
		this.person = person;
	}

	/**
	 * 获取联系电话
	 * @return 联系电话
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置联系电话
	 * @param phone 联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"Violation"};
	}
	
	/**
	 * 分页查询违规信息
	 * @param pageCount 页数（pageCount >= 1）
	 * @param pageSize 页面大小（pageSize >= 1）
	 * @return 违规信息
	 */
	public static List<Violation> queryAllInPage(int pageCount, int pageSize)
	{
		String jpql = "select _violation from Middle.Violation _violation";
		List<Violation> vios = getRepository().createJpqlQuery(jpql.toString())
				.setMaxResults(pageSize)
				.setFirstResult((pageCount -1)* pageSize)
				.list();
		return vios;
	}
}
