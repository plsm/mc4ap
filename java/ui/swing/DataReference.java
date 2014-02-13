package ui.swing;

import data.AbstractDataReference;
import jmercury.userInterface.SetResult_1;

/**
 * Holds the current value of the data shown in some component.
 * 
 * <p> This class allows different components to access the most recent value of some data.
 * 
 * @author Pedro Mariano
 */
final class DataReference<D>
	extends AbstractDataReference<D>
{
	UIFrame frame;
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
	DataReference (UIFrame frame, D value)
	{
		this.setValue (value);
		this.frame = frame;
	}

	@Override
	public boolean handle_setResult (SetResult_1<D> mdata)
	{
		boolean result;
		if (mdata instanceof SetResult_1.Ok_1) {
			SetResult_1.Ok_1<D> newData = (SetResult_1.Ok_1<D>) mdata;
			this.setValue (newData.F1);
			this.frame.messagesLabel.setText (" ");
			result = false;
		}
		else if (mdata instanceof SetResult_1.Error_1) {
			SetResult_1.Error_1 error = (SetResult_1.Error_1) mdata;
			this.frame.messagesLabel.setText (error.F1);
			result = true;
		}
		else {
			throw new UnsupportedOperationException ("Unsupported SetResult");
		}
		frame.okButton.setEnabled (!result);
		return !result;
	}
}
