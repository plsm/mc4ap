/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.swing;

import ui.MercuryReference;

/**
 *
 * @author pedro
 */
abstract class AbstractDataReference<D>
	implements MercuryReference<D>
{
	final private UIFrame frame;
	
	protected AbstractDataReference (UIFrame frame)
	{
		this.frame = frame;
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
	
}
