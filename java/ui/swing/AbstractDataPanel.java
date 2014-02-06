/*
 * DataPanel.java
 *
 * Created on 20 de Dezembro de 2013, 19:52
 */

package ui.swing;

import ui.MercuryReference;

/**
 * Represents a panel that is used to edit some mercury data type.
 *
 * <p> This panel is part of some frame that has an ok button to accept the new data and a notification area to present message when a data update fails.
 *
 * @author  pedro
 */
abstract class AbstractDataPanel
	extends javax.swing.JPanel
{
	/**
	 * The data that is going to be edited by this panel.
	 */
	final protected MercuryReference data;
	/**
	 * The frame where this panel is inserted.
	 */
	protected UIFrame frame;
	
	/**
	 * Creates new {@code DataPanel} with an unique data reference.  Components that are part of this panel should use the other constructor.
	 */
	protected AbstractDataPanel (UIFrame frame)
	{
		this.data = new DataReference (frame);
		this.frame = frame;
	}
	/**
	 * Creates new {@code DataPanel} with the given data reference.  This component is part of an {@code UIPanel} and shares its data reference.
	 */
	protected AbstractDataPanel (MercuryReference data, UIFrame frame)
	{
		this.data = data;
		this.frame = frame;
	}
	
	/**
	 * Construct the panel used by the frame window.
	 * 
	 * @see UIFrame#UIFrame(java.lang.String, java.lang.Object)
	 */
	protected AbstractDataPanel (MercuryReference data)
	{
		this (data, null);
	}
	
	/**
	 * Get a value of some field of the data in this panel.
	 *
	 * @param getFunc the get function used to obtain a field value of the data.
	 *
	 * @return the value of a field of the data in this panel.
	 */
	protected Object applyGetFunc (Object[] getFunc)
	{
		return data.applyGetFunc (getFunc);
//		jmercury.runtime.MethodPtr2 funcMeth = ((jmercury.runtime.MethodPtr2) getFunc[1]);
//		return funcMeth.call___0_0 (getFunc, data.value);
	}
	/**
	 * Updates a field of {@code data} with the given value.  The set function returns an instance of {@code maybe_error(T)}.  If constructor {@code error(string)} is returned, we show the message in the notification area and disable the ok button.
	 *
	 * @param setFunc the set function used to update a field of {@code data} with the given value.
	 *
	 * @param field the new value of the field to update.
	 *
	 * @return {@code true} if the update succeeds.
	 */
	protected boolean applySetFunc (Object[] setFunc, Object field)
	{
		jmercury.runtime.MethodPtr3 funcMeth = ((jmercury.runtime.MethodPtr3) setFunc[1]);
		return handle_maybeError ((jmercury.maybe.Maybe_error_2) funcMeth.call___0_0 (setFunc, data.getValue (), field));
	}
	/**
	 * Handle a value of type {@code maybe_error(T)} where {@code T} is this panel data type.  If the value is ok {@code ok(X)} we update the data value this panel references to.  Otherwise, if the value is {@code error(M)} we show the message in the frame notification area.
	 * 
	 * @param mdata
	 * @return True if there was no error.
	 */
	protected boolean handle_maybeError (jmercury.maybe.Maybe_error_2 mdata)
	{
		boolean error = true;
		if (mdata instanceof jmercury.maybe.Maybe_error_2.Ok_1) {
			this.data.setValue (((jmercury.maybe.Maybe_error_2.Ok_1) mdata).F1);
			this.frame.messagesLabel.setText (" ");
			error = false;
		}
		else {
			this.frame.messagesLabel.setText ((String) ((jmercury.maybe.Maybe_error_2.Error_1) mdata).F1);
		}
		this.frame.okButton.setEnabled (!error);
		return !error;
	}
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      setLayout(new java.awt.GridBagLayout());
   }// </editor-fold>//GEN-END:initComponents
	
	
   // Variables declaration - do not modify//GEN-BEGIN:variables
   // End of variables declaration//GEN-END:variables
	
}
