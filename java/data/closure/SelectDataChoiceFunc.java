/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.closure;

import jmercury.runtime.MethodPtr3;
import jmercury.userInterface.SetResult_1;

/**
 * Encapsultes the {@code func(D,int)=setResult(D)} closure which sets a data
 * choice. This function is used in constructor {@code selectOneOf/3}. If is a
 * function that given a data and a choice index returns the next data that
 * should be presented to the user.
 *
 * @author pedro
 */
public class SelectDataChoiceFunc<D>
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
	public SelectDataChoiceFunc (Object[] selectedChoiceFunc)
	{
		this.selectChoiceFunc = selectedChoiceFunc;
	}

	/**
	 * Apply the function to the given data and index.
	 *
	 * @param data The data to set.
	 * @param index The data choice index.
	 * @return The selected data choice value.
	 */
	public SetResult_1<D> apply (D data, int index)
	{
		SetResult_1<D> result;
		MethodPtr3 funcMeth = ((MethodPtr3) this.selectChoiceFunc [1]);
		result = (SetResult_1<D>) funcMeth.
		       call___0_0 (selectChoiceFunc, data, index);
		return result;
	}
}
