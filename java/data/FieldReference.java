package data;

import data.closure.GetFieldFunc;
import data.closure.SetFieldFunc;
import jmercury.userInterface;

/**
 * Represents a reference to a field of some record-type value. This class
 * specialises {@code AbstractMercuryReference<>} and implements the remaining
 * abstract methods.
 *
 * @param <D> the record whose field this class refers to.
 *
 * @param <F> the field type this class refers to.
 *
 * @author Pedro Mariano
 */
final public class FieldReference<D, F>
	extends AbstractMercuryReference<F>
{

	/**
	 * The reference to the record value.
	 */
	final public AbstractMercuryReference<D> parent;
	/**
	 * The get function used to get a field of {@code parent}.
	 */
	final private GetFieldFunc<D, F> getFieldFunc;
	/**
	 * The set function used to update the field of {@code parent}. The set
	 * function returns an instance of {@code maybe_error(T)}.
	 */
	final private SetFieldFunc<D, F> setFieldMFunc;

	/**
	 * Construc an instance of the class that refers to a field of a record.
	 *
	 * @param data The data whose field this class refers to.
	 *
	 * @param getFunc The function to get the field of the record denoted by
	 * {@code data}.
	 *
	 * @param setFunc The function to set the field of the record denoted by
	 * {@code data}.
	 */
	public FieldReference (AbstractMercuryReference<D> data, GetFieldFunc<D, F> getFunc, SetFieldFunc<D, F> setFunc)
	{
		this.parent = data;
		this.getFieldFunc = getFunc;
		this.setFieldMFunc = setFunc;
	}

	/**
	 * Updates a field of {@code data} with the given value. The set function
	 * returns an instance of {@code maybe_error(T)}. If constructor
	 * {@code error(string)} is returned, we show the message in the notification
	 * area and disable the ok button.
	 *
	 * @return {@code true} if there was no error.
	 */
	@Override
	final public boolean setValue (F value)
	{
		return this.parent.applySetFieldFunc (this.setFieldMFunc, value);
	}

	@Override
	final public F getValue ()
	{
		return parent.applyGetFieldFunc (this.getFieldFunc);
	}

	/**
	 * Handles setting a field data. Seting the a field of a data may result in a
	 * valid data or an error message. In the case of an error message is
	 * produced it must be shown to the user.
	 *
	 * @param mdata the value to handle.
	 *
	 * @return {@code true} if there was no error.
	 */
	@Override
	public boolean handle_setResult (userInterface.SetResult_1<F> mdata)
	{
		if (mdata instanceof userInterface.SetResult_1.Ok_1) {
			userInterface.SetResult_1.Ok_1<F> newData = (userInterface.SetResult_1.Ok_1<F>) mdata;
			this.setValue (newData.F1);
			return true;
		}
		else if (mdata instanceof userInterface.SetResult_1.Error_1) {
			userInterface.SetResult_1.Error_1<F> error = (userInterface.SetResult_1.Error_1<F>) mdata;
			this.parent.
				handle_setResult (new userInterface.SetResult_1.Error_1<D> (error.F1));
			return false;
		}
		else {
			throw new UnsupportedOperationException ("Unsupported SetResult: " + mdata.
				getClass ().
				getSimpleName ());
		}
	}
}
