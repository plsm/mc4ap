/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.closure;

import jmercury.runtime.MethodPtr4;
import jmercury.userInterface.SetResult_1;

/**
 *
 * @author pedro
 */
final public class SetFieldListElementFunc<D, F>
{
	/**
	 * The set function.
	 */
	final private Object[] func;
	public SetFieldListElementFunc (Object[] func)
	{
		this.func = func;
	}
	public SetResult_1<D> apply (D data, F value, int index)
	{
		SetResult_1<D> result;
		MethodPtr4 funcMeth = (MethodPtr4) this.func [1];
		result = (SetResult_1<D>) funcMeth.call___0_0 (this.func, data, value, index);
		return result;
	}
}
