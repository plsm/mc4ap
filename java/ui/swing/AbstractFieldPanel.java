package ui.swing;

import data.AbstractMercuryReference;
import data.FieldReference;
import jmercury.userInterface;

/**
 * An abstract component to display and edit a field of a Mercury data.
 * This class provides common functionality for components that have to
 * display and edit a field of any type be it primitive or user
 * defined.
 *
 * @author  Pedro Mariano
 */
abstract public class AbstractFieldPanel<D, F>
	extends AbstractDataPanel<F, FieldReference<D, F> >
{
//	/**
//	 * The get function used to get a field of {@code data}.
//	 */
//	final private Object[] getFieldFunc;
//	/**
//	 * The set function used to update a field of {@code data}.
//	 */
//	final private Object[] setFieldFunc;
		
	/**
	 * Creates new form FieldListEditorPanel
	 */
	protected AbstractFieldPanel (AbstractMercuryReference<D> data, UIFrame frame, Object[] getFunc, Object[] setFunc)
	{
		super (new data.FieldReference<D, F> (data, new data.closure.GetFieldFunc<D, F> (getFunc), new data.closure.SetFieldFunc<D, F> (setFunc)), frame);
//		this.getFieldFunc = getFunc;
//		this.setFieldFunc = setFunc;
	}
//	/**
//	 * Get the field value represented by this {@code
//	 * AbstractFieldPanel}.
//	 * 
//	 * @return the field represented by this {@code AbstractFieldPanel}.
//	 */
//	final protected F applyGetFunc ()
//	{
//		return (F) super.applyGetFunc (this.getFieldFunc);
//	}
//	/**
//	 * Set the field to the given value.
//	 * 
//	 * @param value The new value of the field represented by this
//	 * {@code AbstractFieldPanel}.
//	 * 
//	 * @return Whether the new value is valid or not.
//	 */
//	final protected boolean applySetFunc (F value)
//	{
//		return super.applySetFunc (this.setFieldFunc, value);
//	}
}
