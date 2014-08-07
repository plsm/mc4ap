package data.closure;

import jmercury.runtime.MethodPtr2;

/**
 *
 * @author pedro
 */
final public class GetFieldFunc<D, F>
{
	/**
	 * The get function.
	 */
	final private Object[] getFunc;
	/**
	 * Construct a set function wrapper.
	 * @param setFunc 
	 */
	public GetFieldFunc (Object[] getFunc)
	{
		this.getFunc = getFunc;
	}
	/**
	 * Apply the get function to the given.
	 * @param data The data to get the field.
	 * @return The field value.
	 */
	public F apply (D data)
	{
		F result;
		MethodPtr2 funcMeth = ((MethodPtr2) this.getFunc [1]);
		result = (F) funcMeth.call___0_0 (getFunc, data);
		return result;
	}
}
