/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.closure;

import jmercury.maybe.Maybe_1;
import jmercury.userInterface.CurrentChoice_1;
import jmercury.runtime.MethodPtr2;

/**
 *
 * @author pedro
 */
public class SelectedChoiceFunc<D, F>
{
	/**
	 * The function to obtain the selected choice.
	 */
	final private Object[] selectedChoiceFunc;
	/**
	 * Construct a set function wrapper.
	 * @param setFunc 
	 */
	public SelectedChoiceFunc (Object[] selectedChoiceFunc)
	{
		this.selectedChoiceFunc = selectedChoiceFunc;
	}
	/**
	 * Apply the function to the given data.
	 * @param data The data to get the field.
	 * @return The field value.
	 */
	public Maybe_1<CurrentChoice_1<F> > apply (D data)
	{
		Maybe_1<CurrentChoice_1<F> > result;
		MethodPtr2 funcMeth = ((MethodPtr2) this.selectedChoiceFunc [1]);
		result = (Maybe_1<CurrentChoice_1<F> >) funcMeth.call___0_0 (selectedChoiceFunc, data);
		return result;
	}
}
