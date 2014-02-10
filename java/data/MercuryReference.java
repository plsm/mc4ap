package data;

import data.closure.SetFieldFunc;
import data.closure.GetFieldFunc;

/**
 * Represents a reference to a mercury value.  Implementors of this interface can use an instance of the class to share a reference between diferent GUI components.
 * @author pedro
 */
public interface MercuryReference<D>
{
	/**
	 * Set the value of this data reference.
	 * 
	 * @param value The new value of this data reference.
	 * 
	 * @return {@code true} if there was no error.
	 */
	public boolean setValue (D value);
	/**
	 * Get the value of this data reference.
	 * 
	 * @return the value of this data reference.
	 */
	public D getValue ();
	/**
	 * Get the value of some field of this data.
	 *
	 * @param getFunc the get function used to obtain a field value of the data.
	 *
	 * @return the value of a field of this data.
	 */
	<F> F applyGetFieldFunc (GetFieldFunc<D, F> func);
	/**
	 * Set the value of some field of this data.
	 * 
	 * @param setFunc The field set function.
	 * 
	 * @param newFieldValue The new field value.
	 */
	<F> boolean applySetFieldFunc (SetFieldFunc<D, F> func, F newFieldValue);
}
