package data.closure;

import jmercury.maybe.Maybe_1;
import jmercury.userInterface.CurrentChoice_1;
import jmercury.runtime.MethodPtr2;

/**
 * Encapsultes the {@code func(D)=maybe(currentChoice(int,F))} closure which
 * returns the current selected choice. This function is used in mercury
 * constructor {@code selectOneOf}. If is a function that given a data returns
 * the current selected choice index and the field value that should be
 * presented to the user.
 *
 * @param <D> The mercury type of the data.
 * @param <F> The mercury type of the field data.
 *
 * @author pedro
 */
public class SelectedFieldChoiceFunc<D, F>
{

	/**
	 * The function to obtain the current selected choice.
	 */
	final private Object[] selectedChoiceFunc;

	/**
	 * Construct a set function wrapper.
	 *
	 * @param selectedChoiceFunc
	 */
	public SelectedFieldChoiceFunc (Object[] selectedChoiceFunc)
	{
		this.selectedChoiceFunc = selectedChoiceFunc;
	}

	/**
	 * Apply the function to the given data.
	 *
	 * @param data The data to get the field.
	 * @return The field value.
	 */
	public Maybe_1<CurrentChoice_1<F>> apply (D data)
	{
		Maybe_1<CurrentChoice_1<F>> result;
		MethodPtr2 funcMeth = ((MethodPtr2) this.selectedChoiceFunc [1]);
		result = (Maybe_1<CurrentChoice_1<F>>) funcMeth.
			call___0_0 (selectedChoiceFunc, data);
		return result;
	}
}
