package data;


/**
 * Holds the current value of the data shown in some component.
 * 
 * <p> This class allows different components to access the most recent
 * value of some data.
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
	@Override
	final public D getValue ()
	{
		return this.value;
	}
	/**
	 * 
	 * @param newValue
	 * @return {@code true} if there was no error.
	 */
	@Override
	final public boolean setValue (D newValue)
	{
		this.value = newValue;
		return true;
	}
}
