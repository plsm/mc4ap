package data.closure;

import jmercury.runtime.MethodPtr2;

/**
 * Encapsulates mercury type {@code func(D)=D}.  This function is used to
 * set a field of some data.  The result may be a valid data, or if the
 * field value is invalid data, a string is returned.
 * 
 * @author pedro
 */
final public class UpdateDataFunc<D>
{
	/**
	 * The set function.
	 */
	final private Object[] updateDataFunc;
	/**
	 * Construct a set function wrapper.
	 * @param setFunc 
	 */
	public UpdateDataFunc (Object[] updateDataFunc)
	{
		this.updateDataFunc = updateDataFunc;
	}
	/**
	 * Apply the set function to the given data and field.
	 * @param data The data to be updated.
	 * @param field The new value of hte field
	 * @return 
	 */
	public D apply (D data)
	{
		D result;
		MethodPtr2 funcMeth = ((MethodPtr2) this.updateDataFunc [1]);
		result = (D) funcMeth.call___0_0 (updateDataFunc, data);
		return result;
	}
}
