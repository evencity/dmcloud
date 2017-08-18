package com.apical.dmcloud.vehicle.core.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.dayatang.utils.Assert;
import org.openkoala.security.core.domain.SecurityAbstractEntity;

import com.apical.dmcloud.vehicle.core.P2PUUIDIsExistedException;
import com.apical.dmcloud.vehicle.core.constant.P2PUUIDConstant;

/**
 * P2PUUID，监控用
 * 
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_p2puuid")
public class P2PUUID extends SecurityAbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * p2puuid
	 */
	@Column(name = "P2PUUID")
	private String p2puuid;

	/**
	 * 公司id
	 */
	/*@Column(name = "COM_ID")
	private String companyId;*/

	/**
	 * 状态
	 */
	@Column(name = "STATE")
	private Integer state;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/*	*//**
	 * 创建时间
	 *//*
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	*//**
	 * 最后更新时间
	 *//*
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFY_TIME")
	private Date lastModifyTime;*/
	
	/*
	 * 设备
	 *
	@OneToOne(targetEntity=Device.class,fetch = FetchType.LAZY,cascade = CascadeType.REFRESH,mappedBy="p2puuid")
	private Device device;*/
	
	

	public P2PUUID() {
		super();
	}

	public P2PUUID(String p2puuid, String companyId, Integer state,
			String description) {
		super();
		this.p2puuid = p2puuid;
		this.state = state;
		this.description = description;
	}

	public P2PUUID(String p2puuid) {
		checkName(p2puuid);
		if (isExistP2PUUID(p2puuid)) {
			throw new P2PUUIDIsExistedException("P2PUUID of p2puuid " + p2puuid + "is exist");
		}
		this.p2puuid = p2puuid;
	}
	
	

	public P2PUUID(String p2puuid, Integer state) {
		this.p2puuid = p2puuid;
		this.state = state;
	}

	/**
	 * 根据id，来删除信息
	 * @return
	 */
	public static boolean deleteById(Long id)
	{
		String jpql = " delete from P2PUUID _p2puuid where _p2puuid.id = :id";
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
	public static P2PUUID getByP2PUUID(String p2puuid) {
		String jpql = "select _p2puuid from P2PUUID _p2puuid where _p2puuid.p2puuid = :p2puuid";
		P2PUUID p = SecurityAbstractEntity.getRepository()
				.createJpqlQuery(jpql.toString())
				.addParameter("p2puuid", p2puuid).singleResult();
		return p;
	}
	public static P2PUUID getNouseP2PUUID() {
		String jpql = "select _p2puuid from P2PUUID _p2puuid where _p2puuid.state = :state and _p2puuid.p2puuid != '' order by _p2puuid.id";
		P2PUUID p = SecurityAbstractEntity.getRepository()
				.createJpqlQuery(jpql.toString())
				.addParameter("state", P2PUUIDConstant.STATE_NOUSE).singleResult();
		return p;
	}
	/**
	 * 批量获取是否存在
	 * @return
	 */
	public static List<P2PUUID> getNouseP2PUUIDs(Integer limit) {
		if(limit == 0){
			return null;
		}
		String jpql = "select _p2puuid from P2PUUID _p2puuid where _p2puuid.state = :state and _p2puuid.p2puuid != '' order by _p2puuid.id";
		List<P2PUUID> p = SecurityAbstractEntity.getRepository()
				.createJpqlQuery(jpql.toString())
				.addParameter("state", P2PUUIDConstant.STATE_NOUSE)
				.setMaxResults(limit).list();
		return p;
	}
	
	/**
	 * 判断该名称是否已被使用
	 * 
	 * @param name
	 *            名称
	 * @return 是否已被使用
	 */
	public static boolean isExistP2PUUID(String p2puuid) {
		String jpql = "select _p2puuid from P2PUUID _p2puuid where _p2puuid.p2puuid = :p2puuid";
		P2PUUID p = SecurityAbstractEntity.getRepository()
				.createJpqlQuery(jpql.toString())
				.addParameter("p2puuid", p2puuid).singleResult();
		if (p == null)
			return false;
		else
			return true;
	}

	/**
	 * 批量判断该名称是否已被使用
	 * 
	 * @param name
	 *            名称
	 * @return 存在的p2p号
	 */
	public static List<String> isExistP2PUUIDs(Set<String> p2puuids) {
		/*StringBuilder jpql = new StringBuilder("select _p2puuid.p2puuid from P2PUUID _p2puuid ");
		if(p2puuids != null && p2puuids.size() > 0){
			jpql.append("where _p2puuid.p2puuid in('");
			for(String s : p2puuids){
				jpql.append(s+"','");
			}
			jpql.delete(jpql.length()-2, jpql.length()).append(")");
		}
		List<String> p = SecurityAbstractEntity.getRepository()
				.createJpqlQuery(jpql.toString()).list();
		return p;*/
		StringBuilder jpql = new StringBuilder("select _p2puuid.p2puuid from P2PUUID _p2puuid ");
		Map<String,Object> conditions = new HashMap<String,Object>();	
		if(p2puuids != null && p2puuids.size() > 0){
			jpql.append("where _p2puuid.p2puuid in(");
			int i = 0;
			for(String s : p2puuids){
				jpql.append(":p2puuid"+i+",");
				conditions.put("p2puuid"+i, s);
				i ++;
			}
			jpql.delete(jpql.length()-1, jpql.length()).append(")");
		}
		List<String> p = getRepository().createJpqlQuery(jpql.toString()).setParameters(conditions).list();
		return p;
	}
	/**
	 * 使用断言方式检测p2puuid名称是否为空
	 * 
	 * @param name
	 *            p2puuid
	 */
	private void checkName(String name) {
		Assert.notBlank(name, "name cannot be empty.");
	}

	/**
	 * 根据id，来删除p2puuid信息
	 * 
	 * @param p2puuid
	 * @return 删除是否成功 true if successful, or false if an error occurred
	 */
	public static boolean delete(String id) {
		String jpql = " delete from P2PUUID _p2puuid where _p2puuid.id = :id";
		int count = SecurityAbstractEntity.getRepository()
				.createJpqlQuery(jpql.toString())
				.addParameter("id", id).executeUpdate();
		if (count == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 分页获取信息
	 * 
	 * @param name
	 *            名称（不需要此条件时，请设置为null）
	 * @param pageCount
	 *            页数（pageCount >= 1）
	 * @param pageSize
	 *            页面大小（pageSize >= 1）
	 * @return 信息
	 */
	public static List<P2PUUID> findAllInPage(String p2puuid,Set<Integer> states,
			int pageCount, int pageSize) {
		//StringBuilder jpql = new StringBuilder("select _p2puuid from P2PUUID _p2puuid left join fetch _p2puuid.devices where");
		StringBuilder jpql = new StringBuilder("select _p2puuid from P2PUUID _p2puuid  where");
		Map<String, Object> conditions = new HashMap<String, Object>();
		if (!StringUtils.isBlank(p2puuid)) {
			jpql.append(" _p2puuid.p2puuid like :p2puuid and");
			conditions.put("p2puuid","%" +p2puuid + "%");
		}
		
		if (states != null && !states.isEmpty()) {
			jpql.append(" _p2puuid.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ") and");
		}
		jpql.append(" 1=1");
		jpql.append(" order by _p2puuid.id");
		List<P2PUUID> p2puuids = getRepository()
				.createJpqlQuery(jpql.toString()).setParameters(conditions)
				.setMaxResults(pageSize)
				.setFirstResult((pageCount - 1) * pageSize).list();
		
		if (p2puuids != null) {
			return p2puuids;
		} else {
			return null;
		}
	}

	/**
	 * 统计数量
	 * 
	 * @return 总数m
	 */
	public static long countAll(String p2puuid,Set<Integer> states) {
		StringBuilder jpql = new StringBuilder(
				"select count(_p2puuid.id) from P2PUUID _p2puuid where");
		Map<String, Object> conditions = new HashMap<String, Object>();
		if (!StringUtils.isBlank(p2puuid)) {
			jpql.append(" _p2puuid.p2puuid like :p2puuid and");
			conditions.put("p2puuid", "%" +p2puuid + "%");
		}
		jpql.append(" 1=1");
		if (states != null && !states.isEmpty()) {
			jpql.append(" and _p2puuid.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")");
		}
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.setParameters(conditions)
				.singleResult();
		return count;
	}
	/**
	 * 统计数量未被使用的
	 * 
	 * @return 总数m
	 */
	public static long countAllNoUse() {
		StringBuilder jpql = new StringBuilder(
				"select count(_p2puuid.id) from P2PUUID _p2puuid where _p2puuid.state=1 and _p2puuid.p2puuid != ''");
		
		Long count = getRepository().createJpqlQuery(jpql.toString())
				.singleResult();
		return count;
	}


	public static Integer saveBatch(List<P2PUUID> list) {
		int count = 0;
		if(list != null && list.size() > 0){
			StringBuilder sql = new StringBuilder("insert into cl_p2puuid (P2PUUID,STATE,DESCRIPTION) values ");
			Map<String,Object> conditions = new HashMap<String,Object>();	
			int i = 0;
			for(P2PUUID p2puuid : list){
				sql.append("(:P2PUUID").append(i).append(",");
				sql.append(":STATE").append(i).append(",");
				sql.append(":DESCRIPTION").append(i).append("),");
				conditions.put("P2PUUID"+i, p2puuid.getP2puuid());
				conditions.put("STATE"+i, p2puuid.getState() == null ? P2PUUIDConstant.STATE_NOUSE : p2puuid.getState());
				conditions.put("DESCRIPTION"+i, p2puuid.getDescription());
				i ++;
			}
			
			sql.delete(sql.length()-1, sql.length());
			count = getRepository().createSqlQuery(sql.toString()).setParameters(conditions).executeUpdate();
			return count;
		}
		return count;
	}

	/**
	 * 更新状态
	 * @param p2puuids
	 */
	public static Integer updateStateToNouse(String p2puuid) {
		StringBuilder sql = new StringBuilder("update cl_p2puuid set cl_p2puuid.STATE = 1 where cl_p2puuid.p2puuid = :p2puuid");
		
		return getRepository().createSqlQuery(sql.toString()).
				addParameter("p2puuid", p2puuid)
				.executeUpdate();
	}

	/**
	 * 批量更新状态
	 * @param p2puuids
	 */
	public static Integer updateStateBatch(List<P2PUUID> p2puuids) {
		int count = 0;
		if(p2puuids != null && p2puuids.size() > 0){
			StringBuilder sql = new StringBuilder("update cl_p2puuid set cl_p2puuid.STATE = 2 where cl_p2puuid.ID in (");
			Map<String,Object> conditions = new HashMap<String,Object>();	
			int i = 0;
			for(P2PUUID p2puuid : p2puuids){
				sql.append(":ID").append(i).append(",");
				conditions.put("ID"+i, p2puuid.getId());
				i ++;
			}
			sql.delete(sql.length()-1, sql.length()).append(")");
			count = SecurityAbstractEntity.getRepository().createSqlQuery(sql.toString()).setParameters(conditions).executeUpdate();
			return count;
		}
		return count;
	}
	public String getP2puuid() {
		return p2puuid;
	}

	public void setP2puuid(String p2puuid) {
		this.p2puuid = p2puuid;
	}

	/*public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	*/

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String[] businessKeys() {
		return new String[]{"P2PUUID"};
	}

	@Override
	public String toString() {
		return "P2PUUID [p2puuid=" + p2puuid + ", state=" + state + ", description=" + description + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((p2puuid == null) ? 0 : p2puuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		P2PUUID other = (P2PUUID) obj;
		if (p2puuid == null) {
			if (other.p2puuid != null)
				return false;
		} else if (!p2puuid.equals(other.p2puuid))
			return false;
		return true;
	}
}
