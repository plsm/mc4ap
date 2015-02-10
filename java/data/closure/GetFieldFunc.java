package data.closure;

import jmercury.runtime.MethodPtr2;

/**
 * Represents a high order function to obtain a field of a record-type value.
 *
 * @param <D> the record whose field is to be obtained.
 *
 * @param <F> the field type that is to be otained.
 *
 * @author Pedro Mariano
 */
final public class GetFieldFunc<D, F>
{
	/**
	 * The get function.
	 */
	final private Object[] getFunc;
	/**
	 * Construct a set function wrapper.
	 *
	 * @param getFunc The function to get the field of a record-type value.
	 */
	public GetFieldFunc (Object[] getFunc)
	{
		this.getFunc = getFunc;
	}
	/**
	 * Apply the get function to the given.
	 *
	 * @param data The data to get the field.
	 *
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
