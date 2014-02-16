/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.closure;

import jmercury.runtime.MethodPtr3;
import jmercury.userInterface.SetResult_1;
import jmercury.userInterface.SelectChoice_2;

/**
 * Encapsultes the {@code func(D,int)=setResult(selectChoice(D,F))} closure
 * which sets a choice. This function is used in constructor
 * {@code selectOneOf}. If is a function that given a data and a choice index
 * returns the next data and field value that should be presented to the user.
 *
 * @author pedro
 */
public class SelectFieldChoiceFunc<D, F>
{

	/**
	 * The function to obtain the selected choice.
	 */
	final private Object[] selectChoiceFunc;

	/**
	 * Construct a set function wrapper.
	 *
	 * @param setFunc
	 */
	public SelectFieldChoiceFunc (Object[] selectedChoiceFunc)
	{
		this.selectChoiceFunc = selectedChoiceFunc;
	}

	/**
	 * Apply the function to the given data.
	 *
	 * @param data The data to get the field.
	 * @return The field value.
	 */
	public SetResult_1<SelectChoice_2<D, F>> apply (D data, int index)
	{
		SetResult_1<SelectChoice_2<D, F>> result;
		MethodPtr3 funcMeth = ((MethodPtr3) this.selectChoiceFunc [1]);
		result = (SetResult_1<SelectChoice_2<D, F>>) funcMeth.
		       call___0_0 (selectChoiceFunc, data, index);
		return result;
	}
}
