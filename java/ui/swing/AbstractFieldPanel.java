package ui.swing;

import data.AbstractMercuryReference;
import data.FieldReference;

/**
 * An abstract component to display and edit a field of a Mercury data.
 * This class provides common functionality for components that have to
 * display and edit a field of any type be it primitive or user
 * defined.
 *
 * @param <D> The type of the data.
 *
 * @param <F> The type of the field data which is shown by this panel.
 *
 * @author  Pedro Mariano
 */
abstract public class AbstractFieldPanel<D, F>
	extends AbstractDataPanel<F, FieldReference<D, F> >
{
	/**
	 * Creates new form FieldListEditorPanel
	 * @param data The data whose field is going to be shown by the panel.
	 * @param frame The frame where this panel is located.
	 * @param getFunc The function to get the field data.
	 * @param setFunc The function to set the field data.
	 */
	protected AbstractFieldPanel (AbstractMercuryReference<D> data, UIFrame frame, Object[] getFunc, Object[] setFunc)
	{
		super (new data.FieldReference<D, F> (data, new data.closure.GetFieldFunc<D, F> (getFunc), new data.closure.SetFieldFunc<D, F> (setFunc)), frame);
	}
}
