/**
 * @(#) StateListener.java 2013-1-11
 */
package com.apical.dmcloud.commons.infra.threadpool;

/**
 * @author Administrator
 * 
 */
public interface StateListener
{
	/**
	 * on busy
	 * 
	 * @param evt
	 */
	public void busyTimes(StateEvent evt);

	/**
	 * on free
	 * 
	 * @param evt
	 */
	public void freeTimes(StateEvent evt);
}
