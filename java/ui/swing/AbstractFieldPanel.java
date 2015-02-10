package ui.swing;

import data.AbstractMercuryReference;
import data.FieldReference;

/**
 * An abstract component to display and edit a field of a Mercury data.
 * This class provides common functionality for components that have to
 * display and edit a field of any type be it primitive or user
 * defined.
 *
 * @author  Pedro Mariano
 * @param <D>
 * @param <F>
 */
abstract public class AbstractFieldPanel<D, F>
	extends AbstractDataPanel<F, FieldReference<D, F> >
{
	/**
	 * Creates new form FieldListEditorPanel
	 * @param data
	 * @param frame
	 * @param getFunc
	 * @param setFunc
	 */
	protected AbstractFieldPanel (AbstractMercuryReference<D> data, UIFrame frame, Object[] getFunc, Object[] setFunc)
	{
		super (new data.FieldReference<D, F> (data, new data.closure.GetFieldFunc<D, F> (getFunc), new data.closure.SetFieldFunc<D, F> (setFunc)), frame);
	}
}
