/**
 * @(#) ExecutableParameter.java 2013-1-11
 */
package com.apical.dmcloud.commons.infra.threadpool;

/**
 * @author Administrator
 * 
 */
public class ExecutableParameter<I, O>
{
	private final Executable<I, O> executable;
	private final I parameter;
	private final CallbackHandler<O> callbackHandler;
	
	public ExecutableParameter(Executable<I, O> executable,
			I parameter,
			CallbackHandler<O> callbackHandler)
	{
		super();
		this.executable = executable;
		this.parameter = parameter;
		this.callbackHandler = callbackHandler;
	}
	
	public Executable<I, O> getExecutable()
	{
		return executable;
	}
	
	public I getParameter()
	{
		return parameter;
	}
	
	public CallbackHandler<O> getCallbackHandler()
	{
		return callbackHandler;
	}
}
