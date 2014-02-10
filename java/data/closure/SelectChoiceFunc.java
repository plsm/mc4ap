/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.closure;

import jmercury.runtime.MethodPtr3;
import jmercury.userInterface.SetResult_1;
import jmercury.userInterface.SelectChoice_2;

/**
 *
 * @author pedro
 */
public class SelectChoiceFunc<D, F>
{
	/**
	 * The function to obtain the selected choice.
	 */
	final private Object[] selectChoiceFunc;
	/**
	 * Construct a set function wrapper.
	 * @param setFunc 
	 */
	public SelectChoiceFunc (Object[] selectedChoiceFunc)
	{
		this.selectChoiceFunc = selectedChoiceFunc;
	}
	/**
	 * Apply the function to the given data.
	 * @param data The data to get the field.
	 * @return The field value.
	 */
	public SetResult_1<SelectChoice_2<D, F> > apply (D data, int index)
	{
		SetResult_1<SelectChoice_2<D, F> > result;
		MethodPtr3 funcMeth = ((MethodPtr3) this.selectChoiceFunc [1]);
		result = (SetResult_1<SelectChoice_2<D, F> >) funcMeth.call___0_0 (selectChoiceFunc, data, index);
		return result;
	}
}
