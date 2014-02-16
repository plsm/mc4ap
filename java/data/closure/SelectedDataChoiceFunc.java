/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.closure;

import jmercury.maybe.Maybe_1;
import jmercury.runtime.MethodPtr2;

/**
 * Encapsultes the {@code func(D)=maybe(int)} closure which returns the current
 * selected data choice. This function is used in constructor
 * {@code selectOneOf/3}. If is a function that given a data returns the current
 * selected data choice index.
 *
 * @author Pedro
 */
public class SelectedDataChoiceFunc<D>
{

	/**
	 * The function to obtain the current selected choice.
	 */
	final private Object[] selectedChoiceFunc;

	/**
	 * Construct a set function wrapper.
	 *
	 * @param setFunc
	 */
	public SelectedDataChoiceFunc (Object[] selectedChoiceFunc)
	{
		this.selectedChoiceFunc = selectedChoiceFunc;
	}

	/**
	 * Apply the function to the given data.
	 *
	 * @param data The data to get the field.
	 * @return The field value.
	 */
	public Maybe_1<Integer> apply (D data)
	{
		Maybe_1<Integer> result;
		MethodPtr2 funcMeth = ((MethodPtr2) this.selectedChoiceFunc [1]);
		result = (Maybe_1<Integer>) funcMeth.
		        call___0_0 (selectedChoiceFunc, data);
		return result;
	}
}
