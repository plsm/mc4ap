package ui.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import ui.MercuryReference;

/**
 * A panel inside some {@code UIPanel} that displays and can be used to edit a field of {@code UIPanel} mercury data reference.
 * 
 * @author Pedro Mariano
 */
final public class InlinePanelField<D>
	extends DynamicDataPanel<InlinePanelField>
	implements ComponentPopulate
{
	/**
	 * The {@code UIPanel} where this {@code InlinePanelField} is.
	 */
	final private UIPanel uipanel;
	
	InlinePanelField (MercuryReference<D> data, UIFrame frame, UIPanel parentPanel, String panelName)
	{
		super (data, frame);
		this.uipanel = parentPanel;
		setBorder(javax.swing.BorderFactory.createTitledBorder (panelName));
//		this.initComponents ();
	}

	InlinePanelField (MercuryReference data, UIFrame frame, UIPanel parentPanel)
	{
		super (data, frame);
		this.uipanel = parentPanel;
	}

	@Override
	public void valueChanged (MercuryReference data)
	{
		for (ComponentPopulate cp : this.componentsPopulate) {
			cp.valueChanged (this.data);
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {
   }// </editor-fold>//GEN-END:initComponents
	
	
   // Variables declaration - do not modify//GEN-BEGIN:variables
   // End of variables declaration//GEN-END:variables

	@Override
	public InlinePanelField handle_subdialog (JButton button, final UIPanel childPanel)
	{
		this.addDynamicComponent (button);
		ActionListener action;
		action = new ActionListener () {
			public void actionPerformed (ActionEvent evt)
			{
				childPanel.setData (InlinePanelField.this.data.getValue ());
				NavigateAction action = new NavigateAction (InlinePanelField.this.getUIPanel ().key)
				{
					public boolean perform ()
					{
//						UIPanel.this.data = childPanel.data;
						return true;
					}
				};
				InlinePanelField.this.frame.showPanel (childPanel.key, action);
			}
		};
		button.addActionListener (action);
		return this;
		//throw new IllegalStateException ("InlinePanelField does not have buttons that open other panels");
	}

	@Override
	public InlinePanelField handle_editField (JButton button, final Object[] getFunc, final Object[] setFunc, final UIPanel childPanel)
	{
		this.addDynamicComponent (button);
		ActionListener action;
		action = new ActionListener () {
			public void actionPerformed (java.awt.event.ActionEvent evt)
			{
				childPanel.setData (applyGetFunc (getFunc));
				NavigateAction action = new NavigateAction (InlinePanelField.this.getUIPanel ().key) {
					public boolean perform ()
					{
						boolean ok = applySetFunc (setFunc, childPanel.data.getValue ());
						if (ok) {
							InlinePanelField.this.setData (InlinePanelField.this.data.getValue ());
						}
						return ok;
					}
				};
				InlinePanelField.this.frame.showPanel (childPanel.key, action);
			}
		};
		button.addActionListener (action);
		return this;
//		throw new IllegalStateException ("InlinePanelField does not have buttons that open other panels");
	}

	@Override
	UIPanel getUIPanel ()
	{
//		if (this.uipanel == null) {
//			throw new IllegalStateException ("Never reached");
//		}
//		else {
			return this.uipanel;
//		}
	}
	/*
	 *     System.out.print ("Changed from " + this.getPreferredSize ());
      this.setPreferredSize (new Dimension (
         this.getSize ().width,
         this.getSize ().height));
   System.out.println (" to " + this.getPreferredSize ());
	 */
}
