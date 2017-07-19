package com.apical.dmcloud.vehicle.core.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.dayatang.utils.Assert;
import org.openkoala.security.core.domain.SecurityAbstractEntity;

import com.apical.dmcloud.vehicle.core.CompanyIsExistedException;
import com.apical.dmcloud.vehicle.core.CompanyNotExistException;

/**
 * 公司。其下有员工，司机，车辆等。
 * @author qiuzeng
 *
 */

@Entity
@Table(name = "cl_company")
public class Company extends SecurityAbstractEntity
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/**
	 * 联系人
	 */
	@Column(name = "LINKMAIN")
	private String linkman;
	
	/**
	 * 联系电话
	 */
	@Column(name = "TELEPHONE")
	private String telephone;
	
	/**
	 * 所属行业
	 */
	@Column(name = "INDUSTRY")
	private String industry;
	
	/**
	 * 联系地址
	 */
	@Column(name = "ADDRESS")
	private String address;
	
	/**
	 * 公司简称
	 */
	@Column(name = "ABBREVIATION")
	private String abbreviation;
	
	/**
	 * 公司法人
	 */
	@Column(name = "CORPORATE")
	private String corporate;
	
	/**
	 * 组织结构代码
	 */
	@Column(name = "CODE")
	private String code;
	
	/**
	 * 营业执照号
	 */
	@Column(name = "LICENSE")
	private String license;
	
	/**
	 * 资金
	 */
	@Column(name = "MONEY")
	private Double money;
	
	protected Company()
	{
	}
	
	public Company(String name)
	{
		checkName(name);
		if(isExistCompanyName(name))
		{
			throw new CompanyIsExistedException("Commpany of name " + name + "is exist");
		};
		this.name = name;
	}

	/**
	 * 获取公司名称
	 * @return 公司名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置公司名称
	 * @param name 公司名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取公司备注
	 * @return 公司备注
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置公司备注
	 * @param description 公司备注
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取联系人
	 * @return 联系人
	 */
	public String getLinkman() {
		return linkman;
	}

	/**
	 * 设置联系人
	 * @param linkman 联系人
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	/**
	 * 获取联系电话
	 * @return 联系电话
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * 设置联系电话
	 * @param telephone 联系电话
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 获取所属行业
	 * @return 所属行业
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * 设置所属行业
	 * @param industry 所属行业
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * 获取公司地址
	 * @return 公司地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置公司地址
	 * @param address 公司地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取公司简称
	 * @return 公司简称
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * 设置公司简称
	 * @param abbreviation 公司简称
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	/**
	 * 获取法人
	 * @return 法人
	 */
	public String getCorporate() {
		return corporate;
	}

	/**
	 * 设置法人
	 * @param corporate 法人
	 */
	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	/**
	 * 获取组织结构代码
	 * @return 组织结构代码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置组织结构代码
	 * @param code 组织结构代码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取营业执照号
	 * @return 营业执照号
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * 设置营业执照号
	 * @param license 营业执照号
	 */
	public void setLicense(String license) {
		this.license = license;
	}

	/**
	 * 获取注册资金
	 * @return 注册资金
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * 设置注册资金
	 * @param money 注册资金
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return new String[]{"Company"};
	}

	/**
	 * 使用断言方式检测公司名称是否为空
	 * @param name 公司名称
	 */
	private void checkName(String name) {
		Assert.notBlank(name, "name cannot be empty.");
	}
	
	/**
	 * 根据公司id，来删除公司信息
	 * @param companyId 公司id
	 * @return 删除是否成功 true if successful, or false if an error occurred
	 */
	public static boolean deleteByCompanyId(long companyId)
	{
		String jpql = " delete from Company _company where _company.id = :id";
		int count = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("id", companyId)
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
	 * 判断该公司名称是否已被使用
	 * @param name 公司名称
	 * @return 是否已被使用
	 */
	public static boolean isExistCompanyName(String name)
	{
		String jpql = "select _company from Company _company where _company.name = :name";
		Company company = getRepository().createJpqlQuery(jpql.toString())
				.addParameter("name", name)
				.singleResult();
		if(company == null)
			return false;
		else
			return true;
	}
	
	/**
	 * 通过公司id，来获取公司信息
	 * @param companyId 公司id
	 * @return 公司信息
	 */
	public static Company getByCompanyId(long companyId) throws CompanyNotExistException
	{
		
		String jpql = "select _company from Company _company where _company.id = :companyId";
		Company company =  getRepository().createJpqlQuery(jpql.toString())
				.addParameter("companyId", companyId)
			    .singleResult();
		if(company == null)
		{
			throw new CompanyNotExistException("Company of id " + companyId + "not exist");
		}
		return company;
	}
	
	/**
	 * 分页获取公司信息
	 * @param name 公司名称（不需要此条件时，请设置为null）
	 * @param pageCount 页数（pageCount >= 1）
	 * @param pageSize 页面大小（pageSize >= 1）
	 * @return 公司信息
	 */
	public static List<Company> findAllCompaniesInPage(String name, int pageCount, int pageSize)
	{
		String jpql;
		List<Company> coms;
		if(name != null)
		{
			jpql = "select _company from Company _company where _company.name like :name order by _company.id";
			coms = getRepository().createJpqlQuery(jpql.toString())
					.addParameter("name", "%"+name+"%")
					.setMaxResults(pageSize)
					.setFirstResult((pageCount -1) * pageSize)
					.list();
		}
		else
		{
			jpql = "select _company from Company _company order by _company.id ";
			coms = getRepository().createJpqlQuery(jpql.toString())
					.setMaxResults(pageSize)
					.setFirstResult((pageCount -1) * pageSize)
					.list();
		}
		if(coms != null)
		{
			return coms;
		}
		else
		{
			return null;
		}
	}
	/**
	 * 统计公司数量
	 * @return 公司总数m
	 */
	public static long countAllCompanys(String name)
	{
		Long count = 0l;
		if(name != null){
			String jpql = "select count(_company.id) from Company _company where _company.name like :name";
			count = getRepository().createJpqlQuery(jpql.toString()).addParameter("name", "%"+name+"%")
					.singleResult();
		}else {
			String jpql = "select count(_company.id) from Company _company";
			count = getRepository().createJpqlQuery(jpql.toString())
					.singleResult();
		}
		return count;
	}
	
	/**
	 * 通过车辆id，来查询该车辆装配的设备信息
	 * @param vehicleId 车辆id
	 * @return 设备信息
	 */
	public static List<Company> listAllCompany(Long companyId) {
		StringBuilder jpql = new StringBuilder("select _company from Company _company where");
		Map<String,Object> conditions = new HashMap<String,Object>();	
		if(companyId != null){
			jpql.append(" _company.id = :companyId and");
			conditions.put("companyId", companyId);
		}
		jpql.append(" 1=1");
		jpql.append(" order by _company.id");
		List<Company> companies =getRepository().createJpqlQuery(jpql.toString()).setParameters(conditions).list();
		if(companies != null) {
			return companies;
		}
		else
		{
			return null;
		}
	}
}
