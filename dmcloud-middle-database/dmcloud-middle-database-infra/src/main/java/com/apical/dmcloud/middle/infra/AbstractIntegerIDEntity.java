package com.apical.dmcloud.middle.infra;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass //用在父类上面。当这个类肯定是父类时，加此标注。如果改成@Entity，则继承后，多个类继承，只会生成一个表，而不是多个继承，生成多个表
public abstract class AbstractIntegerIDEntity extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	/**
	 * 获取资源的行健值
	 * @return Integer 资源行健值
	 */
	@Override
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	@Transient
	public boolean isNew()
	{
		return id == null || id.intValue() == 0;
	}
}
