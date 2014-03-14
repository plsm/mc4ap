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
	 *
	 */
	public static Key EMPTY = new Key () {
		@Override
		public String toString ()
		{
			return "EMPTY";
		}
	};
	public static Key NOT_USED = new Key () {
		@Override
		public String toString ()
		{
			return "NOT_USED";
		}
	};
			  
	/**
	 * Integer used to generate unique keys for {@code UIPanel} instances.
	 */
	private int KEY_GENERATOR = 0;
	
	
	synchronized public Key nextKey ()
	{
//		String result;
//		result = "key" + (++KEY_GENERATOR);
//		return result;
		return new KeyInstance (++KEY_GENERATOR);
	}
	private class KeyInstance
		implements Key
	{
		final int id;
		final String result;
		KeyInstance (int id)
		{
			this.id = id;
			this.result = "key" + id;
		}
		@Override
		public String toString ()
		{
			return result;
		}
	}
}
