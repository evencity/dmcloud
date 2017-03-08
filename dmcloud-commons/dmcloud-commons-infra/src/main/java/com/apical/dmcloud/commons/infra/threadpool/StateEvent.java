/**
 * @(#) StateEvent.java 2013-1-11
 */
package com.apical.dmcloud.commons.infra.threadpool;

import java.util.EventObject;

/**
 * @author Administrator
 * 
 */
public class StateEvent extends EventObject
{
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param source
	 */
	public StateEvent(Object source)
	{
		super(source);
	}
}
