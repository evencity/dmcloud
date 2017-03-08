/**
 * @(#) Executable.java 2013-1-11
 */
package com.apical.dmcloud.commons.infra.threadpool;

/**
 * @author Administrator
 * 
 */
public interface Executable<I, O>
{
	public O execute(I input);
}
