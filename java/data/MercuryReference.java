package ui;

/**
 * Represents a reference to a mercury value.  Implementors of this interface can use an instance of the class to share a reference between diferent GUI components.
 * @author pedro
 */
public interface MercuryReference<T>
{
	/**
	 * Set the value of this data reference.
	 * 
	 * @param value The new value of this data reference.
	 */
	public void setValue (T value);
	/**
	 * Get the value of this data reference.
	 * 
	 * @return the value of this data reference.
	 */
	public T getValue ();
	/**
	 * Get the value of some field of this data.
	 *
	 * @param getFunc the get function used to obtain a field value of the data.
	 *
	 * @return the value of a field of this data.
	 */
	Object applyGetFunc (Object[] getFunc);
	/**
	 * Set the value of some field of this data.
	 * @param setFunc The field set function.
	 * @param newFieldValue The new field value.
	 */
	void applySetFunc (Object[] setFunc, Object newFieldValue);
	/**
	 * Set the value of some field of this data.  The set function returns an instance {@code maybe_error(T)}.
	 * @param setMFunc The field set function.
	 * @param newFieldValue The new field value.
	 */
	boolean applySetMFunc (Object[] setMFunc, Object newFieldValue);
}
