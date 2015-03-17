package data.closure;

import jmercury.runtime.MethodPtr4;
import jmercury.userInterface.SetResult_1;

/**
 * Represents a higher order term built using mercury function
 * {@code setFieldListElement/5}. The last three parameters are a data, a field
 * and an index. The field type is a list. This function sets the {@code n}th element of this list.
 *
 * @param <D> the mercury type of the data
 * @param <F> the mercury type of the elements in the list field data.
 *
 * @author pedro
 */
final public class SetFieldListElementFunc<D, F>
{
	/**
	 * The set function.
	 */
	final private Object[] func;
	/**
	 * Construct a higher order term that sets an element of a list.
	 *
	 * @param func The function to set the {@code n}th element.
	 */
	public SetFieldListElementFunc (Object[] func)
	{
		this.func = func;
	}
	/**
	 * Calls the function to set the {@code index} element of a field list in
	 * {@code data}.
	 *
	 * @param data The current data value.
	 * @param value The field value to set.
	 * @param index The list index to set.
	 *
	 * @return The new value of the data or an error message in case of a
	 * problem.
	 */
	public SetResult_1<D> apply (D data, F value, int index)
	{
		SetResult_1<D> result;
		MethodPtr4 funcMeth = (MethodPtr4) this.func [1];
		result = (SetResult_1<D>) funcMeth.call___0_0 (this.func, data, value, index);
		return result;
	}
}
