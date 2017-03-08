/**
 * @(#) ConcurrentProcessBranch.java 2013-7-1
 * All rights reserved by SFC365
 */
package com.apical.dmcloud.commons.infra.sheduler;

/**
 * @author Administrator
 * 
 */
public abstract class ConcurrentProcessBranch<I, O>
{
	private final String id;
	
	protected final I ownArgument;
	
	private O returns = null;
	
	private Throwable exception = null;
	
	private boolean completed = false;

	/**
	 * @return the completed
	 */
	public boolean isCompleted()
	{
		return completed;
	}

	/**
	 * @param completed
	 *            the completed to set
	 */
	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the exception
	 */
	public Throwable getException()
	{
		return exception;
	}

	/**
	 * @param exception
	 *            the exception to set
	 */
	public void setException(Throwable exception)
	{
		this.exception = exception;
	}

	/**
	 * @return the returns
	 */
	public O getReturns()
	{
		return returns;
	}

	/**
	 * @param returns
	 *            the returns to set
	 */
	public void setReturns(O returns)
	{
		this.returns = returns;
	}

	/**
	 * 根据id和输入参数，创建一个任务处理分支对象
	 * @param id 任务分支id
	 * @param argument 输入参数
	 */
	protected ConcurrentProcessBranch(String id, I argument)
	{
		super();
		this.id = id;
		this.ownArgument = argument;
	}

	public abstract O execute(I input);
}
