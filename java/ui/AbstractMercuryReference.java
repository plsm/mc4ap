/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import data.closure.SetFieldFunc;
import data.closure.GetFieldFunc;
import jmercury.userInterface;
import jmercury.runtime.MethodPtr2;
import jmercury.runtime.MethodPtr3;

/**
 *
 * @author pedro
 */
abstract class AbstractMercuryReference<D>
	implements MercuryReference<D>
{
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
		MethodPtr2 funcMeth = ((MethodPtr2) getFunc[1]);
		return funcMeth.call___0_0 (getFunc, this.getValue ());
	}

	@Override
	final public void applySetFunc (Object[] setFunc, Object newFieldValue)
	{
		MethodPtr3 funcMeth = ((MethodPtr3) setFunc[1]);
		this.setValue ((D) funcMeth.call___0_0 (setFunc, this.getValue (), newFieldValue));
	}
	/**
	 * Handle a value of type {@code setResult(D)}.  If the value is ok {@code ok(X)} we update the data value this panel references to.  Otherwise, if the value is {@code error(M)} we show the message in the frame notification area.
	 * 
	 * @param mdata
	 * @return True if there was no error.
	 */
	abstract protected boolean handle_setResult (userInterface.SetResult_1<D> mdata);
}
