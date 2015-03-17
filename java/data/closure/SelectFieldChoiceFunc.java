package data.closure;

import jmercury.runtime.MethodPtr3;
import jmercury.userInterface.SetResult_1;
import jmercury.userInterface.SelectChoice_2;

/**
 * Encapsultes the {@code func(D,int)=setResult(selectChoice(D,F))} closure
 * which sets a choice. This function is used in mercury constructor {@code
 * selectOneOf}. If is a function that given a data and a choice index
 * returns the next data and field value that should be presented to the
 * user.
 *
 * @param <D> The mercury type of the data
 * @param <F> The mercury type of the field data
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
	 * @param selectedChoiceFunc
	 */
	public SelectFieldChoiceFunc (Object[] selectedChoiceFunc)
	{
		this.selectChoiceFunc = selectedChoiceFunc;
	}

	/**
	 * Apply the function to the given data.
	 *
	 * @param data The data to get the field.
	 * @param index The index of the field data to return.
	 *
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
