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
class FieldDataReference<D, F>
	extends AbstractDataReference<F>
{
	final private MercuryReference<D> parent;
	
	/**
	 * The get function used to get the field of {@code parent}.
	 */
	final private Object[] getFieldFunc;
	/**
	 * The set function used to update the field of {@code parent}. The set function returns an instance of {@code maybe_error(T)}.
	 */
	final private Object[] setFieldMFunc;
		
	FieldDataReference (UIFrame frame, MercuryReference data, Object[] getFunc, Object[] setFunc)
	{
		super (frame);
		this.parent = data;
		this.getFieldFunc = getFunc;
		this.setFieldMFunc = setFunc;
	}

	/**
	 * Updates a field of {@code data} with the given value.  The set function returns an instance of {@code maybe_error(T)}.  If constructor {@code error(string)} is returned, we show the message in the notification area and disable the ok button.
	 *
	 */
	@Override
	public void setValue (F value)
	{
		this.parent.applySetMFunc (this.setFieldMFunc, value);
	}

	@Override
	public F getValue ()
	{
		return (F) parent.applyGetFunc (this.getFieldFunc);
	}
}
