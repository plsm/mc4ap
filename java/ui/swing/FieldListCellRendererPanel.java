/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.swing;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

/**
 * Comoponent responsible for displaying the elements of a field list.
 * 
 * @author Pedro Mariano
 */
public class FieldListCellRendererPanel<D>
	extends DynamicDataPanel<FieldListCellRendererPanel, D, DataReference<D> >
	implements
		TableCellRenderer
{
	/**
	 * The {@code UIPanel} where this {@code InlinePanelField} is.
	 */
	final private UIPanel uipanel;
	/**
	 * Creates new form FieldListCellRendererPanel
	 */
	FieldListCellRendererPanel (UIFrame frame, UIPanel uipanel)
	{
		super (new DataReference (frame), frame);
		this.initComponents ();
		this.uipanel = uipanel;
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), javax.swing.BorderFactory.createEtchedBorder()));
   }// </editor-fold>//GEN-END:initComponents


   // Variables declaration - do not modify//GEN-BEGIN:variables
   // End of variables declaration//GEN-END:variables

	@Override
	public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		//System.out.println ("FieldListCellRendererPanel.getTableCellRendererComponent(): Rendering " + value);//DEBUG
		if (isSelected) {
			setBackground (table.getSelectionBackground ());
			setForeground (table.getSelectionForeground ());
		}
		else {
			setBackground (table.getBackground ());
			setForeground (table.getForeground ());
		}
		if (hasFocus) {
			setBorder (BorderFactory.createCompoundBorder (BorderFactory.createEtchedBorder(), BorderFactory.createEtchedBorder()));
		}
		else {
			setBorder (BorderFactory.createEmptyBorder ());
		}
		this.setData ((D) value);
		return this;
	}

	@Override
	public FieldListCellRendererPanel handle_subdialog (JButton button, UIPanel childPanel)
	{
		//this.addComponent (button, true, true, true);
		this.addDynamicComponent (button);
		return this;
	}

	@Override
	public FieldListCellRendererPanel handle_editField (JButton button, Object[] getFunc, Object[] setFunc, UIPanel childPanel)
	{
		//this.addComponent (button, true, true, true);
		this.addDynamicComponent (button);
		return this;
	}

	@Override
	UIPanel getUIPanel ()
	{
		return this.uipanel;
	}
}
