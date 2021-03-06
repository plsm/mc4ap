/*
 * DataPanel.java
 *
 * Created on 20 de Dezembro de 2013, 19:52
 */

package ui.swing;

import data.AbstractMercuryReference;

/**
 * Represents a panel that is used to edit some mercury data type.
 *
 * <p> This panel is part of some frame that has an ok button to accept the new
 * data and a notification area to present message when a data update fails.
 *
 * @param <D> The mercury data type that is represented by this panel.
 *
 * @param <R> The reference to a mercury value that is represented by this panel.
 *
 * @author Pedro Mariano
 */
abstract class AbstractDataPanel<D, R extends AbstractMercuryReference<D> >
	extends javax.swing.JPanel
{
	/**
	 * The reference to the data that is going to be edited by this panel.
	 */
	final protected R data;

	/**
	 * The frame where this panel is inserted.
	 */
	protected UIFrame frame;

	/**
	 * Creates new {@code DataPanel} with the given data reference. This
	 * component is part of an {@code UIPanel} and shares its data reference.
	 */
	protected AbstractDataPanel (R data, UIFrame frame)
	{
		this.data = data;
		this.frame = frame;
	}

	/**
	 * Construct the panel used by the frame window.
	 * 
	 * @see UIFrame#UIFrame(java.lang.String, java.lang.Object)
	 */
	protected AbstractDataPanel (R data)
	{
		this (data, null);
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
