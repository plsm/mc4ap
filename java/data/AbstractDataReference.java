package data;


/**
 * Holds the current value of the data shown in some component.
 * 
 * <p> This class allows different components to access the most recent
 * value of some data.
 * 
 * @param <D> the type of the mercury value this interface refers to.
 *
 * @author Pedro Mariano
 */
abstract public class AbstractDataReference<D>
	extends AbstractMercuryReference<D>
{
	/**
	 * The current value of the data.
	 */
	D value;
	/**
	 * Return the current value of the data.
	 *
	 * @return the current value of the data
	 */
	@Override
	final public D getValue ()
	{
		return this.value;
	}
	/**
	 * Set the value of the data.
	 * @param newValue the new value of the date.
	 * @return {@code true} if there was no error.
	 */
	@Override
	final public boolean setValue (D newValue)
	{
		this.value = newValue;
		return true;
	}
}
