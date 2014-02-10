package ui.swing;

import ui.MercuryReference;

/**
 * Holds the current value of the data shown in some component.
 * 
 * <p> This class allows different components to access the most recent value of some data.
 * 
 * @author Pedro Mariano
 */
final class DataReference<T>
	extends AbstractDataReference<T>
{
	/**
	 * The current value of the data.
	 */
	T value;
	/**
	 * Construct a data with no value.
	 */
	DataReference (UIFrame frame)
	{
		this (frame, null);
	}
	/**
	 * Construct a data with the given value.
	 * 
	 * @param value 
	 */
	DataReference (UIFrame frame, T value)
	{
		super (frame);
		this.value = value;
	}
	@Override
	public T getValue ()
	{
		return this.value;
	}
	@Override
	public void setValue (T newValue)
	{
		this.value = newValue;
	}
}
