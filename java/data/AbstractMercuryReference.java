package data;

import data.closure.SetFieldFunc;
import data.closure.GetFieldFunc;
import jmercury.userInterface.SetResult_1;

/**
 * Common behaviour of references to record-type values.  This class
 * provides methods to set and get fields of the record.
 *
 * @author pedro
 */
abstract public class AbstractMercuryReference<D>
	implements MercuryReference<D>
{
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
	 * 
	 * @param <F>
	 * @param setFunc
	 * @param field
	 * @return {@code true} if there was no error.
	 */
	@Override
	final public <F> boolean applySetFieldFunc (SetFieldFunc<D, F> setFunc, F field)
	{
		return handle_setResult (setFunc.apply (this.getValue (), field));
	}
	/**
	 * Handle a value of type {@code setResult(D)}.  If the value is ok
	 * {@code ok(X)} we update the data value this panel references to.
	 * Otherwise, if the value is {@code error(M)} we show the message in
	 * the frame notification area.
	 * 
	 * @param mdata
	 * 
	 * @return True if there was no error.
	 */
	abstract public boolean handle_setResult (SetResult_1<D> mdata);
}
