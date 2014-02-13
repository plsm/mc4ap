/*
 * UIPanel.java
 *
 * Created on 18 de Dezembro de 2013, 19:50
 */

package ui.swing;

import data.closure.GetFieldFunc;
import data.closure.SetFieldFunc;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import jmercury.userInterface;
import ui.KeyGenerator;
/**
 *
 * @author  pedro
 */
public class UIPanel<D>
	extends DynamicDataPanel<UIPanel<D>, D, DataReference<D> >
{
	/**
	 * Key that this panel is known by the card layout in the {@code UIPanel} dynamic panel.
	 */
	final String key;
	
	static private KeyGenerator keyGenerator = new KeyGenerator ();
	
	/** Creates new form UIPanel */
	UIPanel (UIFrame frame)
	{
		super (new DataReference<D> (frame), frame);
		//initComponents ();
		this.key = keyGenerator.nextKey ();
	}
//	/**
//	 * Creates a {@code UIPanel} that is going to be used to edit the same data reference of {@code parent}.
//	 */
//	public UIPanel (UIFrame frame, UIPanel parent)
//	{
//		super (parent.data, frame);
//		initComponents ();
//		this.key = nextKey ();
//		this.frame.addPanel (this);
//	}

	/**
	 * Handles constructor {@code subdialog(D)}.  When the user clicks the button a new panel appers to edit the same data of this panel.
	 */
	@Override
	public UIPanel handle_subdialog (JButton button, final UIPanel<D> childPanel)
	{
		//this.addComponent (button, true, true, false);
		this.addDynamicComponent (button);
		ActionListener action;
		action = new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent evt)
			{
				childPanel.setData (UIPanel.this.data.getValue ());
				NavigateAction action = new NavigateAction (UIPanel.this.key)
				{
					@Override
					public boolean perform ()
					{
						return UIPanel.this.setData (childPanel.data.getValue ());
					}
				};
				UIPanel.this.frame.showPanel (childPanel.key, action);
			}
		};
		button.addActionListener (action);
		return this;
	}
	
	/**
	 * Handles constructor {@code editField(get(D,F),set(D,F),dialog(F))}.  Adds a button that shows the panel to edit a field of the data shown by this panel.
	 */
	@Override
	public <F> UIPanel handle_editField (JButton button, final Object[] getFunc, final Object[] setFunc, final UIPanel<F> childPanel)
	{
		//this.addComponent (button, true, true, false);
		this.addDynamicComponent (button);
		ActionListener action;
		action = new ActionListener () {
			GetFieldFunc<D, F> getFieldFunc = new GetFieldFunc<> (getFunc);
			SetFieldFunc<D, F> setFieldFunc = new SetFieldFunc<> (setFunc);
			@Override
			public void actionPerformed (java.awt.event.ActionEvent evt)
			{
				childPanel.setData (UIPanel.this.data.applyGetFieldFunc (getFieldFunc));
				NavigateAction action = new NavigateAction (UIPanel.this.key) {
					@Override
					public boolean perform ()
					{
						boolean ok = UIPanel.this.data.applySetFieldFunc (setFieldFunc, childPanel.data.getValue ());
						return ok;
					}
				};
				UIPanel.this.frame.showPanel (childPanel.key, action);
			}
		};
		button.addActionListener (action);
		return this;
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      setLayout(null);
   }// </editor-fold>//GEN-END:initComponents
	
	
   // Variables declaration - do not modify//GEN-BEGIN:variables
   // End of variables declaration//GEN-END:variables

	@Override
	UIPanel getUIPanel ()
	{
		return this;
	}
	
}
