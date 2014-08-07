package data;

import data.closure.GetFieldFunc;
import data.closure.SetFieldFunc;
import jmercury.userInterface;

/**
 * Represents a reference to a field of some record-type value.
 *
 * @author pedro
 */
final public class FieldReference<D, F>
	extends AbstractMercuryReference<F>
{
	/**
	 * The record-type value.
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
		
	public FieldReference (AbstractMercuryReference<D> data, GetFieldFunc<D, F> getFunc, SetFieldFunc<D, F> setFunc)
	{
		this.parent = data;
		this.getFieldFunc = getFunc;
		this.setFieldMFunc = setFunc;
	}

	/**
	 * Updates a field of {@code data} with the given value.  The set
	 * function returns an instance of {@code maybe_error(T)}.  If
	 * constructor {@code error(string)} is returned, we show the message in
	 * the notification area and disable the ok button.
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
	 * 
	 * @param mdata
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
			this.parent.handle_setResult (new userInterface.SetResult_1.Error_1<D> (error.F1));
			return false;
		}
		else {
			throw new UnsupportedOperationException ("Unsupported SetResult");
		}
	}
}
