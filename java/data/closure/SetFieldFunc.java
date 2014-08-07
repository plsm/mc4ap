package data.closure;

/**
 * Encapsulates mercury type {@code func(D,F)=setResult(D)}.  This function
 * is used to set a field of some data.  The result may be a valid data, or
 * if the field value is invalid data, a string is returned.
 * 
 * @author pedro
 */
final public class SetFieldFunc<D, F>
{
	/**
	 * The set function.
	 */
	final private Object[] setFunc;
	/**
	 * Construct a set function wrapper.
	 * @param setFunc 
	 */
	public SetFieldFunc (Object[] setFunc)
	{
		this.setFunc = setFunc;
	}
	/**
	 * Apply the set function to the given data and field.
	 * @param data The data to be updated.
	 * @param field The new value of hte field
	 * @return 
	 */
	public jmercury.userInterface.SetResult_1<D> apply (D data, F field)
	{
		jmercury.userInterface.SetResult_1<D> result;
		jmercury.runtime.MethodPtr3 funcMeth = ((jmercury.runtime.MethodPtr3) this.setFunc[1]);
		result = (jmercury.userInterface.SetResult_1<D>) funcMeth.call___0_0 (setFunc, data, field);
		return result;
	}
}
