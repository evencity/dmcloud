package com.apical.dmcloud.middle.infra;

import java.io.Serializable;

public abstract interface Entity extends Serializable
{
	/**
	 * 通过id获取实体对象
	 * @return 实体对象
	 */
	public abstract Serializable getId();
	
	/**
	 * 实体是否已经存在于数据库中。
	 * @return 存在与否
	 */
	public abstract boolean existed();
	
	/**
	 * 实体是否不存在于数据库中。
	 * @return 不存在与否
	 */
	public abstract boolean notExisted();
}
