/**
 * @(#) ConcurrentRealtimeJob.java 2013-7-1
 * All rights reserved by SFC365
 */
package com.apical.dmcloud.commons.infra.sheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public abstract class ConcurrentRealtimeJob<I, O>
{
	/**
	 * 输入参数类型
	 */
	protected Class<I> inputType = null;
	
	/**
	 * 输出参数类型
	 */
	protected Class<O> outputType = null;
	
	/**
	 * 组件坐标
	 */
	private final ComponentCoordinate componetCoordinate;
	
	/**
	 * 任务分支
	 */
	protected List<ConcurrentProcessBranch<I, O>> processes = new ArrayList<ConcurrentProcessBranch<I, O>>();

	public ConcurrentRealtimeJob(ComponentCoordinate componetCoordinate, Class<I> inputType,
			Class<O> outputType)
	{
		this.componetCoordinate = componetCoordinate;
		this.inputType = inputType;
		this.outputType = outputType;
	}
	
	/**
	 * 获取输入数据类型
	 * @return 输入数据类型
	 */
	public Class<I> getInputType()
	{
		return inputType;
	}

	/**
	 * 设置输入数据类型
	 * @param inputType - 输入数据类型
	 */
	public void setInputType(Class<I> inputType)
	{
		this.inputType = inputType;
	}

	/**
	 * 获取输出数据类型
	 * @return 输处数据类型
	 */
	public Class<O> getOutputType()
	{
		return outputType;
	}

	/**
	 * 设置输出数据类型
	 * @param outputType 输出数据类型
	 */
	public void setOutputType(Class<O> outputType)
	{
		this.outputType = outputType;
	}
	
	/**
	 * 返回组件坐标
	 * @return 组件坐标
	 */
	public ComponentCoordinate getComponetCoordinate()
	{
		return componetCoordinate;
	}
	
	protected abstract O collectResults(List<ConcurrentProcessBranch<I, O>> processes);

	public final O startJob(I inputParameter)
	{
		for (final ConcurrentProcessBranch<I, O> process : this.processes)
		{
			// dispatch task at the specified thread pool
			process.execute(inputParameter);
		}

		// ////////////// NOTIFY COMPLETED EVENT /////////////////////////
		return collectResults(processes);
	}
}
