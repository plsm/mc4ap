/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 * A generator of unique keys.
 * 
 * @author pedro
 */
final public class KeyGenerator
{
	
	/**
	 * Integer used to generate unique keys for {@code UIPanel} instances.
	 */
	private int KEY_GENERATOR = 0;
	
	
	synchronized public String nextKey ()
	{
		String result;
		result = "key" + (++KEY_GENERATOR);
		return result;
	}
}
