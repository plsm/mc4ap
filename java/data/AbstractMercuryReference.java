package data;

import data.closure.SetFieldFunc;
import data.closure.GetFieldFunc;
import jmercury.userInterface.SetResult_1;

/**
 * Common behaviour of references to record-type values.  This class
 * provides methods to set and get fields of the record.
 *
 * @param <D> the type of the mercury value this class refers to.
 *
 * @author Pedro Mariano
 */
abstract public class AbstractMercuryReference<D>
	implements MercuryReference<D>
{
	/**
	 * Get a field of this record-type value.
	 *
	 * @param <F> The type of the field.
	 *
	 * @param func The function to get the field of the record-type reference by
	 * this class.
	 *
	 * @return The value of the field.
	 */
	@Override
	final public <F> F applyGetFieldFunc (GetFieldFunc<D, F> func)
	{
		D object = this.getValue ();
		if (object == null) {
			return null;
		}
		else {
			return func.apply (object);
		}
	}

	/**
	 * Set a field of this record-type value.
	 *
	 * @param <F> The type of the field.
	 *
	 * @param setFunc The function to set the field of the record-type reference
	 * by this class.
	 *
	 * @param field The new value of the field.
	 *
	 * @return {@code true} if there was no error.
	 */
	@Override
	final public <F> boolean applySetFieldFunc (SetFieldFunc<D, F> setFunc, F field)
	{
		return handle_setResult (setFunc.apply (this.getValue (), field));
	}
	/**
	 * Handle a value of type {@code setResult(D)}.  If the value is
	 * {@code ok(X)} we update the panel that uses this record-type reference.
	 * Otherwise, if the value is {@code error(M)} we show the message in
	 * the frame notification area.
	 * 
	 * @param mdata
	 * 
	 * @return True if there was no error.
	 */
	abstract public boolean handle_setResult (SetResult_1<D> mdata);
}
