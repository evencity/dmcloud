/**
 * @(#) CallbackHandler.java 2013-1-11
 */
package com.apical.dmcloud.commons.infra.threadpool;

/**
 * @author Administrator
 * 
 */
public interface CallbackHandler<T>
{
	public void callback(T returns);

	public void exceptionOccurred(Throwable e);
}
