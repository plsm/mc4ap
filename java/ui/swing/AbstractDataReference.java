/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.swing;

import jmercury.userInterface;
import data.closure.GetFieldFunc;
import ui.MercuryReference;
import data.closure.SetFieldFunc;

/**
 *
 * @author pedro
 */
abstract class AbstractDataReference__<D>
	implements MercuryReference<D>
{
	final private UIFrame frame;
	
	protected AbstractDataReference__ (UIFrame frame)
	{
		this.frame = frame;
	}
	
	@Override
	final public <F> F applyGetFieldFunc (GetFieldFunc<D, F> func)
	{
		return func.apply (this.getValue ());
	}
	
	@Override
	final public <F> boolean applySetFieldFunc (SetFieldFunc<D, F> setFunc, F field)
	{
		return handle_setResult (setFunc.apply (this.getValue (), field));
	}
	
	@Override
	final public Object applyGetFunc (Object[] getFunc)
	{
		jmercury.runtime.MethodPtr2 funcMeth = ((jmercury.runtime.MethodPtr2) getFunc[1]);
		return funcMeth.call___0_0 (getFunc, this.getValue ());
	}

	@Override
	final public void applySetFunc (Object[] setFunc, Object newFieldValue)
	{
		jmercury.runtime.MethodPtr3 funcMeth = ((jmercury.runtime.MethodPtr3) setFunc[1]);
		this.setValue ((D) funcMeth.call___0_0 (setFunc, this.getValue (), newFieldValue));
	}

	@Override
	final public boolean applySetMFunc (Object[] setMFunc, Object newFieldValue)
	{
		D value = this.getValue ();
//		if (value != null) {
			jmercury.runtime.MethodPtr3 funcMeth = ((jmercury.runtime.MethodPtr3) setMFunc[1]);
			Object mdata = (jmercury.maybe.Maybe_error_2) funcMeth.call___0_0 (setMFunc, value, newFieldValue);

			boolean error = true;
			if (mdata instanceof jmercury.maybe.Maybe_error_2.Ok_1) {
				this.setValue ((D) ((jmercury.maybe.Maybe_error_2.Ok_1) mdata).F1);
				this.frame.messagesLabel.setText (" ");
				error = false;
			}
			else {
				this.frame.messagesLabel.setText ((String) ((jmercury.maybe.Maybe_error_2.Error_1) mdata).F1);
			}
			this.frame.okButton.setEnabled (!error);
			return !error;
//		}
//		else {
//			this.frame.messagesLabel.setText ("Not initialised");
//			return false;
//		}
	}
	
	/**
	 * Handle a value of type {@code setResult(D)}.  If the value is ok {@code ok(X)} we update the data value this panel references to.  Otherwise, if the value is {@code error(M)} we show the message in the frame notification area.
	 * 
	 * @param mdata
	 * @return True if there was no error.
	 */
	private boolean handle_setResult (userInterface.SetResult_1<D> mdata)
	{
		boolean result;
		if (mdata instanceof userInterface.SetResult_1.Ok_1) {
			userInterface.SetResult_1.Ok_1<D> newData = (userInterface.SetResult_1.Ok_1<D>) mdata;
			this.setValue (newData.F1);
			this.frame.messagesLabel.setText (" ");
			result = false;
		}
		else if (mdata instanceof userInterface.SetResult_1.Error_1) {
			userInterface.SetResult_1.Error_1 error = (userInterface.SetResult_1.Error_1) mdata;
			this.frame.messagesLabel.setText (error.F1);
			result = true;
		}
		else {
			throw new UnsupportedOperationException ("Unsupported SetResult");
		}
		this.frame.okButton.setEnabled (!result);
		return !result;
	}
}
